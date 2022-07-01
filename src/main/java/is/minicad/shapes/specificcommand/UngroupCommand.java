package is.minicad.shapes.specificcommand;

import is.minicad.command.Command;
import is.minicad.editor.EditorIF;
import is.minicad.shapes.model.GraphicObject;

import java.util.ArrayList;

public class UngroupCommand implements Command {
    private EditorIF editor;
    private int id;
    private boolean didIt;
    ArrayList<GraphicObject> gruppo;

    public UngroupCommand(EditorIF editor, int id){
        this.editor = editor;
        this.id = id;
        this.gruppo = editor.getGruppi().get(id);
    }

    @Override
    public boolean doIt() {
        editor.getGruppi().set(id, null);

        System.out.println("Separo il gruppo con ID " + id);
        return true;
    }

    @Override
    public boolean undoIt() {
        editor.getGruppi().set(id, gruppo);

        System.out.println("Ripristino il gruppo con ID " + id);
        return true;
    }
}
