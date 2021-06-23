package net.papanketik.moviescatalogue.home.tvshow

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import net.papanketik.moviescatalogue.databinding.FragmentTvShowBinding
import net.papanketik.moviescatalogue.core.domain.model.MovieAndTvShow
import net.papanketik.moviescatalogue.detail.DetailActivity
import net.papanketik.moviescatalogue.core.ui.ListDataAdapter
import net.papanketik.moviescatalogue.core.vo.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvShowFragment : Fragment() {
    private lateinit var fragmentTvShowBinding: FragmentTvShowBinding
    private val listDataAdapter = ListDataAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentTvShowBinding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
        return fragmentTvShowBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: TvShowViewModel by viewModel()

        fragmentTvShowBinding.tvProgressBar.visibility = View.VISIBLE
        viewModel.tvShows.observe(viewLifecycleOwner, { tvShows ->
            when(tvShows) {
                is Resource.Loading -> fragmentTvShowBinding.tvProgressBar.visibility = View.VISIBLE
                is Resource.Success -> {
                    fragmentTvShowBinding.tvProgressBar.visibility = View.GONE
                    fragmentTvShowBinding.tvFailMessage.visibility = View.GONE
                    viewLifecycleOwner.lifecycleScope.launch {
                        if (tvShows.data != null) listDataAdapter.submitData(tvShows.data!!)
                    }
                }
                is Resource.Error -> {
                    fragmentTvShowBinding.tvProgressBar.visibility = View.GONE
                    fragmentTvShowBinding.tvFailMessage.visibility = View.VISIBLE
                }
            }
        })

        showRecyclerList()
    }

    private fun showRecyclerList() {
        with(fragmentTvShowBinding.rvTvshow) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = listDataAdapter
        }

        listDataAdapter.setOnItemClickCallback(object: ListDataAdapter.OnItemClickCallback {
            override fun onItemClicked(data: MovieAndTvShow) {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID, data.id)
                intent.putExtra(DetailActivity.EXTRA_TYPE, DetailActivity.TV_TYPE_CODE)
                startActivity(intent)
            }
        })
    }
}