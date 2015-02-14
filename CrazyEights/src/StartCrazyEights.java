import controller.CrazyEightsController;
import view.StartUI;

public class StartCrazyEights {
    public static void main(String[] args) {
        CrazyEightsController controller = new CrazyEightsController();
        new StartUI(controller);
    }
}
