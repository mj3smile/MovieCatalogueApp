package net.papanketik.moviescatalogue.home.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import net.papanketik.moviescatalogue.core.domain.usecase.MovieAndTvShowUseCase

class MovieViewModel(movieAndTvShowUseCase: MovieAndTvShowUseCase) : ViewModel() {
    val movies = movieAndTvShowUseCase.getMovieData().asLiveData()
}