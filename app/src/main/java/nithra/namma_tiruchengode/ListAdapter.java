package nithra.namma_tiruchengode;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import nithra.namma_tiruchengode.Retrofit.Sub_Category;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private LayoutInflater inflater;
    ArrayList<Sub_Category> sub_list;
    public Activity context;
    Title_Interface re_touch;
    String title;

    public ListAdapter(Activity ctx,ArrayList<Sub_Category> sub_list,String tool_title) {
        this.inflater = LayoutInflater.from(ctx);
        this.sub_list = sub_list;
        this.title=tool_title;
        this.context = ctx;
        re_touch=(Title_Interface) context;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_data, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int pos=position;

        holder.cat_title.setText(sub_list.get(pos).subCategory);
        holder.count.setText(sub_list.get(pos).view_count);
        System.out.println("checkcount"+sub_list.get(pos).view_count);
        Glide.with(context).load(sub_list.get(pos).subCategoryLogo)
                .error(R.drawable.warning)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.cat_icon);
        holder.list_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int LAUNCH_SECOND_ACTIVITY = 1;
                Intent i = new Intent(context, Activity_Third_List.class);
                i.putExtra("list_title", sub_list.get(pos).subCategory);
                i.putExtra("id", pos);
                i.putExtra("idd", sub_list.get(pos).getId());
                //i.putExtra("cont",sub_list.get(pos).view_count);
                System.out.println("==check id"+sub_list.get(pos).getId());
                context.startActivityForResult(i,LAUNCH_SECOND_ACTIVITY);
                re_touch.category();


            }
        });
    }



    @Override
    public int getItemCount() {
        return sub_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cat_icon;
        TextView cat_title,count;
        LinearLayout list_click;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cat_icon = itemView.findViewById(R.id.cat_icon);
            cat_title = itemView.findViewById(R.id.cat_title);
            list_click=itemView.findViewById(R.id.list_click);
            count=itemView.findViewById(R.id.count);

        }
    }
}


