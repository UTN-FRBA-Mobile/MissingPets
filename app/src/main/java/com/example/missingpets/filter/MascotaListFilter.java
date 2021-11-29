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

    public static boolean isBetweenDates(String strFechaPerdido, String strFechaDesde, String strfechaHasta) {

        Date eventDate;
        Date startDate;
        Date endDate;
        boolean result;

        try {
            eventDate = new SimpleDateFormat("dd-MM-yyyy").parse(strFechaPerdido);
            startDate = new SimpleDateFormat("dd-MM-yyyy").parse(strFechaDesde);
            endDate = new SimpleDateFormat("dd-MM-yyyy").parse(strfechaHasta);
        } catch (Exception e) {
            try {
                eventDate = new SimpleDateFormat("yyyy-MM-dd").parse(strFechaPerdido);
                startDate = new SimpleDateFormat("yyyy-MM-dd").parse(strFechaDesde);
                endDate = new SimpleDateFormat("yyyy-MM-dd").parse(strfechaHasta);

            } catch (Exception ex) {
                return false;
            }
        }

        if(eventDate.compareTo(startDate) >= 0 && eventDate.compareTo(endDate) <= 0){
            result = true;
        } else {
            result = false;
        }


        return result;
    }


    /**
     *
     * @param mascotaList lista a filtrar
     * @param tipoMascota perro, gato, etc
     * @param sexo macho o hembra
     * @param distanciaMaximaKm si es negativo se omite
     * @param latitude se usa solo si distanciaMaximaKm es > -1
     * @param longitude se usa solo si distanciaMaximaKm es > -1
     * @param strFechaDesde
     * @param strFechaHasta
     * @return
     */
    static public List<Mascota> filter(List<Mascota> mascotaList, String tipoMascota, String sexo, int distanciaMaximaKm, float latitude, float longitude, String strFechaDesde, String strFechaHasta) {

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

        // IMPORTANTE: la fechas deben venir en español dd/mm/yyyy sino falla en isBeteenDates
        if(strFechaDesde != null && strFechaDesde.length() > 0 &&
                strFechaHasta != null && strFechaHasta.length() > 0) {

            result = result
                    .stream()
                    .filter(c ->
                            isBetweenDates(c.getFechaPerdido(), strFechaDesde, strFechaHasta)
                    )
                    .collect(Collectors.toList());
        }



        //Filtro por radio de distancia

          if(distanciaMaximaKm > -1 && latitude!=0f && longitude!=0f) {
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
