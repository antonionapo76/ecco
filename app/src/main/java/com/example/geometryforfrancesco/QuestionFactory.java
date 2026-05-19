package com.example.geometryforfrancesco;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

final class QuestionFactory {
    private static final double PI = 3.14;

    private QuestionFactory() {
    }

    static Question next(boolean includeTwoD, boolean includeThreeD, Set<String> usedKeys, Random random) {
        for (int attempt = 0; attempt < 2000; attempt++) {
            Question question = generate(includeTwoD, includeThreeD, random);
            if (usedKeys.add(question.key)) {
                return question;
            }
        }

        Question fallback = fallbackQuestion(includeTwoD, usedKeys.size(), random);
        usedKeys.add(fallback.key);
        return fallback;
    }

    private static Question generate(boolean includeTwoD, boolean includeThreeD, Random random) {
        if (includeTwoD && includeThreeD) {
            return random.nextBoolean() ? generateTwoD(random) : generateThreeD(random);
        }
        if (includeTwoD) {
            return generateTwoD(random);
        }
        return generateThreeD(random);
    }

    private static Question fallbackQuestion(boolean includeTwoD, int completedQuestions, Random random) {
        int seed = completedQuestions + 101;
        if (includeTwoD) {
            int length = seed;
            int width = number(random, 11, 99);
            return numericQuestion(
                    Question.Dimension.TWO_D,
                    Question.Figure.RECTANGLE,
                    "Rectangle area",
                    "A rectangle is " + length + " cm long and " + width + " cm wide. What is its area?",
                    length * width,
                    "cm2",
                    Arrays.asList("l=" + length, "w=" + width),
                    "fallback-2d-rectangle-area-" + length + "-" + width + "-" + seed,
                    random
            );
        }

        int side = seed;
        return numericQuestion(
                Question.Dimension.THREE_D,
                Question.Figure.CUBE,
                "Cube surface area",
                "A cube has side length " + side + " cm. What is its surface area?",
                6 * side * side,
                "cm2",
                Collections.singletonList("s=" + side),
                "fallback-3d-cube-surface-" + side + "-" + seed,
                random
        );
    }

    private static Question generateTwoD(Random random) {
        switch (random.nextInt(3)) {
            case 0:
                return generateTwoDArea(random);
            case 1:
                return generateTwoDPerimeter(random);
            default:
                return generateTwoDFormula(random);
        }
    }

