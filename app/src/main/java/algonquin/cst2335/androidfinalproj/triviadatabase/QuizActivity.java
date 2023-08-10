package algonquin.cst2335.androidfinalproj.triviadatabase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import algonquin.cst2335.androidfinalproj.R;
import algonquin.cst2335.androidfinalproj.databinding.ActivityQuizBinding;

public class QuizActivity extends AppCompatActivity {
protected RequestQueue  queue = null;

private QuizActivityModel model;

private ActivityQuizBinding binding;

    ArrayList<String> questionsList = new ArrayList<>();
    ArrayList<String>answerList = new ArrayList<>();
    ArrayList<String>userAnswerList = new ArrayList<>();
    int place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(QuizActivityModel.class);

        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        Intent fromPrevious  = getIntent();

        int num = fromPrevious.getIntExtra("numQuestions", 0);

        //API request
        //https://opentdb.com/api.php?amount=10&category=22&type=multiple

        queue = Volley.newRequestQueue(this);

        //String url = "";
        String url = "https://opentdb.com/api.php?amount=" +  num +  "&category=22&type=multiple";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                (response) -> {
                    Toast.makeText(this, "good API", Toast.LENGTH_SHORT).show();
                    try {

                        JSONArray resultsArray = response.getJSONArray("results");

                        for (int i = 0 ; i  < resultsArray.length() ; i++){
                            //JSONObject qObj = questions.getJSONObject(i);
                            JSONObject qObj = resultsArray.getJSONObject(i);
                            String qText = qObj.getString("question");
                            String answerText = qObj.getString("correct_answer");

                            answerList.add(answerText);
                            questionsList.add(qText);
                            Log.d("API", "Added question: " + qText);

                        }

                        //binding.questionTextView.setText(questionsList.get(0));

                    }catch(JSONException e){
                        e.printStackTrace();
                    }

                } ,//end of response
        (error) -> {
            Toast.makeText(this, "bad API", Toast.LENGTH_SHORT).show();

        });
        queue.add(request);

            //binding.questionTextView.setText(questionsList.get(0));
    binding.nextButton.setOnClickListener(clk -> {
        String answer = null;
        answer = binding.userAnswer.getText().toString();

        if (answer.length() == 0 ){
            Toast.makeText(this, "enter answer", Toast.LENGTH_SHORT).show();
        }
        else {
            userAnswerList.add(answer);
        }

        if (place  ==  num ){
            finishQuiz();
        }
        else {
            place++;
            updateQuestion();
        }

    });

    } //end of onCreate()

    private void updateQuestion() {

    //    binding.questionTextView.setText(questionsList[place]);
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
