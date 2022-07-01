package is.minicad.shapes.specificcommand;

import is.minicad.command.Command;
import is.minicad.shapes.model.GraphicObject;

import java.awt.geom.Point2D;

public class MoveCommand implements Command {

	Point2D oldPos;

	Point2D newPos;

	GraphicObject object;
	
	public MoveCommand(GraphicObject go, Point2D pos) {
		oldPos = go.getPosition();
		newPos = pos;
		this.object = go;
	}

	@Override
	public boolean doIt() {
		object.moveTo(newPos);
		System.out.println("Oggetto spostato");
		return true;
	}

	@Override
	public boolean undoIt() {
		object.moveTo(oldPos);
		System.out.println("Oggetto riportato alla posizione precedente");
		return true;
	}

}
