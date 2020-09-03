package com.mthomaz.routemanager.service

import com.mthomaz.routemanager.models.RouteDTO
import com.mthomaz.routemanager.config.repository.RedisRouteDefinitionRepository
import com.mthomaz.routemanager.utils.RouteUtils
import org.springframework.cloud.gateway.filter.FilterDefinition
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition
import org.springframework.cloud.gateway.route.RouteDefinition
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.net.URI


@Service
class RouteService(private val routeDefinitionRepository: RedisRouteDefinitionRepository) {

    fun save(route: RouteDTO): Mono<Void>? {

        val routeDefinition = RouteDefinition()

        routeDefinition.id = route.routeName
        routeDefinition.uri = URI.create(route.uri)
        routeDefinition.filters = extractFilters(route.filters)
        routeDefinition.predicates = extractPredicates(route.predicates)

        return routeDefinitionRepository.save(Mono.just(routeDefinition)).map {
            return@map it
        }
    }

    fun get(): Flux<List<RouteDTO>> {

        val routeDefinition = ArrayList<RouteDefinition>()

        return routeDefinitionRepository.getRouteDefinitions().map {
            if (it != null) {
                routeDefinition.add(it)
            }
        }.map {
            return@map RouteUtils.routeDefinitionToDTO(routeDefinition)
        }

    }



    fun extractFilters(filters: List<String>): List<FilterDefinition> {
        val definitions: ArrayList<FilterDefinition> = ArrayList()

        filters.map {
            definitions.add(FilterDefinition(it))
        }

        return definitions
    }

    fun extractPredicates(predicates: List<String>): List<PredicateDefinition> {
        val definitions: ArrayList<PredicateDefinition> = ArrayList()

        predicates.map {
            definitions.add(PredicateDefinition(it))
        }

        return definitions
    }


}