package me.sujanpoudel.playdeals.common.utils.formatting

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import me.sujanpoudel.playdeals.common.utils.shallowFormatted
import org.junit.jupiter.api.Test
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class DurationShallowFormatTest {

  @Test
  fun `should format correctly for duration with days`() = runTest {
    2.days.shallowFormatted() shouldBe "2 day(s)"
  }

  @Test
  fun `should format correctly for duration with hours`() = runTest {
    2.hours.shallowFormatted() shouldBe "2 hour(s)"
  }

  @Test
  fun `should format correctly for duration with minutes`() = runTest {
    2.minutes.shallowFormatted() shouldBe "2 minute(s)"
  }

  @Test
  fun `should format correctly for duration less then minutes`() = runTest {
    2.seconds.shallowFormatted() shouldBe "a moment"
  }

  @Test
  fun `should format correctly with days as biggest unit`() = runTest {
    20000.hours.shallowFormatted() shouldBe "${(20000 / 24)} day(s)"
  }

  @Test
  fun `should format correctly with biggest unit`() = runTest {
    125.minutes.shallowFormatted() shouldBe "2 hour(s)"
  }
}
