package algonquin.cst2335.androidfinalproj.triviadatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import algonquin.cst2335.androidfinalproj.R;

public class QuizActivity extends AppCompatActivity {

    private TextView questionTextView;
    private Button nextButton;
    private int currentQuestionIndex = 0;
    private int numQuestions;

    private String[] questions = {
            "Question 1",
            "Question 2"
            // must be adapted to retrieve from database
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionTextView = findViewById(R.id.questionTextView);
        nextButton = findViewById(R.id.nextButton);
        numQuestions = getIntent().getIntExtra("numQuestions", 0);

        updateQuestion();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestionIndex++;
                if (currentQuestionIndex < numQuestions) {
                    updateQuestion();
                } else {
                    finishQuiz();
                }
            }
        });
    }

    private void updateQuestion() {
        questionTextView.setText(questions[currentQuestionIndex]);
    }

    private void finishQuiz() {
        // Calculate score and perform any necessary actions
        // ...

        // Start ScoreActivity
        Intent intent = new Intent(QuizActivity.this, ScoreActivity.class);
        startActivity(intent);
        finish();
    }
}
