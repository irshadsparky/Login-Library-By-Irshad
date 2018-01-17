package irshad.sheikh.loginlibrary.vollyrequest;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;


import irshad.sheikh.loginlibrary.LoginType;
import irshad.sheikh.loginlibrary.SmartLoginCallbacks;
import irshad.sheikh.loginlibrary.util.ApplicationUtils;
import irshad.sheikh.loginlibrary.util.SmartLoginException;


/**
 * Created by irshad on 25-12-2017.
 */

public class VollyRequestSimpleLoginClass {
    String tag_json_obj = "json_obj_req";
    String TAG = "VollyRequestSimpleLoginClass";
    public String Url;
    public Map<String, String> params;
    public SmartLoginCallbacks volleyRequestResponce;

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public SmartLoginCallbacks getVolleyRequestResponce() {
        return volleyRequestResponce;
    }

    public void setVolleyRequestResponce(SmartLoginCallbacks volleyRequestResponce) {
        this.volleyRequestResponce = volleyRequestResponce;
    }

    public void Start() {
        if (Url != null && Url.length() > 0) {
            final StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, response.toString());
                            volleyRequestResponce.onSignupSuccess(response);
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    volleyRequestResponce.onLoginFailure(new SmartLoginException(error.getMessage(), LoginType.CustomLogin));
                    // hide the progress dialog

                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Log.e("sendvalue", params.toString());
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    60000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

// Adding request to request queue
            ApplicationUtils.getInstance().

                    addToRequestQueue(stringRequest, tag_json_obj);
        }
        else
        {
            volleyRequestResponce.onLoginFailure(new SmartLoginException("Please Add Url", LoginType.CustomLogin));
        }
    }

}
