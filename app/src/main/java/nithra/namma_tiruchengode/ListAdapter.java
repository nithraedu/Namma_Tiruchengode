package nithra.namma_tiruchengode;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import java.util.List;

import nithra.namma_tiruchengode.Retrofit.Sub_Category;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private LayoutInflater inflater;
    ArrayList<Sub_Category> titles;
    public Context context;
    Title_Interface re_touch;
    String title;


    public ListAdapter(Context ctx,ArrayList<Sub_Category> titles,String tool_title) {
        this.inflater = LayoutInflater.from(ctx);
        this.titles = titles;
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

        holder.cat_title.setText(titles.get(pos).subCategory);
        //holder.count.setText(titles.get(pos).view_count);
        System.out.println("checkcount"+titles.get(pos).view_count);
        Glide.with(context).load(titles.get(pos).subCategoryLogo)
                .error(R.drawable.warning)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.cat_icon);
        holder.list_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Activity_Third_List.class);
                i.putExtra("list_title", titles.get(pos).subCategory);
                i.putExtra("id", pos);
                i.putExtra("idd", titles.get(pos).getId());
                System.out.println("==check id"+titles.get(pos).getId());
                context.startActivity(i);
                re_touch.category();

            }
        });
    }



    @Override
    public int getItemCount() {
        return titles.size();
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
            //count=itemView.findViewById(R.id.count);

        }
    }
}


