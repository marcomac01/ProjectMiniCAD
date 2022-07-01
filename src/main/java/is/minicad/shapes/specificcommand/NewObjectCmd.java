package is.minicad.shapes.specificcommand;

import is.minicad.command.Command;
import is.minicad.editor.EditorIF;
import is.minicad.shapes.model.GraphicObject;
import is.minicad.shapes.view.GraphicObjectPanel;


public class NewObjectCmd implements Command {
	private GraphicObjectPanel panel;
	private GraphicObject go;
	private int id;
	private EditorIF editor;

	public NewObjectCmd( GraphicObjectPanel panel, GraphicObject go) {
		this.panel = panel;
		this.go = go;
	}
	public void setIDEditor(int id, EditorIF editor) {
		this.editor = editor;
		this.id = id;
	}

	@Override
	public boolean doIt() {
		editor.getOggetti().set(id,go);
		panel.add(go);
		System.out.println("Disegno la figura di tipo "+go.getType()+" con ID " +id);
		return true;
	}

	@Override
	public boolean undoIt() {
		this.editor.getOggetti().set(id, null);
		panel.remove(go);
		System.out.println("Rimuovo la figura di tipo "+go.getType()+" con ID " +id);
		return true;
	}
}
