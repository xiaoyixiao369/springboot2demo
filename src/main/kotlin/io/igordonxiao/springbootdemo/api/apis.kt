package io.igordonxiao.springbootdemo.api

import io.igordonxiao.springbootdemo.model.User
import io.igordonxiao.springbootdemo.handler.UserHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class ApiRoutes(val userHandler: UserHandler) {

    @Bean
    fun apiRouter() = router {

        "/users".nest {
            GET("/", userHandler::findUsers)
            POST("/", userHandler::insert)
        }

    }
}