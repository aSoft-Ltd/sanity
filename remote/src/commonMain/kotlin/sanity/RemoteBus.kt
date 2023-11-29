package sanity

import kiota.EventSource


fun RemoteBus(endpoint: SanityEndpoint): LocalBus {
    val bus = LocalBus()
    println("Remote bus at: ${endpoint.events()}")
    EventSource(endpoint.events()).on("message") {
        println("Received from")
        bus.dispatch(it.id ?: "*", it.data)
    }
    return bus
}