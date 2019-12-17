package com.example.livescore_app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyDialogFragment extends DialogFragment {
    private TextView tvContent;

    public MyDialogFragment() {

    }

    public static MyDialogFragment newInstance(String content) {
        MyDialogFragment fragment = new MyDialogFragment();
        Bundle args = new Bundle();
        args.putString("report", content);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment, container);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvContent = view.findViewById(R.id.tvScroll);
        String title = getArguments().getString("title");
        String report = getArguments().getString("report");

        tvContent.setText(report);

        view.findViewById(R.id.btnDismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        view.findViewById(R.id.btnSaveReport).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context ctx = view.getContext();
                try {
                    FileOutputStream file = ctx.openFileOutput("report.txt", Context.MODE_PRIVATE);
                    DataOutputStream out = new DataOutputStream(file);

                    out.writeUTF(getArguments().getString("report"));
                    out.flush();

                    file.close();

                    Toast.makeText(ctx, "Report succesfully saved to " + "report.txt", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
