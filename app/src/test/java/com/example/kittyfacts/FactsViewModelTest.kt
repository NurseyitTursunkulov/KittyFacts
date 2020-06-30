package com.example.kittyfacts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.FactItemModel
import com.example.data.Result
import com.example.domain.GetFactsUseCase
import com.example.testrussia.LiveDataTestUtil
import com.example.testrussia.MainCoroutineRule
import com.example.testrussia.assertLiveDataEventTriggered
import com.example.testrussia.assertSnackbarMessage
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import java.lang.Exception

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class FactsViewModelTest {
    private lateinit var factsViewModel: FactsViewModel
    private lateinit var fakeUseCase: GetFactsUseCase

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        fakeUseCase = FakeGetFactsUseCase()
        val newsModel = FactItemModel(factDescription = ("Title1"), factNumber = 1)
        val newsModel1 = FactItemModel(factDescription = ("Title1"), factNumber = 2)
        val newsModel2 = FactItemModel(factDescription = ("Title1"), factNumber = 3)
        val newsModel3 = FactItemModel(factDescription = ("Title1"), factNumber = 4)

        factsViewModel = FactsViewModel(
            fakeUseCase
        )
        (fakeUseCase as FakeGetFactsUseCase).addNews(newsModel, newsModel1, newsModel2, newsModel3)
    }

    @Test
    fun `factsViewModel loadFacts() loading Toggles And DataLoaded`() = runBlocking<Unit> {
        // Pause dispatcher so we can verify initial values
        mainCoroutineRule.pauseDispatcher()


        // Trigger loading of tasks
        factsViewModel.loadFacts()

        // Then progress indicator is shown
        Truth.assertThat(LiveDataTestUtil.getValue(factsViewModel.dataLoading)).isTrue()

        // Execute pending coroutines actions
        mainCoroutineRule.resumeDispatcher()

        // Then progress indicator is hidden
        Truth.assertThat(LiveDataTestUtil.getValue(factsViewModel.dataLoading)).isFalse()

        // And data correctly loaded
        Truth.assertThat(LiveDataTestUtil.getValue(factsViewModel.items)).hasSize(4)
    }

    @Test
    fun `click on open fact detail sets event`() {

        val newsModel = FactItemModel(factDescription = ("Title1"), factNumber = 1)
        factsViewModel.openDetails(newsModel)

        // Then the event is triggered
        assertLiveDataEventTriggered(factsViewModel.openDetailsEvent, newsModel)
    }

    @Test
    fun `loadFacts() with failure result shows snackBar message`() = runBlocking<Unit> {

        val exception = "just exception"
        (fakeUseCase as FakeGetFactsUseCase).result = Result.Error(Exception(exception))
        // Pause dispatcher so we can verify initial values
        mainCoroutineRule.pauseDispatcher()


        // Trigger loading of tasks
        factsViewModel.loadFacts()

        // Then progress indicator is shown
        Truth.assertThat(LiveDataTestUtil.getValue(factsViewModel.dataLoading)).isTrue()

        // Execute pending coroutines actions
        mainCoroutineRule.resumeDispatcher()

        // Then progress indicator is hidden
        Truth.assertThat(LiveDataTestUtil.getValue(factsViewModel.dataLoading)).isFalse()

        // And data correctly loaded
        assertSnackbarMessage(
            factsViewModel.snackbarText,
            "Error(exception=java.lang.Exception: $exception)"
        )
    }
}