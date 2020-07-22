package com.lodenou.go4lunch;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.shobhitpuri.custombuttons.GoogleSignInButton;



public class ConnexionActivity extends AppCompatActivity {

    // 1 - Identifier for Sign-In Activity
    private static final int RC_SIGN_IN = 123;
    private final String TAG = "cycle";
    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_connexion);
        GoogleSignInButton mSignInButton = findViewById(R.id.btn_sign_in);
        googleBtnOnClick();
        signInFacebook();
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        // Check if user is signed in (non-null) and update UI accordingly.
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        LoginManager.getInstance().logOut();
        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                build();

        GoogleSignInClient googleSignInClient=GoogleSignIn.getClient(ConnexionActivity.this,gso);
        googleSignInClient.signOut();
        super.onDestroy();
    }

    private void googleBtnOnClick() {
        GoogleSignInButton mSignInButton = findViewById(R.id.btn_sign_in);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_sign_in:
                        signInGoogle();
                        break;
                }
            }
        });
    }

    private void signInGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signInFacebook() {
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.login_button);


        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                GoogleSignInOptions gso = new GoogleSignInOptions.
                        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                        build();

                GoogleSignInClient googleSignInClient=GoogleSignIn.getClient(ConnexionActivity.this,gso);
                googleSignInClient.signOut();
                finish();
                startActivity(new Intent(ConnexionActivity.this, MainActivity.class));
                Toast.makeText(ConnexionActivity.this, "You successfully signed-in ", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }
            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });

    }

    public void onClick(View v) {
        Button google = findViewById(R.id.google_button);
        Button fb = (Button) findViewById(R.id.fb);
        GoogleSignInButton googleSignInButton = findViewById(R.id.btn_sign_in);
        LoginButton loginButton = findViewById(R.id.login_button);
        if (v == fb) {
            loginButton.performClick();
        }
        if (v == google) {
            googleSignInButton.performClick();
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateGoogleUI(account);
//            Intent intent = new Intent(ConnexionActivity.this, MainActivity.class);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateGoogleUI(null);
        }
    }

    public void updateGoogleUI(GoogleSignInAccount account) {
        if (account != null) {
            Toast.makeText(this, "You successfully signed-in ", Toast.LENGTH_LONG).show();
            LoginManager.getInstance().logOut();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Failed to sign-in", Toast.LENGTH_LONG).show();
        }
    }
}











