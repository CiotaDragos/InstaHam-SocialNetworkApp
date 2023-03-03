package com.example.social_network_gradle.repository.database;

import com.example.social_network_gradle.domain.Entity;
import com.example.social_network_gradle.domain.friendship.Friendship;
import com.example.social_network_gradle.domain.friendship.StringPair;
import com.example.social_network_gradle.domain.user.Address;
import com.example.social_network_gradle.domain.user.User;
import com.example.social_network_gradle.domain.validators.Validator;
import com.example.social_network_gradle.repository.Repository;
import com.example.social_network_gradle.repository.RepositoryException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FriendshipDatabaseRepository extends AbstractDatabaseRepository<StringPair, Friendship>{

    public FriendshipDatabaseRepository(Validator<Friendship> validator, Connection connection) {
        super(validator, connection);
    }

    private Friendship friendshipFromResultSet(ResultSet result) throws SQLException {
        return new Friendship(result.getString("first_user"),
                result.getString("second_user"),
                result.getTimestamp("friends_from").toLocalDateTime(),
                result.getInt("status")
        );
    }
    @Override
    public Iterable<Friendship> findAll() throws SQLException {
        String sql = "Select * From friendships";
        PreparedStatement statement = connection.prepareStatement(sql);;
        try {
            ResultSet result = statement.executeQuery();
            List<Friendship> list = new ArrayList<Friendship>();
            while (result.next()) {
                list.add(friendshipFromResultSet(result));
            }
            return list;
            } catch(SQLException e){
                throw new RuntimeException(e);
            }

    }


    @Override
    protected void insertEntity(Friendship entity) throws SQLException {
        String sql="INSERT INTO friendships(first_user,second_user,friends_from,status) VALUES (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, entity.getId().getFirst());
        statement.setString(2, entity.getId().getSecond());
        statement.setTimestamp(3, Timestamp.valueOf(entity.getFriendsFrom()));
        statement.setInt(4, entity.getStatus());
        statement.executeUpdate();
    }

    @Override
    protected Friendship getEntity(StringPair stringPair) throws SQLException, RepositoryException {
        String sql = "Select * From friendships Where first_user=? and second_user =?";
        PreparedStatement statement=connection.prepareStatement(sql);
        statement.setString(1,stringPair.getFirst());
        statement.setString(2,stringPair.getSecond());
        ResultSet result = statement.executeQuery();
        if(result.next())
            return friendshipFromResultSet(result);
        return null;
    }

    @Override
    protected void deleteEntity(StringPair stringPair) throws SQLException {
        String sql="DELETE FROM friendships WHERE first_user=? and second_user =?";
        PreparedStatement statement=connection  .prepareStatement(sql);
        statement.setString(1,stringPair.getFirst());
        statement.setString(2,stringPair.getSecond());
        statement.executeUpdate();
    }

    public void increaseStatus(String a, String b) throws SQLException {
        String sql = "UPDATE friendships SET status=1 WHERE first_user=? and second_user =?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,a);
        statement.setString(2,b);
        statement.executeUpdate();
    }



}
