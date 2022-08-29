package com.sg.superhero.dao;

import com.sg.superhero.dto.Hero;
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
            return jdbc.queryForObject(GET_HERO_BY_ID, new HeroMapper(), id);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Hero> getAllHeroes() {
        final String GET_ALL_HEROES = "SELECT * FROM hero";
        return jdbc.query(GET_ALL_HEROES, new HeroMapper());
    }

    @Override
    @Transactional
    public Hero addHero(Hero hero) {
        final String INSERT_HERO = "INSERT INTO hero(heroName, heroDescription, power) " +
                "VALUES(?,?,?)";
        jdbc.update(INSERT_HERO,
                hero.getHeroName(),
                hero.getHeroDescription(),
                hero.getPower());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setHeroId(newId);
        return hero;
    }

    @Override
    public void updateHero(Hero hero) {
        final String UPDATE_TEACHER = "UPDATE hero SET heroName = ?, heroDescription = ?, " +
                "power = ? WHERE heroId = ?";
        jdbc.update(UPDATE_TEACHER,
                hero.getHeroName(),
                hero.getHeroDescription(),
                hero.getPower(),
                hero.getHeroId());
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

        final String DELETE_HERO = "DELETE FROM hero WHERE heroId = ?";
        jdbc.update(DELETE_HERO, id);
    }

    public static final class HeroMapper implements RowMapper<Hero> {

        @Override
        public Hero mapRow(ResultSet rs, int index) throws SQLException {
            Hero hero = new Hero();
            hero.setHeroId(rs.getInt("heroId"));
            hero.setHeroName(rs.getString("heroName"));
            hero.setHeroDescription(rs.getString("heroDescription"));
            hero.setPower(rs.getString("power"));

            return hero;
        }
    }

}
