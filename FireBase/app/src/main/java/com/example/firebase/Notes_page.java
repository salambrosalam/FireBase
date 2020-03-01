package com.example.firebase;

import android.net.wifi.p2p.WifiP2pManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Notes_page extends AppCompatActivity {
    int [] images = {R.drawable.monkey, R.drawable.car, R.drawable.cat, R.drawable.chesse,
            R.drawable.dog, R.drawable.elephant, R.drawable.house, R.drawable.pizza};

    int i = 0;
    int currentimage;

    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_page);


        //Hide window toolbar
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Set persistence mode ON
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        //Initialize firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();


        // Construct the data source
        ArrayList<User> arrayOfUsers = new ArrayList<User>();

        // Create the adapter to convert the array to views
        final List_adapter adapter = new List_adapter(this, arrayOfUsers);

        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.list_of_task);
        listView.setAdapter(adapter);

        //Initialize button
        Button create_button = (Button)findViewById(R.id.create_btn);



        //Button listener
        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText)findViewById(R.id.name_task);
                EditText paswd = (EditText)findViewById(R.id.aim_of_task);

                String a = name.getText().toString().trim();
                String b = paswd.getText().toString().trim();

                String userId = mDatabase.push().getKey();

                User user = new User(a,b);
                mDatabase.child("users").child(userId).setValue(user);
                // pushing user to 'users' node using the userId


                currentimage = images[i];
                User newUser = new User(a,b,currentimage);
                adapter.add(newUser);
                if (i < images.length - 1){
                    i++;
                }else{
                    i = 0;
                }

            }
        });
    }




}



// ...


