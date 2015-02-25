package com.ulargui.healthylinkx_android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class SelectedProviders extends CallService{
    private ArrayList<String[]> outputlist = new ArrayList<String[]> ();

    protected ArrayList<String[]> Output(){return outputlist;}

    SelectedProviders(AsyncResponse a, String npi1, String npi2, String npi3){
        super(a);
		
		String url= "";
        int i=1;
		if (npi1 != null && !npi1.isEmpty()){
			if (!url.isEmpty()) url+="&";
			url+="NPI" + i + "=" + npi1;
            i++;
		}
		if (npi2 != null && !npi2.isEmpty()){
			if (!url.isEmpty()) url+="&";
            url+="NPI" + i + "=" + npi2;
            i++;
		}
		if (npi3 != null && !npi3.isEmpty()){
			if (!url.isEmpty()) url+="&";
            url+="NPI" + i + "=" + npi3;
            i++;
		}

        call( MyAppConstants.BASE_URL + "/shortlist?" + url);
    }	
	
    protected void onPostExecute(String result){
        outputlist.clear();
        String[] transaction = new String[1];

        try {
            JSONObject rootObject = new JSONObject(result);

            transaction[0]=rootObject.getString("Transaction");
            outputlist.add(transaction);

            JSONArray jArray = rootObject.getJSONArray("Providers");
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject oneObject = jArray.getJSONObject(i);

				String[] tmp = new String[5];
                tmp[0] = oneObject.getString("NPI");
                tmp[1] = oneObject.getString("Provider_Full_Name");
                tmp[2] = oneObject.getString("Provider_Full_Street");
                tmp[3] = oneObject.getString("Provider_Full_City");
                tmp[4] = oneObject.getString("Provider_Business_Practice_Location_Address_Telephone_Number");

                outputlist.add(tmp);
            }
        } catch (JSONException e) {
            outputlist.clear();
        } finally {
            super.onPostExecute(result);
        }
    }
}
