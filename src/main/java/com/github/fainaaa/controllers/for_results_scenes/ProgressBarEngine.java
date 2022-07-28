package com.github.fainaaa.controllers.for_results_scenes;

import javafx.concurrent.Task;

import java.util.concurrent.TimeUnit;

public class ProgressBarEngine extends Task<Void> {

    private int maxProgressValue;
    private int realProgressValue;
    public ProgressBarEngine(int maxProgressValue, int realProgressValue){
        this.maxProgressValue = maxProgressValue;
        this.realProgressValue = realProgressValue;
    }

    @Override
    protected Void call(){
        moveProgress();
        return null;
    }

    private void moveProgress(){
        int i = 0;
        while(i <= realProgressValue){
            updateProgress(i++, maxProgressValue);
            double resultInPercents = (double)realProgressValue / maxProgressValue * 100;
            updateMessage( Math.round(resultInPercents) + "%");
            try{
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
