package com.example.plugins

import com.example.routes.customerRouting
import com.example.routes.getOrderByNumber
import com.example.routes.getOrderTotal
import com.example.routes.getOrders
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting() {

    routing {
        get("/"){
            call.respond("Hello")
        }
        customerRouting()
        getOrders()
        getOrderByNumber()
        getOrderTotal()
    }
}
