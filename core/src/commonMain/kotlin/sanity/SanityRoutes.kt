package sanity

class SanityRoutes(private val base: String) {
    fun events() = "$base/events"
}