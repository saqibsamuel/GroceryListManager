package edu.qc.seclass.glm.classes;

import android.widget.CheckBox;

import java.io.Serializable;
import java.util.ArrayList;

public class GroceryList implements Serializable {
    private String name;
    private int id;
    private int noOfLineItems;
    private ArrayList<GroceryLineItem> groceryLineItems;
    private transient DBHelper dbHelper;

    public int getNoOfLineItems() {
        return noOfLineItems;
    }

    public void setNoOfLineItems(int noOfLineItems) {
        this.noOfLineItems = noOfLineItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GroceryList(DBHelper dbHelper, String name) {
        this.dbHelper = dbHelper;
        this.groceryLineItems = new ArrayList<GroceryLineItem>();
        this.name = name;
    }

    public ArrayList<GroceryLineItem> getGroceryLineItem() {
        return groceryLineItems;
    }

    public void setGroceryLineItems(ArrayList<GroceryLineItem> groceryLineItem) {
        this.groceryLineItems = groceryLineItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        dbHelper.updateGLName(name, id);
    }

    public boolean updateGroceryLineItem(Item item, int quantity, boolean selected, int id) {
        if (item == null || item.getName() == null || item.getType() == null)
            return false;
        GroceryLineItem gli = new GroceryLineItem(item, quantity, selected);
        gli.setId(id);
        groceryLineItems.add(gli);
        this.setNoOfLineItems(groceryLineItems.size());
        return true;
    }

    public boolean createGroceryLineItem(Item item, int quantity, boolean selected) {
        if (item == null || item.getName() == null || item.getType() == null)
            return false;
        GroceryLineItem gli = new GroceryLineItem(item, quantity, selected);
        int i = dbHelper.insertNewGLI(gli, getId());
        gli.setId(i);
        groceryLineItems.add(gli);
        this.setNoOfLineItems(groceryLineItems.size());
        return true;
    }

    public boolean removeGroceryLineItem(GroceryLineItem gli) {
        if (!groceryLineItems.contains(gli) || gli == null)
            return false;
        int id = gli.getId();
        groceryLineItems.remove(gli);
        this.setNoOfLineItems(groceryLineItems.size());
        dbHelper.removeGLI(id);
        return true;
    }


    public boolean checkAllLineItems(boolean b) {

        for (GroceryLineItem groceryLineItem : groceryLineItems) {
            groceryLineItem.setDbHelper(dbHelper);
            groceryLineItem.setChecked(b);
        }
        return true;
    }

    @Override
    public String toString() {
        return getName() + " ••• " + getNoOfLineItems() + " item(s)";
    }


    public DBHelper getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
}
