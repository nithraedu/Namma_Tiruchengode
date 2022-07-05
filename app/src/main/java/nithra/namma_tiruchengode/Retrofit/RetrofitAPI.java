package nithra.namma_tiruchengode.Retrofit;

import java.util.ArrayList;
import java.util.HashMap;

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


    @FormUrlEncoded
    @POST("data.php")
    Call<ArrayList<Helplinepojo>> getHelpline(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("data.php")
    Call<ArrayList<Category_Main>> getCat_Main(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("data.php")
    Call<ArrayList<OtpGenerate>> getOtp_generate(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("data.php")
    Call<ArrayList<OtpVerifyPojo>> getOtp_verify(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("data.php")
    Call<ArrayList<AddPojo>> getadd_user(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("data.php")
    Call<ArrayList<SearchPojo>> getsearch(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("data.php")
    Call<ArrayList<T_codePojo>> gettcode(@FieldMap HashMap<String, String> data);

}
