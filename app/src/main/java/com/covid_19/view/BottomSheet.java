package com.covid_19.view;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.covid_19.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class BottomSheet extends BottomSheetDialogFragment {

    View view;
String info;
TextView answerTv, questionTV;
String answer, question;
    public BottomSheet(String question,String answer){
        this.question=question;
        this.answer=answer;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.bottom_sheet, container, false);
        answerTv =view.findViewById(R.id.answer);
        questionTV =view.findViewById(R.id.question);
        questionTV.setText(question);
        answerTv.setText(answer);
        return view;

    }




    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet,null,false);

        dialog.setContentView(rootView);
        FrameLayout bottomSheet =dialog.getWindow().findViewById(R.id.design_bottom_sheet);
        bottomSheet.setBackgroundResource(R.drawable.background_bottom_sheet_filter);

    }
}
