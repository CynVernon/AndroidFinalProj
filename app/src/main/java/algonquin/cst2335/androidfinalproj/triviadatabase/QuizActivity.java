package algonquin.cst2335.androidfinalproj.triviadatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import algonquin.cst2335.androidfinalproj.R;
import algonquin.cst2335.androidfinalproj.databinding.ActivityQuizBinding;

public class QuizActivity extends AppCompatActivity {

private QuizActivityModel model;

private ActivityQuizBinding binding;
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
        model = new ViewModelProvider(this).get(QuizActivityModel.class);

        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        numQuestions = getIntent().getIntExtra("numQuestions", 0);

        updateQuestion();

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
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
        binding.questionTextView.setText(questions[currentQuestionIndex]);
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
