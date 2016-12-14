package com.example.denis.mystadium.Model;

import android.content.Context;
import android.graphics.Color;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by denis on 08-12-16.
 */

public class ClassementAdapter extends BaseAdapter{
    private Context context;
    private List<InfoEquipe> liste;

    public ClassementAdapter(Context c, List<InfoEquipe> liste){
        this.context = c;
        this.liste = liste;
    }

    @Override
    public int getCount() {
        return liste.size();
    }

    @Override
    public Object getItem(int position) {
        return liste.get(position);
    }

    @Override
    public long getItemId(int position) {
        return liste.get(position).getIdEquipe();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            TextView place;
            TextView nomEquipe;
            TextView nbrPoint;
            LinearLayout ll;

            if (convertView == null) {
                place = new TextView(context);
                place.setTextSize(15);
                place.setTextColor(Color.RED);
                place.setPadding(0, 25, 0, 25);
                place.setGravity(Gravity.LEFT);
                place.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        (float) 3.0));

                nomEquipe = new TextView(context);
                nomEquipe.setTextSize(15);
                place.setPadding(0, 25, 0, 25);
                nomEquipe.setTextColor(Color.BLACK);
                nomEquipe.setGravity(Gravity.LEFT);
                nomEquipe.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        (float) 4.0));

                nbrPoint = new TextView(context);
                nbrPoint.setTextSize(15);
                place.setPadding(0, 25, 0, 25);
                nbrPoint.setTextColor(Color.BLACK);
                nbrPoint.setGravity(Gravity.RIGHT);
                nbrPoint.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        (float) 3.0));


                ll = new LinearLayout(context);
                ll.setOrientation(LinearLayout.HORIZONTAL);
                ll.setPadding(5, 5, 5, 10);

                place.setText(""+(position+1));
                nomEquipe.setText(liste.get(position).getNomClub());
                nbrPoint.setText(""+liste.get(position).getNbrPoints());

                ll.addView(place);
                ll.addView(nomEquipe);
                ll.addView(nbrPoint);
            }
            else {
                ll = (LinearLayout) convertView;
                place = (TextView) ll.getChildAt(0);
                nomEquipe = (TextView) ll.getChildAt(1);
                nbrPoint = (TextView) ll.getChildAt(2);

                place.setText(""+(position+1));
                nomEquipe.setText(liste.get(position).getNomClub());
                nbrPoint.setText(""+liste.get(position).getNbrPoints());
            }

            return ll;
        }
}
