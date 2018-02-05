package vic.sample.fireapp2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
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
import com.google.firebase.database.DatabaseReference;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * Created by Vic on 1/22/2018.
 */

public class ActivityOne extends AppCompatActivity{

    private Button feedback;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText mkKeyValue;

    private Firebase mRootRef;
    private Button mAddbtn;
    private Button mfinishedBtn;
    private EditText mValueKey;
    private String user;

    private ProgressBar mProgressBar;
    private TextView tvSignout, tvSigningOut;
    //  private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

        Firebase.setAndroidContext(this);





        mValueKey = (EditText) findViewById(R.id.stnNumber);

        String stnNumber = mValueKey.getText().toString();
        mAddbtn = (Button) findViewById(R.id.btnTEST);
        mfinishedBtn = (Button) findViewById(R.id.finishedBtn);

        mRootRef = new Firebase("https://trashtalkuser-beta2.firebaseio.com/Station/"+stnNumber);
        //  mkKeyValue = (EditText) findViewById(R.id.keyValue);

        Button btnConfirmSignout = (Button) findViewById(R.id.btnConfirmSignOut);



        SharedPreferences sharedPref = ActivityOne.this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        user = sharedPref.getString("ttData", "");


        mAddbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String key = RandomStringUtils.randomAlphanumeric(10);

                String value = mValueKey.getText().toString();


                SharedPreferences sharedPref = ActivityOne.this.getSharedPreferences("wallet", Context.MODE_PRIVATE);

                String nemwallet = sharedPref.getString("wallet", "");

                // mRootRef.child("Address").setValue(nemwallet);
                // mRootRef.child("Status").setValue("Scanning");

                mRootRef.child(value).child("Address").setValue(nemwallet);
                mRootRef.child(value).child("Status").setValue("Scanning");

                mValueKey.setFocusable(false);
                mValueKey.setClickable(false);


                Toast.makeText(ActivityOne.this, "Begin scanning....", Toast.LENGTH_LONG).show();

            }
        });

        mfinishedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String key = RandomStringUtils.randomAlphanumeric(10);

                String value = mValueKey.getText().toString();


                SharedPreferences sharedPref = ActivityOne.this.getSharedPreferences("wallet", Context.MODE_PRIVATE);

                String nemwallet = sharedPref.getString("wallet", "");

                // mRootRef.child("Address").setValue(nemwallet);
                // mRootRef.child("Status").setValue("Scanning");

                mRootRef.child(value).child("Address").setValue(nemwallet);
                mRootRef.child(value).child("Status").setValue("WaitingForPayment");

                mValueKey.setFocusable(true);
                mValueKey.setClickable(true);


                Toast.makeText(ActivityOne.this, "You shall receive payment momentarily", Toast.LENGTH_LONG).show();

            }
        });





        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_arrow:
                        Intent intent0 = new Intent(ActivityOne.this, MainActivity.class);
                        startActivity(intent0);
                        break;
                    case R.id.ic_android:
                        Intent intent2 = new Intent(ActivityOne.this, ActivityTwo.class);
                        startActivity(intent2);
                        break;
                    case R.id.ic_music:

                        break;
                    case R.id.ic_center_focus:
                        Intent intent3 = new Intent(ActivityOne.this, ActivityThree.class);
                        startActivity(intent3);
                        break;
//                    case R.id.ic_backup:
//                        Intent intent4 = new Intent(ActivityOne.this, ActivityFour.class);
//                        startActivity(intent4);
//                        break;
                }

                return false;

            }
        });
    }
}
