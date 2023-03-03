package com.example.social_network_gradle.domain.friendship;

//import utils.Constants;

import com.example.social_network_gradle.domain.Entity;

import java.time.LocalDateTime;

/**
 * Represents the friendship between 2 users
 */

public class Friendship extends Entity<StringPair> {

    LocalDateTime friendsFrom;

    int status; //0 if it's not accepted     1 otherwise
    /**
     * Constructor, keeps the IDs sorted alphabetically
     * @param first, String, the first ID
     * @param second, String, the second ID
     */
    public Friendship(String first, String second) {
        super.setId(new StringPair(first, second));
        friendsFrom = LocalDateTime.now();
        status = 0;
    }

    public Friendship(String first, String second, LocalDateTime friendsFrom, int statuss) {
        super.setId(new StringPair(first, second));
        this.friendsFrom = friendsFrom;
        this.status = statuss;
    }

    public LocalDateTime getFriendsFrom() {
        return friendsFrom;
    }

    public int getStatus() { return status; }

    public void removeStatus() { status = 0; }
    public void addStatus() { status = 1; }

    public void setFriendsFrom(LocalDateTime friendsFrom) {
        this.friendsFrom = friendsFrom;
    }

    @Override
    public String toString() {
        return "Friendship{\n" +
                "\tfirst='" + getId().getFirst() + "'\n" +
                "\tsecond='" + getId().getSecond() + "'\n" +
                "\tfriendsFrom='" + this.friendsFrom + "'\n" +
                "\tstatus='" + this.status + "'\n" +
                '}';
    }
}
