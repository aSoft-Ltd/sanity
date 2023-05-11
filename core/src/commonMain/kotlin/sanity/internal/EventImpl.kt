package sanity.internal

import sanity.Event

class EventImpl(
    override val topic: String,
    override val data: Any?
) : Event