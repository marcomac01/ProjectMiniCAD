package is.minicad.shapes.specificcommand;

import is.minicad.command.Command;
import is.minicad.editor.EditorIF;
import is.minicad.shapes.model.GraphicObject;

import java.util.ArrayList;

public class GroupCommand implements Command {
    private EditorIF gok;
    private int id;
    ArrayList<Integer> idOggetti;
    private ArrayList<GraphicObject> gruppo;
    private boolean didIt = false;

    public GroupCommand(EditorIF gok, ArrayList<Integer> idOggetti){
        this.gok = gok;
        this.idOggetti = idOggetti;
        this.gruppo = new ArrayList<GraphicObject>();
    }

    @Override
    public boolean doIt() {
        if (didIt) {gok.getGruppi().set(id, gruppo); return true;}
        for(Integer i : idOggetti)
            if(gok.getOggetti().get(i)!=null) gruppo.add(gok.getOggetti().get(i));
        for(int i =0; i< gok.getGruppi().size();i++)
            if(gok.getGruppi().get(i)==null) {
                gok.getGruppi().set(i, gruppo);
                this.id = i;
                System.out.println("Raggruppo gli oggetti con ID: "+i);
                didIt = true;
                return true;
            }
        gok.getGruppi().add(gruppo);
        this.id = gok.getGruppi().size()-1;
        System.out.println("Raggruppo gli oggetti con ID: "+ this.id);

        return true;
    }

    @Override
    public boolean undoIt() {
        didIt = !didIt;
        gok.getGruppi().set(id, null);
        System.out.println("Separo il gruppo con ID " + id);
        return true;
    }
}
