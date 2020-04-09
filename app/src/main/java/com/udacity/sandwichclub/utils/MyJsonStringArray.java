package com.udacity.sandwichclub.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

class MyJsonStringArray extends MyJsonArray {
    private ArrayList<String> values = new ArrayList<>();

    MyJsonStringArray(String json) throws Exception{
        super(json);
        JsonParsingPhase phase = JsonParsingPhase.VALUE;

        String buffer = "";
        String value;
        boolean lookForEscapeChar = false;
        boolean escapeChar = false;

        String valueStartEndPattern = "[\"]";
        String separatorPattern = "[,]";

        json = json.replaceAll("(\\[)(.*)(\\])","$2").trim();
        char[] charInput = json.toCharArray();
        String actCharAsStr;
        Log.d("MyJsonStringArray",json);
        for (char c:charInput) {
            actCharAsStr = String.valueOf(c);
            switch(phase) {
                case VALUE:
                    if(buffer.equals("")){
                        // First char of VALUE
                        if(actCharAsStr.matches(valueStartEndPattern)){
                            switch(actCharAsStr){
                                case "\"":
                                    lookForEscapeChar = true;
                                    break;
                                default:
                                    Log.e("MyJsonStringArray","\""+actCharAsStr+"\" does not match pattern:"+valueStartEndPattern);
                                    throw new Exception("Parsing error.");
                            }
                            buffer+=actCharAsStr;
                        }else {
                            Log.e("MyJsonStringArray","\""+actCharAsStr+"\" does not match pattern:"+valueStartEndPattern);
                            throw new Exception("Parsing error.");
                        }
                    }else{
                        // Following chars of VALUE
                        // escape char '\'
                        if(lookForEscapeChar && actCharAsStr.matches("\\\\") && !escapeChar){
                            escapeChar = true;
                            Log.d("MyJsonStringArray","escape char triggered = "+actCharAsStr);
                        }else{
                            buffer+=actCharAsStr;
                            if(escapeChar){
                                Log.d("MyJsonStringArray","escaped char = "+actCharAsStr);
                                escapeChar = false;
                            }else {
                                if (actCharAsStr.matches(valueStartEndPattern)) {
                                    value = trimQuotes(buffer);
                                    buffer = "";
                                    values.add(value);
                                    Log.d("MyJsonStringArray()", "Added value: " + value);
                                    Log.d("MyJsonStringArray()", "Actual array: " + values);
                                    phase = JsonParsingPhase.KKSEPARATOR;
                                }
                            }
                        }
                    }
                    break;
                case KKSEPARATOR:
                    if(actCharAsStr.matches(separatorPattern)){
                        phase = JsonParsingPhase.VALUE;
                    }else{
                        Log.e("MyJsonStringArray","\""+actCharAsStr+"\" does not match pattern: "+separatorPattern);
                        throw new Exception("Parsing error.");
                    }
                    break;
            }
        }
    }

    public int getSize(){
        return this.values.size();
    }

    public String getValueOfIndex(int index){
        int size = this.values.size();
        if(index<0 || index>size-1){
            // Index of boundaries
            Log.e("getValueOfIndex()","Index of boundaries. Index = "+index+"; Size = "+size);
        }else{
            return this.values.get(index);
        }
        return null;
    }

    List<String> getValues(){
        return values;
    }
}
