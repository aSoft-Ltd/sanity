package sanity.internal

import kiota.Url
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import sanity.Event
import sanity.EventDispatcher
import sanity.Subscriber

abstract class AbstractDispatcher : EventDispatcher {

    protected val subscribers = mutableListOf<Subscriber>()
    override fun dispatch(topic: String) = dispatch(topic, null)
    override fun dispatch(topic: String, data: Any?) {
        val event = Event(topic, data)
        subscribers.forEach {
            val sub = it as SubscriberImpl
            val matched = Url(topic).matches(it.pattern) != null
            if (matched) sub.invoke(event)
        }
    }
}