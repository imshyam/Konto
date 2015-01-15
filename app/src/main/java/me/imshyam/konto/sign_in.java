package me.imshyam.konto;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.util.List;

/**
 * Created by imshyam on 2/11/14.
 */
public class sign_in extends Activity {

    Button b1;
    TextView textView;
    EditText e1,e2;

    //Shared Preferences
    public static SharedPreferences somedata;
    public static String file="MyShPre";

    // Progress Dialog
    private ProgressDialog pDialog;

    // single product url
    private static final String url_login = "http://shyamu.herokuapp.com/mobile/login";

    //Json nodes
    private static int Tag_Status = 0;
    public static String error="No Network.";



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sign_in);

        somedata=getSharedPreferences(file, MODE_PRIVATE);
        b1=(Button)findViewById(R.id.button);
        e1=(EditText)findViewById(R.id.editText);
        e2=(EditText)findViewById(R.id.editText2);
        textView=(TextView)findViewById(R.id.textView2);

        //checking whether signed in or not
        //if already signed in
        if(TextUtils.isEmpty(somedata.getString("cookie",null))==false){
            Intent i=new Intent(getApplicationContext(),MyActivity.class);
            startActivity(i);
            finish();
        }
        //else
        else {
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

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new sign_me_in().execute();
                }
            });
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), sign_up.class);
                    startActivity(i);
                    finish();
                }
            });
        }
    }
    /**
     * Background Async Task to Sign in
     * */
    class sign_me_in extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(sign_in.this);
            pDialog.setMessage("Signing In. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * signing in in background thread
         */
        protected String doInBackground(String... params) {
            if (TextUtils.isEmpty(somedata.getString("cookie", null))) {
                // Building Parameters
                HttpPost httpPost = new HttpPost(url_login);
                DefaultHttpClient httpClient = new DefaultHttpClient();

                JSONObject json;
                JSONObject jGroup = new JSONObject();

                try {
                    jGroup.put("username", e1.getText().toString());
                    jGroup.put("password", e2.getText().toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                httpPost.setHeader("Authorization", "de0464e0-552e-11e4-8c11-843497188779");
                final String STR = "UTF-8";
                try {
                    httpPost.setEntity(new StringEntity(jGroup.toString(), STR));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    InputStream is = entity.getContent();
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        is.close();
                        String result = sb.toString();
                        result = result.replace("\\", "");
                        result = result.replace("\"{", "{");
                        result = result.replace("}\"", "}");
                        Log.e("This is what we get : ", result);
                        json = new JSONObject(result);
                        String status = json.getString("status");
                        Tag_Status = Integer.parseInt(status);
                        if (Tag_Status == 0)
                            error = json.getString("message");
                        if (Tag_Status == 1) {
                            //saving cookies
                            SharedPreferences.Editor editor = somedata.edit();
                            editor.putString("cookie", httpClient.getCookieStore().getCookies().toString());
                            editor.commit();
                            Log.e("Cookies : ", somedata.getString("cookie", null));
                        }
                    } catch (Exception e) {
                        Log.e("log_tag", "Error converting result " + e.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * */
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
            if(Tag_Status==1){
                Intent i=new Intent(getApplicationContext(),MyActivity.class);
                startActivity(i);
                finish();
            }
            else if(Tag_Status==0){
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
            }
        }
    }
    //return Cookies
    public static String getCookie() {
        return somedata.getString("cookie",null);
    }
}