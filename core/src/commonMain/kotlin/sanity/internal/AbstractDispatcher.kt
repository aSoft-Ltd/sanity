package sanity.internal

import kiota.Url
import sanity.Event
import sanity.EventDispatcher
import sanity.Subscriber

abstract class AbstractDispatcher : EventDispatcher {

    protected val subscribers = mutableListOf<Subscriber>()
    override fun dispatch(topic: String) = dispatch(topic, null)
    override fun dispatch(topic: String, data: Any?) {
        subscribers.forEach { sub ->
            val match = Url(topic).matches(sub.pattern)
            if (match != null) sub.invoke(Event(topic, match, data))
        }
    }
}