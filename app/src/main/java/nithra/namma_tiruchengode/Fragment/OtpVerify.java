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
    CountDownTimer val;

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

        val = new CountDownTimer(120000, 1000) { // adjust the milli seconds here
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
                if (Utils_Class.isNetworkAvailable(getContext())) {
                    otp_generate();
                } else {
                    Utils_Class.toast_normal(getContext(), "Please connect to your internet");
                }
                new CountDownTimer(120000, 1000) { // adjust the milli seconds here

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
                if (sharedPreference.getString(getContext(), "register_otp").equals("0")) {
                    Utils_Class.toast_center(getContext(), "Click Resend button");
                } else if (otp.getText().toString().equals("")) {
                    Utils_Class.toast_center(getContext(), "Enter your otp");
                } else if (sharedPreference.getString(getContext(), "register_otp").equals(otp.getText().toString())) {
                    if (Utils_Class.isNetworkAvailable(getContext())) {
                        otp_verify();
                    } else {
                        Utils_Class.toast_normal(getContext(), "Please connect to your internet");
                    }
                } else {
                    Utils_Class.toast_center(getContext(), "You entered wrong otp...");
                }
            }
        });

        return view;
    }

    public void otp_verify() {

        Utils_Class.mProgress(getContext(), "Loading please wait...", false).show();
        verify = otp.getText().toString().trim();
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "check_otp");
        map.put("mobile_num", "" + sharedPreference.getString(getContext(), "resend"));
        map.put("otp", verify);
        RetrofitAPI retrofitAPI = RetrofitAPIClient.getRetrofit().create(RetrofitAPI.class);
        Call<ArrayList<OtpVerifyPojo>> call = retrofitAPI.getOtp_verify(map);
        call.enqueue(new Callback<ArrayList<OtpVerifyPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<OtpVerifyPojo>> call, Response<ArrayList<OtpVerifyPojo>> response) {
                if (response.isSuccessful()) {
                    String result = new Gson().toJson(response.body());
                    System.out.println("======response result:" + result);
                    if (response.body().get(0).getStatus().equals("success")) {
                        sharedPreference.putInt(requireActivity(), "profile", 1);
                        System.out.println("print_int==" + sharedPreference.getInt(getContext(), "profile"));
                        otp.getText().clear();
                        Utils_Class.toast_center(getContext(), "OTP Verified");
                        home.register();
                        val.cancel();
                    } else {
                        Utils_Class.toast_center(getContext(), "Invalid otp");
                    }
                    Utils_Class.mProgress.dismiss();

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
        Utils_Class.mProgress(getContext(), "Loading please wait...", false).show();

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
                    if (response.body().get(0).getStatus().equals("success")) {

                        String result = new Gson().toJson(response.body());
                        otp_gene.addAll(response.body());
                        System.out.println("======response result:" + result);
                        sharedPreference.putString(requireActivity(), "register_otp", "" + otp_gene.get(0).getOtp());
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