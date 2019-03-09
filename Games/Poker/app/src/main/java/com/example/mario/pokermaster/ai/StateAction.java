package com.example.mario.pokermaster.ai;

import com.example.mario.pokermaster.actions.Action;
import com.example.mario.pokermaster.util.HandValue;
import com.example.mario.pokermaster.util.HandValueType;

/**
 * Created by Borja on 04/12/2017.
 */

public class StateAction {

    private String estado;

    private HandValueType handRank;

    // 0 - Check/Call
    // 1 - Bet/Raise
    // 2 - Fold
    // If aggression < 50 => max(Call)
    // Else if aggression > 50 => max(Raise)
    // New call and raise actions will be random, Fold new action will be 0
    private int[] action;

    public StateAction(HandValueType handRank) {
        this.handRank = handRank;
        action = new int[3];
    }

    public StateAction(HandValueType handRank, double hs) {
        this.handRank = handRank;
        this.estado = handRank + ":" + hs;
        action = new int[3];
    }

    public StateAction(String sa) {
        String estado = sa.split("#")[0];
        this.estado = estado;
        action = new int[3];
        action[0] = Integer.parseInt(sa.split("#")[1]);
        action[1] = Integer.parseInt(sa.split("#")[2]);
        action[2] = Integer.parseInt(sa.split("#")[3]);
        //TODO TESTING
        //System.out.println(this);
    }

    public HandValueType getHandRank() {
        return handRank;
    }

    public void setHandRank(HandValueType handRank) {
        this.handRank = handRank;
    }

    public int[] getAction() {
        return action;
    }

    public void setAction(int[] action) {
        this.action = action;
    }

    public String toString() {
        return estado + "\t" +  action[0] + "\t" + action[1] + "\t" + action[2];
    }

    public String toSave() {
        return estado + "#" + action[0] + "#" + action[1] + "#" + action[2];
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
