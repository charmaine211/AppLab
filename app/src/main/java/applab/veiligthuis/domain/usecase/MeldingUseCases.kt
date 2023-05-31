package applab.veiligthuis.domain.usecase

data class MeldingUseCases(
    val getMeldingen: GetMeldingen,
    val addMelding: AddMelding,
    val updateMelding: UpdateMelding,
    val deleteMelding: DeleteMelding,
    val getMelding: GetMelding
)
