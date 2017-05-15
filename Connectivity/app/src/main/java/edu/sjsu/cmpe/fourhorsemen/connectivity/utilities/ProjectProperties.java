package edu.sjsu.cmpe.fourhorsemen.connectivity.utilities;

import android.content.Context;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by keyurgolani on 5/14/17.
 */

public class ProjectProperties {

    public static boolean init(Context context) {
        Properties prop = new Properties();
        try {
            prop.load(context.getAssets().open("properties.properties"));
            host = prop.getProperty("host");
            port = prop.getProperty("port");
            METHOD_VERSION = prop.getProperty("METHOD_VERSION");
            METHOD_REGISTER = prop.getProperty("METHOD_REGISTER");
            METHOD_VERIFY_ACCOUNT = prop.getProperty("METHOD_VERIFY_ACCOUNT");
            METHOD_SIGNIN = prop.getProperty("METHOD_SIGNIN");
            METHOD_EMAIL_AVAILABLE = prop.getProperty("METHOD_EMAIL_AVAILABLE");
            METHOD_FORGOT = prop.getProperty("METHOD_FORGOT");
            METHOD_UPDATE_PROFILE = prop.getProperty("METHOD_UPDATE_PROFILE");
            METHOD_FETCH_PROFILE = prop.getProperty("METHOD_FETCH_PROFILE");
            METHOD_FETCH_MESSAGES = prop.getProperty("METHOD_FETCH_MESSAGES");
            METHOD_SEND_MESSAGE = prop.getProperty("METHOD_SEND_MESSAGE");
            METHOD_FETCH_TIMELINE = prop.getProperty("METHOD_FETCH_TIMELINE");
            METHOD_ADD_POST = prop.getProperty("METHOD_ADD_POST");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String host;
    public static String port;
    public static String METHOD_VERSION;
    public static String METHOD_REGISTER;
    public static String METHOD_VERIFY_ACCOUNT;
    public static String METHOD_SIGNIN;
    public static String METHOD_EMAIL_AVAILABLE;
    public static String METHOD_FORGOT;
    public static String METHOD_UPDATE_PROFILE;
    public static String METHOD_FETCH_PROFILE;
    public static String METHOD_FETCH_MESSAGES;
    public static String METHOD_SEND_MESSAGE;
    public static String METHOD_FETCH_TIMELINE;
    public static String METHOD_ADD_POST;

    public static String getBaseURL() {
        return "http://" + host + ":" + port + "/";
    }

    public static String getURL(String method) {
        return "http://" + host + ":" + port + "/" + method;
    }
}
