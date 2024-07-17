package com.noxious_dream.cellularautomata;

import javafx.scene.canvas.GraphicsContext;


public class Hexagon extends Shape {

    public Hexagon(double x, double y, int state) {
        super(x, y, state);
    }

    public static int Count_Neighbors(Shape[][] grid, int index_x, int index_y) {
        int result = 0;
        int[][] neighbors =
                {
                        {index_x + index_y % 2, index_y + 1},
                        {index_x + index_y % 2, index_y - 1},
                        {index_x + 1, index_y},
                        {index_x - 1, index_y},
                        {index_x - 1 + index_y % 2, index_y + 1},
                        {index_x - 1 + index_y % 2, index_y - 1},
                };

        for (int[] neighbor : neighbors) {
            try {
                if (grid[neighbor[0]][neighbor[1]].GetState() == 1) {
                    result++;
                }
            } catch (IndexOutOfBoundsException _) {
            }
        }

        return result;
    }

    @Override
    public void Draw(GraphicsContext gc, int state_of_window, int states) {
        super.Draw(gc, state_of_window, states);
        double[] xPoints = new double[]{
                x,
                x - radius * Math.sqrt(0.75),
                x - radius * Math.sqrt(0.75),
                x,
                x + radius * Math.sqrt(0.75),
                x + radius * Math.sqrt(0.75),
        };
        double[] yPoints = new double[]{
                y - radius,
                y - radius * 0.5,
                y + radius * 0.5,
                y + radius,
                y + radius * 0.5,
                y - radius * 0.5,
        };

        gc.fillPolygon(xPoints, yPoints, 6);
        if (state_of_window == 0) {
            gc.strokePolygon(xPoints, yPoints, 6);
        }
    }

    @Override
    public boolean IsInside(double X_mouse, double Y_mouse) {

        boolean right = (X_mouse <= this.x + radius * Math.sqrt(0.75));
        boolean left = (X_mouse >= this.x - radius * Math.sqrt(0.75));
        boolean down_right = (Math.abs(Y_mouse - y) >= Math.abs(X_mouse - x) / Math.sqrt(3) - radius * 0.9);
        boolean top_right = (Math.abs(Y_mouse - y) <= -Math.abs(X_mouse - x) / Math.sqrt(3) + radius * 0.9);
        boolean top_left = (Math.abs(Y_mouse - y) <= Math.abs(X_mouse - x) / Math.sqrt(3) + radius * 0.9);
        boolean down_left = (Math.abs(Y_mouse - y) >= -Math.abs(X_mouse - x) / Math.sqrt(3) - radius * 0.9);

        return right && left && top_left && top_right && down_left && down_right;

    }
}