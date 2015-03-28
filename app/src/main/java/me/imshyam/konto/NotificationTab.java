package me.imshyam.konto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by shyamsundar on 10/17/2014.
 */
public class NotificationTab extends Fragment {
    ProgressDialog pDialog;
    //url
    private static final String url_getNoti =Config.url_getNoti();
    //Json nodes
    private static int Tag_Status = 0;
    public static String error="No Network.";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        //set title
//        ((MyActivity) getActivity())
//                .setActionBarTitle("Notifications");
        setHasOptionsMenu(true);
        new GetNoti().execute();
        View rootView = inflater.inflate(R.layout.notific, container, false);
        final TextView tv=(TextView)rootView.findViewById(R.id.textView);
        Button b=(Button)rootView.findViewById(R.id.btn);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("kdfjdfhksdfhksdjf");
            }
        });
        return rootView;
    }
    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.my, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_refresh:
                new GetNoti().execute();
                return true;
            case R.id.action_logout:
                SharedPreferences preferences = getActivity().getSharedPreferences("MyShPre", 0);
                preferences.edit().clear().commit();
                Intent i =new Intent(getActivity().getApplicationContext(),sign_in.class);
                startActivity(i);
                return true;
            case R.id.action_locally:
                Intent i1 =new Intent(getActivity().getApplicationContext(),Locally.class);
                startActivity(i1);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /**
     * Background Async Task to add Notifications
     * */
    class GetNoti extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Adding. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * signing in in background thread
         */
        protected String doInBackground(String... params) {
            // Building Parameters
            HttpPost httpPost = new HttpPost(url_getNoti);
            DefaultHttpClient httpClient = new DefaultHttpClient();

            JSONObject json;
            JSONObject jGroup = new JSONObject();

            try {
                jGroup.put("unread", "1");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            httpPost.setHeader("Authorization", "de0464e0-552e-11e4-8c11-843497188779");

            String cookiesString =sign_in.getCookie();
            cookiesString=cookiesString.substring(1,cookiesString.length()-1);
            Log.e("CookieString", cookiesString);
            String[] cookieListS=cookiesString.split(",");

            //Formatting String For JsonObject
            cookieListS[0]=cookieListS[0].replace("][","\",\"");
            cookieListS[0]=cookieListS[0].replace("[","\"");
            cookieListS[0]=cookieListS[0].replace("]","\"");
            cookieListS[0]=cookieListS[0].replace(":","\"=\"");
            cookieListS[0]=cookieListS[0].replaceAll("\\s+","");

            cookieListS[1]=cookieListS[1].replace("][", "\",\"");
            cookieListS[1]=cookieListS[1].replace("[", "\"");
            cookieListS[1]=cookieListS[1].replace("]", "\"");
            cookieListS[1]=cookieListS[1].replace(":", "\"=\"");
            cookieListS[1]=cookieListS[1].replaceAll("\\s+", "");

            //creating JsonObject To Access Elements of String
            JSONObject jsonObject1 = null;
            try {
                jsonObject1=new JSONObject("{"+cookieListS[0]+"}");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Log.e("JsonObj",jsonObject1.toString());
            final JSONObject finalJsonObject = jsonObject1;

            //Assigning Values To Cookie
            Cookie cookie1=new Cookie() {
                @Override
                public String getName() {
                    String s=null;
                    try {
                        s= finalJsonObject.getString("name");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return s;
                }

                @Override
                public String getValue() {
                    String s=null;
                    try {
                        s= finalJsonObject.getString("value");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return s;
                }

                @Override
                public String getComment() {
                    return null;
                }

                @Override
                public String getCommentURL() {
                    return null;
                }

                @Override
                public Date getExpiryDate() {
                    return null;
                }

                @Override
                public boolean isPersistent() {
                    return false;
                }

                @Override
                public String getDomain() {
                    String s=null;
                    try {
                        s= finalJsonObject.getString("domain");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return s;
                }

                @Override
                public String getPath() {
                    String s=null;
                    try {
                        s= finalJsonObject.getString("path");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return s;
                }

                @Override
                public int[] getPorts() {
                    return new int[0];
                }

                @Override
                public boolean isSecure() {
                    return false;
                }

                @Override
                public int getVersion() {
                    int  s=0;
                    try {
                        String str= finalJsonObject.getString("version");
                        s=Integer.parseInt(str);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return s;
                }

                @Override
                public boolean isExpired(Date date) {
                    return false;
                }
            };

            //Second Cookie
            JSONObject jsonObject2 = null;
            try {
                jsonObject2=new JSONObject("{"+cookieListS[1]+"}");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Log.e("JsonObj 2",jsonObject2.toString());
            final JSONObject finalJsonObject1 = jsonObject2;
            Cookie cookie2=new Cookie() {
                @Override
                public String getName() {
                    String s=null;
                    try {
                        s= finalJsonObject1.getString("name");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return s;
                }

                @Override
                public String getValue() {
                    String s=null;
                    try {
                        s= finalJsonObject1.getString("value");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return s;
                }

                @Override
                public String getComment() {
                    return null;
                }

                @Override
                public String getCommentURL() {
                    return null;
                }

                @Override
                public Date getExpiryDate() {
                    return null;
                }

                @Override
                public boolean isPersistent() {
                    return false;
                }

                @Override
                public String getDomain() {
                    String s=null;
                    try {
                        s= finalJsonObject1.getString("domain");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return s;
                }

                @Override
                public String getPath() {
                    String s=null;
                    try {
                        s= finalJsonObject1.getString("path");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return s;
                }

                @Override
                public int[] getPorts() {
                    return new int[0];
                }

                @Override
                public boolean isSecure() {
                    return false;
                }

                @Override
                public int getVersion() {
                    int  s=0;
                    try {
                        String str= finalJsonObject1.getString("version");
                        s=Integer.parseInt(str);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return s;
                }

                @Override
                public boolean isExpired(Date date) {
                    return false;
                }
            };

            //Adding Cookies To CookieStore
            CookieStore cookieStore = new BasicCookieStore();
            cookieStore.addCookie(cookie1);
            cookieStore.addCookie(cookie2);

            //HttpContext For Sending Cookies To HttpPost Request
            HttpContext localContext = null;
            final String STR = "UTF-8";
            try {
                localContext   = new BasicHttpContext();
                localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
                httpPost.setEntity(new StringEntity(jGroup.toString(), STR));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                HttpResponse response = httpClient.execute(httpPost,localContext);
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

                    Log.e("This is what we get : ", result);
                    json = new JSONObject(result);
                    String status = json.getString("status");
                    Tag_Status = Integer.parseInt(status);
                    if (Tag_Status == 0)
                        error = json.getString("message");
                } catch (Exception e) {
                    Log.e("log_tag", "Error converting result " + e.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
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
                //Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
            }
            else if(Tag_Status==0){
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
