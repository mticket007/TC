package com.ticket.m.qrcodescanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText emailId,password;
   private Button loginBtn;
   private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailId=findViewById(R.id.emailId);
        password=findViewById(R.id.password);
        loginBtn=findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailId.getText().toString()==null)
                {
                    emailId.setError("please provide your email address ");
                }
                if(password.getText().toString()==null)
                {
                    password.setError("please provide your password ");
                }
                if(password.getText().toString().length()<8)
                {
                    password.setError(" Password should be of minimum of size 8");
                }
                auth=FirebaseAuth.getInstance();
                auth.signInWithEmailAndPassword(emailId.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    finish();
                                    startActivity(new Intent(LoginActivity.this,QrcodeScannerActivity.class));
                                }
                                else
                                {

                                    AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
                                    builder.setTitle("Your username or password is wrong ");
                                    builder.setCancelable(true);
                                    builder.setIcon(R.drawable.ic_error);
                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    AlertDialog alertDialog=builder.create();
                                    alertDialog.show();
                                }
                            }
                        });

            }
        });

    }
}
