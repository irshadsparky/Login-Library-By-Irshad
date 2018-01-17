package irshad.sheikh.loginlibrary;

import java.util.Map;

import irshad.sheikh.loginlibrary.users.SmartFacebookUser;
import irshad.sheikh.loginlibrary.users.SmartGoogleUser;
import irshad.sheikh.loginlibrary.util.SmartLoginException;

/**
 * Copyright (c) 2017 Codelight Studios
 * Created by kalyandechiraju on 22/04/17.
 */

public interface SmartLoginCallbacks {

    void onLoginSuccess(String user);

    void onSignupSuccess(String user);

    Map<String, String> onFacebookLoginSuccess(SmartFacebookUser user);

    Map<String, String> onGoogleLoginSuccess(SmartGoogleUser user);

    void onLoginFailure(SmartLoginException e);

    Map<String, String> doCustomLogin();

    Map<String, String> doCustomSignup();
}
