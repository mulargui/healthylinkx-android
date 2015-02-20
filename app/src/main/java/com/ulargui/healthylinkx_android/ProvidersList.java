package com.ulargui.healthylinkx_android;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class ProvidersList extends ActionBarActivity
    implements AsyncResponse{

    private Providers conn = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_providers_list, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle b = getIntent().getExtras();
        if (b == null) return;

        String specialty= b.getString("Specialty");
        String lastname= b.getString("Lastname");
        String gender= b.getString("gender");
        String zipcode= b.getString("zipcode");
        String distance= b.getString("distance");

        conn = new Providers(this, zipcode, lastname, specialty, gender, distance);
    }
	
	public void RESTServiceCallFinished(){
        ArrayList<String[]> output=conn.Output();
        conn = null;

        LinearLayout linearLayout =  (LinearLayout)findViewById(R.id.ProviderList);
        RelativeLayout inflatedView;

        String tmp, npi;
        int i;
        for (String[] s: output) {
            tmp="";npi="";
            i=0;
            for (String t : s) {
                if (i==0) npi=t;
                else tmp += t + "\n";
                i++;
            }
            inflatedView = (RelativeLayout) View.inflate(this, R.layout.provider_layout, null);
            ((TextView) inflatedView.findViewById(R.id.provider_details)).setText(tmp);
            ((CheckBox) inflatedView.findViewById(R.id.checkBox)).setTag(npi);
            linearLayout.addView(inflatedView);
        }
    }
	
	public void ShortListProviders(View view) {
        //LinearLayout l = (LinearLayout) getLayoutInflater().inflate(R.layout.Main, null);
        LinearLayout l = (LinearLayout) findViewById(R.id.ProviderList);
        ViewGroup vg = (ViewGroup)l.getRootView();
        String tag="";
        for (int i = 0; i < vg.getChildCount(); i++){
            View v = (View) vg.getChildAt(i);
            if ((v instanceof CheckBox) && ((CheckBox) v).isChecked())
                tag += (String) v.getTag() + "#";
        }

        TextView answer =  (TextView)findViewById(R.id.Answer);
        answer.setText(tag);
    }
}
