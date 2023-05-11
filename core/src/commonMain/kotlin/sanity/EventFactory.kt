package sanity

import sanity.internal.EventImpl

fun Event(
    topic: String,
    data: Any? = null
): Event = EventImpl(topic, data)