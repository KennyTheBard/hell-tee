package com.impaler.helltee.activity;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.impaler.helltee.R;
import com.impaler.helltee.model.Exercise;
import com.impaler.helltee.model.ExerciseType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateNewExerciseActivity extends AppCompatActivity {

    private Exercise exercise = new Exercise();

    private List<ExerciseType> exerciseTypes;

    private DatePicker datePicker;
    private EditText value;
    private Spinner exerciseTypeSpinner;

    private ArrayAdapter<ExerciseType> exerciseTypeAdapter;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference exerciseTypesRef = database.getReference("ExerciseType");
    final DatabaseReference exercisesRef = database.getReference("Exercise");

    private Toast resultMessage;

    private boolean saving = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_exercise);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        exerciseTypeAdapter = new ArrayAdapter<ExerciseType>(CreateNewExerciseActivity.this, R.layout.dropdown_spinner);

        datePicker = findViewById(R.id.date);

        value = findViewById(R.id.value);
        value.setFilters(new InputFilter[]{new InputFilter() {
            final int maxDigitsBeforeDecimalPoint = 4;
            final int maxDigitsAfterDecimalPoint = 0;

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                StringBuilder builder = new StringBuilder(dest);
                builder.replace(dstart, dend, source
                        .subSequence(start, end).toString());
                if (!builder.toString().matches(
                        "(([1-9]{1})([0-9]{0," + (maxDigitsBeforeDecimalPoint - 1) + "})?)?(\\.[0-9]{0," + maxDigitsAfterDecimalPoint + "})?"

                )) {
                    if (source.length() == 0)
                        return dest.subSequence(dstart, dend);
                    return "";
                }

                return null;
            }
        }});
        value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith("0")) {
                    s.delete(0, 1);
                }
            }
        });

        TextView measureUnitText = findViewById(R.id.measure_unit);

        exerciseTypes = new ArrayList<>();
        exerciseTypesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                exerciseTypes.clear();
                exerciseTypeAdapter.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String key = child.getKey();
                    if (key != null) {
                        ExerciseType type = new ExerciseType(key, (String) child.getValue());

                        measureUnitText.setText(type.getMeasureUnit());

                        exerciseTypes.add(type);
                        exerciseTypeAdapter.add(type);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        exerciseTypeSpinner = findViewById(R.id.exercise_type);
        exerciseTypeSpinner.setPrompt("Exercise Type");
        exerciseTypeSpinner.setAdapter(exerciseTypeAdapter);
        exerciseTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                measureUnitText.setText(exerciseTypeAdapter.getItem(position).getMeasureUnit());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                measureUnitText.setText(null);
            }
        });

        this.resultMessage = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveAction(View view) {
        if (saving)
            return;

        saving = true;
        this.exercise.setDate(String.format("%02d", this.datePicker.getDayOfMonth()) + "-" + String.format("%02d", this.datePicker.getMonth() + 1) + "-" + String.format("%04d", this.datePicker.getYear()));
        try {
            this.exercise.setValue(Integer.valueOf(this.value.getText().toString()));
        } catch (NumberFormatException ex) {
            this.exercise.setValue(0);
        }
        this.exercise.setExerciseType((ExerciseType) exerciseTypeSpinner.getSelectedItem());

        if (exercise.getValue() != null) {
            exercisesRef.push().setValue(exercise).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    showMessage("Successfully saved!");
                    setResult(RESULT_OK);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    showMessage("Failed to save");
                    setResult(RESULT_CANCELED);
                    finish();
                }
            });
        } else {
            if (exercise.getValue() == null) {
                showMessage("Make sure to introduce a sum");
            } else {
                showMessage("Make sure to set an user");
            }
        }
    }

    public void cancelAction(View view) {
        finish();
    }

    public void showMessage(String message) {
        resultMessage.setText(message);
        resultMessage.show();
    }
}
