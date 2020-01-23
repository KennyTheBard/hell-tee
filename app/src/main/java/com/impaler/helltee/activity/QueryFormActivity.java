package com.impaler.helltee.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.impaler.helltee.MainActivity;
import com.impaler.helltee.R;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;

import java.util.stream.Collectors;

public class QueryFormActivity extends AppCompatActivity {

    private DatePicker startDate;
    private DatePicker endDate;

    private Toast processingToast;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        setContentView(R.layout.activity_query_form);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.startDate = findViewById(R.id.start_date);
        LocalDate suggestedStartDate = LocalDate.now().minusDays(7);
        this.startDate.updateDate(suggestedStartDate.getYear(), suggestedStartDate.getMonthValue() - 1, suggestedStartDate.getDayOfMonth());
        this.endDate = findViewById(R.id.end_date);

        FloatingActionButton executeQueryButton = findViewById(R.id.query_accept);
        executeQueryButton.setOnClickListener(this::executeQuery);

        FloatingActionButton clearQueryButton = findViewById(R.id.query_remove);
        clearQueryButton.setOnClickListener(this::clearQuery);

        processingToast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG);
    }

    public void executeQuery(View view) {
        Intent intent = new Intent(QueryFormActivity.this, MainActivity.class);
        intent.putExtra("StartDate", String.format("%s-%s-%s",
                String.format("%02d", this.startDate.getDayOfMonth()),
                String.format("%02d", this.startDate.getMonth() + 1),
                String.format("%04d", this.startDate.getYear())));
        intent.putExtra("EndDate", String.format("%s-%s-%s",
                String.format("%02d", this.endDate.getDayOfMonth()),
                String.format("%02d", this.endDate.getMonth() + 1),
                String.format("%04d", this.endDate.getYear())));

        setResult(RESULT_OK, intent);
        finish();
//
//        processingToast.setText("Processing...");
//        processingToast.show();
    }

    public void clearQuery(View view) {
        setResult(RESULT_CANCELED);
        finish();
//
//        processingToast.setText("Processing...");
//        processingToast.show();
    }

}
