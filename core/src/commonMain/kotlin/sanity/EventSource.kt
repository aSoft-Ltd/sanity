package sanity

interface EventSource {
    fun subscribe(topic: String, callback: (Event) -> Unit): Subscriber
}