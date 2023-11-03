package sanity.internal

import sanity.Subscriber
import sanity.Event

class SubscriberImpl(
    val pattern: String,
    private val callback: (Event) -> Unit,
    private val container: MutableList<Subscriber>
) : Subscriber {
    operator fun invoke(event: Event) = callback(event)

    override fun unsubscribe() {
        container.remove(this)
    }
}