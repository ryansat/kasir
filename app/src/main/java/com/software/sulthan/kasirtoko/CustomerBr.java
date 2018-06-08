package com.software.sulthan.kasirtoko;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerBr extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private JSONObject jObject;
    private String xResult ="";
    //Seusuaikan url dengan nama domain yang anda gunakan
    //private String url = "https://mysentra.000webhostapp.com/customerview.php";
    //private String url = "http://mobapp.sentrasolusi.net/customer/customerview.php";
    private String url;
    public Button btn1;
    public TextView txt1;
    private static final String TAG = "CustomerBr";

    private List<Map<String, Object>> data;
    private ListView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customerbrfr);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                startActivity(new Intent(CustomerBr.this, CustomerIU2.class));

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Mulai



    }


    private void parse(TextView txtResult) throws Exception {
        jObject = new JSONObject(xResult);
        JSONArray menuitemArray = jObject.getJSONArray("dataku");
        String sret="";
        for (int i = 0; i < menuitemArray.length(); i++) {
            sret +=menuitemArray.getJSONObject(i).getString("kodecust").toString()+" : ";
            System.out.println(menuitemArray.getJSONObject(i).getString("kodecust").toString());
            System.out.println(menuitemArray.getJSONObject(i).getString("namacust").toString());
            sret +=menuitemArray.getJSONObject(i).getString("namacust").toString()+"\n";

            //sret += menuitemArray.getJSONObject(i).getString("namacust").toString()+" : ";
        }
        txtResult.setText(sret);
    }
    /**
     * Method untuk Mengirimkan data kes erver
     * event by button login diklik
     */
    public String getRequest(String Url){
        String sret="";
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(Url);
        try{
            HttpResponse response = client.execute(request);
            sret =request(response);
        }
        catch(Exception ex){
            Toast.makeText(this,"Gagal "+ex, Toast.LENGTH_SHORT).show();
        }
        return sret;
    }
    /**
     * Method untuk Menenrima data dari server
     * @param response
     * @return
     */
    public static String request(HttpResponse response){
        String result = "";
        try{
            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null){
                str.append(line + "\n");
            }
            in.close();
            result = str.toString();
        }catch(Exception ex){
            result = "Error";
        }
        return result;
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.customer_br, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent intents = new Intent(this, CustomerIU.class);
            startActivity(intents);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_insertcustomer) {
            Intent intents = new Intent(this, CustomerIU2.class);
            startActivity(intents);
        } else if (id == R.id.nav_listcustomer) {
            Intent intents = new Intent(this, CustomerBr.class);
            startActivity(intents);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void btnSearchClick (View v) {
        SettingGlobalData a = new SettingGlobalData();
        url = a.UrlMaster + "/customer/customerview.php";

        TextView txtResult = (TextView)findViewById(R.id.textView4);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        xResult = getRequest(url);
        try {
            parse(txtResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //btn1= (Button)findViewById(R.id.button) ;
    }

    public void btnAddClick (View v) {
        Toast.makeText(this,"Click tambah ya.. ", Toast.LENGTH_SHORT).show();
    }

    private void parse21(TextView txtResult) throws Exception {


        jObject = new JSONObject(xResult);
        JSONArray menuitemArray = jObject.getJSONArray("dataku");
        String xnama = "";
        String xkode = "";
        String xalamat = "";

        Log.d(TAG, "onCreate: Started.");
        ListView mListView = (ListView) findViewById(R.id.listView);

        ArrayList<HashMap<String, String>> arrayList=new ArrayList<>();


        for (int i = 0; i < menuitemArray.length(); i++) {
            xnama = menuitemArray.getJSONObject(i).getString("namacust").toString();
            xkode = menuitemArray.getJSONObject(i).getString("kodecust").toString();
            xalamat = menuitemArray.getJSONObject(i).getString("alamat").toString();

            //sret += "coba  data";
            HashMap<String,String> hashMap=new HashMap<>();//create a hashmap to store the data in key value pair
            hashMap.put("namacust", xnama);
            hashMap.put("kodecust", xkode);
            hashMap.put("alamat", xalamat);
            arrayList.add(hashMap);//add the hashmap into arrayList

            //Create the Person objects
            //Person i = new Person(sret, "12-12-1980", "Male");
            //Person(sret, "12-12-1980", "Male");
            //peopleList.add(sret);
        }
        String[] from={"namacust","kodecust", "alamat"};//string array
        int[] to={R.id.eNamaCust, R.id.eKodeCust, R.id.eAlamat};//int array of views id's
        SimpleAdapter simpleAdapter=new SimpleAdapter(this,arrayList,R.layout.adaptercustomerbr, from, to);//Create object and set the parameters for simpleAdapter
        mListView.setAdapter(simpleAdapter);//sets the adapter for listView


        //txtResult.setText(sret);
        //PersonListAdapter adapter = new PersonListAdapter(this, R.layout.adaptercustomerbr, peopleList);

        //ListView.setAdapter(simpleAdapter);//sets the adapter for listView

        //PersonListAdapter adapter = new PersonListAdapter(this, R.layout.adaptercustomerbr, peopleList);
        //mListView.setAdapter(adapter);

    }
    public void btnSearchFilterClick (View v){
        SettingGlobalData a = new SettingGlobalData();
        url = a.UrlMaster + "/customer/customerview.php";

        TextView txtResult = (TextView)findViewById(R.id.textView4);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        xResult = getRequest(url);
        try {
            parse21(txtResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //btn1= (Button)findViewById(R.id.button) ;


/*        SettingGlobalData a = new SettingGlobalData();
        url = a.UrlMaster + "/customer/customerview.php";

        TextView txtResult = (TextView)findViewById(R.id.textView4);
        //TextView txtResult = (TextView)findViewById(R.id.eNamaCust);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //xResult = getRequest(url);

        String xResult="";
        String sret="";
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        try{
            HttpResponse response = client.execute(request);
            sret =request(response);
        }
        catch(Exception ex){
            Toast.makeText(this,"Gagal "+ex, Toast.LENGTH_SHORT).show();
        }
        xResult = sret;


        try {
            parse(txtResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
*/

/*
        Log.d(TAG, "onCreate: Started.");
        ListView mListView = (ListView) findViewById(R.id.listView);

        //Create the Person objects
        Person john = new Person("John", "12-12-1980", "Male");
        Person stacy = new Person("Stacy", "12-12-1981", "Female");
        Person andre = new Person("Andre", "12-12-1982", "Male");
        Person john2 = new Person("John", "12-12-1980", "Male");
        Person stacy2 = new Person("Stacy", "12-12-1981", "Female");
        Person andr3e = new Person("Andre", "12-12-1982", "Male");
        Person john3 = new Person("John", "12-12-1980", "Male");
        Person stac3y = new Person("Stacy", "12-12-1981", "Female");
        Person andrse = new Person("Andre", "12-12-1982", "Male");
        Person johnd = new Person("John", "12-12-1980", "Male");
        Person stacdy = new Person("Stacy", "12-12-1981", "Female");
        Person andrde = new Person("Andre", "12-12-1982", "Male");
        Person stacsy = new Person("stacsy", "12-12-1981", "Female");
        Person werandrse = new Person("werandrse", "12-12-1982", "Male");
        Person jo3hns = new Person("jo3hns", "12-12-1980", "Male");
        Person stafcsy = new Person("stafcsy", "12-12-1981", "Female");
        Person rewandrrse = new Person("rewandrrse", "12-12-1982", "Male");
        Person johsn = new Person("johsn", "12-12-1980", "Male");
        Person stacssy = new Person("stacssy", "12-12-1981", "Female");
        Person andrsae = new Person("andrsae", "12-12-1982", "Male");

        //Add the person pbjects to ArrayList
        ArrayList<Person> peopleList = new ArrayList<>();
        peopleList.add(john);
        peopleList.add(stacy);
        peopleList.add(andre);
        peopleList.add(john);
        peopleList.add(stacy);
        peopleList.add(andre);
        peopleList.add(john);
        peopleList.add(stacy);
        peopleList.add(andre);
        peopleList.add(john);
        peopleList.add(stacy);
        peopleList.add(andre);
        peopleList.add(john);
        peopleList.add(stacy);
        peopleList.add(andre);
        peopleList.add(john);
        peopleList.add(stacy);
        peopleList.add(andre);
        peopleList.add(john);
        peopleList.add(stacy);
        peopleList.add(andre);

        PersonListAdapter adapter = new PersonListAdapter(this, R.layout.adaptercustomerbr, peopleList);
        mListView.setAdapter(adapter);*/
    }

}
