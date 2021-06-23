package net.papanketik.moviescatalogue.home.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import net.papanketik.moviescatalogue.core.domain.usecase.MovieAndTvShowUseCase

class TvShowViewModel(movieAndTvShowUseCase: MovieAndTvShowUseCase) : ViewModel() {
    val tvShows = movieAndTvShowUseCase.getTvShowData().asLiveData()
}