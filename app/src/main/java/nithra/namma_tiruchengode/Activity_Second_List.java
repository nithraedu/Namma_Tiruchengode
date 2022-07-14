package nithra.namma_tiruchengode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import nithra.namma_tiruchengode.Retrofit.Category;
import nithra.namma_tiruchengode.Retrofit.Category_Main;
import nithra.namma_tiruchengode.Retrofit.RetrofitAPI;
import nithra.namma_tiruchengode.Retrofit.RetrofitAPIClient;
import nithra.namma_tiruchengode.Retrofit.Sub_Category;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Second_List extends AppCompatActivity implements Title_Interface {
    Spinner list_category;
    ArrayList<String> spin;
    ArrayList<Sub_Category> sub_category;
    ArrayList<Category> titles;
    RecyclerView show_cat;
    int pos,key,pos1;
    int spinPosition=-1;
    String title;
    TextView cat_title;
    Intent intent;
    Bundle extra;
    ListAdapter adapter;
    SharedPreference sharedPreference = new SharedPreference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_second_list);
        list_category = findViewById(R.id.list_category);
        show_cat = findViewById(R.id.show_cat);
        cat_title = findViewById(R.id.cat_title);
        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        intent = getIntent();
        extra = intent.getExtras();
        if (extra != null) {
            //title = extra.getString("toolbartitle");
            pos = extra.getInt("id");
            pos1 = extra.getInt("idd");
        }
        key=sharedPreference.getInt(getApplicationContext(),"key_send");
        titles = new ArrayList<Category>();
        spin = new ArrayList<>();
        sub_category = new ArrayList<Sub_Category>();


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        show_cat.setLayoutManager(gridLayoutManager);
        adapter = new ListAdapter(this, sub_category, title);
        show_cat.setAdapter(adapter);
        Utils_Class.mProgress(Activity_Second_List.this, "Loading please wait...", false).show();
        //subcategory(title);
        category_1();
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //list.setAdapter(adapter);// your code
              /*  titles.clear();
                spin.clear();
                category_1();*/
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    public void category_1() {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "get_category");
        RetrofitAPI retrofitAPI = RetrofitAPIClient.getRetrofit().create(RetrofitAPI.class);
        Call<ArrayList<Category_Main>> call = retrofitAPI.getCat_Main(map);
        call.enqueue(new Callback<ArrayList<Category_Main>>() {
            @Override
            public void onResponse(Call<ArrayList<Category_Main>> call, Response<ArrayList<Category_Main>> response) {
                if (response.isSuccessful()) {
                    String result = new Gson().toJson(response.body());
                    System.out.println("======response result:" + result);
                    titles.clear();
                    titles.addAll(response.body().get(0).getCategory());
                    spinner();
                    adapter.notifyDataSetChanged();
                }
                System.out.println("======response :" + response);
            }

            @Override
            public void onFailure(Call<ArrayList<Category_Main>> call, Throwable t) {
                System.out.println("======response t:" + t);
            }
        });
    }

    public void subcategory(String pos) {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "get_sub_category");
        map.put("category_id", pos);
        System.out.println("print_map" +map);
        RetrofitAPI retrofitAPI = RetrofitAPIClient.getRetrofit().create(RetrofitAPI.class);
        Call<ArrayList<Sub_Category>> call = retrofitAPI.getSubCategory(map);
        call.enqueue(new Callback<ArrayList<Sub_Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Sub_Category>> call, Response<ArrayList<Sub_Category>> response) {
                if (response.isSuccessful()) {
                    String result = new Gson().toJson(response.body());
                    System.out.println("======response result:" + result);
                    sub_category.clear();
                    sub_category.addAll(response.body());
                    adapter = new ListAdapter(Activity_Second_List.this, sub_category, title);
                    show_cat.setAdapter(adapter);
                    //adapter.notifyDataSetChanged();
                    Utils_Class.mProgress.dismiss();
                }
                System.out.println("======response :" + response);
            }

            @Override
            public void onFailure(Call<ArrayList<Sub_Category>> call, Throwable t) {
                System.out.println("======response t:" + t);
            }
        });
    }

    public void spinner() {
        //spin.add(0, "All category");
        for (int i = 0; i < titles.size(); i++) {
            spin.add(titles.get(i).category);
        }
      /*  for (int i = 0; i < MainActivity.titles.size(); i++) {
            spin.add(MainActivity.titles.get(i).category);
        }*/


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Activity_Second_List.this, android.R.layout.simple_spinner_item, spin);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        list_category.setAdapter(adapter);
        list_category.setSelection(key);
        list_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cat_title.setText(titles.get(i).category);
                spinPosition=i;
                subcategory(titles.get(i).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void category() {
        if (extra != null) {
            title = extra.getString("titlechange");
        }
    }
   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int LAUNCH_SECOND_ACTIVITY = 1;
        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                //category_1();
                //category_1();
                Log.i("test_pos", "pos  : " + pos);
                Log.i("test_pos", "pos1 : " + pos1);
                subcategory(titles.get(spinPosition).getId());
                System.out.println("code pass"+resultCode);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
                System.out.println("code fail"+resultCode);

            }
        }

    }*/
}