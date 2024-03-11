package com.example.oh2_harjoitustyo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class PasswordList implements Serializable {
    private ArrayList<String[]> PWList = new ArrayList<String[]>();
    public PasswordList(){}
    public void addToList(String[] add){
        this.PWList.add(add);
    }
    public void removeFromList(String[] i){
        this.PWList.remove(i);
    }
    public void setPWList(ArrayList<String[]> PWList) {
        this.PWList = PWList;
    }

    public ArrayList<String[]> getPWList() {
        return PWList;
    }
    public int AmountOfElements(){
        return PWList.size();
    }

    public String Print() {
        return Arrays.toString(PWList.toArray());
    }
}
