package net.papanketik.moviescatalogue.favorite.di

import net.papanketik.moviescatalogue.favorite.movie.FavoriteMovieViewModel
import net.papanketik.moviescatalogue.favorite.tvshow.FavoriteTvShowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { FavoriteMovieViewModel(get()) }
    viewModel { FavoriteTvShowViewModel(get()) }
}