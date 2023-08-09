package algonquin.cst2335.androidfinalproj.triviadatabase;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import algonquin.cst2335.androidfinalproj.R;

public class ScoreActivity extends AppCompatActivity {

    private TextView scoreTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        scoreTextView = findViewById(R.id.scoreTextView);
        int score = getIntent().getIntExtra("score", 0);
        scoreTextView.setText("Score: " + score);
        scoreTextView.setText("Score: 100"); // Placeholder for testing
    }
}
