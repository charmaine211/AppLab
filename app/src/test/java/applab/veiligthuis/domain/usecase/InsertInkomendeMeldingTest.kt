package applab.veiligthuis.domain.usecase

import applab.veiligthuis.data.repository.TestMeldingRepository
import applab.veiligthuis.domain.model.melding.AfgeslotenMelding
import applab.veiligthuis.domain.model.melding.InkomendeMelding
import applab.veiligthuis.domain.model.melding.Melding
import applab.veiligthuis.domain.model.melding.MeldingStatus
import applab.veiligthuis.domain.util.MeldingType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class InsertInkomendeMeldingTest {

    private lateinit var insertInkomendeMelding: InsertInkomendeMelding
    private lateinit var testMeldingRepository: TestMeldingRepository

    @Before
    fun setUp() {
        testMeldingRepository = TestMeldingRepository()
        insertInkomendeMelding = InsertInkomendeMelding(testMeldingRepository)
    }

    @Test
    fun `InsertInkomendeMelding - succesful insert`() = runBlocking {
        val now = LocalDateTime.now()
        insertInkomendeMelding(
            datum = now,
            beschrijving = "Dit is een beschrijving",
            plaatsNaam = "Plaats",
            typeGeweld = "Ongecategoriseerd",
            beroepsmatig = false,
        )

        val meldingen = testMeldingRepository.getMeldingen(MeldingType.Inkomend).first()

        assertEquals(meldingen.size, 1)
    }
}