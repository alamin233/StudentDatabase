package com.example.worddemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewWordActivity extends AppCompatActivity {

    Button saveBtn;
    EditText editWordEt;
    private WordViewModel mWordViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_word_activity);

        saveBtn = findViewById(R.id.button_save);
        editWordEt = findViewById(R.id.edit_word);
        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editWordEt.getText().toString().equals(""))
                {
                    Toast.makeText(NewWordActivity.this,"Enter Word", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Word word = new Word(editWordEt.getText().toString());
                    mWordViewModel.insert(word);
                    Toast.makeText(NewWordActivity.this, "Save Successfully",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(NewWordActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });



    }
}
