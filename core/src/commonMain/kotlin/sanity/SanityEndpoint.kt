package sanity

class SanityEndpoint(private val base: String) {
    fun events() = "$base/events"
}