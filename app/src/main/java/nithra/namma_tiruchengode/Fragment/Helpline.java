package nithra.namma_tiruchengode.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import nithra.namma_tiruchengode.R;
import nithra.namma_tiruchengode.Retrofit.Helplinepojo;
import nithra.namma_tiruchengode.Retrofit.RetrofitAPI;
import nithra.namma_tiruchengode.Retrofit.RetrofitAPIClient;
import nithra.namma_tiruchengode.Utils_Class;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Helpline extends Fragment {
    ArrayList<Helplinepojo> titles;
    RecyclerView recycle;
    Help_Adapter adapter;
    TextInputEditText name_txt;
    TextView nodata;


    public Helpline() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_helpline, container, false);
        recycle = view.findViewById(R.id.recycle);
        name_txt = view.findViewById(R.id.name_txt);
        nodata = view.findViewById(R.id.nodata);
        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.pullToRefresh);

        titles = new ArrayList<Helplinepojo>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
        recycle.setLayoutManager(gridLayoutManager);

        name_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<Helplinepojo> tempHistoryList = new ArrayList<Helplinepojo>();
                tempHistoryList.clear();
                for (int i = 0; i < titles.size(); i++) {
                    if (titles.get(i).helplineCategory.toLowerCase(Locale.ROOT).contains(s.toString().toLowerCase(Locale.ROOT))) {
                        tempHistoryList.add(titles.get(i));
                        System.out.println("listprint" + titles.get(i).helplineCategory);
                    }
                    if (tempHistoryList.size()==0) {
                        recycle.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                    }
                    else {
                        nodata.setVisibility(View.GONE);
                        recycle.setVisibility(View.VISIBLE);
                    }

                }
                adapter = new Help_Adapter(getContext(), tempHistoryList);
                recycle.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        help();
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //list.setAdapter(adapter);// your code
                titles.clear();
                help();
                pullToRefresh.setRefreshing(false);
            }
        });
        return view;
    }


    public void help() {
        Utils_Class.mProgress(getContext(), "Loading please wait...", false).show();

        HashMap<String, String> map = new HashMap<>();
        map.put("action", "get_helpline");
        RetrofitAPI retrofitAPI = RetrofitAPIClient.getRetrofit().create(RetrofitAPI.class);
        Call<ArrayList<Helplinepojo>> call = retrofitAPI.getHelpline(map);
        call.enqueue(new Callback<ArrayList<Helplinepojo>>() {
            @Override
            public void onResponse(Call<ArrayList<Helplinepojo>> call, Response<ArrayList<Helplinepojo>> response) {
                if (response.isSuccessful()) {
                    String result = new Gson().toJson(response.body());
                    System.out.println("======response result:" + result);
                    if (response.body().get(0).getStatus().equals("Success")) {
                        if (response.body() != null) {
                            titles.addAll(response.body());
                        } else {
                            recycle.setVisibility(View.GONE);
                        }

                   /* if (titles.isEmpty()){
                        recycle.setVisibility(View.GONE);
                    }else {
                        recycle.setVisibility(View.VISIBLE);
                    }*/

                        // titles.addAll(response.body());
                        adapter = new Help_Adapter(getContext(), titles);
                        recycle.setAdapter(adapter);
                    }
                    Utils_Class.mProgress.dismiss();
                }
                System.out.println("======response :" + response);
            }

            @Override
            public void onFailure(Call<ArrayList<Helplinepojo>> call, Throwable t) {
                System.out.println("======response t:" + t);
            }
        });
    }


    public class Help_Adapter extends RecyclerView.Adapter<Help_Adapter.ViewHolder> {
        private LayoutInflater inflater;
        ArrayList<Helplinepojo> titles;
        public Context context;

        public Help_Adapter(Context ctx, ArrayList<Helplinepojo> titles) {
            this.inflater = LayoutInflater.from(ctx);
            this.titles = titles;
            this.context = ctx;
        }

        @NonNull
        @Override
        public Help_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.help_adapter, parent, false);
            Help_Adapter.ViewHolder viewHolder = new Help_Adapter.ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull Help_Adapter.ViewHolder holder, int position) {
            int pos = position;
            holder.help_title.setText(titles.get(pos).getHelplineCategory());
            holder.phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phone = titles.get(pos).getMobile().trim();
                    if (phone.equals("")) {
                        Utils_Class.toast_center(getContext(), "Mobile number not available");
                    } else {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                        startActivity(intent);
                    }

                }
            });

        }


        @Override
        public int getItemCount() {
            return titles.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView help_title;
            CardView phone;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                help_title = itemView.findViewById(R.id.help_title);
                phone = itemView.findViewById(R.id.phone);
            }
        }
    }


}