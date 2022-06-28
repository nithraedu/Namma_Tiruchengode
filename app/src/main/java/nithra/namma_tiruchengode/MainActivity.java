package nithra.namma_tiruchengode;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import nithra.namma_tiruchengode.Fragment.Enquiry;
import nithra.namma_tiruchengode.Fragment.Helpline;
import nithra.namma_tiruchengode.Fragment.Home;
import nithra.namma_tiruchengode.Retrofit.Category;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener,Gotohome {
    ArrayList<Category> titles;
    ArrayList<Integer> images2;
    BottomNavigationView bottomnavigationview;
    LinearLayout notification;
    TextView notifi_count, search;
    SQLiteDatabase db1;
    TextView code, name;
    int versionCode = BuildConfig.VERSION_CODE;
    String versionName = BuildConfig.VERSION_NAME;
    ViewPager2 viewpager2;
    Frag_Adapter frag_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.app_lay);
        viewpager2 = findViewById(R.id.viewpager2);
        viewpager2.setUserInputEnabled(false);
        frag_adapter = new Frag_Adapter(getSupportFragmentManager(), getLifecycle());

        frag_adapter.addFragment(new Home());
        frag_adapter.addFragment(new Enquiry());
        frag_adapter.addFragment(new Helpline());
        viewpager2.setAdapter(frag_adapter);
        bottomnavigationview = findViewById(R.id.bottomnavigationview);
        bottomnavigationview.setOnItemSelectedListener(this);
        bottomnavigationview.setSelectedItemId(R.id.bottom_home);



       /* notification = findViewById(R.id.notification);
        notifi_count = findViewById(R.id.notifi_count);
        search=findViewById(R.id.search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        db1 = openOrCreateDatabase("myDB", MODE_PRIVATE, null);
        String tablenew = "noti_cal";
        db1.execSQL("CREATE TABLE IF NOT EXISTS " + tablenew
                + " (id integer NOT NULL PRIMARY KEY AUTOINCREMENT,title VARCHAR,message VARCHAR,date VARCHAR,time VARCHAR,isclose INT(4),isshow INT(4) default 0,type VARCHAR,"
                + "bm VARCHAR,ntype VARCHAR,url VARCHAR);");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        NavigationView navigationView = findViewById(R.id.nav_mm_view);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        View v = navigationView.inflateHeaderView(R.layout.header);
        code = v.findViewById(R.id.code);
        name = v.findViewById(R.id.name);
        code.setText("" + versionCode);
        name.setText(versionName);

        titles = new ArrayList<Category>();
        images2 = new ArrayList<Integer>();

        RecyclerView list = findViewById(R.id.list);
        RecyclerView list2 = findViewById(R.id.list2);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        list.setLayoutManager(gridLayoutManager);

        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        list2.setLayoutManager(gridLayoutManager2);
        adapter = new Adapter(this, titles);
        adapter2 = new Adapter2(this, images2);
        list.setAdapter(adapter);
        list2.setAdapter(adapter2);
        Utils.mProgress(this, "Loading please wait...", false).show();
        category();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(MainActivity.this,Activity_Search.class);
                startActivity(i);
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NotificationView.class);
                startActivity(i);
            }
        });*/

        bottomnavigationview.setOnItemSelectedListener(item -> {
            Fragment currentFragment = null;
            FragmentTransaction ft;
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    //getSupportFragmentManager().beginTransaction().replace(R.id.container, firstFragment).commit();
                    viewpager2.setCurrentItem(0);
                    return true;

                case R.id.bottom_city:
                    //getSupportFragmentManager().beginTransaction().replace(R.id.container, secondFragment).commit();
                    return true;

                case R.id.bottom_helpline:
                    //getSupportFragmentManager().beginTransaction().replace(R.id.container, thirdFragment).commit();
                    viewpager2.setCurrentItem(2);
                    return true;

                case R.id.bottom_enquiry:
                    //getSupportFragmentManager().beginTransaction().replace(R.id.container, thirdFragment).commit();
                    viewpager2.setCurrentItem(1);
                    return true;
            }
            return false;
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void home() {
        viewpager2.setCurrentItem(0);
        bottomnavigationview.getMenu().getItem(0).setChecked(true);
    }

   /* @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(false);
        int id = item.getItemId();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
            *//*Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=nithra.tamil.andaal.thiruppavai"));
            startActivity(intent);*//*

            Utils.toast_normal(MainActivity.this, "Not Available in playstore");

        } else if (id == R.id.nav_feedback) {
            feedback();

        } else if (id == R.id.nav_policy) {
            if (Utils.isNetworkAvailable(MainActivity.this)) {
                Intent i = new Intent(MainActivity.this, PrivacyPolicy.class);
                startActivity(i);
            } else {
                Utils.toast_normal(MainActivity.this, "Please connect to your internet");
            }

        } else if (id == R.id.nav_exit) {
            finish();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void feedback() {
        EditText email_edt, feedback_edt;
        TextView privacy;
        TextView submit_btn;
        Dialog dialog = new Dialog(MainActivity.this, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
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
                if (Utils.isNetworkAvailable(MainActivity.this)) {
                    Intent intent = new Intent(MainActivity.this, PrivacyPolicy.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "please connect to the internet...", Toast.LENGTH_SHORT).show();
                }

            }
        });
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String feedback = feedback_edt.getText().toString().trim();
                String email = email_edt.getText().toString().trim();

                if (feedback.equals("")) {
                    Toast.makeText(MainActivity.this, "Please type your feedback or suggestion, Thank you", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Utils.isNetworkAvailable(MainActivity.this)) {
                    Toast.makeText(MainActivity.this, "please connect to the internet...", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(MainActivity.this, "Feedback sent, Thank you", Toast.LENGTH_SHORT).show();

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
               *//* if (a == 1) {
                    finish();
                    Intent intent = new Intent(MainActivity.this, ExitScreen.class);
                    startActivity(intent);
                }*//*
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
    protected void onResume() {
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
       *//* Animation noti_shake = null;
        if (noti_count != 0) {

            noti_shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.noti_shake);
            notifi_count.startAnimation(noti_shake);
        }*//*
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
            ViewHolder viewHolder = new ViewHolder(listItem);
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
                    Intent i = new Intent(MainActivity.this, Activity_Second_List.class);
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
            ViewHolder viewHolder = new ViewHolder(listItem);
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
    }*/


    public class Frag_Adapter extends FragmentStateAdapter {

        private ArrayList<Fragment> fragmentList = new ArrayList<>();

        public Frag_Adapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }


        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        public void addFragment(Fragment fragment) {
            fragmentList.add(fragment);
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }
    }


}