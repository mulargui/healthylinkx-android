package com.ulargui.healthylinkx_android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

class MyAppConstants {
    public static final String BASE_URL = "http://10.0.2.2:8081";
}


public class Providers extends CallService{
    private ArrayList<String[]> outputlist = new ArrayList<String[]> ();

    protected ArrayList<String[]> Output(){return outputlist;}

    Providers(AsyncResponse a, String zipcode, String lastname, String specialty, String gender, String distance){
        super(a);
		
		String url= "";
		if (zipcode != null && !zipcode.isEmpty()){
			if (!url.isEmpty()) url+="&";
			url+="zipcode="+zipcode;
		}
		if (lastname != null && !lastname.isEmpty()){
			if (!url.isEmpty()) url+="&";
			url+="lastname1="+lastname;
		}
		if (specialty != null && !specialty.isEmpty()){
			if (!url.isEmpty()) url+="&";
			url+="specialty="+specialty;
		}
		if (gender != null && !gender.isEmpty()){
			if (gender == "F" || gender == "M"){
				if (!url.isEmpty()) url+="&";
				url+="gender="+gender;
			}
		}
		if (distance != null && !distance.isEmpty()){
			if (!url.isEmpty()) url+="&";
			url+="distance="+distance;
		}
        call( MyAppConstants.BASE_URL + "/providers?" + url);
    }	
	
    protected void onPostExecute(String result){
        outputlist.clear();

        try {
            JSONArray jArray = new JSONArray(result);

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject oneObject = jArray.getJSONObject(i);
				String[] tmp = new String[4];
                tmp[0] = oneObject.getString("NPI");
                tmp[1] = oneObject.getString("Provider_Full_Name");
                tmp[2] = oneObject.getString("Provider_Full_Street");
                tmp[3] = oneObject.getString("Provider_Full_City");
                outputlist.add(tmp);
            }
        } catch (JSONException e) {
            outputlist.clear();
        } finally {
            super.onPostExecute(result);
        }
    }
}
