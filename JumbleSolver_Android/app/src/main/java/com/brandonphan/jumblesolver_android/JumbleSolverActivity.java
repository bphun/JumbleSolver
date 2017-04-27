package com.brandonphan.jumblesolver_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class JumbleSolverActivity extends AppCompatActivity {

    private TextView unscrambledWordTextView;
    private EditText scrambledWordEditText;
    private JumbleSolver jumbleSolver;
    private HashSet<String> possible;
    private Iterator possibleIterator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jumble_solver);

        jumbleSolver = new JumbleSolver(openTextFile());
        System.out.println("Opened file");
        possible = new HashSet<>();

        unscrambledWordTextView = (TextView) findViewById(R.id.unscrambledTextView);
        scrambledWordEditText = (EditText) findViewById(R.id.scrambledWordEditText);

        scrambledWordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                possible = jumbleSolver.unscramble(scrambledWordEditText.getText().toString());
                if (possible == null) {
                    unscrambledWordTextView.setText("");
                    return;
                }
                possibleIterator = possible.iterator();
                while (possibleIterator.hasNext()) {
                    String next = (String) possibleIterator.next();
                    System.out.println(next);
                    unscrambledWordTextView.setText("Possible: ");
                    unscrambledWordTextView.setText(next + "\n");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public List<String> openTextFile() {
        List<String> lines = new ArrayList<>();
        InputStream is = getResources().openRawResource(R.raw.words);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
