/*

Name: Francisco Ozuna Diaz
Assignment: CS 7455 Lab 8
Lab Date: Due July 19, 2020 at 11:59 PM

 */

package com.example.lab8a;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText studentName, studentGrade;

    private StudentListDBAdapter studentListDBAdapter;
    private String studentRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentName = (EditText) findViewById(R.id.editTextName);
        studentGrade = (EditText) findViewById(R.id.editTextGrade);

        studentListDBAdapter = StudentListDBAdapter.getStudentListDBAdapterInstance(this);
        studentRecords = studentListDBAdapter.getAllStudents();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAddName:
                Toast.makeText(getApplicationContext(), "Add Name Button Pressed", Toast.LENGTH_SHORT).show();
                addStudentName(studentName.getText().toString(), studentGrade.getText().toString());
                break;
            case R.id.buttonRetrieveStudents:
                Toast.makeText(getApplicationContext(), "Retrieve Student Button Pressed", Toast.LENGTH_SHORT).show();
                retrieveStudents();
                break;
        }
    }

    public void addStudentName(String name, String grade) {
        boolean isInserted = studentListDBAdapter.insert(name, grade);
        if (isInserted) {
            Toast.makeText(MainActivity.this, "Data inserted. ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Data not inserted. ", Toast.LENGTH_SHORT).show();
        }
    }

    public void retrieveStudents() {
        studentRecords = studentListDBAdapter.getAllStudents();
        if (studentRecords.length() == 0) {
            showMessage("Error", "No student records in table...");
            return;
        }
        showMessage("Student records", studentRecords);
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}