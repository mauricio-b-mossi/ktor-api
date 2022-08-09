package com.example.routes

import com.example.models.Customer
import com.example.models.customerStorage
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.Accepted
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerRouting() {
    route("/customer") {

        get {

            if (customerStorage.isNotEmpty()) {
                call.respond(customerStorage)
            } else {
                call.respondText("No customer found", status = HttpStatusCode.OK)
            }

        }

        get("{id?}") {

            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing Id",
                status = BadRequest
            )

            val customer = customerStorage.find { customer -> customer.id == id } ?: return@get call.respondText(
                "No customer with id $id",
                status = NotFound
            )

            call.respond(customer)

        }

        post {

//            call.receive<Type> works with Content Negotiation to deserialize the JSON into a Customer obj
            val customer = call.receive<Customer>()

            customerStorage.add(customer)

            call.respondText("Customer stored correctly", status = Created)

        }

        delete("{id?}") {

            val id = call.parameters["id"] ?: return@delete call.respondText(
                "Missing Id",
                status = NotFound
            )
//
//            val customer = customerStorage.find { customer -> customer.id == id  } ?: call.respondText(
//                "No Customer with id $id",
//                status = BadRequest
//            )
//
//            customerStorage.remove(customer)

            if (customerStorage.removeIf { customer -> customer.id == id }) {
                call.respondText("Customer removed correctly", status = Accepted)
            } else {
                call.respondText("Not Found", status = NotFound)
            }

        }

    }
}

