package is.hi.recipeapp.hugbv2.model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

import is.hi.recipeapp.hugbv2.R;

public class CustomListAdapter extends ArrayAdapter<Matches> {

    private final Activity context;
    private final ArrayList<Matches> items;

    public CustomListAdapter(Activity context, ArrayList<Matches> items) {
        super(context, R.layout.mylist, items);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.items=items;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(items.get(position).getRecipeName());
        Picasso.get().load(items.get(position).getSmallImageUrls()[0]).into(imageView);
        extratxt.setText("Description: " + convertToString(items.get(position).getIngredients()));
        return rowView;

    };

    public String convertToString(String[] array) {
        String value = "";

        for (int i = 0; i < array.length; i++) {
            if (i == array.length-1) {
                value += array[i] + ".";
            } else {
                value += array[i] + ", ";
            }
        }

        return value;
    }
}