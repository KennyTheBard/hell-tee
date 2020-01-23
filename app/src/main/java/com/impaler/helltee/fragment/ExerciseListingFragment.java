package com.impaler.helltee.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.impaler.helltee.R;
import com.impaler.helltee.activity.CreateNewExerciseActivity;
import com.impaler.helltee.activity.QueryFormActivity;
import com.impaler.helltee.model.Exercise;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ExerciseListingFragment extends UpdatableFragment {

    private static final int CREATE_NEW_EXERCISE = 0;
    private static final int GET_QUERY_DATA = 1;

    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> exerciseTypes;

    private DateTimeFormatter formatter;

    private ListView listView;

    private ArrayAdapter<Exercise> exercisesAdapter;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference exerciseRef = database.getReference("Exercise");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        formatter = DateTimeFormatter.ofPattern(getString(R.string.date_format));
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_exercise_listing, container, false);

        listView = root.findViewById(R.id.listView);
        exercisesAdapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_list_item_1, new ArrayList<Exercise>(){
            @Override
            public boolean add(Exercise o) {
                boolean ret = super.add(o);
                if (ret) {
                    for (int i = size() - 1; i > 0 && o.compareTo(get(i - 1)) < 0; i--)
                        Collections.swap(this, i, i - 1);
                }
                return ret;
            }
        });

        listView.setAdapter(exercisesAdapter);

        FloatingActionButton addButton = root.findViewById(R.id.add_fab);
        addButton.setOnClickListener(view -> {
            Intent intent = new Intent(ExerciseListingFragment.this.getActivity(), CreateNewExerciseActivity.class);
            ExerciseListingFragment.this.startActivityForResult(intent, CREATE_NEW_EXERCISE);
        });

        FloatingActionButton statsButton = root.findViewById(R.id.stats_fab);
        statsButton.setOnClickListener(view -> {
            Intent intent = new Intent(ExerciseListingFragment.this.getActivity(), QueryFormActivity.class);
            ExerciseListingFragment.this.startActivityForResult(intent, GET_QUERY_DATA);
        });

        loadData();

        return root;
    }

    private void loadData() {
        exerciseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                exercisesAdapter.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Exercise exercise = snapshot.getValue(Exercise.class);

                    if (exercise != null) {
                        exercise.setId(snapshot.getKey());

                        if (startDate != null && endDate != null) {
                            LocalDate recordDate = LocalDate.parse(exercise.getDate(), formatter);
                            if (recordDate.isBefore(startDate) || recordDate.isAfter(endDate)) {
                                continue;
                            }
                        }

                        if (exerciseTypes != null && !exerciseTypes.isEmpty()) {
                            String type = exercise.getExerciseType().getName();
                            if (!exerciseTypes.contains(type)) {
                                continue;
                            }
                        }

                        exercisesAdapter.add(exercise);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void update(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;

        loadData();
    }
}
