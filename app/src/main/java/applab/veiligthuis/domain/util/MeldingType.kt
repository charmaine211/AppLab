package applab.veiligthuis.domain.util

import applab.veiligthuis.domain.model.melding.AfgeslotenMelding
import applab.veiligthuis.domain.model.melding.InkomendeMelding
import applab.veiligthuis.domain.model.melding.Melding

sealed class MeldingType(val classType: Class<out Melding>) {
    object Inkomend: MeldingType(InkomendeMelding::class.java)
    object Afgesloten: MeldingType(AfgeslotenMelding::class.java)
}
