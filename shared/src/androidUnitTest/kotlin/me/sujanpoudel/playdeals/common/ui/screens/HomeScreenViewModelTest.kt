package me.sujanpoudel.playdeals.common.ui.screens

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import me.sujanpoudel.playdeals.common.domain.models.AppDeal
import me.sujanpoudel.playdeals.common.networking.Failure
import me.sujanpoudel.playdeals.common.networking.RemoteAPI
import me.sujanpoudel.playdeals.common.networking.Result
import me.sujanpoudel.playdeals.common.ui.screens.home.HomeScreenState
import me.sujanpoudel.playdeals.common.ui.screens.home.HomeScreenViewModel
import me.sujanpoudel.playdeals.common.utils.setMainDispatcher
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenViewModelTest {

  @MockK
  lateinit var remoteAPI: RemoteAPI


  @BeforeEach
  fun setup() {
    MockKAnnotations.init(this)
  }


  @Test
  fun `should have correct initial state`() = runTest {
    val state = HomeScreenState()
    state.allAppDeals shouldHaveSize 0
    state.isLoading shouldBe false
    state.isRefreshing shouldBe false
  }


  @Test
  fun `should call to get the deals when view model is created`() = runTest {

    setMainDispatcher(UnconfinedTestDispatcher())

    coEvery { remoteAPI.getDeals() } returns Result.success(emptyList())

    HomeScreenViewModel(remoteAPI)

    coVerify { remoteAPI.getDeals() }
  }


  @Test
  fun `should correctly update state before calling the remoteAPI_getDeals`(): Unit = runTest {

    val dispatcher = StandardTestDispatcher()

    setMainDispatcher(dispatcher)

    coEvery { remoteAPI.getDeals() } returns Result.success(emptyList())

    val viewModel = HomeScreenViewModel(remoteAPI)

    viewModel.state.value.let {
      it.allAppDeals shouldHaveSize 0
      it.isLoading shouldBe true
      it.isRefreshing shouldBe false
    }
  }

  @Test
  fun `should correctly update state after getting failure result from remoteAPI`(): Unit = runTest {

    val dispatcher = StandardTestDispatcher()

    setMainDispatcher(dispatcher)

    coEvery { remoteAPI.getDeals() } returns Result.failure(Failure.UnknownError)

    val viewModel = HomeScreenViewModel(remoteAPI)

    dispatcher.scheduler.runCurrent()

    viewModel.state.value.also { state ->
      state.allAppDeals shouldHaveSize 0
      state.isLoading shouldBe false
      state.isRefreshing shouldBe false
      state.persistentError shouldBe Failure.UnknownError.message
    }
  }

  @Test
  fun `should correctly update state after getting success result from remoteAPI`(): Unit = runTest {

    val dispatcher = StandardTestDispatcher()

    val deal = mockk<AppDeal>()

    every { deal.category } returns ""

    setMainDispatcher(dispatcher)

    coEvery { remoteAPI.getDeals() } returns Result.success(listOf(deal))

    val viewModel = HomeScreenViewModel(remoteAPI)

    dispatcher.scheduler.runCurrent()

    viewModel.state.value.also { state ->
      state.allAppDeals shouldContainExactly listOf(deal)
      state.isLoading shouldBe false
      state.isRefreshing shouldBe false
      state.persistentError shouldBe null
    }
  }


  @Test
  fun `it should reset the error message when retrying`(): Unit = runTest {

    val dispatcher = StandardTestDispatcher()

    val deal = mockk<AppDeal>()

    every { deal.category } returns ""

    setMainDispatcher(dispatcher)

    coEvery { remoteAPI.getDeals() } returns
      Result.failure(Failure.UnknownError) andThen
      Result.success(listOf(deal))

    val viewModel = HomeScreenViewModel(remoteAPI)

    dispatcher.scheduler.advanceUntilIdle()

    viewModel.refreshDeals()

    viewModel.state.value.also { state ->
      state.allAppDeals shouldHaveSize 0
      state.isLoading shouldBe true
      state.isRefreshing shouldBe false
      state.persistentError shouldBe null
    }

    dispatcher.scheduler.advanceUntilIdle()

    viewModel.state.value.also { state ->
      state.allAppDeals shouldContainExactly listOf(deal)
      state.isLoading shouldBe false
      state.isRefreshing shouldBe false
      state.persistentError shouldBe null
    }

  }

  @Test
  fun `it should correctly represent refresh in state`(): Unit = runTest {

    val dispatcher = StandardTestDispatcher()

    val deal = mockk<AppDeal>()

    every { deal.category } returns ""

    setMainDispatcher(dispatcher)

    coEvery { remoteAPI.getDeals() } returns
      Result.success(listOf(deal)) andThen
      Result.success(listOf(deal))

    val viewModel = HomeScreenViewModel(remoteAPI)

    dispatcher.scheduler.advanceUntilIdle()

    viewModel.refreshDeals()

    viewModel.state.value.also { state ->
      state.allAppDeals shouldContainExactly listOf(deal)
      state.isLoading shouldBe false
      state.isRefreshing shouldBe true
      state.persistentError shouldBe null
    }

    dispatcher.scheduler.advanceUntilIdle()

    viewModel.state.value.also { state ->
      state.allAppDeals shouldContainExactly listOf(deal)
      state.isLoading shouldBe false
      state.isRefreshing shouldBe false
      state.persistentError shouldBe null
    }

  }


  @Test
  fun `it should replace the app deals on refresh`(): Unit = runTest {

    val dispatcher = StandardTestDispatcher()

    val deal = mockk<AppDeal>()
    val deal2 = mockk<AppDeal>()

    every { deal.category } returns ""
    every { deal2.category } returns ""


    setMainDispatcher(dispatcher)

    coEvery { remoteAPI.getDeals() } returns
      Result.success(listOf(deal)) andThen
      Result.success(listOf(deal,deal2))

    val viewModel = HomeScreenViewModel(remoteAPI)

    dispatcher.scheduler.advanceUntilIdle()

    viewModel.refreshDeals()

    dispatcher.scheduler.advanceUntilIdle()

    viewModel.state.value.also { state ->
      state.allAppDeals shouldContainExactly listOf(deal, deal2)
      state.isLoading shouldBe false
      state.isRefreshing shouldBe false
      state.persistentError shouldBe null
    }

  }

}
