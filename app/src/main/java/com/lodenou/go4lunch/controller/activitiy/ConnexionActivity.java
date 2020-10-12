package com.lodenou.go4lunch.controller.activitiy;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.lodenou.go4lunch.R;
import com.shobhitpuri.custombuttons.GoogleSignInButton;


public class ConnexionActivity extends AppCompatActivity {

    // 1 - Identifier for Sign-In Activity
    private static final int RC_SIGN_IN = 123;
    private final String TAG = "cycle";
    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;
    private GoogleSignInButton mGoogleSignInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        // INITIATE FIREBASE AUTH
        mAuth = FirebaseAuth.getInstance();

        signInGoogle();
        signInFacebook();
    }

    @Override
    protected void onStart() {
        // Check if user is signed in (non-null) and update UI accordingly.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        // FIREBASE LOGOUT
        FirebaseAuth.getInstance().signOut();
        // GOOGLE LOGOUT
        gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(ConnexionActivity.this, gso);
        googleSignInClient.signOut();
        // FACBOOK LOGOUT
        LoginManager.getInstance().logOut();
        super.onDestroy();
    }

    // GOOGLE SIGN IN CONFIG -----------------------------------------------------------------------
    private void signInGoogle() {
        mAuth = FirebaseAuth.getInstance();
        mGoogleSignInButton = findViewById(R.id.btn_sign_in);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("311367097628-4d5cspo2lpcvmcrvakugfvrdkdjvoico.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
            startActivity(new Intent(this, MainActivity.class));
        }
        mGoogleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(sign, RC_SIGN_IN);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // GOOGLE
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn
                    .getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount signInAccount = signInAccountTask.getResult(ApiException.class);
                AuthCredential authCredential = GoogleAuthProvider
                        .getCredential(signInAccount.getIdToken(), null);
                mAuth.signInWithCredential(authCredential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                Toast.makeText(getApplicationContext(),
                                        "Your Google account is connected to our application",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                    }
                });


            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
        // FACEBOOK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    // FACEBOOK SIGN IN CONFIG ---------------------------------------------------------------------
    private void signInFacebook() {
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        final LoginButton loginButton = findViewById(R.id.login_button);


        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                GoogleSignInOptions gso = new GoogleSignInOptions.
                        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                        build();

                GoogleSignInClient googleSignInClient = GoogleSignIn
                        .getClient(ConnexionActivity.this, gso);
                googleSignInClient.signOut();
                finish();
                startActivity(new Intent(ConnexionActivity.this, MainActivity.class));
                Toast.makeText(ConnexionActivity.this,
                        "You successfully signed-in ", Toast.LENGTH_SHORT).show();
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

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(ConnexionActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
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

}











