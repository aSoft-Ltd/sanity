package sanity

import kiota.EventSource
import kiota.Url
import sanity.internal.AbstractDispatcher
import sanity.internal.SubscriberImpl

class RemoteBus(private val endpoint: SanityEndpoint) : AbstractDispatcher(), EventBus {

    private var es: EventSource? = null
    override fun subscribe(topic: String, callback: (Event) -> Unit): Subscriber {
        val sub = SubscriberImpl(topic, callback, subscribers)
        subscribers.add(sub)
        if (es == null) beginSubscription()
        return sub
    }

    private fun beginSubscription() {
        es = EventSource(endpoint.events())
        es?.on("message") { dispatch(it.id ?: "*", it.data) }
    }
}