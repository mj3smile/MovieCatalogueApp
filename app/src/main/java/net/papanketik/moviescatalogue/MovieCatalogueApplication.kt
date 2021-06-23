package net.papanketik.moviescatalogue

import android.app.Application
import net.papanketik.moviescatalogue.core.di.databaseModule
import net.papanketik.moviescatalogue.core.di.networkModule
import net.papanketik.moviescatalogue.core.di.repositoryModule
import net.papanketik.moviescatalogue.di.useCaseModule
import net.papanketik.moviescatalogue.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MovieCatalogueApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MovieCatalogueApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}