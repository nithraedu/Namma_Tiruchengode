package nithra.namma_tiruchengode.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import nithra.namma_tiruchengode.Gotohome;
import nithra.namma_tiruchengode.R;
import nithra.namma_tiruchengode.Utils_Class;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Enquiry extends Fragment {
    TextInputEditText name, contact_number, email, enquiry;
    TextView submit;
    Gotohome home;
    String cus_name, cus_contact_number, cus_email, cus_enquiry;

    public Enquiry() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enquiry, container, false);

        name = view.findViewById(R.id.name);
        contact_number = view.findViewById(R.id.contact_number);
        email = view.findViewById(R.id.email);
        enquiry = view.findViewById(R.id.enquiry);
        submit = view.findViewById(R.id.submit);
        home = (Gotohome) getContext();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cus_name = name.getText().toString().trim();
                cus_contact_number = contact_number.getText().toString().trim();
                cus_email = email.getText().toString().trim();
                cus_enquiry = enquiry.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (cus_name.equals("")) {
                    Utils_Class.toast_center(getContext(), "Please Enter Your Name...");
                } else if (cus_contact_number.length() < 10) {
                    Utils_Class.toast_center(getContext(), "Please Enter Correct Mobile Number...");
                } else if (cus_email.equals("")) {
                    Utils_Class.toast_center(getContext(), "Please Enter Your Email...");
                } else if (!cus_email.matches(emailPattern)) {
                    Toast.makeText(getContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                } else if (cus_enquiry.equals("")) {
                    Utils_Class.toast_center(getContext(), "Please Enter Your enquiry...");
                } else {
                    if (Utils_Class.isNetworkAvailable(getContext())) {
                        submit_res();
                    } else {
                        Utils_Class.toast_normal(getContext(), "Please connect to your internet");
                    }
                }
            }
        });

        return view;
    }


    public void submit_res() {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "set_enquiry");
        map.put("name", cus_name);
        map.put("contact_number", cus_contact_number);
        map.put("email", cus_email);
        map.put("enquiry", cus_enquiry);
        EnquiryMethod enquiryMethod = EnquiryRetrofitClient.getRetrofit().create(EnquiryMethod.class);
        Call<ArrayList<EnquiryPojo>> call = enquiryMethod.getEnquiry(map);
        call.enqueue(new Callback<ArrayList<EnquiryPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<EnquiryPojo>> call, Response<ArrayList<EnquiryPojo>> response) {
                if (response.isSuccessful()) {
                    String result = new Gson().toJson(response.body());
                    System.out.println("======response result:" + result);
                    if (response.body().get(0).getStatus().equals("Success")) {
                        name.getText().clear();
                        contact_number.getText().clear();
                        email.getText().clear();
                        enquiry.getText().clear();
                        Toast.makeText(getContext(), "Enquiry sent, Thank you", Toast.LENGTH_SHORT).show();
                    }
                    home.home();
                }
                System.out.println("======response :" + response);
            }

            @Override
            public void onFailure(Call<ArrayList<EnquiryPojo>> call, Throwable t) {
                System.out.println("======response t:" + t);
            }
        });
    }

}