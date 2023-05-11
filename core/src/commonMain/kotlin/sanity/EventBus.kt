package sanity

interface EventBus : EventSource {
    fun dispatch(topic: String)

    fun dispatch(topic: String, data: Any?)
}