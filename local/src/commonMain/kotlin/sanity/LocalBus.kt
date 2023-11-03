package sanity

import sanity.internal.AbstractDispatcher
import sanity.internal.SubscriberImpl

class LocalBus : AbstractDispatcher(), EventBus {
    override fun subscribe(topic: String, callback: (Event) -> Unit): Subscriber {
        val sub = SubscriberImpl(topic, callback, subscribers)
        subscribers.add(sub)
        return sub
    }
}