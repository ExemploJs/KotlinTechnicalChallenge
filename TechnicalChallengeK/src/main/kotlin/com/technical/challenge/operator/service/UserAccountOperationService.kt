package com.technical.challenge.operator.service

import com.technical.challenge.APIException
import com.technical.challenge.AccountDoesntHaveBalanceException
import com.technical.challenge.account.processor.AccountProcessor
import com.technical.challenge.account.service.AccountService
import com.technical.challenge.history.data.Operation
import com.technical.challenge.history.producer.HistoryProducer
import com.technical.challenge.history.request.HistoryRequest
import com.technical.challenge.operator.request.BillRequest
import com.technical.challenge.operator.request.TransferRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class UserAccountOperationService @Autowired constructor(
    val accountService: AccountService,
    val historyProducer: HistoryProducer
) {
    @Value("\${kafka.enable}")
    var isKafkaEnabled: Boolean? = null

    @Transactional
    fun withdraw(userId: Long, value: BigDecimal) {
        val account = accountService
            .getAccountAsAccountItself(userId)

        try {
            accountService.save(
                AccountProcessor(
                    account
                ).withdraw(value)
            )

            isKafkaEnabled?.also { kafkaEnable ->
                if (kafkaEnable) {
                    historyProducer.send(
                        HistoryRequest(
                            Operation.WITHDRAW,
                            "Saque de $value realizado por ${account.user.name}", account.id!!, account.balance
                        )
                    )
                }
            }
        } catch (e: AccountDoesntHaveBalanceException) {
            throw AccountDoesntHaveBalanceException()
        } catch (e: Exception) {
            throw APIException(e.message!!)
        }
    }

    @Transactional
    fun deposit(userId: Long, value: BigDecimal?) {
        val account = accountService.getAccountAsAccountItself(userId)

        try {
            accountService.save(AccountProcessor(account).deposit(value!!))

            isKafkaEnabled?.also { kafkaEnable ->
                if (kafkaEnable) {
                    historyProducer.send(
                        HistoryRequest(
                            Operation.DEPOSIT,
                            "Depósito de $value realizado por ${account.user.name}", account.id!!, account.balance
                        )
                    )
                }
            }

        } catch (e: Exception) {
            throw APIException(e.message!!)
        }
    }

    @Transactional
    fun transfer(fromUserId: Long, toUserId: Long, transferRequest: TransferRequest) {
        val fromAccount = accountService.getAccountAsAccountItself(fromUserId)
        val toAccount = accountService.getAccountAsAccountItself(toUserId)

        try {
            accountService.save(AccountProcessor(fromAccount).withdraw(transferRequest.transferedValue))
            accountService.save(AccountProcessor(toAccount).deposit(transferRequest.transferedValue))

            isKafkaEnabled?.also { kafkaEnable ->
                if (kafkaEnable) {
                    historyProducer.send(
                        HistoryRequest(
                            Operation.TRANSFERENCE,
                            "Enviando transferência de ${transferRequest.transferedValue} " +
                                    "para ${toAccount.user.name} " +
                                    "realizado por ${fromAccount.user.name}", fromAccount.id!!, fromAccount.balance
                        )
                    )

                    historyProducer.send(
                        HistoryRequest(
                            Operation.TRANSFERENCE,
                            "Recebido transferência de ${transferRequest.transferedValue} na conta de ${toAccount.user.name} " +
                                    "realizado por ${fromAccount.user.name}",
                            toAccount.id!!,
                            toAccount.balance
                        )
                    )
                }
            }
        } catch (e: AccountDoesntHaveBalanceException) {
            throw AccountDoesntHaveBalanceException()
        } catch (e: Exception) {
            throw APIException(e.message!!)
        }
    }

    @Transactional
    fun payBill(userId: Long, billRequest: BillRequest) {
        val account = accountService
            .getAccountAsAccountItself(userId)

        try {
            this.accountService
                .save(
                    AccountProcessor(
                        account
                    )
                        .withdraw(billRequest.value)
                )

            isKafkaEnabled?.also { kafkaEnable ->
                if (kafkaEnable) {
                    historyProducer.send(
                        HistoryRequest(
                            Operation.BILL_PAYMENT,
                            "Pagamento de conta de ${billRequest.value} realizado por ${account.user.name}",
                            account.id!!,
                            account.balance
                        )
                    )
                }
            }
        } catch (e: Exception) {
            throw APIException(e.message!!)
        }
    }
}
