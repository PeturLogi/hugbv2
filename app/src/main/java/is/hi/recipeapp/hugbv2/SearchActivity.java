package is.hi.recipeapp.hugbv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import static android.R.layout.simple_list_item_2;

public class SearchActivity extends AppCompatActivity {

    // private CheckBox checkBoxCicken, checkBoxBeef, checkBoxfish;
    private Button showRecipe;
    private ListView listView;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        /*
        checkBoxCicken = (CheckBox) findViewById(R.id.checkBoxChicken);
        checkBoxBeef = (CheckBox) findViewById(R.id.checkBoxBeef);
        checkBoxfish = (CheckBox) findViewById(R.id.checkBoxfish);
        */


        showRecipe = findViewById(R.id.showRecipe);
        listView = (ListView) findViewById(R.id.listView);
        listItems = new ArrayList<String>();

        listItems.add("First Item - added on Activity Create");
        listItems.add("Second Item - added on Activity Create");
        listItems.add("Third Item - added on Activity Create");

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listItems);

        //listView.setAdapter(adapter);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setAdapter(adapter);
            }
        };
        showRecipe.setOnClickListener(listener);
    }

}


