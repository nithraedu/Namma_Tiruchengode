package nithra.namma_tiruchengode;

import static android.text.TextUtils.isEmpty;
import static androidx.core.content.pm.PackageInfoCompat.getLongVersionCode;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;

import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by NITHRA-G5 on 12/2/2015.
 */
public class Utils {

    public static ProgressDialog mProgress;

    public static ProgressDialog mProgress(Context context, String txt, Boolean aBoolean) {
        mProgress = new ProgressDialog(context);
        mProgress.setMessage(txt);
        mProgress.setCancelable(aBoolean);
        return mProgress;
    }


    public static int versioncode_get(Context context) {
        PackageInfo pInfo = null;

        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return (int) getLongVersionCode(pInfo);
    }

    public static String versionname_get(Context context) {
        PackageInfo pInfo = null;

        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return pInfo.versionName;
    }

    public static String[] empty_array(int val) {
        String str[] = new String[val];
        for (int i = 0; i > val; i++) {
            str[i] = "";
        }
        return str;
    }

    public static String[] empty_array_f(int val, String sdate) {
        System.out.println("empty_array_f : " + sdate);
        String[] str = new String[val];
        for (int i = 0; i < val; i++) {
            System.out.println("empty_array_f : " + i);
            String[] str_date = sdate.split("/");
            Calendar c2 = Calendar.getInstance();
            c2.set(Calendar.DAY_OF_MONTH, Integer.parseInt(str_date[0]));
            c2.set(Calendar.MONTH, Integer.parseInt(str_date[1]) - 1);
            c2.set(Calendar.YEAR, Integer.parseInt(str_date[2]));
            c2.add(Calendar.DAY_OF_MONTH, -(i + 1));

            str[i] = "" + c2.get(Calendar.DAY_OF_MONTH);
        }
        List<String> list = Arrays.asList(str);
        // Reversing the list using Collections.reverse() method
        Collections.reverse(list);
        // Converting list back to Array
        return list.toArray(str);
    }

    public static String[] empty_array_L(int val, String sdate) {
        System.out.println("empty_array_l : " + sdate);
        String[] str = new String[val];
        for (int i = 0; i < val; i++) {
            System.out.println("empty_array_l : " + i);
            String[] str_date = sdate.split("/");
            Calendar c2 = Calendar.getInstance();
            c2.set(Calendar.DAY_OF_MONTH, Integer.parseInt(str_date[0]));
            c2.set(Calendar.MONTH, Integer.parseInt(str_date[1]) - 1);
            c2.set(Calendar.YEAR, Integer.parseInt(str_date[2]));
            c2.add(Calendar.DAY_OF_MONTH, (i + 1));

            str[i] = "" + c2.get(Calendar.DAY_OF_MONTH);
        }
        return str;
    }



    public static int[] empty_array1(int val) {
        int str[] = new int[val];
        for (int i = 0; i > val; i++) {
            str[i] = 0;
        }
        return str;
    }


    public static String[] mergeArrays(String[] mainArray, String[] addArray) {
        String[] finalArray = new String[mainArray.length + addArray.length];
        System.arraycopy(mainArray, 0, finalArray, 0, mainArray.length);
        System.arraycopy(addArray, 0, finalArray, mainArray.length, addArray.length);

        return finalArray;
    }

    public static int[] mergeArrays1(int[] mainArray, int[] addArray) {
        int[] finalArray = new int[mainArray.length + addArray.length];
        System.arraycopy(mainArray, 0, finalArray, 0, mainArray.length);
        System.arraycopy(addArray, 0, finalArray, mainArray.length, addArray.length);

        return finalArray;
    }

    public static String date_change(String strr) {

        String[] str1 = strr.split("\\ ");


        String[] str2 = str1[0].split("\\-");

        /*String[] str3 = str1[1].split("\\:");*/

        return "" + str2[2] + "/" + str2[1] + "/" + str2[0];
    }

    public static int get_weekday(String str) {
        int val = 0;
        if (str.equals("ఆదివారము")) {
            val = 0;
        } else if (str.equals("సోమవారము")) {
            val = 1;
        } else if (str.equals("మంగళవారము")) {
            val = 2;
        } else if (str.equals("బుధవారము")) {
            val = 3;
        } else if (str.equals("గురువారము")) {
            val = 4;
        } else if (str.equals("శుక్రవారము")) {
            val = 5;
        } else if (str.equals("శనివారము")) {
            val = 6;
        }
        return val;
    }

