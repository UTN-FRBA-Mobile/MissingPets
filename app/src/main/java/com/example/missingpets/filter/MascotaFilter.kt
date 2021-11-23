package com.example.missingpets.filter

import com.example.missingpets.network.Mascota

class MascotaFilter {

    private fun filter (list: List<Mascota>, filter: Map<String?,String>): List<Mascota> {
        var result: List<Mascota>
        if(filter.containsKey("tipomascota")) {
            list.filter {  }
        }
        return list
    }


    private fun calcularDistancia(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val theta = lon1 - lon2
        var dist = (Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + (Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta))))
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist = dist * 60 * 1.1515
        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }

}

private fun <E> List<E>.filter(predicate: (E) -> Unit) {

}
