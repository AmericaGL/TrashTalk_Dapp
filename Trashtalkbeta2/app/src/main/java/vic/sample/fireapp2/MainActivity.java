package vic.sample.fireapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private TextView apiData;
    private String xemData;
    private String ttData;
    private Boolean addressIsGood;
    private Boolean hasLetters;
    private Boolean hasNumbers;
    private Boolean noSpecial;
    private Boolean noSpace;
    private Boolean checking;
    private Boolean beginsT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();


      //  if (whole.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]+$")) A-Z & 0-9 & a-z
       // if (whole.matches("^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$")) A-Z & 0-9
      //  if (whole.matches("^(?=.*[a-zA-Z])(?=.*[0-9])"))
      //  if ((whole.matches("..*")) && (whole.matches(".*[a-z].*")) || (whole.matches(".*[A-Z].*")))


//          (whole.matches(".*\\d+.*")) has any number.
//          (whole.matches(".*[a-z].*")) has any lowercase letter.
//          (whole.matches(".*[A-Z].*")) has any uper case letter.



        String whole = "tA6VBLESN32HY44LJ6QTO6MW747STGJCVVVIX5Me";
       // String first = whole.substring(0, 1);
        char firstchar = whole.charAt(0);
        String first = Character.toString(firstchar);


        int length = whole.length();




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


        Log.v("                        "+whole,      "addressIsGood =  " +addressIsGood.toString());





            SharedPreferences sharedPref = MainActivity.this.getSharedPreferences("wallet", Context.MODE_PRIVATE);

            String nemwallet = sharedPref.getString("wallet", "");






      //  getSupportFragmentManager().beginTransaction().replace(R.id.container,new Tab1Fragment()).commit();
      //  setContentView(R.layout.fragment1_layout);


        String nemPrefix = "http://23.228.67.85:7890/account/get?address=";
        String ttPrefix = "http://23.228.67.85:7890/account/mosaic/owned?address=";
        String walletAddress = nemwallet;

        String nemAccountJoined = (nemPrefix+walletAddress);
        String ttAccountJoined = (ttPrefix+walletAddress);

        Log.v("walletAddress", walletAddress);
       // Log.v("nemwallet///////", walletAddr);

//        Button btnHit = (Button) findViewById(R.id.btnTEST);
//        apiData = (TextView) findViewById(R.id.apiJson);
//
//        btnHit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
                new JSONTask().execute(nemAccountJoined);
        new ttJSONTask().execute(ttAccountJoined);
//                Toast.makeText(MainActivity.this, "FROM MAINACTIVITY",Toast.LENGTH_SHORT).show();


//            }
//
//
//        });








        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_talk_vector);
        tabLayout.getTabAt(0).setText("TrashTalk");
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_nem_logo);
        tabLayout.getTabAt(1).setText("NEM");
        //tabLayout.getTabAt(2).setIcon(R.drawable.ic_attach_file);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_arrow:
                        break;
                    case R.id.ic_android:
                        Intent intent1 = new Intent(MainActivity.this, ActivityOne.class);
                        startActivity(intent1);
                        break;
                    case R.id.ic_music:
                        Intent intent2 = new Intent(MainActivity.this, ActivityTwo.class);
                        startActivity(intent2);
                        break;
                    case R.id.ic_center_focus:
                        Intent intent3 = new Intent(MainActivity.this, ActivityThree.class);
                        startActivity(intent3);
                        break;
//                    case R.id.ic_backup:
//                        Intent intent4 = new Intent(MainActivity.this, ActivityFour.class);
//                        startActivity(intent4);
//                        break;
                }

                    return false;

            }
        });



    }
    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter  = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment());
        adapter.addFragment(new Tab2Fragment());
      //  adapter.addFragment(new Tab3Fragment());
        viewPager.setAdapter(adapter);
    }

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String stringJson = buffer.toString();

                JSONObject accountDataJson = new JSONObject(stringJson);
                JSONObject accountParentObj = accountDataJson.getJSONObject("account");

                String address = accountParentObj.getString("address");
                long balance = accountParentObj.getLong("balance");
                int divisibilty = 6;
                BigDecimal bigBalance = new BigDecimal(balance);
                BigDecimal fixedBalance = bigBalance.scaleByPowerOfTen(-divisibilty);
                double dblBalance = fixedBalance.doubleValue();
                // long fixedBalance = decBalance.longValue();


                String strBalanceUS = NumberFormat.getCurrencyInstance().format(dblBalance);




                NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
                String strBalance = numberFormat.format(fixedBalance);

                String publicKey = accountParentObj.getString("publicKey");

                SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("address", address);
                editor.putString("strBalance", strBalance);
                editor.putString("publickKey", publicKey);
            //    editor.putString("nemAddy")
                editor.apply();



                return "Address" + " - " + address + " Balance" + " - " + strBalance + " Public key" + " - " + publicKey;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null)
                    connection.disconnect();
                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
          //  apiData.setText(result);
            xemData = result;


        }


    }

    public class ttJSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String stringJson = buffer.toString();

                JSONObject accountDataJson = new JSONObject(stringJson);
          //     JSONObject accountParentObj = accountDataJson.getJSONObject("account");
//
//                String address = accountParentObj.getString("address");
//                long balance = accountParentObj.getLong("balance");
//                int divisibilty = 6;
//                BigDecimal bigBalance = new BigDecimal(balance);
//                BigDecimal fixedBalance = bigBalance.scaleByPowerOfTen(-divisibilty);
//                double dblBalance = fixedBalance.doubleValue();
//                // long fixedBalance = decBalance.longValue();
//
//
//                String strBalanceUS = NumberFormat.getCurrencyInstance().format(dblBalance);
//
//
                ttData = stringJson;

           //     JSONObject jo0 = new JSONObject(accountDataJson); //json you described
                JSONArray ja = accountDataJson.getJSONArray("data");
                for(int i=0;i<ja.length();i++){
                    if(ja.getJSONObject(1).getString("username").equals("knighthero")){
                        System.out.println(ja.getString(1));
                        break;
                    }
                }
//
//                NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
//                String strBalance = numberFormat.format(fixedBalance);
//
//                String publicKey = accountParentObj.getString("publicKey");




                SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("ttData", ttData);

                //    editor.putString("nemAddy")
                editor.apply();






                Log.v("string", stringJson);
//
//
//
//                return "Address" + " - " + address + " Balance" + " - " + strBalance + " Public key" + " - " + publicKey;
                return "";


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null)
                    connection.disconnect();
                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //  apiData.setText(result);
         //   ttData = result;




        }


    }


}
