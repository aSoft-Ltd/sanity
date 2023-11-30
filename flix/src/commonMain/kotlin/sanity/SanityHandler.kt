package sanity

class SanityHandler(
    internal val bus: EventBus,
    private val maxClientsPerIp: Int
) {
//    internal val clients = mutableMapOf<String, MutableList<Client>>()
//
//    internal fun ensureMaxPolicy(domain: String) {
//        val connected = clients[domain] ?: return
//        if (connected.size < maxClientsPerIp) return
//        val discard = connected.first()
//        discard.alive = false
//        discard.subscriber.unsubscribe()
//        connected.remove(discard)
//    }
//
//    internal fun add(domain: String, subscriber: Subscriber): Client {
//        val list = clients.getOrPut(domain) { mutableListOf() }
//        val client = Client(subscriber, true)
//        list.add(client)
//        return client
//    }
}