package algonquin.cst2335.androidfinalproj.triviadatabase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import algonquin.cst2335.androidfinalproj.R;

public class WelcomeActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText numQuestionsEditText;
    private Button startQuizButton;
    private SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        usernameEditText = findViewById(R.id.usernameEditText);
        numQuestionsEditText = findViewById(R.id.numQuestionsEditText);
        startQuizButton = findViewById(R.id.startQuizButton);
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                int numQuestions = Integer.parseInt(numQuestionsEditText.getText().toString());

                // Save username using SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.apply();

                // Start QuizActivity
                Intent intent = new Intent(WelcomeActivity.this, QuizActivity.class);
                intent.putExtra("numQuestions", numQuestions);
                startActivity(intent);
            }
        });
    }
}
