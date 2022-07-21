package nithra.namma_tiruchengode.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ShareCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import nithra.namma_tiruchengode.R;
import nithra.namma_tiruchengode.Retrofit.RetrofitAPI;
import nithra.namma_tiruchengode.Retrofit.RetrofitAPIClient;
import nithra.namma_tiruchengode.Retrofit.T_codePojo;
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
        adapter = new Tcode(getContext(), tcode);
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

        Tcode(Context ctx, ArrayList<T_codePojo> data_load) {
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

            if (holder.heading.getLineCount() > 2) {
                holder.btShowmore.setVisibility(View.VISIBLE);
            } else {
                holder.btShowmore.setVisibility(View.GONE);
            }

            holder.btShowmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (holder.btShowmore.getText().toString().equalsIgnoreCase("Show more...")) {
                        holder.heading.setMaxLines(Integer.MAX_VALUE);//your TextView
                        holder.btShowmore.setText("Show less");
                    } else {
                        holder.heading.setMaxLines(2);//your TextView
                        holder.btShowmore.setText("Show more...");
                    }
                }
            });
            holder.share_news.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    File dir = getContext().getFilesDir();
                    Uri uri = FileProvider.getUriForFile(getContext(), "com.example.provider", new File(String.valueOf(dir)));
                    Intent share = ShareCompat.IntentBuilder.from((Activity) context)
                            .setStream(uri) // uri from FileProvider
                            .setType("text/html")
                            .getIntent()
                            .setAction(Intent.ACTION_SEND) //Change if needed
                            .setDataAndType(uri, "image/*")
                            .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    startActivity(Intent.createChooser(share, data_load.get(pos).contentImage));


                  /*  try {
                        URL url = new URL(data_load.get(pos).contentImage);
                        Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                        intent.setType("image/jpeg");
                        intent.putExtra(Intent.EXTRA_STREAM, image);
                        startActivity(intent);

                    } catch(IOException e) {
                        System.out.println(e);
                    }*/

                   /* Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    String shareBody = data_load.get(pos).newsContent;
                    intent.setType("text/plain");
                    //intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
                    intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    startActivity(intent);*/


                }
            });


        }


        @Override
        public int getItemCount() {
            return data_load.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView heading, date, btShowmore;
            ImageView image, share_news;
            LinearLayout layout;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                heading = itemView.findViewById(R.id.heading);
                date = itemView.findViewById(R.id.date);
                image = itemView.findViewById(R.id.image);
                btShowmore = itemView.findViewById(R.id.btShowmore);
                share_news = itemView.findViewById(R.id.share_news);
                layout=itemView.findViewById(R.id.layout_one);
            }
        }


        public void share_agee(final LinearLayout Main_layout) {
            Main_layout.postDelayed(() -> {
                Bitmap bitmap = Bitmap.createBitmap(Main_layout.getWidth(), Main_layout.getHeight(), Bitmap.Config.ARGB_8888);

                Canvas canvas = new Canvas(bitmap);
                Main_layout.draw(canvas);


                String root = getContext().getFilesDir().toString();
                File mydir = new File(root + "/Nithra/Tamil Calendar");
                mydir.mkdirs();
                String fname = "Image-cash.jpg";
                final File file = new File(mydir, fname);

                if (file.exists()) {
                    file.delete();
                }

                try {
                    FileOutputStream out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    out.flush();
                    out.close();

                    if (file.exists()) {
                        //Uri data = FileProvider.getUriForFile(mContext, mContext.getPackageName() + "", new File(file[0]));
                        Uri uri = FileProvider.getUriForFile(getContext(),getContext().getPackageName(),file);
                        Intent share = new Intent();
                        share.setAction(Intent.ACTION_SEND);
                        share.setType("image/*");
                        share.putExtra(Intent.EXTRA_STREAM, uri);
                        share.putExtra(Intent.EXTRA_SUBJECT,
                                "மகளிர் மட்டும் செயலி");
                        share.putExtra(
                                Intent.EXTRA_TEXT,
                                "\n\nஇது போன்று பெண்களுக்கு தேவையான அனைத்து தகவல்களையும் கொண்ட  நித்ரா மகளிர் மட்டும் செயலியை இலவசமாக பதிவிறக்கம் செய்ய இங்கே கிளிக் செய்யுங்கள்."+ "\n\nhttps://goo.gl/3RxJ2J");
                        startActivity(Intent.createChooser(share, "Share"));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 10);
        }


    }




}