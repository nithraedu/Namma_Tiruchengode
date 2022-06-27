package nithra.namma_tiruchengode.Feedback;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static  Retrofit retrofit;
    private static String BASE_URL="https://www.nithra.mobi/apps/";
    public static Retrofit getRetrofit(){
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
