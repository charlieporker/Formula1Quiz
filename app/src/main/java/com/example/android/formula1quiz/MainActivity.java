package com.example.android.formula1quiz;

import android.content.Context;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int currentQuestion = 1;
    int numberOfQuestions = 3;
    int totalScore = 0;
    String questionNumber;
    String questionText;
    String answerStringName;
    String selectedAnswer;
    String correctAnswer;
    String questionTypeStringName;
    String questionType;
    String results;

    /**
     * Variables for identifying views; used to update based on question content
     */

    ImageView questionImageView;
    TextView questionPrefixView;
    TextView questionNumberView;
    TextView questionTextView;
    EditText freeTextAnswer;
    RelativeLayout checkboxAnswer;
    CheckBox checkBoxOptionA;
    CheckBox checkBoxOptionB;
    CheckBox checkBoxOptionC;
    CheckBox checkBoxOptionD;
    LinearLayout radioButtonAnswer;
    RadioButton radioButtonOptionA;
    RadioButton radioButtonOptionB;
    RadioButton radioButtonOptionC;
    TextView scoreTextView;
    Button nextButton;
    Button backButton;
    Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findAllViews();
        updateScreen();
    }

    /**
     * Assign Views to Variables
     */

    public void findAllViews (){
        questionImageView = (ImageView) findViewById(R.id.question_picture);
        questionPrefixView = (TextView) findViewById(R.id.question_prefix);
        questionNumberView = (TextView) findViewById(R.id.question_number);
        questionTextView = (TextView) findViewById(R.id.question_text);
        freeTextAnswer = (EditText) findViewById(R.id.free_text_answer_view);
        checkboxAnswer = (RelativeLayout) findViewById(R.id.checkbox_answer_view);
        checkBoxOptionA = (CheckBox) findViewById(R.id.checkbox_option_a);
        checkBoxOptionB = (CheckBox) findViewById(R.id.checkbox_option_b);
        checkBoxOptionC = (CheckBox) findViewById(R.id.checkbox_option_c);
        checkBoxOptionD = (CheckBox) findViewById(R.id.checkbox_option_d);
        radioButtonAnswer = (LinearLayout) findViewById(R.id.radio_answer_view);
        radioButtonOptionA = (RadioButton) findViewById(R.id.radio_button_a);
        radioButtonOptionB = (RadioButton) findViewById(R.id.radio_button_b);
        radioButtonOptionC = (RadioButton) findViewById(R.id.radio_button_c);
        scoreTextView = (TextView) findViewById(R.id.score_text);
        nextButton = (Button) findViewById(R.id.next_button);
        backButton = (Button) findViewById(R.id.back_button);
        exitButton = (Button) findViewById(R.id.exit_button);
    }

    /**
     * Method called from Activity_Main.xml when either navigation button is clicked. Reads question type and calls checkAnswer method. Moves onto next or previous question
     */
    public void navigationButtons(View view) {
        determineAnswer();
        checkAnswer(selectedAnswer, correctAnswer);
        switch (view.getId()) {
            case (R.id.back_button):
                if (currentQuestion != 1) {
                    currentQuestion = currentQuestion - 1;
                } else {
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;

                    CharSequence correct = "Correct Answer!";
                    CharSequence inCorrect = "Wrong Answer!";

                    Toast toastCorrect = Toast.makeText(context, correct, duration);
                    Toast toastInCorrect = Toast.makeText(context, inCorrect, duration);
                }
                break;
            case (R.id.next_button):
                if (currentQuestion != numberOfQuestions) {
                    currentQuestion = currentQuestion + 1;
                } else {
                    displayResults();
                    return;
                }
                break;
        }
        updateScreen();
    }

    /**
     * Method for determining the users select/entered answer
     */
    public void determineAnswer() {
        selectedAnswer = "";
        switch (questionType) {
            case ("freeText"):
                EditText FreeTextAnswer = (EditText) findViewById(R.id.free_text_answer_view);
                selectedAnswer = FreeTextAnswer.getText().toString();
                break;
            case ("multiChoice"):
                if (checkBoxOptionA.isChecked())
                    selectedAnswer = selectedAnswer + "A";
                if (checkBoxOptionB.isChecked())
                    selectedAnswer = selectedAnswer + "B";
                if (checkBoxOptionC.isChecked())
                    selectedAnswer = selectedAnswer + "C";
                if (checkBoxOptionD.isChecked())
                    selectedAnswer = selectedAnswer + "D";
                break;
            case ("multiChoiceRadio"):
                if (radioButtonOptionA.isChecked())
                    selectedAnswer = "A";
                if (radioButtonOptionB.isChecked())
                    selectedAnswer = "B";
                if (radioButtonOptionC.isChecked())
                    selectedAnswer = "C";
                break;

        }
    }

    /**
     * Method for retrieving the strings for the current question
     */

    public void currentQuestionStrings() {

        questionNumber = "question_" + currentQuestion;
        questionTypeStringName = questionNumber + "_type";
        answerStringName = questionNumber + "_answer";
        questionType = getString(getResources().getIdentifier(questionTypeStringName, "string", getPackageName()));
        questionText = getString(getResources().getIdentifier(questionNumber, "string", getPackageName()));
        correctAnswer = getString(getResources().getIdentifier(answerStringName, "string", getPackageName()));
    }


    public void updateScreen() {
        currentQuestionStrings();
        questionImageView.setImageResource(getResources().getIdentifier(questionNumber, "drawable", getPackageName()));
        questionTextView.setText(getResources().getIdentifier(questionNumber, "string", getPackageName()));

        switch (questionType) {
            case ("freeText"):
                radioButtonAnswer.setVisibility(View.GONE);
                checkboxAnswer.setVisibility(View.GONE);
                freeTextAnswer.setVisibility(View.VISIBLE);
                break;
            case ("multiChoice"):
                radioButtonAnswer.setVisibility(View.GONE);
                freeTextAnswer.setVisibility(View.GONE);
                displayMultiChoiceCheckBox(questionNumber);
                checkboxAnswer.setVisibility(View.VISIBLE);
                break;
            case ("multiChoiceRadio"):
                freeTextAnswer.setVisibility(View.GONE);
                checkboxAnswer.setVisibility(View.GONE);
                displayMultiChoiceRadio(questionNumber);
                radioButtonAnswer.setVisibility(View.VISIBLE);
                break;
        }
        questionNumberView.setText(" " + currentQuestion);
    }

    public void displayMultiChoiceRadio(String questionNumber) {

        String stringOptionA = questionNumber + "_option_a";
        String stringOptionB = questionNumber + "_option_b";
        String stringOptionC = questionNumber + "_option_c";

        radioButtonOptionA.setText(getResources().getIdentifier(stringOptionA, "string", getPackageName()));
        radioButtonOptionB.setText(getResources().getIdentifier(stringOptionB, "string", getPackageName()));
        radioButtonOptionC.setText(getResources().getIdentifier(stringOptionC, "string", getPackageName()));

    }

    public void displayMultiChoiceCheckBox(String questionNumber) {

        String stringOptionA = questionNumber + "_option_a";
        String stringOptionB = questionNumber + "_option_b";
        String stringOptionC = questionNumber + "_option_c";
        String stringOptionD = questionNumber + "_option_d";

        checkBoxOptionA.setText(getResources().getIdentifier(stringOptionA, "string", getPackageName()));
        checkBoxOptionB.setText(getResources().getIdentifier(stringOptionB, "string", getPackageName()));
        checkBoxOptionC.setText(getResources().getIdentifier(stringOptionC, "string", getPackageName()));
        checkBoxOptionD.setText(getResources().getIdentifier(stringOptionD, "string", getPackageName()));

    }

    /**
     * Method for checking the users answer against the correct answer, displaying a toast and updating the score
     *
     * @param correctAnswer  retrieved from the strings.xml in the currentQuestionStrings method
     * @param selectedAnswer determined by the determineAnswer method
     * @return total score
     */

    public int checkAnswer(String correctAnswer, String selectedAnswer) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        CharSequence correct = "Correct Answer!";
        CharSequence inCorrect = "Wrong Answer!";

        Toast toastCorrect = Toast.makeText(context, correct, duration);
        Toast toastInCorrect = Toast.makeText(context, inCorrect, duration);

        if (correctAnswer.equals(selectedAnswer)) {
            totalScore = totalScore + 1;
            toastCorrect.show();
            return totalScore;

        } else {
            totalScore = totalScore - 1;
            toastInCorrect.show();
            return totalScore;
        }
    }

    public void displayResults() {
        radioButtonAnswer.setVisibility(View.GONE);
        checkboxAnswer.setVisibility(View.GONE);
        freeTextAnswer.setVisibility(View.GONE);
        questionPrefixView.setVisibility(View.GONE);
        scoreTextView.setVisibility(View.VISIBLE);
        backButton.setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE);
        exitButton.setVisibility(View.VISIBLE);

        questionImageView.setImageResource(getResources().getIdentifier(questionNumber, "drawable", getPackageName()));
        questionNumberView.setText(getResources().getIdentifier("complete_header", "string", getPackageName()));
        questionTextView.setText(getResources().getIdentifier("complete_body", "string", getPackageName()));
        questionTextView.setGravity((Gravity.CENTER));

        results = String.valueOf(totalScore) + "/" + String.valueOf(numberOfQuestions);
        scoreTextView.setText(results);

    }

    public void restartButton(View view) {
        currentQuestion = 1;
        totalScore = 0;
        questionPrefixView.setVisibility(View.VISIBLE);
        radioButtonAnswer.setVisibility(View.GONE);
        checkboxAnswer.setVisibility(View.GONE);
        freeTextAnswer.setVisibility(View.GONE);
        scoreTextView.setVisibility(View.GONE);
        backButton.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.VISIBLE);
        exitButton.setVisibility(View.GONE);

        updateScreen();
    }
}


