package com.github.fainaaa.controllers.for_intermediate_scenes;

import com.github.fainaaa.Launch;
import com.github.fainaaa.controllers.CollectionsSceneController;
import com.github.fainaaa.controllers.for_studying_scenes.MemorizingSceneController;
import com.github.fainaaa.controllers.for_studying_scenes.RepeatingSceneController;
import com.github.fainaaa.controllers.for_testing_scenes.ChoosingTranslationSceneController;
import com.github.fainaaa.entities.Collection;
import com.github.fainaaa.entities.User;
import com.github.fainaaa.entities.for_grading_test.CommonTestResult;
import com.github.fainaaa.entities.for_grading_test.OnePartOfTestResult;
import com.github.fainaaa.helpers.Scenes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class IntermediateSceneController implements Initializable {
    private static final Logger logger = LogManager.getLogger(IntermediateSceneController.class);
    private static final int NUMBER_OF_PARTS_IN_TEST = 3;

    static final HashMap<CollectionsSceneButtons, String> MESSAGES = new HashMap<>(){{
        this.put(CollectionsSceneButtons.REPEAT_BUTTON, "You will be given English words from chosen collection.Your task is to write Russian translation " +
                "for every word. If you can't write the translation, you will be able to see description " +
                "for every word, which you wrote by yourself during adding this collection. Good luck!");
        this.put(CollectionsSceneButtons.MEMORIZE_BUTTON, "You will be given Russian words from chosen collection.Your task is to write English translation " +
                "for every word. If you can't write the translation, you will be able to see description " +
                "for every word, which you wrote by yourself during adding this collection. Good luck!");
        this.put(CollectionsSceneButtons.TEST_BUTTON, "You will be given the test, consisting of three parts. In the first part you need to write"+
                "translation for either Russian or English words, than in the second part you will be given anagrams" +
                "of English words, you need to write correct word(also in English).Good luck!");
    }};
    static final HashMap<CollectionsSceneButtons, URL> URLS = new HashMap<>(){{
        this.put(CollectionsSceneButtons.REPEAT_BUTTON, Launch.class.getResource("scenes/repeatingScene.fxml"));
        this.put(CollectionsSceneButtons.TEST_BUTTON, Launch.class.getResource("scenes/choosingTranslationScene.fxml"));
        this.put(CollectionsSceneButtons.MEMORIZE_BUTTON, Launch.class.getResource("scenes/memorizingScene.fxml"));
    }};

    User user;
    Collection currentCollection;
    CollectionsSceneButtons buttonInvokedThisScene;
    public IntermediateSceneController(User user, Collection col, CollectionsSceneButtons button){
        this.user = user;
        this.currentCollection = col;
        this.buttonInvokedThisScene = button;
    }

    @FXML
    private Label messageLabel;

    @Override
    public void initialize(URL var1, ResourceBundle var2){
        logger.info("Pressed" + buttonInvokedThisScene.name() +". On intermediate scene.");
        setMessageLabel();
    }
    private void setMessageLabel(){
        messageLabel.setText(MESSAGES.get(buttonInvokedThisScene));
    }
    @FXML
    private void onClickLetsGo(MouseEvent event){
        Object controllerForTheNextScene = getControllerForTheNextScene();
        if(controllerForTheNextScene != null) {
            Scenes.sceneChange(event,
                    URLS.get(buttonInvokedThisScene),
                    controllerForTheNextScene);
        }
        else{
            logger.error("Controller object for the after intermediate scene isn't present.");
        }
    }
    private Object getControllerForTheNextScene(){
        switch(buttonInvokedThisScene){
            case REPEAT_BUTTON:
                return new RepeatingSceneController(user, currentCollection);
            case MEMORIZE_BUTTON:
                return new MemorizingSceneController(user, currentCollection);
            case TEST_BUTTON:
                return new ChoosingTranslationSceneController(user, currentCollection,
                        new CommonTestResult(currentCollection.getPhrasesNumber(),
                                new OnePartOfTestResult[NUMBER_OF_PARTS_IN_TEST]));
            default:
                return null;
        }
    }
    @FXML
    private void onClickGoBack(MouseEvent event){
        URL previousSceneUrl = Launch.class.getResource("scenes/collectionsScene.fxml");
        Scenes.sceneChange(event, previousSceneUrl, new CollectionsSceneController(user));
    }

}
