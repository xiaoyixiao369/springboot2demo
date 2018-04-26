package io.igordonxiao.springbootdemo.handler

import io.igordonxiao.springbootdemo.dao.IUserRepository
import io.igordonxiao.springbootdemo.model.User
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok

@Component
class UserHandler(private val userRepository: IUserRepository) {

    fun findUsers(req: ServerRequest) = ok().body(userRepository.findAll(), User::class.java)

    fun insert(req: ServerRequest) = ok().body(userRepository.insert(req.bodyToMono(User::class.java)), User::class.java)
}