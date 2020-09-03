package com.mthomaz.routemanager.utils

import com.mthomaz.routemanager.models.RouteDTO
import org.springframework.cloud.gateway.route.RouteDefinition
import org.springframework.stereotype.Service

@Service
class RouteUtils {

    companion object {

        fun routeDefinitionToDTO(routeDefinition: List<RouteDefinition>): List<RouteDTO> {

            val routes: ArrayList<RouteDTO> = ArrayList()

            routeDefinition.forEach {

                val filters = it.filters.map { it2 ->
                    it2.name
                }

                routes.add(RouteDTO(it.id, it.filters.map { it.name }, it.predicates.map { it.name }, it.uri.toString()))
            }

            return routes

        }
    }
}