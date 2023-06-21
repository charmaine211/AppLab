package applab.veiligthuis.domain.util

import applab.veiligthuis.domain.model.melding.AfgeslotenMelding
import applab.veiligthuis.domain.model.melding.InkomendeMelding
import applab.veiligthuis.domain.model.melding.Melding

sealed class MeldingType(val classType: Class<out Melding>, val value: String) {
    object Inkomend: MeldingType(InkomendeMelding::class.java, "inkomend")
    object Afgesloten: MeldingType(AfgeslotenMelding::class.java, "afgesloten")
}
