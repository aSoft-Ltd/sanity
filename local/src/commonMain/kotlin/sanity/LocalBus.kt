package sanity

import sanity.internal.SubscriberImpl

class LocalBus : EventBus {

    private val subscribers = mutableListOf<Subscriber>()
    override fun dispatch(topic: String) = dispatch(topic, null)

    override fun dispatch(topic: String, data: Any?) {
        val event = Event(topic, data)
        subscribers.forEach {
            val sub = it as SubscriberImpl
            val matched = topic.matchesPattern(it.pattern)
            if (matched) sub.invoke(event)
        }
    }

    private fun String.matchesPattern(pattern: String): Boolean {
        if (pattern == "*") return true
        if (this == pattern) return true
        val concreteSegments = split("/")
        val patternSegments = pattern.split("/")
        if (concreteSegments.size == patternSegments.size) {
            val matches = Array(concreteSegments.size) { false }
            for (i in concreteSegments.indices) {
                val c = concreteSegments[i]
                val p = patternSegments[i]
                matches[i] = p == "*" || p == c
            }
            return matches.all { it }
        }
        return false
    }

    override fun subscribe(topic: String, callback: (Event) -> Unit): Subscriber {
        val sub = SubscriberImpl(topic, callback, subscribers)
        subscribers.add(sub)
        return sub
    }
}