    private static Question generateTwoDArea(Random random) {
        switch (random.nextInt(11)) {
            case 0: {
                int base = number(random, 4, 24);
                int height = number(random, 3, 18);
                double area = base * height / 2.0;
                return numericQuestion(
                        Question.Dimension.TWO_D,
                        Question.Figure.TRIANGLE,
                        "Triangle area",
                        "A triangle has base " + base + " cm and height " + height + " cm. What is its area?",
                        area,
                        "cm2",
                        Arrays.asList("b=" + base, "h=" + height),
                        "2d-area-triangle-" + base + "-" + height,
                        random
                );
            }
            case 1: {
                int legA = number(random, 3, 18);
                int legB = number(random, 3, 18);
                double area = legA * legB / 2.0;
                return numericQuestion(
                        Question.Dimension.TWO_D,
                        Question.Figure.RIGHT_TRIANGLE,
                        "Right triangle area",
                        "A right triangle has perpendicular sides " + legA + " cm and " + legB + " cm. What is its area?",
                        area,
                        "cm2",
                        Arrays.asList("a=" + legA, "b=" + legB),
                        "2d-area-right-triangle-" + legA + "-" + legB,
                        random
                );
            }
            case 2: {
                int base = evenNumber(random, 6, 24);
                int height = number(random, 4, 18);
                double area = base * height / 2.0;
                return numericQuestion(
                        Question.Dimension.TWO_D,
                        Question.Figure.ISOSCELES_TRIANGLE,
                        "Isosceles triangle area",
                        "An isosceles triangle has base " + base + " cm and height " + height + " cm. What is its area?",
                        area,
                        "cm2",
                        Arrays.asList("b=" + base, "h=" + height),
                        "2d-area-isosceles-" + base + "-" + height,
                        random
                );
            }
            case 3: {
                int side = number(random, 3, 20);
                return numericQuestion(
                        Question.Dimension.TWO_D,
                        Question.Figure.SQUARE,
                        "Square area",
                        "A square has side length " + side + " cm. What is its area?",
                        side * side,
                        "cm2",
                        Collections.singletonList("s=" + side),
                        "2d-area-square-" + side,
                        random
                );
            }
            case 4: {
                int length = number(random, 5, 28);
                int width = number(random, 3, 18);
                return numericQuestion(
                        Question.Dimension.TWO_D,
                        Question.Figure.RECTANGLE,
                        "Rectangle area",
                        "A rectangle is " + length + " cm long and " + width + " cm wide. What is its area?",
                        length * width,
                        "cm2",
                        Arrays.asList("l=" + length, "w=" + width),
                        "2d-area-rectangle-" + length + "-" + width,
                        random
                );
            }
            case 5: {
                int radius = number(random, 2, 14);
                return numericQuestion(
                        Question.Dimension.TWO_D,
                        Question.Figure.CIRCLE,
                        "Circle area",
                        "A circle has radius " + radius + " cm. Using pi approx 3.14, what is its area?",
                        PI * radius * radius,
                        "cm2",
                        Collections.singletonList("r=" + radius),
                        "2d-area-circle-" + radius,
                        random
                );
            }
            case 6: {
                int baseOne = number(random, 4, 18);
                int baseTwo = number(random, baseOne + 2, baseOne + 18);
                int height = number(random, 3, 14);
                double area = (baseOne + baseTwo) * height / 2.0;
                return numericQuestion(
                        Question.Dimension.TWO_D,
                        Question.Figure.TRAPEZOID,
                        "Trapezoid area",
                        "A trapezoid has bases " + baseOne + " cm and " + baseTwo + " cm, with height " + height + " cm. What is its area?",
                        area,
                        "cm2",
                        Arrays.asList("a=" + baseOne, "b=" + baseTwo, "h=" + height),
                        "2d-area-trapezoid-" + baseOne + "-" + baseTwo + "-" + height,
                        random
                );
            }
            case 7: {
                int base = number(random, 4, 24);
                int height = number(random, 3, 18);
                return numericQuestion(
                        Question.Dimension.TWO_D,
                        Question.Figure.PARALLELOGRAM,
                        "Parallelogram area",
                        "A parallelogram has base " + base + " cm and height " + height + " cm. What is its area?",
                        base * height,
                        "cm2",
                        Arrays.asList("b=" + base, "h=" + height),
                        "2d-area-parallelogram-" + base + "-" + height,
                        random
                );
            }
            case 8:
                return regularPolygonAreaQuestion(Question.Figure.PENTAGON, "Pentagon", 5, random);
            case 9:
                return regularPolygonAreaQuestion(Question.Figure.HEXAGON, "Hexagon", 6, random);
            default:
                return regularPolygonAreaQuestion(Question.Figure.OCTAGON, "Octagon", 8, random);
        }
    }

