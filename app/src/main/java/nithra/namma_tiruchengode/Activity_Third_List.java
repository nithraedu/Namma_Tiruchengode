package nithra.namma_tiruchengode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import nithra.namma_tiruchengode.Retrofit.RetrofitAPI;
import nithra.namma_tiruchengode.Retrofit.RetrofitAPIClient;
import nithra.namma_tiruchengode.Retrofit.Third_Category;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Third_List extends AppCompatActivity {
    ListAdapter_1 adapter;
    LinearLayout linear;
    RecyclerView show_cat;
    TextView cat_title, nodata;
    Intent intent;
    Bundle extra;
    int pos;
    String idd;
    String list_title, title;
    ArrayList<Third_Category> third_category;
    SharedPreference sharedPreference = new SharedPreference();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_second_list);
        linear = findViewById(R.id.linear);
        show_cat = findViewById(R.id.show_cat);
        cat_title = findViewById(R.id.cat_title);
        nodata = findViewById(R.id.nodata);
        intent = getIntent();
        extra = intent.getExtras();
        linear.setVisibility(View.GONE);
        if (extra != null) {
            list_title = extra.getString("list_title");
            pos = extra.getInt("id");
            idd = extra.getString("idd");
            System.out.println("====iddddd" + idd);
            cat_title.setText(list_title);
        }
        third_category = new ArrayList<Third_Category>();


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        show_cat.setLayoutManager(gridLayoutManager);

        Utils_Class.mProgress(this, "Loading please wait...", false).show();
        third_category();
    }

    public void third_category() {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "get_post");
        map.put("sub_category", idd);
        System.out.println("mapping" + map);
        RetrofitAPI retrofitAPI = RetrofitAPIClient.getRetrofit().create(RetrofitAPI.class);
        Call<ArrayList<Third_Category>> call = retrofitAPI.getThirdCategory(map);
        System.out.println("======map:" + map);

        call.enqueue(new Callback<ArrayList<Third_Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Third_Category>> call, Response<ArrayList<Third_Category>> response) {
                if (response.isSuccessful()) {
                    String result = new Gson().toJson(response.body());
                    System.out.println("======response result:" + result);
                    third_category.clear();
                    third_category.addAll(response.body());
                    /*if (third_category.isEmpty()){
                        show_cat.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                    }else {
                        show_cat.setVisibility(View.VISIBLE);
                        nodata.setVisibility(View.GONE);
                    }*/
                    adapter = new ListAdapter_1(Activity_Third_List.this, third_category, title);
                    show_cat.setAdapter(adapter);
                    Utils_Class.mProgress.dismiss();
                }
                System.out.println("======response :" + response);
            }

            @Override
            public void onFailure(Call<ArrayList<Third_Category>> call, Throwable t) {
                System.out.println("======response t:" + t);
            }
        });
    }

    public class ListAdapter_1 extends RecyclerView.Adapter<ListAdapter_1.ViewHolder> {
        private LayoutInflater inflater;
        ArrayList<Third_Category> titles;
        public Activity context;
        String title;

        public ListAdapter_1(Activity ctx, ArrayList<Third_Category> titles, String tool_title) {
            this.inflater = LayoutInflater.from(ctx);
            this.titles = titles;
            this.title = tool_title;
            this.context = ctx;
        }

        @NonNull
        @Override
        public ListAdapter_1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.third_adapter, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ListAdapter_1.ViewHolder holder, int position) {
            int pos = position;
            if (titles.get(pos).sectorName.trim().isEmpty()) {
                holder.cat_title.setVisibility(View.GONE);
            } else {
                holder.cat_title.setText(titles.get(pos).sectorName);
                holder.cat_title.setVisibility(View.VISIBLE);
            }
            if (titles.get(pos).address.trim().isEmpty()) {
                holder.adderss.setVisibility(View.GONE);
            } else {
                holder.adderss.setText(titles.get(pos).address);
                holder.adderss.setVisibility(View.VISIBLE);
            }

           /* if (titles.get(pos).openingTime.trim().isEmpty()) {
                holder.start_time.setVisibility(View.GONE);
            } else {
                holder.start_time.setText(titles.get(pos).openingTime);
                holder.start_time.setVisibility(View.VISIBLE);
            }*/

            if (titles.get(pos).closingTime.trim().isEmpty()) {
                holder.close_time.setVisibility(View.GONE);
            } else {
                holder.close_time.setText("Closes " + titles.get(pos).closingTime);
                holder.close_time.setVisibility(View.VISIBLE);
            }

           /* if (titles.get(pos).leaveDay.trim().isEmpty()) {
                holder.work_day.setVisibility(View.GONE);
            } else {
                holder.work_day.setText(titles.get(pos).leaveDay);
                holder.work_day.setVisibility(View.VISIBLE);
            }*/

           /* if (titles.get(pos).logo.trim().isEmpty()){
                holder.cat_icon.setVisibility(View.GONE);
            }else {
                Glide.with(context).load(titles.get(pos).logo)
                        .error(R.drawable.warning)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.cat_icon);
                holder.cat_icon.setVisibility(View.VISIBLE);
            }*/
          /*  holder.cat_title.setText(titles.get(pos).sectorName);
            holder.adderss.setText(titles.get(pos).address);
            holder.start_time.setText(titles.get(pos).openingTime);
            holder.close_time.setText(titles.get(pos).closingTime);
            holder.work_day.setText(titles.get(pos).leaveDay);*/
            Glide.with(context).load(titles.get(pos).logo)
                    .error(R.drawable.warning)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.cat_icon);
            holder.list_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int LAUNCH_SECOND_ACTIVITY = 1;
                    Intent i = new Intent(context, Category_Full_View.class);
                    i.putExtra("list_title", titles.get(pos).sectorName);
                    i.putExtra("id", pos);
                    i.putExtra("idd", titles.get(pos).getId());
                    context.startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
                }
            });
            holder.count.setText(titles.get(pos).view_count);
        }


        @Override
        public int getItemCount() {
            return titles.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView cat_icon;
            TextView cat_title, adderss, start_time, close_time, work_day, count;
            LinearLayout list_click;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                cat_icon = itemView.findViewById(R.id.cat_icon);
                cat_title = itemView.findViewById(R.id.cat_title);
                list_click = itemView.findViewById(R.id.list_click);
                adderss = itemView.findViewById(R.id.adderss);
                start_time = itemView.findViewById(R.id.start_time);
                close_time = itemView.findViewById(R.id.close_time);
                work_day = itemView.findViewById(R.id.work_day);
                count = itemView.findViewById(R.id.count);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int LAUNCH_SECOND_ACTIVITY = 1;
        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                third_category();
                System.out.println("code pass" + resultCode);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
                System.out.println("code fail" + resultCode);

            }
        }

    }
}
