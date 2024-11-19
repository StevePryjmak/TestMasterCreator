package com.LerningBara.model;

import javafx.scene.Scene;

public abstract class AbstractQuestion {
    protected Scene scene;

    public Scene getScene() {
        return scene;
    }

    public abstract boolean checkAnswer(String answer);
}