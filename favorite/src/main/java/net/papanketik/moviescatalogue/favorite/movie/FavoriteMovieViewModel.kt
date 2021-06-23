package net.papanketik.moviescatalogue.favorite.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import net.papanketik.moviescatalogue.core.domain.usecase.MovieAndTvShowUseCase

class FavoriteMovieViewModel(movieAndTvShowUseCase: MovieAndTvShowUseCase): ViewModel() {
    val favoriteMovies = movieAndTvShowUseCase.getBookmarkedMovieData().asLiveData()
}