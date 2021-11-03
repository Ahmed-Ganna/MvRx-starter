/*
 * Copyright (c) 2020 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.watchlist

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.airbnb.mvrx.*
import kotlinx.android.synthetic.main.fragment_all_movies.*

class AllMoviesFragment : BaseMvRxFragment() {

  private lateinit var movieAdapter: MovieAdapter

  private val watchlistViewModel: WatchlistViewModel by activityViewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_all_movies, container, false)
  }

  override fun invalidate() {
    withState(watchlistViewModel) { state ->
      when (state.movies) {
        // 1
        is Loading -> {
          progress_bar.visibility = View.VISIBLE
          all_movies_recyclerview.visibility = View.GONE
        }
        // 2
        is Success -> {
          progress_bar.visibility = View.GONE
          all_movies_recyclerview.visibility = View.VISIBLE
          movieAdapter.setMovies(state.movies.invoke())
        }
        // 3
        is Fail -> {
          Toast.makeText(
            requireContext(),
            "Failed to load all movies",
            Toast.LENGTH_SHORT
          ).show()
        }
      }
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    movieAdapter = MovieAdapter(object : MovieAdapter.WatchlistListener {
      override fun addToWatchlist(movieId: Long) {
        watchlistViewModel.watchlistMovie(movieId)
      }

      override fun removeFromWatchlist(movieId: Long) {
        watchlistViewModel.removeMovieFromWatchlist(movieId)
      }
    })
    all_movies_recyclerview.adapter = movieAdapter
    watchlistViewModel.errorMessage.observe(viewLifecycleOwner, Observer {
      Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    })
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.watchlist, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == R.id.watchlist) {
      val watchlistFragment = WatchlistFragment()
      val transaction = requireFragmentManager().beginTransaction()
      transaction.replace(R.id.fragment_container, watchlistFragment)
      transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
      transaction.addToBackStack(null)
      transaction.commit()
    }
    return super.onOptionsItemSelected(item)
  }
}