package com.mthomaz.routemanager.utils

import com.mthomaz.routemanager.models.RouteDTO
import org.springframework.cloud.gateway.route.RouteDefinition
import org.springframework.stereotype.Service

@Service
class RouteUtils {

    companion object {

        fun routeDefinitionToDTO(routeDefinition: RouteDefinition): RouteDTO {

            return RouteDTO(routeDefinition.id, routeDefinition.filters.map { it.name }, routeDefinition.predicates.map { it.name.plus("=").plus(it.args["_genkey_0"]) }, routeDefinition.uri.toString())

        }
    }
}