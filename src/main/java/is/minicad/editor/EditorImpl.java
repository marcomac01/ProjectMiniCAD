package is.minicad.editor;
import is.minicad.command.HistoryCommandHandler;
import is.minicad.shapes.model.GraphicObject;
import is.minicad.shapes.specificcommand.*;
import is.minicad.shapes.view.GraphicObjectPanel;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class EditorImpl implements EditorIF {
    private GraphicObjectPanel gpanel;
    private HistoryCommandHandler handler;
    private ArrayList<GraphicObject> listaOggetti;
    private ArrayList<ArrayList<GraphicObject>> listaGruppi;

    public EditorImpl(HistoryCommandHandler handler, GraphicObjectPanel gpanel){
        this.gpanel = gpanel;
        this.handler = handler;
        this.listaGruppi = new ArrayList<ArrayList<GraphicObject>>();
        this.listaOggetti = new ArrayList<GraphicObject>();
    }

    @Override
    public int create(GraphicObject go) {
        NewObjectCmd comando;
            for(int i = 0; i< listaOggetti.size(); i++)
                if(listaOggetti.get(i) == null ) {
                    listaOggetti.set(i, go);
                    comando = new NewObjectCmd(gpanel, go);
                    comando.setIDEditor(i, this);
                    return i;
                }
            listaOggetti.add(go);
            comando = (new NewObjectCmd(gpanel, go));
            comando.setIDEditor(listaOggetti.size()-1, this);
            handler.handle(comando);
            return listaOggetti.size()-1;
        }
    @Override
    public void rem(int id, boolean selettoreGruppo) {
        if(!selettoreGruppo) handler.handle(new RemoveCommand(id, this, gpanel));
        else handler.handle(new RemoveGroupCommand(gpanel, id, this));
        }


    @Override
    public void move(int id, Point2D punto, boolean selettoreGruppo){
        if(selettoreGruppo) handler.handle(new MoveGroupCommand(listaGruppi.get(id), punto));
        else handler.handle(new MoveCommand(listaOggetti.get(id), punto));
    }
    @Override
    public void moveOffset(int id, Point2D punto, boolean selettoreGruppo){
        if(selettoreGruppo) handler.handle(new MoveGroupOffsetCommand(listaGruppi.get(id), punto));
        else {
            GraphicObject o = listaOggetti.get(id);
            Point2D finale = new Point2D.Double(o.getPosition().getX()+punto.getX(), o.getPosition().getY()+punto.getY());
            handler.handle(new MoveCommand(listaOggetti.get(id), finale));
        }
    }
    @Override
    public void scale(int id, double factor, boolean selettoreGruppo){
        if(selettoreGruppo) handler.handle(new ZoomGroupCommand(listaGruppi.get(id), factor));
        else handler.handle(new ZoomCommand(listaOggetti.get(id), factor));
    }


    @Override
    public void print(int id, boolean selettoreGruppo) {
        if(selettoreGruppo){
            System.out.println("Il gruppo con ID"  + id + " contiene gli oggetti:");
            for (GraphicObject o : listaGruppi.get(id)) System.out.println(getStringFromObject(o));
        } else System.out.println(getStringFromObject(listaOggetti.get(id)));
    }
    @Override
    public void print(String type) {
        for(int i = 0; i < listaOggetti.size(); i++)
            if (listaOggetti.get(i).getType().equals(type))
                System.out.println(getStringFromObject(listaOggetti.get(i))+" - ID: "+i);
    }
    @Override
    public void print() {
        for(int i = 0; i < listaOggetti.size(); i++)
                System.out.println(getStringFromObject(listaOggetti.get(i))+" - ID: "+i);
    }
    @Override
    public void printGroups() {
        for(int i =0; i <listaGruppi.size(); i++){
            if(listaGruppi.get(i) == null) continue;
            print(i,true);
        }
    }


    @Override
    public double getArea(int id, boolean selettoreGruppo) {
            double ret = 0;
            if(selettoreGruppo)
                for(GraphicObject o : listaGruppi.get(id)) ret += o.getArea();
            else{ret = listaOggetti.get(id).getArea();}
            return ret;
        }
    @Override
    public double getArea(String type) {
            double ret = 0;
            for (GraphicObject o : listaOggetti) if (o.getType().equals(type)) ret += o.getArea();
            return ret;
    }
    @Override
    public double getArea() {
        double ret = 0;
        for (GraphicObject o : listaOggetti) ret += o.getArea();
        return ret;
    }
    @Override
    public double getPerimeter(int id, boolean selettoreGruppo) {
        double ret = 0;
        if(selettoreGruppo)
            for(GraphicObject o : listaGruppi.get(id)) ret += o.getPerimeter();
        else{ret = listaOggetti.get(id).getPerimeter();}
        return ret;
    }
    @Override
    public double getPerimeter(String type) {
        double ret = 0;
        for (GraphicObject o : listaOggetti) if (o.getType().equals(type)) ret += o.getPerimeter();
        return ret;
    }
    @Override
    public double getPerimeter() {
        double ret = 0;
        for (GraphicObject o : listaOggetti) ret += o.getPerimeter();
        return ret;
    }


    @Override
    public int group(ArrayList<Integer> ids) {
        handler.handle(new GroupCommand(this, ids));
        return 0;
    }
    @Override
    public void ungroup(int id) {
        handler.handle(new UngroupCommand(this, id));
    }


    @Override
    public EditorIF dammiEditor(HistoryCommandHandler handler, GraphicObjectPanel gpanel) {
        return new EditorImpl(handler,gpanel);
    }

    public ArrayList<GraphicObject> getOggetti(){
        return listaOggetti;
    }

    public ArrayList<ArrayList<GraphicObject>> getGruppi(){
        return listaGruppi;
    }
    private static String getStringFromObject(GraphicObject obj) {
        return "Tipo oggetto: "+obj.getType() +
                " - Posizione: X"+obj.getPosition().getX()+" Y"+obj.getPosition().getY() +
                " - Dimensioni: " + "L"+obj.getDimension().getWidth()+" A"+ obj.getDimension().getHeight()+
                " - Perimetro: " + obj.getPerimeter()+
                " - Area: "+obj.getArea();
    }
}
