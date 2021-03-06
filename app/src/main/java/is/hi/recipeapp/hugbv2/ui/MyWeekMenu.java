package is.hi.recipeapp.hugbv2.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.security.PublicKey;

import is.hi.recipeapp.hugbv2.R;

/**
 * Created by Brynjar Árnason on 02/04/2018.
 * HBV601G Hugbúnaðarverkefni 2
 * Háskóli Íslands
 *
 * Birtir val um vikudaga sem innihalda síðan hver um sig matseðil fyrir hvern vikudag.
 */

public class MyWeekMenu extends AppCompatActivity {

    private ListView listView;
    public static SharedPreferences sharedPreferences;
    public static String SEL_DAY;

    /**
     * Smiður
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_week_menu);

        setupUIViews();

        setupListView();
    }

    // Setur upp viðmótið fyrir vikudaga.
    private void setupUIViews() {
        listView = (ListView)findViewById(R.id.lvWeek);
        sharedPreferences = getSharedPreferences("MY DAY", MODE_PRIVATE);
    }

    /**
     * Setur upp lista með gögnum um vikudaga auk Listener sem hlustar eftir því á
     * hvaða vikudag er smellt á og bregst við með opna nýtt avtivity MyDayDetail
     */
    private void setupListView() {
        String[] week = getResources().getStringArray(R.array.Week);

        WeekAdapter adapter = new WeekAdapter(this, R.layout.activity_week_single_item, week);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                switch (position) {
                    case 0: {
                        startActivity(new Intent(MyWeekMenu.this, MyDayDetail.class));
                        sharedPreferences.edit().putString(SEL_DAY, "Monday").apply();
                        break;
                    }
                    case 1: {
                        startActivity(new Intent(MyWeekMenu.this, MyDayDetail.class));
                        sharedPreferences.edit().putString(SEL_DAY, "Tuesday").apply();
                        break;
                    }
                    case 2: {
                        startActivity(new Intent(MyWeekMenu.this, MyDayDetail.class));
                        sharedPreferences.edit().putString(SEL_DAY, "Wednesday").apply();
                        break;
                    }
                    case 3: {
                        startActivity(new Intent(MyWeekMenu.this, MyDayDetail.class));
                        sharedPreferences.edit().putString(SEL_DAY, "Thursday").apply();
                        break;
                    }
                    case 4: {
                        startActivity(new Intent(MyWeekMenu.this, MyDayDetail.class));
                        sharedPreferences.edit().putString(SEL_DAY, "Friday").apply();
                        break;
                    }
                    case 5: {
                        startActivity(new Intent(MyWeekMenu.this, MyDayDetail.class));
                        sharedPreferences.edit().putString(SEL_DAY, "Saturday").apply();
                        break;
                    }
                    case 6: {
                        startActivity(new Intent(MyWeekMenu.this, MyDayDetail.class));
                        sharedPreferences.edit().putString(SEL_DAY, "Sunday").apply();
                        break;
                    }
                }
            }
        });
    }


    // Klasi sem inniheldur aðferð þar sem haldið er utan um mikilvægar breytur fyrir
    // Adapter þegar kemur að því að umreyta gögnum frá Arraylist yfir í item á lista
    public class WeekAdapter extends ArrayAdapter {

        private int resource;
        private LayoutInflater layoutinflater;
        private String[] week = new String[]{};

        public WeekAdapter(Context context, int resource, String[] objects) {
            super(context, resource, objects);
            this.resource = resource;
            this.week = objects;
            layoutinflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        }

        /**
         *  Sækir og geymir gögn úr layout til að umbreyta með adapter til að sýna síðan í Viewlist
         * @param postion
         * @param convertView
         * @param parent
         * @return convertView
         */
        @Override
        public View getView(int postion, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null) {
                holder = new ViewHolder();
                convertView = layoutinflater.inflate(resource, null);
                holder.ivLogo = (LetterImageView) convertView.findViewById(R.id.ivLetter);
                holder.tvWeek = (TextView) convertView.findViewById(R.id.tvWeek);
                convertView.setTag(holder);
            } else {
                    holder = (ViewHolder)convertView.getTag();
                }

                holder.ivLogo.setOval(true);
                holder.ivLogo.setLetter(week[postion].charAt(0));
                holder.tvWeek.setText(week[postion]);

                return convertView;
            }

        // klasi sem heldur utan um breytur sem innhalda mynd og texta
        class ViewHolder{
            private LetterImageView ivLogo;
            private TextView tvWeek;
        }
    }

}
