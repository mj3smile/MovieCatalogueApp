package net.papanketik.moviescatalogue.core.di

import androidx.room.Room
import net.papanketik.moviescatalogue.core.data.MovieAndTvShowRepository
import net.papanketik.moviescatalogue.core.data.source.local.LocalDataSource
import net.papanketik.moviescatalogue.core.data.source.local.room.MovieAndTvShowDatabase
import net.papanketik.moviescatalogue.core.data.source.remote.RemoteDataSource
import net.papanketik.moviescatalogue.core.data.source.remote.network.ApiService
import net.papanketik.moviescatalogue.core.domain.repository.IMovieAndTvShowRepository
import net.papanketik.moviescatalogue.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val databaseModule = module {
    factory { get<MovieAndTvShowDatabase>().movieAndTvDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("a8760306ac8bb1516b3e6681bc11744c".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            MovieAndTvShowDatabase::class.java, "db_movies"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = "api.themoviedb.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0= ")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IMovieAndTvShowRepository> {
        MovieAndTvShowRepository(get(), get(), get())
    }
}