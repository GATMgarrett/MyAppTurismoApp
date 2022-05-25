package com.univalle.myappturismo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import com.google.firebase.components.Lazy;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    int REQUEST_CODE = 200;

    private GoogleApiClient googleApiClient;
    //private SignInButton signInButton;
    private ImageView signInButton;
    private ImageView signInButtonF;

    private CallbackManager callbackManager;

    public static final int SIGN_IN_CODE = 777;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verificarPermisos();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        callbackManager = CallbackManager.Factory.create();

        signInButton = (ImageView) findViewById(R.id.google_btn);
        signInButton.setScrollBarSize(SignInButton.SIZE_WIDE);

        signInButtonF = (ImageView) findViewById(R.id.facebook_btn);
        //signInButtonF.setScreenReaderFocusable(Arrays.asList("email"));

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    goMainScreen();
                }
            }
        };
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


    }

    private void verificarPermisos() {
        int PermisosInternet =  ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        int PermisosCamera =  ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int PermisosGPS =  ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if(PermisosInternet == PackageManager.PERMISSION_GRANTED
                && PermisosCamera == PackageManager.PERMISSION_GRANTED
                && PermisosGPS == PackageManager.PERMISSION_GRANTED){
            //Si el permiso esta aceptando se dara un mensaje en un toast..
            Toast.makeText(this, "Permisos consedidos", Toast.LENGTH_SHORT).show();
        }else{
            requestPermissions(new String[]{Manifest.permission.INTERNET,
                    Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }
        /*
        if(PermisosCamera == PackageManager.PERMISSION_GRANTED){
            //Si el permiso esta aceptando se dara un mensaje en un toast..
            Toast.makeText(this, "Permiso CAMERA Consedido", Toast.LENGTH_SHORT).show();
        }else{
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CODE);
        }
        if(PermisosGPS == PackageManager.PERMISSION_GRANTED){
            //Si el permiso esta aceptando se dara un mensaje en un toast..
            Toast.makeText(this, "Permiso GPS Consedido", Toast.LENGTH_SHORT).show();
        }else{
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }*/
    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()){
            firebaseAuthWithGoogle(result.getSignInAccount());
        }else{
            Toast.makeText(this, "No se pudo iniciar sesion", Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount signInAccount) {
        progressBar.setVisibility(View.VISIBLE);
        signInButton.setVisibility(View.GONE);
        signInButtonF.setVisibility(View.GONE);

        AuthCredential credential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressBar.setVisibility(View.GONE);
                signInButton.setVisibility(View.VISIBLE);
                signInButtonF.setVisibility(View.VISIBLE);

                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "No se pudo autenticar con firebase", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goMainScreen() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }
}