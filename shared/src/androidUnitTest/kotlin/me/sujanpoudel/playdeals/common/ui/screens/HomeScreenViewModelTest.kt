package me.sujanpoudel.playdeals.common.ui.screens

import com.russhwolf.settings.MapSettings
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import me.sujanpoudel.playdeals.common.domain.entities.DealEntity
import me.sujanpoudel.playdeals.common.domain.models.Failure
import me.sujanpoudel.playdeals.common.domain.models.Result
import me.sujanpoudel.playdeals.common.domain.persistent.AppPreferences
import me.sujanpoudel.playdeals.common.domain.repositories.DealsRepository
import me.sujanpoudel.playdeals.common.domain.repositories.ForexRepository
import me.sujanpoudel.playdeals.common.domain.repositories.defaultUsdConversion
import me.sujanpoudel.playdeals.common.ui.screens.home.HomeScreenState
import me.sujanpoudel.playdeals.common.ui.screens.home.HomeScreenViewModel
import me.sujanpoudel.playdeals.common.utils.setMainDispatcher
import me.sujanpoudel.playdeals.common.utils.settings.asObservableSettings
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

fun dealEntity(id: String = "1") = DealEntity(
  id = id,
  name = "name",
  icon = "icon",
  images = listOf(),
  normalPrice = 1.1f,
  currentPrice = 1.2f,
  currency = "$",
  url = "",
  category = "game",
  downloads = "150k",
  rating = 3.7f,
  offerExpiresIn = kotlinx.datetime.Clock.System.now(),
  type = "",
  source = "",
  createdAt = kotlinx.datetime.Clock.System.now(),
  updatedAt = kotlinx.datetime.Clock.System.now(),
)

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenViewModelTest {

  @MockK
  lateinit var dealsRepository: DealsRepository

  @MockK
  lateinit var forexRepository: ForexRepository

  @BeforeEach
  fun setup() {
    MockKAnnotations.init(this)
    coEvery { forexRepository.refreshRatesIfNecessary() } returns Result.success(Unit)
    coEvery { forexRepository.forexRatesFlow() } returns flowOf(emptyList())
    coEvery { forexRepository.forexUpdateAtFlow() } returns MutableStateFlow(null)
    coEvery { forexRepository.preferredConversionRateFlow() } returns MutableStateFlow(defaultUsdConversion())
  }

  private fun appPreference(): AppPreferences {
    return AppPreferences(MapSettings().asObservableSettings())
  }

  @Test
  fun `should have correct initial state`() =
    runTest {
      val state = HomeScreenState()
      state.allDeals shouldHaveSize 0
      state.isLoading shouldBe false
      state.isRefreshing shouldBe false
    }

  @Test
  fun `should call to get the deals when view model is created`() =
    runTest {
      setMainDispatcher(UnconfinedTestDispatcher())

      coEvery { dealsRepository.dealsFlow() } returns emptyFlow()
      coEvery { dealsRepository.refreshDeals() } returns Result.success(Unit)

      HomeScreenViewModel(appPreference(), dealsRepository, forexRepository)

      coVerify {
        dealsRepository.dealsFlow()
      }
    }

  @Test
  fun `should call to refresh the deals when view model is created`() = runTest {
    setMainDispatcher(UnconfinedTestDispatcher())

    coEvery { dealsRepository.dealsFlow() } returns emptyFlow()
    coEvery { dealsRepository.refreshDeals() } returns Result.success(Unit)

    HomeScreenViewModel(appPreference(), dealsRepository, forexRepository)

    coVerify { dealsRepository.refreshDeals() }
  }

  @Test
  fun `should correctly update state before calling the remoteAPI_getDeals`(): Unit = runTest {
    coEvery { dealsRepository.dealsFlow() } returns emptyFlow()
    coEvery { dealsRepository.refreshDeals() } returns Result.success(Unit)

    val viewModel = HomeScreenViewModel(appPreference(), dealsRepository, forexRepository)

    viewModel.state.value.let {
      it.allDeals shouldHaveSize 0
      it.isLoading shouldBe true
      it.isRefreshing shouldBe false
    }
  }

  @Test
  fun `should correctly update state after getting failure result from remoteAPI`(): Unit = runTest {
    val dispatcher = StandardTestDispatcher()

    setMainDispatcher(dispatcher)

    coEvery { dealsRepository.dealsFlow() } returns emptyFlow()
    coEvery { dealsRepository.refreshDeals() } returns Result.failure(Failure.UnknownError)

    val viewModel = HomeScreenViewModel(appPreference(), dealsRepository, forexRepository)

    dispatcher.scheduler.runCurrent()

    viewModel.state.value.also { state ->
      state.allDeals shouldHaveSize 0
      state.isLoading shouldBe false
      state.isRefreshing shouldBe false
      state.persistentError shouldBe Failure.UnknownError.message
    }
  }

  @Test
  fun `should correctly update state after getting success result from remoteAPI`(): Unit =
    runTest {
      val dispatcher = StandardTestDispatcher()

      setMainDispatcher(dispatcher)

      coEvery { dealsRepository.dealsFlow() } returns emptyFlow()
      coEvery { dealsRepository.refreshDeals() } returns Result.success(Unit)

      val viewModel = HomeScreenViewModel(appPreference(), dealsRepository, forexRepository)

      dispatcher.scheduler.runCurrent()

      viewModel.state.value.also { state ->
        state.allDeals shouldContainExactly emptyList()
        state.isLoading shouldBe false
        state.isRefreshing shouldBe false
        state.persistentError shouldBe null
      }
    }

  @Test
  fun `should correctly update state when flow emits deals`(): Unit =
    runTest {
      val dispatcher = StandardTestDispatcher()

      val entity = dealEntity("32")

      setMainDispatcher(dispatcher)

      coEvery { dealsRepository.dealsFlow() } returns flowOf(listOf(entity))
      coEvery { dealsRepository.refreshDeals() } returns Result.success(Unit)

      val viewModel = HomeScreenViewModel(appPreference(), dealsRepository, forexRepository)

      dispatcher.scheduler.runCurrent()

      viewModel.state.value.also { state ->
        state.allDeals.shouldContainExactly(entity)
        state.isLoading shouldBe false
        state.isRefreshing shouldBe false
        state.persistentError shouldBe null
      }
    }

  @Test
  fun `should reset the error message when retrying`(): Unit =
    runTest {
      val dispatcher = StandardTestDispatcher()

      setMainDispatcher(dispatcher)

      coEvery { dealsRepository.refreshDeals() } returns
        Result.failure(Failure.UnknownError) andThen
        Result.success(Unit)

      coEvery { dealsRepository.dealsFlow() } returns emptyFlow()

      val viewModel = HomeScreenViewModel(appPreference(), dealsRepository, forexRepository)

      viewModel.state.value.also { state ->
        state.allDeals shouldHaveSize 0
        state.isLoading shouldBe true
        state.isRefreshing shouldBe false
        state.persistentError shouldBe null
        state.errorOneOff shouldBe null
      }

      dispatcher.scheduler.advanceUntilIdle()

      viewModel.state.value.also { state ->
        state.allDeals shouldContainExactly emptyList()
        state.isLoading shouldBe false
        state.isRefreshing shouldBe false
        state.persistentError shouldBe Failure.UnknownError.message
        state.errorOneOff shouldBe null
      }

      viewModel.refreshDeals()

      viewModel.state.value.also { state ->
        state.allDeals shouldContainExactly emptyList()
        state.isLoading shouldBe true
        state.isRefreshing shouldBe false
        state.persistentError shouldBe null
        state.errorOneOff shouldBe null
      }

      dispatcher.scheduler.advanceUntilIdle()

      viewModel.state.value.also { state ->
        state.allDeals shouldContainExactly emptyList()
        state.isLoading shouldBe false
        state.isRefreshing shouldBe false
        state.persistentError shouldBe null
        state.errorOneOff shouldBe null
      }
    }

  @Test
  fun `should switch to one off error and loading if there is already cache`(): Unit =
    runTest {
      val dispatcher = StandardTestDispatcher()

      val entity = dealEntity("1")

      setMainDispatcher(dispatcher)

      coEvery { dealsRepository.refreshDeals() } returns
        Result.failure(Failure.UnknownError) andThen
        Result.success(Unit)

      coEvery { dealsRepository.dealsFlow() } returns flowOf(listOf(entity))

      val viewModel = HomeScreenViewModel(appPreference(), dealsRepository, forexRepository)

      viewModel.state.value.also { state ->
        state.allDeals shouldHaveSize 0
        state.isLoading shouldBe true
        state.isRefreshing shouldBe false
        state.persistentError shouldBe null
        state.errorOneOff shouldBe null
      }
      dispatcher.scheduler.advanceTimeBy(10000)
      dispatcher.scheduler.advanceUntilIdle()

      viewModel.state.value.also { state ->
        state.allDeals.shouldContainExactly(entity)
        state.isLoading shouldBe false
        state.isRefreshing shouldBe false
        state.persistentError shouldBe null
        state.errorOneOff shouldBe Failure.UnknownError.message
      }

      viewModel.refreshDeals()

      viewModel.state.value.also { state ->
        state.allDeals.shouldContainExactly(entity)
        state.isLoading shouldBe false
        state.isRefreshing shouldBe true
        state.persistentError shouldBe null
        state.errorOneOff shouldBe null
      }

      dispatcher.scheduler.advanceUntilIdle()

      viewModel.state.value.also { state ->
        state.allDeals.shouldContainExactly(entity)
        state.isLoading shouldBe false
        state.isRefreshing shouldBe false
        state.persistentError shouldBe null
        state.errorOneOff shouldBe null
      }
    }

  @Test
  fun `should replace the app deals when new value is emitted`(): Unit =
    runTest {
      val dispatcher = StandardTestDispatcher()
      setMainDispatcher(dispatcher)

      val deal1 = dealEntity("123")
      val deal2 = dealEntity("321")

      val flow = MutableStateFlow<List<DealEntity>>(emptyList())

      coEvery { dealsRepository.refreshDeals() } returns Result.success(Unit)
      coEvery { dealsRepository.dealsFlow() } returns flow

      val viewModel = HomeScreenViewModel(appPreference(), dealsRepository, forexRepository)

      dispatcher.scheduler.advanceUntilIdle()

      viewModel.state.value.also { state ->
        state.allDeals.shouldBeEmpty()
      }

      flow.emit(listOf(deal1, deal2))

      dispatcher.scheduler.advanceUntilIdle()
      dispatcher.scheduler.runCurrent()

      viewModel.state.value.also { state ->
        state.allDeals.shouldContainExactly(deal1, deal2)
      }
    }

  @Test
  fun `should correctly update the app deals when new value is emitted`(): Unit =
    runTest {
      val dispatcher = StandardTestDispatcher()
      setMainDispatcher(dispatcher)

      val deal1 = dealEntity()
      val deal2 = dealEntity("2")

      val flow = MutableStateFlow<List<DealEntity>>(emptyList())

      coEvery { dealsRepository.refreshDeals() } returns Result.failure(Failure.UnknownError)
      coEvery { dealsRepository.dealsFlow() } returns flow

      val viewModel = HomeScreenViewModel(appPreference(), dealsRepository, forexRepository)

      dispatcher.scheduler.advanceTimeBy(10000)
      dispatcher.scheduler.advanceUntilIdle()

      viewModel.state.value.also { state ->
        state.allDeals.shouldBeEmpty()
        state.persistentError shouldBe Failure.UnknownError.message
        state.errorOneOff shouldBe null
        state.isLoading shouldBe false
        state.isRefreshing shouldBe false
      }

      flow.emit(listOf(deal1, deal2))

      dispatcher.scheduler.advanceUntilIdle()

      viewModel.state.value.also { state ->
        state.allDeals.shouldContainExactly(deal1, deal2)
        state.persistentError shouldBe null
        state.errorOneOff shouldBe Failure.UnknownError.message
        state.isLoading shouldBe false
        state.isRefreshing shouldBe false
      }
    }
}
