package is.hi.recipeapp.hugbv2.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import is.hi.recipeapp.hugbv2.R;

public class MyDayDetail extends AppCompatActivity {

    private ListView listView;

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

        if(selected_day.equalsIgnoreCase("Monday")) {
            //Sækjum vistað recipe fyrir Monday o.s.frv
        }else if (selected_day.equalsIgnoreCase("Tuesday")) {

        } else if (selected_day.equalsIgnoreCase("Wednesday")) {

        } else if (selected_day.equalsIgnoreCase("Thursday")) {

        } else if (selected_day.equalsIgnoreCase("Friday")) {

        } else if (selected_day.equalsIgnoreCase("Saturday")) {

        } else if (selected_day.equalsIgnoreCase("Sunday")) {

        }

        //Vantar aðferð fyrir hvern vikudag sem bæði geymir uppskrift þegar
        //kallað er á hana úr SearchActivity og síðan aðferð fyrir sama dag
        //sem skilar uppskrift þegar aðferðin að ofan(setupListView) kallar á hana.

    }
}
