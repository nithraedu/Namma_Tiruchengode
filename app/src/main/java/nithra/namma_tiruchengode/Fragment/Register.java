package nithra.namma_tiruchengode.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import nithra.namma_tiruchengode.Gotohome;
import nithra.namma_tiruchengode.R;
import nithra.namma_tiruchengode.Retrofit.OtpGenerate;
import nithra.namma_tiruchengode.Retrofit.RetrofitAPI;
import nithra.namma_tiruchengode.Retrofit.RetrofitAPIClient;
import nithra.namma_tiruchengode.SharedPreference;
import nithra.namma_tiruchengode.Utils_Class;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends Fragment {
    TextInputEditText reg_name, reg_number;
    TextView get_otp;
    SharedPreferences pref;
    LinearLayout register;
    Gotohome home;
    String name, number;
    SharedPreference sharedPreference = new SharedPreference();
    ArrayList<OtpGenerate> otp_gene;


    public Register() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        pref = getContext().getSharedPreferences("register", Context.MODE_PRIVATE);
        reg_name = view.findViewById(R.id.reg_name);
        reg_number = view.findViewById(R.id.reg_number);
        get_otp = view.findViewById(R.id.get_otp);
        register = view.findViewById(R.id.register);
        home = (Gotohome) getContext();
        otp_gene = new ArrayList<OtpGenerate>();

        get_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = reg_name.getText().toString().trim();
                number = reg_number.getText().toString().trim();
                if (name.equals("")) {
                    Utils_Class.toast_center(getContext(), "Please Enter Your Name...");
                } else if (number.equals("")) {
                    Utils_Class.toast_center(getContext(), "Please Enter Your Mobile Number...");
                } else if (number.length() !=10) {
                    Utils_Class.toast_center(getContext(), "Please Enter Correct Mobile Number...");
                }else {

                    if (Utils_Class.isNetworkAvailable(getContext())) {

                        otp_generate();
                    } else {
                        Utils_Class.toast_normal(getContext(), "Please connect to your internet");
                    }

                    sharedPreference.putString(requireActivity(), "resend", "" + number);
                }
            }
        });
        return view;
    }


    public void otp_generate() {
        Utils_Class.mProgress(getContext(), "Loading please wait...", false).show();


        HashMap<String, String> map = new HashMap<>();
        map.put("action", "otp_generate");
        map.put("name", name);
        map.put("mobile_num", number);
        map.put("android_id", Utils_Class.android_id(getContext()));
        map.put("is_check", "1");
        RetrofitAPI retrofitAPI = RetrofitAPIClient.getRetrofit().create(RetrofitAPI.class);
        Call<ArrayList<OtpGenerate>> call = retrofitAPI.getOtp_generate(map);
        call.enqueue(new Callback<ArrayList<OtpGenerate>>() {
            @Override
            public void onResponse(Call<ArrayList<OtpGenerate>> call, Response<ArrayList<OtpGenerate>> response) {
                if (response.isSuccessful()) {
                    String result = new Gson().toJson(response.body());
                    System.out.println("======response result:" + result);
                    if (response.body().get(0).getStatus().equals("success")) {
                            otp_gene.addAll(response.body());
                            sharedPreference.putString(requireActivity(), "register_otp", "" + otp_gene.get(0).getOtp());
                            reg_name.getText().clear();
                            reg_number.getText().clear();
                            home.verify();
                    }
                    Utils_Class.mProgress.dismiss();
                }
                System.out.println("======response :" + response);
            }

            @Override
            public void onFailure(Call<ArrayList<OtpGenerate>> call, Throwable t) {
                System.out.println("======response t:" + t);
            }
        });
    }
}