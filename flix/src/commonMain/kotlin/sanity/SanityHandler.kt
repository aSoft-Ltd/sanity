package sanity

class SanityHandler(
    internal val bus: EventBus,
    private val maxClientsPerIp: Int
) {
    internal val clients = mutableMapOf<String, MutableList<Client>>()

    internal fun ensureMaxPolicy(ipv4: String) {
        val connected = clients[ipv4] ?: return
        if (connected.size < maxClientsPerIp) return
        val discard = connected.first()
        discard.alive = false
        discard.subscriber.unsubscribe()
        connected.remove(discard)
    }

    internal fun add(ipv4: String, subscriber: Subscriber): Client {
        val list = clients.getOrPut(ipv4) { mutableListOf() }
        val client = Client(subscriber, true)
        list.add(client)
        return client
    }
}