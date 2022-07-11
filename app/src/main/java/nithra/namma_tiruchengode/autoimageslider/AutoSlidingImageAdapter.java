package nithra.namma_tiruchengode.autoimageslider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import nithra.namma_tiruchengode.R;
import nithra.namma_tiruchengode.Retrofit.BannerSlider;


public class AutoSlidingImageAdapter extends SliderViewAdapter<SliderViewAdapter.ViewHolder> {

    Context mContext;
    ArrayList<BannerSlider> images;
    LayoutInflater inflater;
    Context context;


    public AutoSlidingImageAdapter(Context ctx, ArrayList<BannerSlider> images) {
        mContext = ctx;
        this.images = images;
        this.inflater = LayoutInflater.from(ctx);
        this.context = ctx;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {

            return new SliderAdapterVHnew(LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_2, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


            SliderAdapterVHnew viewHolder = (SliderAdapterVHnew) holder;
       // viewHolder.slide_mat.setImageResource(images.get(position));
        int pos = position;
        if(images.get(pos).getBanner_slider_image().isEmpty()){
            ((SliderAdapterVHnew) holder).slide_mat.setVisibility(View.GONE);
        }
        else {
            Glide.with(context).load(images.get(pos).getBanner_slider_image())
                    .error(R.drawable.tiruchengode)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.slide_mat);
        }
    }

    @Override
    public int getCount() {
            return images.size();
    }

    static class SliderAdapterVHnew extends ViewHolder {
        View itemView;
        ImageView slide_mat;



        public SliderAdapterVHnew(View view) {
            super(view);

            slide_mat = view.findViewById(R.id.slide_mat);

            this.itemView = view;
        }
    }

}