    private static Question generateTwoDPerimeter(Random random) {
        switch (random.nextInt(12)) {
            case 0: {
                int a = number(random, 4, 20);
                int b = number(random, 4, 20);
                int c = number(random, Math.abs(a - b) + 1, a + b - 1);
                return numericQuestion(
                        Question.Dimension.TWO_D,
                        Question.Figure.TRIANGLE,
                        "Triangle perimeter",
                        "A triangle has sides " + a + " cm, " + b + " cm, and " + c + " cm. What is its perimeter?",
                        a + b + c,
                        "cm",
                        Arrays.asList("a=" + a, "b=" + b, "c=" + c),
                        "2d-perimeter-triangle-" + a + "-" + b + "-" + c,
                        random
                );
            }
            case 1: {
                int scale = number(random, 1, 6);
                int a = 3 * scale;
                int b = 4 * scale;
                int c = 5 * scale;
                return numericQuestion(
                        Question.Dimension.TWO_D,
                        Question.Figure.RIGHT_TRIANGLE,
                        "Right triangle perimeter",
                        "A right triangle has sides " + a + " cm, " + b + " cm, and " + c + " cm. What is its perimeter?",
                        a + b + c,
                        "cm",
                        Arrays.asList("a=" + a, "b=" + b, "c=" + c),
                        "2d-perimeter-right-triangle-" + scale,
                        random
                );
            }
            case 2: {
                int equalSide = number(random, 5, 22);
                int base = number(random, 4, equalSide * 2 - 2);
                return numericQuestion(
                        Question.Dimension.TWO_D,
                        Question.Figure.ISOSCELES_TRIANGLE,
                        "Isosceles triangle perimeter",
                        "An isosceles triangle has equal sides " + equalSide + " cm and base " + base + " cm. What is its perimeter?",
                        equalSide + equalSide + base,
                        "cm",
                        Arrays.asList("s=" + equalSide, "b=" + base),
                        "2d-perimeter-isosceles-" + equalSide + "-" + base,
                        random
                );
            }
            case 3: {
                int side = number(random, 3, 18);
                return numericQuestion(
                        Question.Dimension.TWO_D,
                        Question.Figure.EQUILATERAL_TRIANGLE,
                        "Equilateral triangle perimeter",
                        "An equilateral triangle has side length " + side + " cm. What is its perimeter?",
                        3 * side,
                        "cm",
                        Collections.singletonList("s=" + side),
                        "2d-perimeter-equilateral-" + side,
                        random
                );
            }
            case 4: {
                int side = number(random, 3, 24);
                return numericQuestion(
                        Question.Dimension.TWO_D,
                        Question.Figure.SQUARE,
                        "Square perimeter",
                        "A square has side length " + side + " cm. What is its perimeter?",
                        4 * side,
                        "cm",
                        Collections.singletonList("s=" + side),
                        "2d-perimeter-square-" + side,
                        random
                );
            }
            case 5: {
                int length = number(random, 5, 28);
                int width = number(random, 3, 18);
                return numericQuestion(
                        Question.Dimension.TWO_D,
                        Question.Figure.RECTANGLE,
                        "Rectangle perimeter",
                        "A rectangle is " + length + " cm long and " + width + " cm wide. What is its perimeter?",
                        2 * (length + width),
                        "cm",
                        Arrays.asList("l=" + length, "w=" + width),
                        "2d-perimeter-rectangle-" + length + "-" + width,
                        random
                );
            }
            case 6: {
                int radius = number(random, 2, 14);
                return numericQuestion(
                        Question.Dimension.TWO_D,
                        Question.Figure.CIRCLE,
                        "Circle perimeter",
                        "A circle has radius " + radius + " cm. Using pi approx 3.14, what is its perimeter (circumference)?",
                        2 * PI * radius,
                        "cm",
                        Collections.singletonList("r=" + radius),
                        "2d-perimeter-circle-" + radius,
                        random
                );
            }
            case 7: {
                int side = number(random, 4, 18);
                int baseOne = number(random, 6, 24);
                int baseTwo = number(random, 6, 24);
                return numericQuestion(
                        Question.Dimension.TWO_D,
                        Question.Figure.TRAPEZOID,
                        "Trapezoid perimeter",
                        "An isosceles trapezoid has bases " + baseOne + " cm and " + baseTwo + " cm, and legs " + side + " cm each. What is its perimeter?",
                        baseOne + baseTwo + 2 * side,
                        "cm",
                        Arrays.asList("a=" + baseOne, "b=" + baseTwo, "l=" + side),
                        "2d-perimeter-trapezoid-" + baseOne + "-" + baseTwo + "-" + side,
                        random
                );
            }
            case 8: {
                int base = number(random, 5, 22);
                int side = number(random, 4, 18);
                return numericQuestion(
                        Question.Dimension.TWO_D,
                        Question.Figure.PARALLELOGRAM,
                        "Parallelogram perimeter",
                        "A parallelogram has base " + base + " cm and side " + side + " cm. What is its perimeter?",
                        2 * (base + side),
                        "cm",
                        Arrays.asList("b=" + base, "s=" + side),
                        "2d-perimeter-parallelogram-" + base + "-" + side,
                        random
                );
            }
            case 9:
                return regularPolygonPerimeterQuestion(Question.Figure.PENTAGON, "Pentagon", 5, random);
            case 10:
                return regularPolygonPerimeterQuestion(Question.Figure.HEXAGON, "Hexagon", 6, random);
            default:
                return regularPolygonPerimeterQuestion(Question.Figure.OCTAGON, "Octagon", 8, random);
        }
    }

