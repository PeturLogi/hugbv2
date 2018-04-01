package is.hi.recipeapp.hugbv2.ui;

import android.content.Context;
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

public class MyWeekMenu extends AppCompatActivity {

    private ListView listView;
    public static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_week_menu);

        setupUIViews();

        setupListView();
    }

    private void setupUIViews() {
        listView = (ListView)findViewById(R.id.lvWeek);
    }

    private void setupListView() {
        String[] week = getResources().getStringArray(R.array.Week);

        WeekAdapter adapter = new WeekAdapter(this, R.layout.activity_week_single_item, week);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                switch (position) {
                    case 0: break;
                    case 1: break;
                    case 2: break;
                    case 3: break;
                    case 4: break;
                    case 5: break;
                    case 6: break;
                }
            }
        });
    }



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

        class ViewHolder{
            private LetterImageView ivLogo;
            private TextView tvWeek;
        }
    }

}
