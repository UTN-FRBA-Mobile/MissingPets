package com.example.missingpets.dataRV

import com.example.missingpets.R
import com.example.missingpets.modelRV.Buscado

class MissingDatasource {
    fun loadMissingPets(): List<Buscado> {
        return listOf<Buscado>(
            Buscado("Hola, me perdi ayer a las 18:00 cerca de la plaza.", R.drawable.image1),
            Buscado("Se busca, recompensa $$$", R.drawable.image2),
            Buscado("Ayudaaaaa", R.drawable.image3),
            Buscado("Se perdioooooo", R.drawable.image4),
            Buscado("Si alguien lo ve llame al WP: 000000000", R.drawable.image5),
            Buscado("Se busca, recompensa $$$", R.drawable.image6),
            Buscado("Hola, me perdi ayer a las 18:00 cerca de la plaza.", R.drawable.image7),
            Buscado("Hola, me perdi ayer a las 18:00 cerca de la plaza.", R.drawable.image8),
            Buscado("Si alguien lo ve llame al WP: 000000000", R.drawable.image9),
            Buscado("Si alguien lo ve llame al WP: 000000000", R.drawable.image10)
        )
    }
}