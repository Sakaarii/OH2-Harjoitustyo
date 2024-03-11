package com.example.oh2_harjoitustyo;

import java.security.SecureRandom;
import java.util.Date;

public class Generator {
    private int length;
    private String password;
    private Date date;
    private boolean useNumbers = true;
    private boolean useSymbols = true;
    private boolean useCapital = true;
    Generator(int leng, boolean symbols, boolean numbers, boolean capital){
        this.length = leng;
        this.useSymbols = symbols;
        this.useNumbers = numbers;
        this.useCapital = capital;
    }

    public void setUseCapital(boolean useCapital) {
        this.useCapital = useCapital;
    }
    public void setUseNumbers(boolean useNumbers) {
        this.useNumbers = useNumbers;
    }
    public void setUseSymbols(boolean useSymbols) {
        this.useSymbols = useSymbols;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public String getPassword(){
        return password;
    }
    public Date getDate() {
        return date;
    }

    public void generatePassword(){

        date = new Date();

        int leftLimit = 48; // "0"
        int rightLimit = 122; // "z"

        if (!useNumbers&&!useCapital){
            leftLimit = 58; // "first symbol"
        }
        else if(!useNumbers){
            leftLimit = 58; // "A"
        }
        if(useSymbols){
            rightLimit = 126; // "}"
        }
        
        // Secure random makes password 
        SecureRandom random = new SecureRandom();

        if (!useSymbols&&!useCapital){
            // filters out symbols and capitals
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
}
