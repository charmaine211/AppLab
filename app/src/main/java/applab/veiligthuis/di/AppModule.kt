package applab.veiligthuis.di

import applab.veiligthuis.domain.usecase.*
import applab.veiligthuis.data.melding.MeldingRepository
import applab.veiligthuis.data.melding.MeldingRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMeldingRepository(): MeldingRepository {
        return MeldingRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideMeldingUseCases(repository: MeldingRepository): MeldingUseCases {
        return MeldingUseCases(
            getMeldingen = GetMeldingen(repository),
            insertInkomendeMelding = InsertInkomendeMelding(repository),
            editMelding = EditMelding(repository),
            getMelding = GetMelding(repository)
        )
    }
}