package is.hi.recipeapp.hugbv2.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;

import is.hi.recipeapp.hugbv2.R;

/**
 * Created by Brynjar Árnason on 02/04/2018.
 * HBV601G Hugbúnaðarverkefni 2
 * Háskóli Íslands
 *
 * Birtir upplýsingar um uppskrift sem passar við valin vikudag í MyWeekMenu
 */
public class MyDayDetail extends AppCompatActivity {

    private ListView listView; // heldur utan um listView

    private ArrayList<Object> monday = new ArrayList<>(); // Heldur utan um uppskrift fyrir monday

    /**
     * Smiðurinn
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_day_detail);

        setupUIViews();
        setupListView();
    }

    // heldur utan um vikudag í listview
    private void setupUIViews() {
        listView = (ListView)findViewById(R.id.lvDayDetail);
    }

    // Finnur út úr hvaða vikudagur var valin og heldur utan um uppskrift sem tilheyrir
    // þeim vikudagi
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
        // þetta eru aðeins upplýsingar til að geyma til hliðsjónar!!!
        // Þessi klasi er ennþá í vinnslu
        // SearchActivity class example:
        // MyDayDetail myDayDetail = new MyDayDetail();
        // Object recipe;
        // myDayDetail.setObjectMonday(recipe);


        // getter aðferð fyrir uppskrift tengt monday
        private ArrayList<Object> getObjectMonday() {
            return monday;
        }

        // Setter aðferð fyrir uppskrift tengt monday
        private void setObjectMonday(ArrayList<Object> monday) {
            this.monday = monday;
        }

    }

