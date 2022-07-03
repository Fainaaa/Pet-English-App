package com.github.fainaaa.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fainaaa.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class JacksonUserWriterReader {
    private static final Logger logger = LogManager.getLogger(JacksonUserWriterReader.class);
    private static File currentUserFile = new File("src/main/resources/com/github/fainaaa/userData/currentUser.json");

    //записывает
    public static void marshall(User user) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(currentUserFile, user);
            logger.info("Marshalling user to .json: successfully");
        } catch (IOException e) {
            logger.error("Marshalling user to .json: FAILED\n" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //читает
    public static User unmarshall() {
        User currentUser = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            currentUser = mapper.readValue(currentUserFile, User.class);
            logger.info("Unmarshalling user from .json: successfully");
        } catch (IOException e) {
            logger.error("Unmarshalling user to .json: FAILED\n" + e.getMessage());
            throw new RuntimeException(e);
        }
        return currentUser;
    }
}
