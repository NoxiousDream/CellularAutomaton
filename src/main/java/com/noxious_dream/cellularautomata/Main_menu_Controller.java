package com.noxious_dream.cellularautomata;

import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class Main_menu_Controller {

    // Objects on the main_menu screen
    public TextField G; // Generations
    public TextField B; // Birth rules
    public TextField S; // Survival rules
    public TextField Rad_s;
    public TextField X_Count;
    public TextField Y_Count;
    public Slider slider_percent;
    public Button Gen_Percent;
    public Label B_Label;
    public Label S_Label;
    public MenuButton Menu;

    int Gens = 2;
    int[] B_List = new int[]{2};
    int[] S_List = new int[]{3, 4};
    int X = 60;
    int Y = 30;
    public static Renderer win;
    private int Radius = 10;
    private int percent = 50;
    Shapes_Type type = Shapes_Type.Hexagon;

    private int B_S_Max = 6;

    public void Radius_p_pressed() {
        if (Radius < 15)
            Radius++;
        Rad_s.setText("" + Radius);
    }

    public void Radius_m_pressed() {
        if (Radius > 1)
            Radius--;
        Rad_s.setText("" + Radius);
    }

    public void GenerateEmpty() {
        if (win == null)
            win = new Renderer(Radius, X, Y, B_List, S_List, Gens, 0, type);
    }

    public void GeneratePercent() {
        if (win == null)
            win = new Renderer(Radius, X, Y, B_List, S_List, Gens, percent, type);
    }

    public void PercentageChanged() {
        percent = (int) slider_percent.getValue();
        Gen_Percent.setText("With " + percent + "% fullness");
    }

    public void Set_Rad_By_Text(KeyEvent actionEvent) {
        if (actionEvent.getCode() == KeyCode.ENTER || actionEvent.getCode() == KeyCode.TAB) {
            try {
                Radius = Math.max(Math.min(Integer.parseInt(Rad_s.getText()), 15), 1);
            } catch (Exception _) {
            }
            Rad_s.setText("" + Radius);
        }
    }

    public void GetX(KeyEvent actionEvent) {
        if (actionEvent.getCode() == KeyCode.ENTER || actionEvent.getCode() == KeyCode.TAB) {
            try {
                X = Integer.parseInt(X_Count.getText());
            } catch (Exception _) {}
            X = Math.max(1, Math.min(1000, X));
            X_Count.setText("" + X);
        }
    }

    public void GetY(KeyEvent actionEvent) {
        if (actionEvent.getCode() == KeyCode.ENTER || actionEvent.getCode() == KeyCode.TAB) {
            try {
                Y = Integer.parseInt(Y_Count.getText());
            } catch (Exception _) {}
            Y = Math.max(1, Math.min(1000, Y));
            Y_Count.setText("" + Y);
        }
    }

    public void GetB()  {
        ArrayList<Integer> list = new ArrayList<>();
        String str = B.getText();
        for (int i = 0; i <= str.length(); i++) {
            try {
                int curr = Integer.parseInt(String.valueOf(str.charAt(i)));
                if (curr >= 0 && curr <= B_S_Max && !list.contains(curr))
                    list.add(curr);
            } catch (Exception _) {
            }
        }
        StringBuilder str_for_label = new StringBuilder();
        B_List = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            B_List[i] = list.get(i);
            str_for_label.append(list.get(i)).append(", ");
        }
        if (str_for_label.toString().endsWith(", "))
            str_for_label.delete(str_for_label.length() - 2, str_for_label.length());
        B_Label.setText("= " + str_for_label);
    }

    public void GetS() {
        ArrayList<Integer> list = new ArrayList<>();
        String str = S.getText();
        for (int i = 0; i < str.length(); i++) {
            try {
                int curr = Integer.parseInt(str.substring(i, i + 1));
                if (curr >= 0 && curr <= B_S_Max && !list.contains(curr))
                    list.add(curr);
            } catch (Exception _) {
            }
        }

        StringBuilder str_for_label = new StringBuilder();
        S_List = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            S_List[i] = list.get(i);
            str_for_label.append(list.get(i)).append(", ");
        }
        if (str_for_label.toString().endsWith(", "))
            str_for_label.delete(str_for_label.length() - 2, str_for_label.length());
        S_Label.setText("= " + str_for_label);
    }

    public void GetG(KeyEvent actionEvent) {
        if (actionEvent.getCode() == KeyCode.ENTER || actionEvent.getCode() == KeyCode.TAB) {
            String str = G.getText();
            try {
                Gens = Math.max(2, Math.min(Integer.parseInt(str), 20));
            } catch (Exception _) {
            }
            G.setText("" + Gens);
        }
    }

    static public void close() {
        win = null;
    }

    public void Pressed_Hexagon() {
        Menu.setText("Hexagon");
        B_S_Max = 6;
        type = Shapes_Type.Hexagon;
    }

    public void Pressed_Square() {
        Menu.setText("Square");
        B_S_Max = 8;
        type = Shapes_Type.Square;
    }
}
