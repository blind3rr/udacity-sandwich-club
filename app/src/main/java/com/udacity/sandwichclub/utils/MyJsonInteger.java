package com.udacity.sandwichclub.utils;

class MyJsonInteger extends MyJsonElement{
    private int value;

    MyJsonInteger(String json) {
        super(json);
        value = Integer.parseInt(super.json);
    }

    @Override
    public String toString(){
        return String.valueOf(value);
    }

    public int getValue(){
        return this.value;
    }

}
