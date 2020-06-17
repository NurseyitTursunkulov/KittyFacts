package com.example.kittyfacts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.FactItemModel
import com.example.domain.GetFactsUseCase
import com.example.kittyfacts.factList.FactsViewModel
import com.example.testrussia.LiveDataTestUtil
import com.example.testrussia.MainCoroutineRule
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Before
import org.junit.Rule

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class ExampleUnitTest {
    //TODO RENAME!!
    private lateinit var newsViewModel: FactsViewModel
    private lateinit var fakeUseCase: GetFactsUseCase
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        // We initialise the tasks to 3, with one active and two completed
            fakeUseCase = FakeGetFactsUseCase()
        val newsModel = FactItemModel(factDescription = ("Title1"),factNumber = 1)
        val newsModel1 = FactItemModel(factDescription = ("Title1"),factNumber = 2)
        val newsModel2 = FactItemModel(factDescription = ("Title1"),factNumber = 3)
        val newsModel3 = FactItemModel(factDescription = ("Title1"),factNumber = 4)

        newsViewModel = FactsViewModel(
            fakeUseCase
        )
        (fakeUseCase as FakeGetFactsUseCase).addNews(newsModel, newsModel1, newsModel2, newsModel3)
    }

    @Test
    fun `newsViewModel loadFacts() loading Toggles And DataLoaded`() = runBlocking<Unit> {
        // Pause dispatcher so we can verify initial values
        mainCoroutineRule.pauseDispatcher()


        // Trigger loading of tasks
        newsViewModel.loadFacts()

        // Then progress indicator is shown
        Truth.assertThat(LiveDataTestUtil.getValue(newsViewModel.dataLoading)).isTrue()

        // Execute pending coroutines actions
        mainCoroutineRule.resumeDispatcher()

        // Then progress indicator is hidden
        Truth.assertThat(LiveDataTestUtil.getValue(newsViewModel.dataLoading)).isFalse()

        // And data correctly loaded
        Truth.assertThat(LiveDataTestUtil.getValue(newsViewModel.items)).hasSize(4)
    }

}