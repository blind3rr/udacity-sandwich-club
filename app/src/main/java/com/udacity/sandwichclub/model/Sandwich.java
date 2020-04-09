package com.udacity.sandwichclub.model;

import java.util.List;

public class Sandwich {

    private String mainName;
    private List<String> alsoKnownAs = null;
    private String placeOfOrigin;
    private String description;
    private String image;
    private List<String> ingredients = null;

    /**
     * No args constructor for use in serialization
     */
    public Sandwich() {
    }

    public Sandwich(String mainName, List<String> alsoKnownAs, String placeOfOrigin, String description, String image, List<String> ingredients) {
        this.mainName = mainName;
        this.alsoKnownAs = alsoKnownAs;
        this.placeOfOrigin = placeOfOrigin;
        this.description = description;
        this.image = image;
        this.ingredients = ingredients;
    }

    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public List<String> getAlsoKnownAs() {
        return alsoKnownAs;
    }

    public String getAlsoKnownAsAsString(){
        String ingredientsAsString = "";
        int size = this.alsoKnownAs.size();
        if(size<1){
            ingredientsAsString = "None";
        }else if (size==1){
            ingredientsAsString = "\""+this.alsoKnownAs.get(0)+"\".";
        }else {
            for(int i=0;i<size;i++){
                ingredientsAsString += "\""+this.alsoKnownAs.get(i)+"\"";
                if(i<size-2){
                    ingredientsAsString += ", ";
                }else if(i==size-2){
                    ingredientsAsString += " or ";
                }else{
                    ingredientsAsString += "";
                }
            }
        }
        return ingredientsAsString;
    }

    public void setAlsoKnownAs(List<String> alsoKnownAs) {
        this.alsoKnownAs = alsoKnownAs;
    }

    public String getPlaceOfOrigin() {
        return placeOfOrigin;
    }

    public void setPlaceOfOrigin(String placeOfOrigin) {
        this.placeOfOrigin = placeOfOrigin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public String getIngredientsAsString(){
        String ingredientsAsString = "";
        int size = this.ingredients.size();
        if(size<1){
            ingredientsAsString = "Unknown ingredients";
        }else if (size==1){
            ingredientsAsString = this.ingredients.get(0)+".";
        }else {
            for(int i=0;i<size;i++){
                ingredientsAsString += this.ingredients.get(i);
                if(i<size-2){
                    ingredientsAsString += ", ";
                }else if(i==size-2){
                    ingredientsAsString += " and ";
                }else{
                    ingredientsAsString += "";
                }
            }
        }
        return ingredientsAsString;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
