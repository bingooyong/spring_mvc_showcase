package org.n3r.web.controller.demo;

import java.util.List;

public class User {

    private String firstName;
    private String lastName;
    private String nationality;

    private List<User> childrenUsers;
    private boolean isLocked;

    public User(String firstName, String lastName, String nationality) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
    }

    public User(String firstName, String lastName, String nationality, List<User> childrenUsers, boolean locked) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.childrenUsers = childrenUsers;
        isLocked = locked;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<User> getChildrenUsers() {
        return childrenUsers;
    }

    public void setChildrenUsers(List<User> childrenUsers) {
        this.childrenUsers = childrenUsers;
    }

    public boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(boolean locked) {
        isLocked = locked;
    }
}
