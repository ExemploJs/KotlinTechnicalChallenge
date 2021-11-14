package com.technical.challenge.user.repository

import com.technical.challenge.user.entity.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long> {

}
