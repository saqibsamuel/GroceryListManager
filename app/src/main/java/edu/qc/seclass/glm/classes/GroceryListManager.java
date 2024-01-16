package edu.qc.seclass.glm.classes;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

import edu.qc.seclass.glm.MainActivity;

public class GroceryListManager implements Serializable {

    private final ArrayList<GroceryList> groceryLists;
    private GroceryList selectedGroceryList;
    private transient DBHelper dbHelper;

    public GroceryListManager() {
        groceryLists = new ArrayList<>();
    }


    public GroceryListManager(DBHelper db) {
        this.dbHelper = db;
        this.groceryLists = new ArrayList<GroceryList>();
        this.selectedGroceryList = null;

        Cursor c = dbHelper.getAllLists();
        while (c.moveToNext()) {
            GroceryList gl = new GroceryList(dbHelper, c.getString(1));
            gl.setId(c.getInt(0));
            gl.setNoOfLineItems(c.getInt(2));
            groceryLists.add(gl);
        }
    }

    public ArrayList<GroceryList> getGroceryLists() {
        return groceryLists;
    }

    public boolean addGroceryList(String name) {
        if (name == null)
            return false;
        GroceryList gl = new GroceryList(dbHelper, name);
        groceryLists.add(gl);
        int id = dbHelper.insertNewGL(gl);
        gl.setId(id);
        return true;
    }

    public boolean selectGroceryList(int index) {
        if (index >= groceryLists.size() || index < 0)
            return false;
        selectedGroceryList = groceryLists.get(index);
        return true;
    }

    public boolean renameGroceryList(String name) {
        if (selectedGroceryList != null) {
            selectedGroceryList.setName(name);
            return true;
        }
        return false;
    }

    public boolean removeGroceryList(int index) {
        if (index >= groceryLists.size() || index < 0)
            return false;
        int id = groceryLists.get(index).getId();
        groceryLists.remove(index);
        dbHelper.removeGL(id);
        selectedGroceryList = null;
        return true;
    }

    public GroceryList getSelectedGroceryList() {
        return selectedGroceryList;
    }

    public DBHelper getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
}

