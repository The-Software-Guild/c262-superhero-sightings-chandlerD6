package com.sg.superhero.dao;

import com.sg.superhero.dto.Hero;
import com.sg.superhero.dto.Location;
import com.sg.superhero.dto.Sighting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public class SightingDaoDB implements SightingDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Sighting getSightingById(int id) {
        try {
            final String SELECT_SIGHTING_BY_ID = "SELECT * FROM sighting WHERE sightingId = ?";
            Sighting sighting = jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightingMapper(), id);
            sighting.setHero(getHeroForSighting(sighting.getHeroId()));
            sighting.setLoc(getLocationForSighting(sighting.getLocId()));
            return sighting;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Hero getHeroForSighting(int id){
        final String SELECT_HERO_FOR_SIGHTING = "SELECT h.* FROM hero h "
                + "JOIN sighting s on s.heroId=h.heroId WHERE s.heroId = ?";
        List<Hero> hero = jdbc.query(SELECT_HERO_FOR_SIGHTING, new HeroDaoDB.HeroMapper(), id);
        if(!hero.isEmpty()){
            return hero.get(0);
        }
        else {
            return null;
        }
    }

    private Location getLocationForSighting(int id){
        final String SELECT_LOC_FOR_SIGHTING = "SELECT l.* FROM location l "
                + "JOIN sighting s on s.locId=l.locId WHERE s.locId = ?";
        List<Location> loc = jdbc.query(SELECT_LOC_FOR_SIGHTING, new LocationDaoDB.LocationMapper(), id);
        if(!loc.isEmpty()){
            return loc.get(0);
        }
        else {
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSighting() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM sighting";
        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
        associateSightingsAndHeroesAndLocs(sightings);
        return sightings;
    }

    private void associateSightingsAndHeroesAndLocs(List<Sighting> sightings){
        for(Sighting sighting : sightings){
            sighting.setHero(getHeroForSighting(sighting.getSightingId()));
            sighting.setLoc(getLocationForSighting(sighting.getSightingId()));
        }
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        final String INSERT_SIGHTING = "INSERT INTO sighting(heroId, locId, sightingDate) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_SIGHTING,
                sighting.getHeroId(),
                sighting.getLocId(),
                sighting.getSightingDate());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setSightingId(newId);
        sighting.setHero(getHeroForSighting(sighting.getHeroId()));
        sighting.setLoc(getLocationForSighting(sighting.getLocId()));
        return sighting;
    }

    @Override
    @Transactional
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE sighting SET heroId = ?, locId = ?, " +
                "sightingDate = ? WHERE sightingId = ?";
        jdbc.update(UPDATE_SIGHTING,
                sighting.getHeroId(),
                sighting.getLocId(),
                sighting.getSightingDate(),
                sighting.getSightingId());
        sighting.setHero(getHeroForSighting(sighting.getHeroId()));
        sighting.setLoc(getLocationForSighting(sighting.getLocId()));
    }

    @Override
    public void deleteSightingById(int id) {
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE sightingId = ?";
        jdbc.update(DELETE_SIGHTING, id);
    }


    @Override
    public List<Sighting> getSightingByDate(Timestamp ts){
        final String GET_SIGHTING_BY_DATE = "SELECT * FROM sighting s WHERE s.sightingDate = ?";
        List<Sighting> sightings = jdbc.query(GET_SIGHTING_BY_DATE, new SightingMapper(), ts);
        for(Sighting sighting : sightings){
            sighting.setHero(getHeroForSighting(sighting.getHeroId()));
            sighting.setLoc(getLocationForSighting(sighting.getLocId()));
        }
        return sightings;
    }



    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setSightingId(rs.getInt("sightingId"));
            sighting.setHeroId(rs.getInt("heroId"));
            sighting.setLocId(rs.getInt("locId"));
            sighting.setSightingDate(rs.getTimestamp("sightingDate"));
            return sighting;
        }
    }

}
