package com.technical.challenge

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class UserNotFoundException(message: String) : RuntimeException(message) {

    private object UserNotFoundException {
        const val DEFAULT_MESSAGE = "Usuário não encontrado!"
    }

    constructor() : this(UserNotFoundException.DEFAULT_MESSAGE)
}

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class AccountNotFoundException(message: String) : RuntimeException(message) {

    private object AccountNotFoundException {
        const val DEFAULT_MESSAGE = "Conta não encontrada!"
    }

    constructor() : this(AccountNotFoundException.DEFAULT_MESSAGE)
}

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class AccountDoesntHaveBalanceException(message: String) : RuntimeException(message) {

    private object AccountDoesntHaveBalanceException {
        const val DEFAULT_MESSAGE = "A conta não possui saldo suficiente!"
    }

    constructor() : this(AccountDoesntHaveBalanceException.DEFAULT_MESSAGE)
}

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class FromUserIdCannotBeTheSameOfToUserIdException(message: String) : RuntimeException(message) {

    private object FromUserIdCannotBeTheSameOfToUserIdException {
        const val DEFAULT_MESSAGE = "O id do usuário que está transferindo não pode ser igual ao do usuário que está recebendo!"
    }

    constructor() : this(FromUserIdCannotBeTheSameOfToUserIdException.DEFAULT_MESSAGE)
}

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class FieldCannotBeNullException(message: String) : RuntimeException(message) {

    private object FieldCannotBeNullException {
        const val DEFAULT_MESSAGE = "O campo não pode ser nulo!"
    }

    constructor() : this(FieldCannotBeNullException.DEFAULT_MESSAGE)
}

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class ValueCannotBeNegativeOrZeroException(message : String) : RuntimeException(message) {

    private object ValueCannotBeNegativeOrZeroException {
        const val DEFAULT_MESSAGE = "O campo não pode ser negativo ou zerado!"
    }

    constructor() : this(ValueCannotBeNegativeOrZeroException.DEFAULT_MESSAGE)
}

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
class APIException(message : String) : RuntimeException(message) {

    private object APIException {
        const val DEFAULT_MESSAGE = "Ocorreu um erro interno no servidor! Contate o Administrador ou tente novamente mais tarde!"
    }

    constructor() : this(APIException.DEFAULT_MESSAGE)
}

