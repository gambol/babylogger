package com.rorlig.babylog.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ocpsoft.pretty.time.PrettyTime;
import com.rorlig.babylog.R;
import com.rorlig.babylog.dao.DiaperChangeDao;
import com.rorlig.babylog.model.diaper.DiaperChangeEnum;
import com.rorlig.babylog.model.diaper.DiaperIncident;
import com.rorlig.babylog.ui.activity.InjectableActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by admin on 4/22/14.
 * todo the UI requires considerable skinning....
 */
public class DiaperChangeAdapter extends ArrayAdapter<DiaperChangeDao> {

    private final PrettyTime prettyTime;
    private final SimpleDateFormat simpleDateFormat;
    private Context context;
    private List<DiaperChangeDao> diaperChangeDaoArrayList;


    private static final int TYPE_TEXT = 0;
    private static final int TYPE_IMAGE = 1;
    private String TAG="DiaperChangeAdapter";

    private float aspectRatio = 1;

    private RequestCreator request;

    @Inject
    Picasso picasso;

    private int BITMAP_MAX_HEIGHT = 256;
    private int BITMAP_MAX_WIDTH = 256;



    public DiaperChangeAdapter(Activity activity, int textViewResourceId, List<DiaperChangeDao> diaperChangeDao) {
        super(activity.getApplicationContext(), textViewResourceId, diaperChangeDao);
        Log.d(TAG, "constructor DiaperChangeAdapter");
        this.diaperChangeDaoArrayList = new ArrayList<DiaperChangeDao>(diaperChangeDao);
        this.context = activity.getApplicationContext();
        ((InjectableActivity)activity).inject(this);
        prettyTime = new PrettyTime();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Log.d(TAG, "default time zone 0" + TimeZone.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getDefault());


    }

    @Override
    public View getView( final int position, View convertView, ViewGroup parent ) {
        View view;
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflator.inflate(R.layout.list_item_diaper_change, null);
            viewHolder = new ViewHolder(view);
        } else {
            view = convertView;
        }
        final DiaperChangeDao diaperChangeDao = diaperChangeDaoArrayList.get(position);
//        Log.d(TAG,  event.toString());

        Log.d(TAG, "merchant " + diaperChangeDao);

        viewHolder  = (ViewHolder) view.getTag();
        Log.d(TAG, " date is " + new Date(diaperChangeDao.getDate().getTime()).toString());
        viewHolder.textViewTime.setText(simpleDateFormat.format(new Date(diaperChangeDao.getDate().getTime())));

        setDiaperChangeType(diaperChangeDao, viewHolder);
        setDiaperColor(diaperChangeDao,viewHolder);
        setDiaperIncidentType(diaperChangeDao, viewHolder);

        setPoopTexture(diaperChangeDao, viewHolder);

