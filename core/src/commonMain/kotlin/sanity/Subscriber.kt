package sanity

class Subscriber(
    val pattern: String,
    private val callback: (Event) -> Unit,
    private val container: MutableList<Subscriber>
) {
    operator fun invoke(event: Event) = callback(event)

    fun unsubscribe() {
        container.remove(this)
    }
}