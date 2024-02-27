package sanity

import kiota.UrlMatch

class Event(
    val topic: String,
    val match: UrlMatch,
    val data: Any? = null
)