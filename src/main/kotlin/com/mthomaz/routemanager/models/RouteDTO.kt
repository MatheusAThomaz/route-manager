package com.mthomaz.routemanager.models

import lombok.Data
import org.springframework.cloud.gateway.route.Route
import org.springframework.cloud.gateway.route.RouteDefinition

@Data
class RouteDTO (
        val routeName: String,
        val filters: List<String>,
        val predicates: List<String>,
        val uri: String
) {

}