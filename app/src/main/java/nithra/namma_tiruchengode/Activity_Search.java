package nithra.namma_tiruchengode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import nithra.namma_tiruchengode.Retrofit.RetrofitAPI;
import nithra.namma_tiruchengode.Retrofit.RetrofitAPIClient;
import nithra.namma_tiruchengode.Retrofit.SearchPojo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Search extends AppCompatActivity {
    SearchView search_view;
    ListAdapter_1 adapter;
    ArrayList<SearchPojo> searchlist;
    TextInputEditText name_txt;
    RecyclerView search_list;
    String title;
    String idd;
    TextView nodata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search);
        search_view = findViewById(R.id.search_view);
        name_txt = findViewById(R.id.name_txt);
        search_list = findViewById(R.id.search_list);
        nodata = findViewById(R.id.nodata);
        search_view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(view, 0);
                }
            }
        });

        searchlist = new ArrayList<SearchPojo>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        search_list.setLayoutManager(gridLayoutManager);
        adapter = new ListAdapter_1(this, searchlist, title);
        search_list.setAdapter(adapter);




        name_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<SearchPojo> tempHistoryList = new ArrayList<SearchPojo>();
                tempHistoryList.clear();
                for (int i = 0; i < searchlist.size(); i++) {
                    if (searchlist.get(i).sectorName.toLowerCase(Locale.ROOT).contains(s.toString().toLowerCase(Locale.ROOT))) {
                        tempHistoryList.add(searchlist.get(i));
                        System.out.println("listprint"   +searchlist.get(i).sectorName);

                    }
                    if (tempHistoryList.size()==0) {
                        search_list.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                    }
                    else {
                        nodata.setVisibility(View.GONE);
                        search_list.setVisibility(View.VISIBLE);
                    }
                }
                adapter = new ListAdapter_1(Activity_Search.this, tempHistoryList, title);
                search_list.setAdapter(adapter);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Utils_Class.mProgress(this, "Loading please wait...", false).show();
        search_category();

    }


    public void search_category() {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "get_search_post");
        RetrofitAPI retrofitAPI = RetrofitAPIClient.getRetrofit().create(RetrofitAPI.class);
        Call<ArrayList<SearchPojo>> call = retrofitAPI.getsearch(map);
        System.out.println("======map:" + map);

        call.enqueue(new Callback<ArrayList<SearchPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<SearchPojo>> call, Response<ArrayList<SearchPojo>> response) {
                if (response.isSuccessful()) {
                    String result = new Gson().toJson(response.body());
                    System.out.println("======response result:" + result);
                    searchlist.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    Utils_Class.mProgress.dismiss();
                }
                System.out.println("======response :" + response);
            }

            @Override
            public void onFailure(Call<ArrayList<SearchPojo>> call, Throwable t) {
                System.out.println("======response t:" + t);
            }
        });
    }


    public class ListAdapter_1 extends RecyclerView.Adapter<ListAdapter_1.ViewHolder> {
        private LayoutInflater inflater;
        ArrayList<SearchPojo> titles;
        public Context context;
        String title;

        public ListAdapter_1(Context ctx, ArrayList<SearchPojo> titles, String tool_title) {
            this.inflater = LayoutInflater.from(ctx);
            this.titles = titles;
            this.title = tool_title;
            this.context = ctx;
        }

        @NonNull
        @Override
        public ListAdapter_1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.search_adapter, parent, false);
            ListAdapter_1.ViewHolder viewHolder = new ListAdapter_1.ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ListAdapter_1.ViewHolder holder, int position) {
            int pos = position;
            holder.cat_title.setText(titles.get(pos).sectorName);
            holder.adderss.setText(titles.get(pos).address);
            Glide.with(context).load(titles.get(pos).logo)
                    .error(R.drawable.warning)
                    .placeholder(R.drawable.warning)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.cat_icon);
            holder.list_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, Category_Full_View.class);
                    i.putExtra("list_title", titles.get(pos).sectorName);
                    i.putExtra("id", pos);
                    i.putExtra("idd", titles.get(pos).getId());
                    context.startActivity(i);
                }
            });
        }


        @Override
        public int getItemCount() {
            return titles.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView cat_icon;
            TextView cat_title, adderss;
            LinearLayout list_click;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                cat_icon = itemView.findViewById(R.id.cat_icon);
                cat_title = itemView.findViewById(R.id.cat_title);
                list_click = itemView.findViewById(R.id.list_click);
                adderss = itemView.findViewById(R.id.adderss);
            }
        }
    }

}