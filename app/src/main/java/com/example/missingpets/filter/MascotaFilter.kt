package com.example.missingpets.filter

import com.example.missingpets.network.Mascota

/**
 * Este filtro recibe un List de Mascota, y filtra en base a un Map de String.  En el Map cada
 * criterio de busqueda es un par key-value, y en el caso de la distancia maxima son 3 key-value: 1 para
 * la distancia en kilometros, otro para la latitud, y otro para la longitud
 */

class MascotaFilter {

    private fun filter (list: List<Mascota>, searchCriteria: Map<String?,String>): List<Mascota> {
        var result: List<Mascota>

        //Filtro por tipomascota
        if(searchCriteria.containsKey("tipomascota")) {
            result = list.filter { it.tipoAnimal == searchCriteria.get("tipoanimal") }
        } else {
            result = list
        }

        //TODO agregar el resto de los filtros

        if(searchCriteria.containsKey("distanciamaxima")) {
            result = list.filter { calcularDistancia(it.latitude?.toDouble(), it.longitude?.toDouble(),
                searchCriteria.get("latitude")?.toDouble(), searchCriteria.get("longitude")?.toDouble()) <= searchCriteria.get("distanciamaxima")?.toInt()!!
            }
        } else {
            result = list
        }

        return result
    }


    private fun calcularDistancia(lat1: Double?, lon1: Double?, lat2: Double?, lon2: Double?): Double {
        //TODO calcular la distancia en kilómetros
        return 0.0
    }

}
