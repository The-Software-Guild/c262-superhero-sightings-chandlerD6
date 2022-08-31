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
            return jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightingMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSighting() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM sighting";
        return jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
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
        return sighting;
    }

    @Override
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE sighting SET heroId = ?, locId = ?, " +
                "sightingDate = ? WHERE sightingId = ?";
        jdbc.update(UPDATE_SIGHTING,
                sighting.getHeroId(),
                sighting.getLocId(),
                sighting.getSightingDate(),
                sighting.getSightingId());
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
