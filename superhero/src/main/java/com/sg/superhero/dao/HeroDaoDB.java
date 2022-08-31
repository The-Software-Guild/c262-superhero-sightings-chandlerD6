package com.sg.superhero.dao;

import com.sg.superhero.dto.Hero;
import com.sg.superhero.dto.Location;
import com.sg.superhero.dto.Power;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class HeroDaoDB implements HeroDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Hero getHeroById(int id) {
        try {
            final String GET_HERO_BY_ID = "SELECT * FROM hero WHERE heroId = ?";
            Hero hero = jdbc.queryForObject(GET_HERO_BY_ID, new HeroMapper(), id);
            hero.setPower(getPowersForHero(id));
            return hero;
        } catch(DataAccessException ex) {
            return null;
        }
    }

    private List<Power> getPowersForHero(int id) {
        final String SELECT_POWERS_FOR_HERO = "SELECT p.* FROM power p "
                + "JOIN heroPowers hp ON hp.powerId = p.powerId WHERE hp.heroId = ?";
        return jdbc.query(SELECT_POWERS_FOR_HERO, new PowerDaoDB.PowerMapper(), id);
    }

    @Override
    public List<Hero> getAllHeroes() {
        final String GET_ALL_HEROES = "SELECT * FROM hero";
        List<Hero> heroes = jdbc.query(GET_ALL_HEROES, new HeroMapper());
        associatePowers(heroes);
        return heroes;
    }

    private void associatePowers(List<Hero> heroes){
        for (Hero hero : heroes){
            hero.setPower(getPowersForHero(hero.getHeroId()));
        }
    }

    @Override
    @Transactional
    public Hero addHero(Hero hero) {
        final String INSERT_HERO = "INSERT INTO hero(heroName, heroDescription) " +
                "VALUES(?,?)";
        jdbc.update(INSERT_HERO,
                hero.getHeroName(),
                hero.getHeroDescription());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setHeroId(newId);
        insertHeroPower(hero);
        return hero;
    }

    private void insertHeroPower(Hero hero) {
        final String INSERT_HERO_POWER = "INSERT INTO "
                + "heroPowers(powerId, heroId) VALUES(?,?)";
        for(Power power : hero.getPower()) {
            jdbc.update(INSERT_HERO_POWER,
                    power.getPowerId(),
                    hero.getHeroId());
        }
    }

    @Override
    @Transactional
    public void updateHero(Hero hero) {
        final String UPDATE_HERO = "UPDATE hero SET heroName = ?, heroDescription = ? " +
                "WHERE heroId = ?";
        jdbc.update(UPDATE_HERO,
                hero.getHeroName(),
                hero.getHeroDescription(),
                hero.getHeroId());

        final String DELETE_HERO_POWER = "DELETE FROM heroPowers WHERE heroId = ?";
        jdbc.update(DELETE_HERO_POWER, hero.getHeroId());
        insertHeroPower(hero);
    }

    @Override
    @Transactional
    public void deleteHeroById(int id) {
        final String DELETE_SIGHTING = "DELETE st.* FROM sighting st "
                + "JOIN hero h ON st.heroId = h.heroId WHERE h.heroId = ?";
        jdbc.update(DELETE_SIGHTING, id);

        final String DELETE_ORG_MEMBERS = "DELETE om.* FROM orgMembers om "
                + "JOIN hero h ON om.heroId = h.heroId WHERE h.heroId = ?";
        jdbc.update(DELETE_ORG_MEMBERS, id);

        final String DELETE_HERO_POWERS = "DELETE hp.* FROM heroPowers hp "
                + "JOIN hero h ON hp.heroId = h.heroId WHERE h.heroId = ?";
        jdbc.update(DELETE_HERO_POWERS, id);

        final String DELETE_HERO = "DELETE FROM hero WHERE heroId = ?";
        jdbc.update(DELETE_HERO, id);
    }

    @Override
    public List<Hero> getHeroesForSightings(Location loc){
        final String SELECT_HEROES_FROM_SIGHTINGS = "SELECT h.heroId, h.heroName, h.heroDescription "
                + "FROM sighting s JOIN location l ON l.locId=s.locId "
                + "JOIN hero h ON h.heroId=s.heroId "
                + "WHERE l.locId = ?";
        List<Hero> heroes = jdbc.query(SELECT_HEROES_FROM_SIGHTINGS,
                new HeroMapper(), loc.getLocId());
        associatePowers(heroes);
        return heroes;
    }

    public static final class HeroMapper implements RowMapper<Hero> {

        @Override
        public Hero mapRow(ResultSet rs, int index) throws SQLException {
            Hero hero = new Hero();
            hero.setHeroId(rs.getInt("heroId"));
            hero.setHeroName(rs.getString("heroName"));
            hero.setHeroDescription(rs.getString("heroDescription"));

            return hero;
        }
    }

}
