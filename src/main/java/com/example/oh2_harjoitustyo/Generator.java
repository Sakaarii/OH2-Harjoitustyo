package com.example.oh2_harjoitustyo;

import java.security.SecureRandom;

public class Generator {
    private final int length;
    private String password;
    boolean useNumbers = true;
    boolean useSymbols = true;
    boolean useCapital = true;
    Generator(int leng, boolean symbols, boolean numbers, boolean capital){
        this.length = leng;
        this.useSymbols = symbols;
        this.useNumbers = numbers;
        this.useCapital = capital;
    }
    public void generatePassword(){
        int leftLimit = 48; // "0"
        int rightLimit = 122; // "z"

        if (!useNumbers&&!useCapital){
            leftLimit = 58; // "first symbol"
        }
        else if(!useNumbers){
            leftLimit = 65; // "A"
        }
        if(useSymbols){
            rightLimit = 126; // "}"
        }
        
        // Secure random makes password 
        SecureRandom random = new SecureRandom();

        if (!useSymbols&&!useCapital){
            // filters out only symbols
            password = random.ints(leftLimit, rightLimit+1)
                    .filter(i -> (i <= 57 || i >= 97))
                    .limit(length)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
        }else if(!useSymbols){
            // filters out only symbols
            password = random.ints(leftLimit, rightLimit+1)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(length)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
        }else if(!useCapital){
            // filters out only capitals
            password = random.ints(leftLimit, rightLimit+1)
                    .filter(i -> (i <= 64 || i >= 91))
                    .limit(length)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
        } else{
            // no filter
            password = random.ints(leftLimit, rightLimit+1)
                    .limit(length)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
        }

    }
    public String getPassword(){
        return password;
    }

    public static void main(String[] args) {
        Generator b = new Generator(20, true,false,true);
        b.generatePassword();
        System.out.println(b.getPassword());
    }
}
