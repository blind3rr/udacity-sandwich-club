package com.udacity.sandwichclub.utils;

import android.util.Log;
import android.util.Pair;

import java.util.Arrays;

abstract class MyJsonElement{
    enum JsonParsingPhase{
        KEY,
        KVSEPARATOR,
        VALUE,
        KKSEPARATOR
    }

    enum JsonValueType{
        INT,
        STRING,
        RECORD,
        ARRAY,
        UNKNOWN
    }

    String json;
    //private JsonValueType valueType;

    MyJsonElement(String json){
        this.json = json;
    }

    @Override
    public String toString(){
        return this.json;
    }

    static String trimQuotes(String orig){
        return orig.replaceAll("(\\\")(.*)(\\\")","$2");
    }

    static JsonValueType getJsonValueType(String json){
        //String json = this.json.trim();
        if(json!=null && !json.equals("")){
            if (json.matches("\\d*")){
                Log.d("getJsonValueType","is Int <= "+json);
                return JsonValueType.INT;
            }else if(json.matches("\".*\"")){
                Log.d("getJsonValueType","is String <= "+json);
                return JsonValueType.STRING;
            }else if(json.matches("\\{.*\\}")){
                Log.d("getJsonValueType","is Record <= "+json);
                return JsonValueType.RECORD;
            }else if(json.matches("\\[.*\\]")){
                Log.d("getJsonValueType","is Array <= "+json);
                return JsonValueType.ARRAY;
            }
        }
        Log.d("getValueType","is unknown <= "+json);
        return JsonValueType.UNKNOWN;
    }

    static Pair<String, String>[] getKeyValuePairs(String record) throws Exception{

        JsonParsingPhase phase = JsonParsingPhase.KEY;

        String buffer = "";
        String key = "";
        String value;
        String valueStartedWith = "";
        String valueEndsWith = "";

        boolean lookForEscapeChar = false;
        boolean escapeChar = false;
        int level = 0;

        String keyStartPattern = "[\"]";
        String keyValueSeparatorPattern = "[:]";
        String valueStartPattern = "[\"\\[\\{\\]\\d]";
        String keyKeySeparator = "[,]";

        record = record.replaceAll("(\\{)(.*)(\\})","$2").trim();
        char[] charInput = record.toCharArray();
        String actCharAsStr;
        Pair<String,String>[] returnVal = null;
        Log.d("getKeyValuePairs",record);

        for (int i=0;i<charInput.length;i++)
        {
            actCharAsStr = String.valueOf(charInput[i]);
            switch(phase) {
                case KEY:
                    if(buffer.equals("")){
                        // first char of KEY
                        if(actCharAsStr.matches(keyStartPattern)) {
                            buffer += actCharAsStr;
                        }else{
                            Log.e("getKeyValuePairs","\""+actCharAsStr+"\" does not match pattern:"+keyStartPattern);
                            throw new Exception("Parsing error.");
                        }
                    }else{
                        buffer += actCharAsStr;
                        if(actCharAsStr.matches(keyStartPattern)) {
                            key = buffer;
                            buffer = "";
                            Log.d("getKeyValuePairs","KEY: "+key);
                            phase = JsonParsingPhase.KVSEPARATOR;
                        }
                    }
                    break;
                case KVSEPARATOR:
                    if(actCharAsStr.matches(keyValueSeparatorPattern)){
                        phase = JsonParsingPhase.VALUE;
                    }else{
                        Log.e("getKeyValuePairs","\""+actCharAsStr+"\" does not match pattern:"+keyValueSeparatorPattern);
                        throw new Exception("Parsing error.");
                    }
                    break;
                case VALUE:
                    if(buffer.equals("")){
                        // First char of VALUE
                        if(actCharAsStr.matches(valueStartPattern)){
                            switch(actCharAsStr){
                                case "{":
                                    valueStartedWith = "[\\{]";
                                    valueEndsWith = "[\\}]";
                                    lookForEscapeChar = true;
                                    break;
                                case "\"":
                                    valueStartedWith = "[\"]";
                                    valueEndsWith = "[\"]";
                                    lookForEscapeChar = true;
                                    break;
                                case "[":
                                    valueStartedWith = "[\\[]";
                                    valueEndsWith = "[\\]]";
                                    lookForEscapeChar = true;
                                    break;
                                case "\\d":
                                    valueStartedWith = "";
                                    valueEndsWith = "\\D";
                                    lookForEscapeChar = false;
                                    break;
                                default:
                                    throw new Exception("Parsing error.");
                            }
                            level=1;
                            buffer+=actCharAsStr;
                        }else {
                            Log.e("getKeyValuePairs","\""+actCharAsStr+"\" does not match pattern:"+valueStartPattern);
                            throw new Exception("Parsing error.");
                        }
                    }else{
                        // Following chars of VALUE
                        // escape char '\'
                        if(lookForEscapeChar && actCharAsStr.matches("\\\\") && !escapeChar){
                            escapeChar = true;
                            Log.d("getKeyValuePairs","escape char triggered = "+actCharAsStr);
                        }else{
                            buffer+=actCharAsStr;
                            if(escapeChar){
                                Log.d("getKeyValuePairs","escaped char = "+actCharAsStr);
                                escapeChar = false;
                            }else {
                                if (actCharAsStr.matches(valueEndsWith)) {
                                    level--;
                                } else if (!valueStartedWith.equals("") && actCharAsStr.matches(valueStartedWith)) {
                                    level++;
                                }
                                if (level <= 0) {
                                    if (valueEndsWith.equals("\\D")) {
                                        // Int value, removing last non-digit char from buffer, than moving the index of char array one position back
                                        buffer = buffer.substring(0, buffer.length() - 1);
                                        i--;
                                    }
                                    value = buffer;
                                    buffer = "";
                                    Log.d("getKeyValuePairs", "VALUE: " + value);
                                    if(returnVal==null){
                                        returnVal = new Pair[]{new Pair<>(key,value)};
                                    }else{
                                        returnVal= Arrays.copyOf(returnVal,returnVal.length+1);
                                        returnVal[returnVal.length-1] = new Pair<>(key,value);
                                    }
                                    key = "";
                                    if (i==charInput.length-1){
                                        return returnVal;
                                    }else {
                                        phase = JsonParsingPhase.KKSEPARATOR;
                                    }
                                }
                            }
                        }
                    }
                    break;
                case KKSEPARATOR:
                    if(actCharAsStr.matches(keyKeySeparator)) {
                        phase = JsonParsingPhase.KEY;
                    }else{
                        Log.e("getKeyValuePairs","\""+actCharAsStr+"\" does not match pattern:"+keyKeySeparator);
                        throw new Exception("Parsing error.");
                    }
                    break;
            }
        }
        Log.e("getKeyValuePairs","End of json reached, without value being returned");
        throw new Exception("Parsing error.");
    }

}
