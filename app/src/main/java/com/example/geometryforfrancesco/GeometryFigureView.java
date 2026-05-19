package com.example.geometryforfrancesco;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.Collections;
import java.util.List;

public final class GeometryFigureView extends View {
    private final Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint guidePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Question.Figure figure = Question.Figure.TRIANGLE;
    private List<String> labels = Collections.emptyList();

    public GeometryFigureView(Context context) {
        super(context);
        init();
    }

    public GeometryFigureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    void setQuestion(Question question) {
        this.figure = question.figure;
        this.labels = question.figureLabels;
        invalidate();
    }

    private void init() {
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(Color.rgb(221, 235, 255));

        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(5f);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setStrokeJoin(Paint.Join.ROUND);
        linePaint.setColor(Color.rgb(31, 85, 208));

        guidePaint.setStyle(Paint.Style.STROKE);
        guidePaint.setStrokeWidth(3f);
        guidePaint.setStrokeCap(Paint.Cap.ROUND);
        guidePaint.setColor(Color.rgb(92, 105, 130));

        textPaint.setColor(Color.rgb(36, 42, 56));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(31f);
        textPaint.setFakeBoldText(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.rgb(247, 248, 252));
        switch (figure) {
            case RIGHT_TRIANGLE:
                drawRightTriangle(canvas);
                break;
            case ISOSCELES_TRIANGLE:
                drawIsoscelesTriangle(canvas);
                break;
            case EQUILATERAL_TRIANGLE:
                drawEquilateralTriangle(canvas);
                break;
            case SQUARE:
                drawSquare(canvas);
                break;
            case RECTANGLE:
                drawRectangle(canvas);
                break;
            case CIRCLE:
                drawCircle(canvas);
                break;
            case TRAPEZOID:
                drawTrapezoid(canvas);
                break;
            case PARALLELOGRAM:
                drawParallelogram(canvas);
                break;
            case PENTAGON:
                drawRegularPolygon(canvas, 5);
                break;
            case HEXAGON:
                drawRegularPolygon(canvas, 6);
                break;
            case OCTAGON:
                drawRegularPolygon(canvas, 8);
                break;
            case SPHERE:
                drawSphere(canvas);
                break;
            case CYLINDER:
                drawCylinder(canvas);
                break;
            case CUBE:
                drawCube(canvas);
                break;
            case RECTANGULAR_PRISM:
                drawRectangularPrism(canvas);
                break;
            case CONE:
                drawCone(canvas);
                break;
            case TRIANGLE:
            default:
                drawTriangle(canvas);
                break;
        }
    }

    private void drawTriangle(Canvas canvas) {
        float w = getWidth();
        float h = getHeight();
        Path path = new Path();
        path.moveTo(w * 0.50f, h * 0.16f);
        path.lineTo(w * 0.18f, h * 0.78f);
        path.lineTo(w * 0.82f, h * 0.78f);
        path.close();
        canvas.drawPath(path, fillPaint);
        canvas.drawPath(path, linePaint);
        canvas.drawLine(w * 0.50f, h * 0.16f, w * 0.50f, h * 0.78f, guidePaint);
        drawLabel(canvas, label(0, "base"), w * 0.50f, h * 0.91f);
        drawLabel(canvas, label(1, "height"), w * 0.64f, h * 0.49f);
        drawOptionalLabel(canvas, 2, w * 0.26f, h * 0.46f);
    }

    private void drawRightTriangle(Canvas canvas) {
        float w = getWidth();
        float h = getHeight();
        Path path = new Path();
        path.moveTo(w * 0.25f, h * 0.18f);
        path.lineTo(w * 0.25f, h * 0.78f);
        path.lineTo(w * 0.80f, h * 0.78f);
        path.close();
        canvas.drawPath(path, fillPaint);
        canvas.drawPath(path, linePaint);
        canvas.drawRect(new RectF(w * 0.25f, h * 0.67f, w * 0.36f, h * 0.78f), guidePaint);
        drawLabel(canvas, label(0, "a"), w * 0.16f, h * 0.50f);
        drawLabel(canvas, label(1, "b"), w * 0.54f, h * 0.91f);
        drawOptionalLabel(canvas, 2, w * 0.59f, h * 0.46f);
    }

    private void drawIsoscelesTriangle(Canvas canvas) {
        float w = getWidth();
        float h = getHeight();
        Path path = new Path();
        path.moveTo(w * 0.50f, h * 0.14f);
        path.lineTo(w * 0.20f, h * 0.80f);
        path.lineTo(w * 0.80f, h * 0.80f);
        path.close();
        canvas.drawPath(path, fillPaint);
        canvas.drawPath(path, linePaint);
        canvas.drawLine(w * 0.50f, h * 0.14f, w * 0.50f, h * 0.80f, guidePaint);
        drawLabel(canvas, label(0, "side"), w * 0.24f, h * 0.48f);
        drawLabel(canvas, label(1, "base"), w * 0.50f, h * 0.93f);
        drawOptionalLabel(canvas, 2, w * 0.62f, h * 0.48f);
    }

