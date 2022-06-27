package nithra.namma_tiruchengode.Feedback;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Method {
    @FormUrlEncoded
    @POST("appfeedback.php")
    Call<List<Feedback>> getAlldata(@FieldMap HashMap<String, String> data);
}
