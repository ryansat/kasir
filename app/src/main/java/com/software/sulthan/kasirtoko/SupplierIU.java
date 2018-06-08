package com.software.sulthan.kasirtoko;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SupplierIU extends AppCompatActivity {

    // Progress Dialog
    private ProgressDialog pDialog;
    ProgressDialog pd;

    JSONParser jsonParser = new JSONParser();
    EditText inputNama;
    EditText inputAlamat;

    // inisialisasi url tambahanggota.php
    private static String url_tambah_anggota = "http://mobapp.sentrasolusi.net/customer/tambahanggota.php";

    //  inisialisasi nama node dari json yang dihasilkan oleh php (utk class ini hanya node "sukses")
    private static final String TAG_SUKSES = "sukses";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supplieriufr);
        pd = null;

        // inisialisasi Edit Text
        inputNama = (EditText) findViewById(R.id.inputNama);
        inputAlamat = (EditText) findViewById(R.id.inputAlamat);

        // inisialisasi  button
        Button btnTambahAnggota = (Button) findViewById(R.id.btnTambahAnggota);

        // klik even tombol tambah anggota
        btnTambahAnggota.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // buat method pada background thread
                new BuatAnggotaBaru().execute();
            }
        });
    }

    @Override
    public void onPause(){

        super.onPause();
        if(pd != null)
            pd.dismiss();
    }


    // *** Background Async Task untuk menambah data anggota baru

    class BuatAnggotaBaru extends AsyncTask<String, String, String> {

        // *** sebelum memulai background thread tampilkan Progress Dialog

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(SupplierIU.this,"","Retrieving Inbox...", true,false);
            super.onPreExecute();
            pDialog = new ProgressDialog(SupplierIU.this);
            pDialog.setMessage("Menambah data..silahkan tunggu");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        // *** menambah data

        protected String doInBackground(String... args) {
            String nama = inputNama.getText().toString();
            String alamat = inputAlamat.getText().toString();

            //Toast.makeText(SupplierIU.this, "Coba=" + nama + "="+alamat, Toast.LENGTH_LONG).show();


            // membangun Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("nama", nama));
            params.add(new BasicNameValuePair("alamat", alamat));


            //Toast.makeText(SupplierIU.this, "Coba=" + params + "=", Toast.LENGTH_LONG).show();

            // mengambil JSON Object
            //dengan method POST
            JSONObject json = jsonParser.makeHttpRequest(url_tambah_anggota,"POST", params);

            // periksa log cat respon
            Log.d("Respon tambah anggota", json.toString());

            // check for success tag
            try {
                int sukses = json.getInt(TAG_SUKSES);

                if (sukses == 1) {
                    // jika sukses menambah data baru
                    //Toast.makeText(SupplierIU.this, "Sukses ya...", Toast.LENGTH_LONG).show();
                    //Intent i = new Intent(getApplicationContext(), SemuaAnggota.class);
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

        // *** After completing background task Dismiss the progress dialog

        protected void onPostExecute(String file_url) {
            // hilangkan dialog ketika selesai menambah data baru
            pDialog.dismiss();
            pd.dismiss();

        }

    }
}
