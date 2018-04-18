package com.duytue.finalproject;

import java.util.ArrayList;

/**
 * Created by Phy on 7/26/2017.
 */

public class Cook {
    int id;
    ArrayList<String> instruction;
    ArrayList<String> ingredients;

    public Cook() {
        id = -1;
        instruction = new ArrayList<>();
        ingredients = new ArrayList<>();
    }

    public Cook(int id, ArrayList<String> instruction, ArrayList<String> ingredients) {
        this.id = id;
        this.ingredients = ingredients;
        this.instruction = instruction;
    }

    public int getId() {
        return id;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public ArrayList<String> getInstruction() {
        return instruction;
    }
}
