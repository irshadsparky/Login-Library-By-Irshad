package studios.codelight.smartlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import irshad.sheikh.loginlibrary.LoginType;
import irshad.sheikh.loginlibrary.SmartLogin;
import irshad.sheikh.loginlibrary.SmartLoginCallbacks;
import irshad.sheikh.loginlibrary.SmartLoginConfig;
import irshad.sheikh.loginlibrary.SmartLoginFactory;
import irshad.sheikh.loginlibrary.UserSessionManager;
import irshad.sheikh.loginlibrary.users.SmartFacebookUser;
import irshad.sheikh.loginlibrary.users.SmartGoogleUser;
import irshad.sheikh.loginlibrary.users.SmartUser;
import irshad.sheikh.loginlibrary.util.SmartLoginException;
import irshad.sheikh.loginlibrary.vollyrequest.VolleyRequestFacebook;
import irshad.sheikh.loginlibrary.vollyrequest.VolleyRequestGoogle;
import irshad.sheikh.loginlibrary.vollyrequest.VollyRequestSimpleLoginClass;
import irshad.sheikh.loginlibrary.vollyrequest.VollyRequestSimpleSignup;


public class MainActivity extends AppCompatActivity implements SmartLoginCallbacks {

    private Button facebookLoginButton, googleLoginButton, customSigninButton, customSignupButton, logoutButton;
    private EditText emailEditText, passwordEditText;
    SmartUser currentUser;
    //GoogleApiClient mGoogleApiClient;
    SmartLoginConfig config;
    SmartLogin smartLogin;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        setListeners();

        config = new SmartLoginConfig(this, this);
        config.setFacebookAppId(getString(R.string.facebook_app_id));
        config.setFacebookPermissions(null);
        config.setGoogleApiClient(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentUser = UserSessionManager.getCurrentUser(this);
        refreshLayout();
    }

    private void refreshLayout() {
        currentUser = UserSessionManager.getCurrentUser(this);
        if (currentUser != null) {
            Log.d("Smart Login", "Logged in user: " + currentUser.toString());
            facebookLoginButton.setVisibility(View.GONE);
            googleLoginButton.setVisibility(View.GONE);
            customSigninButton.setVisibility(View.GONE);
            customSignupButton.setVisibility(View.GONE);
            emailEditText.setVisibility(View.GONE);
            passwordEditText.setVisibility(View.GONE);
            logoutButton.setVisibility(View.VISIBLE);
        } else {
            facebookLoginButton.setVisibility(View.VISIBLE);
            googleLoginButton.setVisibility(View.VISIBLE);
            customSigninButton.setVisibility(View.VISIBLE);
            customSignupButton.setVisibility(View.VISIBLE);
            emailEditText.setVisibility(View.VISIBLE);
            passwordEditText.setVisibility(View.VISIBLE);
            logoutButton.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (smartLogin != null) {
            smartLogin.onActivityResult(requestCode, resultCode, data, config);
        }
    }

    private void setListeners() {
        facebookLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform Facebook login
                //validation here
                VolleyRequestFacebook volleyRequestFacebook = new VolleyRequestFacebook();
                volleyRequestFacebook.setUrl("http://coimbossengineers.com/ohana_api/webservice.php");
                smartLogin = SmartLoginFactory.build(LoginType.Facebook);
                smartLogin.facebook(config, volleyRequestFacebook);
            }
        });

        googleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform Google login
                //validation here
                VolleyRequestGoogle volleyRequestGoogle = new VolleyRequestGoogle();
                smartLogin = SmartLoginFactory.build(LoginType.Google);
                volleyRequestGoogle.setUrl("http://coimbossengineers.com/ohana_api/webservice.php");
                smartLogin.google(config, volleyRequestGoogle);
            }
        });

        customSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform custom sign in
//validation here
                VollyRequestSimpleLoginClass   vollyRequestSimpleLoginClass=new VollyRequestSimpleLoginClass();
                smartLogin = SmartLoginFactory.build(LoginType.CustomLogin);
                vollyRequestSimpleLoginClass.setUrl("http://coimbossengineers.com/ohana_api/webservice.php");
                smartLogin.login(config, vollyRequestSimpleLoginClass);

            }
        });

        customSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform custom sign up
                //validation here
                VollyRequestSimpleSignup VollyRequestSimpleSignup = new VollyRequestSimpleSignup();
                smartLogin = SmartLoginFactory.build(LoginType.CustomLogin);
                VollyRequestSimpleSignup.setUrl("http://coimbossengineers.com/ohana_api/webservice.php");
                smartLogin.signup(config, VollyRequestSimpleSignup);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    if (currentUser instanceof SmartFacebookUser) {
                        smartLogin = SmartLoginFactory.build(LoginType.Facebook);
                    } else if (currentUser instanceof SmartGoogleUser) {
                        smartLogin = SmartLoginFactory.build(LoginType.Google);
                    } else {
                        smartLogin = SmartLoginFactory.build(LoginType.CustomLogin);
                    }
                    boolean result = smartLogin.logout(MainActivity.this);
                    if (result) {
                        refreshLayout();
                        Toast.makeText(MainActivity.this, "User logged out successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void bindViews() {
        facebookLoginButton = (Button) findViewById(R.id.facebook_login_button);
        googleLoginButton = (Button) findViewById(R.id.google_login_button);
        customSigninButton = (Button) findViewById(R.id.custom_signin_button);
        customSignupButton = (Button) findViewById(R.id.custom_signup_button);
        emailEditText = (EditText) findViewById(R.id.email_edittext);
        passwordEditText = (EditText) findViewById(R.id.password_edittext);
        logoutButton = (Button) findViewById(R.id.logout_button);
    }

    @Override
    public void onLoginSuccess(String user) {
        Toast.makeText(this, user, Toast.LENGTH_SHORT).show();
        refreshLayout();
    }

    @Override
    public void onSignupSuccess(String user) {
        Toast.makeText(this, user, Toast.LENGTH_SHORT).show();
        refreshLayout();
    }

    @Override
    public Map<String, String> onFacebookLoginSuccess(SmartFacebookUser user) {
        Map<String, String> map = new HashMap<>();
        map.put("action","login");
        map.put("email",""+user.getEmail());
        map.put("password",""+user.getProfileName());
        Toast.makeText(this, user.getProfileName(), Toast.LENGTH_SHORT).show();
        refreshLayout();
        return map;
    }

    @Override
    public Map<String, String> onGoogleLoginSuccess(SmartGoogleUser user) {
        Map<String, String> map = new HashMap<>();
        map.put("action","login");
        map.put("email",""+user.getEmail());
        map.put("password",""+user.getDisplayName());
        Toast.makeText(this, user.getDisplayName(), Toast.LENGTH_SHORT).show();
        refreshLayout();
        return map;
    }

    @Override
    public void onLoginFailure(SmartLoginException e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public Map<String, String> doCustomLogin() {
       // parameter:action=login,email,password
        Map<String, String> map = new HashMap<>();
        map.put("action","login");
        map.put("email",""+emailEditText.getText().toString());
        map.put("password",""+passwordEditText.getText().toString());
        return map;
    }

    @Override
    public Map<String, String> doCustomSignup() {
        Map<String, String> map = new HashMap<>();
        map.put("action","register");
        map.put("name","nameone");
        map.put("email",""+emailEditText.getText().toString());
        map.put("password",""+passwordEditText.getText().toString());
        return map;
    }


}
