package nithra.namma_tiruchengode.Enquiry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nithra.namma_tiruchengode.Feedback.Feedback;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface EnquiryMethod {
    @FormUrlEncoded
    @POST("data.php")
    Call<ArrayList<EnquiryPojo>> getEnquiry(@FieldMap HashMap<String, String> data);
}
