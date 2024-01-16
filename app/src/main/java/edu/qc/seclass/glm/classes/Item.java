package edu.qc.seclass.glm.classes;

import java.io.Serializable;

public class Item implements Serializable {

    private int id;
    private String name;
    private String type;


    public Item() {
    }


    public Item(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return getName() + " ••• Type: " + getType();
    }
}
