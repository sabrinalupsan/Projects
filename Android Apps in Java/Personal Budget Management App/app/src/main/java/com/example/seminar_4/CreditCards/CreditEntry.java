package com.example.seminar_4.CreditCards;


import java.io.Serializable;

public class CreditEntry implements Serializable {
   private String iban, amount, type, category, date;

    public CreditEntry(String iban, String amount, String type, String category, String date)
    {
        this.iban = iban;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.date = date;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount(){return amount;}
    public void setAmount(String amount){this.amount = amount;}

    public String getType(){return type;}
    public void setTpe(String amount){this.amount = amount;}

    public String getCategory(){return category;}
    public void setCategory(String amount){this.amount = amount;}

    public String getDate(){return date;}
    public void setDate(String amount){this.amount = amount;}


    @Override
    public String toString() {
        return "IBAN: " + this.iban  + ", Type: " + this.type +
                ", Category: " + this.category + ", Date: " + this.date + ", Amount: " + this.amount;
    }
}
