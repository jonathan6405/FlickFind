package com.example.moviedb2025.database

import com.example.moviedb2025.models.Movie
import com.example.moviedb2025.models.Genre

class Movies {
    fun getMovies(): List<Movie>{
        return listOf(
            Movie(
                950387,
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
                "tt3566834"
            ),
            Movie(
                822119,
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
                "tt14513804"
            ),
            Movie(
                2062,
                "Ratatouille",
                "/t3vaWRPSf6WjDSamIkKDs1iQWna.jpg",
                "/xgDj56UWyeWQcxQ44f5A3RTWuSs.jpg",
                "2007-06-28",
                "Remy, a resident of Paris, appreciates good food and has quite a sophisticated palate. He would love to become a chef so he can create and enjoy culinary masterpieces to his heart's delight. The only problem is, Remy is a rat. When he winds up in the sewer beneath one of Paris' finest restaurants, the rodent gourmet finds himself ideally placed to realize his dream.",
                listOf(
                    Genre(16, "Animation"),
                    Genre(35, "Comedy"),
                    Genre(10751, "Family"),
                    Genre(14, "Fantasy")
                ),
                "http://disney.go.com/disneypictures/ratatouille",
                "tt0382932"
            ),
            Movie(
                1232546,
                "Until Dawn",
                "/6O9nkcmZBymDXtxOGJmulqcxJdv.jpg",
                "/3xKJ0nyUTlySmMBCpOcsuSnFPI1.jpg",
                "2025-04-23",
                "One year after her sister Melanie mysteriously disappeared, Clover and her friends head into the remote valley where she vanished in search of answers. Exploring an abandoned visitor center, they find themselves stalked by a masked killer and horrifically murdered one by one…only to wake up and find themselves back at the beginning of the same evening.",
                listOf(
                    Genre(27, "Horror"),
                    Genre(9648, "Mystery")
                ),
                "https://untildawn.movie",
                "tt30955489"
            ),
            Movie(
                120,
                "The Lord of the Rings: The Fellowship of the Ring",
                "/6oom5QYQ2yQTMJIbnvbkBL9cHo6.jpg",
                "/x2RS3uTcsJJ9IfjNPcgDmukoEcQ.jpg",
                "2001-12-18",
                "Young hobbit Frodo Baggins, after inheriting a mysterious ring from his uncle Bilbo, must leave his home in order to keep it from falling into the hands of its evil creator. Along the way, a fellowship is formed to protect the ringbearer and make sure that the ring arrives at its final destination: Mt. Doom, the only place where it can be destroyed.",
                listOf(
                    Genre(12, "Adventure"),
                    Genre(14, "Fantasy"),
                    Genre(28, "Action")
                ),
                "http://www.lordoftherings.net/",
                "tt0120737"
            ),
            Movie(
                70160,
                "The Hunger Games",
                "/yXCbOiVDCxO71zI7cuwBRXdftq8.jpg",
                "/p2i9vE7mu2ZTmwYbEiimgHeKpq5.jpg",
                "2012-03-12",
                "Every year in the ruins of what was once North America, the nation of Panem forces each of its twelve districts to send a teenage boy and girl to compete in the Hunger Games.  Part twisted entertainment, part government intimidation tactic, the Hunger Games are a nationally televised event in which “Tributes” must fight with one another until one survivor remains.  Pitted against highly-trained Tributes who have prepared for these Games their entire lives, Katniss is forced to rely upon her sharp instincts as well as the mentorship of drunken former victor Haymitch Abernathy.  If she’s ever to return home to District 12, Katniss must make impossible choices in the arena that weigh survival against humanity and life against love. The world will be watching.",
                listOf(
                    Genre(878, "Science Fiction"),
                    Genre(12, "Adventure"),
                    Genre(14, "Fantasy")
                ),
                "http://www.thehungergames.movie/",
                "tt1392170"
            ),
            Movie(
                129,
                "Spirited Away",
                "/39wmItIWsg5sZMyRUHLkWBcuVCM.jpg",
                "/6oaL4DP75yABrd5EbC4H2zq5ghc.jpg",
                "2001-07-20",
                "A young girl, Chihiro, becomes trapped in a strange new world of spirits. When her parents undergo a mysterious transformation, she must call upon the courage she never knew she had to free her family.",
                listOf(
                    Genre(16, "Animation"),
                    Genre(10751, "Family"),
                    Genre(14, "Fantasy")
                ),
                "http://movies.disney.com/spirited-away",
                "tt0245429"
            )
        )
    }
}