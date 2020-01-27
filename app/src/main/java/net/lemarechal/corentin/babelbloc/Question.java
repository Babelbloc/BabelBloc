package net.lemarechal.corentin.babelbloc;

import java.util.List;

public class Question {

    private String question;
    private List<String> choix;
    private byte type;

    public final static byte TEXT = 0;
    public final static byte QCU = 1;
    public final static byte QCM = 2;
    public final static byte IMAGE = 3;

    public Question(String question, List<String> choix, byte type){
        this.question = question;
        this.choix = choix;
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getChoix() {
        return choix;
    }

    public byte getType() {
        return type;
    }
}
