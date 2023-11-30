package sanity

import kiota.EventSource


fun RemoteBus(endpoint: SanityEndpoint): LocalBus {
    val bus = LocalBus()
    EventSource(endpoint.events()).on("message") {
        bus.dispatch(it.id ?: "*", it.data)
    }
    return bus
}