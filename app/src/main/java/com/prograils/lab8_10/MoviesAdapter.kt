package com.prograils.lab8_10

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prograils.lab8_10.api.Movie
import com.prograils.lab8_10.databinding.RecyclerViewItemBinding

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    private var data: List<Movie> = listOf()

    class ViewHolder(
            val binding: RecyclerViewItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.movieTitle.text = data[position].title
        holder.binding.movieYear.text = data[position].releaseDate
        holder.binding.movieGenre.text = data[position].overview
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: List<Movie>) {
        this.data = data
        notifyDataSetChanged()
    }
}