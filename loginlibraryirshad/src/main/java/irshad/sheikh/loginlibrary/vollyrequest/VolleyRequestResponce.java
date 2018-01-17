package irshad.sheikh.loginlibrary.vollyrequest;

import com.android.volley.VolleyError;

/**
 * Created by irshad on 25-12-2017.
 */

public interface VolleyRequestResponce {
    void onResponse(String response);

    void onErrorResponse(VolleyError response);
}
