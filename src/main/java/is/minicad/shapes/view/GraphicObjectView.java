package is.minicad.shapes.view;

import is.minicad.shapes.model.GraphicObject;

import java.awt.Graphics2D;

public interface GraphicObjectView {
	void drawGraphicObject(GraphicObject go, Graphics2D g);
}
