/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author houdebil
 */
public class IA {
    public Point jouer(ArrayList<Point> pts) {
        if (pts.isEmpty()) return null;
        Random r = new Random();
        return pts.get(r.nextInt(pts.size()));
    }
}
