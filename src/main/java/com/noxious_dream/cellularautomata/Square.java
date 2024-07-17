package com.noxious_dream.cellularautomata;

import javafx.scene.canvas.GraphicsContext;

public class Square extends Shape {

    public Square(double x, double y, int state) {
        super(x, y, state);
    }

    public static int Count_Neighbors(Shape[][] grid, int index_x, int index_y) {
        int result = 0;
        int[][] neighbors =
                {
                        {index_x + 1, index_y},
                        {index_x - 1, index_y},
                        {index_x, index_y + 1},
                        {index_x, index_y - 1},
                        {index_x + 1, index_y - 1},
                        {index_x - 1, index_y - 1},
                        {index_x - 1, index_y + 1},
                        {index_x + 1, index_y + 1}
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
                x + radius,
                x + radius,
                x - radius,
                x - radius,
        };
        double[] yPoints = new double[]{
                y + radius,
                y - radius,
                y - radius,
                y + radius,
        };

        gc.fillPolygon(xPoints, yPoints, 4);
        if (state_of_window == 0) {
            gc.strokePolygon(xPoints, yPoints, 4);
        }
    }

    @Override
    public boolean IsInside(double x_mouse, double y_mouse) {
        return Math.abs(x_mouse - x) <= radius && Math.abs(y_mouse - y) <= radius;
    }

}

