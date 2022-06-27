package nithra.namma_tiruchengode.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import nithra.namma_tiruchengode.Activity_Third_List;
import nithra.namma_tiruchengode.Category_Full_View;
import nithra.namma_tiruchengode.R;
import nithra.namma_tiruchengode.Retrofit.Third_Category;

public class Helpline extends Fragment {


    public Helpline() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_helpline, container, false);
        return view;
    }


    public class Help_Adapter extends RecyclerView.Adapter<Help_Adapter.ViewHolder> {
        private LayoutInflater inflater;
        ArrayList<Third_Category> titles;
        public Context context;
        String title;

        public Help_Adapter(Context ctx, ArrayList<Third_Category> titles, String tool_title) {
            this.inflater = LayoutInflater.from(ctx);
            this.titles = titles;
            this.title = tool_title;
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


        }


        @Override
        public int getItemCount() {
            return titles.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

            }
        }
    }


}