package applab.veiligthuis.domain.usecase

import applab.veiligthuis.data.repository.TestMeldingRepository
import applab.veiligthuis.domain.model.melding.AfgeslotenMelding
import applab.veiligthuis.domain.model.melding.InkomendeMelding
import applab.veiligthuis.domain.model.melding.Melding
import applab.veiligthuis.domain.model.melding.MeldingStatus
import applab.veiligthuis.domain.util.MeldingType
import applab.veiligthuis.data.melding.MeldingNotFoundException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class GetMeldingTest {

    private lateinit var getMelding: GetMelding
    private lateinit var testMeldingRepository: TestMeldingRepository

    @Before
    fun setUp() {
        testMeldingRepository = TestMeldingRepository()
        getMelding = GetMelding(testMeldingRepository)

        val testMeldingen = mutableListOf<Melding>()
        ('a'..'m').forEachIndexed { index, c ->
            testMeldingen.add(InkomendeMelding(
                datum = index.toLong(),
                status = MeldingStatus.ONBEHANDELD,
                beschrijving = "test $c",
                plaatsNaam = "plaats $c",
                key = c.toString(),
                typeGeweld = "geweld $c",
                beroepsmatig = false
            ))
        }
        ('n'..'z').forEachIndexed { index, c ->
            testMeldingen.add(AfgeslotenMelding(
                datum = index.toLong(),
                status = MeldingStatus.AFGESLOTEN,
                beschrijving = "test $c",
                plaatsNaam = "plaats $c",
                key = c.toString(),
                typeGeweld = "geweld $c",
                beroepsmatig = false
            ))
        }
        testMeldingen.shuffle()
        testMeldingen.forEach { testMeldingRepository.insertOrUpdateMelding(it) }
    }

    @Test
    fun `Getmelding - existing key`() = runBlocking {
        val meldingKey = "b"
        val melding = getMelding(meldingKey, MeldingType.Inkomend).first()
        assertEquals(melding.key, meldingKey)
    }

    @Test
    fun `GetMelding - correct subtype of melding`() = runBlocking {
        val meldingKey1 = "a"
        val meldingKey2 = "q"
        val inkomendeMelding = getMelding(meldingKey1, MeldingType.Inkomend).first()
        assertEquals(inkomendeMelding::class.java ,InkomendeMelding::class.java)
        val afgeslotenMelding = getMelding(meldingKey2, MeldingType.Afgesloten).first()
        assertEquals(afgeslotenMelding::class.java, AfgeslotenMelding::class.java)
    }

    @Test(expected = MeldingNotFoundException::class)
    fun `GetMelding - non existing key`() = runBlocking {
        val meldingKey = "abcdef"
        val melding = getMelding(meldingKey, MeldingType.Inkomend).first()
    }


}