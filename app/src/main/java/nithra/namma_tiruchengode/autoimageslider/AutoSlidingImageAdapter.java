package nithra.namma_tiruchengode.autoimageslider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import nithra.namma_tiruchengode.R;


public class AutoSlidingImageAdapter extends SliderViewAdapter<SliderViewAdapter.ViewHolder> {

    Context mContext;
    ArrayList<Integer> images;
    LayoutInflater inflater;


    public AutoSlidingImageAdapter(Context ctx, ArrayList<Integer> images) {
        mContext = ctx;
        this.images = images;
        this.inflater = LayoutInflater.from(ctx);

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {

            return new SliderAdapterVHnew(LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_2, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


            SliderAdapterVHnew viewHolder = (SliderAdapterVHnew) holder;
        viewHolder.slide_mat.setImageResource(images.get(position));



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
