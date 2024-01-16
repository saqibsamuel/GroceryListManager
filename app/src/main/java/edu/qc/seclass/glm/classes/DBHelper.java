package edu.qc.seclass.glm.classes;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Random;

import edu.qc.seclass.glm.MainActivity;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "myDB";

    private static final String TABLE_TYPE = "types";
    private static final String TYPE_COL_ID = "id";
    private static final String TYPE_COL_TYPE = "type";


    private static final String TABLE_ITEM = "items";
    private static final String ITEMS_COL_ID = "id";
    private static final String ITEMS_COL_NAME = "name";
    private static final String ITEMS_COL_TYPE = "type";
    private static final String ITEMS_COL_TYPE_ID = "type_id";


    private static final String TABLE_LINE_ITEMS = "line_items";
    private static final String LINE_ITEMS_COL_ID = "id";
    private static final String LINE_ITEMS_COL_ITEM_ID = "item_id";
    private static final String LINE_ITEMS_COL_GL_ID = "gl_id";
    private static final String LINE_ITEMS_COL_QTY = "quantity";
    private static final String LINE_ITEMS_COL_CHECKED = "checked";


    private static final String TABLE_GROCERY_LIST = "grocery_lists";
    private static final String GROCERY_LIST_COL_ID = "id";
    private static final String GROCERY_LIST_COL_NAME = "name";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DBHelper() {
        super(MainActivity.appContext, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TYPE_TABLE = "CREATE TABLE " + TABLE_TYPE + "("
                + TYPE_COL_ID + " INTEGER PRIMARY KEY," + TYPE_COL_TYPE + " TEXT" + ")";


        String CREATE_ITEM_TABLE = "CREATE TABLE " + TABLE_ITEM + "("
                + ITEMS_COL_ID + " INTEGER PRIMARY KEY,"
                + ITEMS_COL_NAME + " TEXT,"
                + ITEMS_COL_TYPE_ID + " INTEGER," +
                " FOREIGN KEY(" + ITEMS_COL_TYPE_ID + ") REFERENCES " + TABLE_TYPE + "(id)" +
                ")";

        String CREATE_LINE_ITEM_TABLE = "CREATE TABLE " + TABLE_LINE_ITEMS + "("
                + LINE_ITEMS_COL_ID + " INTEGER PRIMARY KEY,"
                + LINE_ITEMS_COL_ITEM_ID + " INTEGER,"
                + LINE_ITEMS_COL_QTY + " INTEGER,"
                + LINE_ITEMS_COL_CHECKED + " INTEGER,"
                + LINE_ITEMS_COL_GL_ID + " INTEGER,"
                + " FOREIGN KEY(" + LINE_ITEMS_COL_ITEM_ID + ") REFERENCES " + TABLE_ITEM + "(id),"
                + " FOREIGN KEY(" + LINE_ITEMS_COL_GL_ID + ") REFERENCES " + TABLE_GROCERY_LIST + "(id)"
                + ")";


        String CREATE_GL_TABLE = "CREATE TABLE " + TABLE_GROCERY_LIST + "("
                + GROCERY_LIST_COL_ID + " INTEGER PRIMARY KEY,"
                + GROCERY_LIST_COL_NAME + " TEXT"
                + ")";

        db.execSQL(CREATE_TYPE_TABLE);
        db.execSQL(CREATE_ITEM_TABLE);
        db.execSQL(CREATE_GL_TABLE);
        db.execSQL(CREATE_LINE_ITEM_TABLE);
        loadDefItems(db);
    }

    public void loadDefItems(SQLiteDatabase a) {
        ContentValues tCV = new ContentValues();
        tCV.put(TYPE_COL_TYPE, "fruits");
        a.insert(TABLE_TYPE, null, tCV);

        ContentValues iCV = new ContentValues();
        iCV.put(ITEMS_COL_NAME, "apple");
        iCV.put(ITEMS_COL_TYPE_ID, 1);

        a.insert(TABLE_ITEM, null, iCV);

        ContentValues iCV2 = new ContentValues();
        iCV2.put(ITEMS_COL_NAME, "grapes");
        iCV2.put(ITEMS_COL_TYPE_ID, 1);

        a.insert(TABLE_ITEM, null, iCV2);

        ContentValues tCV2 = new ContentValues();
        tCV2.put(TYPE_COL_TYPE, "soda");
        a.insert(TABLE_TYPE, null, tCV2);

        iCV = new ContentValues();
        iCV.put(ITEMS_COL_NAME, "pepsi");
        iCV.put(ITEMS_COL_TYPE_ID, 2);

        a.insert(TABLE_ITEM, null, iCV);

        iCV2 = new ContentValues();
        iCV2.put(ITEMS_COL_NAME, "coke");
        iCV2.put(ITEMS_COL_TYPE_ID, 2);

        a.insert(TABLE_ITEM, null, iCV2);

        ContentValues tCV3 = new ContentValues();
        tCV3.put(TYPE_COL_TYPE, "dairy");
        a.insert(TABLE_TYPE, null, tCV3);

        iCV = new ContentValues();
        iCV.put(ITEMS_COL_NAME, "milk");
        iCV.put(ITEMS_COL_TYPE_ID, 3);

        a.insert(TABLE_ITEM, null, iCV);

        iCV2 = new ContentValues();
        iCV2.put(ITEMS_COL_NAME, "cheese");
        iCV2.put(ITEMS_COL_TYPE_ID, 3);

        a.insert(TABLE_ITEM, null, iCV2);

        ContentValues tCV4 = new ContentValues();
        tCV4.put(TYPE_COL_TYPE, "cereal");
        a.insert(TABLE_TYPE, null, tCV4);

        iCV = new ContentValues();
        iCV.put(ITEMS_COL_NAME, "cheerios");
        iCV.put(ITEMS_COL_TYPE_ID, 4);

        a.insert(TABLE_ITEM, null, iCV);

        iCV2 = new ContentValues();
        iCV2.put(ITEMS_COL_NAME, "honey nut cheerios");
        iCV2.put(ITEMS_COL_TYPE_ID, 4);

        a.insert(TABLE_ITEM, null, iCV2);


    }

    public void createTestData(SQLiteDatabase a) {

        a.execSQL("DELETE FROM " + TABLE_ITEM + "");
        a.execSQL("DELETE FROM " + TABLE_TYPE + "");
        a.execSQL("DELETE FROM " + TABLE_LINE_ITEMS + "");
        a.execSQL("DELETE FROM " + TABLE_GROCERY_LIST + "");
        ContentValues tCV = new ContentValues();
        tCV.put(TYPE_COL_TYPE, "Fruits");
        a.insert(TABLE_TYPE, null, tCV);

        ContentValues tCV2 = new ContentValues();
        tCV2.put(TYPE_COL_TYPE, "Soda");
        a.insert(TABLE_TYPE, null, tCV2);

        ContentValues iCV = new ContentValues();
        iCV.put(ITEMS_COL_NAME, "Apple");
        iCV.put(ITEMS_COL_TYPE_ID, 0);

        a.insert(TABLE_ITEM, null, iCV);

        ContentValues iCV2 = new ContentValues();
        iCV2.put(ITEMS_COL_NAME, "Grapes");
        iCV2.put(ITEMS_COL_TYPE_ID, 0);

        a.insert(TABLE_ITEM, null, iCV2);

        ContentValues iCV3 = new ContentValues();
        iCV3.put(ITEMS_COL_NAME, "Pepsi");
        iCV3.put(ITEMS_COL_TYPE_ID, 1);

        a.insert(TABLE_ITEM, null, iCV3);

        ContentValues glCV = new ContentValues();

        glCV.put(GROCERY_LIST_COL_NAME, "First List!");
        a.insert(TABLE_GROCERY_LIST, null, glCV);

        ContentValues glCV2 = new ContentValues();

        glCV2.put(GROCERY_LIST_COL_NAME, "Second List!!");
        a.insert(TABLE_GROCERY_LIST, null, glCV2);


        Cursor c = a.rawQuery("SELECT * FROM " + TABLE_ITEM, null);
        while (c.moveToNext()) {
            Item it = new Item(c.getString(1), "");
            it.setId(c.getInt(0));

            Random r = new Random();
            ContentValues cv = new ContentValues();
            cv.put(LINE_ITEMS_COL_ITEM_ID, it.getId());
            cv.put(LINE_ITEMS_COL_QTY, r.nextInt(10));
            cv.put(LINE_ITEMS_COL_CHECKED, 0);
            cv.put(LINE_ITEMS_COL_GL_ID, 1);

            a.insert(TABLE_LINE_ITEMS, null, cv);
        }

        c.moveToLast();
        Item it = new Item(c.getString(1), "");
        it.setId(c.getInt(0));

        Random r = new Random();
        ContentValues cv = new ContentValues();
        cv.put(LINE_ITEMS_COL_ITEM_ID, it.getId());
        cv.put(LINE_ITEMS_COL_QTY, r.nextInt(10));
        cv.put(LINE_ITEMS_COL_CHECKED, 1);
        cv.put(LINE_ITEMS_COL_GL_ID, 2);
        a.insert(TABLE_LINE_ITEMS, null, cv);
    }

    public Cursor getAllLists() {
        SQLiteDatabase s = this.getWritableDatabase();
        Cursor c = s.rawQuery("SELECT gl.id, gl.name, count(li.id) FROM " + TABLE_GROCERY_LIST + " gl LEFT JOIN " + TABLE_LINE_ITEMS + " li ON gl." + GROCERY_LIST_COL_ID + "=li." + LINE_ITEMS_COL_GL_ID + " GROUP BY " + GROCERY_LIST_COL_NAME + " ORDER BY gl." + GROCERY_LIST_COL_ID, null);
        return c;
    }

    public Cursor getLineItemsForList(int i) {
        SQLiteDatabase s = this.getWritableDatabase();
        Cursor c = s.rawQuery("SELECT * FROM " + TABLE_LINE_ITEMS
                + " WHERE " + LINE_ITEMS_COL_GL_ID + "=" + i, null);

        return c;
    }


    public Item getItem(int id) {
        SQLiteDatabase s = this.getWritableDatabase();
        Cursor c = s.rawQuery("SELECT * FROM " + TABLE_ITEM
                + " WHERE " + ITEMS_COL_ID + "=" + id, null);
        String n = "";
        int typeIndex = 0;
        while (c.moveToNext()) {
            n = c.getString(1);
            typeIndex = (c.getInt(2) + 1);
        }

        Cursor c2 = s.rawQuery("SELECT * FROM " + TABLE_TYPE
                + " WHERE " + TYPE_COL_ID + "=" + typeIndex, null);
        String t = "";
        while (c2.moveToNext()) {
            t = c2.getString(1);
        }
        return new Item(n, t);
    }


    public Cursor getAllItems() {
        SQLiteDatabase s = this.getWritableDatabase();
        Cursor c = s.rawQuery("SELECT i.id,name,type FROM items i JOIN types t ON i.type_id = t.id", null);

        return c;
    }


    public int insertNewGL(GroceryList gl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(GROCERY_LIST_COL_NAME, gl.getName());
        long l = db.insert(TABLE_GROCERY_LIST, null, cv);
        return (int) l;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
        onCreate(db);
    }


    public void removeGL(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_GROCERY_LIST + " WHERE " + GROCERY_LIST_COL_ID + "=" + id);
        db.execSQL("DELETE FROM " + TABLE_LINE_ITEMS + " WHERE " + LINE_ITEMS_COL_GL_ID + "=" + id);
    }

    public int insertNewGLI(GroceryLineItem gli, int glId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(LINE_ITEMS_COL_CHECKED, 0);
        cv.put(LINE_ITEMS_COL_QTY, gli.getQuantity());
        cv.put(LINE_ITEMS_COL_ITEM_ID, gli.getItem().getId());
        cv.put(LINE_ITEMS_COL_GL_ID, glId);
        long l = db.insert(TABLE_LINE_ITEMS, null, cv);
        return (int) l;
    }

    public void updateGLIChecked(boolean checked, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LINE_ITEMS_COL_CHECKED, checked);
        db.update(TABLE_LINE_ITEMS, cv, "id=?", new String[]{id + ""});
    }

    public void updateGLName(String name, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(GROCERY_LIST_COL_NAME, name);
        db.update(TABLE_GROCERY_LIST, cv, "id=?", new String[]{id + ""});
    }

    public void updateGLIQuantity(int qty, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LINE_ITEMS_COL_QTY, qty);
        db.update(TABLE_LINE_ITEMS, cv, "id=?", new String[]{id + ""});
    }

    public void removeGLI(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_LINE_ITEMS + " WHERE " + LINE_ITEMS_COL_ID + "=" + id);
    }

    public ArrayList<Item> searchItems(String searchKeyword) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT  i.id,name,type FROM items i JOIN types t ON i.type_id = t.id WHERE  name LIKE '%" + searchKeyword + "%' OR type LIKE '%" + searchKeyword + "%' ORDER BY type", null);
        ArrayList<Item> itemArrayList = new ArrayList<>();

        while (c.moveToNext()) {
            Item it = new Item(c.getString(1), c.getString(2));
            it.setId(c.getInt(0));
            itemArrayList.add(it);
        }
        return itemArrayList;
    }

    public Item insertNewItem(Item it) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_TYPE + " WHERE " + TYPE_COL_TYPE + " = '" + it.getType().toLowerCase().trim() + "'", null);
        if (c.getCount() == 1) {
            c.moveToFirst();
            int i = c.getInt(0);

            ContentValues cv = new ContentValues();
            cv.put(ITEMS_COL_NAME, it.getName().toLowerCase().trim());
            cv.put(ITEMS_COL_TYPE_ID, i);
            long l2 = db.insert(TABLE_ITEM, null, cv);

            Item newItem = new Item(it.getName().toLowerCase().trim(), it.getType().toLowerCase().trim());
            newItem.setId((int) l2);
            return newItem;
        } else {
            ContentValues cv = new ContentValues();
            cv.put(TYPE_COL_TYPE, it.getType().toLowerCase().trim());

            long l = db.insert(TABLE_TYPE, null, cv);
            ContentValues cv2 = new ContentValues();
            cv2.put(ITEMS_COL_NAME, it.getName().toLowerCase().trim());
            cv2.put(ITEMS_COL_TYPE_ID, (int) l);
            long l2 = db.insert(TABLE_ITEM, null, cv2);

            Item i = new Item(it.getName().toLowerCase().trim(), it.getType().toLowerCase().trim());
            i.setId((int) l2);
            return i;
        }

    }
}