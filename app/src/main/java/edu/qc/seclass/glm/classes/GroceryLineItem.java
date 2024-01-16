package edu.qc.seclass.glm.classes;


import java.io.Serializable;

public class GroceryLineItem implements Serializable {

    private Item item;

    private int id;
    private int quantity;
    private boolean checked;
    private transient DBHelper dbHelper;

    public GroceryLineItem() {
    }

    public GroceryLineItem(Item item, int quantity, boolean checked) {
        this.item = item;
        this.quantity = quantity;
        this.checked = checked;
    }

    public DBHelper getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 1)
            quantity = 1;
        this.quantity = quantity;
        dbHelper.updateGLIQuantity(quantity, id);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;


        dbHelper.updateGLIChecked(checked, id);
    }

    public int getId() {
        return id;
    }

    public void setId(int itemID) {
        this.id = itemID;
    }

    @Override
    public String toString() {
        return item.getName() + " ••• Qty:" + quantity;
    }

}
