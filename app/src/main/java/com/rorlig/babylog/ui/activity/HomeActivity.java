package com.rorlig.babylog.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rorlig.babylog.R;
import com.rorlig.babylog.model.ItemModel;
import com.rorlig.babylog.otto.ItemsSelectedEvent;
import com.rorlig.babylog.ui.fragment.home.HomeFragment;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Created by rorlig on 5/31/15.
 */
public class HomeActivity extends InjectableActivity {

    @Inject
    SharedPreferences preferences;
    private String TAG = "HomeActivity";
    @Inject
    Gson gson;
    private ArrayList<ItemModel> logs;

    private EventListener eventListener = new EventListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        showFragment(HomeFragment.class, "home_fragment", false);
//        String logItems = preferences.getString("logItems", "");
//
//        Log.d(TAG, logItems);
//        if (!logItems.equals("")) {
//
//            logs = gson.fromJson(logItems, new TypeToken<List<ItemModel>>(){}.getType());
//            Log.d(TAG, "logs " + logs);
//        }
    }

    @Override
    public void onStart(){
        super.onStart();
        scopedBus.register(eventListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        scopedBus.unregister(eventListener);
    }

//    private void showDatePickerDialog() {

    // Use the current time as the default values for the picker


    // Create a new instance of DatePickerDialog and return it

//        datePickerDialog.show();
////        DialogFragment newFragment = new DatePickerFragment();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Log.d(TAG, "action_settings");
            startActivity(new Intent(this, PrefsActivity.class));


            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void showFragment(Class<?> paramClass,String paramString, boolean addToStack ){
        Log.d(TAG, "showFragment for " + paramClass);

        FragmentManager localFragmentManager = getSupportFragmentManager();



        Fragment localFragment = localFragmentManager.findFragmentById(R.id.fragment_container);

        if ((localFragment==null)||(!paramClass.isInstance(localFragment))){
            Log.d(TAG, "adding to back stack ");
            try {
                localFragment = (Fragment)paramClass.newInstance();
                if (addToStack) {
                    localFragmentManager.beginTransaction()
                            .add(R.id.fragment_container, localFragment)
                            .addToBackStack("main_screen_stack")
                            .commit();
                } else {
                    localFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, localFragment)
                            .commit();
                }

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


    private class EventListener {
        private EventListener() {
        }

        @Subscribe
        public void onItemsSelectedEvent(ItemsSelectedEvent itemSelectedEvent) {
            preferences.edit().putString("logItems", gson.toJson(itemSelectedEvent.getLogListItem())).commit();
            preferences.edit().putString("name", itemSelectedEvent.getName()).commit();
            preferences.edit().putString("dob", itemSelectedEvent.getDob()).commit();
            showFragment(HomeFragment.class, "home_fragment", false);
        }


    }
}
