package com.udacity.sandwichclub.utils;

import android.util.Log;
import android.util.Pair;

import java.util.HashMap;

class MyJsonRecord extends MyJsonElement {
    //private ArrayList<MyJsonElement> myJsonElements = new ArrayList<MyJsonElement>();
    private HashMap<String, MyJsonElement> myJsonElements = new HashMap<>();
    private MyJsonElement value;

    MyJsonRecord(String json) throws Exception {
        super(json);
        Pair<String, String>[] keyValuePairs = getKeyValuePairs(super.json);
        String key;
        for (Pair<String, String> pair : keyValuePairs) {
            key = trimQuotes(pair.first);
            // Check that key does not already exist
            if (myJsonElements.containsKey(key)) {
                Log.e("MyJsonObject", "Duplicate key \"" + key + "\" in " + super.json);
                throw new Exception("Duplicate key(s) in MyJsonObject constructor");
            }
            switch (getJsonValueType(pair.second)) {
                case INT:
                    value = new MyJsonInteger(pair.second);
                    break;
                case STRING:
                    value = new MyJsonString(pair.second);
                    break;
                case ARRAY:
                    value = new MyJsonStringArray(pair.second);
                    break;
                case RECORD:
                    value = new MyJsonRecord(pair.second);
                    break;
                default:
                    Log.e("MyJsonObject", "Invalid type of value for key: \"" + key + "\" value: " + pair.second);
                    throw new Exception("Invalid type of value in MyJsonObject constructor");
            }
            myJsonElements.put(key, value);
        }
    }

    MyJsonString getMyJsonString(String key){
        MyJsonElement value;

        if(this.myJsonElements.containsKey(key)){
            value = myJsonElements.get(key);
            if(value instanceof MyJsonString){
                Log.d("getMyJsonString","key \""+key+"\" found. Value = "+value.toString());
                return (MyJsonString) value;
            }else{
                Log.w("getMyJsonString()","key's \""+key+"\" value is not type 'MyJsonString'.");
            }
        }else{
            Log.w("getMyJsonString()","key \""+key+"\" does not exist.");
        }
        return null;
    }

    MyJsonRecord getMyJsonRecord(String key){
        MyJsonElement value;

        if(this.myJsonElements.containsKey(key)){
            value = myJsonElements.get(key);
            if(value instanceof MyJsonRecord){
                Log.d("getMyJsonRecord","key \""+key+"\" found. Value = "+value.toString());
                return (MyJsonRecord) value;
            }else{
                Log.w("getMyJsonRecord()","key's \""+key+"\" value is not type 'MyJsonRecord'.");
            }
        }else{
            Log.w("getMyJsonRecord()","key \""+key+"\" does not exist.");
        }
        return null;
    }

    MyJsonStringArray getMyJsonStringArray(String key){
        MyJsonElement value;

        if(this.myJsonElements.containsKey(key)){
            value = myJsonElements.get(key);
            if(value instanceof MyJsonStringArray){
                Log.d("getMyJsonRecord","key \""+key+"\" found. Value = "+value.toString());
                return (MyJsonStringArray) value;
            }else{
                Log.w("getMyJsonRecord()","key's \""+key+"\" value is not type 'MyJsonStringArray'.");
            }
        }else{
            Log.w("getMyJsonRecord()","key \""+key+"\" does not exist.");
        }
        return null;
    }

}
