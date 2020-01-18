package com.system.software.solutions.sts.model;

import java.io.Serializable;

public enum UserProfileType implements Serializable {

    USER("USER"),
    SALES("SALES"),
    ADMIN("ADMIN");

    String userProfileType;

    private UserProfileType(String userProfileType) {
        this.userProfileType = userProfileType;
    }

    public String getUserProfileType() {
        return userProfileType;
    }

}
