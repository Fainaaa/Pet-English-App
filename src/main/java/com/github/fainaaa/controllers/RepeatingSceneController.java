package com.github.fainaaa.controllers;

import com.github.fainaaa.entities.Collection;
import com.github.fainaaa.entities.User;

public class RepeatingSceneController {
    Collection currentCollection;
    User user;
    public RepeatingSceneController(User user, Collection collection){
        this.currentCollection = collection;
        this.user = user;

    }
}
