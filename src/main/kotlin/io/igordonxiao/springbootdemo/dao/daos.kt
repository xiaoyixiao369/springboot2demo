package io.igordonxiao.springbootdemo.dao

import io.igordonxiao.springbootdemo.model.User
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface IUserRepository : ReactiveMongoRepository<User, String>