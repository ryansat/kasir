package com.software.sulthan.kasirtoko;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.app.ProgressDialog;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class CustomerIU2 extends AppCompatActivity {
    private EditText eKode;
    private EditText eNama;
    private Button btnSave;
    private String url = "http://mobapp.sentrasolusi.net/customer/customerinsert.php";
    //  inisialisasi nama node dari json yang dihasilkan oleh php (utk class ini hanya node "sukses")
    private static final String TAG_SUKSES = "sukses";

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customeriu2fr);

        getSupportActionBar().setTitle("Tambah Pelanggan");


        Intent it = getIntent();

        eKode = (EditText) findViewById(R.id.eKode);
        eNama = (EditText) findViewById(R.id.eNama);
        btnSave = (Button) findViewById(R.id.btnSave);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        //SettingGlobalData a = new SettingGlobalData();
        //eCoba.setText(a.Hubungkan +"-" +a.UrlMaster+"-");

        //tNama.setText(it.getStringExtra("versi"));

        //Mulai
/*
        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v){
                new BuatAnggotaBaru().execute();
            }

        });
*/

    }


    /**
     * Background Async Task untuk menambah data anggota baru
     * */
    class BuatAnggotaBaru extends AsyncTask<String, String, String> {

        /**
         * sebelum memulai background thread tampilkan Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CustomerIU2.this);
            pDialog.setMessage("Menambah data..silahkan tunggu");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * menambah data
         * */
        protected String doInBackground(String... args) {
            String nama = eKode.getText().toString();
            String alamat = eNama.getText().toString();

            // membangun Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("nama", nama));
            params.add(new BasicNameValuePair("alamat", alamat));

            // mengambil JSON Object
            //dengan method POST
            JSONObject json = jsonParser.makeHttpRequest(url,"POST", params);

            // periksa log cat respon
            Log.d("Respon tambah anggota", json.toString());

            // check for success tag
            try {
                int sukses = json.getInt(TAG_SUKSES);

                if (sukses == 1) {
                    // jika sukses menambah data baru
                    Intent i = new Intent(getApplicationContext(), CustomerBr.class);
                    startActivity(i);

                    // tutup activity ini
                    finish();
                } else {
                    // jika gagal dalam menambah data
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // hilangkan dialog ketika selesai menambah data baru
            pDialog.dismiss();
        }

    }
    //==================================


    public void BtnSaveClick (View v) //Insert data Customer
    {
        try {
            String kode = URLEncoder.encode(eKode.getText().toString(), "utf-8");
            String nama = URLEncoder.encode(eNama.getText().toString(), "utf-8");

            //Toast.makeText(this, "Kode:"+kode + "=="+nama, Toast.LENGTH_LONG).show();
            //String kode = eKode.getText().toString();
            //String nama = eNama.getText().toString();
            url += "?kodecust=" + kode + "&namacust=" + nama;
            getRequest(url);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void getRequest(String Url) {
        //Toast.makeText(this, "Tambah Data " + Url + " ", Toast.LENGTH_LONG).show();
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        try {
            HttpResponse response = client.execute(request);
            Toast.makeText(this, "Tambah Data " + request(response) + " ",
                    Toast.LENGTH_LONG).show();
            finish();
        } catch (Exception ex) {
            Toast.makeText(this, "Tambah Data Gagal !", Toast.LENGTH_LONG)
                    .show();
            finish();
        }
    }

    public static String request(HttpResponse response) {
        String result = "";
        try {
            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                str.append(line + "\n");
            }
            in.close();
            result = str.toString();
        } catch (Exception ex) {
            result = "Error";
        }
        return result;
    }

}
