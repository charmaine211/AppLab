package applab.veiligthuis.di

import applab.veiligthuis.domain.usecase.GetMeldingen
import applab.veiligthuis.domain.usecase.MeldingUseCases
import applab.veiligthuis.repository.melding.MeldingRepository
import applab.veiligthuis.repository.melding.MeldingRepositoryImpl
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
            getMeldingen = GetMeldingen(repository)
        )
    }
}