package nithra.namma_tiruchengode.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import nithra.namma_tiruchengode.R;
import nithra.namma_tiruchengode.Retrofit.RetrofitAPI;
import nithra.namma_tiruchengode.Retrofit.RetrofitAPIClient;
import nithra.namma_tiruchengode.Retrofit.T_codePojo;
import nithra.namma_tiruchengode.Retrofit.Third_Category;
import nithra.namma_tiruchengode.Utils_Class;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class T_code extends Fragment {
    Tcode adapter;
    RecyclerView recycle;
    ArrayList<T_codePojo> tcode;

    public T_code() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_t_code, container, false);
        recycle = view.findViewById(R.id.recycle);
        tcode = new ArrayList<T_codePojo>();
        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.pullToRefresh);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
        recycle.setLayoutManager(gridLayoutManager);
        adapter = new Tcode(getContext(),tcode);
        recycle.setAdapter(adapter);
        Utils_Class.mProgress(getContext(), "Loading please wait...", false).show();
        tcode();
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //list.setAdapter(adapter);// your code
                tcode.clear();
                tcode();
                pullToRefresh.setRefreshing(false);
            }
        });
        return view;
    }

    public void tcode() {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "get_news");
        RetrofitAPI retrofitAPI = RetrofitAPIClient.getRetrofit().create(RetrofitAPI.class);
        Call<ArrayList<T_codePojo>> call = retrofitAPI.gettcode(map);
        System.out.println("======map:" + map);

        call.enqueue(new Callback<ArrayList<T_codePojo>>() {
            @Override
            public void onResponse(Call<ArrayList<T_codePojo>> call, Response<ArrayList<T_codePojo>> response) {
                if (response.isSuccessful()) {
                    String result = new Gson().toJson(response.body());
                    System.out.println("======response result:" + result);
                    tcode.addAll(response.body());
                    /*if (third_category.isEmpty()){
                        show_cat.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                    }else {
                        show_cat.setVisibility(View.VISIBLE);
                        nodata.setVisibility(View.GONE);
                    }*/
                    adapter.notifyDataSetChanged();
                    Utils_Class.mProgress.dismiss();
                }
                System.out.println("======response :" + response);
            }

            @Override
            public void onFailure(Call<ArrayList<T_codePojo>> call, Throwable t) {
                System.out.println("======response t:" + t);
            }
        });
    }



    public class Tcode extends RecyclerView.Adapter<Tcode.ViewHolder> {
        private LayoutInflater inflater;
        ArrayList<T_codePojo> data_load;
        public Context context;

        Tcode(Context ctx,ArrayList<T_codePojo> data_load) {
            this.inflater = LayoutInflater.from(ctx);
            this.data_load = data_load;
            this.context = ctx;
        }

        @NonNull
        @Override
        public Tcode.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.t_code_adapter, parent, false);
            Tcode.ViewHolder viewHolder = new Tcode.ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull Tcode.ViewHolder holder, int position) {
            int pos = position;
            holder.heading.setText(data_load.get(pos).newsContent);
            holder.date.setText(data_load.get(pos).cdate);

            Glide.with(context).load(data_load.get(pos).contentImage)
                    .error(R.drawable.warning)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.image);
            holder.image.setVisibility(View.VISIBLE);

        }


        @Override
        public int getItemCount() {
            return data_load.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView heading,date;
            ImageView image;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                heading = itemView.findViewById(R.id.heading);
                date = itemView.findViewById(R.id.date);
                image = itemView.findViewById(R.id.image);
            }
        }
    }

}