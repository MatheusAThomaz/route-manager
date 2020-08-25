package com.mthomaz.routemanager.models

import lombok.Data

@Data
data class RouteModel (
        val routeName: String,
        val filters: List<String>,
        val predicates: List<String>,
        val uri: String
)