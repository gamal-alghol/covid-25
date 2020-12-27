package com.covid_19.fregments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.covid_19.Adapter.UsersAdabter;
import com.covid_19.R;
import com.covid_19.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class UsersFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    UsersAdabter usersAdapter;
    ArrayList<User> allUsers;
    ArrayList<User> usersChatted;
    RecyclerView listViewUsers;
    DatabaseReference reference;
    String CurrentUser;
    String UserId;
    ProgressBar progressBar;
View view;
    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;
        listViewUsers = view.findViewById(R.id.list_users);
        progressBar=view.findViewById(R.id.progressBar);

        allUsers =new ArrayList<>();
        getAllUsers();

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    private void getAllUsers(){
        new Handler().post(new Runnable() {
            @Override
            public void run() {

                FirebaseDatabase.getInstance().getReference("Users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                            if(!FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().equals(snapshot.getKey())){
                                User user=snapshot.getValue(User.class);
                                allUsers.add(user);
                            }
                        }
                        usersAdapter=new UsersAdabter(allUsers,getContext());
                        listViewUsers.setLayoutManager(new LinearLayoutManager(getContext()));
                        listViewUsers.setAdapter(usersAdapter);
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


    }
}

