package com.software.sulthan.kasirtoko;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CustomerIU extends AppCompatActivity {
    private EditText eEmail;
    private EditText eKode;
    private EditText eNama;
    private Button btnSave;
    private TextView tNama;
    private EditText eCoba;
    private String url = "http://mobapp.sentrasolusi.net/customer/customerinsert.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customeriufrm);

        getSupportActionBar().setTitle("Data Pelanggan");


        //eCoba = (EditText) findViewById(R.id.eCoba);
        //eEmail = (EditText) findViewById(R.id.eEmail);
        //eKode = (EditText) findViewById(R.id.eKode);
        //eNama = (EditText) findViewById(R.id.eNama);

        Intent it = getIntent();

        //eEmail = (EditText) findViewById(R.id.eEmail);
        eKode = (EditText) findViewById(R.id.eKode);
        eNama = (EditText) findViewById(R.id.eNama);
        btnSave = (Button) findViewById(R.id.btnSave);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        //SettingGlobalData a = new SettingGlobalData();
        //eCoba.setText(a.Hubungkan +"-" +a.UrlMaster+"-");

        //tNama.setText(it.getStringExtra("versi"));
    }

    public void BtnSaveClick (View v) //Insert data Customer
    {
        try {
            String kode = URLEncoder.encode(eKode.getText().toString(), "utf-8");
            String nama = URLEncoder.encode(eNama.getText().toString(), "utf-8");
            url += "?kodecust=" + kode + "&namacust=" + nama;
            getRequest(url);
            finish();
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
        } catch (Exception ex) {
            Toast.makeText(this, "Tambah Data Gagal !", Toast.LENGTH_LONG)
                    .show();
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

    public void ClearDisplayForm (View v) //Bersihkan layar
    {
        eKode.setText("");
        eNama.setText("");
    }



}
