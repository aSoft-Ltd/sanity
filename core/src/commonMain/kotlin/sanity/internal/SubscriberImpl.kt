package sanity.internal

import sanity.Subscriber
import sanity.Event

class SubscriberImpl(
    val pattern: String,
    val callback: (Event) -> Unit,
    val container: MutableList<Subscriber>
) : Subscriber {
    operator fun invoke(event: Event) = callback(event)

    override fun unsubscribe() {
        container.remove(this)
    }
}