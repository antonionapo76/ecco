package com.example.bestreminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MathAnswerReceiver extends BroadcastReceiver {
    static final String ACTION_ANSWER = "com.example.bestreminder.ANSWER_MATH";
    static final String EXTRA_SELECTED_ANSWER = "selected_answer";
    static final String EXTRA_CORRECT_ANSWER = "correct_answer";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!ACTION_ANSWER.equals(intent.getAction())) {
            return;
        }

        int selectedAnswer = intent.getIntExtra(EXTRA_SELECTED_ANSWER, Integer.MIN_VALUE);
        int correctAnswer = intent.getIntExtra(EXTRA_CORRECT_ANSWER, Integer.MAX_VALUE);
        NotificationHelper.showAnswerResult(context, selectedAnswer == correctAnswer, correctAnswer);
    }
}
