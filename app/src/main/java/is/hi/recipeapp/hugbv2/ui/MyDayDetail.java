package is.hi.recipeapp.hugbv2.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;

import is.hi.recipeapp.hugbv2.R;

public class MyDayDetail extends AppCompatActivity {

    private ListView listView;

    private ArrayList<Object> monday = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_day_detail);

        setupUIViews();
        setupListView();
    }

    private void setupUIViews() {
        listView = (ListView)findViewById(R.id.lvDayDetail);
    }

    private void setupListView() {

        // Hér erum við komin með valin dag úr MyWeekMenu sem strenginn selected_day
        String selected_day = MyWeekMenu.sharedPreferences.getString(MyWeekMenu.SEL_DAY, null);

        if (selected_day.equalsIgnoreCase("Monday")) {

            /*
            listView=(ListView)findViewById(R.id.listView);
            getObjectMonday();
            SimpleAdapter adapter = new SimpleAdapter(this, android.R.layout.simple_list_item_1, monday);
            listView.setAdapter(adapter);
            */

        } else if (selected_day.equalsIgnoreCase("Tuesday")) {

        } else if (selected_day.equalsIgnoreCase("Wednesday")) {

        } else if (selected_day.equalsIgnoreCase("Thursday")) {

        } else if (selected_day.equalsIgnoreCase("Friday")) {

        } else if (selected_day.equalsIgnoreCase("Saturday")) {

        } else if (selected_day.equalsIgnoreCase("Sunday")) {

        }
    }

        // SearchActivity class example:
        // MyDayDetail myDayDetail = new MyDayDetail();
        // Object recipe;
        // myDayDetail.setObjectMonday(recipe);


        private ArrayList<Object> getObjectMonday() {
            return monday;
        }

        private void setObjectMonday(ArrayList<Object> monday) {
            this.monday = monday;
        }

    }

