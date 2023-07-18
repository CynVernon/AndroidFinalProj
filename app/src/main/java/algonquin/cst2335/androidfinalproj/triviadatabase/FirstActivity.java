package algonquin.cst2335.androidfinalproj.triviadatabase;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import algonquin.cst2335.androidfinalproj.R;

public class FirstActivity extends AppCompatActivity {

    private TextView textViewTitle;
    private EditText editTextName;
    private EditText editTextNumQuestions;
    private Button buttonSubmit;

    private SharedPreferences sharedPreferences;

    private static final String PREF_NAME = "TriviaPreferences";
    private static final String KEY_NAME = "name";
    private static final String KEY_NUM_QUESTIONS = "numQuestions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        textViewTitle = findViewById(R.id.textViewTitle);
        editTextName = findViewById(R.id.editTextName);
        editTextNumQuestions = findViewById(R.id.editTextNumQuestions);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        // Load shared preferences
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String savedName = sharedPreferences.getString(KEY_NAME, "");
        editTextName.setText(savedName);

        Toast.makeText(this, "Welcome to Trivia!", Toast.LENGTH_SHORT).show();

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String numQuestions = editTextNumQuestions.getText().toString();

                // Save name to shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_NAME, name);
                editor.putString(KEY_NUM_QUESTIONS, numQuestions);
                editor.apply();

                Snackbar.make(v, "Ready for quiz?", Snackbar.LENGTH_LONG)
                        .setAction("Yes", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showAlertDialog();
                            }
                        }).show();
            }
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("How to do the quiz")
                .setMessage("Instructions for the quiz go here.")
                .setPositiveButton("I understand", null)
                .show();
    }
}
