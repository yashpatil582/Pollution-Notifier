package swaroop.com.yash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * Created by User on 6/19/2017.
 */

public class login extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private Context mContext;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;

    EditText mEmail, mPassword;
    // private TextView mPleaseWait;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTitle("Login");
        setContentView(R.layout.activity_login);
        // mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        // mPleaseWait = (TextView) findViewById(R.id.pleaseWait);
        mEmail = findViewById(R.id.input_email);
        mPassword = findViewById(R.id.input_password);
        mContext = login.this;
        Button button1=findViewById(R.id.btn_login);
        // Button button2=findViewById(R.id.btn_student);

        mAuth = FirebaseAuth.getInstance();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();


                if (email.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(login.this,"You have not entered anything",Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Log.w(TAG, "signInWithEmail:failed", task.getException());
                                        Toast.makeText(login.this, getString(R.string.auth_failed),
                                                Toast.LENGTH_SHORT).show();

                                    } else {
                                        Intent intent = new Intent(login.this, home.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }

        });

        if(mAuth.getCurrentUser() != null){
            Intent intent = new Intent(login.this, home.class);
            startActivity(intent);
            finish();
        }

        setupFirebaseAuth();
    }


    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

  /*  private boolean isStringNull(String string) {
        Log.d(TAG, "isStringNull: checking string if null.");

        return string.equals("");
    }*/
}
