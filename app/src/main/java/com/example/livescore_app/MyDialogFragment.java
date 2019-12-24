package com.example.livescore_app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        view.findViewById(R.id.btnModify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                LayoutInflater inflater = getLayoutInflater();
                View myView = inflater.inflate(R.layout.dialog_modify, null);

                final Spinner teamSpinner = myView.findViewById(R.id.spinnerTeam);
                List<String> spinnerValues = new ArrayList<>();
                for (Match m : MainActivity.savedMatches) {
                    spinnerValues.add(m.getMatchId() + ". " + m.getHomeTeamName() + " - " + m.getAwayTeamName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, spinnerValues);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                teamSpinner.setAdapter(adapter);

                final EditText tvHomeTeam = myView.findViewById(R.id.homeTeamName);
                final EditText tvAwayTeam = myView.findViewById(R.id.awayTeamName);
                tvHomeTeam.setText(MainActivity.savedMatches.get(0).getHomeTeamName());
                tvAwayTeam.setText(MainActivity.savedMatches.get(0).getAwayTeamName());

                teamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (!tvHomeTeam.getText().toString().equalsIgnoreCase(MainActivity.savedMatches.get(position).getHomeTeamName())) {
                            tvHomeTeam.setText(MainActivity.savedMatches.get(position).getHomeTeamName());
                            tvAwayTeam.setText(MainActivity.savedMatches.get(position).getAwayTeamName());
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                builder.setView(myView)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                char id = teamSpinner.getSelectedItem().toString().toCharArray()[0];
                                int idd = Character.getNumericValue(id);
                                MainActivity.database.dbDao().updateMatch(tvHomeTeam.getText().toString(), tvAwayTeam.getText().toString(), idd);

                                for(Match m : MainActivity.savedMatches) {
                                    if(m.getMatchId() == idd) {
                                        m.setHomeTeamName(tvHomeTeam.getText().toString());
                                        m.setAwayTeamName(tvAwayTeam.getText().toString());
                                    }
                                }

                                Toast.makeText(getContext(), "Database update successfull", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.create().show();
            }
        });
    }
}
