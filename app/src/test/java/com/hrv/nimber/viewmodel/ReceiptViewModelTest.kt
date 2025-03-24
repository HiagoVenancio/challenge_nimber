package com.hrv.nimber.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hrv.nimber.repository.FakeReceiptRepository
import com.hrv.nimber.presentation.viewmodel.ReceiptViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ReceiptViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var fakeRepository: FakeReceiptRepository
    private lateinit var viewModel: ReceiptViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeReceiptRepository()
        viewModel = ReceiptViewModel(fakeRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `addReceipt adds a new receipt successfully`() = runTest {
        assertTrue(viewModel.receipts.value.isEmpty())
        viewModel.addReceipt("2023-03-12", 100.0f, listOf("photo1.jpg"))
        advanceUntilIdle()
        var receipts = viewModel.receipts.value
        assertEquals(1, receipts.size)
        val receipt = receipts.first()
        assertEquals("2023-03-12", receipt.date)
        assertEquals(100.0f, receipt.amount)
        assertEquals(listOf("photo1.jpg"), receipt.photoPath)
    }

    @Test
    fun `addReceipt adds multiple receipts successfully`() = runTest {
        // Test Case 2: Add two receipts.
        viewModel.addReceipt("2023-03-22", 100.0f, listOf("photo1.jpg"))
        viewModel.addReceipt("2023-03-23", 200.0f, listOf("photo2.jpg", "photo3.jpg"))
        advanceUntilIdle()

        val receipts = viewModel.receipts.value
        assertEquals(2, receipts.size)
        val receipt1 = receipts[0]
        val receipt2 = receipts[1]

        assertEquals("2023-03-22", receipt1.date)
        assertEquals(100.0f, receipt1.amount)
        assertEquals(listOf("photo1.jpg"), receipt1.photoPath)

        assertEquals("2023-03-23", receipt2.date)
        assertEquals(200.0f, receipt2.amount)
        assertEquals(listOf("photo2.jpg", "photo3.jpg"), receipt2.photoPath)
    }

    @Test
    fun `delete receipt successfully`() = runTest {
        viewModel.addReceipt("2023-03-22", 100.0f, listOf("photo1.jpg"))
        viewModel.addReceipt("2023-03-23", 200.0f, listOf("photo2.jpg", "photo3.jpg"))
        advanceUntilIdle()

        val receipts = viewModel.receipts.value
        assertEquals(2, receipts.size)

        viewModel.deleteReceipt(1)
        advanceUntilIdle()

        assertEquals(1, viewModel.receipts.value.size)
    }
}
