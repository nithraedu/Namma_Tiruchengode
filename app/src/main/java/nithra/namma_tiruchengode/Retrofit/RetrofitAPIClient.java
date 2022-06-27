package nithra.namma_tiruchengode.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAPIClient {
    private static Retrofit retrofit;
    private static String BASE_URL="http://15.206.173.184/upload/nammaooru_tiruchengode/api/";
    public static Retrofit getRetrofit(){
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
