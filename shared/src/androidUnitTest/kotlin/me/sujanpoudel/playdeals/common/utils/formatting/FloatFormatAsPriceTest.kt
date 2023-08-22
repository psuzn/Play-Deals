package me.sujanpoudel.playdeals.common.utils.formatting

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import me.sujanpoudel.playdeals.common.utils.formatAsPrice
import org.junit.jupiter.api.Test

class FloatFormatAsPriceTest {

  @Test
  fun `should correctly format`() = runTest {
    2.001f.formatAsPrice() shouldBe "2.00"
    2.11111f.formatAsPrice() shouldBe "2.11"
  }

  @Test
  fun `should add zeros after decimals`() = runTest {
    2f.formatAsPrice() shouldBe "2.00"
    2.1f.formatAsPrice() shouldBe "2.10"
  }

  @Test
  fun `should round decimals place values`() = runTest {
    2.2299f.formatAsPrice() shouldBe "2.23"
  }
}
