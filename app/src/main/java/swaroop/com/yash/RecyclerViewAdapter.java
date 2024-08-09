package swaroop.com.yash;

/**
 * Created by Swaroop on 15-01-2018.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by AndroidJSon.com on 6/18/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    List<ImageUpload> MainImageUploadList;

    DatabaseReference ref= FirebaseDatabase.getInstance().getReference();

    public RecyclerViewAdapter(Context context, List<ImageUpload> TempList) {

        this.MainImageUploadList = TempList;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feeds, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ImageUpload UploadInfo = MainImageUploadList.get(position);

        holder.imageNameTextView.setText(UploadInfo.getName());

        //Loading image from Glide library.
        Glide.with(context).load(UploadInfo.getUrl()).into(holder.imageView);
//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                holder.lottieAnimationView.setVisibility(View.VISIBLE);
//                holder.lottieAnimationView.setEnabled(true);
//            }
//        });
    }

    @Override
    public int getItemCount() {

        return MainImageUploadList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView imageNameTextView;
        public TextView counter;
        public ImageView like;



        public ViewHolder(View itemView) {
            super(itemView);


            imageView = (ImageView) itemView.findViewById(R.id.post_image2);
            imageNameTextView = (TextView) itemView.findViewById(R.id.descriptiontxt10);
            //like= itemView.findViewById(R.id.image_heart_red1);
          // lottieAnimationView=itemView.findViewById(R.id.image_heart1);
          // counter=itemView.findViewById(R.id.countertext);z
        }
    }

}
