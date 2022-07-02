package com.github.fainaaa.controllers;

import com.github.fainaaa.entities.Collection;
import com.github.fainaaa.entities.User;

public class TestingSceneController {
    Collection currentCollection;
    User user;
    public TestingSceneController(User user, Collection collection){
        this.currentCollection = collection;
        this.user = user;

    }
}
