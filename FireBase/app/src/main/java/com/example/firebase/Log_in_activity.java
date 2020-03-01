package com.example.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class Log_in_activity extends AppCompatActivity {

    //create fields
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private AuthResult mAuthResult;
    private String mEmail;
    private String mPassword;
    private EditText mLogin_editText;
    private EditText mPassword_editText;
    private Button mButton_login;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);


        //Hide window toolbar
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mLogin_editText = (EditText)findViewById(R.id.log_in_editText_login) ;
        mPassword_editText = (EditText)findViewById(R.id.log_in_editText_password);
        mButton_login = (Button) findViewById(R.id.log_in_btn);

        mAuth = FirebaseAuth.getInstance();


        mButton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mLogin_editText.getText().toString().trim();
                String password = mPassword_editText.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Log_in_activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d("Login", "Login is succesfull");
                            Toast.makeText(getApplicationContext(), "Login succesfull", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Log_in_activity.this, Notes_page.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Log.d("Login", "Login is failed");
                            Toast.makeText(getApplicationContext(), "Login is failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });


    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    public void  updateUI(FirebaseUser account){
        if(account != null){
            Toast.makeText(this,"U Signed In successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,Notes_page.class));
        }else {
            Toast.makeText(this,"U Didnt signed in", Toast.LENGTH_LONG).show();
        }
    }




}
