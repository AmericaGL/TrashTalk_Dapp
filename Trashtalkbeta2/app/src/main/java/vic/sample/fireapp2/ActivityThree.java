package vic.sample.fireapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.commons.lang3.RandomStringUtils;


/**
 * Created by Vic on 1/22/2018.
 */

public class ActivityThree extends AppCompatActivity{

    private static final String TAG = "ActivityThree";

    private Button feedback;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText mkKeyValue;

    private Firebase mRootRef;
    private Button mAddbtn;
    private EditText mValueKey;
    private String user;

    private ProgressBar mProgressBar;
    private TextView tvSignout, tvSigningOut;
  //  private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);

        Firebase.setAndroidContext(this);

        //mDatabase = FirebaseDatabase.getInstance().getReference().child("Feedback");

      //  tvSignout = (TextView) findViewById(R.id.tvConfirmSignout);
       // mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    //    tvSigningOut = (TextView) findViewById(R.id.tvSigningOut);

        mRootRef = new Firebase("https://trashtalkuser-beta2.firebaseio.com/Feedback");


        mValueKey = (EditText) findViewById(R.id.valueField);
        mAddbtn = (Button) findViewById(R.id.addBtn);
      //  mkKeyValue = (EditText) findViewById(R.id.keyValue);

        Button btnConfirmSignout = (Button) findViewById(R.id.btnConfirmSignOut);

        setupFirebaseAuth();

        SharedPreferences sharedPref = ActivityThree.this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        user = sharedPref.getString("ttData", "");







        feedback= (Button) findViewById(R.id.addBtn);


        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String key = RandomStringUtils.randomAlphanumeric(10);

                String value = mValueKey.getText().toString();
              //  String key = mkKeyValue.getText().toString();


              //  String value = "test";
              //  String key = "123";
              //  String key="1234567sdfsf8";
                //custom object
             //   User user=new User();
            //    DatabaseReference mDatabase;
              //  mDatabase = FirebaseDatabase.getInstance().getReference();
              //  mRootRef.child(user).child(key).setValue(value);

                SharedPreferences sharedPref = ActivityThree.this.getSharedPreferences("wallet", Context.MODE_PRIVATE);

                String nemwallet = sharedPref.getString("wallet", "");

                mRootRef.child(nemwallet).child(key).setValue(value);



                //     Firebase childRef = mRootRef.child(key);

             //   mRootRef.push().setValue(value);
            //    mRootRef.setValue(value);

                Toast.makeText(ActivityThree.this, "Thank you!", Toast.LENGTH_LONG).show();

            }
        });

        btnConfirmSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mAuth.signOut();
                ActivityThree.this.finish();
            }
        });


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_arrow:
                        Intent intent0 = new Intent(ActivityThree.this, MainActivity.class);
                        startActivity(intent0);
                        break;
                    case R.id.ic_android:
                        Intent intent1 = new Intent(ActivityThree.this, ActivityOne.class);
                        startActivity(intent1);

                        break;
                    case R.id.ic_music:
                        Intent intent2 = new Intent(ActivityThree.this, ActivityTwo.class);
                        startActivity(intent2);
                        break;
//                    case R.id.ic_center_focus:
//
//                        break;
//                    case R.id.ic_backup:
//                        Intent intent4 = new Intent(ActivityThree.this, ActivityFour.class);
//                        startActivity(intent4);
//                        break;
                }

                return false;

            }
        });


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

                    Log.d(TAG, "onAuthStateChanged: navigating back to login screen.");
                    Intent intent = new Intent(ActivityThree.this, MainActivitySPLASH.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
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
}