    public static String get_samaskirtham(String str) {
        String val = "";
        if (str.equals("ఆదివారము")) {
            val = "భాను వాసరః";
        } else if (str.equals("సోమవారము")) {
            val = "ఇందు వాసరః";
        } else if (str.equals("మంగళవారము")) {
            val = "భౌమ వాసరః";
        } else if (str.equals("బుధవారము")) {
            val = "సౌమ్య వాసరః";
        } else if (str.equals("గురువారము")) {
            val = "బృహస్పతి వాసరః";
        } else if (str.equals("శుక్రవారము")) {
            val = "భృగు వాసరః";
        } else if (str.equals("శనివారము")) {
            val = "స్థిర వాసరః";
        }
        return val;
    }


    public static String get_month(int val) {
        String str = "";
        if (val == 0) {
            str = "జనవరి";
        } else if (val == 1) {
            str = "ఫిబ్రవరి";
        } else if (val == 2) {
            str = "మార్చి";
        } else if (val == 3) {
            str = "ఏప్రిల్";
        } else if (val == 4) {
            str = "మే";
        } else if (val == 5) {
            str = "జూన్";
        } else if (val == 6) {
            str = "జూలై";
        } else if (val == 7) {
            str = "ఆగస్టు";
        } else if (val == 8) {
            str = "సెప్టెంబర్";
        } else if (val == 9) {
            str = "అక్టోబర్";
        } else if (val == 10) {
            str = "నవంబర్";
        } else if (val == 11) {
            str = "డిసెంబర్";
        }

        return str;
    }

    public static String get_eng_month(int val) {
        String str = "";
        if (val == 1) {
            str = "January";
        } else if (val == 2) {
            str = "February";
        } else if (val == 3) {
            str = "March";
        } else if (val == 4) {
            str = "April";
        } else if (val == 5) {
            str = "May";
        } else if (val == 6) {
            str = "June";
        } else if (val == 7) {
            str = "July";
        } else if (val == 8) {
            str = "August";
        } else if (val == 9) {
            str = "September";
        } else if (val == 10) {
            str = "October";
        } else if (val == 11) {
            str = "November";
        } else if (val == 12) {
            str = "December";
        }

        return str;
    }


    public static int get_month_num(String str) {
        int val = 0;
        if (str.equals("జనవరి")) {
            val = 1;
        } else if (str.equals("ఫిబ్రవరి")) {
            val = 2;
        } else if (str.equals("మార్చి")) {
            val = 3;
        } else if (str.equals("ఏప్రిల్")) {
            val = 4;
        } else if (str.equals("మే")) {
            val = 5;
        } else if (str.equals("జూన్")) {
            val = 6;
        } else if (str.equals("జూలై")) {
            val = 7;
        } else if (str.equals("ఆగస్టు")) {
            val = 8;
        } else if (str.equals("సెప్టెంబర్")) {
            val = 9;
        } else if (str.equals("అక్టోబర్")) {
            val = 10;
        } else if (str.equals("నవంబర్")) {
            val = 11;
        } else if (str.equals("డిసెంబర్")) {
            val = 12;
        }

        return val;
    }

    public static void toast_normal(Context context, String str) {
        Toast.makeText(context, "" + str, Toast.LENGTH_SHORT).show();
    }

    public static void toast_center(Context context, String str) {
        Toast toast = Toast.makeText(context, "" + str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    public static String pad(String str) {
        if (str.length() == 1) {
            str = "0" + str;
        }
        return str;
    }


    public static String getDeviceName() {

        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String Brand = Build.BRAND;
        String Product = Build.PRODUCT;

        return manufacturer + "-" + model + "-" + Brand + "-" + Product;
    }

    public static boolean isNetworkAvailable(Context context) {
        boolean result = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (cm != null) {
                NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        result = true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        result = true;
                    }
                }
            }
        } else {
            if (cm != null) {
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null) {
                    // connected to the internet
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        result = true;
                    } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }


