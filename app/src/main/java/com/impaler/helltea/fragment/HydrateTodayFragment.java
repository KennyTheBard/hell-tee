package com.impaler.helltea.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.impaler.helltea.R;
import com.impaler.helltea.model.Exercise;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;

public class HydrateTodayFragment extends UpdatableFragment {

    private ListView listView;

//    private ArrayAdapter<Exercise> exercisesAdapter;
//
//    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
//    private final DatabaseReference exerciseRef = database.getReference("Exercise");
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        formatter = DateTimeFormatter.ofPattern(getString(R.string.date_format));
//    }
//
//    @Override
//    public View onCreateView(
//            @NonNull LayoutInflater inflater,
//            ViewGroup container,
//            Bundle savedInstanceState) {
//
//        View root = inflater.inflate(R.layout.fragment_exercise_listing, container, false);
//
//        listView = root.findViewById(R.id.listView);
//        exercisesAdapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_list_item_1, new ArrayList<Exercise>(){
//            @Override
//            public boolean add(Exercise o) {
//                boolean ret = super.add(o);
//                if (ret) {
//                    for (int i = size() - 1; i > 0 && o.compareTo(get(i - 1)) < 0; i--)
//                        Collections.swap(this, i, i - 1);
//                }
//                return ret;
//            }
//        });
//
//        listView.setAdapter(exercisesAdapter);
//
//        loadData();
//
//        return root;
//    }
//
//    private void loadData() {
//        exerciseRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                exercisesAdapter.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Exercise record = snapshot.getValue(Exercise.class);
//
//                    if (record != null) {
//                        record.setId(snapshot.getKey());
//
//                        if (startDate != null && endDate != null) {
//                            LocalDate recordDate = LocalDate.parse(record.getDate(), formatter);
//                            if (recordDate.isBefore(startDate) || recordDate.isAfter(endDate)) {
//                                continue;
//                            }
//                        }
//
//                        if (exerciseTypes != null && !exerciseTypes.isEmpty()) {
//                            String type = record.getExerciseType().getName();
//                            if (!exerciseTypes.contains(type)) {
//                                continue;
//                            }
//                        }
//
//                        exercisesAdapter.add(record);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//    }

    @Override
    public void update(LocalDate startDate, LocalDate endDate) {

    }
}
