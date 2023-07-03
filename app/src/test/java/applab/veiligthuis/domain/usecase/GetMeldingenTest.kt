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

class GetMeldingenTest {
    private lateinit var getMeldingen: GetMeldingen
    private lateinit var testMeldingRepository: TestMeldingRepository

    @Before
    fun setUp() {
        testMeldingRepository = TestMeldingRepository()
        getMeldingen = GetMeldingen(testMeldingRepository)

        val testMeldingen = mutableListOf<Melding>()
        // a - i 9
        ('a'..'i').forEachIndexed { index, c ->
            testMeldingen.add(
                InkomendeMelding(
                    datum = index.toLong(),
                    status = MeldingStatus.ONBEHANDELD,
                    beschrijving = "test $c",
                    plaatsNaam = "plaats $c",
                    key = c.toString(),
                    typeGeweld = "geweld $c",
                    beroepsmatig = index % 2 == 0
                )
            )
        }
        // j - r 9
        ('j'..'r').forEachIndexed { index, c ->
            testMeldingen.add(
                InkomendeMelding(
                    datum = index.toLong(),
                    status = MeldingStatus.IN_BEHANDELING,
                    beschrijving = "test $c",
                    plaatsNaam = "plaats $c",
                    key = c.toString(),
                    typeGeweld = "geweld $c",
                    beroepsmatig = index % 2 == 0
                )
            )
        }
        // s - z 8
        ('s'..'z').forEachIndexed { index, c ->
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
    fun `GetMeldingen - Inkomende meldingen`() = runBlocking {
        val meldingen = getMeldingen(meldingType = MeldingType.Inkomend).first()
        assertEquals(18, meldingen.size)
    }

    @Test
    fun `GetMeldingen - Afgesloten meldingen`() = runBlocking {
        val meldingen = getMeldingen(meldingType = MeldingType.Afgesloten).first()
        assertEquals(8, meldingen.size)
    }

    @Test
    fun `GetMeldingen - Inkomende meldingen filter status `() = runBlocking {
        val meldingenOnbehandeld = getMeldingen(meldingType = MeldingType.Inkomend, filterStatusPredicates = listOf { melding: Melding -> melding.status == MeldingStatus.ONBEHANDELD }).first()
        val meldingenInbehandeling = getMeldingen(meldingType = MeldingType.Inkomend, filterStatusPredicates = listOf { melding: Melding -> melding.status == MeldingStatus.IN_BEHANDELING }).first()
        val meldingenOnbehandeldInBehandling = getMeldingen(meldingType = MeldingType.Inkomend, filterStatusPredicates = listOf ({ melding: Melding -> melding.status == MeldingStatus.ONBEHANDELD }, { melding: Melding -> melding.status == MeldingStatus.IN_BEHANDELING })).first()
        val meldingenAfgesloten = getMeldingen(meldingType = MeldingType.Inkomend, filterStatusPredicates = listOf { melding: Melding -> melding.status == MeldingStatus.AFGESLOTEN }).first()
        assertEquals(9, meldingenOnbehandeld.size)
        assertEquals(9, meldingenInbehandeling.size)
        assertEquals(18, meldingenOnbehandeldInBehandling.size)
        assertEquals(0, meldingenAfgesloten.size)
    }

    @Test
    fun `GetMeldingen - Afgesloten meldingen filter status `() = runBlocking {
        val meldingenOnbehandeld = getMeldingen(meldingType = MeldingType.Afgesloten, filterStatusPredicates = listOf { melding: Melding -> melding.status == MeldingStatus.ONBEHANDELD }).first()
        val meldingenAfgesloten = getMeldingen(meldingType = MeldingType.Afgesloten, filterStatusPredicates = listOf { melding: Melding -> melding.status == MeldingStatus.AFGESLOTEN }).first()
        assertEquals(0, meldingenOnbehandeld.size)
        assertEquals(8, meldingenAfgesloten.size)
    }

    @Test
    fun `GetMeldingen - Inkomende meldingen filter beroepsmatig `() = runBlocking {
        val meldingenBeroepsmatig = getMeldingen(meldingType = MeldingType.Inkomend, filterBeroepsmatigPredicates = listOf { melding: Melding -> melding.beroepsmatig }).first()
        val meldingenNotBeroepsmatig = getMeldingen(meldingType = MeldingType.Inkomend, filterBeroepsmatigPredicates = listOf { melding: Melding -> !melding.beroepsmatig }).first()
        assertEquals(10, meldingenBeroepsmatig.size)
        assertEquals(8, meldingenNotBeroepsmatig.size)
    }

    @Test
    fun `GetMeldingen - Inkomende meldingen filter datum `() = runBlocking {
        val meldingenDatumAll = getMeldingen(meldingType = MeldingType.Inkomend, filterDatumPredicates = listOf { melding: Melding -> melding.datum!! >= 0 }).first()
        val meldingenDatumBetween = getMeldingen(meldingType = MeldingType.Inkomend, filterDatumPredicates = listOf({ melding: Melding -> melding.datum!! > 0 }, { melding: Melding -> melding.datum!! <= 1 }) ).first()
        assertEquals(18, meldingenDatumAll.size)
        assertEquals(2, meldingenDatumBetween.size)
    }

    @Test
    fun `GetMeldingen - Inkomende meldingen filter type geweld `() = runBlocking {
        val meldingenSingleType = getMeldingen(meldingType = MeldingType.Inkomend, filterSoortGeweldPredicates = listOf { melding: Melding -> melding.typeGeweld == "geweld a" }).first()
        val meldingenMultiple = getMeldingen(meldingType = MeldingType.Inkomend, filterSoortGeweldPredicates = listOf({ melding: Melding -> melding.typeGeweld == "geweld a" }, { melding: Melding -> melding.typeGeweld == "geweld j" }) ).first()
        assertEquals(1, meldingenSingleType.size)
        assertEquals(2, meldingenMultiple.size)
    }

    @Test
    fun `GetMeldingen - Inkomende meldingen multiple filters combined `() = runBlocking {
        val meldingen = getMeldingen(
            meldingType = MeldingType.Inkomend,
            filterBeroepsmatigPredicates = listOf { melding: Melding -> melding.beroepsmatig },
            filterStatusPredicates = listOf { melding: Melding -> melding.status == MeldingStatus.ONBEHANDELD }

        ).first()
        assertEquals(5, meldingen.size)
    }

    @Test
    fun `GetMeldingen - Inkomende meldingen filter plaats `() = runBlocking {
        val meldingen =
            getMeldingen(meldingType = MeldingType.Inkomend, plaatsen = listOf("plaats a")).first()
        assertEquals(1, meldingen.size)
    }





}