    public static String am_pm(int hur, int min) {

        String AM_PM = "AM";

        if (hur >= 12) {
            hur = hur - 12;
            AM_PM = "PM";
        } else {
            AM_PM = "AM";

        }
        if (hur == 0) {
            hur = 12;
        }

        return Utils.pad("" + hur) + " : " + Utils.pad("" + min) + " " + AM_PM;
    }

    public static String am_pm_2(int hur, int min) {

        String AM_PM = "AM";

        if (hur >= 12) {
            hur = hur - 12;
            AM_PM = "PM";
        } else {
            AM_PM = "AM";

        }
        if (hur == 0) {
            hur = 12;
        }

        return Utils.pad("" + hur) + ":" + Utils.pad("" + min) + ":" + AM_PM;
    }



    public static void data_insert(SQLiteDatabase myDB, String des, String date, String time, String day, String month, String year, String isremaind) {
        //notes (id integer NOT NULL PRIMARY KEY AUTOINCREMENT,des VARCHAR,date VARCHAR,time VARCHAR,day VARCHAR,month VARCHAR,year VARCHAR,isremaind INT(4),isclose INT(4));");
        ContentValues values = new ContentValues();

        values.put("des", des);
        values.put("date", date);
        values.put("time", time);
        values.put("day", day);
        values.put("month", month);
        values.put("year", year);
        values.put("isremaind", isremaind);
        values.put("isclose", "0");

        System.out.println("data_insert == " + values);
        myDB.insert("notes", null, values);
    }

    public static void data_update(SQLiteDatabase myDB, String des, String id, String time, String day, String month, String year, String isremaind, String datee) {
        ContentValues values = new ContentValues();
        values.put("des", des);
        values.put("date", datee);
        values.put("time", time);
        values.put("day", day);
        values.put("month", month);
        values.put("year", year);
        values.put("isremaind", isremaind);
        values.put("isclose", 0);
        System.out.println("data_update == " + values);
        myDB.update("notes", values, "id='" + id + "'", null);
    }

