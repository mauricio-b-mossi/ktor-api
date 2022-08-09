package com.example.routes

import com.example.models.orderStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

//What I do not like is that the routes do not reinforce a type of data to be returned.
fun Route.getOrders(){

    get("/orders"){
        if (orderStorage.isNotEmpty()){
            call.respond(orderStorage)
        }
    }
}

fun Route.getOrderByNumber(){

    get("/order/{number?}"){

        val number = call.parameters["number"] ?: return@get call.respondText(
            "Parameter Number missing",
            status = HttpStatusCode.BadRequest
        )

        val order = orderStorage.find { order -> order.number == number  } ?: return@get call.respondText(
            "No order with number $number found",
            status = HttpStatusCode.NotFound
        )

        call.respond(order)
    }
}

fun Route.getOrderTotal(){

    get("/order/{number?}/total"){

        val number = call.parameters["number"] ?: return@get call.respondText(
            "Parameter Number missing",
            status = HttpStatusCode.BadRequest
        )

        val order = orderStorage.find { order -> order.number == number  } ?: return@get call.respondText(
            "No order with number $number found",
            status = HttpStatusCode.NotFound
        )

        val total : Double = order.contents.sumOf { it.price * it.amount }

        call.respond(total)
    }
}