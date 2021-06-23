package net.papanketik.moviescatalogue.detail

import androidx.lifecycle.*
import net.papanketik.moviescatalogue.core.domain.model.MovieAndTvShow
import net.papanketik.moviescatalogue.core.domain.usecase.MovieAndTvShowUseCase
import net.papanketik.moviescatalogue.core.vo.Resource

class DetailViewModel(private val movieAndTvShowUseCase: MovieAndTvShowUseCase): ViewModel() {
    private val contentId = MutableLiveData<Int>()
    private val contentType = MutableLiveData<Int>()

    fun setContent(contentId: Int, contentType: Int) {
        this.contentId.value = contentId
        this.contentType.value = contentType
    }

    var contentDetail: LiveData<Resource<MovieAndTvShow>> = Transformations.switchMap(contentId) { contentId ->
        when(contentType.value) {
            DetailActivity.MOVIE_TYPE_CODE -> movieAndTvShowUseCase.getMovieDataById(contentId).asLiveData()
            DetailActivity.TV_TYPE_CODE -> movieAndTvShowUseCase.getTvShowDataById(contentId).asLiveData()
            else -> MutableLiveData()
        }
    }

    fun setBookmark() {
        val contentResource  = contentDetail.value
        if (contentResource != null) {
            val contentData = contentResource.data
            if (contentData != null) {
                val newBookmarkState = !contentData.bookmarked
                movieAndTvShowUseCase.setContentBookmark(contentData, newBookmarkState)
            }
        }
    }
}