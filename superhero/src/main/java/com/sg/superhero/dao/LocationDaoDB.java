package com.sg.superhero.dao;

import com.sg.superhero.dto.Location;
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
public class LocationDaoDB implements LocationDao{

    @Autowired
    JdbcTemplate jdbc;


    @Override
    public Location getLocationById(int id) {
        try {
            final String GET_LOC_BY_ID = "SELECT * FROM location WHERE locId = ?";
            return jdbc.queryForObject(GET_LOC_BY_ID, new LocationMapper(), id);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        final String GET_ALL_LOCS = "SELECT * FROM location";
        return jdbc.query(GET_ALL_LOCS, new LocationMapper());
    }

    @Override
    @Transactional
    public Location addLocation(Location loc) {
        final String INSERT_LOC = "INSERT INTO location(locName, locDescription, locAddress, locLat, locLong) " +
                "VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_LOC,
                loc.getLocName(),
                loc.getLocDescription(),
                loc.getLocAddress(),
                loc.getLocLat(),
                loc.getLocLong());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        loc.setLocId(newId);
        return loc;
    }

    @Override
    public void updateLocation(Location loc) {
        final String UPDATE_LOC = "UPDATE location SET locName = ?, locDescription = ?, " +
                "locAddress = ?, locLat = ?, locLong = ? WHERE id = ?";
        jdbc.update(UPDATE_LOC,
                loc.getLocName(),
                loc.getLocDescription(),
                loc.getLocAddress(),
                loc.getLocLat(),
                loc.getLocLong());
    }

    @Override
    @Transactional
    public void deleteLocationById(int id) {
        final String DELETE_SIGHTING = "DELETE st.* FROM sighting st "
                + "JOIN location l ON st.locId = l.locId WHERE l.locId = ?";
        jdbc.update(DELETE_SIGHTING, id);

        final String DELETE_LOC = "DELETE FROM location WHERE locId = ?";
        jdbc.update(DELETE_LOC, id);
    }

    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location loc = new Location();
            loc.setLocId(rs.getInt("locId"));
            loc.setLocName(rs.getString("locName"));
            loc.setLocDescription(rs.getString("locDescription"));
            loc.setLocAddress(rs.getString("locAddress"));
            loc.setLocLat(rs.getFloat("locLat"));
            loc.setLocLong(rs.getFloat("locLong"));

            return loc;
        }
    }

}
