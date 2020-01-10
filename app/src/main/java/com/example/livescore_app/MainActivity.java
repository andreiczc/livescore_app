package com.example.livescore_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Network net;
    public static List<Match> savedMatches = null;
    public static DatabaseInstance database;

    public static final int SharedPrefs = 1;
    public static final int DB = 2;
    public static final int Text = 3;
    public static final int ClearSharedPrefs = 4;
    public static final int ShowText = 5;
    public static final int ShowDB = 6;
    public static final int ClearDB = 7;
    public static final int ShowMatchesByLeagueDB = 8;
    public static final int BackupToFirebase = 9;

    private FirebaseDatabase fbDatabase;
    private FirebaseAuth fbAuth;

    private Button btnAccount;

    private View.OnClickListener signedIn;
    private View.OnClickListener signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = DatabaseInstance.getDatabaseInstance(this);

        try {
            net = new Network();
            net.execute(new URL("http://api.football-data.org/v2/matches"));
        } catch (Exception e) {
            Log.e("errorNetwork", e.getMessage());
        }

        fbAuth = FirebaseAuth.getInstance();
        fbDatabase = FirebaseDatabase.getInstance();

        /*fbAuth.createUserWithEmailAndPassword("andrei.cazacu@live.com", "03andrei").addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Register succesfull", Toast.LENGTH_LONG).show();
                    FirebaseUser user = fbAuth.getCurrentUser();
                } else {
                    Toast.makeText(MainActivity.this, "Register failed", Toast.LENGTH_LONG).show();
                }
            }
        });*/

        /*fbAuth.signInWithEmailAndPassword("andrei.cazacu@live.com", "03andrei").addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                    FirebaseUser user = fbAuth.getCurrentUser();
                } else {
                    Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                }
            }
        });*/

        btnAccount = findViewById(R.id.btnAccount);

        signedIn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Are you sure that you want to sign out ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                fbAuth.signOut();
                                btnAccount.setText(R.string.sign_in);
                                btnAccount.setOnClickListener(signIn);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        };

        signIn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View loginView = inflater.inflate(R.layout.dialog_login, null);
                builder.setView(loginView)
                        .setTitle("Login dialog");

                final EditText tbEmail = loginView.findViewById(R.id.tbEmail);
                final EditText tbPassword = loginView.findViewById(R.id.tbPassword);
                final Button btnLogin = loginView.findViewById(R.id.btnLogin);
                final Button btnDismiss = loginView.findViewById(R.id.btnNoLogin);
                final Button btnRegister = loginView.findViewById(R.id.btnRegister);

                final AlertDialog dialog = builder.create();

                btnDismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email = tbEmail.getText().toString();
                        String password = tbPassword.getText().toString();

                        fbAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                FirebaseUser user = fbAuth.getCurrentUser();
                                if (user != null) {
                                    btnAccount.setText(user.getEmail());
                                    btnAccount.setOnClickListener(signedIn);

                                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Login failed.\nPlease try to input your email or password again.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });

                btnRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email = tbEmail.getText().toString();
                        String password = tbPassword.getText().toString();

                        if (email.contains("@")) {
                            fbAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    FirebaseUser user = fbAuth.getCurrentUser();
                                    if (task.isSuccessful()) {
                                        btnAccount.setText(user.getEmail());
                                        btnAccount.setOnClickListener(signedIn);

                                        Toast.makeText(getApplicationContext(), "Register successful", Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Register failed.\nThe email address can not be used in multiple accounts", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "Register failed.\nPlease make sure your email has a valid form.", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                dialog.show();
            }
        };

        FirebaseUser user = fbAuth.getCurrentUser();
        if (user != null) {
            btnAccount.setText(user.getEmail());
            btnAccount.setOnClickListener(signedIn);
        } else {
            btnAccount.setText(R.string.sign_in);
            btnAccount.setOnClickListener(signIn);
        }

        if (savedMatches == null) {
            //readMatchesPreferences("matches");
            //savedMatches = database.dbDao().getMatches();
            savedMatches = readMatchesFromFirebase();
        }
    }

    private List<Match> readMatchesFromFirebase() {
        FirebaseUser user = fbAuth.getCurrentUser();
        if (user != null) {
            return null;
        } else {
            return null;
        }
    }

    public void showMatches(View view) {
        Intent intent = new Intent(this, ShowMatchesActivity.class);
        intent.putExtra("json", net.getObjResult().toString());

        startActivity(intent);
    }

    public void savedMatches(View view) {
        Intent intent = new Intent(this, SavedMatchesActivity.class);

        startActivity(intent);
    }

    public void showMatchesByDate(View view) {
        Intent intent = new Intent(this, ChooseDateActivity.class);

        startActivity(intent);
    }

    private void saveMatchesToFirebase(List<Match> matches) {
        FirebaseUser user = fbAuth.getCurrentUser();
        if (user != null) {
            String key = user.getUid();
            DatabaseReference reference = fbDatabase.getReference(key);
            reference.setValue(savedMatches);
            Toast.makeText(this, "Backup successful", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "You are not logged in", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, SharedPrefs, 1, "Save matches with shared preferences");
        menu.add(0, ClearSharedPrefs, 1, "Clear matches from preference");
        menu.add(0, DB, 3, "Save matches to database");
        menu.add(0, ShowDB, 4, "Show matches saved in the database");
        menu.add(0, ShowMatchesByLeagueDB, 5, "Show matches by league");
        menu.add(0, ClearDB, 6, "Delete all saved matches from db");
        menu.add(0, Text, 7, "Save matches with text file");
        menu.add(0, ShowText, 8, "Show matches with text file");
        menu.add(0, BackupToFirebase, 9, "Backup to online database");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case SharedPrefs:
                saveMatchesPreferences(savedMatches);
                break;
            case DB:
                saveMatchesDatabase();
                break;
            case Text:
                try {
                    writeToText("matches.txt");
                } catch (IOException e) {
                    Toast.makeText(this, "Can't save matches to file", Toast.LENGTH_LONG).show();
                }
                break;
            case ClearSharedPrefs:
                clearMatchesPreference("matches");
                break;
            case ShowText:
                try {
                    readFromText("matches.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case ShowDB:
                showMatchesDb();
                break;
            case ClearDB:
                database.dbDao().deleteAllMatches();
                database.dbDao().deleteAllLeagues();
                Toast.makeText(this, "Database successfully cleared", Toast.LENGTH_LONG).show();
                break;
            case ShowMatchesByLeagueDB:
                showMatchesByLeagueDb();
                break;
            case BackupToFirebase:
                saveMatchesToFirebase(savedMatches);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveMatchesPreferences(List<Match> matches) {
        SharedPreferences settings = getSharedPreferences("matches", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        for (int i = 0; i < matches.size(); i++) {
            Match curr = matches.get(i);

            editor.putString("competitionName" + i, curr.getCompetitionName());
            editor.putString("eventDate" + i, curr.getEventDate());
            editor.putBoolean("finished" + i, curr.isFinished());
            editor.putString("homeTeamName" + i, curr.getHomeTeamName());
            editor.putString("awayTeamName" + i, curr.getAwayTeamName());
            editor.putInt("homeTeamGoals" + i, curr.getHomeTeamGoals());
            editor.putInt("awayTeamGoals" + i, curr.getAwayTeamGoals());
        }
        editor.putInt("noMatches", matches.size());

        editor.apply();

        Toast.makeText(this, "Matches successfully saved", Toast.LENGTH_LONG).show();
    }

    private void readMatchesPreferences(String prefName) {
        SharedPreferences settings = getSharedPreferences("matches", MODE_PRIVATE);
        int noMatches = settings.getInt("noMatches", 0);

        List<Match> temp = new ArrayList<>();

        for (int i = 0; i < noMatches; i++) {
            Match curr = new Match(
                    settings.getString("competitionName" + i, "blank"),
                    settings.getString("eventDate" + i, "blank"),
                    settings.getBoolean("finished" + i, false),
                    settings.getString("homeTeamName" + i, "blank"),
                    settings.getString("awayTeamName" + i, "blank"),
                    settings.getInt("homeTeamGoals" + i, 0),
                    settings.getInt("awayTeamGoals" + i, 0),
                    null,
                    null);

            temp.add(curr);
        }

        savedMatches = temp;
    }

    private void clearMatchesPreference(String prefName) {
        SharedPreferences settings = getSharedPreferences(prefName, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.apply();

        Toast.makeText(this, "Preferences successfully cleared", Toast.LENGTH_LONG).show();
    }

    private void writeToText(String fileName) throws IOException {
        FileOutputStream file = openFileOutput(fileName, MODE_PRIVATE);
        DataOutputStream out = new DataOutputStream(file);

        out.writeUTF("No of saved matches: " + savedMatches.size() + "\n\n");
        for (int i = 0; i < savedMatches.size(); i++) {
            Match curr = savedMatches.get(i);

            out.writeUTF("Competition name: " + curr.getCompetitionName() + "\n");
            out.writeUTF("Event date: " + curr.getEventDate() + "\n");
            out.writeUTF("Finished: " + curr.isFinished() + "\n");
            out.writeUTF("Home team name: " + curr.getHomeTeamName() + "\n");
            out.writeUTF("Away team name: " + curr.getAwayTeamName() + "\n");
            out.writeUTF("Home team goals: " + curr.getHomeTeamGoals() + "\n");
            out.writeUTF("Away team goals: " + curr.getAwayTeamGoals() + "\n\n");
        }
        out.flush();

        file.close();

        Toast.makeText(this, "Matches successfully saved to file", Toast.LENGTH_LONG).show();
    }

    private void readFromText(String fileName) throws IOException {
        FileInputStream file = openFileInput(fileName);
        DataInputStream in = new DataInputStream(file);

        StringBuilder res = new StringBuilder("");
        String line = in.readUTF();

        try {
            while (true) {
                res.append(line);
                line = in.readUTF();
            }
        } catch (EOFException e) {

        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Matches saved")
                .setMessage(res.toString())
                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void saveMatchesDatabase() {
        for (int i = 0; i < savedMatches.size(); i++)
            database.dbDao().insertMatch(savedMatches.get(i));

        Toast.makeText(this, "Matches successfully saved to database", Toast.LENGTH_LONG).show();
    }

    private void showMatchesDb() {
        List<Match> temp = database.dbDao().getMatches();
        String report = createReport(temp);

        FragmentManager fm = getSupportFragmentManager();
        MyDialogFragment dialog = MyDialogFragment.newInstance(report);
        dialog.show(fm, "fragment_dialog_db");
    }

    private void showMatchesByLeagueDb() {
        String report = createReportByLeague();

        FragmentManager fm = getSupportFragmentManager();
        MyDialogFragment dialog = MyDialogFragment.newInstance(report);
        dialog.show(fm, "fragment_dialog_league");
    }

    private String createReportByLeague() {
        StringBuilder res = new StringBuilder("");
        List<League> leagues = database.dbDao().getLeagues();
        for (League l : leagues) {
            res.append(l.leagueName + "\n\n" + "Matches: \n");
            for (Match m : l.matches) {
                res.append(m.toStringR() + "\n\n");
            }
            res.append("\n");
        }

        return res.toString();
    }

    private String createReport(List<Match> temp) {
        StringBuilder res = new StringBuilder("");
        double avgGoals = 0;
        int totalGoals = 0;
        int homeTeamGoals = 0;
        int awayTeamGoals = 0;
        int noMatches = temp.size();
        int timesTie = 0;
        int timesHomeWin = 0;
        int timesAwayWin = 0;
        int noFinished = 0;

        for (Match m : temp) {
            if (m.isFinished()) {
                homeTeamGoals += m.getHomeTeamGoals();
                awayTeamGoals += m.getAwayTeamGoals();
                noFinished++;

                if (m.getHomeTeamGoals() > m.getAwayTeamGoals()) {
                    timesHomeWin++;
                } else if (m.getHomeTeamGoals() == m.getAwayTeamGoals()) {
                    timesTie++;
                } else {
                    timesAwayWin++;
                }
            }
        }

        totalGoals = homeTeamGoals + awayTeamGoals;

        res.append("No matches: " + noMatches + "\n\n");
        if (noMatches != 0) {
            if (noFinished != 0) {
                avgGoals = totalGoals / noMatches;
                res.append("Total Goals: " + totalGoals + "\nAverage no of goals per game: " + avgGoals + "\nNo of goals for a home team: "
                        + homeTeamGoals + "\nNo of goals for an away team: " + awayTeamGoals + "\nNo of times the home team won: " +
                        timesHomeWin + "\nNo of times the away team won: " + timesAwayWin + "\nNo of times tied: " + timesTie + "\n\n");
            }

            res.append("Matches saved: \n\n");
            for (Match m : temp) {
                res.append(m.toStringR() + "\n\n");
            }
        }

        return res.toString();
    }
}
