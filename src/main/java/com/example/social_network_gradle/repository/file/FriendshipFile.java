package com.example.social_network_gradle.repository.file;

import com.example.social_network_gradle.domain.friendship.Friendship;
import com.example.social_network_gradle.domain.friendship.StringPair;
import com.example.social_network_gradle.domain.user.Address;
import com.example.social_network_gradle.domain.user.User;
import com.example.social_network_gradle.domain.validators.Validator;
import com.example.social_network_gradle.domain.Constants;

import java.time.LocalDateTime;
import java.util.List;

public class FriendshipFile extends AbstractFileRepository<StringPair, Friendship>{

    public FriendshipFile(String fileName, Validator<Friendship> validator) {
        super(fileName, validator);
    }

    @Override
    public Friendship extractEntity(List<String> friendships) {
        String firstFriend = friendships.get(0);
        String secondFriend = friendships.get(1);
        //String date = friendships.get(2);

        //LocalDateTime friendsFrom = LocalDateTime.parse(date, Constants.FORMATTER);
        return new Friendship(firstFriend, secondFriend);
    }

    @Override
    protected String createEntityAsString(Friendship friendship) {
        String firstUser = friendship.getId().getFirst();
        String secondUser = friendship.getId().getSecond();

        return firstUser + ";" + secondUser;
    }
}
