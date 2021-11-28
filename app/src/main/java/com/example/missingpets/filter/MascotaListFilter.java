package com.example.missingpets.filter;

import com.example.missingpets.network.Mascota;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MascotaListFilter {

    public MascotaListFilter() {
    }

    static long calcularAntiguedad(String fecha) {
        Date eventDate ;
        Date currentDate = new Date();;
        try {
            eventDate = new SimpleDateFormat("dd-MM-yyyy").parse(fecha);
        } catch (Exception e) {
            try {
                eventDate = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
            } catch (Exception ex) {
                return 30000;
            }
        }

        long diffInMillies = Math.abs(currentDate.getTime() - eventDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return diff;

    }

    /**
     *
     * @param latitude1 grados
     * @param longitude1 grados
     * @param latitude2 grados
     * @param longitude2 grados
     * @return kilometros
     */
    public static double calcularDistancia(double latitude1, double longitude1, double latitude2, double longitude2) {
        if ((latitude1 == latitude2) && (longitude1 == longitude2)) {
            return 0;
        }
        else {
            double theta = longitude1 - longitude2;
            double dist = Math.sin(Math.toRadians(latitude1)) * Math.sin(Math.toRadians(latitude2)) + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344;
            return (dist);
        }
    }

    /**
     *
     * @param mascotaList lista a filtrar
     * @param tipoMascota perro, gato, etc
     * @param sexo macho o hembra
     * @param distanciaMaximaKm si es negativo se omite
     * @param latitude se usa solo si distanciaMaximaKm es > -1
     * @param longitude se usa solo si distanciaMaximaKm es > -1
     * @param antiguedadMaxima cantidad de dias
     * @return
     */
    static public List<Mascota> filter(List<Mascota> mascotaList, String tipoMascota, String sexo, int distanciaMaximaKm, float latitude, float longitude, int antiguedadMaxima) {

        List<Mascota> result;

        //Filtro por tipomascota
        if(tipoMascota != null && tipoMascota.length() > 0) {
            result = mascotaList
                    .stream()
                    .filter(c -> 0 == c.getTipoAnimal().compareToIgnoreCase(tipoMascota))
                    .collect(Collectors.toList());
        } else {
            result = mascotaList;
        }

        //Filtro por sexo
        if(sexo != null && sexo.length() > 0) {
            result = result
                    .stream()
                    .filter(c -> 0 == c.getSexoAnimal().compareToIgnoreCase(sexo))
                    .collect(Collectors.toList());
        } else {
            //result = result;
        }

        //Filtro por fecha
        if(antiguedadMaxima > -1) {
            result = result
                    .stream()
                    .filter(c -> calcularAntiguedad(c.getFechaPerdido()) < antiguedadMaxima)
                    .collect(Collectors.toList());
        } else {
            //result = mascotaList;
        }

        //Filtro por radio de distancia

      //  if(distanciaMaximaKm > -1) {
        if(distanciaMaximaKm > 100000000) {  // le puse este valor para que no entre ... algo no funciona bien.
            result = result
                    .stream()
                    .filter(c ->
                            calcularDistancia(c.getLatitude(), c.getLongitude(),
                                    latitude,
                                    longitude
                                    ) < distanciaMaximaKm
                    )
                    .collect(Collectors.toList());
        } else {
            //result = mascotaList;
        }


        return result;
    }


}
