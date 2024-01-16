package edu.qc.seclass.glm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuCompat;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import edu.qc.seclass.glm.classes.DBHelper;
import edu.qc.seclass.glm.classes.GroceryList;
import edu.qc.seclass.glm.classes.GroceryListManager;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main:";
    FloatingActionButton fab;
    LinearLayout layout;
    Menu mainMenu;
    boolean inEditMode = false;
    GroceryListManager glm;
    ArrayList<ImageButton> editButtons;
    public static Context appContext;
    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appContext = getApplicationContext();

        dbHelper = new DBHelper();
        glm = new GroceryListManager(dbHelper);
        editButtons = new ArrayList<>();

        fab = findViewById(R.id.AddNewList);
        layout = findViewById(R.id.linearLayoutMain);


        loadMainLayout();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (glm.getGroceryLists().size() < 50) {
                    createNewListDialog();
                } else {
                    Toast.makeText(MainActivity.this, "Ops! Number of lists limit(50) reached!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private View createGroceryListItemView(final int i, String listName) {

        View v = getLayoutInflater().inflate(R.layout.grocery_list_edit_item_layout, null);
        ImageButton deleteButton = v.findViewById(R.id.groceryListItemButton);
        TextView tv = v.findViewById(R.id.groceryListItemTextView);
        if (inEditMode) {
            deleteButton.setVisibility(View.VISIBLE);
        }
        tv.setText(listName);
        editButtons.add(deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createDeleteListDialog(i);
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, SelectedGroceryList.class);
                intent.putExtra("index", i);
                glm.selectGroceryList(i);
                intent.putExtra("glms", glm);
                startActivityForResult(intent, 0);
            }
        });

        return v;
    }

    private void loadMainLayout() {
        layout.removeAllViews();
        editButtons.clear();
        for (final GroceryList gl : glm.getGroceryLists()) {
            int i = glm.getGroceryLists().indexOf(gl);
            View v = createGroceryListItemView(i, gl.toString());

            layout.addView(v);
        }
    }

    private void createNewListDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        final View v = View.inflate(MainActivity.this, R.layout.dialog_rename_list, null);

        dialog.setTitle("Enter List Name");
        dialog.setView(v);


        dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                EditText et = v.findViewById(R.id.dialogSetListName);
                String name = et.getText().toString();
                if (name.length() > 0) {
                    Toast.makeText(MainActivity.this, "Creating new list ...", Toast.LENGTH_SHORT).show();
                    glm.addGroceryList(name);
                    View v = createGroceryListItemView(glm.getGroceryLists().size() - 1, glm.getGroceryLists().get((glm.getGroceryLists().size() - 1)).toString());
                    layout.addView(v);
                } else {
                    Toast.makeText(MainActivity.this, "Grocery List name cannot be empty!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.show();
    }

    private void showDeleteButtons() {

        for (ImageButton b : editButtons) {
            b.setVisibility(View.VISIBLE);
        }
    }


    private void createDeleteListDialog(final int index) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

        dialog.setTitle("Delete List");
        dialog.setMessage("Do you want to delete the list?");

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                glm.removeGroceryList(index);
                loadMainLayout();
                Toast.makeText(MainActivity.this, "Deleting list ...", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "Canceled!", Toast.LENGTH_SHORT).show();

            }
        });
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onBackPressed() {
        if (inEditMode) {
            inEditMode = false;
            mainMenu.getItem(0).setVisible(true);
            mainMenu.getItem(1).setVisible(false);
            loadMainLayout();
        } else
            super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            glm = (GroceryListManager) data.getSerializableExtra("result");
            glm.setDbHelper(dbHelper);
            loadMainLayout();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        mainMenu = menu;
        MenuCompat.setGroupDividerEnabled(menu, true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int i = item.getOrder();
        if (i == 0) {
            inEditMode = !inEditMode;
            if (inEditMode) {
                item.setTitle("Done");
                showDeleteButtons();
            } else {
                item.setTitle("Edit");
                loadMainLayout();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}