        setNotesContent(diaperChangeDao, viewHolder);
 //       viewHolder.notesContent.setText(diaperChangeDao.getDiaperChangeNotes());

//        viewHolder.poopColor.setImageDrawable(co);
        return view;
    }

    private void setNotesContent(DiaperChangeDao diaperChangeDao, ViewHolder viewHolder) {
        if (diaperChangeDao.getDiaperChangeNotes() != null && diaperChangeDao.getDiaperChangeNotes().trim().length() > 0) {
            viewHolder.notesContent.setText(diaperChangeDao.getDiaperChangeNotes());
        } else {
            viewHolder.row4.setVisibility(View.GONE);
        }
    }


    private void setPoopTexture(DiaperChangeDao diaperChangeDao, ViewHolder viewHolder) {
        if (diaperChangeDao.getPoopTexture()!=null) {
            viewHolder.textViewPoopTexture.setText(diaperChangeDao.getPoopTexture().toString());
        }
    }

    private void setDiaperIncidentType(DiaperChangeDao diaperChangeDao, ViewHolder viewHolder) {
        if (diaperChangeDao.getDiaperChangeIncidentType()!=null  && diaperChangeDao.getDiaperChangeIncidentType() != DiaperIncident.NONE) {
            viewHolder.incidentDetails.setText(diaperChangeDao.getDiaperChangeIncidentType().toString());
        } else {
            viewHolder.incidentDetails.setVisibility(View.GONE);
            viewHolder.row3.setVisibility(View.GONE);
        }
    }



    private void setDiaperColor(DiaperChangeDao diaperChangeDao, ViewHolder viewHolder) {
        if (diaperChangeDao.getPoopColor()!=null) {
            switch (diaperChangeDao.getPoopColor()) {
                case COLOR_1:
                    viewHolder.poopColor.setBackgroundColor(context.getResources().getColor(R.color.poop_color_1));
                    break;
                case COLOR_2:
                    viewHolder.poopColor.setBackgroundColor(context.getResources().getColor(R.color.poop_color_2));
                    break;
                case COLOR_3:
                    viewHolder.poopColor.setBackgroundColor(context.getResources().getColor(R.color.poop_color_3));
                    break;
                case COLOR_4:
                    viewHolder.poopColor.setBackgroundColor(context.getResources().getColor(R.color.poop_color_4));
                    break;
                case COLOR_5:
                    viewHolder.poopColor.setBackgroundColor(context.getResources().getColor(R.color.poop_color_5));
                    break;
                case COLOR_6:
                    viewHolder.poopColor.setBackgroundColor(context.getResources().getColor(R.color.poop_color_6));
                    break;
                case COLOR_7:
                    viewHolder.poopColor.setBackgroundColor(context.getResources().getColor(R.color.poop_color_7));
                    break;
                case COLOR_8:
                    viewHolder.poopColor.setBackgroundColor(context.getResources().getColor(R.color.poop_color_8));
                    break;

            }
        }

    }

    private void setDiaperChangeType(DiaperChangeDao diaperChangeDao, ViewHolder viewHolder) {
        if (diaperChangeDao.getDiaperChangeEventType()== DiaperChangeEnum.BOTH) {
            viewHolder.diaperWetChecked.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_tick_selected));
            viewHolder.diaperPoopChecked.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_tick_selected));

        } else if (diaperChangeDao.getDiaperChangeEventType()==DiaperChangeEnum.WET){
            viewHolder.diaperWetChecked.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_tick_selected));
           // viewHolder.diaperPoopChecked.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_tick_unselected));
            viewHolder.diaperPoopDisplay.setVisibility(View.GONE);
            viewHolder.diaperPoopChecked.setVisibility(View.GONE);
            viewHolder.row2.setVisibility(View.GONE);
        } else {
            //viewHolder.diaperWetChecked.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_tick_unselected));
            viewHolder.diaperWetDisplay.setVisibility(View.GONE);
            viewHolder.diaperWetChecked.setVisibility(View.GONE);
            viewHolder.diaperPoopChecked.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_tick_selected));
        }
    }


    @Override
    public int getCount() {
        return diaperChangeDaoArrayList.size();
    }

    public void update(List<DiaperChangeDao> diaperChangeDaoArrayList) {
        Log.d(TAG, "update");
        this.diaperChangeDaoArrayList = new ArrayList<DiaperChangeDao>(diaperChangeDaoArrayList);
        notifyDataSetInvalidated();
        notifyDataSetChanged();
    }


    /*
            *  View Holder class for individual list items
            */
    public static class ViewHolder {

//        @InjectView(R.id.card_merchant_icon)
//        ImageView merchantIcon;
//
//        @InjectView(R.id.card_merchant_name)
//        TextView merchantName;
//
////        @InjectView(R.id.merchant_low_value)
////        TextView merchantLowValue

        @InjectView(R.id.diaperWetChecked)
        ImageView diaperWetChecked;

        @InjectView(R.id.diaperWetDisplay)
        TextView diaperWetDisplay;

        @InjectView(R.id.diaperPoopChecked)
        ImageView diaperPoopChecked;

        @InjectView(R.id.diaperPoopDisplay)
        TextView diaperPoopDisplay;

        @InjectView(R.id.diaperChangeTime)
        TextView textViewTime;

        @InjectView(R.id.poopColor)
        ImageView poopColor;

        @InjectView(R.id.poopTexture)
        TextView textViewPoopTexture;

        @InjectView(R.id.notesContent)
        TextView notesContent;

        @InjectView(R.id.incidentDetails)
        TextView incidentDetails;

        @InjectView(R.id.row2)
        LinearLayout row2;

        @InjectView(R.id.row3)
        LinearLayout row3;

        @InjectView(R.id.row4)
        LinearLayout row4;


        public ViewHolder(View view){
            ButterKnife.inject(this, view);
            view.setTag(this);
        }
    }



}
