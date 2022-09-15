package nithra.namma_tiruchengode.autoimageslider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;

import nithra.namma_tiruchengode.R;
import nithra.namma_tiruchengode.Retrofit.Category_Main;
import nithra.namma_tiruchengode.Retrofit.Slider;
import nithra.namma_tiruchengode.Utils;


public class AutoSlidingImageAdapterNew extends SliderViewAdapter<SliderViewAdapter.ViewHolder> {

    Context mContext;
    ArrayList<Slider> images;
    LayoutInflater inflater;
    Context context;


    public AutoSlidingImageAdapterNew(Context ctx, ArrayList<Slider> images) {
        mContext = ctx;
        this.images = images;
        this.inflater = LayoutInflater.from(ctx);
        this.context = ctx;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {

            return new SliderAdapterVHnew(LayoutInflater.from(parent.getContext()).inflate(R.layout.slider, null));
    }

    @Override
    public void onBindViewHolder(SliderViewAdapter.ViewHolder holder, int position) {


            SliderAdapterVHnew viewHolder = (SliderAdapterVHnew) holder;
        //viewHolder.img_slide.setImageResource(images.get(0).getSlider_image());
        int pos = position;


            Glide.with(context).load(images.get(pos).getSlider_image())
                    .error(R.drawable.tiruchengode)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.img_slide);
            //System.out.println("imageurl"+images[pos]);



    }

    @Override
    public int getCount() {
            return images.size();
    }

    static class SliderAdapterVHnew extends ViewHolder {
        View itemView;
        ImageView img_slide;



        public SliderAdapterVHnew(View view) {
            super(view);

            img_slide = view.findViewById(R.id.img_slide);

            this.itemView = view;
        }
    }

}
