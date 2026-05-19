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
        RIGHT_TRIANGLE,
        ISOSCELES_TRIANGLE,
        EQUILATERAL_TRIANGLE,
        SQUARE,
        RECTANGLE,
        CIRCLE,
        TRAPEZOID,
        PARALLELOGRAM,
        PENTAGON,
        HEXAGON,
        OCTAGON,
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
    final List<String> figureLabels;
    final String key;

    Question(
            Dimension dimension,
            Figure figure,
            String title,
            String prompt,
            String correctAnswer,
            String wrongAnswerOne,
            String wrongAnswerTwo,
            List<String> figureLabels,
            String key
    ) {
        this.dimension = dimension;
        this.figure = figure;
        this.title = title;
        this.prompt = prompt;
        this.correctAnswer = correctAnswer;
        this.answers = Arrays.asList(correctAnswer, wrongAnswerOne, wrongAnswerTwo);
        this.figureLabels = figureLabels;
        this.key = key;
    }
}
