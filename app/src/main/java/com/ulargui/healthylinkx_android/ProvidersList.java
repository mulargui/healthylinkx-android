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
import android.widget.Toast;

import java.util.ArrayList;


public class ProvidersList extends ActionBarActivity
    implements AsyncResponse{

    private Providers conn = null;
    String[] npi = new String[3];

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
        setContentView(R.layout.activity_providers_list);

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

        String tmp, tmpnpi;
        int i;
        for (String[] s: output) {
            tmp="";tmpnpi="";
            i=0;
            for (String t : s) {
                if (i==0) tmpnpi=t;
                else tmp += t + "\n";
                i++;
            }
            inflatedView = (RelativeLayout) getLayoutInflater().inflate(R.layout.provider_layout, null);
            ((TextView) inflatedView.findViewById(R.id.provider_details)).setText(tmp);
            ((CheckBox) inflatedView.findViewById(R.id.checkBox)).setTag(tmpnpi);
            linearLayout.addView(inflatedView,0);
        }

        //show the buttons
        findViewById(R.id.ShortList).setVisibility(View.VISIBLE);
        findViewById(R.id.NewSearch).setVisibility(View.VISIBLE);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        if(((CheckBox) view).isChecked()){
            for (int i=0; i< npi.length;i++){
                if((npi[i]==null) || npi[i].isEmpty()){
                    npi[i]=(String) view.getTag();
                    return;
                }
            }
            // too many selected
            ((CheckBox) view).setChecked(false);
            Toast.makeText(getApplicationContext(), "You can only select three providers", Toast.LENGTH_SHORT).show();
            return;
        }else{
            for (int i=0; i< npi.length;i++){
                if ((npi[i]!= null) && (npi[i] == (String) view.getTag())) {
                    npi[i]="";
                    return;
                }
            }
            //not found?
        }
    }

    public void SearchAgain(View view) {
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

	public void ShortListProviders(View view) {
        // Creating Bundle object
        Bundle b = new Bundle();

        int found=0;
        for (int i=0; i< npi.length;i++)
            if ((npi[i]!= null) && (!npi[i].isEmpty())){
                b.putString("NPI"+i, npi[i]);
                found ++;
            }

        if (found==0){
            Toast.makeText(getApplicationContext(), "You need to select at least one provider", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, ShortList.class);
        intent.putExtras(b);
        startActivity(intent);
    }
}