    private static Question generateTwoDFormula(Random random) {
        switch (random.nextInt(12)) {
            case 0:
                return formulaQuestion(Question.Dimension.TWO_D, Question.Figure.TRIANGLE, "Triangle area formula", "Which formula gives the area of a triangle?", "A = (base x height) / 2", "A = base x height", "A = 2 x (base + height)", Arrays.asList("base", "height"));
            case 1:
                return formulaQuestion(Question.Dimension.TWO_D, Question.Figure.RIGHT_TRIANGLE, "Right triangle area formula", "Which formula gives the area of a right triangle using its perpendicular sides a and b?", "A = (a x b) / 2", "A = a x b", "A = a + b + c", Arrays.asList("a", "b"));
            case 2:
                return formulaQuestion(Question.Dimension.TWO_D, Question.Figure.SQUARE, "Square area formula", "Which formula gives the area of a square?", "A = side x side", "A = 4 x side", "A = side + side", Collections.singletonList("side"));
            case 3:
                return formulaQuestion(Question.Dimension.TWO_D, Question.Figure.RECTANGLE, "Rectangle perimeter formula", "Which formula gives the perimeter of a rectangle?", "P = 2 x (length + width)", "P = length x width", "P = length + width", Arrays.asList("length", "width"));
            case 4:
                return formulaQuestion(Question.Dimension.TWO_D, Question.Figure.CIRCLE, "Circle area formula", "Which formula gives the area of a circle?", "A = pi x r x r", "A = 2 x pi x r", "A = pi x d", Collections.singletonList("r"));
            case 5:
                return formulaQuestion(Question.Dimension.TWO_D, Question.Figure.CIRCLE, "Circle perimeter formula", "Which formula gives the perimeter (circumference) of a circle?", "C = 2 x pi x r", "C = pi x r x r", "C = r + r", Collections.singletonList("r"));
            case 6:
                return formulaQuestion(Question.Dimension.TWO_D, Question.Figure.TRAPEZOID, "Trapezoid area formula", "Which formula gives the area of a trapezoid?", "A = ((base1 + base2) x height) / 2", "A = base1 x base2 x height", "A = 2 x (base1 + base2)", Arrays.asList("base1", "base2", "height"));
            case 7:
                return formulaQuestion(Question.Dimension.TWO_D, Question.Figure.PARALLELOGRAM, "Parallelogram area formula", "Which formula gives the area of a parallelogram?", "A = base x height", "A = (base x height) / 2", "A = 2 x (base + height)", Arrays.asList("base", "height"));
            case 8:
                return formulaQuestion(Question.Dimension.TWO_D, Question.Figure.PENTAGON, "Regular pentagon perimeter formula", "A regular pentagon has side s. Which formula gives its perimeter?", "P = 5 x s", "P = 6 x s", "P = s x s", Collections.singletonList("s"));
            case 9:
                return formulaQuestion(Question.Dimension.TWO_D, Question.Figure.HEXAGON, "Regular hexagon perimeter formula", "A regular hexagon has side s. Which formula gives its perimeter?", "P = 6 x s", "P = 5 x s", "P = 8 x s", Collections.singletonList("s"));
            case 10:
                return formulaQuestion(Question.Dimension.TWO_D, Question.Figure.OCTAGON, "Regular octagon perimeter formula", "A regular octagon has side s. Which formula gives its perimeter?", "P = 8 x s", "P = 6 x s", "P = 4 x s", Collections.singletonList("s"));
            default:
                return formulaQuestion(Question.Dimension.TWO_D, Question.Figure.PENTAGON, "Regular polygon area formula", "Which formula gives the area of a regular polygon using perimeter P and apothem a?", "A = (P x a) / 2", "A = P + a", "A = P x P", Arrays.asList("P", "a"));
        }
    }

    private static Question generateThreeD(Random random) {
        if (random.nextInt(3) == 0) {
            return generateThreeDFormula(random);
        }
        return generateThreeDSurfaceArea(random);
    }

