package com.mthomaz.routemanager.controller

import com.mthomaz.routemanager.models.RouteModel
import com.mthomaz.routemanager.service.RouteService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


@RestController
class RouteController (
        private val routeService: RouteService) {

    @PostMapping(path = ["/save"])
    fun toggles(@RequestBody(required = true) route: RouteModel) : Mono<ResponseEntity<String>> {

        return routeService.save(route)!!.map {
            return@map ResponseEntity.ok("Ola")
        }


    }


}