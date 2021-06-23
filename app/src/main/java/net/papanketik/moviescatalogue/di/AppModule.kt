package net.papanketik.moviescatalogue.di

import net.papanketik.moviescatalogue.core.domain.usecase.MovieAndTvShowInteractor
import net.papanketik.moviescatalogue.core.domain.usecase.MovieAndTvShowUseCase
import net.papanketik.moviescatalogue.detail.DetailViewModel
import net.papanketik.moviescatalogue.home.movie.MovieViewModel
import net.papanketik.moviescatalogue.home.tvshow.TvShowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieAndTvShowUseCase> { MovieAndTvShowInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { TvShowViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}