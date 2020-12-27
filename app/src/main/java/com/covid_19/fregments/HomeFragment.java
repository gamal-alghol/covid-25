package com.covid_19.fregments;


import android.Manifest;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.covid_19.R;
import com.covid_19.view.BottomSheet;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    ImageView corona,symptoms,protection, Infection;
    ImageView [] imageViews;
    String [] Uris;
    private    View bottomSheet;
    PlayerView playerView;
    private SimpleExoPlayer player;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    ProgressBar loading;
    View view;
    String urlVidoe;

    FirebaseStorage storage=FirebaseStorage.getInstance(); ;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        corona=view.findViewById(R.id.img_corona);
        symptoms=view.findViewById(R.id.img_symptoms);
        protection=view.findViewById(R.id.img_protection);
        Infection =view.findViewById(R.id.img_Syndrome);
        bottomSheet = view.findViewById(R.id.bottom_sheet);
        playerView = view.findViewById(R.id.video_view);
        loading = view.findViewById(R.id.loading);
        imageViews= new ImageView[]{corona, symptoms, protection, Infection};
        this.view=view;
        ActivityCompat.requestPermissions((Activity) view.getContext(),
                new String[]{Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                100);


        Uris= new String[]{"covid.jpg", "protection.jpg", "Symptoms-of-Corona-Virus-scaled.jpg", "symptoms.jpg"};
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        downloadVideo();
        downLoadImages();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistisc, container, false);
    }
    private void downLoadImages(){
        imageNum1();
        imageNum2();
        imageNum3();
        imageNum4();
    }

    private void imageNum4() {
        storage.getReference().child(Uris[3]).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(getContext())
                        .load(uri.toString())
                        .into(imageViews[3]);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getContext(), "cant dwonload image", Toast.LENGTH_SHORT).show();

            }
        });
        imageViews[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dowloadText("Infection");
            }
        });
    }

    private void imageNum3() {
        storage.getReference().child(Uris[2]).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(getContext())
                        .load(uri.toString())
                        .into(imageViews[2]);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getContext(), "cant dwonload image", Toast.LENGTH_SHORT).show();

            }
        });
        imageViews[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dowloadText("symptoms");
            }
        });



    }
    private void imageNum2(){
        storage.getReference().child(Uris[1]).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(getContext())
                        .load(uri.toString())
                        .into(imageViews[1]);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getContext(), "cant dwonload image", Toast.LENGTH_SHORT).show();

            }
        });

        imageViews[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dowloadText("protection");
            }
        });
    }

    private void imageNum1() {
        storage.getReference().child(Uris[0]).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(getContext())
                        .load(uri.toString())
                        .into(imageViews[0]);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getContext(), "cant dwonload image", Toast.LENGTH_SHORT).show();

            }
        });

        imageViews[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dowloadText("definition");

            }
        });
    }

    private void dowloadText(String info){
        FirebaseFirestore.getInstance().collection("information").
                document(info).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        BottomSheet bottomSheetFilter = new BottomSheet(task.getResult().get("question").toString(),task.getResult().get("answer").toString());
                        bottomSheetFilter.show(getFragmentManager(), bottomSheetFilter.getTag());
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    private void downloadVideo(){

        storage.getReference().child("video1.mp4").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                 urlVidoe = uri.toString();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getContext(), "cant dwonload image", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void initializePlayer() {
        TrackSelection.Factory adaptiveTrackSelection = new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
        player = ExoPlayerFactory.newSimpleInstance(view.getContext(),new DefaultTrackSelector(adaptiveTrackSelection), new DefaultLoadControl());

        playerView.setPlayer(player);

        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(view.getContext(),
                Util.getUserAgent(view.getContext(), "Exo2"), defaultBandwidthMeter);

        Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/my-project-covid-9e1c7.appspot.com/o/video1.mp4?alt=media&token=cff28f20-829b-4694-8573-d4cd65af8dd6");
        MediaSource mediaSource = buildMediaSource(uri);

        player.setPlayWhenReady(playWhenReady);

        player.addListener(new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState) {
                    case ExoPlayer.STATE_READY:
                        loading.setVisibility(View.GONE);
                        break;
                    case ExoPlayer.STATE_BUFFERING:
                        loading.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        player.seekTo(currentWindow, playbackPosition);
        player.prepare(mediaSource, true, false);

    }

    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(view.getContext(), "exoplayer-codelab");
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        TrackSelection.Factory adaptiveTrackSelection = new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());

        initializePlayer();

    }

    @Override
    public void onResume() {
        super.onResume();
        if ( player == null) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        releasePlayer();

    }

    @Override
    public void onStop() {
        super.onStop();

        releasePlayer();

    }

}
