package com.sg.superhero.dao;

import com.sg.superhero.dto.Hero;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class HeroDaoDBTest {

    @Autowired
    HeroDao heroDao;

    public HeroDaoDBTest(){

    }

    @BeforeAll
    public static void setUpClass(){

    }

    @AfterAll
    public static void tearDownClass(){

    }

    @BeforeEach
    public void setUp() {
        List<Hero> heroes = heroDao.getAllHeroes();
        for (Hero hero : heroes){
            heroDao.deleteHeroById(hero.getHeroId());
        }
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    public void testAddAndGetHero(){
        Hero hero = new Hero();
        hero.setHeroName("Thor");
        hero.setHeroDescription("The god of thunder");
        hero.setPower("Lightning");
        hero = heroDao.addHero(hero);

        Hero fromDao = heroDao.getHeroById(hero.getHeroId());

        assertEquals(hero, fromDao);
    }

}
