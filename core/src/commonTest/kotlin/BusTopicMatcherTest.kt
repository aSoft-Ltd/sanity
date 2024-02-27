import sanity.EventBus
import kotlin.test.Test
import sanity.LocalBus
import kommander.expect

class BusTopicMatcherTest {


    val bus: EventBus = LocalBus()

    @Test
    fun should_be_able_to_match_the_params_passed() {
        var collected: String? = null
        bus.subscribe("/test/{uid}") {
            val uid by it.match
            collected = uid
        }
        bus.dispatch("/test/123")
        expect(collected).toBe("123")
    }

    @Test
    fun should_be_able_to_match_multiple_params_passed() {
        var id: String? = null
        var m: String? = null
        bus.subscribe("/test/{method}/{uid}") {
            val uid by it.match
            val method by it.match
            id = uid
            m = method
        }
        bus.dispatch("/test/email/123")
        expect(id).toBe("123")
        expect(m).toBe("email")
    }
}