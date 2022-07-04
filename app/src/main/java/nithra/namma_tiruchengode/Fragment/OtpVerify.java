package nithra.namma_tiruchengode.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import nithra.namma_tiruchengode.Gotohome;
import nithra.namma_tiruchengode.R;
import nithra.namma_tiruchengode.Retrofit.OtpVerifyPojo;
import nithra.namma_tiruchengode.Retrofit.RetrofitAPI;
import nithra.namma_tiruchengode.Retrofit.RetrofitAPIClient;
import nithra.namma_tiruchengode.SharedPreference;
import nithra.namma_tiruchengode.Utils_Class;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerify extends Fragment {
    EditText otp;
    TextView verify_otp;
    Gotohome home;
    String verify;
    SharedPreference sharedPreference = new SharedPreference();

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

        verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreference.getString(getContext(), "register_otp").equals(otp.getText().toString()) ) {
                    otp_verify();
                    home.register();
                }else {
                    Utils_Class.toast_center(getContext(),"Invalid otp");
                }
                /*if (name.equals("1234") ) {
                    home.register();
                }*/
            }
        });

        otp_verify();

        return view;
    }

    public void otp_verify() {
        verify = otp.getText().toString().trim();
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "otp_generate");
        //map.put("mobile_num", number);
        map.put("otp", verify);
        RetrofitAPI retrofitAPI = RetrofitAPIClient.getRetrofit().create(RetrofitAPI.class);
        Call<ArrayList<OtpVerifyPojo>> call = retrofitAPI.getOtp_verify(map);
        call.enqueue(new Callback<ArrayList<OtpVerifyPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<OtpVerifyPojo>> call, Response<ArrayList<OtpVerifyPojo>> response) {
                if (response.isSuccessful()) {
                    String result = new Gson().toJson(response.body());
                    sharedPreference.putInt(getContext(),"yes",1);

                    System.out.println("======response result:" + result);
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

}