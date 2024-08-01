package sanity

import io.ktor.http.CacheControl
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.server.application.call
import io.ktor.server.response.cacheControl
import io.ktor.server.response.header
import io.ktor.server.response.respondBytesWriter
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.utils.io.writeStringUtf8
import kotlinx.coroutines.*

fun Routing.installSanity(controller: SanityController) = get(controller.endpoint.events()) {
    call.response.cacheControl(CacheControl.NoCache(null))
    call.response.header(HttpHeaders.Connection, "Keep-Alive")
    call.respondBytesWriter(contentType = ContentType.Text.EventStream) {
        val scope = CoroutineScope(Dispatchers.Default)
        // TODO: Subscriber gets treated harshly by the GC, find out why
        val subscriber = controller.handler.bus.subscribe("*") {
            if (!isClosedForWrite) scope.launch {
                writeStringUtf8("id: ${it.topic}\n")
                writeStringUtf8("event: message\n")
                writeStringUtf8("data: ${it.data}\n")
                writeStringUtf8("\n")
                flush()
            }
        }

        try {
            awaitCancellation()
        } finally {
            subscriber.unsubscribe()
        }
    }
}