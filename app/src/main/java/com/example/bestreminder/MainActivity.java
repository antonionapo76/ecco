package com.example.bestreminder;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity {
    private static final Random RANDOM = new Random();

    private TextView questionText;
    private TextView feedbackText;
    private TextView scoreText;
    private LinearLayout answerButtonsLayout;
    private Button startButton;
    private Button finishButton;

    private int correctAnswer;
    private int correctCount;
    private int attemptedCount;
    private boolean quizRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createContentView());
        showStoppedState();
    }

    private ScrollView createContentView() {
        int horizontalPadding = dp(24);

        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        scrollView.setBackgroundColor(Color.WHITE);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setPadding(horizontalPadding, dp(36), horizontalPadding, dp(24));
        scrollView.addView(root, new ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        TextView title = new TextView(this);
        title.setText("Tabelline per Michele");
        title.setTextColor(Color.rgb(30, 64, 175));
        title.setTextSize(34);
        title.setGravity(Gravity.CENTER);
        title.setTypeface(title.getTypeface(), android.graphics.Typeface.BOLD);
        root.addView(title, matchWrapLayout());

        TextView subtitle = new TextView(this);
        subtitle.setText("Allenati con le tabelline dal 2 al 12.");
        subtitle.setTextColor(Color.rgb(71, 85, 105));
        subtitle.setTextSize(18);
        subtitle.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams subtitleParams = matchWrapLayout();
        subtitleParams.setMargins(0, dp(12), 0, dp(32));
        root.addView(subtitle, subtitleParams);

        scoreText = new TextView(this);
        scoreText.setTextColor(Color.rgb(51, 65, 85));
        scoreText.setTextSize(18);
        scoreText.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams scoreParams = matchWrapLayout();
        scoreParams.setMargins(0, 0, 0, dp(24));
        root.addView(scoreText, scoreParams);

        LinearLayout controlRow = new LinearLayout(this);
        controlRow.setOrientation(LinearLayout.HORIZONTAL);
        controlRow.setGravity(Gravity.CENTER);
        root.addView(controlRow, matchWrapLayout());

        startButton = new Button(this);
        startButton.setText("Start");
        startButton.setAllCaps(false);
        startButton.setTextSize(20);
        startButton.setOnClickListener(view -> startQuiz());
        LinearLayout.LayoutParams startParams = weightedButtonParams();
        startParams.setMargins(0, 0, dp(8), 0);
        controlRow.addView(startButton, startParams);

        finishButton = new Button(this);
        finishButton.setText("Finish");
        finishButton.setAllCaps(false);
        finishButton.setTextSize(20);
        finishButton.setOnClickListener(view -> finishQuiz());
        LinearLayout.LayoutParams finishParams = weightedButtonParams();
        finishParams.setMargins(dp(8), 0, 0, 0);
        controlRow.addView(finishButton, finishParams);

        questionText = new TextView(this);
        questionText.setTextColor(Color.rgb(15, 23, 42));
        questionText.setTextSize(42);
        questionText.setGravity(Gravity.CENTER);
        questionText.setTypeface(questionText.getTypeface(), android.graphics.Typeface.BOLD);
        LinearLayout.LayoutParams questionParams = matchWrapLayout();
        questionParams.setMargins(0, dp(40), 0, dp(24));
        root.addView(questionText, questionParams);

        answerButtonsLayout = new LinearLayout(this);
        answerButtonsLayout.setOrientation(LinearLayout.VERTICAL);
        answerButtonsLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        root.addView(answerButtonsLayout, matchWrapLayout());

        feedbackText = new TextView(this);
        feedbackText.setTextColor(Color.rgb(51, 65, 85));
        feedbackText.setTextSize(24);
        feedbackText.setGravity(Gravity.CENTER);
        feedbackText.setTypeface(feedbackText.getTypeface(), android.graphics.Typeface.BOLD);
        LinearLayout.LayoutParams feedbackParams = matchWrapLayout();
        feedbackParams.setMargins(0, dp(24), 0, 0);
        root.addView(feedbackText, feedbackParams);

        return scrollView;
    }

    private void startQuiz() {
        quizRunning = true;
        correctCount = 0;
        attemptedCount = 0;
        startButton.setEnabled(false);
        finishButton.setEnabled(true);
        feedbackText.setText("Vai Michele!");
        feedbackText.setTextColor(Color.rgb(30, 64, 175));
        updateScore();
        showNextQuestion();
    }

    private void finishQuiz() {
        quizRunning = false;
        startButton.setEnabled(true);
        finishButton.setEnabled(false);
        answerButtonsLayout.removeAllViews();
        questionText.setText("Premi Start per giocare.");
        feedbackText.setText("Hai finito! Bravo Michele!");
        feedbackText.setTextColor(Color.rgb(30, 64, 175));
        updateScore();
    }

    private void showStoppedState() {
        quizRunning = false;
        finishButton.setEnabled(false);
        answerButtonsLayout.removeAllViews();
        questionText.setText("Premi Start per giocare.");
        feedbackText.setText("");
        updateScore();
    }

    private void showNextQuestion() {
        if (!quizRunning) {
            return;
        }

        int left = randomBetween(2, 12);
        int right = randomBetween(2, 12);
        correctAnswer = left * right;
        questionText.setText(left + " x " + right + " = ?");

        answerButtonsLayout.removeAllViews();
        for (int answer : createAnswers(correctAnswer)) {
            Button answerButton = new Button(this);
            answerButton.setText(String.valueOf(answer));
            answerButton.setTextSize(26);
            answerButton.setAllCaps(false);
            answerButton.setOnClickListener(view -> checkAnswer(answer));

            LinearLayout.LayoutParams answerParams = matchWrapLayout();
            answerParams.setMargins(0, 0, 0, dp(12));
            answerButtonsLayout.addView(answerButton, answerParams);
        }
    }

    private void checkAnswer(int selectedAnswer) {
        if (!quizRunning) {
            return;
        }

        attemptedCount++;
        if (selectedAnswer == correctAnswer) {
            correctCount++;
            feedbackText.setText("Bravo Michele! Risposta giusta!");
            feedbackText.setTextColor(Color.rgb(22, 101, 52));
            updateScore();
            showNextQuestion();
            return;
        }

        feedbackText.setText("Riprova, puoi farcela!");
        feedbackText.setTextColor(Color.rgb(185, 28, 28));
        updateScore();
    }

    private List<Integer> createAnswers(int correct) {
        List<Integer> answers = new ArrayList<>();
        answers.add(correct);

        while (answers.size() < 3) {
            int offset = randomBetween(1, 12);
            int wrongAnswer = correct + (RANDOM.nextBoolean() ? offset : -offset);
            if (wrongAnswer > 0 && !answers.contains(wrongAnswer)) {
                answers.add(wrongAnswer);
            }
        }

        Collections.shuffle(answers, RANDOM);
        return answers;
    }

    private void updateScore() {
        scoreText.setText("Punti: " + correctCount + " / " + attemptedCount);
    }

    private int randomBetween(int min, int max) {
        return min + RANDOM.nextInt(max - min + 1);
    }

    private LinearLayout.LayoutParams weightedButtonParams() {
        return new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
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
