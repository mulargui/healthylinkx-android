package com.ulargui.healthylinkx_android;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ShortList extends ActionBarActivity
    implements AsyncResponse {

    private SelectedProviders conn = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_short_list, menu);
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
        setContentView(R.layout.activity_short_list);

        Intent intent = getIntent();
        Bundle b = getIntent().getExtras();
        if (b == null) return;

        String npi1 = b.getString("NPI0");
        String npi2 = b.getString("NPI1");
        String npi3 = b.getString("NPI2");

        //Toast.makeText(getApplicationContext(), "#" + npi1 + "#" + npi2 + "#" + npi3 + "#", Toast.LENGTH_SHORT).show();
        conn = new SelectedProviders(this, npi1, npi2, npi3);
    }

    public void RESTServiceCallFinished() {
        ArrayList<String[]> output = conn.Output();
        conn = null;

        LinearLayout linearLayout =  (LinearLayout)findViewById(R.id.SelectedProviders);

        for (String[] s: output) {
            for (String t : s) {
                // is the transaction number
                if(s.length==1){
                    TextView value = new TextView(this);
                    value.setText("Transaction: " + t);
                    ((LinearLayout) linearLayout).addView(value);
                    break;
                }
                TextView value = new TextView(this);
                value.setText(t);
                ((LinearLayout) linearLayout).addView(value);
            }
            //including a separation line
            TextView value = new TextView(this);
            value.setText("");
            ((LinearLayout) linearLayout).addView(value);
        }

        //show the button
        View b = findViewById(R.id.NewSearch);
        b.setVisibility(View.VISIBLE);
    }

    public void SearchAgain(View view) {
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }
}
