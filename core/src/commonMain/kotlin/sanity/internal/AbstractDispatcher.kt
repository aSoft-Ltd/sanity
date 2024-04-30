package sanity.internal

import kiota.Url
import sanity.Event
import sanity.EventDispatcher
import sanity.Subscriber

abstract class AbstractDispatcher : EventDispatcher {

    protected val subscribers = mutableListOf<Subscriber?>()

    override fun dispatch(topic: String) = dispatch(topic, null)

    override fun dispatch(topic: String, data: Any?) {
        // Sometimes subscribers are being collected harshly by the garbage collector.
        // So, filter out those that have already being collected and we are still holing their references

        val (active, inactive) = subscribers.partition { it != null }
        active.filterIsInstance<Subscriber>().forEach { sub ->
            val match = Url(topic).matches(sub.pattern)
            if (match != null) sub.invoke(Event(topic, match, data))
        }

        subscribers -= inactive
    }
}