package is.minicad.shapes.specificcommand;

import is.minicad.command.Command;
import is.minicad.editor.EditorIF;
import is.minicad.shapes.model.GraphicObject;
import is.minicad.shapes.view.GraphicObjectPanel;

public class RemoveCommand implements Command {
    private  EditorIF editor;
    private GraphicObjectPanel panel;
    private GraphicObject go;
    private int id;

    public RemoveCommand(int id, EditorIF editor, GraphicObjectPanel panel){
        this.id = id;
        this.editor = editor;
        this.panel = panel;
        this.go = editor.getOggetti().get(id);
    }

    @Override
    public boolean doIt() {
        editor.getOggetti().set(id, null);
        panel.remove(go);
        System.out.println("Rimuovo la figura");
        return true;
    }

    @Override
    public boolean undoIt() {
        editor.getOggetti().set(id, go);
        //go.moveTo(go.getPosition().getX(), go.getPosition().getY());
        panel.add(go);
        System.out.println("Ridisegno la figura");
        return true;
    }

}
