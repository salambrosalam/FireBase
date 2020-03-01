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

public class reg_activity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private AuthResult mAuthResult;
    private String mEmail;
    private String mPassword;
    private EditText mLogin_editText;
    private EditText mPassword_editText;
    private Button mRegister_btn;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);


        //Hide window toolbar
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //Initialize UI elements
        mLogin_editText = (EditText)findViewById(R.id.email_edit_text) ;
        mPassword_editText = (EditText)findViewById(R.id.password_edit_text);
        mRegister_btn = (Button) findViewById(R.id.reg_button);

        //Initialize firebase
        mAuth = FirebaseAuth.getInstance();
        //Button listener
        mRegister_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email_reg = mLogin_editText.getText().toString().trim();
                String password_reg = mPassword_editText.getText().toString().trim();


                //Checking fields for fill
                if (TextUtils.isEmpty(email_reg)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password_reg)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password_reg.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Create new user
                mAuth.createUserWithEmailAndPassword(email_reg, password_reg).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser(); //You Firebase user
                            // user registered, start profile activity
                            Toast.makeText(reg_activity.this,"Account Created",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(reg_activity.this, Notes_page.class);
                            startActivity(intent);
                            finish();

                        }else {
                            Toast.makeText(reg_activity.this,"Sign_Up is failed",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }
}