package nithra.namma_tiruchengode.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import nithra.namma_tiruchengode.Enquiry.EnquiryMethod;
import nithra.namma_tiruchengode.Enquiry.EnquiryPojo;
import nithra.namma_tiruchengode.Enquiry.EnquiryRetrofitClient;
import nithra.namma_tiruchengode.R;
import nithra.namma_tiruchengode.Retrofit.AddPojo;
import nithra.namma_tiruchengode.Retrofit.Category;
import nithra.namma_tiruchengode.Retrofit.Category_Main;
import nithra.namma_tiruchengode.Retrofit.RetrofitAPI;
import nithra.namma_tiruchengode.Retrofit.RetrofitAPIClient;
import nithra.namma_tiruchengode.Retrofit.Sub_Category;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add extends Fragment {
    ArrayList<Category> titles;
    ArrayList<Sub_Category> sub_category;
    Spinner list_category, list_subcategory;
    ArrayList<String> spin;
    ArrayList<String> spin_1;
    TextInputEditText shop_txt, add_txt, num_txt, what_txt, email_txt, web_txt, open_txt, close_txt;
    EditText locat_link, fb_link, insta_link, twit_link;
    TextView submit;

    public Add() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);


        list_category = view.findViewById(R.id.list_category);
        list_subcategory = view.findViewById(R.id.list_subcategory);
        shop_txt = view.findViewById(R.id.shop_txt);
        add_txt = view.findViewById(R.id.add_txt);
        num_txt = view.findViewById(R.id.num_txt);
        what_txt = view.findViewById(R.id.what_txt);
        email_txt = view.findViewById(R.id.email_txt);
        web_txt = view.findViewById(R.id.web_txt);
        open_txt = view.findViewById(R.id.open_txt);
        close_txt = view.findViewById(R.id.close_txt);
        locat_link = view.findViewById(R.id.locat_link);
        fb_link = view.findViewById(R.id.fb_link);
        insta_link = view.findViewById(R.id.insta_link);
        twit_link = view.findViewById(R.id.twit_link);
        submit = view.findViewById(R.id.submit);

        titles = new ArrayList<Category>();
        spin = new ArrayList<>();
        spin_1 = new ArrayList<>();
        sub_category = new ArrayList<Sub_Category>();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shop_name = shop_txt.getText().toString().trim();
                String shop_add = add_txt.getText().toString().trim();
                String mob_num = num_txt.getText().toString().trim();
                String what_num = what_txt.getText().toString().trim();
                String email = email_txt.getText().toString().trim();
                String web = web_txt.getText().toString().trim();
                String open_time = open_txt.getText().toString().trim();
                String close_time = close_txt.getText().toString().trim();
                String location = locat_link.getText().toString().trim();
                String facebook = fb_link.getText().toString().trim();
                String insta = insta_link.getText().toString().trim();
                String twitter = twit_link.getText().toString().trim();

                HashMap<String, String> map = new HashMap<>();
                map.put("action", "add_post");
                map.put("category", ""+list_category.getSelectedItemPosition());
                map.put("sub_category", ""+list_category.getSelectedItemPosition());
                map.put("sector_name", shop_name);
                map.put("address", shop_add);
                map.put("mobile", mob_num);
                map.put("whats_app", what_num);
                map.put("email", email);
                map.put("website", web);
                map.put("start_time", open_time);
                map.put("end_time", close_time);
                map.put("location", location);
                map.put("facebook", facebook);
                map.put("instagram", insta);
                map.put("twitter", twitter);
                RetrofitAPI retrofitAPI = RetrofitAPIClient.getRetrofit().create(RetrofitAPI.class);
                Call<ArrayList<AddPojo>> call = retrofitAPI.getadd_user(map);
                call.enqueue(new Callback<ArrayList<AddPojo>>() {
                    @Override
                    public void onResponse(Call<ArrayList<AddPojo>> call, Response<ArrayList<AddPojo>> response) {
                        if (response.isSuccessful()) {
                            String result = new Gson().toJson(response.body());
                            System.out.println("======response result:" + result);
                            shop_txt.getText().clear();
                            add_txt.getText().clear();
                            num_txt.getText().clear();
                            what_txt.getText().clear();
                            email_txt.getText().clear();
                            web_txt.getText().clear();
                            open_txt.getText().clear();
                            close_txt.getText().clear();
                            locat_link.getText().clear();
                            fb_link.getText().clear();
                            insta_link.getText().clear();
                            twit_link.getText().clear();
                            Toast.makeText(getContext(), "Your shop added successfully, Thank you", Toast.LENGTH_SHORT).show();


                        }
                        System.out.println("======response :" + response);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<AddPojo>> call, Throwable t) {
                        System.out.println("======response t:" + t);
                    }
                });

            }
        });

        category_1();
        return view;

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
                    titles.addAll(response.body().get(0).getCategory());
                    spinner();
                    //adapter.notifyDataSetChanged();
                }
                System.out.println("======response :" + response);
            }

            @Override
            public void onFailure(Call<ArrayList<Category_Main>> call, Throwable t) {
                System.out.println("======response t:" + t);
            }
        });
    }

    public void spinner() {
        spin.add(0, "All category");
        for (int i = 0; i < titles.size(); i++) {
            spin.add(titles.get(i).category);
        }
      /*  for (int i = 0; i < MainActivity.titles.size(); i++) {
            spin.add(MainActivity.titles.get(i).category);
        }*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spin);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        list_category.setAdapter(adapter);
        //list_category.setSelection(pos);
        list_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
             /*   cat_title.setText(titles.get(i).category);
                subcategory(titles.get(i).getId());*/
                if (i == 0) {
                    sub_category.clear();
                    spin_1.add(0, "Sub category");
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spin_1);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    list_subcategory.setAdapter(adapter);
                } else {
                    sub_category.clear();
                    spin_1.clear();
                    subcategory(titles.get(i - 1).id);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        adapter.notifyDataSetChanged();

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
                   /* Sub_Category sub=new Sub_Category();
                    sub.setId("0");
                    sub.setSubCategory("Sub category");
                    sub_category.add(0, sub);*/
                    sub_category.addAll(response.body());
                    spinner_1();
                    //adapter = new ListAdapter(Activity_Second_List.this, sub_category, title);
                    //show_cat.setAdapter(adapter);
                    //adapter.notifyDataSetChanged();
                }
                System.out.println("======response :" + response);
            }

            @Override
            public void onFailure(Call<ArrayList<Sub_Category>> call, Throwable t) {
                System.out.println("======response t:" + t);
            }
        });
    }

    public void spinner_1() {
        //spin_1.add(0, "Sub category");
        for (int i = 0; i < sub_category.size(); i++) {
            spin_1.add(sub_category.get(i).subCategory);
        }


      /*  for (int i = 0; i < MainActivity.titles.size(); i++) {
            spin.add(MainActivity.titles.get(i).category);
        }*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spin_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        list_subcategory.setAdapter(adapter);
        //list_subcategory.setSelection(pos);
        list_subcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
             /*   cat_title.setText(titles.get(i).category);
                subcategory(titles.get(i).getId());*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        adapter.notifyDataSetChanged();

    }


}