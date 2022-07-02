package com.github.fainaaa.DBEntities;

public enum CollectionsTableColumns {
    TABLE_NAME("Collections"),
    ID("collection_id"),
    USER_ID("user_id"),
    NAME("name"),
    WORDS_NUMBER("words_number");
    private CollectionsTableColumns(String name){
        this.nameInDB = name;
    }
    private String nameInDB;

    public String getNameInDB(){
        return nameInDB;
    }
}
