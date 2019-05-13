package com.inducesmile.loginff;



import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText nameField;
    private EditText emailField;
    private EditText passwordField;
    private Button registerBtn;
    private Button signup;
    private ProgressDialog mprogress;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);



        mAuth = FirebaseAuth.getInstance();

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users");

        mprogress=new ProgressDialog(this);
        nameField=(EditText) findViewById(R.id.nameField);
        emailField=(EditText) findViewById(R.id.emailField);
        passwordField=(EditText) findViewById(R.id.passwordField);
        registerBtn=(Button) findViewById(R.id.registerBtn);



        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
            }
        });


    }

    private void startRegister() {
        final String name=nameField.getText().toString().trim();
        String email=emailField.getText().toString().trim();
        String password=passwordField.getText().toString().trim();
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

            mprogress.setMessage("Signing In....");
            mprogress.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        String user_id=mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db= mDatabase.child(user_id);
                        current_user_db.child("name").setValue(name);
                        current_user_db.child("image").setValue("default");
                        mprogress.dismiss();
                        Toast.makeText(getApplicationContext(),"Data Registered",Toast.LENGTH_SHORT).show();






                    }

                }
            });

        }
        else {
            Toast.makeText(getApplicationContext(),"Data Registration Failed",Toast.LENGTH_SHORT).show();


        }
    }


}

