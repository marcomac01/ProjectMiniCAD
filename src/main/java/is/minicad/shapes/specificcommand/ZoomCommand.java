package is.minicad.shapes.specificcommand;

import is.minicad.command.Command;
import is.minicad.shapes.model.GraphicObject;

public class ZoomCommand implements Command {
	
	private GraphicObject object;
	private double factor;

	public ZoomCommand(GraphicObject obj, double factor) {
		object = obj;
		this.factor = factor;
		
	}

	@Override
	public boolean doIt() {
		object.scale(factor);
		System.out.println("Fattore di scala figura mutato");
		return true;
	}

	@Override
	public boolean undoIt() {
		object.scale(1.0 / factor);
		System.out.println("Fattore di scala figura ripristinato");
		return true;
	}

}
