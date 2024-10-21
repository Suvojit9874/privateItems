package com.example.demo.model.auth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

enum Authoritires {
    VIEW , ADD_COURSE_OWNER, ADD_USER, WISH, GET_ROLE
}

public class Authority {
    public static List<Authoritires> getAuthorities(Role role) {
        switch (role.name()) {
            case "CANDIDATE":
                return Arrays.asList(Authoritires.VIEW, Authoritires.ADD_USER);
            case "ADMIN":
                return Arrays.asList(Authoritires.ADD_USER, Authoritires.WISH, Authoritires.GET_ROLE);
            case "COURSEOWNER":
                return Arrays.asList(Authoritires.ADD_COURSE_OWNER, Authoritires.VIEW);
            case "PROJECTMANAGER":
                return Arrays.asList(Authoritires.ADD_COURSE_OWNER, Authoritires.VIEW);
            case "ADMINISTRATOR":
                return Arrays.asList(Authoritires.ADD_COURSE_OWNER, Authoritires.VIEW);
            default:
                return new ArrayList<>();
        }
    }


}
