package nithra.namma_tiruchengode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import nithra.namma_tiruchengode.Retrofit.Third_Category;

public class Activity_Search extends AppCompatActivity {
    SearchView search_view;
    ListAdapter_1 adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search);
        search_view = findViewById(R.id.search_view);
        search_view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(view, 0);
                }
            }
        });
    }

    public class ListAdapter_1 extends RecyclerView.Adapter<Activity_Search.ListAdapter_1.ViewHolder> {
        private LayoutInflater inflater;
        ArrayList<Third_Category> titles;
        public Context context;
        String title;

        public ListAdapter_1(Context ctx,ArrayList<Third_Category> titles,String tool_title) {
            this.inflater = LayoutInflater.from(ctx);
            this.titles = titles;
            this.title=tool_title;
            this.context = ctx;
        }

        @NonNull
        @Override
        public Activity_Search.ListAdapter_1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.third_adapter, parent, false);
            Activity_Search.ListAdapter_1.ViewHolder viewHolder = new Activity_Search.ListAdapter_1.ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull Activity_Search.ListAdapter_1.ViewHolder holder, int position) {
            int pos=position;
            holder.cat_title.setText(titles.get(pos).sectorName);
            holder.adderss.setText(titles.get(pos).address);
            holder.start_time.setText(titles.get(pos).openingTime);
            holder.close_time.setText(titles.get(pos).closingTime);
            holder.work_day.setText(titles.get(pos).leaveDay);
            Glide.with(context).load(titles.get(pos).logo)
                    .error(R.drawable.warning)
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
            TextView cat_title,adderss,start_time,close_time,work_day;
            LinearLayout list_click;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                cat_icon = itemView.findViewById(R.id.cat_icon);
                cat_title = itemView.findViewById(R.id.cat_title);
                list_click=itemView.findViewById(R.id.list_click);
                adderss=itemView.findViewById(R.id.adderss);
                start_time=itemView.findViewById(R.id.start_time);
                close_time=itemView.findViewById(R.id.close_time);
                work_day=itemView.findViewById(R.id.work_day);
            }
        }
    }

}