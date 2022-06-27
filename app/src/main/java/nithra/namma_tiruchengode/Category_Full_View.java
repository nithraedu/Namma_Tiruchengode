package nithra.namma_tiruchengode;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import nithra.namma_tiruchengode.Retrofit.Full_View;
import nithra.namma_tiruchengode.Retrofit.RetrofitAPI;
import nithra.namma_tiruchengode.Retrofit.RetrofitAPIClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Category_Full_View extends AppCompatActivity {
    TextView cat_title, cat_name;
    int pos;
    String list_title;
    Adapter2 adapter2;
    ArrayList<Full_View> images2;
    ArrayList<Full_View> images3;
    CardView location, website, email, whatsapp, facebook, instagram, twitter,share;
    LinearLayout phone;
    TextView text_address, owner_name, mobile_text, start_time, close_time, work_day, description,mobile_divider,name_divider;
    String idd;
    ArrayList<Full_View> titles;
    RecyclerView list2, list3;
    LinearLayout time,mobile_call,owner_lay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.full_view);
        cat_title = findViewById(R.id.cat_title);
        cat_name = findViewById(R.id.cat_name);
        location = findViewById(R.id.location);
        website = findViewById(R.id.website);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        mobile_text = findViewById(R.id.mobile_text);
        owner_name = findViewById(R.id.owner_name);
        text_address = findViewById(R.id.text_address);
        start_time = findViewById(R.id.start_time);
        close_time = findViewById(R.id.close_time);
        work_day = findViewById(R.id.work_day);
        time=findViewById(R.id.time);
        titles = new ArrayList<Full_View>();
        images2 = new ArrayList<Full_View>();
        images3 = new ArrayList<Full_View>();
        list2 = findViewById(R.id.list2);
        list3 = findViewById(R.id.list3);
        whatsapp = findViewById(R.id.whatsapp);
        facebook = findViewById(R.id.facebook);
        instagram = findViewById(R.id.instagram);
        twitter = findViewById(R.id.twitter);
        share = findViewById(R.id.share);
        description = findViewById(R.id.description);
        mobile_call=findViewById(R.id.mobile_call);
        mobile_divider=findViewById(R.id.mobile_divider);
        owner_lay=findViewById(R.id.owner_lay);
        name_divider=findViewById(R.id.name_divider);
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        if (extra != null) {
            list_title = extra.getString("list_title");
            pos = extra.getInt("id");
            idd = extra.getString("idd");
            cat_title.setText("Details of " + list_title);
        }

        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        list2.setLayoutManager(gridLayoutManager2);

      /*  list3.setLayoutManager(gridLayoutManager3);
        adapter2 = new Adapter2(this, images3);
        list3.setAdapter(adapter2);*/
        Utils.mProgress(this, "Loading please wait...", false).show();
        fullview();
    }

    public void fullview() {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "get_post_details");
        map.put("post_id", idd);
        RetrofitAPI retrofitAPI = RetrofitAPIClient.getRetrofit().create(RetrofitAPI.class);
        Call<ArrayList<Full_View>> call = retrofitAPI.getFullView(map);
        call.enqueue(new Callback<ArrayList<Full_View>>() {
            @Override
            public void onResponse(Call<ArrayList<Full_View>> call, Response<ArrayList<Full_View>> response) {
                if (response.isSuccessful()) {
                    String result = new Gson().toJson(response.body());
                    System.out.println("======response result:" + result);
                    titles.addAll(response.body());
                    images2.addAll(response.body());

                    String currentString = titles.get(pos).getSliderImage();
                    String[] separated = currentString.split(",");
                    adapter2 = new Adapter2(Category_Full_View.this, images2, separated);
                    list2.setAdapter(adapter2);
                    System.out.println("printsplit" + separated.toString());

                    /*if (titles.get(0).getSectorName() == null) {
                        cat_name.setVisibility(View.GONE);
                    } else {
                        cat_name.setText(titles.get(0).getSectorName());
                        cat_name.setVisibility(View.VISIBLE);
                    }

                    if (titles.get(0).getAddress() == null) {
                        text_address.setVisibility(View.GONE);
                    } else {
                        text_address.setText(titles.get(0).getAddress());
                        text_address.setVisibility(View.VISIBLE);
                    }

                    if (titles.get(0).getPersonName().trim() == null) {
                        owner_lay.setVisibility(View.GONE);
                        name_divider.setVisibility(View.GONE);
                    } else {
                        owner_name.setText(titles.get(0).getPersonName().trim());
                        owner_lay.setVisibility(View.VISIBLE);
                        name_divider.setVisibility(View.VISIBLE);
                    }

                    if (titles.get(0).getMobile() == null) {
                        mobile_call.setVisibility(View.GONE);
                        mobile_divider.setVisibility(View.GONE);
                    } else {
                        mobile_text.setText(titles.get(0).getMobile());
                        mobile_call.setVisibility(View.VISIBLE);
                        mobile_divider.setVisibility(View.VISIBLE);
                    }

                    if (titles.get(0).getOpeningTime() == null) {
                        time.setVisibility(View.GONE);
                    } else {
                        start_time.setText(titles.get(0).getOpeningTime());
                        time.setVisibility(View.VISIBLE);
                    }

                    if (titles.get(0).getClosingTime() == null) {
                        time.setVisibility(View.GONE);
                    } else {
                        close_time.setText(titles.get(0).getClosingTime());
                        time.setVisibility(View.VISIBLE);
                    }

                    if (titles.get(0).getLeaveDay() == null) {
                        work_day.setVisibility(View.GONE);
                    } else {
                        work_day.setText(titles.get(0).getLeaveDay());
                        work_day.setVisibility(View.VISIBLE);
                    }

                    if (titles.get(0).getDescription() == null) {
                        description.setVisibility(View.GONE);
                    } else {
                        description.setText(titles.get(0).getDescription());
                        description.setVisibility(View.VISIBLE);
                    }*/

                    cat_name.setText(titles.get(0).getSectorName());
                    text_address.setText(titles.get(0).getAddress());
                    owner_name.setText(titles.get(0).getPersonName());
                    mobile_text.setText(titles.get(0).getMobile());
                    start_time.setText(titles.get(0).getOpeningTime());
                    close_time.setText(titles.get(0).getClosingTime());
                    work_day.setText(titles.get(0).getLeaveDay());
                    description.setText(titles.get(0).getDescription());

                    website.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (titles.get(0).getWebsite() != null) {
                                if (Utils.isNetworkAvailable(Category_Full_View.this)) {
                                    String url = titles.get(0).getWebsite().trim();
                                    System.out.println("urlprint" + url);
                                    //String url = "http://15.206.173.184/upload/nammaooru_tiruchengode/dashboard.php";
                                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                                    CustomTabsIntent customTabsIntent = builder.build();
                                    customTabsIntent.launchUrl(Category_Full_View.this, Uri.parse(url));
                                } else {
                                    Utils.toast_center(Category_Full_View.this, "Wharsapp not install...");
                                }
                            } else {
                                Utils.toast_center(Category_Full_View.this, "Website not available...");
                            }
                        }
                    });
                    location.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (titles.get(0).getLocation() != null) {
                                if (Utils.isNetworkAvailable(Category_Full_View.this)) {
                                    String url = titles.get(0).getLocation().trim();
                                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                                    CustomTabsIntent customTabsIntent = builder.build();
                                    customTabsIntent.launchUrl(Category_Full_View.this, Uri.parse(url));
                                } else {
                                    Utils.toast_center(Category_Full_View.this, "Wharsapp not install...");
                                }
                            } else {
                                Utils.toast_center(Category_Full_View.this, "Location not available...");
                            }
                        }
                    });
                    email.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Utils.isNetworkAvailable(Category_Full_View.this)) {
                                /*Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                        "mailto", "abc@gmail.com", null));
                                //emailIntent.setType("message/rfc822");
                                //emailIntent.setPackage("com.google.android.gm");
                                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "நம்ம ஊரு திருச்செங்கோடு");
                                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                                startActivity(Intent.createChooser(emailIntent, "Send email..."));*/

                                Intent intent = new Intent(Intent.ACTION_SENDTO);
                                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"abc@gmail.com"});
                                intent.putExtra(Intent.EXTRA_SUBJECT, "நம்ம ஊரு திருச்செங்கோடு");
                                intent.putExtra(Intent.EXTRA_TEXT, "Body Here");
                                if (intent.resolveActivity(getPackageManager()) != null) {
                                    startActivity(intent);
                                }


                            } else {
                                Utils.toast_center(Category_Full_View.this, "Wharsapp not install...");
                            }
                        }
                    });
                    phone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String phone = titles.get(0).getMobile().trim();
                            if (phone.equals("")) {
                                Utils.toast_center(Category_Full_View.this, "Mobile number not available");
                            } else {
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                                startActivity(intent);
                            }

                        }
                    });
                    whatsapp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (titles.get(0).getWhatsapp() != null) {
                                if (Utils.isNetworkAvailable(Category_Full_View.this)) {
                                    String phoneNumber = titles.get(0).getWhatsapp().trim();
                                    if (appInstalledOrNot("com.whatsapp") || appInstalledOrNot("com.whatsapp.w4b")) {
                                        String toNumber = phoneNumber;
                                        System.out.println("printnumber" + toNumber);

                                        try {
                                            if (!toNumber.startsWith("+91")) {
                                                toNumber = "+91" + phoneNumber;
                                            } else {
                                                toNumber = phoneNumber;
                                            }
                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://api.whatsapp.com/send?phone=" + toNumber + "&text="));
                                            startActivity(intent);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        Utils.toast_center(Category_Full_View.this, "Whatsapp not install...");
                                    }
                                } else {
                                    Utils.toast_center(Category_Full_View.this, "Check Your Internet Connection...");
                                }
                            } else {
                                Utils.toast_center(Category_Full_View.this, "Whatsapp number not available...");
                            }
                        }
                    });
                    facebook.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (titles.get(0).getFacebook() != null) {
                                if (Utils.isNetworkAvailable(Category_Full_View.this)) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(titles.get(0).getFacebook().trim()));
                                    startActivity(intent);
                                } else {
                                    Utils.toast_center(Category_Full_View.this, "Check Your Internet Connection...");
                                }
                            } else {
                                Utils.toast_center(Category_Full_View.this, "Facebook account not available...");
                            }
                        }
                    });
                    instagram.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (titles.get(0).getInstagram() != null) {
                                if (Utils.isNetworkAvailable(Category_Full_View.this)) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(titles.get(0).getInstagram().trim()));
                                    startActivity(intent);
                                } else {
                                    Utils.toast_center(Category_Full_View.this, "Check Your Internet Connection...");
                                }
                            } else {
                                Utils.toast_center(Category_Full_View.this, "Instagram account not available...");
                            }
                        }
                    });
                    twitter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (titles.get(0).getInstagram() != null) {
                                if (Utils.isNetworkAvailable(Category_Full_View.this)) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(titles.get(0).getTwitter().trim()));
                                    startActivity(intent);
                                } else {
                                    Utils.toast_center(Category_Full_View.this, "Check Your Internet Connection...");
                                }
                            } else {
                                Utils.toast_center(Category_Full_View.this, "Twitter account not available...");
                            }
                        }
                    });
                    share.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Utils.toast_center(Category_Full_View.this, "Details not available...");
                        }
                    });

                    adapter2.notifyDataSetChanged();
                    Utils.mProgress.dismiss();
                }
                System.out.println("======response :" + response);
            }

            @Override
            public void onFailure(Call<ArrayList<Full_View>> call, Throwable t) {
                System.out.println("======response t:" + t);
            }
        });
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

    public class Adapter2 extends RecyclerView.Adapter<Category_Full_View.Adapter2.ViewHolder> {
        ArrayList<Full_View> images;
        LayoutInflater inflater;
        Context context;
        String[] glide_image;

        public Adapter2(Context ctx, ArrayList<Full_View> images, String[] check) {
            this.images = images;
            this.inflater = LayoutInflater.from(ctx);
            this.context = ctx;
            this.glide_image = check;
        }

        @NonNull
        @Override
        public Category_Full_View.Adapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.best_seller, parent, false);
            Category_Full_View.Adapter2.ViewHolder viewHolder = new Category_Full_View.Adapter2.ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull Category_Full_View.Adapter2.ViewHolder holder, int position) {
            int pos = position;
            Glide.with(context).load(glide_image[pos])
                    .error(R.drawable.warning)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.gridImage);
        }

        @Override
        public int getItemCount() {
            return glide_image.length;
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