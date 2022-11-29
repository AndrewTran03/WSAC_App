package com.example.wsac_app;

public class Parser {

    public Parser() {
        //default constructor
    }

    public String getTitle(String title) {
        return title;
    }

    public int getTime(String time) {
        int t;
        try {
            t = Integer.parseInt(time);
        }
        catch(NumberFormatException e) {
            t = -1;
        }
        return t;
    }

    //TODO: Handle Number format exceptions for any Doubles or Ints
    public double getCost(String cost) {
        double c;
        try {
            c = Double.parseDouble(cost);
        }
        catch(NumberFormatException e) {
            c = -1.00;
        }
        return c;
    }

    public int getCal(String cal) {
        int l;
        try {
            l = Integer.parseInt(cal);
        }
        catch(NumberFormatException e) {
            l = -1;
        }
        return l;
    }

    public String[] getIngredients(String ingredients) {
        String[] ingreds = ingredients.split("\n");
        return ingreds;
    }

    public String[] getInstructions(String instructions) {
        String[] instrs = instructions.split("\n");
        return instrs;
    }

    //TODO: parse photo to map to photo id integer in a dictionary or hashmap (separate class)
    public int getPhoto(String photo) {
        return 0;
    }

}
