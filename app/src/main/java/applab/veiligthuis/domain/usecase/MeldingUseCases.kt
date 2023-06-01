package applab.veiligthuis.domain.usecase

data class MeldingUseCases(
    val getMeldingen: GetMeldingen,
    val insertInkomendeMelding: InsertInkomendeMelding,
    val editMelding: EditMelding,
    val getMelding: GetMelding
)
