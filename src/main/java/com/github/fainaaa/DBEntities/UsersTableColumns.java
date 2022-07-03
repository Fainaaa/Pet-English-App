package com.github.fainaaa.DBEntities;

public enum UsersTableColumns {
    TABLE_NAME("Users"),
    ID("user_id"),
    LOGIN("login"),
    PASSWORD("password"),
    NAME("name");

    private UsersTableColumns(String name) {
        this.nameInDB = name;
    }

    private String nameInDB;

    public String getNameInDB() {
        return nameInDB;
    }
}
