package net.papanketik.moviescatalogue.favorite.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import net.papanketik.moviescatalogue.core.domain.usecase.MovieAndTvShowUseCase

class FavoriteTvShowViewModel(movieAndTvShowUseCase: MovieAndTvShowUseCase): ViewModel() {
    val favoriteTvShows = movieAndTvShowUseCase.getBookmarkedTvShowData().asLiveData()
}