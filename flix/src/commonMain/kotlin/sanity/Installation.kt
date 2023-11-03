package sanity

import io.ktor.http.CacheControl
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.server.application.call
import io.ktor.server.plugins.origin
import io.ktor.server.response.cacheControl
import io.ktor.server.response.header
import io.ktor.server.response.respondBytesWriter
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.utils.io.writeStringUtf8
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun Routing.installSanity(handler: SanityHandler, endpoint: SanityEndpoint) = get(endpoint.events()) {
    call.response.cacheControl(CacheControl.NoCache(null))
    call.response.header(HttpHeaders.Connection, "Keep-Alive")
    val ipv4 = call.request.origin.remoteAddress
    println("Starting new connection: $ipv4")
    handler.ensureMaxPolicy(call.request.origin.remoteAddress)
    call.respondBytesWriter(contentType = ContentType.Text.EventStream) {
        val subscriber = handler.bus.subscribe("*") {
            launch {
                writeStringUtf8("id: ${it.topic}\n")
                writeStringUtf8("event: message\n")
                writeStringUtf8("data: ${it.data}\n")
                writeStringUtf8("\n")
                flush()
            }
        }

        val client = handler.add(ipv4, subscriber)

        while (client.alive) { // keep alive for future events
            delay(1000)
        }
    }
    println("Disconnected: $ipv4")
}