    private static Question generateThreeDSurfaceArea(Random random) {
        switch (random.nextInt(5)) {
            case 0: {
                int radius = number(random, 2, 12);
                return numericQuestion(
                        Question.Dimension.THREE_D,
                        Question.Figure.SPHERE,
                        "Sphere surface area",
                        "A sphere has radius " + radius + " cm. Using pi approx 3.14, what is its surface area?",
                        4 * PI * radius * radius,
                        "cm2",
                        Collections.singletonList("r=" + radius),
                        "3d-surface-sphere-" + radius,
                        random
                );
            }
            case 1: {
                int radius = number(random, 2, 10);
                int height = number(random, 4, 24);
                return numericQuestion(
                        Question.Dimension.THREE_D,
                        Question.Figure.CYLINDER,
                        "Cylinder surface area",
                        "A closed cylinder has radius " + radius + " cm and height " + height + " cm. Using pi approx 3.14, what is its surface area?",
                        2 * PI * radius * (radius + height),
                        "cm2",
                        Arrays.asList("r=" + radius, "h=" + height),
                        "3d-surface-cylinder-" + radius + "-" + height,
                        random
                );
            }
            case 2: {
                int side = number(random, 3, 18);
                return numericQuestion(
                        Question.Dimension.THREE_D,
                        Question.Figure.CUBE,
                        "Cube surface area",
                        "A cube has side length " + side + " cm. What is its surface area?",
                        6 * side * side,
                        "cm2",
                        Collections.singletonList("s=" + side),
                        "3d-surface-cube-" + side,
                        random
                );
            }
            case 3: {
                int length = number(random, 3, 18);
                int width = number(random, 3, 16);
                int height = number(random, 3, 14);
                return numericQuestion(
                        Question.Dimension.THREE_D,
                        Question.Figure.RECTANGULAR_PRISM,
                        "Rectangular prism surface area",
                        "A rectangular prism is " + length + " cm by " + width + " cm by " + height + " cm. What is its surface area?",
                        2 * (length * width + length * height + width * height),
                        "cm2",
                        Arrays.asList("l=" + length, "w=" + width, "h=" + height),
                        "3d-surface-prism-" + length + "-" + width + "-" + height,
                        random
                );
            }
            default: {
                int radius = number(random, 2, 10);
                int slantHeight = number(random, radius + 2, radius + 16);
                return numericQuestion(
                        Question.Dimension.THREE_D,
                        Question.Figure.CONE,
                        "Cone surface area",
                        "A cone has radius " + radius + " cm and slant height " + slantHeight + " cm. Using pi approx 3.14, what is its total surface area?",
                        PI * radius * (radius + slantHeight),
                        "cm2",
                        Arrays.asList("r=" + radius, "l=" + slantHeight),
                        "3d-surface-cone-" + radius + "-" + slantHeight,
                        random
                );
            }
        }
    }

    private static Question generateThreeDFormula(Random random) {
        switch (random.nextInt(5)) {
            case 0:
                return formulaQuestion(Question.Dimension.THREE_D, Question.Figure.SPHERE, "Sphere surface area formula", "Which formula gives the surface area of a sphere?", "SA = 4 x pi x r x r", "SA = pi x r x r", "SA = 2 x pi x r", Collections.singletonList("r"));
            case 1:
                return formulaQuestion(Question.Dimension.THREE_D, Question.Figure.CYLINDER, "Cylinder surface area formula", "Which formula gives the surface area of a closed cylinder?", "SA = 2 x pi x r x (r + h)", "SA = pi x r x r x h", "SA = 2 x pi x r", Arrays.asList("r", "h"));
            case 2:
                return formulaQuestion(Question.Dimension.THREE_D, Question.Figure.CUBE, "Cube surface area formula", "Which formula gives the surface area of a cube?", "SA = 6 x s x s", "SA = s x s x s", "SA = 4 x s", Collections.singletonList("s"));
            case 3:
                return formulaQuestion(Question.Dimension.THREE_D, Question.Figure.RECTANGULAR_PRISM, "Rectangular prism surface area formula", "Which formula gives the surface area of a rectangular prism?", "SA = 2 x (lw + lh + wh)", "SA = l x w x h", "SA = 2 x (l + w + h)", Arrays.asList("l", "w", "h"));
            default:
                return formulaQuestion(Question.Dimension.THREE_D, Question.Figure.CONE, "Cone surface area formula", "Which formula gives the total surface area of a cone?", "SA = pi x r x (r + l)", "SA = pi x r x r x h", "SA = 2 x pi x r", Arrays.asList("r", "l"));
        }
    }

