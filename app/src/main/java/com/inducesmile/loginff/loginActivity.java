package com.inducesmile.loginff;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginActivity extends AppCompatActivity {
    private EditText mEmail;
    private EditText mPass;
    private Button mloginbtn;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Button signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users");
        mEmail=(EditText) findViewById(R.id.etEmail);
        mPass=(EditText) findViewById(R.id.etpass);
        mloginbtn=(Button) findViewById(R.id.LoginBtn);


        signup=(Button) findViewById(R.id.SignupBtn);








        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(loginActivity.this, MainActivity.class);

                loginActivity.this.startActivity(myIntent);
            }
        });

        mloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }
    private void checkLogin(){
        String email=mEmail.getText().toString().trim();
        String password=mPass.getText().toString().trim();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        checkUser();
                        Intent myIntent = new Intent(loginActivity.this, MainActivity2.class);

                        loginActivity.this.startActivity(myIntent);

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Error Login",Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
    }

    private void checkUser() {
        final String user_id=mAuth.getCurrentUser().getUid();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(user_id)){
                    Toast.makeText(getApplicationContext(),"Login successfully",Toast.LENGTH_SHORT).show();


                }
                else {
                    Toast.makeText(getApplicationContext(),"You need to Sign UP",Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
