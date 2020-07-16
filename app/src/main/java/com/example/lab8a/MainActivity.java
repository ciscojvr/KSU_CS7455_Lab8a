package com.example.lab8a;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText studentName, studentGrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentName = (EditText) findViewById(R.id.editTextName);
        studentGrade = (EditText) findViewById(R.id.editTextGrade);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAddName:
                Toast.makeText(getApplicationContext(), "Add Name Button Pressed", Toast.LENGTH_SHORT).show();
                addStudentName();
                break;
            case R.id.buttonRetrieveStudents:
                Toast.makeText(getApplicationContext(), "Retrieve Student Button Pressed", Toast.LENGTH_SHORT).show();
                retrieveStudents();
                break;
        }
    }

    public void addStudentName() {
        // Todo: Complete method for adding student name as new record in table.
    }

    public void retrieveStudents() {
        // Todo: Complete method for retrieving students stored as records in the table.
    }
}