    private static Question regularPolygonAreaQuestion(Question.Figure figure, String name, int sides, Random random) {
        int sideLength = number(random, 3, 18);
        int apothem = number(random, 3, 14);
        double area = sides * sideLength * apothem / 2.0;
        return numericQuestion(
                Question.Dimension.TWO_D,
                figure,
                name + " area",
                "A regular " + name.toLowerCase(Locale.US) + " has side length " + sideLength + " cm and apothem " + apothem + " cm. What is its area?",
                area,
                "cm2",
                Arrays.asList("s=" + sideLength, "a=" + apothem),
                "2d-area-regular-" + name + "-" + sideLength + "-" + apothem,
                random
        );
    }

    private static Question regularPolygonPerimeterQuestion(Question.Figure figure, String name, int sides, Random random) {
        int sideLength = number(random, 3, 24);
        return numericQuestion(
                Question.Dimension.TWO_D,
                figure,
                name + " perimeter",
                "A regular " + name.toLowerCase(Locale.US) + " has side length " + sideLength + " cm. What is its perimeter?",
                sides * sideLength,
                "cm",
                Collections.singletonList("s=" + sideLength),
                "2d-perimeter-regular-" + name + "-" + sideLength,
                random
        );
    }

    private static Question formulaQuestion(
            Question.Dimension dimension,
            Question.Figure figure,
            String title,
            String prompt,
            String correctAnswer,
            String wrongAnswerOne,
            String wrongAnswerTwo,
            List<String> labels
    ) {
        return new Question(
                dimension,
                figure,
                title,
                prompt,
                correctAnswer,
                wrongAnswerOne,
                wrongAnswerTwo,
                labels,
                "formula-" + title + "-" + correctAnswer
        );
    }

    private static Question numericQuestion(
            Question.Dimension dimension,
            Question.Figure figure,
            String title,
            String prompt,
            double correctValue,
            String unit,
            List<String> labels,
            String key,
            Random random
    ) {
        List<String> answers = numericAnswers(correctValue, unit, random);
        return new Question(
                dimension,
                figure,
                title,
                prompt,
                answers.get(0),
                answers.get(1),
                answers.get(2),
                labels,
                key
        );
    }

    private static List<String> numericAnswers(double correctValue, String unit, Random random) {
        Set<String> answers = new HashSet<>();
        String correct = answerText(correctValue, unit);
        answers.add(correct);

        double[] candidates = new double[]{
                correctValue * 2,
                correctValue / 2,
                correctValue + number(random, 3, 18),
                Math.max(1, correctValue - number(random, 2, 12)),
                correctValue + number(random, 20, 45)
        };
        for (double candidate : candidates) {
            answers.add(answerText(candidate, unit));
            if (answers.size() == 3) {
                break;
            }
        }

        int nudge = 1;
        while (answers.size() < 3) {
            answers.add(answerText(correctValue + nudge, unit));
            nudge++;
        }

        List<String> ordered = new ArrayList<>(answers);
        ordered.remove(correct);
        ordered.add(0, correct);
        return ordered;
    }

    private static String answerText(double value, String unit) {
        String formatted = format(value);
        return unit.isEmpty() ? formatted : formatted + " " + unit;
    }

    private static String format(double value) {
        if (Math.abs(value - Math.round(value)) < 0.001) {
            return String.valueOf(Math.round(value));
        }
        return String.format(Locale.US, "%.2f", value).replaceAll("0+$", "").replaceAll("\\.$", "");
    }

    private static int number(Random random, int minInclusive, int maxInclusive) {
        return minInclusive + random.nextInt(maxInclusive - minInclusive + 1);
    }

    private static int evenNumber(Random random, int minInclusive, int maxInclusive) {
        int value = number(random, minInclusive, maxInclusive);
        return value % 2 == 0 ? value : value + 1;
    }
}
