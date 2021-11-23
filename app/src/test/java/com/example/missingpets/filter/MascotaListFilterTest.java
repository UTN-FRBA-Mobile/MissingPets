package com.example.missingpets.filter;

import static org.junit.Assert.*;

import com.example.missingpets.network.Mascota;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MascotaListFilterTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void calcularAntiguedad() {

    }

    @Test
    public void filter() {
        List<Mascota> mascotaList = new ArrayList<>();
        Mascota mascota1 = new Mascota();
        Mascota mascota2 = new Mascota();
        Mascota mascota3 = new Mascota();

        mascota1.setTipoAnimal("gato");
        mascota2.setTipoAnimal("perro");
        mascota3.setTipoAnimal("perro");


        mascotaList.add(mascota1);
        mascotaList.add(mascota2);
        mascotaList.add(mascota3);

        List<Mascota> result = MascotaListFilter.filter(mascotaList, "gato", "", -1, 30.5f, 43.2f, -1);
        assertEquals( 1, result.size());

        List<Mascota> result2 = MascotaListFilter.filter(mascotaList, "perro", "", -1, 30.5f, 43.2f, -1);
        assertEquals( 2, result2.size());

        List<Mascota> result3 = MascotaListFilter.filter(mascotaList, "conejo", "", -1, 30.5f, 43.2f, -1);
        assertEquals( 0, result3.size());
    }
}