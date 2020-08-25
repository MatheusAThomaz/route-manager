package com.mthomaz.routemanager.config.repository

import mu.KLogger
import mu.KotlinLogging
import org.springframework.cloud.gateway.route.RouteDefinition
import org.springframework.cloud.gateway.route.RouteDefinitionRepository
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.core.ReactiveValueOperations
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.empty
import java.util.function.BiConsumer

@Repository
class RedisRouteDefinitionRepository(
        private val reactiveRedisTemplate: ReactiveRedisTemplate<String?, RouteDefinition?>?,
        private val log: KLogger = KotlinLogging.logger {}) : RouteDefinitionRepository {

    private val ROUTEDEFINITION_REDIS_KEY_PREFIX_QUERY = "routedefinition_"


    private val routeDefinitionReactiveValueOperations: ReactiveValueOperations<String?, RouteDefinition?>?

    init {
        this.routeDefinitionReactiveValueOperations = reactiveRedisTemplate!!.opsForValue()
    }


    override fun save(route: Mono<RouteDefinition>): Mono<Void> {

            return route.flatMap { routeDefinition ->

                val id = createKey(routeDefinition.id)

                log.info { "id = $id" }

                return@flatMap routeDefinitionReactiveValueOperations?.set(id , routeDefinition)?.then()

            }

    }

    override fun getRouteDefinitions(): Flux<RouteDefinition?> {
        return reactiveRedisTemplate!!.keys(createKey("*"))
                .flatMap { key: Any? -> reactiveRedisTemplate.opsForValue()[key!!] }
                .onErrorContinue(BiConsumer { throwable: Any, routeDefinition: Any? ->
                })
    }

    override fun delete(routeId: Mono<String>?): Mono<Void> {
        TODO("Not yet implemented")
    }

    private fun createKey(routeId: String): String {
        return ROUTEDEFINITION_REDIS_KEY_PREFIX_QUERY + routeId
    }
}