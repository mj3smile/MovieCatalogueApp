package net.papanketik.moviescatalogue.core.domain.usecase

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import kotlinx.coroutines.runBlocking
import net.papanketik.moviescatalogue.core.data.MovieAndTvShowRepository
import net.papanketik.moviescatalogue.core.domain.model.MovieAndTvShow
import org.junit.Before
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieAndTvShowUseCaseTest {
    private lateinit var movieAndTvShowUseCase: MovieAndTvShowUseCase
    @Mock private lateinit var movieAndTvShowRepository: MovieAndTvShowRepository

    @Before
    fun setUp() {
        movieAndTvShowUseCase = MovieAndTvShowInteractor(movieAndTvShowRepository)
        val dummyMovieData = ArrayList<MovieAndTvShow>()
        dummyMovieData.add(MovieAndTvShow(151, "https://image.tmdb.org/t/p/w500/aR2gt5y9zv8.jpg", DUMMY_TITLE, 8.6, "Overview", "2016", 0))
        Mockito.`when`(movieAndTvShowRepository.getMovieDataTest()).thenReturn(dummyMovieData)
    }

    @Test
    fun `should get movie data from repository`() = runBlocking {
        val movieEntity = movieAndTvShowUseCase.getMovieDataTest().first()

        verify(movieAndTvShowRepository).getMovieDataTest()
        verifyNoMoreInteractions(movieAndTvShowRepository)

        assertNotNull(movieEntity)
        assertEquals(DUMMY_TITLE, movieEntity.title)
    }

    companion object {
        const val DUMMY_TITLE = "The Movie Title"
    }
}