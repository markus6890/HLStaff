package com.gmail.markushygedombrowski.staffprofile;

import com.gmail.markushygedombrowski.utils.Sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class StaffProfiles {
    private final Sql sql;
    private final HashMap<UUID, StaffProfile> staffProfileHashMap = new HashMap<>();

    public StaffProfiles(Sql sql) {
        this.sql = sql;
    }

    public void loadStaffProfiles() throws SQLException {
        Connection connection = sql.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM staffprofile");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            UUID uuid = UUID.fromString(resultSet.getString("uuid"));
            boolean vanished = resultSet.getBoolean("vanished");
            boolean commandSpy = resultSet.getBoolean("commandspy");
            boolean buildprotect = resultSet.getBoolean("buildprotect");
            StaffProfile staffProfile = new StaffProfile(name, uuid, buildprotect, commandSpy, vanished);
            staffProfileHashMap.put(uuid, staffProfile);
        }
        sql.closeAllSQL(connection, statement, resultSet);

    }

    public void saveStaffProfiles() throws SQLException {
        Connection connection = sql.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM staffprofile");
        statement.executeUpdate();
        for (StaffProfile staffProfile : staffProfileHashMap.values()) {
            statement = connection.prepareStatement("INSERT INTO staffprofile (UUID, name, vanished, buildprotect) VALUES (?, ?, ?, ?)");
            statement.setString(1, staffProfile.getUuid().toString());
            statement.setString(2, staffProfile.getName());
            statement.setBoolean(3, staffProfile.isVanished());
            statement.setBoolean(4, staffProfile.isBuildprotect());
            statement.executeUpdate();
        }
        sql.closeAllSQL(connection, statement, null);
    }

    public void addStaffProfile(StaffProfile staffProfile) {
        staffProfileHashMap.put(staffProfile.getUuid(), staffProfile);
    }
    public StaffProfile getStaffProfile(UUID uuid) {
        return staffProfileHashMap.get(uuid);
    }




}
