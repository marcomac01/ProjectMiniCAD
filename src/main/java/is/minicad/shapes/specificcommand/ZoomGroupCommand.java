package is.minicad.shapes.specificcommand;

import is.minicad.command.Command;
import is.minicad.shapes.model.GraphicObject;

import java.util.ArrayList;

public class ZoomGroupCommand implements Command {
    private ArrayList<GraphicObject> gruppo;
    private double factor;

    public ZoomGroupCommand(ArrayList<GraphicObject> gruppo, double factor) {
        this.gruppo = gruppo;
        this.factor = factor;

    }

    @Override
    public boolean doIt() {
        for(GraphicObject o : gruppo) o.scale(factor);
        System.out.println("Fattore di scala del gruppo mutato");
        return true;
    }

    @Override
    public boolean undoIt() {
        for(GraphicObject o : gruppo) o.scale(1.0 / factor);
        System.out.println("Fattore di scala del gruppo ripristinato");
        return true;
    }
}