    public static int get_id(Context context, SQLiteDatabase myDB, String str, String str1, String msgg) {
        if (myDB == null) {
            myDB = context.openOrCreateDatabase("myDB1", 0, null);
        }
        int val = 0;
        try {
            Cursor c = myDB.rawQuery("select id from notes where date = '" + str + "' AND time = '" + str1 + "' AND des = '" + msgg + "' order by id desc limit 1", null);
            if (c.getCount() != 0) {
                c.moveToFirst();
                val = c.getInt(0);
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return val;
    }


    public static String get_curday(int val) {
        Calendar cal = Calendar.getInstance();
        //  String month = Utils.get_month(cal.get(Calendar.MONTH));
        if (val != 0) {
            cal.add(Calendar.DAY_OF_MONTH, val);
        }
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day + "/" + (month + 1) + "/" + year;
    }


    public static boolean rate_check(Context context) {
        boolean shown = false;
        SharedPreference sharedPreference = new SharedPreference();


        Calendar calendar = Calendar.getInstance();
        long next_hour = calendar.getTimeInMillis();

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/M/yyyy");
        Date result = new Date(next_hour);
        String formatted = sdf1.format(result);

        StringTokenizer st2 = new StringTokenizer(formatted, "/");
        int rep_day = Integer.parseInt(st2.nextToken());
        int rep_month = Integer.parseInt(st2.nextToken());
        int rep_year = Integer.parseInt(st2.nextToken());

        rep_month = rep_month - 1;

        String today_date = rep_day + "/" + rep_month + "/" + rep_year;

        Date date_today = null, date_app_update = null;

        try {
            date_today = sdf1.parse(today_date);
            if (!sharedPreference.getString(context, "rate_date").equals("")) {
                date_app_update = sdf1.parse(sharedPreference.getString(context, "rate_date"));
            } else {
                date_app_update = sdf1.parse(today_date);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (sharedPreference.getInt(context, "rate_show") == 0) {
            shown = true;
            sharedPreference.putInt(context, "rate_show", 1);
            Utils.date_put(context, "rate_date", 15);
        } else if (sharedPreference.getInt(context, "rate_show") == 1) {
            if (date_today.compareTo(date_app_update) >= 0) {
                shown = true;
                sharedPreference.putInt(context, "rate_show", 2);
                Utils.date_put(context, "rate_date", 30);
            } else {
                shown = false;
            }

        } else if (sharedPreference.getInt(context, "rate_show") == 2) {
            if (date_today.compareTo(date_app_update) >= 0) {
                shown = true;
                sharedPreference.putInt(context, "rate_show", 2);
                Utils.date_put(context, "rate_date", 60);
            } else {
                shown = false;
            }

        } else {
            shown = false;
        }

        return shown;
    }


    public static void date_put(Context context, String str, int val) {
        Calendar calendar = Calendar.getInstance();
        long next_hour = calendar.getTimeInMillis() + val * DateUtils.DAY_IN_MILLIS;

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/M/yyyy");
        Date results = new Date(next_hour);
        String formatted = sdf1.format(results);

        StringTokenizer st2 = new StringTokenizer(formatted, "/");
        int rep_day = Integer.parseInt(st2.nextToken());
        int rep_month = Integer.parseInt(st2.nextToken());
        int rep_year = Integer.parseInt(st2.nextToken());

        rep_month = rep_month - 1;

        String strdate = rep_day + "/" + rep_month + "/" + rep_year;

        SharedPreference sharedPreference = new SharedPreference();
        sharedPreference.putString(context, str, strdate);
    }

    public static boolean update_check(Context context) {

        Boolean aBoolean = false;
        SharedPreference sharedPreference = new SharedPreference();
        Calendar calendar = Calendar.getInstance();
        long next_hour = calendar.getTimeInMillis();

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/M/yyyy");
        Date result = new Date(next_hour);
        String formatted = sdf1.format(result);

        StringTokenizer st2 = new StringTokenizer(formatted, "/");
        int rep_day = Integer.parseInt(st2.nextToken());
        int rep_month = Integer.parseInt(st2.nextToken());
        int rep_year = Integer.parseInt(st2.nextToken());

        rep_month = rep_month - 1;

        String today_date = rep_day + "/" + rep_month + "/" + rep_year;

        Date date_today = null, date_app_update = null;

        try {
            date_today = sdf1.parse(today_date);
            if (!sharedPreference.getString(context, "update_date").equals("")) {
                date_app_update = sdf1.parse(sharedPreference.getString(context, "update_date"));
            } else {
                date_app_update = sdf1.parse(today_date);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (sharedPreference.getString(context, "update_date").equals("")) {
            Utils.date_put(context, "update_date", 7);
            aBoolean = true;
        } else {
            if (date_today.compareTo(date_app_update) >= 0) {
                Utils.date_put(context, "update_date", 7);
                aBoolean = true;
            }
        }

        System.out.println("putdate_update_date---" + aBoolean);

        return aBoolean;
    }


    public static String am_pm1(int hur, int min) {

        String AM_PM = "AM";

        if (hur >= 12) {
            hur = hur - 12;
            AM_PM = "PM";
        } else {
            AM_PM = "AM";

        }
        if (hur == 0) {
            hur = 12;
        }

        return Utils.pad("" + hur) + " : " + Utils.pad("" + min) + " " + AM_PM;
    }


    public static void ClearCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
            System.out.println("clr_chace : error ClearCache");
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public static Boolean clr_chace(Context context) {
        Boolean aBoolean = false;
        SharedPreference sharedPreference = new SharedPreference();
        Calendar calendar = Calendar.getInstance();
        long next_hour = calendar.getTimeInMillis();

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/M/yyyy");
        Date result = new Date(next_hour);
        String formatted = sdf1.format(result);

        StringTokenizer st2 = new StringTokenizer(formatted, "/");
        int rep_day = Integer.parseInt(st2.nextToken());
        int rep_month = Integer.parseInt(st2.nextToken());
        int rep_year = Integer.parseInt(st2.nextToken());

        rep_month = rep_month - 1;

        String today_date = rep_day + "/" + rep_month + "/" + rep_year;

        Date date_today = null, date_app_update = null;

        try {
            date_today = sdf1.parse(today_date);
            if (!sharedPreference.getString(context, "clr_chace").equals("")) {
                date_app_update = sdf1.parse(sharedPreference.getString(context, "clr_chace"));
            } else {
                date_app_update = sdf1.parse(today_date);
            }

        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("clr_chace : error");
        }


        if (sharedPreference.getString(context, "clr_chace").equals("")) {

            aBoolean = true;
            System.out.println("clr_chace : " + aBoolean);
        } else {
            if (date_today.compareTo(date_app_update) >= 0) {
                aBoolean = true;
                System.out.println("clr_chace : " + aBoolean);
            }
        }


        return aBoolean;
    }


    @SuppressLint("WrongConstant")
    public static void custom_tabs(Context context, String url) {
        SharedPreference sharedPreference = new SharedPreference();
        String colorr = "#C79500";
        if (sharedPreference.getInt(context, "tab_flag") == 0) {
            if (sharedPreference.getString(context, "color_codee").equals("")) {
                sharedPreference.putString(context, "color_codee", "#CC004C");
            }
            colorr = sharedPreference.getString(context, "color_codee");
        } else if (sharedPreference.getInt(context, "tab_flag") == 1) {
            colorr = "#3A7CEC";
        } else if (sharedPreference.getInt(context, "tab_flag") == 2) {
            colorr = "#C79500";
        } else if (sharedPreference.getInt(context, "tab_flag") == 3) {
            colorr = "#274200";
        } else if (sharedPreference.getInt(context, "tab_flag") == 4) {
            colorr = "#6FBF00";
        } else {
            colorr = "#3A7CEC";
        }

        try {

            /*CustomTabColorSchemeParams params = new CustomTabColorSchemeParams.Builder()
                    .setNavigationBarColor(Color.parseColor(colorr))
                    .setToolbarColor(Color.parseColor(colorr))
                    .setSecondaryToolbarColor(Color.parseColor(colorr))
                    .build();*/


            CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                    //.setDefaultColorSchemeParams(params)
                    .setExitAnimations(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .build();

            customTabsIntent.intent.setPackage("com.android.chrome");
            customTabsIntent.launchUrl(context, Uri.parse(url));


        } catch (Exception e) {

            Log.e("almighty_push", "custom tab error : " + e.getMessage());

            e.printStackTrace();


            try {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.addCategory(Intent.CATEGORY_BROWSABLE);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            } catch (ActivityNotFoundException e1) {
                e1.printStackTrace();
                Log.e("almighty_push", "custom tab error 2 : " + e.getMessage());
            }
        }
    }

    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static SharedPreference prefnull(SharedPreference sharedPreference) {
        if (sharedPreference == null)
            return sharedPreference = new SharedPreference();
        else
            return sharedPreference = new SharedPreference();
    }
    public static SharedPreference prefnull() {

            return new SharedPreference();

    }


    public static String size(int size) {

        String hrSize;

        double m = size / 1048576.0;
        double g = size / 1073741824.0;


        DecimalFormat dec = new DecimalFormat("0.00");

        if (g > 1) {
            hrSize = dec.format(g).concat("GB");
        } else if (m > 1) {
            hrSize = dec.format(m).concat("MB");
        } else {
            hrSize = dec.format(size / 1048576.0).concat("MB");
        }

        return hrSize;

    }

    public static String size1(int size) {

        String hrSize;

        double m = size / 1048576.0;
        double g = size / 1073741824.0;


        DecimalFormat dec = new DecimalFormat("0.00");

        if (g > 1) {
            hrSize = dec.format(g).concat(" / Gbs");
        } else if (m > 1) {
            hrSize = dec.format(m).concat(" / Mbs");
        } else {
            hrSize = dec.format(size / 1024.0).concat(" / Kbs");
        }

        return hrSize;

    }

    public static int get_color(Context context) {
        SharedPreference sharedPreference = new SharedPreference();
        if (sharedPreference.getInt(context,"TEC_DESIGN_TYPE")==1){
        if (sharedPreference.getString(context, "color_codee").equals("")) {
            sharedPreference.putString(context, "color_codee", "#CC004C");
        }
        if (sharedPreference.getInt(context, "tab_flag") == 0) {
            return Color.parseColor(sharedPreference.getString(context, "color_codee"));
        } else if (sharedPreference.getInt(context, "tab_flag") == 1) {
            return Color.parseColor("#3A7CEC");
        } else if (sharedPreference.getInt(context, "tab_flag") == 2) {
            return Color.parseColor("#C79500");
        } else if (sharedPreference.getInt(context, "tab_flag") == 3) {
            return Color.parseColor("#274200");
        } else if (sharedPreference.getInt(context, "tab_flag") == 4) {
            return Color.parseColor("#6FBF00");
        } else {
            return sharedPreference.getInt(context, "color_vibrant");
        }
    }
 else{
            return get_color1(context);
        }
    }
    public static int get_color1(Context context) {
        SharedPreference sharedPreference = new SharedPreference();
        if (sharedPreference.getString(context, "color_codee1").equals("")) {
            sharedPreference.putString(context, "color_codee1", "#FF3A52");
        }
        return Color.parseColor(sharedPreference.getString(context, "color_codee1"));
    }


    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }





    public static String kocaram_create(String c1, String c2, String c3, String c4, String c5, String c6, String c7,
                                        String c8, String c9, String c10, String c11, String c12, String mainn, String color) {


        String str_val = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<style>" +
                "table {" +
                "    border-collapse: collapse;" +
                "    width: 85%;" +
                "align : center" +
                "}" +
                "td, th {" +
                "    border: 2px solid " + color + ";" +
                "    text-align: center;" +
                "" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<table align='center'>" +
                "  <tr>" +
                "    <td width='55px' height='50px' >" + c1 + "</td>" +
                "    <td width='55px' height='50px'>" + c2 + "</td>" +
                "    <td width='55px' height='50px'>" + c3 + "</td>" +
                "    <td width='55px' height='50px'>" + c4 + "</td>" +
                "  </tr>" +
                "  <tr>" +
                "  <td width='55px' height='50px'>" + c5 + "</td>" +
                "    <td width='110px' height='100px'  rowspan='2'colspan='2'>" + mainn + "</td>" +
                "    <td width='55px' height='50px'>" + c6 + "</td>" +
                "  </tr>" +
                "  <tr>" +
                "    <td width='55px' height='50px'>" + c7 + "</td>" +
                "    <td width='55px' height='50px' colspan='2'>" + c8 + "</td> " +
                "  </tr>" +
                "  <tr>" +
                "    <td width='55px' height='50px'>" + c9 + "</td>" +
                "    <td width='55px' height='50px' >" + c10 + "</td>" +
                "    <td width='55px' height ='50px'>" + c11 + "</td>" +
                "<td width='55px' height='50px'>" + c12 + "</td>" +
                "  </tr>  " +
                "</table>" +
                "</body>" +
                "</html>";

        return str_val;
    }


    public static void share_fun(Context context, String str) {

        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.addCategory(Intent.CATEGORY_BROWSABLE);
        i.setData(Uri.parse("calendar://telugu_calendar/share:" + str.replaceAll("%", "%25")));
        context.startActivity(i);


    }

    public static String[] substringsBetween(final String str, final String open, final String close) {
        if (str == null || isEmpty(open) || isEmpty(close)) {
            return null;
        }
        final int strLen = str.length();
        if (strLen == 0) {
            return new String[0];
        }
        final int closeLen = close.length();
        final int openLen = open.length();
        final List<String> list = new ArrayList<>();
        int pos = 0;
        while (pos < strLen - closeLen) {
            int start = str.indexOf(open, pos);
            if (start < 0) {
                break;
            }
            start += openLen;
            final int end = str.indexOf(close, start);
            if (end < 0) {
                break;
            }
            list.add(str.substring(start, end));
            pos = end + closeLen;
        }
        if (list.isEmpty()) {
            return null;
        }
        return list.toArray(new String[list.size()]);
    }


    public static boolean appInstalledOrNot(String uri, Context context) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public static boolean mobileNumberValidation(String value) {
        String number = value.trim();
        String numberRegex = "^[6-9]\\d{9}$";
        Pattern pattern = Pattern.compile(numberRegex);
        Matcher matcher = pattern.matcher(number);

        return matcher.matches();
    }

    public static boolean isAlphabet(String value) {
        String number = value.trim();
        String numberRegex = "\"^[a-zA-Z]*$";
        Pattern pattern = Pattern.compile(numberRegex);
        Matcher matcher = pattern.matcher(number);

        return matcher.matches();
    }




}

