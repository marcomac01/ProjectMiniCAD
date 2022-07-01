package is.minicad.shapes.specificcommand;

import is.minicad.command.Command;
import is.minicad.shapes.model.GraphicObject;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class MoveGroupOffsetCommand implements Command {

    private Point2D[] puntiVecchi;

    Point2D newPos;

    ArrayList<GraphicObject> gruppo;



    public MoveGroupOffsetCommand(ArrayList<GraphicObject> gruppo, Point2D pos) {
        newPos = pos;
        this.gruppo = gruppo;
        this.puntiVecchi = new Point2D[gruppo.size()];
        int i = 0;
        for(GraphicObject object : gruppo) {puntiVecchi[i] = object.getPosition(); i++;}
    }

    @Override
    public boolean doIt() {
        for(GraphicObject object : gruppo) {
            Point2D finale = new Point2D.Double(object.getPosition().getX()+newPos.getX(), object.getPosition().getY()+newPos.getY());
            object.moveTo(finale);
        }
        System.out.println("Gruppo spostato");
        return true;
    }
    public boolean undoIt(){
        for (int i = 0; i < gruppo.size(); i++) gruppo.get(i).moveTo(puntiVecchi[i]);
        System.out.println("Gruppo riportato alla posizione precedente");
        return true;
    }
}
