package net.papanketik.moviescatalogue.favorite.tvshow

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import net.papanketik.moviescatalogue.core.domain.model.MovieAndTvShow
import net.papanketik.moviescatalogue.core.ui.ListDataAdapter
import net.papanketik.moviescatalogue.detail.DetailActivity
import net.papanketik.moviescatalogue.favorite.databinding.FragmentFavoriteTvShowBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteTvShowFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteTvShowBinding
    private val listDataAdapter = ListDataAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteTvShowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: FavoriteTvShowViewModel by viewModel()

        binding.favoriteTvProgressBar.visibility = View.VISIBLE
        viewModel.favoriteTvShows.observe(viewLifecycleOwner, {
            binding.favoriteTvProgressBar.visibility = View.GONE
            viewLifecycleOwner.lifecycleScope.launch {
                listDataAdapter.submitData(it)
            }

            listDataAdapter.addLoadStateListener { loadState ->
                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && listDataAdapter.itemCount < 1) {
                    binding.noDataMessage.visibility = View.VISIBLE
                } else {
                    binding.noDataMessage.visibility = View.GONE
                }
            }
        })

        showRecyclerList()
    }

    private fun showRecyclerList() {
        with(binding.rvFavoriteTv) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = listDataAdapter
        }

        listDataAdapter.setOnItemClickCallback(object: ListDataAdapter.OnItemClickCallback {
            override fun onItemClicked(data: MovieAndTvShow) {
                val intent = Intent(activity, Class.forName("net.papanketik.moviescatalogue.detail.DetailActivity"))
                intent.putExtra(DetailActivity.EXTRA_ID, data.id)
                intent.putExtra(DetailActivity.EXTRA_TYPE, DetailActivity.TV_TYPE_CODE)
                startActivity(intent)
            }
        })
    }
}