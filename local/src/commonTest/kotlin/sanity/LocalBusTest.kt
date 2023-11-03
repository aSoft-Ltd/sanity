package sanity

import kommander.expect
import kotlin.test.Test

class LocalBusTest {

    private val bus: EventBus = LocalBus()

    @Test
    fun should_be_able_to_subscribe_to_any_event() {
        var eventFired = false
        val subscriber = bus.subscribe("*") {
            eventFired = true
        }
        bus.dispatch("test/123")
        subscriber.unsubscribe()
        expect(eventFired).toBe(true)
    }

    @Test
    fun should_be_able_to_subscribe() {
        var eventFired = false
        val subscriber = bus.subscribe("test") {
            eventFired = true
        }
        bus.dispatch("test")
        subscriber.unsubscribe()
        expect(eventFired).toBe(true)
    }

    @Test
    fun should_be_able_to_unsubscribe() {
        var topic = 0
        val subscriber = bus.subscribe("topic") {
            topic += 1
        }
        bus.dispatch("topic")
        expect(topic).toBe(1)
        subscriber.unsubscribe()
        bus.dispatch("topic")
        expect(topic).toBe(1)
    }

    @Test
    fun should_not_fire_events_when_they_are_not_subscribe() {
        var topic1 = 0
        var topic2 = 0
        val sub1 = bus.subscribe("topic/1") {
            topic1 += 1
        }

        val sub2 = bus.subscribe("topic/2") {
            topic2 += 1
        }
        bus.dispatch("topic/1")
        expect(topic1).toBe(1)
        expect(topic2).toBe(0)

        bus.dispatch("topic/2")
        expect(topic1).toBe(1)
        expect(topic2).toBe(1)

        sub1.unsubscribe()
        sub2.unsubscribe()
    }

    @Test
    fun should_subscribe_to_matched_topics() {
        var topics = 0

        val sub1 = bus.subscribe("topic/*") {
            topics += 1
        }

        bus.dispatch("topic/1")
        expect(topics).toBe(1)
        bus.dispatch("topic/2")
        expect(topics).toBe(2)
        sub1.unsubscribe()
    }

    @Test
    fun should_subscribe_to_a_specific_topic_pattern() {
        var topics = 0

        val sub1 = bus.subscribe("topic/added/*") {
            topics += 1
        }

        bus.dispatch("topic/added/1")
        expect(topics).toBe(1)
        bus.dispatch("topic/deleted/2")
        expect(topics).toBe(1)
        sub1.unsubscribe()
    }

    @Test
    fun can_get_published_data() {
        var topics = 0

        val sub1 = bus.subscribe("topic/added/*") {
            val data = it.data as Int
            topics = data
        }

        bus.dispatch("topic/added/1", 10)
        sub1.unsubscribe()
        expect(topics).toBe(10)
    }
}