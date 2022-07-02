package com.github.fainaaa.DBEntities;

public enum PhrasesTableColumns {
    TABLE_NAME("Phrases"),
    ID("phrase_id"),
    COLLECTION_ID("collection_id"),
    PHRASE("phrase"),
    TRANSLATION("translation"),
    DESCRIPTION("description");

    private PhrasesTableColumns(String name){
        this.nameInDB = name;
    }
    private String nameInDB;

    public String getNameInDB(){
        return nameInDB;
    }
}
