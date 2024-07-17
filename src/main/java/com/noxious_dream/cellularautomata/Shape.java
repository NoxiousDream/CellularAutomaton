package com.noxious_dream.cellularautomata;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {
    static double radius;
    protected double x, y;
    protected int state;
    protected int next_state;
    protected static Color[] colors = new Color[]{
            Color.color(1, 1, 1), Color.color(0, 0, 0), Color.color(0, 0.1, 0),
            Color.color(0, 0.2, 0), Color.color(0, 0.3, 0), Color.color(0, 0.4, 0),
            Color.color(0, 0.5, 0), Color.color(0, 0.6, 0), Color.color(0, 0.7, 0),
            Color.color(0, 0.8, 0), Color.color(0, 0.9, 0), Color.color(0.1, 1, 0.1),
            Color.color(0.2, 1, 0.2), Color.color(0.3, 1, 0.3), Color.color(0.4, 1, 0.4),
            Color.color(0.5, 1, 0.5), Color.color(0.6, 1, 0.6), Color.color(0.7, 1, 0.7),
            Color.color(0.8, 1, 0.8), Color.color(0.9, 1, 0.9)};

    public Shape(double x, double y, int state) {
        this.x = x;
        this.y = y;
        this.state = state;
        next_state = 0;
    }

    public int GetState() {
        return state;
    }

    public void Update() {
        state = next_state;
    }

    public void ChangeState(boolean right_now, int states) {
        if (right_now)
            state = (state + 1) % states;
        else
            next_state = (state + 1) % states;
    }

    void Draw(GraphicsContext gc, int state_of_window, int states) {
        gc.setLineWidth(1);

        if (state == 0) {
            gc.setFill(colors[0]);
            if (state_of_window == 0)
                gc.setStroke(colors[1]);
            else
                gc.setStroke(colors[0]);
        } else if (state == 1) {
            gc.setFill(colors[1]);
            gc.setStroke(colors[1]);
        } else {
            gc.setFill(colors[(int) Math.floor(state * 19.0 / states)]);
            gc.setStroke(colors[(int) Math.floor(state * 19.0 / states)]);
        }
    }

    abstract boolean IsInside(double x_mouse, double y_mouse);
}
