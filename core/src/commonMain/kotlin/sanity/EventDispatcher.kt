package sanity

interface EventDispatcher {
    fun dispatch(topic: String)

    fun dispatch(topic: String, data: Any?)
}