package com.example.geometryforfrancesco;

import java.util.ArrayList;
import java.util.List;

final class QuestionBank {
    private QuestionBank() {
    }

    static List<Question> allQuestions() {
        List<Question> questions = new ArrayList<>();

        questions.add(new Question(
                Question.Dimension.TWO_D,
                Question.Figure.TRIANGLE,
                "Triangle area",
                "A triangle has base 10 cm and height 6 cm. What is its area?",
                "30 cm2",
                "60 cm2",
                "16 cm2"
        ));
        questions.add(new Question(
                Question.Dimension.TWO_D,
                Question.Figure.SQUARE,
                "Square area",
                "A square has side length 8 cm. What is its area?",
                "64 cm2",
                "32 cm2",
                "16 cm2"
        ));
        questions.add(new Question(
                Question.Dimension.TWO_D,
                Question.Figure.RECTANGLE,
                "Rectangle area",
                "A rectangle is 12 cm long and 5 cm wide. What is its area?",
                "60 cm2",
                "34 cm2",
                "17 cm2"
        ));
        questions.add(new Question(
                Question.Dimension.TWO_D,
                Question.Figure.CIRCLE,
                "Circle area",
                "A circle has radius 4 cm. Using pi approx 3.14, what is its area?",
                "50.24 cm2",
                "25.12 cm2",
                "12.56 cm2"
        ));
        questions.add(new Question(
                Question.Dimension.TWO_D,
                Question.Figure.TRAPEZOID,
                "Trapezoid area",
                "A trapezoid has bases 8 cm and 14 cm, with height 5 cm. What is its area?",
                "55 cm2",
                "110 cm2",
                "27.5 cm2"
        ));
        questions.add(new Question(
                Question.Dimension.TWO_D,
                Question.Figure.PARALLELOGRAM,
                "Parallelogram area",
                "A parallelogram has base 9 cm and height 7 cm. What is its area?",
                "63 cm2",
                "32 cm2",
                "126 cm2"
        ));

        questions.add(new Question(
                Question.Dimension.THREE_D,
                Question.Figure.SPHERE,
                "Sphere surface area",
                "A sphere has radius 3 cm. Using pi approx 3.14, what is its surface area?",
                "113.04 cm2",
                "28.26 cm2",
                "37.68 cm2"
        ));
        questions.add(new Question(
                Question.Dimension.THREE_D,
                Question.Figure.CYLINDER,
                "Cylinder surface area",
                "A closed cylinder has radius 3 cm and height 7 cm. Using pi approx 3.14, what is its surface area?",
                "188.4 cm2",
                "131.88 cm2",
                "65.94 cm2"
        ));
        questions.add(new Question(
                Question.Dimension.THREE_D,
                Question.Figure.CUBE,
                "Cube surface area",
                "A cube has side length 5 cm. What is its surface area?",
                "150 cm2",
                "125 cm2",
                "30 cm2"
        ));
        questions.add(new Question(
                Question.Dimension.THREE_D,
                Question.Figure.RECTANGULAR_PRISM,
                "Rectangular prism surface area",
                "A rectangular prism is 4 cm by 5 cm by 6 cm. What is its surface area?",
                "148 cm2",
                "120 cm2",
                "74 cm2"
        ));
        questions.add(new Question(
                Question.Dimension.THREE_D,
                Question.Figure.CONE,
                "Cone surface area",
                "A cone has radius 3 cm and slant height 5 cm. Using pi approx 3.14, what is its total surface area?",
                "75.36 cm2",
                "47.1 cm2",
                "28.26 cm2"
        ));

        return questions;
    }
}
