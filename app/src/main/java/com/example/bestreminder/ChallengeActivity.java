package com.example.bestreminder;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChallengeActivity extends Activity {
    static final String EXTRA_QUESTION = "question";
    static final String EXTRA_CORRECT_ANSWER = "correct_answer";
    static final String EXTRA_ANSWERS = "answers";

    private TextView resultText;
    private LinearLayout answersLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createContentView());
    }

    private LinearLayout createContentView() {
        String question = getIntent().getStringExtra(EXTRA_QUESTION);
        int correctAnswer = getIntent().getIntExtra(EXTRA_CORRECT_ANSWER, Integer.MIN_VALUE);
        int[] answers = getIntent().getIntArrayExtra(EXTRA_ANSWERS);

        if (question == null || answers == null || answers.length != 3) {
            MathChallenge fallbackChallenge = MathChallenge.create();
            question = fallbackChallenge.question;
            correctAnswer = fallbackChallenge.correctAnswer;
            answers = fallbackChallenge.answers;
        }

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setPadding(dp(24), dp(44), dp(24), dp(24));
        root.setBackgroundColor(Color.WHITE);

        TextView title = new TextView(this);
        title.setText("Math Challenge");
        title.setTextColor(Color.rgb(30, 64, 175));
        title.setTextSize(32);
        title.setGravity(Gravity.CENTER);
        title.setTypeface(title.getTypeface(), android.graphics.Typeface.BOLD);
        root.addView(title, matchWrapLayout());

        TextView questionText = new TextView(this);
        questionText.setText(question);
        questionText.setTextColor(Color.rgb(15, 23, 42));
        questionText.setTextSize(34);
        questionText.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams questionParams = matchWrapLayout();
        questionParams.setMargins(0, dp(32), 0, dp(28));
        root.addView(questionText, questionParams);

        resultText = new TextView(this);
        resultText.setTextColor(Color.rgb(51, 65, 85));
        resultText.setTextSize(20);
        resultText.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams resultParams = matchWrapLayout();
        resultParams.setMargins(0, 0, 0, dp(24));
        root.addView(resultText, resultParams);

        answersLayout = new LinearLayout(this);
        answersLayout.setOrientation(LinearLayout.VERTICAL);
        answersLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        root.addView(answersLayout, matchWrapLayout());

        for (int answer : answers) {
            Button answerButton = new Button(this);
            answerButton.setText(String.valueOf(answer));
            answerButton.setTextSize(24);
            answerButton.setAllCaps(false);
            int finalCorrectAnswer = correctAnswer;
            answerButton.setOnClickListener(view -> showResult(answer == finalCorrectAnswer, finalCorrectAnswer));

            LinearLayout.LayoutParams answerParams = matchWrapLayout();
            answerParams.setMargins(0, 0, 0, dp(12));
            answersLayout.addView(answerButton, answerParams);
        }

        return root;
    }

    private void showResult(boolean isCorrect, int correctAnswer) {
        if (isCorrect) {
            resultText.setText("Congratulations!");
            resultText.setTextColor(Color.rgb(22, 101, 52));
        } else {
            resultText.setText("Not correct. The correct answer is " + correctAnswer + ".");
            resultText.setTextColor(Color.rgb(185, 28, 28));
        }

        for (int i = 0; i < answersLayout.getChildCount(); i++) {
            answersLayout.getChildAt(i).setEnabled(false);
        }
    }

    private LinearLayout.LayoutParams matchWrapLayout() {
        return new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
