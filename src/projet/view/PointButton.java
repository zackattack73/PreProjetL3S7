package projet.view;

import javafx.scene.control.Button;
import projet.model.Point;

public class PointButton extends Button {
    private Point point;

    public PointButton(String text, Point point) {
        super(text);
        this.point = point;
    }

    public Point getPoint() {
        return this.point;
    }
}
