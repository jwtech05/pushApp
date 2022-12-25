package com.example.push1_1;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class AdjustPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_page);

        Intent intent = getIntent();

        TextView textViewToDo = (TextView) findViewById(R.id.whatToDoEdit);
        String toDo = intent.getStringExtra("todo");
        textViewToDo.setText(toDo);

        TextView textViewDate = (TextView) findViewById(R.id.dateEdit);
        String date = intent.getStringExtra("date");
        textViewDate.setText(date);

        TextView textViewLocation = (TextView) findViewById(R.id.locationEdit);
        String location = intent.getStringExtra("location");
        textViewLocation.setText(location);

        Button postButton = (Button) findViewById(R.id.regibutton);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NavActivity.class);

                EditText editTextToDo = (EditText) findViewById(R.id.whatToDoEdit);
                intent.putExtra("todo", editTextToDo.getText().toString());

                EditText editTextDate = (EditText) findViewById(R.id.dateEdit);
                intent.putExtra("date", editTextDate.getText().toString());

                EditText editTextLocation = (EditText) findViewById(R.id.locationEdit);
                intent.putExtra("location", editTextLocation.getText().toString());

                startActivity(intent);
            }
        });

    }
}