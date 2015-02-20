package com.ulargui.healthylinkx_android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class Taxonomy extends CallService{
    private ArrayList<String> outputlist = new ArrayList<String> ();

    protected ArrayList<String> Output(){return outputlist;}

    Taxonomy(AsyncResponse a){
        super(a);
        call("http://10.0.2.2:8081" + "/taxonomy");
    }

    protected void onPostExecute(String result){
        outputlist.clear();

        try {
            JSONArray jArray = new JSONArray(result);

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject oneObject = jArray.getJSONObject(i);
                String oneObjectsItem = oneObject.getString("Classification");
                outputlist.add(oneObjectsItem);
            }
        } catch (JSONException e) {
            outputlist.clear();
        } finally {
            super.onPostExecute(result);
        }
    }
}