    private void drawEquilateralTriangle(Canvas canvas) {
        float w = getWidth();
        float h = getHeight();
        Path path = new Path();
        path.moveTo(w * 0.50f, h * 0.14f);
        path.lineTo(w * 0.20f, h * 0.80f);
        path.lineTo(w * 0.80f, h * 0.80f);
        path.close();
        canvas.drawPath(path, fillPaint);
        canvas.drawPath(path, linePaint);
        drawLabel(canvas, label(0, "s"), w * 0.50f, h * 0.93f);
    }

    private void drawSquare(Canvas canvas) {
        RectF rect = centeredRect(0.25f, 0.18f, 0.75f, 0.78f);
        canvas.drawRect(rect, fillPaint);
        canvas.drawRect(rect, linePaint);
        drawLabel(canvas, label(0, "side"), getWidth() * 0.50f, getHeight() * 0.91f);
    }

    private void drawRectangle(Canvas canvas) {
        RectF rect = centeredRect(0.15f, 0.26f, 0.85f, 0.70f);
        canvas.drawRect(rect, fillPaint);
        canvas.drawRect(rect, linePaint);
        drawLabel(canvas, label(0, "length"), getWidth() * 0.50f, getHeight() * 0.84f);
        drawLabel(canvas, label(1, "width"), getWidth() * 0.91f, getHeight() * 0.50f);
    }

    private void drawCircle(Canvas canvas) {
        float cx = getWidth() * 0.50f;
        float cy = getHeight() * 0.50f;
        float radius = Math.min(getWidth(), getHeight()) * 0.30f;
        canvas.drawCircle(cx, cy, radius, fillPaint);
        canvas.drawCircle(cx, cy, radius, linePaint);
        canvas.drawLine(cx, cy, cx + radius, cy, guidePaint);
        drawLabel(canvas, label(0, "r"), cx + radius * 0.55f, cy - 18f);
    }

    private void drawTrapezoid(Canvas canvas) {
        float w = getWidth();
        float h = getHeight();
        Path path = new Path();
        path.moveTo(w * 0.32f, h * 0.22f);
        path.lineTo(w * 0.68f, h * 0.22f);
        path.lineTo(w * 0.84f, h * 0.76f);
        path.lineTo(w * 0.16f, h * 0.76f);
        path.close();
        canvas.drawPath(path, fillPaint);
        canvas.drawPath(path, linePaint);
        canvas.drawLine(w * 0.32f, h * 0.22f, w * 0.32f, h * 0.76f, guidePaint);
        drawLabel(canvas, label(0, "base1"), w * 0.50f, h * 0.16f);
        drawLabel(canvas, label(1, "base2"), w * 0.50f, h * 0.90f);
        drawOptionalLabel(canvas, 2, w * 0.39f, h * 0.52f);
    }

    private void drawParallelogram(Canvas canvas) {
        float w = getWidth();
        float h = getHeight();
        Path path = new Path();
        path.moveTo(w * 0.30f, h * 0.23f);
        path.lineTo(w * 0.78f, h * 0.23f);
        path.lineTo(w * 0.62f, h * 0.76f);
        path.lineTo(w * 0.14f, h * 0.76f);
        path.close();
        canvas.drawPath(path, fillPaint);
        canvas.drawPath(path, linePaint);
        canvas.drawLine(w * 0.30f, h * 0.23f, w * 0.30f, h * 0.76f, guidePaint);
        drawLabel(canvas, label(0, "base"), w * 0.38f, h * 0.90f);
        drawLabel(canvas, label(1, "height"), w * 0.42f, h * 0.52f);
    }

