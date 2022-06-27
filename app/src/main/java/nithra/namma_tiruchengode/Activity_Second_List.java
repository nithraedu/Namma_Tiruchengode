package nithra.namma_tiruchengode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import nithra.namma_tiruchengode.Retrofit.Category;
import nithra.namma_tiruchengode.Retrofit.RetrofitAPI;
import nithra.namma_tiruchengode.Retrofit.RetrofitAPIClient;
import nithra.namma_tiruchengode.Retrofit.Sub_Category;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Second_List extends AppCompatActivity implements Title_Interface {
    Spinner list_category;
    ArrayList<String> spin;
    ArrayList<Sub_Category> sub_category;
    ArrayList<Category> titles;
    RecyclerView show_cat;
    int pos;
    String title;
    TextView cat_title;
    Intent intent;
    Bundle extra;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_second_list);
        list_category = findViewById(R.id.list_category);
        show_cat = findViewById(R.id.show_cat);
        cat_title = findViewById(R.id.cat_title);
        intent = getIntent();
        extra = intent.getExtras();
        if (extra != null) {
            title = extra.getString("toolbartitle");
            pos = extra.getInt("id");
        }
        titles = new ArrayList<Category>();
        spin = new ArrayList<>();
        sub_category = new ArrayList<Sub_Category>();


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        show_cat.setLayoutManager(gridLayoutManager);
        adapter = new ListAdapter(this, sub_category, title);
        show_cat.setAdapter(adapter);
        Utils.mProgress(Activity_Second_List.this, "Loading please wait...", false).show();
        subcategory(title);
        category_1();
    }

    public void category_1() {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "get_category");
        RetrofitAPI retrofitAPI = RetrofitAPIClient.getRetrofit().create(RetrofitAPI.class);
        Call<ArrayList<Category>> call = retrofitAPI.getCategory(map);
        call.enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                if (response.isSuccessful()) {
                    String result = new Gson().toJson(response.body());
                    System.out.println("======response result:" + result);
                    titles.addAll(response.body());
                    spinner();
                    adapter.notifyDataSetChanged();
                }
                System.out.println("======response :" + response);
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                System.out.println("======response t:" + t);
            }
        });
    }

    public void subcategory(String pos) {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "get_sub_category");
        map.put("category_id", pos);
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
                    Utils.mProgress.dismiss();
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
        spin.add(0, "All category");
        for (int i=0;i<titles.size();i++){
            spin.add(titles.get(i).category);
        }
      /*  for (int i = 0; i < MainActivity.titles.size(); i++) {
            spin.add(MainActivity.titles.get(i).category);
        }*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Activity_Second_List.this, android.R.layout.simple_spinner_item, spin);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        list_category.setAdapter(adapter);
        list_category.setSelection(pos);
        list_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cat_title.setText(titles.get(i-1).category);
                subcategory(titles.get(i-1).getId());
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
}