package applab.veiligthuis.domain.usecase

import applab.veiligthuis.data.repository.TestMeldingRepository
import applab.veiligthuis.domain.model.melding.AfgeslotenMelding
import applab.veiligthuis.domain.model.melding.InkomendeMelding
import applab.veiligthuis.domain.model.melding.Melding
import applab.veiligthuis.domain.model.melding.MeldingStatus
import applab.veiligthuis.domain.util.MeldingType
import applab.veiligthuis.data.melding.MeldingPaths
import applab.veiligthuis.data.melding.MeldingRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class EditMeldingTest {
    private lateinit var editMelding: EditMelding
    private lateinit var testMeldingRepository: MeldingRepository

    @Before
    fun setUp() {
        testMeldingRepository = TestMeldingRepository()
        editMelding = EditMelding(testMeldingRepository)

        val testMeldingen = mutableListOf<Melding>()
        ('a'..'c').forEachIndexed { index, c ->
            testMeldingen.add(
                InkomendeMelding(
                    datum = index.toLong(),
                    status = MeldingStatus.ONBEHANDELD,
                    beschrijving = "test $c",
                    plaatsNaam = "plaats $c",
                    key = c.toString(),
                    typeGeweld = "geweld $c",
                    beroepsmatig = false
                )
            )
        }
        ('d'..'f').forEachIndexed { index, c ->
            testMeldingen.add(
                AfgeslotenMelding(
                    datum = index.toLong(),
                    status = MeldingStatus.AFGESLOTEN,
                    beschrijving = "test $c",
                    plaatsNaam = "plaats $c",
                    key = c.toString(),
                    typeGeweld = "geweld $c",
                    beroepsmatig = false
                )
            )
        }
        testMeldingen.shuffle()
        testMeldingen.forEach { testMeldingRepository.insertOrUpdateMelding(it) }
    }

    @Test
    fun `UpdateMelding - change typegeweld`() = runBlocking {
        val meldingKey = "a"
        val melding = testMeldingRepository.getMelding(meldingKey, MeldingType.Inkomend).first()
        assertEquals("geweld a", melding.typeGeweld)

        editMelding(melding, melding.status, "Testwaarde")
        val meldingEdited =
            testMeldingRepository.getMelding(meldingKey, MeldingType.Inkomend).first()
        assertNotEquals(meldingEdited.typeGeweld, melding.typeGeweld)
    }

    @Test
    fun `UpdateMelding - change status onbehandeld in behandeling`() = runBlocking {
        val meldingKey = "b"
        val melding = testMeldingRepository.getMelding(meldingKey, MeldingType.Inkomend).first()
        assertEquals(MeldingStatus.ONBEHANDELD, melding.status)

        editMelding(melding, MeldingStatus.IN_BEHANDELING, melding.typeGeweld)
        val meldingNewStatus =
            testMeldingRepository.getMelding(meldingKey, MeldingType.Inkomend).first()
        assertEquals(MeldingStatus.IN_BEHANDELING, meldingNewStatus.status)
    }

    @Test
    fun `UpdateMelding - change status to afgesloten`() = runBlocking {
        val meldingKey = "c"
        val melding = testMeldingRepository.getMelding(meldingKey, MeldingType.Inkomend).first()
        assertEquals(MeldingStatus.ONBEHANDELD, melding.status)

        editMelding(melding, MeldingStatus.AFGESLOTEN, melding.typeGeweld)
        val meldingAfgeslotenStatus =
            testMeldingRepository.getMelding(meldingKey, MeldingType.Afgesloten).first()
        assertEquals(MeldingStatus.AFGESLOTEN, meldingAfgeslotenStatus.status)
    }

    @Test
    fun `UpdateMelding - check if path gets updated on status change from onbehandeld in behandeling to afgesloten`() =
        runBlocking {
            val meldingKey = "a"
            val melding = testMeldingRepository.getMelding(meldingKey, MeldingType.Inkomend).first()

            var meldingenAfgesloten = testMeldingRepository.getMeldingen(
                MeldingType.Afgesloten
            ).first()
            var meldingenInkomend = testMeldingRepository.getMeldingen(
                MeldingType.Inkomend
            ).first()

            assertTrue(meldingenInkomend.contains(melding))
            assertFalse(meldingenAfgesloten.contains(melding))
            assertEquals(3, meldingenAfgesloten.size)
            assertEquals(3, meldingenInkomend.size)

            editMelding(melding, MeldingStatus.AFGESLOTEN, melding.typeGeweld)
            val meldingAfgesloten =
                testMeldingRepository.getMelding(meldingKey, MeldingType.Afgesloten).first()

            meldingenAfgesloten = testMeldingRepository.getMeldingen(
                MeldingType.Afgesloten
            ).first()
            meldingenInkomend = testMeldingRepository.getMeldingen(
                MeldingType.Inkomend
            ).first()

            assertTrue(meldingenAfgesloten.contains(meldingAfgesloten))
            assertFalse(meldingenInkomend.contains(melding))
            assertEquals(4, meldingenAfgesloten.size)
            assertEquals(2, meldingenInkomend.size)
        }

    @Test
    fun `UpdateMelding - non existing melding`() {

    }


}