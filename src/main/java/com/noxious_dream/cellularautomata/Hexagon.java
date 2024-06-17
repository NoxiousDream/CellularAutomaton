package com.noxious_dream.cellularautomata;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Hexagon {

    static double Radius = 10;
    double X, Y;
    private int state, next_state = 0;


    // Constructors
    public Hexagon(double X, double Y) {
        this.X = X;
        this.Y = Y;
        state = 0;
    }

    public Hexagon(double X, double Y, int state) {
        this.X = X;
        this.Y = Y;
        this.state = state;
    }


    static void SetRadius(double radius_input) {
        Radius = radius_input;
    }

    public static int Count_Neighbors_For_Hexagons(Hexagon[][] hexagons, int index_x, int index_y) {
        int result = 0;
        int[][] neighbors = new int[][]{
                new int[]{index_x + index_y % 2, index_y + 1},
                new int[]{index_x + index_y % 2, index_y - 1},
                new int[]{index_x + 1, index_y},
                new int[]{index_x - 1, index_y},
                new int[]{index_x - 1 + index_y % 2, index_y + 1},
                new int[]{index_x - 1 + index_y % 2, index_y - 1},
        };

        int x, y;
        for (int i = 0; i < neighbors.length; i++) {
            x = neighbors[i][0];
            y = neighbors[i][1];
            try {
                if (hexagons[x][y].getState() == 1) {
                    result++;
                }
            } catch (IndexOutOfBoundsException _) {
            }
        }

        return result;
    }

    public int getState() {
        return state;
    }

    public void Draw(GraphicsContext gc, int state_of_window, int states) {
        Color[] colors = new Color[]{
                Color.color(1, 1, 1),
                Color.color(0, 0, 0),
                Color.color(0, 0.1, 0),
                Color.color(0, 0.2, 0),
                Color.color(0, 0.3, 0),
                Color.color(0, 0.4, 0),
                Color.color(0, 0.5, 0),
                Color.color(0, 0.6, 0),
                Color.color(0, 0.7, 0),
                Color.color(0, 0.8, 0),
                Color.color(0, 0.9, 0),
                Color.color(0.1, 1, 0.1),
                Color.color(0.2, 1, 0.2),
                Color.color(0.3, 1, 0.3),
                Color.color(0.4, 1, 0.4),
                Color.color(0.5, 1, 0.5),
                Color.color(0.6, 1, 0.6),
                Color.color(0.7, 1, 0.7),
                Color.color(0.8, 1, 0.8),
                Color.color(0.9, 1, 0.9)};


        gc.setLineWidth(1);
        Color st;
        if (state_of_window == 0) {
            st = Color.color(0, 0, 0);
        } else {
            st = Color.color(0.3, 0.3, 0.3);
        }
        //gc.setStroke(Color.color(0, 0, 0));

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
        double[] xPoints = new double[]{
                X,
                X - Radius * Math.sqrt(0.75),
                X - Radius * Math.sqrt(0.75),
                X,
                X + Radius * Math.sqrt(0.75),
                X + Radius * Math.sqrt(0.75),
        };
        double[] yPoints = new double[]{
                Y - Radius,
                Y - Radius * 0.5,
                Y + Radius * 0.5,
                Y + Radius,
                Y + Radius * 0.5,
                Y - Radius * 0.5,
        };

        gc.fillPolygon(xPoints, yPoints, 6);
        if (state_of_window == 0) {
            gc.strokePolygon(xPoints, yPoints, 6);
        }
    }

    public boolean IsInside(double X_mouse, double Y_mouse) {

        boolean right = (X_mouse <= this.X + Radius * Math.sqrt(0.75));
        boolean left = (X_mouse >= this.X - Radius * Math.sqrt(0.75));
        boolean down_right = (Math.abs(Y_mouse - Y) >= Math.abs(X_mouse - X) / Math.sqrt(3) - Radius * 0.9);
        boolean top_right = (Math.abs(Y_mouse - Y) <= -Math.abs(X_mouse - X) / Math.sqrt(3) + Radius * 0.9);
        boolean top_left = (Math.abs(Y_mouse - Y) <= Math.abs(X_mouse - X) / Math.sqrt(3) + Radius * 0.9);
        boolean down_left = (Math.abs(Y_mouse - Y) >= -Math.abs(X_mouse - X) / Math.sqrt(3) - Radius * 0.9);

        return right && left && top_left && top_right && down_left && down_right;

    }

    public void ChangeState(boolean right_now, int states) {
        if (right_now)
            state = (state + 1) % states;
        else
            next_state = (state + 1) % states;
    }

    public void ChangeState(int state, boolean right_now) {
        if (right_now)
            this.state = state;
        else
            next_state = state;
    }

    public void Update() {
        state = next_state;
    }
}