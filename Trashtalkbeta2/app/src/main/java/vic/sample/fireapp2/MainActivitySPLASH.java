package vic.sample.fireapp2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
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

public class MainActivitySPLASH extends AppCompatActivity {

    private EditText mEmailField;
    private EditText mPasswordField;

    private TextView mToRegister;

    private Button mLoginBtn;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainlauncher);

        FirebaseApp.initializeApp(MainActivitySPLASH.this);

        mAuth = FirebaseAuth.getInstance();

        mEmailField = (EditText) findViewById(R.id.emailField);
        mPasswordField = (EditText) findViewById(R.id.passwordField);

        mLoginBtn = (Button) findViewById(R.id.loginBtn);

        mToRegister = (TextView) findViewById(R.id.toRegister);
     //   SpannableString content = new SpannableString("NEW TO TRASHTALK? TAP HERE.");
      //  content.setSpan(new UnderlineSpan(), 0, content.length(), 0);



      //  mToRegister.setText(content);

        // set on-click listener
        mToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // your code to perform when the user clicks on the TextView
        //        Toast.makeText(MainActivitySPLASH.this, "You clicked on TextView 'Click Me'.", Toast.LENGTH_SHORT).show();
                Intent regIntent = new Intent(MainActivitySPLASH.this, RegisterActivity.class);
                //regIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                regIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(regIntent);
            }
        });




        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null) {

                    Intent loginIntent = new Intent(MainActivitySPLASH.this, RegisterActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(new Intent(MainActivitySPLASH.this, MainActivity.class));


                }
            }
        };

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startSignIn();
            }
        });

    }

    @Override public void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }


    private void startSignIn() {

            String email = mEmailField.getText().toString();
            String password = mPasswordField.getText().toString();

            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

                Toast toast = Toast.makeText(MainActivitySPLASH.this, "Fields are empty.", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();


            } else {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {

                            Toast toast = Toast.makeText(MainActivitySPLASH.this, "Sign in problem.", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        }
                    }
                });
            }


    }
}
