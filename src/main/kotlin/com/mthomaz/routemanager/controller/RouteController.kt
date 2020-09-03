package com.mthomaz.routemanager.controller

import com.mthomaz.routemanager.models.RouteDTO
import com.mthomaz.routemanager.service.RouteService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@RestController
@RequestMapping(path = ["/routes"])
class RouteController(
        private val routeService: RouteService) {

    @PostMapping
    fun toggles(@RequestBody(required = true) route: RouteDTO): Mono<ResponseEntity<String>> {
        return routeService.save(route)!!.map {
            return@map ResponseEntity.ok("Ola")
        }
    }

    @GetMapping
    fun toggles(): Flux<List<RouteDTO>> {
        return routeService.get()
    }


}