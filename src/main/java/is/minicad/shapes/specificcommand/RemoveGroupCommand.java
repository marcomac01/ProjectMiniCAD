package is.minicad.shapes.specificcommand;

import is.minicad.command.Command;
import is.minicad.editor.EditorIF;
import is.minicad.shapes.model.GraphicObject;
import is.minicad.shapes.view.GraphicObjectPanel;

import java.util.ArrayList;

public class RemoveGroupCommand implements Command {

    private GraphicObjectPanel panel;
    private EditorIF gok;
    private int id;
    private ArrayList<GraphicObject> gruppo;
    private int[] idOggTolti;
    
    public RemoveGroupCommand(GraphicObjectPanel panel, int id, EditorIF gok) {
        this.gok = gok;
        this.panel = panel;
        this.id = id;
        this.gruppo = gok.getGruppi().get(id);
        this.idOggTolti = new int[gruppo.size()];
    }

    @Override
    public boolean doIt() {
        gok.getGruppi().set(id, null);
        for (int i = 0; i < gruppo.size(); i++)
            for (int j = 0; j < gok.getOggetti().size(); j++)
                if(gruppo.get(i) ==  gok.getOggetti().get(j)){
                    idOggTolti[i] = j;
                    panel.remove(gok.getOggetti().get(j));
                    gok.getOggetti().set(j, null);
                }
        System.out.println("Cancello il gruppo con ID "+id);
        return true;
    }

    @Override
    public boolean undoIt() {
        gok.getGruppi().set(id,gruppo);
        for (int i = 0; i < gruppo.size(); i++) {
            gok.getOggetti().set(i,gruppo.get(i));
            panel.add(gruppo.get(i));
        }
        System.out.println("Ripristino il gruppo con ID "+id);
        return true;
    }
}
