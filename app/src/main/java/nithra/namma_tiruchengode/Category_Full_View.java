package nithra.namma_tiruchengode;

import android.app.Activity;
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
import nithra.namma_tiruchengode.autoimageslider.SliderAnimations;
import nithra.namma_tiruchengode.autoimageslider.SliderView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Category_Full_View extends AppCompatActivity {
    TextView cat_title, cat_name;
    int pos;
    String list_title;
    AutoSlidingGalleryAdapter adapter2;
    ArrayList<Full_View> images2;
    ArrayList<Full_View> images3;
    CardView location, website, email, whatsapp, facebook, instagram, twitter, phone;
    ImageView share;
    TextView text_address, owner_name, mobile_text, start_time, close_time, work_day, description, mobile_divider, name_divider;
    String idd;
    ArrayList<Full_View> titles;
    RecyclerView list3;
    SliderView slide;
    LinearLayout time, mobile_call, owner_lay;
    TextView btShowmore;


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
        time = findViewById(R.id.time);
        btShowmore = findViewById(R.id.btShowmore);

        btShowmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btShowmore.getText().toString().equalsIgnoreCase("Show more...")) {
                    description.setMaxLines(Integer.MAX_VALUE);//your TextView
                    btShowmore.setText("Show less");
                } else {
                    description.setMaxLines(3);//your TextView
                    btShowmore.setText("Show more...");
                }
            }
        });


        titles = new ArrayList<Full_View>();
        images2 = new ArrayList<Full_View>();
        images3 = new ArrayList<Full_View>();
        slide = findViewById(R.id.slide);
        list3 = findViewById(R.id.list3);
        whatsapp = findViewById(R.id.whatsapp);
        facebook = findViewById(R.id.facebook);
        instagram = findViewById(R.id.instagram);
        twitter = findViewById(R.id.twitter);
        share = findViewById(R.id.share);
        description = findViewById(R.id.description);
        mobile_call = findViewById(R.id.mobile_call);
        mobile_divider = findViewById(R.id.mobile_divider);
        owner_lay = findViewById(R.id.owner_lay);
        name_divider = findViewById(R.id.name_divider);
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
        //slide.setLayoutManager(gridLayoutManager2);

      /*  list3.setLayoutManager(gridLayoutManager3);
        adapter2 = new Adapter2(this, images3);
        list3.setAdapter(adapter2);*/
        Utils_Class.mProgress(this, "Loading please wait...", false).show();
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

                    String currentString = titles.get(0).getSliderImage();
                    System.out.println("image_print" + titles.get(0).getSliderImage());
                    String[] separated = currentString.split(",");
                    adapter2 = new AutoSlidingGalleryAdapter(Category_Full_View.this, images2, separated);
                    slide.setSliderAdapter(adapter2);
                    slide.setSliderTransformAnimation(
                            SliderAnimations.SIMPLETRANSFORMATION
                    );
                    slide.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);

                    slide.setScrollTimeInSec(5);
                    slide.setAutoCycle(true);
                    slide.startAutoCycle();
                    System.out.println("printsplit" + separated.toString());

                    if (titles.get(0).getSectorName().trim().isEmpty()) {
                        cat_name.setVisibility(View.GONE);
                    } else {
                        cat_name.setText(titles.get(0).getSectorName());
                        cat_name.setVisibility(View.VISIBLE);
                    }

                    if (titles.get(0).getAddress().trim().isEmpty()) {
                        text_address.setVisibility(View.GONE);
                    } else {
                        text_address.setText(titles.get(0).getAddress());
                        text_address.setVisibility(View.VISIBLE);
                    }

             /*       if (titles.get(0).getPersonName().trim().trim().isEmpty()) {
                        owner_lay.setVisibility(View.GONE);
                        name_divider.setVisibility(View.GONE);
                    } else {
                        owner_name.setText(titles.get(0).getPersonName().trim());
                        owner_lay.setVisibility(View.VISIBLE);
                        name_divider.setVisibility(View.VISIBLE);
                    }*/

                   /* if (titles.get(0).getMobile().trim().isEmpty()) {
                        mobile_call.setVisibility(View.GONE);
                        mobile_divider.setVisibility(View.GONE);
                    } else {
                        mobile_text.setText(titles.get(0).getMobile());
                        mobile_call.setVisibility(View.VISIBLE);
                        mobile_divider.setVisibility(View.VISIBLE);
                    }*/

                    if (titles.get(0).getOpeningTime().trim().isEmpty()) {
                        time.setVisibility(View.GONE);
                    } else {
                        start_time.setText(titles.get(0).getOpeningTime());
                        time.setVisibility(View.VISIBLE);
                    }

                    if (titles.get(0).getClosingTime().trim().isEmpty()) {
                        time.setVisibility(View.GONE);
                    } else {
                        close_time.setText(titles.get(0).getClosingTime());
                        time.setVisibility(View.VISIBLE);
                    }

                    if (titles.get(0).getLeaveDay().trim().isEmpty()) {
                        work_day.setVisibility(View.GONE);
                    } else {
                        work_day.setText(titles.get(0).getLeaveDay());
                        work_day.setVisibility(View.VISIBLE);
                    }

                    if (titles.get(0).getDescription().trim().isEmpty()) {
                        description.setVisibility(View.GONE);
                    } else {
                        description.setText(titles.get(0).getDescription() + "\n\nContact" + "\n" + titles.get(0).getMobile());
                        description.setVisibility(View.VISIBLE);
                    }


                    if (description.getLineCount() > 3) {
                        btShowmore.setVisibility(View.VISIBLE);
                    } else {
                        btShowmore.setVisibility(View.GONE);
                    }


                  /*  cat_name.setText(titles.get(0).getSectorName());
                    text_address.setText(titles.get(0).getAddress());
                    owner_name.setText(titles.get(0).getPersonName());
                    mobile_text.setText(titles.get(0).getMobile());
                    start_time.setText(titles.get(0).getOpeningTime());
                    close_time.setText(titles.get(0).getClosingTime());
                    work_day.setText(titles.get(0).getLeaveDay());
                    description.setText(titles.get(0).getDescription());*/

                    website.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (titles.get(0).getWebsite() != null && !titles.get(0).getWebsite().trim().isEmpty()) {
                                if (Utils_Class.isNetworkAvailable(Category_Full_View.this)) {
                                    String url = titles.get(0).getWebsite().trim();
                                    System.out.println("urlprint" + url);
                                    //String url = "http://15.206.173.184/upload/nammaooru_tiruchengode/dashboard.php";
                                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                                    CustomTabsIntent customTabsIntent = builder.build();
                                    customTabsIntent.launchUrl(Category_Full_View.this, Uri.parse(url));
                                } else {
                                    Utils_Class.toast_center(Category_Full_View.this, "Check Your Internet Connection...");
                                }
                            } else {
                                Utils_Class.toast_center(Category_Full_View.this, "Website not available...");
                            }
                        }
                    });
                    location.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (titles.get(0).getLocation() != null && !titles.get(0).getLocation().trim().isEmpty()) {
                                if (Utils_Class.isNetworkAvailable(Category_Full_View.this)) {
                                    String url = titles.get(0).getLocation().trim();
                                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                                    CustomTabsIntent customTabsIntent = builder.build();
                                    customTabsIntent.launchUrl(Category_Full_View.this, Uri.parse(url));
                                } else {
                                    Utils_Class.toast_center(Category_Full_View.this, "Check Your Internet Connection...");
                                }
                            } else {
                                Utils_Class.toast_center(Category_Full_View.this, "Location not available...");
                            }
                        }
                    });
                    email.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (titles.get(0).getEmail() != null && !titles.get(0).getEmail().trim().isEmpty()) {
                                if (Utils_Class.isNetworkAvailable(Category_Full_View.this)) {
                                /*Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                        "mailto", "abc@gmail.com", null));
                                //emailIntent.setType("message/rfc822");
                                //emailIntent.setPackage("com.google.android.gm");
                                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "நம்ம ஊரு திருச்செங்கோடு");
                                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                                startActivity(Intent.createChooser(emailIntent, "Send email..."));*/

                                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                                    intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{titles.get(0).getEmail().trim()});
                                    intent.putExtra(Intent.EXTRA_SUBJECT, "நம்ம ஊரு திருச்செங்கோடு");
                                    intent.putExtra(Intent.EXTRA_TEXT, "Body Here");
                                    if (intent.resolveActivity(getPackageManager()) != null) {
                                        startActivity(intent);
                                    }


                                } else {
                                    Utils_Class.toast_center(Category_Full_View.this, "Check Your Internet Connection...");
                                }
                            } else {
                                Utils_Class.toast_center(Category_Full_View.this, "Email not available...");

                            }
                        }
                    });
                    phone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String phone = titles.get(0).getMobile().trim();
                            if (phone.equals("")) {
                                Utils_Class.toast_center(Category_Full_View.this, "Mobile number not available");
                            } else {
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                                startActivity(intent);
                            }

                        }
                    });
                    whatsapp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (titles.get(0).getWhatsapp() != null && !titles.get(0).getWhatsapp().trim().isEmpty()) {
                                if (Utils_Class.isNetworkAvailable(Category_Full_View.this)) {
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
                                        Utils_Class.toast_center(Category_Full_View.this, "Whatsapp not install...");
                                    }
                                } else {
                                    Utils_Class.toast_center(Category_Full_View.this, "Check Your Internet Connection...");
                                }
                            } else {
                                Utils_Class.toast_center(Category_Full_View.this, "Whatsapp number not available...");
                            }
                        }
                    });
                    facebook.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (titles.get(0).getFacebook() != null && !titles.get(0).getFacebook().trim().isEmpty()) {
                                if (Utils_Class.isNetworkAvailable(Category_Full_View.this)) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(titles.get(0).getFacebook().trim()));
                                    startActivity(intent);
                                } else {
                                    Utils_Class.toast_center(Category_Full_View.this, "Check Your Internet Connection...");
                                }
                            } else {
                                Utils_Class.toast_center(Category_Full_View.this, "Facebook account not available...");
                            }
                        }
                    });
                    instagram.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (titles.get(0).getInstagram() != null && !titles.get(0).getInstagram().trim().isEmpty()) {
                                if (Utils_Class.isNetworkAvailable(Category_Full_View.this)) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(titles.get(0).getInstagram().trim()));
                                    startActivity(intent);
                                } else {
                                    Utils_Class.toast_center(Category_Full_View.this, "Check Your Internet Connection...");
                                }
                            } else {
                                Utils_Class.toast_center(Category_Full_View.this, "Instagram account not available...");
                            }
                        }
                    });
                    twitter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (titles.get(0).getInstagram() != null && !titles.get(0).getInstagram().trim().isEmpty()) {
                                if (Utils_Class.isNetworkAvailable(Category_Full_View.this)) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(titles.get(0).getTwitter().trim()));
                                    startActivity(intent);
                                } else {
                                    Utils_Class.toast_center(Category_Full_View.this, "Check Your Internet Connection...");
                                }
                            } else {
                                Utils_Class.toast_center(Category_Full_View.this, "Twitter account not available...");
                            }
                        }
                    });
                    share.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Utils_Class.toast_center(Category_Full_View.this, "Details not available...");
                        }
                    });

                    adapter2.notifyDataSetChanged();
                    Utils_Class.mProgress.dismiss();
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

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

}