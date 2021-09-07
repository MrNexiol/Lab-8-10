package com.prograils.lab8_10

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prograils.lab8_10.databinding.ActivityMainBinding
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MoviesAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        binding.typeSpinner.adapter = ArrayAdapter.createFromResource(this, R.array.spinner_values, android.R.layout.simple_spinner_dropdown_item)
        recyclerView = binding.moviesRecyclerView
        adapter = MoviesAdapter()
        layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager

        binding.searchButton.setOnClickListener {
            val query = binding.searchQueryField.text.toString()
            val criteria = binding.typeSpinner.selectedItem.toString()
            if (query.isNotEmpty()) {
                when (criteria) {
                    "Tytuł" -> getMoviesByTitle(query)
                    "Rok" -> {
                        try {
                            val numberQuery = query.toInt()
                            discoverMovies(year = numberQuery)
                        } catch (nfe: NumberFormatException) {
                            Toast.makeText(this, getString(R.string.number_query), Toast.LENGTH_LONG).show()
                        }
                    }
                    "Aktor" -> {
                        val phrases = query.split(",")
                        val ids = mutableListOf<Int>()
                        phrases.forEach { phrase ->
                            AppContainer.repository.getActors(phrase).observe(this, { actors ->
                                if (actors != null && actors.isNotEmpty()) {
                                    ids.add(actors.first().id)
                                    if (ids.size == phrases.size) discoverMovies(actorIds = ids.joinToString())
                                }
                            })
                        }
                    }
                    else -> Toast.makeText(this, "Elo", Toast.LENGTH_LONG).show()
                }
                hideKeyboard()
            } else {
                Toast.makeText(this, getString(R.string.too_short), Toast.LENGTH_LONG).show()
            }
        }

        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getMoviesByTitle(title: String) {
        AppContainer.repository.getMoviesByTitle(title).observe(this, { movies ->
            if (movies != null) {
                adapter.setData(movies)
            }
        })
    }

    private fun discoverMovies(year: Int? = null, actorIds: String? = null) {
        AppContainer.repository.discoverMovies(year, actorIds).observe(this, { movies ->
            if (movies != null) {
                adapter.setData(movies)
            }
        })
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = currentFocus
        if (view == null) view = View(this)
        imm.hideSoftInputFromWindow(view.windowToken,0)
    }
}