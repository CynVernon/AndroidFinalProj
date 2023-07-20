package algonquin.cst2335.androidfinalproj.triviadatabase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import algonquin.cst2335.androidfinalproj.R;
import algonquin.cst2335.androidfinalproj.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends AppCompatActivity {

   private ActivityWelcomeBinding binding;

   private WelcomeModel model;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(WelcomeModel.class);

        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Retrieve the saved username
        String savedUsername = sharedPreferences.getString("username", "");
        binding.usernameEditText.setText(savedUsername);

        binding.startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.usernameEditText.getText().toString();
                int numQuestions = Integer.parseInt(binding.numQuestionsEditText.getText().toString());

                // Save the username using SharedPreferences
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
