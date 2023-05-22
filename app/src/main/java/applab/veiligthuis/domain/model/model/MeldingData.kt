package applab.veiligthuis.domain.model.model

import java.time.LocalDate
import java.time.LocalDateTime


data class MeldingData(
    val datum: LocalDateTime? = null,
    val locatie: String? = null,
    val info: String? = null,
    val status: MeldingStatus? = null,
    val anoniem: Boolean? = null,
)