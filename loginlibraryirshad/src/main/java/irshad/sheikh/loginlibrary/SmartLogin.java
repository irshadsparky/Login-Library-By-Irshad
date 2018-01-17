package irshad.sheikh.loginlibrary;

import android.content.Context;
import android.content.Intent;

import irshad.sheikh.loginlibrary.vollyrequest.VolleyRequestFacebook;
import irshad.sheikh.loginlibrary.vollyrequest.VolleyRequestGoogle;
import irshad.sheikh.loginlibrary.vollyrequest.VollyRequestSimpleLoginClass;
import irshad.sheikh.loginlibrary.vollyrequest.VollyRequestSimpleSignup;

/**
 * Copyright (c) 2017 Codelight Studios
 * Created by kalyandechiraju on 22/04/17.
 */

public abstract class SmartLogin {

    public abstract void login(SmartLoginConfig config, VollyRequestSimpleLoginClass vollyRequestSimpleLoginClass);

    public abstract void facebook(SmartLoginConfig config, VolleyRequestFacebook vollyRequestSimpleLoginClass);

    public abstract void google(SmartLoginConfig config, VolleyRequestGoogle vollyRequestSimpleLoginClass);

    public abstract void signup(SmartLoginConfig config, VollyRequestSimpleSignup vollyRequestSimpleLoginClass);

    public abstract boolean logout(Context context);

    public abstract void onActivityResult(int requestCode, int resultCode, Intent data, SmartLoginConfig config);

}
