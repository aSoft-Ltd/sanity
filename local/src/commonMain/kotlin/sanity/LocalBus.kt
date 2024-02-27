package sanity

import sanity.internal.AbstractDispatcher

class LocalBus : AbstractDispatcher(), EventBus {
    override fun subscribe(topic: String, callback: (Event) -> Unit): sanity.Subscriber {
        val sub = Subscriber(topic, callback, subscribers)
        subscribers.add(sub)
        return sub
    }
}