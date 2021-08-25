package com.cc916647.cmcoffee91.geoquiz;

/**
 * Created by cmcoffee91 on 2/2/18.
 */
public class Question {
    private String statement;
    private boolean answer;

    public void setStatement(String s){
        statement = s;
    }
    public void setAnswer(boolean b){
        answer = b;
    }
    public String getStatement(){
        return statement;
    }
    public boolean getAnswer(){
        return answer;
    }
}