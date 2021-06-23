package net.papanketik.moviescatalogue.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import net.papanketik.moviescatalogue.R
import net.papanketik.moviescatalogue.core.domain.model.MovieAndTvShow
import net.papanketik.moviescatalogue.databinding.ActivityDetailBinding
import net.papanketik.moviescatalogue.core.vo.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var activityDetailBinding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val contentId = intent.getIntExtra(EXTRA_ID, -1)
        val contentType = intent.getIntExtra(EXTRA_TYPE, -1)

        showLoading(true)

        viewModel.setContent(contentId, contentType)

        viewModel.contentDetail.observe(this, {
            showLoading(false)
            setView(it.data)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bookmark, menu)
        this.menu = menu
        viewModel.contentDetail.observe(this, { contentDetails ->
            if (contentDetails != null) {
                when(contentDetails) {
                    is Resource.Loading -> showLoading(true)
                    is Resource.Success -> if (contentDetails.data != null) {
                        showLoading(false)
                        val isBookmarked = contentDetails.data!!.bookmarked
                        setBookmarkState(isBookmarked)
                    }
                    is Resource.Error -> {
                        showLoading(false)
                    }
                }
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_bookmark) {
            viewModel.setBookmark()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setView(contentData: MovieAndTvShow?) {
        Glide.with(this)
            .load(contentData?.poster)
            .into(activityDetailBinding.imgPoster)
        activityDetailBinding.imgPoster.tag = contentData?.poster
        activityDetailBinding.tvTitle.text = contentData?.title
        activityDetailBinding.tvRatingValue.text = contentData?.rating.toString()
        activityDetailBinding.tvOverviewValue.text = contentData?.overview
        activityDetailBinding.tvYear.text = contentData?.year
    }

    private fun setBookmarkState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_bookmark)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_bookmark)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_bookmark_border)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            activityDetailBinding.progressBar.visibility = View.VISIBLE
            activityDetailBinding.tvRating.visibility = View.GONE
            activityDetailBinding.tvOverview.visibility = View.GONE
        } else {
            activityDetailBinding.progressBar.visibility = View.GONE
            activityDetailBinding.tvRating.visibility = View.VISIBLE
            activityDetailBinding.tvOverview.visibility = View.VISIBLE
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_TYPE = "extra_type"
        const val MOVIE_TYPE_CODE = 100
        const val TV_TYPE_CODE = 101
    }
}