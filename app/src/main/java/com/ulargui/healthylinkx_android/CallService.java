package com.ulargui.healthylinkx_android;

public class CallService extends CallRESTService{
    private AsyncResponse activity = null;

    CallService(AsyncResponse a){
        activity=a;
    }

    protected void call(String url){
        super.call(url);
        super.execute();
    }

    protected void onPostExecute(String result){
        if(activity != null)
            activity.RESTServiceCallFinished();
    }
}
