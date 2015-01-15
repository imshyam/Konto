package me.imshyam.konto;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by imshyam on 2/11/14.
 */
public class sign_up extends Activity {
    // Progress Dialog
    private ProgressDialog pDialog;

    // url to get all products list
    private static String url_signup = "http://shyamu.herokuapp.com/mobile/signup";

    // JSON Node names
    protected static int Tag_Status = 0;
    public static String error="No Network.";


    EditText e1,e2,e3,e4,e5;
    Button b1;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sign_up);

        e1=(EditText)findViewById(R.id.editText);
        e2=(EditText)findViewById(R.id.editText2);
        e3=(EditText)findViewById(R.id.editText3);
        e4=(EditText)findViewById(R.id.editText4);
        e5=(EditText)findViewById(R.id.editText5);
        b1=(Button)findViewById(R.id.button);

//IME_ACTION_GO implementation
        e2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    b1.performClick();
                    return true;
                }
                return false;
            }
        });
        TextView textView=(TextView)findViewById(R.id.textView2);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(),sign_in.class);
                startActivity(i);
                finish();
            }
        });
        // Loading products in Background Thread
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SignUp().execute();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent i =new Intent(getApplicationContext(),sign_in.class);
        startActivity(i);
        finish();
    }
    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class SignUp extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(sign_up.this);
            pDialog.setMessage("Adding Details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


         protected String doInBackground(String... args) {
            // Building Parameters
            HttpPost httpPost =new HttpPost(url_signup);
            DefaultHttpClient httpClient = new DefaultHttpClient();

             JSONObject json;
             JSONObject jGroup = new JSONObject();

             try {
                 jGroup.put("username", e3.getText().toString());
                 jGroup.put("password", e5.getText().toString());
                 jGroup.put("firstname", e1.getText().toString());
                 jGroup.put("lastname", e2.getText().toString());
                 jGroup.put("phone", e4.getText().toString());

             } catch (JSONException e) {
                 e.printStackTrace();
             }
             httpPost.setHeader("Authorization","de0464e0-552e-11e4-8c11-843497188779");
             final String STR="UTF-8";
             try {
                 httpPost.setEntity(new StringEntity(jGroup.toString(),STR));
             } catch (UnsupportedEncodingException e) {
                 e.printStackTrace();
             }
             try {
                 HttpResponse response = httpClient.execute(httpPost);
                 HttpEntity entity =response.getEntity();
                 InputStream is=entity.getContent();
                 try{
                     BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
                     StringBuilder sb = new StringBuilder();
                     String line = null;
                     while ((line = reader.readLine()) != null) {
                         sb.append(line + "\n");
                     }
                     is.close();
                     String result=sb.toString();
                     json=new JSONObject(result);
                     String status=json.getString("status");
                     Tag_Status=Integer.parseInt(status);
                     if(Tag_Status==0)
                        error=json.getString("message");
                 }catch(Exception e){
                     Log.e("log_tag", "Error converting result " + e.toString());
                 }
             } catch (IOException e) {
                 e.printStackTrace();
             }


             return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            if(Tag_Status==1){
                Intent i=new Intent(getApplicationContext(),sign_in.class);
                startActivity(i);
            }
            if(Tag_Status==0){
                Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
            }
        }




    }
}