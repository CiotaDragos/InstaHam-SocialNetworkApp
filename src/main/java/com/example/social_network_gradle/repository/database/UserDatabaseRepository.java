package com.example.social_network_gradle.repository.database;

import com.example.social_network_gradle.domain.user.Address;
import com.example.social_network_gradle.domain.user.User;
import com.example.social_network_gradle.domain.validators.Validator;
import com.example.social_network_gradle.repository.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDatabaseRepository extends AbstractDatabaseRepository<String, User>{

    public UserDatabaseRepository(Validator<User> validator, Connection connection) {
        super(validator, connection);
    }

    private User userFromResultSet(ResultSet result) throws SQLException {
        return new User(result.getString("user_id"),
                result.getString("first_name"),
                result.getString("last_name"),
                result.getString("email"),
                result.getString("year_of_birth"),
                new Address(result.getString("country"),
                        result.getString("county"),
                        result.getString("city"),
                        result.getString("street"),
                        result.getLong("street_number"))
                );
    }


    @Override
    public Iterable<User> findAll() {
        String sql = "Select * From users";
        PreparedStatement statement= null;
        try {
            statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            List<User> list = new ArrayList<User>();
            while(result.next()) {
                list.add(userFromResultSet(result));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void increaseStatus(String a, String b) throws SQLException {

    }

    @Override
    protected void insertEntity(User entity) throws SQLException {
        String sql="INSERT INTO users(user_id,first_name,last_name,email,year_of_birth,country,county,city,street,street_number) VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, entity.getId());
        statement.setString(2, entity.getFirstName());
        statement.setString(3, entity.getLastName());
        statement.setString(4, entity.getEmail());
        statement.setString(5, entity.getYearOfBirth());
        statement.setString(6, entity.getAddress().getCountry());
        statement.setString(7, entity.getAddress().getCounty());
        statement.setString(8, entity.getAddress().getCity());
        statement.setString(9, entity.getAddress().getStreet());
        statement.setLong(10, entity.getAddress().getStreetNumber());
        statement.executeUpdate();
    }

    @Override
    protected User getEntity(String s) throws SQLException, RepositoryException {
        String sql = "Select * From users Where user_id=?";
        PreparedStatement statement=connection.prepareStatement(sql);
        statement.setString(1,s);
        ResultSet result = statement.executeQuery();
        if(result.next())
            return userFromResultSet(result);
        return null;
    }

    @Override
    protected void deleteEntity(String s) throws SQLException {
        String sql="DELETE FROM users WHERE user_id=?";
        PreparedStatement statement=connection  .prepareStatement(sql);
        statement.setString(1,s);
        statement.executeUpdate();
    }
}
