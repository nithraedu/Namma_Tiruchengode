package nithra.namma_tiruchengode.Fragment;

import android.os.Bundle;
import android.util.Log;
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

import nithra.namma_tiruchengode.R;
import nithra.namma_tiruchengode.Retrofit.AddPojo;
import nithra.namma_tiruchengode.Retrofit.Otp_check_and_category;
import nithra.namma_tiruchengode.Retrofit.Otp_check_category;
import nithra.namma_tiruchengode.Retrofit.RetrofitAPI;
import nithra.namma_tiruchengode.Retrofit.RetrofitAPIClient;
import nithra.namma_tiruchengode.SharedPreference;
import nithra.namma_tiruchengode.Utils_Class;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add extends Fragment {
    Otp_check_and_category main_titles;
    Spinner list_category, list_subcategory;
    ArrayList<String> spin;
    ArrayList<String> spin_1;
    TextInputEditText shop_txt, add_txt, num_txt, what_txt, email_txt, web_txt, open_txt, close_txt, details;
    EditText locat_link, fb_link, insta_link, twit_link;
    TextView submit, cancel;
    String spin_id1, spin_id2;
    String shop_name, shop_add, mob_num, what_num, email, description, web, open_time, close_time, location, facebook, insta, twitter;
    SharedPreference sharedPreference = new SharedPreference();


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
        cancel = view.findViewById(R.id.cancel);
        details = view.findViewById(R.id.details);

        main_titles = new Otp_check_and_category();
        spin = new ArrayList<>();
        spin_1 = new ArrayList<>();

            otp_verify();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shop_name = shop_txt.getText().toString().trim();
                shop_add = add_txt.getText().toString().trim();
                mob_num = num_txt.getText().toString().trim();
                what_num = what_txt.getText().toString().trim();
                email = email_txt.getText().toString().trim();
                web = web_txt.getText().toString().trim();
                open_time = open_txt.getText().toString().trim();
                close_time = close_txt.getText().toString().trim();
                location = locat_link.getText().toString().trim();
                facebook = fb_link.getText().toString().trim();
                insta = insta_link.getText().toString().trim();
                twitter = twit_link.getText().toString().trim();
                description = details.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


                if (list_category.getSelectedItemPosition() == 0) {
                    Utils_Class.toast_center(getContext(), "Please select category...");
                } else if (list_subcategory.getSelectedItemPosition() == 0) {
                    Utils_Class.toast_center(getContext(), "Please select Subcategory...");
                } else if (shop_name.equals("")) {
                    Utils_Class.toast_center(getContext(), "Please Enter Your Name...");
                } else if (shop_add.equals("")) {
                    Utils_Class.toast_center(getContext(), "Please Enter Your address...");
                } else if (mob_num.length() < 10) {
                    Utils_Class.toast_center(getContext(), "Please Enter Correct Mobile Number...");
                } else if (what_num.length() < 10) {
                    Utils_Class.toast_center(getContext(), "Please Enter Correct Whatsapp Mobile Number...");
                } else if (email.equals("")) {
                    Utils_Class.toast_center(getContext(), "Please Enter Your Email...");
                } else if (!email.matches(emailPattern)) {
                    Toast.makeText(getContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                } else if (description.equals("")) {
                    Utils_Class.toast_center(getContext(), "Please Enter Your Details...");
                } else {

                    submit_res();

                }

            }
        });

        Utils_Class.mProgress(getContext(), "Loading please wait...", false).show();


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        return view;

    }


    public void submit_res() {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "add_post");
        map.put("category", "" + spin_id1);
        map.put("sub_category", "" + spin_id2);
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
        map.put("description", description);


        RetrofitAPI retrofitAPI = RetrofitAPIClient.getRetrofit().create(RetrofitAPI.class);
        Call<ArrayList<AddPojo>> call = retrofitAPI.getadd_user(map);
        call.enqueue(new Callback<ArrayList<AddPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<AddPojo>> call, Response<ArrayList<AddPojo>> response) {
                if (response.isSuccessful()) {
                    String result = new Gson().toJson(response.body());
                    System.out.println("======response result:" + result);
                    if (response.body().get(0).getStatus().equals("Success")) {
                        list_category.setSelection(0);
                        //list_subcategory.setSelection(0);
                        spin_1.clear();
                        list_subcategory.setEnabled(false);
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
                        details.getText().clear();
                        Toast.makeText(getContext(), "Your shop added successfully, Thank you", Toast.LENGTH_SHORT).show();
                    }

                }
                System.out.println("======response :" + response);
            }

            @Override
            public void onFailure(Call<ArrayList<AddPojo>> call, Throwable t) {
                System.out.println("======response t:" + t);
            }
        });


    }


    public void otp_verify() {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "get_cate_sub");
        RetrofitAPI retrofitAPI = RetrofitAPIClient.getRetrofit().create(RetrofitAPI.class);
        Call<Otp_check_and_category> call = retrofitAPI.getOtp_verify_category(map);
        call.enqueue(new Callback<Otp_check_and_category>() {
            @Override
            public void onResponse(Call<Otp_check_and_category> call, Response<Otp_check_and_category> response) {
                if (response.isSuccessful()) {
                    //System.out.println("checkres"+response);
                    Log.i("checkres : ", "" + response);

                    String result = new Gson().toJson(response.body());
                    System.out.println("======response result:" + result);
                    main_titles = (response.body());
                    spinner();
                    Utils_Class.mProgress.dismiss();
                }
                System.out.println("======response :" + response);
            }

            @Override
            public void onFailure(Call<Otp_check_and_category> call, Throwable t) {
                System.out.println("======response t:" + t);
            }
        });
    }


    public void spinner() {
        spin.add(0, "All category");
        for (int i = 1; i < main_titles.getCategory().size(); i++) {
            spin.add(main_titles.getCategory().get(i).category);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spin);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        list_category.setAdapter(adapter);
        list_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    spin_id1 = main_titles.getCategory().get(i - 1).getId();
                }
                if (i == 0) {
                    //sub_title.clear();
                    spin_1.add(0, "Sub category");
                    list_subcategory.setEnabled(false);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spin_1);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    list_subcategory.setAdapter(adapter);
                } else {
                    list_subcategory.setEnabled(true);
                    spin_1.clear();
                    spinner_1(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        adapter.notifyDataSetChanged();

    }


    public void spinner_1(int pos) {
        spin_1.add(0, "Sub category");
        for (int i = 0; i < main_titles.getCategory().get(pos).getSubCategory().size(); i++) {
            spin_1.add(main_titles.getCategory().get(pos).getSubCategory().get(i).subCategory);
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spin_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        list_subcategory.setAdapter(adapter);
        list_subcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0) {
                    spin_id2 = main_titles.getCategory().get(pos).getSubCategory().get(i-1).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        adapter.notifyDataSetChanged();

    }

    public void clear() {
        list_category.setSelection(0);
        spin_1.clear();
        list_subcategory.setEnabled(false);
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
        details.getText().clear();
    }


}