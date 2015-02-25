package com.ulargui.healthylinkx_android;

import android.os.AsyncTask;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import java.io.IOException;
import java.io.InputStream;

     class CallRESTService extends AsyncTask <Void, Void, String> {

        private String url = null;

        protected void call (String u){
             url=u;
        }

        protected String getData(HttpEntity entity) throws IllegalStateException, IOException {
            InputStream in = entity.getContent();
            StringBuffer out = new StringBuffer();
            int n;
			byte[] b = new byte[4096];
			do {
                n = in.read(b);
                if (n>0) out.append(new String(b, 0, n));
				if (isCancelled()) break;
            }while (n>0);
            return out.toString();
        }

        @Override
        protected String doInBackground(Void... params) {
            if(url==null)return new String();

            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpGet httpGet = new HttpGet(url);
            String text = null;
            try {
                //HttpResponse response = httpClient.execute(httpGet, localContext);
                HttpResponse response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                text = getData(entity);
            } catch (Exception e) {
                //return e.getLocalizedMessage();
                return new String();
            }
            return text;
        }

        protected void onPostExecute(String result) {
        }
    }