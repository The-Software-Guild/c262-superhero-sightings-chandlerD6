package com.sg.superhero.dao;

import com.sg.superhero.dto.Hero;
import com.sg.superhero.dto.Power;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PowerDaoDBTest {
    @Autowired
    HeroDao heroDao;

    @Autowired
    PowerDao powerDao;

    public PowerDaoDBTest(){

    }

    @BeforeAll
    public static void setUpClass(){

    }

    @AfterAll
    public static void tearDownClass(){

    }

    @BeforeEach
    public void setUp() {
        List<Power> powers = powerDao.getAllPower();
        for (Power power : powers){
            powerDao.deletePowerById(power.getPowerId());
        }

    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    public void testAddAndGetPower(){
        Power power = new Power();
        power.setPowerName("Telekinesis");
        power.setPowerDescription("Control objects with your mind.");
        power = powerDao.addPower(power);

        Power fromDao = powerDao.getPowerById(power.getPowerId());

        assertEquals(power, fromDao);
    }
}
