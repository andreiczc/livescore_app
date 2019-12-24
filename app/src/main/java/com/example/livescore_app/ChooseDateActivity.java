package com.example.livescore_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ChooseDateActivity extends AppCompatActivity {
    DatePicker datePicker;
    TextView tvFrom;
    TextView tvTo;
    Button btnFrom;
    Button btnTo;
    Button btnResults;

    String fromDate;
    String toDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_date);

        datePicker = findViewById(R.id.datePicker);
        tvFrom = findViewById(R.id.tvFrom);
        tvTo = findViewById(R.id.tvTo);
        btnFrom = findViewById(R.id.btnSetFrom);
        btnTo = findViewById(R.id.btnSetTo);
        btnResults = findViewById(R.id.btnResults);
    }

    public void showResults(View view) {
        if (tvFrom.getText().length() == 0 || tvTo.getText().length() == 0) {
            Snackbar.make(findViewById(R.id.constraintLay), "First select a from and a to date", Snackbar.LENGTH_LONG).show();
        } else if (fromDate.compareToIgnoreCase(toDate) > 0) {
            Snackbar.make(findViewById(R.id.constraintLay), "To date should be after from date", Snackbar.LENGTH_LONG).show();
        } else {
            @SuppressLint("StaticFieldLeak") Network net = new Network() {
                @Override
                protected void onPostExecute(JSONObject jsonObject) {
                    super.onPostExecute(jsonObject);

                    Intent intent = new Intent(ChooseDateActivity.this, ShowMatchesByDateActivity.class);
                    intent.putExtra("json", jsonObject.toString());

                    startActivity(intent);
                }
            };
            try {
                net.execute(new URL("https://api.football-data.org/v2/matches/?dateFrom=" + fromDate + "&dateTo=" + toDate));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

//            Intent intent = new Intent(this, ShowMatchesByDateActivity.class);
//            intent.putExtra("net", (Serializable) net);
//            try {
//                startActivity(intent);
//            } catch (Exception e) {
//                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
//            }
        }
    }

    public void setDateFrom(View view) {
        int month = datePicker.getMonth() + 1;
        fromDate = datePicker.getYear() + "-" + month + "-" + datePicker.getDayOfMonth();
        tvFrom.setText("From date: " + datePicker.getDayOfMonth() + " " + getMonthFullName(datePicker.getMonth()));
    }

    public void setDateTo(View view) {
        int month = datePicker.getMonth() + 1;
        toDate = datePicker.getYear() + "-" + month + "-" + datePicker.getDayOfMonth();
        tvTo.setText("To date: " + datePicker.getDayOfMonth() + " " + getMonthFullName(datePicker.getMonth()));
    }

    private String getMonthFullName(int monthNumber) {
        String monthName = "";

        if (monthNumber >= 0 && monthNumber < 12)
            try {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.MONTH, monthNumber);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM");
                simpleDateFormat.setCalendar(calendar);
                monthName = simpleDateFormat.format(calendar.getTime());
            } catch (Exception e) {
                if (e != null)
                    e.printStackTrace();
            }

        return monthName;
    }
}
