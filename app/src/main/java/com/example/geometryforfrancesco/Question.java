package com.example.geometryforfrancesco;

import java.util.Arrays;
import java.util.List;

final class Question {
    enum Dimension {
        TWO_D,
        THREE_D
    }

    enum Figure {
        TRIANGLE,
        SQUARE,
        RECTANGLE,
        CIRCLE,
        TRAPEZOID,
        PARALLELOGRAM,
        SPHERE,
        CYLINDER,
        CUBE,
        RECTANGULAR_PRISM,
        CONE
    }

    final Dimension dimension;
    final Figure figure;
    final String title;
    final String prompt;
    final String correctAnswer;
    final List<String> answers;

    Question(
            Dimension dimension,
            Figure figure,
            String title,
            String prompt,
            String correctAnswer,
            String wrongAnswerOne,
            String wrongAnswerTwo
    ) {
        this.dimension = dimension;
        this.figure = figure;
        this.title = title;
        this.prompt = prompt;
        this.correctAnswer = correctAnswer;
        this.answers = Arrays.asList(correctAnswer, wrongAnswerOne, wrongAnswerTwo);
    }
}
