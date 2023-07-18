package algonquin.cst2335.androidfinalproj.triviadatabase;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import algonquin.cst2335.androidfinalproj.R;

public class SecondActivity extends AppCompatActivity {

    private TextView textViewQuestion;
    private RadioGroup radioGroupOptions;
    private Button buttonNext;

    private String[] questions;
    private JSONArray options;
    private int currentQuestionIndex = 0;

    private static final String API_URL = "https://opentdb.com/api.php?amount=10&category=22&type=multiple";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textViewQuestion = findViewById(R.id.textViewQuestion);
        radioGroupOptions = findViewById(R.id.radioGroupOptions);
        buttonNext = findViewById(R.id.buttonNext);

        // Fetch questions and options from the API
        new FetchQuestionsTask().execute(API_URL);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedOptionId = radioGroupOptions.getCheckedRadioButtonId();
                if (selectedOptionId == -1) {
                    Toast.makeText(SecondActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                } else {
                    RadioButton selectedOption = findViewById(selectedOptionId);
                    // Do something with the selected option (store the answer, calculate score, etc.)

                    // Increment the question index
                    currentQuestionIndex++;

                    if (currentQuestionIndex < questions.length) {
                        // Display the next question and options
                        displayQuestion(currentQuestionIndex);
                    } else {
                        // Reached the last question
                        // Do something (e.g., show score, navigate to the next activity)
                    }
                }
            }
        });
    }

    private void displayQuestion(int questionIndex) {
        try {
            String question = questions[questionIndex];
            textViewQuestion.setText(question);

            JSONObject questionObject = options.getJSONObject(questionIndex);
            JSONArray optionsArray = questionObject.getJSONArray("incorrect_answers");
            optionsArray.put(questionObject.getString("correct_answer"));

            radioGroupOptions.removeAllViews();

            for (int i = 0; i < optionsArray.length(); i++) {
                String option = optionsArray.getString(i);

                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(option);
                radioButton.setId(View.generateViewId());

                radioGroupOptions.addView(radioButton);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class FetchQuestionsTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String apiUrl = urls[0];
            String result = "";

            try {
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Parse the JSON response and extract the questions and options
            try {
                JSONObject response = new JSONObject(result);
                JSONArray questionArray = response.getJSONArray("results");

                questions = new String[questionArray.length()];
                options = new JSONArray();

                for (int i = 0; i < questionArray.length(); i++) {
                    JSONObject questionObject = questionArray.getJSONObject(i);
                    String question = questionObject.getString("question");
                    questions[i] = question;
                    options.put(questionObject);
                }

                // Display the first question
                displayQuestion(currentQuestionIndex);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
