package com.noxious_dream.cellularautomata;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Random;

public class Renderer {
    Shape[][] grid;
    GraphicsContext gc;
    Stage stage;
    int States_Of_Shapes;
    int State_Of_Window = 0; // 0 — настройка, 1 — симуляция, 2 — подсчёт
    int X_count;
    int Y_count;
    double X_dist = 1.7;
    double Y_dist = 1.5;
    int[] B;
    int[] S;
    int Persentage;

    public Renderer(int radius, int xCount, int yCount, int[] B_input, int[] S_input, int States_of_Shapes,
                    int percentage, Shapes_Type type) {
        Shape.radius = radius;
        X_count = xCount;
        Y_count = yCount;
        B = B_input;
        S = S_input;
        this.States_Of_Shapes = States_of_Shapes;
        Persentage = percentage;

        stage = new Stage();
        Group group = new Group();
        Canvas canvas = switch (type) {
            case Hexagon -> {
                grid = new Hexagon[X_count][Y_count];
                yield new Canvas(
                        Shape.radius * 4 + Shape.radius * (X_count - 0.5) * X_dist,
                        Shape.radius * 4 + Shape.radius * (Y_count - 1) * Y_dist);
            }
            case Square -> {
                grid = new Square[X_count][Y_count];
                yield new Canvas(
                        Shape.radius * 2 * (X_count + 1),
                        Shape.radius * 2 * (Y_count + 1));
            }
        };

        gc = canvas.getGraphicsContext2D();
        group.getChildren().add(canvas);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(group));

        // Events
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            if (State_Of_Window != 0) return;
            for (int i = 0; i < X_count; i++) {
                for (int j = 0; j < Y_count; j++) {
                    Shape h = grid[i][j];
                    if (h.IsInside(e.getX(), e.getY())) {
                        h.ChangeState(true, States_Of_Shapes);
                        Render();
                        return;
                    }
                }
            }
        });
        stage.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            switch (e.getCode()) {
                case ESCAPE -> {
                    stage.close();
                    Main_menu_Controller.close();
                }
                case SPACE -> {
                    switch (State_Of_Window) {
                        case 0:
                            State_Of_Window = 1;
                            break;
                        case 1:
                            State_Of_Window = 0;
                            break;
                    }
                    Render();
                }
                case ENTER -> {
                    if (State_Of_Window == 1) {
                        State_Of_Window = 2;

                        for (int i = 0; i < X_count; i++) {
                            for (int j = 0; j < Y_count; j++) {

                                int active_neighbors = switch (type) {
                                    case Hexagon -> Hexagon.Count_Neighbors(grid, i, j);
                                    case Square -> Square.Count_Neighbors(grid, i, j);
                                };
                                if (grid[i][j].GetState() == 0) {
                                    if (Contains(B, active_neighbors)) {
                                        grid[i][j].ChangeState(false, States_Of_Shapes);
                                    }
                                } else if (grid[i][j].GetState() == 1) {
                                    if (!Contains(S, active_neighbors))
                                        grid[i][j].ChangeState(false, States_Of_Shapes);

                                } else if (States_Of_Shapes != 2) {
                                    grid[i][j].ChangeState(false, States_Of_Shapes);
                                }
                            }
                        }

                        // Grid update
                        for (int i = 0; i < X_count; i++) {
                            for (int j = 0; j < Y_count; j++) {
                                grid[i][j].Update();
                            }
                        }

                        Render();
                        State_Of_Window = 1;
                    }
                }
            }
        });


        GenerateGrid(Persentage / 100.0, type);

        Render();

        stage.show();
    }

    private boolean Contains(int[] array, int value) {
        for (int elem : array) {
            if (elem == value) return true;
        }
        return false;
    }

    private void GenerateGrid(double chanse, Shapes_Type type) {
        Random rand = new Random();

        for (int i = 0; i < Y_count; i++) {
            for (int j = 0; j < X_count; j++) {
                switch (type) {
                    case Hexagon -> grid[j][i] = new Hexagon(
                            X_dist * j * Shape.radius + (X_dist / 2) * (i % 2) * Shape.radius + Shape.radius * 2,
                            Y_dist * i * Shape.radius + Shape.radius * 2,
                            rand.nextFloat() > chanse ? 0 : 1);
                    case Square -> grid[j][i] = new Square(
                            j * Shape.radius * 2 + Shape.radius * 2,
                            i * Shape.radius * 2 + Shape.radius * 2,
                            rand.nextFloat() > chanse ? 0 : 1);
                }
            }
        }
    }

    private void Render() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, stage.getWidth(), stage.getHeight());
        for (Shape[] row : grid) {
            for (Shape s : row)
                s.Draw(gc, State_Of_Window, States_Of_Shapes);
        }
    }
}