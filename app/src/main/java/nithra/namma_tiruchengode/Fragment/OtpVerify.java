package nithra.namma_tiruchengode.Fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import nithra.namma_tiruchengode.Gotohome;
import nithra.namma_tiruchengode.R;
import nithra.namma_tiruchengode.Retrofit.OtpGenerate;
import nithra.namma_tiruchengode.Retrofit.OtpVerifyPojo;
import nithra.namma_tiruchengode.Retrofit.RetrofitAPI;
import nithra.namma_tiruchengode.Retrofit.RetrofitAPIClient;
import nithra.namma_tiruchengode.SharedPreference;
import nithra.namma_tiruchengode.Utils_Class;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerify extends Fragment {
    TextInputEditText otp;
    TextView verify_otp;
    Gotohome home;
    String verify;
    SharedPreference sharedPreference = new SharedPreference();
    ArrayList<OtpGenerate> otp_gene;


    public OtpVerify() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_otp_verify, container, false);
        otp = view.findViewById(R.id.otp);
        verify_otp = view.findViewById(R.id.verify_otp);
        home = (Gotohome) getContext();
        TextView _tv = view.findViewById(R.id.timer);
        otp_gene = new ArrayList<OtpGenerate>();
        new CountDownTimer(30000, 1000) { // adjust the milli seconds here
            public void onTick(long millisUntilFinished) {
                _tv.setText("If you didn't receive a otp?" + millisUntilFinished / 1000);
            }
            public void onFinish() {
                _tv.setText("If you didn't receive a otp? Resend");
                sharedPreference.putString(getContext(), "register_otp", "" + 0);
            }
        }.start();


        _tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sharedPreference.putString(getContext(), "register_otp_1", "register_otp");
                otp_generate();
                new CountDownTimer(60000, 1000) { // adjust the milli seconds here

                    public void onTick(long millisUntilFinished) {

                        _tv.setText("If you didn't receive a otp? " + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        _tv.setText("If you didn't receive a otp? Resend");
                    }

                }.start();

            }
        });

        verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (otp.getText().toString().equals("")) {
                    Utils_Class.toast_center(getContext(), "Enter your otp");
                } else if (sharedPreference.getString(getContext(), "register_otp").equals(otp.getText().toString())) {
                    otp_verify();
                    home.register();
                } else {
                    Utils_Class.toast_center(getContext(), "Invalid otp");
                }


               /* if (sharedPreference.getString(getContext(), "register_otp").equals(otp.getText().toString()) ) {
                    otp_verify();
                    home.register();
                }else {
                    Utils_Class.toast_center(getContext(),"Invalid otp");
                }*/


                /*if (name.equals("1234") ) {
                    home.register();
                }*/
            }
        });

        return view;
    }

    public void otp_verify() {
        verify = otp.getText().toString().trim();
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "check_otp");
        //map.put("mobile_num", "" + sharedPreference.getString(getContext(), "resend"));
        map.put("otp", verify);
        RetrofitAPI retrofitAPI = RetrofitAPIClient.getRetrofit().create(RetrofitAPI.class);
        Call<ArrayList<OtpVerifyPojo>> call = retrofitAPI.getOtp_verify(map);
        call.enqueue(new Callback<ArrayList<OtpVerifyPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<OtpVerifyPojo>> call, Response<ArrayList<OtpVerifyPojo>> response) {
                if (response.isSuccessful()) {
                    String result = new Gson().toJson(response.body());
                    System.out.println("======response result:" + result);
                        sharedPreference.putInt(getContext(), "yes", 1);
                        otp.getText().toString();

                }
                System.out.println("======response :" + response);
            }

            @Override
            public void onFailure(Call<ArrayList<OtpVerifyPojo>> call, Throwable t) {
                System.out.println("======response t:" + t);
            }
        });
    }


    public void otp_generate() {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "otp_generate");
        //map.put("name","" );
        map.put("mobile_num", "" + sharedPreference.getString(getContext(), "resend"));
        map.put("android_id", Utils_Class.android_id(getContext()));
        map.put("is_check", "1");
        RetrofitAPI retrofitAPI = RetrofitAPIClient.getRetrofit().create(RetrofitAPI.class);
        Call<ArrayList<OtpGenerate>> call = retrofitAPI.getOtp_generate(map);
        call.enqueue(new Callback<ArrayList<OtpGenerate>>() {
            @Override
            public void onResponse(Call<ArrayList<OtpGenerate>> call, Response<ArrayList<OtpGenerate>> response) {
                if (response.isSuccessful()) {

                    String result = new Gson().toJson(response.body());
                    otp_gene.addAll(response.body());
                    System.out.println("======response result:" + result);
                    sharedPreference.putString(getContext(), "register_otp", ""+ otp_gene.get(0).getOtp());
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