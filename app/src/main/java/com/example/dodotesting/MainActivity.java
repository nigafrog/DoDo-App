package com.example.dodotesting;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ModelTaskAdapter mModelTaskAdapter;
    private List<ModelTask> mList_ModelTask;
    private Button mBtnHistory;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList_ModelTask = new ArrayList<>();

        mRecyclerView = findViewById(R.id.recyclerView);
        mBtnHistory = findViewById(R.id.button_History);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mModelTaskAdapter = new ModelTaskAdapter(mList_ModelTask, MainActivity.this);
        mRecyclerView.setAdapter(mModelTaskAdapter);

        db.collection("nTasks").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                mList_ModelTask.clear();

                for (int i=0; i<queryDocumentSnapshots.size(); i++){
                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(i);

                    if(documentSnapshot.exists()) {

                        String id = documentSnapshot.getId();
                        String task = documentSnapshot.getString("Title");
                        String date = documentSnapshot.getString("Date");
                        String description = documentSnapshot.getString("Description");

                        ModelTask mModelTask = new ModelTask (id, task, date, description);

                        mList_ModelTask.add(mModelTask);
                    }
                }
                mModelTaskAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        FloatingActionButton mFloatingActionButton = findViewById(R.id.addButton);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        mBtnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DoneActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.five_min){
            Intent intent = new Intent(MainActivity.this, Rest_timer_5min.class);
            startActivity(intent);

        }else if (id == R.id.fifteen_min){
            Intent intent = new Intent(MainActivity.this, Rest_timer_15min.class);
            startActivity(intent);

        }else if (id == R.id.thirty_min) {
            Intent intent = new Intent(MainActivity.this, Rest_timer_30min.class);
            startActivity(intent);
        }else if (id == R.id.forty_min) {
            Intent intent = new Intent(MainActivity.this, Rest_timer_40min.class);
            startActivity(intent);
        }
        return true;
    }
}
