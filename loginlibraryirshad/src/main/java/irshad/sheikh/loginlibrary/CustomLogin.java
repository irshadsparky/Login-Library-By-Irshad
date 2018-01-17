package irshad.sheikh.loginlibrary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Map;

import irshad.sheikh.loginlibrary.util.Constants;
import irshad.sheikh.loginlibrary.vollyrequest.VolleyRequestFacebook;
import irshad.sheikh.loginlibrary.vollyrequest.VolleyRequestGoogle;
import irshad.sheikh.loginlibrary.vollyrequest.VollyRequestSimpleLoginClass;
import irshad.sheikh.loginlibrary.vollyrequest.VollyRequestSimpleSignup;

/**
 * Copyright (c) 2016 Codelight Studios
 * Created by kalyandechiraju on 26/09/16.
 */

public class CustomLogin extends SmartLogin {
    @Override
    public void login(SmartLoginConfig config, VollyRequestSimpleLoginClass vollyRequestSimpleLoginClass) {
        Map<String, String> user = config.getCallback().doCustomLogin();
        vollyRequestSimpleLoginClass.setParams(user);
        vollyRequestSimpleLoginClass.setVolleyRequestResponce(config.getCallback());
        vollyRequestSimpleLoginClass.Start();
      /*  if (user != null) {
            // Save the user
//            UserSessionManager.setUserSession(config.getActivity(), user);
//            config.getCallback().onLoginSuccess(user);
        } else {
            config.getCallback().onLoginFailure(new SmartLoginException("Custom login failed", LoginType.CustomLogin));
        }*/
    }

    @Override
    public void facebook(SmartLoginConfig config, VolleyRequestFacebook vollyRequestSimpleLoginClass) {

    }

    @Override
    public void google(SmartLoginConfig config, VolleyRequestGoogle vollyRequestSimpleLoginClass) {

    }

    @Override
    public void signup(SmartLoginConfig config, VollyRequestSimpleSignup vollyRequestSimpleLoginClass) {
      /*  SmartUser user = config.getCallback().doCustomSignup();
        if (user != null) {
            // Save the user
            UserSessionManager.setUserSession(config.getActivity(), user);
            config.getCallback().onLoginSuccess(user);
        } else {
            config.getCallback().onLoginFailure(new SmartLoginException("Custom signup failed", LoginType.CustomLogin));
        }*/
        Map<String, String> user = config.getCallback().doCustomSignup();
        vollyRequestSimpleLoginClass.setParams(user);
        vollyRequestSimpleLoginClass.setVolleyRequestResponce(config.getCallback());
        vollyRequestSimpleLoginClass.Start();
    }

    @Override
    public boolean logout(Context context) {
        try {
            SharedPreferences preferences = context.getSharedPreferences(Constants.USER_PREFS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove(Constants.USER_TYPE);
            editor.remove(Constants.USER_SESSION);
            editor.apply();
            return true;
        } catch (Exception e) {
            Log.e("CustomLogin", e.getMessage());
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data, SmartLoginConfig config) {

    }
}
