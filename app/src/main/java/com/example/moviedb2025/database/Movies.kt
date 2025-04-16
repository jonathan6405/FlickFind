package com.example.moviedb2025.database

import com.example.moviedb2025.models.Movie
import com.example.moviedb2025.models.Genre

class Movies {
    fun getMovies(): List<Movie>{
        return listOf(
            Movie(
                1,
                "A Minecraft Movie",
                "/yFHHfHcUgGAxziP1C3lLt0q2T4s.jpg",
                "/2Nti3gYAX513wvhp8IiLL6ZDyOm.jpg",
                "2025-03-31",
                "Four misfits find themselves struggling with ordinary problems when they are suddenly pulled through a mysterious portal into the Overworld: a bizarre, cubic wonderland that thrives on imagination. To get back home, they'll have to master this world while embarking on a magical quest with an unexpected, expert crafter, Steve.",
                listOf(
                    Genre(10751,"Family"),
                    Genre(35, "Comedy"),
                    Genre(12, "Adventure"),
                    Genre(14, "Fantasy")
                ),
                "https://www.minecraft.net",
                "tt1234567"
            ),
            Movie(
                2,
                "Captain America: Brave New World",
                "/pzIddUEMWhWzfvLI3TwxUG2wGoi.jpg",
                "/ce3prrjh9ZehEl5JinNqr4jIeaB.jpg",
                "2025-02-12",
                "After meeting with newly elected U.S. President Thaddeus Ross, Sam finds himself in the middle of an international incident. He must discover the reason behind a nefarious global plot before the true mastermind has the entire world seeing red.",
                listOf(
                    Genre(28, "Action"),
                    Genre(53, "Thriller"),
                    Genre(878, "Science Fiction")
                ),
                "https://marvel.com/captainamerica",
                "tt8912936"
            )
        )
    }
}