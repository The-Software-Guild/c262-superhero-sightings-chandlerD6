package com.sg.superhero.dao;

import com.sg.superhero.dto.Power;
import com.sg.superhero.dto.Sighting;
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
public class PowerDaoDB implements PowerDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Power getPowerById(int id) {
        try {
            final String SELECT_POWER_BY_ID = "SELECT * FROM power WHERE powerId = ?";
            return jdbc.queryForObject(SELECT_POWER_BY_ID, new PowerMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Power> getAllPower() {
        final String SELECT_ALL_POWER = "SELECT * FROM power";
        return jdbc.query(SELECT_ALL_POWER, new PowerMapper());
    }

    @Override
    @Transactional
    public Power addPower(Power power) {
        final String INSERT_POWER = "INSERT INTO power(powerName, powerDescription) "
                + "VALUES(?,?)";
        jdbc.update(INSERT_POWER,
                power.getPowerName(),
                power.getPowerDescription());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        power.setPowerId(newId);
        return power;
    }

    @Override
    public void updatePower(Power power) {
        final String UPDATE_POWER = "UPDATE power SET powerName = ?, powerDescription = ? " +
                "WHERE powerId = ?";
        jdbc.update(UPDATE_POWER,
                power.getPowerName(),
                power.getPowerDescription(),
                power.getPowerId());
    }

    @Override
    @Transactional
    public void deletePowerById(int id) {
        final String DELETE_POWERS = "DELETE hp.* FROM heroPowers hp "
                + "JOIN power p ON hp.powerId = p.powerId WHERE p.powerId = ?";
        jdbc.update(DELETE_POWERS, id);

        final String DELETE_POWER = "DELETE FROM power WHERE powerId = ?";
        jdbc.update(DELETE_POWER, id);
    }

    public static final class PowerMapper implements RowMapper<Power> {

        @Override
        public Power mapRow(ResultSet rs, int index) throws SQLException {
            Power power = new Power();
            power.setPowerId(rs.getInt("powerId"));
            power.setPowerName(rs.getString("powerName"));
            power.setPowerDescription(rs.getString("powerDescription"));
            return power;
        }
    }

}
