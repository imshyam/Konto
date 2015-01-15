package me.imshyam.konto;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import java.util.List;

/**
 * Created by shyamsundar on 10/17/2014.
 */
public class Home extends Fragment {
    public static final String ARG_OBJECT = "object";

    JSONObject json;
    ProgressDialog pDialog;
    TableLayout ll;
    Button b1,b2;
    //url
    private static final String url_getAll = "http://shyamu.herokuapp.com/mobile/getall";
    //Json nodes
    private static int Tag_Status = 0;
    public static String error="No Network.";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.home, container, false);

        b1=(Button)rootView.findViewById(R.id.button3);
        b2=(Button)rootView.findViewById(R.id.button4);
        ll=(TableLayout)rootView.findViewById(R.id.table);
        new GetAll().execute();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),Positive.class);
                startActivity(i);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),Negative.class);
                startActivity(i);
            }
        });
        return rootView;
    }
    /**
     * Background Async Task to add Notifications
     * */
    class GetAll extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * signing in in background thread
         */
        protected String doInBackground(String... params) {
            // Building Parameters
            HttpPost httpPost = new HttpPost(url_getAll);
            DefaultHttpClient httpClient = new DefaultHttpClient();


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
            localContext   = new BasicHttpContext();
            localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
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
            if(Tag_Status==1) {
                try {
                    String balance=json.getString("current_balance");
                    TableRow tbRow=new TableRow(getActivity());
                    TextView t0 = new TextView(getActivity());
                    t0.setTextSize(20);
                    t0.setText("");
                    TextView t = new TextView(getActivity());
                    t.setTextSize(20);
                    t.setText("Current Balance : ");
                    TextView tv = new TextView(getActivity());
                    tv.setWidth(500);
                    tv.setTextSize(20);
                    tv.setText(balance);
                    tbRow.addView(t0);
                    tbRow.addView(t);
                    tbRow.addView(tv);
                    ll.addView(tbRow);
                    View v1 = new View(getActivity());
                    LinearLayout.LayoutParams param1s = new LinearLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                    v1.setLayoutParams(param1s);
                    ll.addView(v1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            else if(Tag_Status==0){
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}

