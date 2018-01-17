package irshad.sheikh.loginlibrary;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

import irshad.sheikh.loginlibrary.users.SmartGoogleUser;
import irshad.sheikh.loginlibrary.util.Constants;
import irshad.sheikh.loginlibrary.util.SmartLoginException;
import irshad.sheikh.loginlibrary.util.UserUtil;
import irshad.sheikh.loginlibrary.vollyrequest.VolleyRequestFacebook;
import irshad.sheikh.loginlibrary.vollyrequest.VolleyRequestGoogle;
import irshad.sheikh.loginlibrary.vollyrequest.VollyRequestSimpleLoginClass;
import irshad.sheikh.loginlibrary.vollyrequest.VollyRequestSimpleSignup;

/**
 * Copyright (c) 2016 Codelight Studios
 * Created by kalyandechiraju on 26/09/16.
 */

public class GoogleLogin extends SmartLogin {
    VolleyRequestGoogle vollyRequestSimpleLoginClass;

    @Override
    public void login(@NonNull SmartLoginConfig config, VollyRequestSimpleLoginClass vollyRequestSimpleLoginClass) {

    }

    @Override
    public void facebook(SmartLoginConfig config, VolleyRequestFacebook vollyRequestSimpleLoginClass) {

    }

    @Override
    public void google(SmartLoginConfig config, VolleyRequestGoogle vollyRequestSimpleLoginClass) {
        this.vollyRequestSimpleLoginClass = vollyRequestSimpleLoginClass;
        GoogleApiClient apiClient = config.getGoogleApiClient();
        Activity activity = config.getActivity();

        if (apiClient == null) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestProfile()
                    .build();

            apiClient = new GoogleApiClient.Builder(activity)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }

        ProgressDialog progress = ProgressDialog.show(activity, "", activity.getString(irshad.sheikh.loginlibrary.R.string.logging_holder), true);
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(apiClient);
        activity.startActivityForResult(signInIntent, Constants.GOOGLE_LOGIN_REQUEST);
        progress.dismiss();
    }

    @Override
    public void signup(SmartLoginConfig config, VollyRequestSimpleSignup vollyRequestSimpleLoginClass) {

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
            Log.e("GoogleLogin", e.getMessage());
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data, SmartLoginConfig config) {
        ProgressDialog progress = ProgressDialog.show(config.getActivity(), "", config.getActivity().getString(irshad.sheikh.loginlibrary.R.string.getting_data), true);
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        Log.d("GOOGLE SIGN IN", "handleSignInResult:" + result.isSuccess());
        vollyRequestSimpleLoginClass.setVolleyRequestResponce(config.getCallback());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            SmartGoogleUser googleUser = UserUtil.populateGoogleUser(acct);
            // Save the user
            UserSessionManager.setUserSession(config.getActivity(), googleUser);
            vollyRequestSimpleLoginClass.setParams(config.getCallback().onGoogleLoginSuccess(googleUser));
            vollyRequestSimpleLoginClass.Start(config.getActivity());
            progress.dismiss();
        } else {
//            vollyRequestSimpleLoginClass.getVolleyRequestResponce().onLoginFailure(new SmartLoginException("requestCode-->"+requestCode,LoginType.Google));
            Log.d("GOOGLE SIGN IN", "" + requestCode);
            // Signed out, show unauthenticated UI.
            progress.dismiss();
            config.getCallback().onLoginFailure(new SmartLoginException("Google login failed", LoginType.Google));
        }
    }
}
