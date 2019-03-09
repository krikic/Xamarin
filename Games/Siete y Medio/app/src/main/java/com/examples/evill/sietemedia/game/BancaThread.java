package com.examples.evill.sietemedia.game;

/**
 * Created by Evill on 30/09/2015.
 */
public class BancaThread extends Thread {

    private JuegoPresenter jp;
    private boolean running = true;


    public BancaThread(JuegoPresenter p){
        jp = p;
    }

    public void setRunning(boolean s){
        running = s;
    }



    @Override
    public void run() {

        while(running){
            jp.bancaObtieneCarta();

            try {
                sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

