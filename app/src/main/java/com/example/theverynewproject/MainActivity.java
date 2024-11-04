package com.example.theverynewproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.theverynewproject.Booking;
import com.example.theverynewproject.LoginActivity;
import com.example.theverynewproject.R;
import com.example.theverynewproject.SignUpActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText customerNameEditText;
    private EditText passwordEditText;
    private DatePicker datePicker;
    private Button bookButton;
    private Button signUpButton;
    private Button loginButton;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("bookings");

        customerNameEditText = findViewById(R.id.customerNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        datePicker = findViewById(R.id.datePicker);
        bookButton = findViewById(R.id.bookButton);
        signUpButton = findViewById(R.id.signUpButton);
        loginButton = findViewById(R.id.loginButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to SignUpActivity
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to LoginActivity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get customer name and selected date
                String customerName = customerNameEditText.getText().toString();
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1; // Months are 0-based
                int year = datePicker.getYear();

                // Check if the user is authenticated
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    // Create a booking object
                    String bookingDate = day + "/" + month + "/" + year;
                    Booking booking = new Booking(customerName, bookingDate);

                    // Push the booking to Firebase Database
                    databaseReference.push().setValue(booking);

                    // Clear input fields
                    customerNameEditText.getText().clear();
                    passwordEditText.getText().clear();

                    // Inform the user about the successful booking
                    Toast.makeText(MainActivity.this, "Booking Successful", Toast.LENGTH_SHORT).show();
                } else {
                    // Inform the user to log in first
                    Toast.makeText(MainActivity.this, "Please log in first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Handle user authentication (login) when the activity starts
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // If the user is not logged in, prompt them to log in
            // For simplicity, you may want to redirect the user to a login activity
            Toast.makeText(this, "Please log in first", Toast.LENGTH_SHORT).show();
        }
    }
}
