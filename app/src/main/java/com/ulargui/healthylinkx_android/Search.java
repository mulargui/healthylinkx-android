package com.ulargui.healthylinkx_android;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;

public class Search extends ActionBarActivity
						implements AsyncResponse{

    private Taxonomy conn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        conn = new Taxonomy(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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

   public void RESTServiceCallFinished(){
        ArrayList<String> output=conn.Output();
        conn = null;

		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter adapter = new ArrayAdapter(this,
	        android.R.layout.simple_spinner_dropdown_item , output);
        Spinner specialty = (Spinner) findViewById((R.id.Specialty));
        specialty.setAdapter(adapter);
    }	
	
    public void SearchProviders(View view) {
        Spinner specialty = (Spinner) findViewById((R.id.Specialty));
        EditText lastname = (EditText) findViewById(R.id.LastName);
        EditText zipcode = (EditText) findViewById(R.id.Zipcode);

        RadioGroup gendergroup = (RadioGroup) findViewById(R.id.Gender);
        String gender;
        int genderid = gendergroup.getCheckedRadioButtonId();
        if (genderid == R.id.Female ) gender = "F";
        else if (genderid == R.id.Male ) gender = "M";
        else gender = "N";

        RadioGroup distancegroup = (RadioGroup) findViewById(R.id.Distance);
        String distance;
        int distanceid = distancegroup.getCheckedRadioButtonId();
        if (distanceid == R.id.FiveMiles ) distance = "5";
        else if (distanceid == R.id.TenMiles ) distance = "10";
        else distance = "25";

        // Creating Bundle object
        Bundle b = new Bundle();
        // Storing data into bundle
        b.putString("Specialty", String.valueOf(specialty.getSelectedItem()));
        b.putString("Lastname", lastname.getText().toString());
        b.putString("gender", gender);
        b.putString("zipcode", zipcode.getText().toString());
        b.putString("distance", distance);

        Intent intent = new Intent(this, ProvidersList.class);
        intent.putExtras(b);
        startActivity(intent);
    }
}