    private void drawRegularPolygon(Canvas canvas, int sides) {
        float w = getWidth();
        float h = getHeight();
        float cx = w * 0.50f;
        float cy = h * 0.48f;
        float radius = Math.min(w, h) * 0.31f;
        Path path = new Path();
        for (int i = 0; i < sides; i++) {
            double angle = -Math.PI / 2 + i * 2 * Math.PI / sides;
            float x = cx + (float) Math.cos(angle) * radius;
            float y = cy + (float) Math.sin(angle) * radius;
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        path.close();
        canvas.drawPath(path, fillPaint);
        canvas.drawPath(path, linePaint);
        canvas.drawLine(cx, cy, cx, cy - radius * 0.75f, guidePaint);
        drawLabel(canvas, label(0, "s"), cx, h * 0.91f);
        drawOptionalLabel(canvas, 1, cx + radius * 0.54f, cy - radius * 0.30f);
    }

    private void drawSphere(Canvas canvas) {
        float cx = getWidth() * 0.50f;
        float cy = getHeight() * 0.50f;
        float radius = Math.min(getWidth(), getHeight()) * 0.30f;
        canvas.drawCircle(cx, cy, radius, fillPaint);
        canvas.drawCircle(cx, cy, radius, linePaint);
        canvas.drawOval(new RectF(cx - radius, cy - radius * 0.28f, cx + radius, cy + radius * 0.28f), guidePaint);
        canvas.drawLine(cx, cy, cx + radius, cy, guidePaint);
        drawLabel(canvas, label(0, "r"), cx + radius * 0.55f, cy - 18f);
    }

    private void drawCylinder(Canvas canvas) {
        float w = getWidth();
        float h = getHeight();
        RectF top = new RectF(w * 0.24f, h * 0.18f, w * 0.76f, h * 0.36f);
        RectF bottom = new RectF(w * 0.24f, h * 0.62f, w * 0.76f, h * 0.80f);
        canvas.drawOval(top, fillPaint);
        canvas.drawLine(w * 0.24f, h * 0.27f, w * 0.24f, h * 0.71f, linePaint);
        canvas.drawLine(w * 0.76f, h * 0.27f, w * 0.76f, h * 0.71f, linePaint);
        canvas.drawOval(top, linePaint);
        canvas.drawOval(bottom, linePaint);
        drawLabel(canvas, label(0, "r"), w * 0.50f, h * 0.27f);
        drawOptionalLabel(canvas, 1, w * 0.88f, h * 0.54f);
    }

    private void drawCube(Canvas canvas) {
        drawBox(canvas, label(0, "s"), false);
    }

    private void drawRectangularPrism(Canvas canvas) {
        drawBox(canvas, joinLabels("l x w x h"), true);
    }

    private void drawBox(Canvas canvas, String label, boolean wide) {
        float w = getWidth();
        float h = getHeight();
        float left = wide ? w * 0.20f : w * 0.28f;
        float top = h * 0.34f;
        float right = wide ? w * 0.68f : w * 0.66f;
        float bottom = h * 0.75f;
        float dx = w * 0.12f;
        float dy = -h * 0.16f;

        RectF front = new RectF(left, top, right, bottom);
        canvas.drawRect(front, fillPaint);
        canvas.drawRect(front, linePaint);
        canvas.drawLine(left, top, left + dx, top + dy, linePaint);
        canvas.drawLine(right, top, right + dx, top + dy, linePaint);
        canvas.drawLine(right, bottom, right + dx, bottom + dy, linePaint);
        canvas.drawLine(left + dx, top + dy, right + dx, top + dy, linePaint);
        canvas.drawLine(right + dx, top + dy, right + dx, bottom + dy, linePaint);
        canvas.drawLine(right + dx, bottom + dy, right, bottom, linePaint);
        drawLabel(canvas, label, w * 0.50f, h * 0.91f);
    }

    private void drawCone(Canvas canvas) {
        float w = getWidth();
        float h = getHeight();
        Path path = new Path();
        path.moveTo(w * 0.50f, h * 0.14f);
        path.lineTo(w * 0.22f, h * 0.76f);
        path.lineTo(w * 0.78f, h * 0.76f);
        path.close();
        canvas.drawPath(path, fillPaint);
        canvas.drawPath(path, linePaint);
        canvas.drawOval(new RectF(w * 0.22f, h * 0.67f, w * 0.78f, h * 0.85f), linePaint);
        canvas.drawLine(w * 0.50f, h * 0.14f, w * 0.78f, h * 0.76f, guidePaint);
        drawLabel(canvas, label(0, "r"), w * 0.50f, h * 0.82f);
        drawOptionalLabel(canvas, 1, w * 0.70f, h * 0.44f);
    }

    private RectF centeredRect(float left, float top, float right, float bottom) {
        return new RectF(getWidth() * left, getHeight() * top, getWidth() * right, getHeight() * bottom);
    }

    private String label(int index, String fallback) {
        if (index >= 0 && index < labels.size()) {
            return labels.get(index);
        }
        return fallback;
    }

    private String joinLabels(String fallback) {
        if (labels.isEmpty()) {
            return fallback;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < labels.size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(labels.get(i));
        }
        return builder.toString();
    }

    private void drawOptionalLabel(Canvas canvas, int index, float x, float y) {
        if (index >= 0 && index < labels.size()) {
            drawLabel(canvas, labels.get(index), x, y);
        }
    }

    private void drawLabel(Canvas canvas, String text, float x, float y) {
        canvas.drawText(text, x, y, textPaint);
    }
}
