package com.konopelko.foodtestapp.presentation.home

import app.cash.turbine.test
import com.konopelko.foodtestapp.data.api.model.getfood.Food
import com.konopelko.foodtestapp.data.utils.Result
import com.konopelko.foodtestapp.domain.usecase.GetFoodUseCase
import com.konopelko.foodtestapp.presentation.home.HomeFragmentEvent.ShowLoadingError
import com.konopelko.foodtestapp.presentation.home.HomeIntent.SearchInputChanged
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.lang.Exception
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
class HomeViewModelTest {

    private lateinit var objectUnderTest: HomeViewModel

    @RelaxedMockK
    private lateinit var getFoodUseCase: GetFoodUseCase

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should populate food list when loading food is successful`() = runTest {
        // Given
        val expectedFoodList: List<Food> = listOf(Food(0, "", "", 0, 0))
        coEvery { getFoodUseCase(any()) } returns Result.Success(expectedFoodList)
        createObjectUnderTest()

        // When
        objectUnderTest.acceptIntent(SearchInputChanged("someNewInput"))

        // Then
        coVerify { getFoodUseCase(any()) }
        objectUnderTest.uiState.test {
            assertEquals(
                expected = expectedFoodList,
                actual = awaitItem().food
            )
        }
    }

    @Test
    fun `should show loading error when loading food failed`() = runTest {
        // Given
        val errorMessage = "error message"
        coEvery { getFoodUseCase(any()) } returns Result.Error(Exception(errorMessage))
        createObjectUnderTest()

        // When
        objectUnderTest.acceptIntent(SearchInputChanged("someNewInput"))

        // Then
        coVerify { getFoodUseCase(any()) }
        objectUnderTest.fragmentEvent.test {
            assertEquals(
                expected = ShowLoadingError(errorMessage),
                actual = awaitItem()
            )
        }
    }

    private fun createObjectUnderTest(
        initialState: HomeUiState = HomeUiState()
    ) {
        objectUnderTest = HomeViewModel(
            initialState = initialState,
            getFoodUseCase = getFoodUseCase
        )
    }
}