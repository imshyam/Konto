package me.imshyam.konto;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by shyam on 1/8/2015.
 */
public class Config{
    public static String cookie=sign_in.getCookie();
    static String name1;
    static String name2;
    static String value1;
    static String value2;
    //Reading Cookies
    public static String assignValue() throws JSONException {


        List<String> cookieList=new ArrayList<String>(Arrays.asList(cookie.split(",")));
        JSONObject jsonObj;
        String cookies = String.valueOf(cookieList.get(0));
        cookies =cookies.substring(1);
        cookies = cookies.replace("[", "\"");
        cookies = cookies.replace("]", "\"");
        cookies = cookies.replace(":", "\":\"");
        cookies = "{" + cookies + "}";
        cookies=cookies.replaceAll("\\s", "");
        cookies=cookies.replace("\"\"","\",\"");
        jsonObj=new JSONObject(cookies);
        name1=jsonObj.getString("name");
        value1=jsonObj.getString("value");

        cookies = String.valueOf(cookieList.get(1));
        cookies =cookies.substring(0,cookies.length()-1);
        cookies = cookies.replace("[", "\"");
        cookies = cookies.replace("]", "\"");
        cookies = cookies.replace(":", "\":\"");
        cookies = "{" + cookies + "}";
        cookies=cookies.replaceAll("\\s", "");
        cookies=cookies.replace("\"\"","\",\"");
        jsonObj=new JSONObject(cookies);
        name2=jsonObj.getString("name");
        value2=jsonObj.getString("value");

        return name1+","+value1+","+name2+","+value2;

    }
    public static String url_getAll(){
        //url
        return "http://shyamu.herokuapp.com/mobile/getall";
    }
    public static String url_add(){
        //url
        return  "http://shyamu.herokuapp.com/mobile/add";
    }
    public static String url_getNoti(){
        //url
        return "http://shyamu.herokuapp.com/mobile/notification";
    }
    public static String url_delNot(){
        //url
        return "http://shyamu.herokuapp.com/mobile/notification/delete";
    }
    public static String url_signup(){
        //url
        return "http://shyamu.herokuapp.com/mobile/signup";
    }

}
