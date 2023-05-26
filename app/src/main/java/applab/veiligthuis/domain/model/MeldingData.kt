package applab.veiligthuis.domain.model

import java.time.LocalDateTime


data class MeldingData(
    val key: String? = null,
    val datum: LocalDateTime? = null,
    val plaatsnaam: String? = null,
    val beschrijving: String? = null,
    val status: MeldingStatus? = null,
    val typeGeweld: String? = null,
    val beroepsmatig: Boolean = false
)