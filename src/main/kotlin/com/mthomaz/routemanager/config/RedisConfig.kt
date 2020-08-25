package com.mthomaz.routemanager.config


import com.mthomaz.routemanager.config.repository.RedisRouteDefinitionRepository
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.cloud.gateway.route.RouteDefinition
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {

    @Bean
    @ConditionalOnProperty(value = ["spring.cloud.gateway.redis-route-definition-repository.enabled"], havingValue = "true")
    @ConditionalOnClass(ReactiveRedisTemplate::class)
    fun redisRouteDefinitionRepository(
            reactiveRedisTemplate: ReactiveRedisTemplate<String?, RouteDefinition?>?): RedisRouteDefinitionRepository? {
        return RedisRouteDefinitionRepository(reactiveRedisTemplate)
    }

    @Bean
    fun reactiveRedisRouteDefinitionTemplate(
            factory: ReactiveRedisConnectionFactory?): ReactiveRedisTemplate<String?, RouteDefinition?>? {
        val keySerializer = StringRedisSerializer()
        val valueSerializer: Jackson2JsonRedisSerializer<RouteDefinition> = Jackson2JsonRedisSerializer(
                RouteDefinition::class.java)
        val builder: RedisSerializationContext.RedisSerializationContextBuilder<String, RouteDefinition> = RedisSerializationContext
                .newSerializationContext(keySerializer)
        val context: RedisSerializationContext<String, RouteDefinition> = builder
                .value(valueSerializer).build()
        return factory?.let { ReactiveRedisTemplate(it, context) }
    }

}