package nithra.namma_tiruchengode.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nithra.namma_tiruchengode.Activity_Search;
import nithra.namma_tiruchengode.Activity_Second_List;
import nithra.namma_tiruchengode.BuildConfig;
import nithra.namma_tiruchengode.Feedback.Feedback;
import nithra.namma_tiruchengode.Feedback.Method;
import nithra.namma_tiruchengode.Feedback.RetrofitClient;
import nithra.namma_tiruchengode.MainActivity;
import nithra.namma_tiruchengode.Notification.NotificationView;
import nithra.namma_tiruchengode.PrivacyPolicy;
import nithra.namma_tiruchengode.R;
import nithra.namma_tiruchengode.Retrofit.Category;
import nithra.namma_tiruchengode.Retrofit.RetrofitAPI;
import nithra.namma_tiruchengode.Retrofit.RetrofitAPIClient;
import nithra.namma_tiruchengode.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<Category> titles;
    ArrayList<Integer> images2;
    Adapter adapter;
    Adapter2 adapter2;
    LinearLayout notification;
    TextView notifi_count,search;
    SQLiteDatabase db1;
    TextView code, name;
    int versionCode = BuildConfig.VERSION_CODE;
    String versionName = BuildConfig.VERSION_NAME;
    DrawerLayout drawer;

    public Home() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.main_lay, container, false);
        notification = view.findViewById(R.id.notification);
        notifi_count = view.findViewById(R.id.notifi_count);
        drawer = view.findViewById(R.id.drawer_layout);
        search=view.findViewById(R.id.search);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        db1 = getContext().openOrCreateDatabase("myDB", MODE_PRIVATE, null);
        String tablenew = "noti_cal";
        db1.execSQL("CREATE TABLE IF NOT EXISTS " + tablenew
                + " (id integer NOT NULL PRIMARY KEY AUTOINCREMENT,title VARCHAR,message VARCHAR,date VARCHAR,time VARCHAR,isclose INT(4),isshow INT(4) default 0,type VARCHAR,"
                + "bm VARCHAR,ntype VARCHAR,url VARCHAR);");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        DrawerLayout drawer = view.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        NavigationView navigationView = view.findViewById(R.id.nav_mm_view);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        View v = navigationView.inflateHeaderView(R.layout.header);
        code = v.findViewById(R.id.code);
        name = v.findViewById(R.id.name);
        code.setText("" + versionCode);
        name.setText(versionName);

        titles = new ArrayList<Category>();
        images2 = new ArrayList<Integer>();

        RecyclerView list = view.findViewById(R.id.list);
        RecyclerView list2 = view.findViewById(R.id.list2);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4, GridLayoutManager.VERTICAL, false);
        list.setLayoutManager(gridLayoutManager);

        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false);
        list2.setLayoutManager(gridLayoutManager2);
        adapter = new Adapter(getContext(), titles);
        adapter2 = new Adapter2(getContext(), images2);
        list.setAdapter(adapter);
        list2.setAdapter(adapter2);
        Utils.mProgress(getContext(), "Loading please wait...", false).show();
        category();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getContext(), Activity_Search.class);
                startActivity(i);
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), NotificationView.class);
                startActivity(i);
            }
        });

        return view;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(false);
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            drawer.closeDrawer(GravityCompat.START);

        } else if (id == R.id.nav_share) {
            String sharebody = "நித்ரா நம்ம ஊரு திருச்செங்கோடு அப்ளிகேசன் வாயிலாக நம்ம ஊரு திருச்செங்கோடு பற்றி அறிந்து கொள்ள கீழ்க்கண்ட லிங்கை கிளிக் செய்யுங்கள்!\n\n short link ";
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "நம்ம ஊரு திருச்செங்கோடு");
            intent.putExtra(Intent.EXTRA_TEXT, sharebody);
            startActivity(Intent.createChooser(intent, "Share Via"));
        } else if (id == R.id.nav_rateus) {
            /*Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=nithra.tamil.andaal.thiruppavai"));
            startActivity(intent);*/

            Utils.toast_normal(getContext(), "Not Available in playstore");

        } else if (id == R.id.nav_feedback) {
            feedback();

        } else if (id == R.id.nav_policy) {
            if (Utils.isNetworkAvailable(getContext())) {
                Intent i = new Intent(getContext(), PrivacyPolicy.class);
                startActivity(i);
            } else {
                Utils.toast_normal(getContext(), "Please connect to your internet");
            }

        } else if (id == R.id.nav_exit) {
            getActivity().finish();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void feedback() {
        EditText email_edt, feedback_edt;
        TextView privacy;
        TextView submit_btn;
        Dialog dialog = new Dialog(getContext(), android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
        dialog.setContentView(R.layout.feed_back);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        email_edt = dialog.findViewById(R.id.edit_email);
        feedback_edt = dialog.findViewById(R.id.editText1);
        submit_btn = dialog.findViewById(R.id.btnSend);
        privacy = dialog.findViewById(R.id.policy);
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isNetworkAvailable(getContext())) {
                    Intent intent = new Intent(getContext(), PrivacyPolicy.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "please connect to the internet...", Toast.LENGTH_SHORT).show();
                }

            }
        });
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String feedback = feedback_edt.getText().toString().trim();
                String email = email_edt.getText().toString().trim();

                if (feedback.equals("")) {
                    Toast.makeText(getContext(), "Please type your feedback or suggestion, Thank you", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Utils.isNetworkAvailable(getContext())) {
                    Toast.makeText(getContext(), "please connect to the internet...", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    feedback = URLEncoder.encode(feedback, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                HashMap<String, String> map = new HashMap<>();
                map.put("type", "Namma_Tiruchengode");
                map.put("feedback", feedback);
                map.put("email", email);
                map.put("model", Build.MODEL);
                map.put("vcode", "1.0");
                Method method = RetrofitClient.getRetrofit().create(Method.class);
                Call<List<Feedback>> call = method.getAlldata(map);
                call.enqueue(new Callback<List<Feedback>>() {
                    @Override
                    public void onResponse(Call<List<Feedback>> call, Response<List<Feedback>> response) {
                        if (response.isSuccessful()) {
                            try {
                                List<Feedback> feedbacks = response.body();
                                System.out.println("======response feedbacks:" + feedbacks.get(0).getStatus());

                                JSONArray jsonArray = new JSONArray(new Gson().toJson(response.body()));
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                System.out.println("======response feedbacks:" + jsonObject.getString("status"));
                                dialog.dismiss();
                                Toast.makeText(getContext(), "Feedback sent, Thank you", Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                System.out.println("======response e:" + e.toString());
                                e.printStackTrace();
                            }
                        }
                        System.out.println("======response :" + response);
                    }

                    @Override
                    public void onFailure(Call<List<Feedback>> call, Throwable t) {
                        System.out.println("======response t:" + t);
                    }
                });
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
               /* if (a == 1) {
                    finish();
                    Intent intent = new Intent(getContext(), ExitScreen.class);
                    startActivity(intent);
                }*/
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void category() {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "get_category");
        RetrofitAPI retrofitAPI = RetrofitAPIClient.getRetrofit().create(RetrofitAPI.class);
        Call<ArrayList<Category>> call = retrofitAPI.getCategory(map);
        call.enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                if (response.isSuccessful()) {
                    String result = new Gson().toJson(response.body());
                    System.out.println("======response result:" + result);
                    titles.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    Utils.mProgress.dismiss();
                }
                System.out.println("======response :" + response);
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                System.out.println("======response t:" + t);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        visible();
    }

    public void visible() {

        Cursor c = db1.rawQuery("select * from noti_cal where isclose=0", null);
        int noti_count = c.getCount();
        if (noti_count != 0) {
            notifi_count.setVisibility(View.VISIBLE);
            if (noti_count <= 9) {
                notifi_count.setText("" + noti_count);
            } else {
                notifi_count.setText("9+");
            }
        } else {
            notifi_count.setVisibility(View.INVISIBLE);
        }
       /* Animation noti_shake = null;
        if (noti_count != 0) {

            noti_shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.noti_shake);
            notifi_count.startAnimation(noti_shake);
        }*/
    }

    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        ArrayList<Category> titles;
        LayoutInflater inflater;
        Context context;

        public Adapter(Context ctx, ArrayList<Category> titles) {
            this.titles = titles;
            this.inflater = LayoutInflater.from(ctx);
            this.context = ctx;
        }

        @NonNull
        @Override
        public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.adapter_main, parent, false);
            Adapter.ViewHolder viewHolder = new Adapter.ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
            int pos = position;
            holder.title.setText(titles.get(pos).category);
            Glide.with(context).load(titles.get(position).categoryLogo)
                    .error(R.drawable.warning)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.gridImage);
            holder.card_category.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getContext(), Activity_Second_List.class);
                    i.putExtra("toolbartitle", titles.get(pos).category);
                    i.putExtra("id", pos+1);
                    i.putExtra("idd", titles.get(pos).id+1);
                    startActivity(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return titles.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            ImageView gridImage;
            CardView card_category;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.gridText);
                gridImage = itemView.findViewById(R.id.imageGrid);
                card_category = itemView.findViewById(R.id.category);
            }
        }
    }

    public class Adapter2 extends RecyclerView.Adapter<Adapter2.ViewHolder> {
        ArrayList<Integer> images;
        LayoutInflater inflater;

        public Adapter2(Context ctx, ArrayList<Integer> images) {
            this.images = images;
            this.inflater = LayoutInflater.from(ctx);
        }

        @NonNull
        @Override
        public Adapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.best_seller, parent, false);
            Adapter2.ViewHolder viewHolder = new Adapter2.ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull Adapter2.ViewHolder holder, int position) {
            holder.gridImage.setImageResource(images.get(position));
        }

        @Override
        public int getItemCount() {
            return images.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView gridImage;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                gridImage = itemView.findViewById(R.id.imageGrid);
            }
        }
    }

}