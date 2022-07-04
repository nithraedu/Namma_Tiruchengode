package nithra.namma_tiruchengode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import nithra.namma_tiruchengode.Retrofit.Full_View;
import nithra.namma_tiruchengode.autoimageslider.SliderViewAdapter;


public class AutoSlidingGalleryAdapter extends SliderViewAdapter<SliderViewAdapter.ViewHolder> {

    ArrayList<Full_View> images;
    LayoutInflater inflater;
    Context context;
    String[] glide_image;


    public AutoSlidingGalleryAdapter(Context ctx, ArrayList<Full_View> images, String[] check) {
        this.images = images;
        this.inflater = LayoutInflater.from(ctx);
        this.context = ctx;
        this.glide_image = check;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {

            return new SliderAdapterVHnew(LayoutInflater.from(parent.getContext()).inflate(R.layout.best_seller, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


            SliderAdapterVHnew viewHolder = (SliderAdapterVHnew) holder;
        int pos = position;
        Glide.with(context).load(glide_image[pos])
                .error(R.drawable.tiruchengode)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.gridImage);
        System.out.println("imageurl"+glide_image[pos]);


    }

    @Override
    public int getCount() {
            return glide_image.length;
    }

    static class SliderAdapterVHnew extends ViewHolder {
        View itemView;
        ImageView gridImage;



        public SliderAdapterVHnew(View view) {
            super(view);

            gridImage = view.findViewById(R.id.imageGrid);

            this.itemView = view;
        }
    }

}
