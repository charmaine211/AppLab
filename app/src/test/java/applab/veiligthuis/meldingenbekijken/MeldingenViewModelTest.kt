package applab.veiligthuis.meldingenbekijken

import applab.veiligthuis.model.Melding
import applab.veiligthuis.model.MeldingStatus
import applab.veiligthuis.repository.melding.MeldingLijstRepository
import applab.veiligthuis.viewmodel.MeldingLijstViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before

import org.junit.Test


class MeldingenViewModelTest {

    class MockMeldingLijstRepo : MeldingLijstRepository {
        override fun getMeldingen(): Flow<List<Melding?>> {
            var data = arrayListOf<Melding>()
            data.add(Melding("1-1-1111", "TestLocatie", "Info Info Info", MeldingStatus.ONBEHANDELD, true))

            return flow {
                emit(data)
            }
        }
    }

    private val mainThreadSurrogate = newSingleThreadContext("TestThread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }




    private val viewModel = MeldingLijstViewModel(meldingLijstRepository = MockMeldingLijstRepo())

    @Test
    fun testViewModelData() = runTest {
        viewModel.loadMeldingen()
        assertEquals(viewModel.uiState.value.meldingen.size, 1)
    }


}