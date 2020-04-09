package com.udacity.sandwichclub.utils;

class MyJsonString extends MyJsonElement {
    private String value;

    MyJsonString(String json) {
        super(json);
        this.value = trimQuotes(json);
    }

    @Override
    public String toString(){
        return this.value;
    }

    public String getValue(){
        return this.value;
    }

}