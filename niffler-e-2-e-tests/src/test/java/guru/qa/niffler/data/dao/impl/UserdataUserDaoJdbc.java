package guru.qa.niffler.data.dao.impl;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.Databases;
import guru.qa.niffler.data.dao.UserdataUserDao;
import guru.qa.niffler.data.entity.spend.SpendEntity;
import guru.qa.niffler.data.entity.user.UserEntity;
import guru.qa.niffler.model.CurrencyValues;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.UUID;


public class UserdataUserDaoJdbc implements UserdataUserDao {

    private static final Config CFG = Config.getInstance();
    private final Connection connection;

    public UserdataUserDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public UserEntity create(UserEntity user) {
            try(PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO user(username, currency, firstname, surname, fullname, photo, photoSmall) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)){
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getCurrency().name());
                ps.setString(3, user.getFirstname());
                ps.setString(4, user.getSurname());
                ps.setString(5, user.getFullname());
                ps.setBytes(6, user.getPhoto());
                ps.setBytes(7, user.getPhotoSmall());

                ps.executeUpdate();

                final UUID generatedKey;
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if(rs.next()) {
                        generatedKey = rs.getObject("id", UUID.class);
                    }
                    else {
                        throw new SQLException("Can't find id in ResultSet");
                    }
                }
                user.setId(generatedKey);
                return user;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    @Override
    public void deleteUser(UserEntity user) {
            try(PreparedStatement ps = connection.prepareStatement("DELETE FROM user WHERE id = ?")) {
                ps.setObject(1, user.getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    @Override
    public Optional<UserEntity> findUserById (UUID id) {
            try(PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM user WHERE id = ?")) {
                ps.setObject(1, id);
                ps.execute();
                try (ResultSet rs = ps.getResultSet()) {
                    if(rs.next()) {
                        UserEntity se = new UserEntity();
                        se.setId(rs.getObject("id", UUID.class));
                        se.setUsername(rs.getString("username"));
                        se.setCurrency(rs.getObject("currency", CurrencyValues.class));
                        se.setFirstname(rs.getString("firstname"));
                        se.setSurname(rs.getString("surname"));
                        se.setFullname(rs.getString("fullname"));
                        se.setPhoto(rs.getBytes("photo"));
                        se.setPhoto(rs.getBytes("photoSmall"));
                        return Optional.of(se);
                    }
                    else {
                        return Optional.empty();
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
            try(PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM user WHERE username = ?")) {
                ps.setObject(1, username);
                ps.execute();
                try (ResultSet rs = ps.getResultSet()) {
                    if(rs.next()) {
                        UserEntity se = new UserEntity();
                        se.setId(rs.getObject("id", UUID.class));
                        se.setUsername(rs.getString("username"));
                        se.setCurrency(rs.getObject("currency", CurrencyValues.class));
                        se.setFirstname(rs.getString("firstname"));
                        se.setSurname(rs.getString("surname"));
                        se.setFullname(rs.getString("fullname"));
                        se.setPhoto(rs.getBytes("photo"));
                        se.setPhoto(rs.getBytes("photoSmall"));
                        return Optional.of(se);
                    }
                    else {
                        return Optional.empty();
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
}
