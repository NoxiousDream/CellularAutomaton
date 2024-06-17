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

public class Renderer extends Stage {
    Hexagon[][] hexagons;
    GraphicsContext gc;
    Stage stage;
    int States_Of_Hexagons;
    int State_Of_Window = 0; // 0 — настройка, 1 — симуляция, 2 — подсчёт
    int X_count;
    int Y_count;
    double X_dist = 1.7;
    double Y_dist = 1.5;
    int[] B;
    int[] S;
    int Persentage;

    public Renderer(int radius, int xCount, int yCount, int[] B_input, int[] S_input, int states_Of_Hexagons, int percentage) {
        Hexagon.SetRadius(radius);
        X_count = xCount;
        Y_count = yCount;
        B = B_input;
        S = S_input;
        States_Of_Hexagons = states_Of_Hexagons;
        Persentage = percentage;
        start(new Stage());
    }

    private boolean Contains(int[] array, int value) {
        for (int elem : array) {
            if (elem == value) return true;
        }
        return false;
    }

    private void GenerateGrid(double chanse) {
        Random rand = new Random();

        for (int i = 0; i < Y_count; i++) {
            for (int j = 0; j < X_count; j++) {
                hexagons[j][i] = new Hexagon(
                        X_dist * j * Hexagon.Radius + (X_dist / 2) * (i % 2) * Hexagon.Radius + Hexagon.Radius * 2,
                        Y_dist * i * Hexagon.Radius + Hexagon.Radius * 2,
                        rand.nextFloat() > chanse ? 0 : 1);
            }
        }
    }

    public void start(Stage stage) {
        this.stage = stage;
        Group group = new Group();
        Canvas canvas = new Canvas(
                Hexagon.Radius * 4 + Hexagon.Radius * (X_count - 0.5) * X_dist,
                Hexagon.Radius * 4 + Hexagon.Radius * (Y_count - 1) * Y_dist);
        gc = canvas.getGraphicsContext2D();
        hexagons = new Hexagon[X_count][Y_count];
        group.getChildren().add(canvas);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(group));

        // Events
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            if (State_Of_Window != 0) return;
            for (int i = 0; i < X_count; i++) {
                for (int j = 0; j < Y_count; j++) {
                    Hexagon h = hexagons[i][j];
                    if (h.IsInside(e.getX(), e.getY())) {
                        h.ChangeState(true, States_Of_Hexagons);
                    }
                }

            }
            Render();
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
                                int active_neighbors = Hexagon.Count_Neighbors_For_Hexagons(hexagons, i, j); // подсчёт "живых" соседей
                                if (hexagons[i][j].getState() == 0) {
                                    if (Contains(B, active_neighbors)) {
                                        hexagons[i][j].ChangeState(false, States_Of_Hexagons);
                                    }
                                } else if (hexagons[i][j].getState() == 1) {
                                    if (!Contains(S, active_neighbors)) {
                                        hexagons[i][j].ChangeState(false, States_Of_Hexagons);
                                    }
                                } else {
                                    hexagons[i][j].ChangeState(false, States_Of_Hexagons);
                                }
                            }
                        }

                        for (int i = 0; i < X_count; i++) {
                            for (int j = 0; j < Y_count; j++) {
                                hexagons[i][j].Update();
                            }
                        }

                        Render();
                        State_Of_Window = 1;
                    }
                }
            }
        });


        GenerateGrid(Persentage / 100.0);

        Render();

        stage.show();
    }

    private void Render() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, stage.getWidth(), stage.getHeight());
        for (Hexagon[] row : hexagons) {
            for (Hexagon h : row)
                h.Draw(gc, State_Of_Window, States_Of_Hexagons);
        }
    }
}