package applab.veiligthuis.domain.usecase

data class MeldingUseCases(
    val getMeldingen: GetMeldingen,
    val addMelding: AddMelding,
    val editMelding: EditMelding,
    val deleteMelding: DeleteMelding
)
