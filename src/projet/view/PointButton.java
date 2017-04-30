package projet.view;

import javafx.scene.control.Button;
import projet.model.Point;

/**
 * Package ${PACKAGE} / Project PreProjetL3S7.
 * Date 2017 04 30.
 * Created by Nico (23:59).
 */
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
