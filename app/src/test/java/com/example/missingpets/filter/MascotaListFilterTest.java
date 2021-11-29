package com.example.missingpets.filter;

import static org.junit.Assert.*;

import com.example.missingpets.network.Mascota;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MascotaListFilterTest {

    private final float MEDRANO_LATITUDE = -34.59859106473504f;
    private final float MEDRANO_LONGITUDE = -58.422127484213156f;

    private final float CAMPUS_LATITUDE = -34.65927256794779f;
    private final float CAMPUS_LONGITUDE = -58.4695278842118f;

    private final float SUNSET_LATITUDE = -34.45460265713158f;
    private final float SUNSET_LONGITUDE = -58.91265038421665f;

    private final float SUBTE_MEDRANO_LATITUDE = -34.60317446497749f;
    private final float SUBTE_MEDRANO_LONGITUDE = -58.423155584213134f;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void calcularAntiguedad() {
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        LocalDate localDate = LocalDate.now().minusDays(23);
        Date currentDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        String strDate = simpleDateFormat.format(currentDate);
        assertEquals(23, MascotaListFilter.calcularAntiguedad(strDate));
    }

    @Test
    public void calcularDistancia() {
        double distance = MascotaListFilter.calcularDistancia(MEDRANO_LATITUDE, MEDRANO_LONGITUDE, SUBTE_MEDRANO_LATITUDE, SUBTE_MEDRANO_LONGITUDE);
        assertEquals( 0.52, distance, 0.005f);

        double distance2 = MascotaListFilter.calcularDistancia(MEDRANO_LATITUDE, MEDRANO_LONGITUDE, CAMPUS_LATITUDE, CAMPUS_LONGITUDE);
        assertEquals( 8.02064629711324, distance2, 0.005f);
    }

    @Test
    public void filtrarPorFechas() {
        List<Mascota> mascotaList = new ArrayList<>();
        Mascota mascota1 = new Mascota();
        Mascota mascota2 = new Mascota();
        Mascota mascota3 = new Mascota();

        mascota1.setTipoAnimal("gato");
        mascota1.setFechaPerdido("25-05-2021");


        mascota2.setTipoAnimal("perro");
        mascota2.setFechaPerdido("26-05-2021");

        mascota3.setTipoAnimal("perro");
        mascota3.setFechaPerdido("01-06-2021");


        mascotaList.add(mascota1);
        mascotaList.add(mascota2);
        mascotaList.add(mascota3);

        List<Mascota> result10 = MascotaListFilter.filter(mascotaList, "", "", -1, 0.0f, 0.0f,  "01-05-2016", "25-05-2021");
        assertEquals( 1, result10.size());

        List<Mascota> result11 = MascotaListFilter.filter(mascotaList, "", "", -1, 0.0f, 0.0f,  "01-05-2016", "26-05-2021");
        assertEquals( 2, result11.size());

        List<Mascota> result12 = MascotaListFilter.filter(mascotaList, "", "", -1, 0.0f, 0.0f,  "01-05-2016", "01-07-2021");
        assertEquals( 3, result12.size());

        List<Mascota> result13 = MascotaListFilter.filter(mascotaList, "", "", -1, 0.0f, 0.0f,  "01-01-2000", "24-05-2021");
        assertEquals( 0, result13.size());

        List<Mascota> result14 = MascotaListFilter.filter(mascotaList, "", "", -1, 0.0f, 0.0f,  "02-06-2021", "31-12-2022");
        assertEquals( 0, result14.size());

    }
    @Test
    public void filter() {
        List<Mascota> mascotaList = new ArrayList<>();
        Mascota mascota1 = new Mascota();
        Mascota mascota2 = new Mascota();
        Mascota mascota3 = new Mascota();

        mascota1.setTipoAnimal("gato");
        mascota1.setLatitude(MEDRANO_LATITUDE);
        mascota1.setLongitude(MEDRANO_LONGITUDE);
        mascota1.setFechaPerdido("25-05-2021");


        mascota2.setTipoAnimal("perro");
        mascota2.setLatitude(CAMPUS_LATITUDE);
        mascota2.setLongitude(CAMPUS_LONGITUDE);
        mascota2.setFechaPerdido("26-05-2021");

        mascota3.setTipoAnimal("perro");
        mascota3.setLatitude(SUNSET_LATITUDE);
        mascota3.setLongitude(SUNSET_LONGITUDE);
        mascota3.setFechaPerdido("01-06-2021");


        mascotaList.add(mascota1);
        mascotaList.add(mascota2);
        mascotaList.add(mascota3);

        List<Mascota> result = MascotaListFilter.filter(mascotaList, "gato", "", -1, MEDRANO_LATITUDE, MEDRANO_LONGITUDE,"", "");
        assertEquals( 1, result.size());

        List<Mascota> result2 = MascotaListFilter.filter(mascotaList, "perro", "", -1, CAMPUS_LATITUDE, CAMPUS_LONGITUDE,"", "");
        assertEquals( 2, result2.size());

        List<Mascota> result3 = MascotaListFilter.filter(mascotaList, "conejo", "", -1, SUNSET_LATITUDE, SUNSET_LONGITUDE,"", "");
        assertEquals( 0, result3.size());


        List<Mascota> result4 = MascotaListFilter.filter(mascotaList, "", "", 2, SUBTE_MEDRANO_LATITUDE, SUBTE_MEDRANO_LONGITUDE, "", "");
        assertEquals( 1, result4.size());

        List<Mascota> result5 = MascotaListFilter.filter(mascotaList, "", "", 6, SUBTE_MEDRANO_LATITUDE, SUBTE_MEDRANO_LONGITUDE,  "", "");
        assertEquals( 1, result5.size());

        List<Mascota> result6 = MascotaListFilter.filter(mascotaList, "", "", 60, SUBTE_MEDRANO_LATITUDE, SUBTE_MEDRANO_LONGITUDE,  "", "");
        assertEquals( 3, result6.size());

        List<Mascota> result7 = MascotaListFilter.filter(mascotaList, "", "", 20, SUBTE_MEDRANO_LATITUDE, SUBTE_MEDRANO_LONGITUDE, "", "");
        assertEquals( 2, result7.size());

        List<Mascota> result8 = MascotaListFilter.filter(mascotaList, "", "", 8, CAMPUS_LATITUDE, CAMPUS_LONGITUDE,  "", "");
        assertEquals( 1, result8.size());

        List<Mascota> result9 = MascotaListFilter.filter(mascotaList, "", "", 9, CAMPUS_LATITUDE, CAMPUS_LONGITUDE,  "", "");
        assertEquals( 2, result9.size());


        List<Mascota> result10 = MascotaListFilter.filter(mascotaList, "", "", -1, 0.0f, 0.0f,  "01-05-2016", "25-05-2021");
        assertEquals( 1, result10.size());

        List<Mascota> result11 = MascotaListFilter.filter(mascotaList, "", "", -1, 0.0f, 0.0f,  "01-05-2016", "26-05-2021");
        assertEquals( 2, result11.size());

        List<Mascota> result12 = MascotaListFilter.filter(mascotaList, "", "", -1, 0.0f, 0.0f,  "01-05-2016", "01-07-2021");
        assertEquals( 3, result12.size());

    }
}