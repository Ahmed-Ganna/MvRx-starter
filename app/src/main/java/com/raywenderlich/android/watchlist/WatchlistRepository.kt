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

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class WatchlistRepository {

  private val movies = mutableListOf<MovieModel>()

  fun getWatchlistedMovies() = Observable.fromCallable<List<MovieModel>> {
    Thread.sleep(3000)
    movies.addAll(listOf(
        MovieModel(
            1234,
            "Fight Club",
            "https://image.tmdb.org/t/p/w500/adw6Lq9FiC9zjYEpOqfq03ituwp.jpg",
            false
        ),
        MovieModel(
            1235,
            "Ad Astra",
            "https://image.tmdb.org/t/p/w500/xBHvZcjRiWyobQ9kxBhO6B2dtRI.jpg",
            false
        ),
        MovieModel(
            1236,
            "Joker",
            "https://image.tmdb.org/t/p/w500/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg",
            false
        ),
        MovieModel(
            1237,
            "Star Wars: The Rise of Skywalker",
            "https://image.tmdb.org/t/p/w500/db32LaOibwEliAmSL2jjDF6oDdj.jpg",
            false
        ),
        MovieModel(
            1238,
            "Jumanji: The Next Level",
            "https://image.tmdb.org/t/p/w500//jyw8VKYEiM1UDzPB7NsisUgBeJ8.jpg",
            false
        ),
        MovieModel(
            1239,
            "Ip Man 4: The Finale",
            "https://image.tmdb.org/t/p/w500/yJdeWaVXa2se9agI6B4mQunVYkB.jpg",
            false
        ),
        MovieModel(
            1210,
            "Frozen II",
            "https://image.tmdb.org/t/p/w500/pjeMs3yqRmFL3giJy4PMXWZTTPa.jpg",
            false
        ),
        MovieModel(
            1211,
            "Terminator: Dark Fate",
            "https://image.tmdb.org/t/p/w500/vqzNJRH4YyquRiWxCCOH0aXggHI.jpg",
            false
        ),
        MovieModel(
            1212,
            "Once Upon a Time… in Hollywood",
            "https://image.tmdb.org/t/p/w500/8j58iEBw9pOXFD2L0nt0ZXeHviB.jpg",
            false
        ),
        MovieModel(
            1213,
            "Maleficent: Mistress of Evil",
            "https://image.tmdb.org/t/p/w500/vloNTScJ3w7jwNwtNGoG8DbTThv.jpg",
            false
        )
    ))
    movies

  }.subscribeOn(Schedulers.io())

    fun watchlistMovie(movieId: Long): Observable<MovieModel> {
        return Observable.fromCallable {
            val movie = movies.first { movie -> movie.id == movieId }
            movie.copy(isWatchlisted = true)
        }
    }

    fun removeMovieFromWatchlist(movieId: Long): Observable<MovieModel> {
        return Observable.fromCallable {
            val movie = movies.first { movie -> movie.id == movieId }
            movie.copy(isWatchlisted = false)
        }
    }

}