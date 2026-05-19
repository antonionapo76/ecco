package com.example.geometryforfrancesco;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

public final class MainActivity extends Activity {
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final List<Button> answerButtons = new ArrayList<>();
    private final Set<String> usedQuestionKeys = new HashSet<>();
    private final Random random = new Random();

    private CheckBox twoDCheckBox;
    private CheckBox threeDCheckBox;
    private GeometryFigureView figureView;
    private TextView questionTitleText;
    private TextView questionPromptText;
    private TextView feedbackText;
    private TextView progressText;
    private Button finishButton;
    private Question currentQuestion;

    private int currentQuestionNumber;
    private int correctCount;
    private int wrongTries;
    private boolean testRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createContentView());
        resetQuizView("Tick 2D, 3D, or both, then press Start.");
    }

    private View createContentView() {
        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        scrollView.setBackgroundColor(Color.rgb(247, 248, 252));

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(20), dp(24), dp(20), dp(24));
        scrollView.addView(root, new ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        TextView appTitle = new TextView(this);
        appTitle.setText("Geometry for Francesco");
        appTitle.setTextSize(31);
        appTitle.setTextColor(Color.rgb(24, 30, 44));
        appTitle.setGravity(Gravity.CENTER);
        appTitle.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
        root.addView(appTitle, matchWidthWrapHeight());

        TextView subtitle = new TextView(this);
        subtitle.setText("Random area, perimeter, and formula questions. Finish when you want to stop.");
        subtitle.setTextSize(16);
        subtitle.setTextColor(Color.rgb(89, 99, 120));
        subtitle.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams subtitleParams = matchWidthWrapHeight();
        subtitleParams.setMargins(0, dp(8), 0, dp(16));
        root.addView(subtitle, subtitleParams);

        LinearLayout filters = new LinearLayout(this);
        filters.setOrientation(LinearLayout.HORIZONTAL);
        filters.setGravity(Gravity.CENTER);
        twoDCheckBox = new CheckBox(this);
        twoDCheckBox.setText("2D");
        twoDCheckBox.setTextSize(18);
        twoDCheckBox.setChecked(true);
        threeDCheckBox = new CheckBox(this);
        threeDCheckBox.setText("3D");
        threeDCheckBox.setTextSize(18);
        threeDCheckBox.setChecked(true);
        filters.addView(twoDCheckBox);
        filters.addView(threeDCheckBox);
        root.addView(filters, matchWidthWrapHeight());

        LinearLayout controls = new LinearLayout(this);
        controls.setOrientation(LinearLayout.HORIZONTAL);
        controls.setGravity(Gravity.CENTER);
        Button startButton = new Button(this);
        startButton.setText("Start");
        startButton.setOnClickListener(view -> startTest());
        finishButton = new Button(this);
        finishButton.setText("Finish");
        finishButton.setOnClickListener(view -> finishTest());
        controls.addView(startButton, equalButtonParams());
        controls.addView(finishButton, equalButtonParams());
        LinearLayout.LayoutParams controlsParams = matchWidthWrapHeight();
        controlsParams.setMargins(0, dp(8), 0, dp(16));
        root.addView(controls, controlsParams);

        progressText = new TextView(this);
        progressText.setTextSize(14);
        progressText.setTextColor(Color.rgb(89, 99, 120));
        progressText.setGravity(Gravity.CENTER);
        root.addView(progressText, matchWidthWrapHeight());

        figureView = new GeometryFigureView(this);
        LinearLayout.LayoutParams figureParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dp(220)
        );
        figureParams.setMargins(0, dp(14), 0, dp(14));
        root.addView(figureView, figureParams);

        questionTitleText = new TextView(this);
        questionTitleText.setTextSize(22);
        questionTitleText.setTextColor(Color.rgb(24, 30, 44));
        questionTitleText.setGravity(Gravity.CENTER);
        questionTitleText.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
        root.addView(questionTitleText, matchWidthWrapHeight());

        questionPromptText = new TextView(this);
        questionPromptText.setTextSize(18);
        questionPromptText.setTextColor(Color.rgb(49, 58, 75));
        questionPromptText.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams promptParams = matchWidthWrapHeight();
        promptParams.setMargins(0, dp(8), 0, dp(12));
        root.addView(questionPromptText, promptParams);

        for (int i = 0; i < 3; i++) {
            Button answerButton = new Button(this);
            answerButton.setTextSize(17);
            final int index = i;
            answerButton.setOnClickListener(view -> checkAnswer(answerButtons.get(index).getText().toString()));
            answerButtons.add(answerButton);
            LinearLayout.LayoutParams answerParams = matchWidthWrapHeight();
            answerParams.setMargins(0, dp(6), 0, 0);
            root.addView(answerButton, answerParams);
        }

        feedbackText = new TextView(this);
        feedbackText.setTextSize(20);
        feedbackText.setGravity(Gravity.CENTER);
        feedbackText.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
        LinearLayout.LayoutParams feedbackParams = matchWidthWrapHeight();
        feedbackParams.setMargins(0, dp(18), 0, 0);
        root.addView(feedbackText, feedbackParams);

        return scrollView;
    }

    private void startTest() {
        handler.removeCallbacksAndMessages(null);
        if (!twoDCheckBox.isChecked() && !threeDCheckBox.isChecked()) {
            resetQuizView("Please tick 2D, 3D, or both before starting.");
            return;
        }

        usedQuestionKeys.clear();
        testRunning = true;
        currentQuestionNumber = 0;
        correctCount = 0;
        wrongTries = 0;
        finishButton.setEnabled(true);
        twoDCheckBox.setEnabled(false);
        threeDCheckBox.setEnabled(false);
        showNextQuestion();
    }

    private void finishTest() {
        handler.removeCallbacksAndMessages(null);
        if (!testRunning) {
            resetQuizView("The test is not running. Press Start when you are ready.");
            return;
        }

        String summary = String.format(
                Locale.getDefault(),
                "Test finished. Francesco completed %d questions with %d wrong tries.",
                correctCount,
                wrongTries
        );
        resetQuizView(summary);
    }

    private void showNextQuestion() {
        currentQuestionNumber++;
        currentQuestion = QuestionFactory.next(
                twoDCheckBox.isChecked(),
                threeDCheckBox.isChecked(),
                usedQuestionKeys,
                random
        );

        progressText.setText(String.format(
                Locale.getDefault(),
                "Question %d - completed %d",
                currentQuestionNumber,
                correctCount
        ));
        figureView.setVisibility(View.VISIBLE);
        figureView.setQuestion(currentQuestion);
        questionTitleText.setText(currentQuestion.title);
        questionPromptText.setText(currentQuestion.prompt);
        feedbackText.setText("");

        List<String> shuffledAnswers = new ArrayList<>(currentQuestion.answers);
        Collections.shuffle(shuffledAnswers, random);
        for (int i = 0; i < answerButtons.size(); i++) {
            Button answerButton = answerButtons.get(i);
            answerButton.setText(shuffledAnswers.get(i));
            answerButton.setEnabled(true);
            answerButton.setVisibility(View.VISIBLE);
        }
    }

    private void checkAnswer(String selectedAnswer) {
        if (!testRunning || currentQuestion == null) {
            return;
        }

        if (selectedAnswer.equals(currentQuestion.correctAnswer)) {
            correctCount++;
            feedbackText.setTextColor(Color.rgb(0, 128, 78));
            feedbackText.setText("Congrats Francesco!");
            setAnswerButtonsEnabled(false);
            handler.postDelayed(this::showNextQuestion, 1100);
        } else {
            wrongTries++;
            feedbackText.setTextColor(Color.rgb(196, 65, 45));
            feedbackText.setText("Try again.");
        }
    }

    private void resetQuizView(String message) {
        testRunning = false;
        currentQuestion = null;
        twoDCheckBox.setEnabled(true);
        threeDCheckBox.setEnabled(true);
        finishButton.setEnabled(false);
        progressText.setText(message);
        figureView.setVisibility(View.GONE);
        questionTitleText.setText("");
        questionPromptText.setText("");
        feedbackText.setText("");
        for (Button answerButton : answerButtons) {
            answerButton.setVisibility(View.GONE);
            answerButton.setEnabled(false);
        }
    }

    private void setAnswerButtonsEnabled(boolean enabled) {
        for (Button answerButton : answerButtons) {
            answerButton.setEnabled(enabled);
        }
    }

    private LinearLayout.LayoutParams matchWidthWrapHeight() {
        return new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
    }

    private LinearLayout.LayoutParams equalButtonParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
        );
        params.setMargins(dp(4), 0, dp(4), 0);
        return params;
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
