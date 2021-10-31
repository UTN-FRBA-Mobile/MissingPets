package com.example.missingpets.dataRV

import com.example.missingpets.R
import com.example.missingpets.modelRV.MissingPet

class MissingDatasource {
    fun loadMissingPets(): List<MissingPet> {
        return listOf(
            MissingPet("Hola, me perdi ayer a las 18:00 cerca de la plaza.", R.drawable.image1),
            MissingPet("Se busca, recompensa $$$", R.drawable.image2),
            MissingPet("Ayudaaaaa", R.drawable.image3),
            MissingPet("Se perdioooooo", R.drawable.image4),
            MissingPet("Si alguien lo ve llame al WP: 000000000", R.drawable.image5),
            MissingPet("Se busca, recompensa $$$", R.drawable.image6),
            MissingPet("Hola, me perdi ayer a las 18:00 cerca de la plaza.", R.drawable.image7),
            MissingPet("Hola, me perdi ayer a las 18:00 cerca de la plaza.", R.drawable.image8),
            MissingPet("Si alguien lo ve llame al WP: 000000000", R.drawable.image9),
            MissingPet("Si alguien lo ve llame al WP: 000000000", R.drawable.image10)
        )
    }
}