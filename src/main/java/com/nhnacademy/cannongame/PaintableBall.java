package com.nhnacademy.cannongame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PaintableBall extends Ball {
    private Color color;

    public PaintableBall(double x, double y, double radius, Color color) {
        super(x, y, radius);

        if(color == null) {
            throw new IllegalArgumentException("색상은 Null No");
        }
        this.color = color;
    }

    public PaintableBall(double x, double y, double radius) {
        this(x, y, radius, Color.RED);
    }

    public Color getColor(){
        return color;
    }

    public void setColor(Color color){
        if(color == null) {
            throw new NullPointerException("색상은 Null No");
        }
        this.color = color;
    }

    public PaintableBall(Point point, double radius, Color color){
        super(point, radius);

        if(color == null) {
            throw new IllegalArgumentException("색상은 Null No");
        }
        this.color = color;

    }

    public PaintableBall(Point point, double radius){
        this(point, radius, Color.RED);
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(getCenter().getX() - getRadius(),
                   getCenter().getY() - getRadius(),
                   getRadius() * 2,
                   getRadius() * 2);

        gc.setStroke(Color.BLACK);
        gc.strokeOval(getCenter().getX() - getRadius(),
                getCenter().getY() - getRadius(),
                getRadius() * 2,
                getRadius() * 2);
    }
}
