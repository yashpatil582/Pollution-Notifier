package swaroop.com.yash;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Swaroop on 08-01-2018.
 */

public class feeds extends AppCompatActivity {

    private static final String TAG ="HomeActivity" ;
    protected FloatingActionMenu floatingActionMenu;
    private static final int ACTIVITY_NUM = 0;
    private Context mContext = feeds.this;
    private static int POST_DELAYED=1500;

    private RecyclerView mRecyclerview;
    private FrameLayout mFrameLayout;
    private RelativeLayout mRelativeLayout;
    protected ImageButton button2;

    FirebaseStorage storage;
    private ImageView mImageView;
    private StorageReference mStorage;

    //public SwipeRefreshLayout swipeRefreshLayout;


    //  SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
    // Creating DatabaseReference.
    DatabaseReference databaseReference;

    // Creating RecyclerView.
    RecyclerView recyclerView;

    // Creating RecyclerView.Adapter.
    RecyclerView.Adapter adapter ;

    // Creating Progress dialog
    ProgressDialog progressDialog;

    // Creating List of ImageUploadInfo class.
    List<ImageUpload> list = new ArrayList<>();
    List<ImageUpload> list2= new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_fragment);

        mStorage = FirebaseStorage.getInstance().getReference();

        //LottieAnimationView animationView= findViewById(R.id.image_heart1);
        ImageView i=findViewById(R.id.post_image2);
        // final ImageView a=findViewById(R.id.image_heart_red1);

        // mRelativeLayout = (RelativeLayout) findViewById(R.id.relLayoutParent);
        //mImageView = (ImageView) findViewById(R.id.post_image);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView100);

        // Setting RecyclerView size true.
        recyclerView.setHasFixedSize(true);

        // Setting RecyclerView layout as LinearLayout.
        recyclerView.setLayoutManager(new LinearLayoutManager(feeds.this));
        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(mContext) {
            @Override protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };

        // Assign activity this to progress dialog.

        progressDialog = new ProgressDialog(feeds.this);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading...");

        // Showing progress dialog.
        progressDialog.show();

        // Setting up Firebase image upload folder path in databaseReference.
        // The path is already defined in MainActivity.
        databaseReference = FirebaseDatabase.getInstance().getReference(upload.FB_DATABASE_PATH);

        getData();

    }

    public void getData(){

        // Adding Add Value Event Listener to databaseReference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    ImageUpload imageUpload = postSnapshot.getValue(ImageUpload.class);

                    list.add(imageUpload);


                }
                int cnt=list.size();

                for(int i=cnt;i>0;i--)
                {
                    list2.add(list.get(i-1));
                }


                adapter = new RecyclerViewAdapter(getApplicationContext(), list2);
                recyclerView.setAdapter(adapter);

                // Hiding the progress dialog.
                progressDialog.dismiss();
                //swipeRefreshLayout.setEnabled(true);
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

                // Hiding the progress dialog.
                progressDialog.dismiss();

            }

        });

    }





}