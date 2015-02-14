package view;

import controller.CrazyEightsController;

public class TestUI {
    public static void main(String[] args) {
        //new StartUI();
        CrazyEightsController controller = new CrazyEightsController();
        new StartUI(controller);
    }
}
