package sanity

interface Event {
    val topic: String
    val data: Any?
}