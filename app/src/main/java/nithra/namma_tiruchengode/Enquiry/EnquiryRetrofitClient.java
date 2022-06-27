package nithra.namma_tiruchengode.Enquiry;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EnquiryRetrofitClient {
    private static Retrofit retrofit;
    private static String BASE_URL = "";

    public static Retrofit getRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
