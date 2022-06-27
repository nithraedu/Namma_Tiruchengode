package nithra.namma_tiruchengode.Retrofit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @FormUrlEncoded
    @POST("data.php")
    Call<ArrayList<Category>> getCategory(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("data.php")
    Call<ArrayList<Sub_Category>> getSubCategory(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("data.php")
    Call<ArrayList<Third_Category>> getThirdCategory(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("data.php")
    Call<ArrayList<Full_View>> getFullView(@FieldMap HashMap<String, String> data);
}
