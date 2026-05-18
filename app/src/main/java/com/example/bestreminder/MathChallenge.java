package com.example.bestreminder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

final class MathChallenge {
    private static final Random RANDOM = new Random();

    final String question;
    final int correctAnswer;
    final int[] answers;

    MathChallenge(String question, int correctAnswer, int[] answers) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.answers = answers;
    }

    static MathChallenge create() {
        int operation = RANDOM.nextInt(4);
        switch (operation) {
            case 0:
                return createAddition();
            case 1:
                return createSubtraction();
            case 2:
                return createMultiplication();
            default:
                return createDivision();
        }
    }

    private static MathChallenge createAddition() {
        int left = randomBetween(1, 50);
        int right = randomBetween(1, 50);
        int answer = left + right;
        return new MathChallenge(left + " + " + right + " = ?", answer, createAnswers(answer));
    }

    private static MathChallenge createSubtraction() {
        int left = randomBetween(10, 99);
        int right = randomBetween(1, left);
        int answer = left - right;
        return new MathChallenge(left + " - " + right + " = ?", answer, createAnswers(answer));
    }

    private static MathChallenge createMultiplication() {
        int left = randomBetween(2, 12);
        int right = randomBetween(2, 12);
        int answer = left * right;
        return new MathChallenge(left + " x " + right + " = ?", answer, createAnswers(answer));
    }

    private static MathChallenge createDivision() {
        int divisor = randomBetween(2, 12);
        int answer = randomBetween(2, 12);
        int dividend = divisor * answer;
        return new MathChallenge(dividend + " / " + divisor + " = ?", answer, createAnswers(answer));
    }

    private static int[] createAnswers(int correctAnswer) {
        List<Integer> answers = new ArrayList<>();
        answers.add(correctAnswer);

        while (answers.size() < 3) {
            int offset = randomBetween(1, 10);
            int wrongAnswer = correctAnswer + (RANDOM.nextBoolean() ? offset : -offset);
            if (wrongAnswer >= 0 && !answers.contains(wrongAnswer)) {
                answers.add(wrongAnswer);
            }
        }

        Collections.shuffle(answers, RANDOM);
        return new int[]{answers.get(0), answers.get(1), answers.get(2)};
    }

    private static int randomBetween(int min, int max) {
        return min + RANDOM.nextInt(max - min + 1);
    }
}
