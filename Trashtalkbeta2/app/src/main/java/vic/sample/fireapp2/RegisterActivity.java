package vic.sample.fireapp2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.commons.lang3.StringUtils;

public class RegisterActivity extends AppCompatActivity implements TextWatcher{

    private EditText mEmailField;
    private EditText mPasswoordField;
    private EditText mNemAddress;
    private TextView mCounter;

    private Button mRegisterBtn;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private ProgressDialog mProgress;

    private TextView mToLogin;

    private Boolean addressIsGood;
    private Boolean hasLetters;
    private Boolean hasNumbers;
    private Boolean noSpecial;
    private Boolean noSpace;
    private Boolean beginsT;

    private String email;
    private String nem;

    private Firebase mRootref;

    private String currentS;
    private String display;

    private int textAmount;
    private int left;
    private String noDashNEM;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Firebase.setAndroidContext(this);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mProgress = new ProgressDialog(this);

        mEmailField = findViewById(R.id.emailField);
        mPasswoordField = findViewById(R.id.passwordField);
        mNemAddress = findViewById(R.id.nemField);
        mCounter = findViewById(R.id.counter);




        mNemAddress.addTextChangedListener(this);

        mNemAddress.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        mRegisterBtn =  findViewById(R.id.registerBtn);

        mToLogin = (TextView) findViewById(R.id.toLogin);
        SpannableString content = new SpannableString("LOGIN");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        mToLogin.setText(content);

        // set on-click listener
        mToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // your code to perform when the user clicks on the TextView
           //     Toast.makeText(RegisterActivity.this, "You clicked on TextView 'Click Me'.", Toast.LENGTH_SHORT).show();
                Intent regIntent = new Intent(RegisterActivity.this, MainActivitySPLASH.class);
                //regIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                regIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(regIntent);
            }
        });



        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startRegister();

            }
        });


    }

    private void startRegister() {

        final String email = mEmailField.getText().toString().trim();
        final String password = mPasswoordField.getText().toString().trim();
     //   final String nem = mNemAddress.getText().toString().trim();
        final String nem = noDashNEM;

//        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
//        if(email.equals(fuser.getEmail().trim())) {
//            Toast.makeText(this, "Email is taken", Toast.LENGTH_SHORT).show();
//            return;
//        }

        String whole = nem;
        String first = whole.substring(0, 1);
        int length = whole.length();

        final SharedPreferences sharedPref = getSharedPreferences("wallet", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("useremail", email);
        editor.putString("wallet", nem);
        editor.apply();

        final SharedPreferences user = getSharedPreferences("newuser", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor1 = sharedPref.edit();
        editor1.putString("email", email);
        editor1.putString("nem", nem);
        editor1.apply();




        if  ((whole.matches(".*[a-z].*")) || (whole.matches(".*[A-Z].*"))) // has atleast 1 lowercase OR uppercase.
        {
            hasLetters = true;
        }
        else
        {
            hasLetters = false;
        }
        if (whole.matches(".*\\d+.*"))   // has at least 1 digit.
        {
            hasNumbers = true;
        }
        else
        {
            hasNumbers = false;
        }

        if (whole.matches("[a-zA-Z0-9 ]*"))   // comprised of letters and numbers only (no special charachters)
        {
            noSpecial = true;
        }
        else
        {
            noSpecial = false;
        }
        if (!StringUtils.containsWhitespace(whole))  // does not cntain whitespace.
        {
            noSpace = true;
        }
        else
        {
            noSpace = false;
        }
        if (whole.substring(0,1).equals("t") || whole.substring(0,1).equals("T") ) {
            beginsT = true;
        }
        else
        {
            beginsT = false;
        }

        if (hasLetters && hasNumbers && noSpecial && noSpace && (length == 40) && beginsT)
        {
            addressIsGood = true;
        }
        else
        {
            addressIsGood = false;
        }


        if (addressIsGood == false)
        {
            Toast toast = Toast.makeText(RegisterActivity.this, "Your NEM address must be a valid Testnet address.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }



        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty((nem))) {

            Toast toast = Toast.makeText(RegisterActivity.this, "Fields are empty.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }

       if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(nem) && addressIsGood)
        {

            mProgress.setMessage("Signing Up ...");
            mProgress.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {

                        Toast toast = Toast.makeText(RegisterActivity.this, "Sign up problem.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        mProgress.dismiss();
                    }

                    if(task.isSuccessful()){

                        Firebase mRootRef = new Firebase("https://trashtalkuser-beta2.firebaseio.com/Users");

                        String user_id = mAuth.getCurrentUser().getUid();

                        mDatabase.child("user_id");

                        DatabaseReference current_user_db = mDatabase.child(user_id);

                        current_user_db.child("email").setValue(email);
                        current_user_db.child("nem").setValue(nem);


                        mProgress.dismiss();

                        Toast toast = Toast.makeText(RegisterActivity.this, "Registered succesfully! ", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        SharedPreferences newuser = RegisterActivity.this.getSharedPreferences("newuser", Context.MODE_PRIVATE);
//
                      //  String user = newuser.getString("email", "");
                    //    String nem = newuser.getString("nem", "");

                        String goodemail = email.replace(".", "_DOT_");

                        mRootRef.child("Registration").child(goodemail).setValue(nem);

                        Intent mainIntent = new Intent(RegisterActivity.this, MainActivitySPLASH.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);






                    }


                }



            });

        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {


    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

//        Toast toast = Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.show();
//        ViewGroup group = (ViewGroup) toast.getView();
//        TextView messageTextView = (TextView) group.getChildAt(0);
//        messageTextView.setTextSize(25);





              //  textAmount = s.length();


   //     display = currentS;


//             if (textAmount > 1) {
//                 display = currentS + " " + textAmount + " / 40 " + "Left:" + left;
//             }
//        if (textAmount == 0) {
//            display = " + textAmount + " / 40 " + "Left:" + left;
//        }
     //   }

      //  if (currentS.length() == 0) {
       //     display = textAmount + " / 40 " + "Left:" + left;
      //  }


   //     mCounter.setText(display);

     //   mCounter.setText(String.valueOf(s.length()));

    }

    @Override
    public void afterTextChanged(Editable s) {



        String currentS = s.toString();
        noDashNEM = currentS.replaceAll("-", "").trim();
        textAmount = noDashNEM.length();


        left = 40 - textAmount;
        //    display =   textAmount + " / 40 " + "Left:" + left;

        display = textAmount + " / 40";

        mCounter.setText(display);

//        String myString = s.toString();
//
//        StringBuilder str = new StringBuilder(myString);
//        for(int i = 0; i < str.length(); i++){
//            if(i == 6){
//
//                str.insert(i, "-");
//            }
//            if(i == 13){
//                str.insert(i, "-");
//            }
//            if(i == 20){
//                str.insert(i, "-");
//            }
//            if(i == 27){
//                str.insert(i, "-");
//            }
//            if(i == 34){
//                str.insert(i, "-");
//            }
//            if(i == 41){
//                str.insert(i, "-");
//            }
//        }
//        // System.out.println(str.toString());
//        mCounter.setText(str);

    }

}


// 6, 12, 18, 24, 30, 36, 42