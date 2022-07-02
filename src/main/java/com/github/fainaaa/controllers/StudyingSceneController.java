package com.github.fainaaa.controllers;

import com.github.fainaaa.entities.Collection;
import com.github.fainaaa.entities.User;

public class StudyingSceneController {
    Collection currentCollection;
    User user;
    public StudyingSceneController(User user, Collection collection){
        this.currentCollection = collection;
        this.user = user;

    }
}
