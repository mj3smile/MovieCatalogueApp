package net.papanketik.moviescatalogue.home.movie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import net.papanketik.moviescatalogue.databinding.FragmentMovieBinding
import net.papanketik.moviescatalogue.core.domain.model.MovieAndTvShow
import net.papanketik.moviescatalogue.detail.DetailActivity
import net.papanketik.moviescatalogue.core.ui.ListDataAdapter
import net.papanketik.moviescatalogue.core.vo.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {
    private lateinit var fragmentMovieBinding: FragmentMovieBinding
    private val listDataAdapter = ListDataAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentMovieBinding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return fragmentMovieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: MovieViewModel by viewModel()

        fragmentMovieBinding.movieProgressBar.visibility = View.VISIBLE
        viewModel.movies.observe(viewLifecycleOwner, { movies ->
            when(movies) {
                is Resource.Loading -> fragmentMovieBinding.movieProgressBar.visibility = View.VISIBLE
                is Resource.Success -> {
                    fragmentMovieBinding.movieProgressBar.visibility = View.GONE
                    fragmentMovieBinding.movieFailMessage.visibility = View.GONE
                    viewLifecycleOwner.lifecycleScope.launch {
                        if (movies.data != null) listDataAdapter.submitData(movies.data!!)
                    }
                }
                is Resource.Error -> {
                    fragmentMovieBinding.movieProgressBar.visibility = View.GONE
                    fragmentMovieBinding.movieFailMessage.visibility = View.VISIBLE
                }
            }
        })

        showRecyclerList()
    }

    private fun showRecyclerList() {
        with(fragmentMovieBinding.rvMovie) {
            layoutManager = LinearLayoutManager(context)
            adapter = listDataAdapter
        }

        listDataAdapter.setOnItemClickCallback(object: ListDataAdapter.OnItemClickCallback {
            override fun onItemClicked(data: MovieAndTvShow) {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID, data.id)
                intent.putExtra(DetailActivity.EXTRA_TYPE, DetailActivity.MOVIE_TYPE_CODE)
                startActivity(intent)
            }
        })
    }
}