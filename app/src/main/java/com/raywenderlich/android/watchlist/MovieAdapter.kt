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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieAdapter(private val wathclistListener: WatchlistListener) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

  private val movies = mutableListOf<MovieModel>()

  fun setMovies(movies: List<MovieModel>) {
    this.movies.clear()
    this.movies.addAll(movies)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
    val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.movie_viewholder_layout, parent, false)
    return MovieViewHolder(view)
  }

  override fun getItemCount() = movies.size

  override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
    val movie = movies[position]

    Glide
        .with(holder.itemView)
        .load(movie.posterLink)
        .centerCrop()
        .into(holder.posterImageView)

    holder.movieNameTextView.text = movie.name
    if (movie.isWatchlisted) {
      holder.watchlistButton.setImageResource(R.drawable.ic_remove_from_watchlist)
    } else {
      holder.watchlistButton.setImageResource(R.drawable.ic_add_to_watchlist)
    }
    holder.watchlistButton.setOnClickListener {
      if (movie.isWatchlisted) {
        wathclistListener.removeFromWatchlist(movie.id)
      } else {
        wathclistListener.addToWatchlist(movie.id)
      }
    }
  }


  inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val posterImageView: ImageView = itemView.findViewById(R.id.movie_poster_imageview)
    val movieNameTextView: TextView = itemView.findViewById(R.id.movie_name_textview)
    val watchlistButton: ImageView = itemView.findViewById(R.id.watchlist_button)
  }

  interface WatchlistListener {

    fun addToWatchlist(movieId: Long)

    fun removeFromWatchlist(movieId: Long)
  }
}