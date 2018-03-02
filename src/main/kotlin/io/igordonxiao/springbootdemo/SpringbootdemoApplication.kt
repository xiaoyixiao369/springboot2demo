package io.igordonxiao.springbootdemo

import org.springframework.boot.Banner.Mode.OFF
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.router
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono


@SpringBootApplication
class SpringbootdemoApplication

@Document
data class User(@Id val id: String, val name: String)

interface IUserRepository : ReactiveMongoRepository<User, String>

@Configuration
class ApiRoutes(private val userRepository: IUserRepository) {

    @Bean
    fun apiRouter() = router {

        "/users".nest {
            GET("/") {
                ok().body(userRepository.findAll(), User::class.java)
            }
            POST("/") {
                ok().body(userRepository.insert(it.bodyToMono(User::class.java)).toMono(), User::class.java)
            }
        }

    }
}

@Component
class CorsFilter : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        exchange.response.headers.add("Access-Control-Allow-Origin", "*")
        exchange.response.headers.add("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS")
        exchange.response.headers.add("Access-Control-Allow-Headers", "DNT,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Content-Range,Range")
        if (exchange.request.method == HttpMethod.OPTIONS) {
            exchange.response.headers.add("Access-Control-Max-Age", "1728000")
            exchange.response.statusCode = HttpStatus.NO_CONTENT
            return Mono.empty()
        } else {
            exchange.response.headers.add("Access-Control-Expose-Headers", "DNT,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Content-Range,Range")
            return chain.filter(exchange) ?: Mono.empty()
        }
    }

}

fun main(args: Array<String>) {
    runApplication<SpringbootdemoApplication>(*args) {
        setBannerMode(OFF)
    }
}
