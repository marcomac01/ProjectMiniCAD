package is.minicad.editor;

import is.minicad.command.HistoryCommandHandler;
import is.minicad.shapes.model.GraphicObject;
import is.minicad.shapes.view.GraphicObjectPanel;

import java.awt.geom.Point2D;
import java.util.ArrayList;

 public interface EditorIF {
    ArrayList<GraphicObject> getOggetti();
    ArrayList<ArrayList<GraphicObject>> getGruppi();


     int create(GraphicObject go);
     void rem(int id,  boolean selettoreGruppo);


     void move(int id, Point2D punto, boolean selettoreGruppo);
     void moveOffset(int id, Point2D punto, boolean selettoreGruppo);
     void scale(int id, double factor, boolean selettoreGruppo);


     int group(ArrayList<Integer> id);
     void ungroup(int id);
     EditorIF dammiEditor(HistoryCommandHandler handler, GraphicObjectPanel gpanel);


    //stampa
     void print(int id, boolean selettoreGruppo);
     void print(String type);
     void print();
     void printGroups();


    //visulizzazione dati
     double getArea(int id, boolean selettoreGruppo); // restituisce area dell'oggetto o la somma aree del gruppo
     double getArea(String type); // restituisce la somma aree degli oggetti di tipo type
     double getArea();// restituisce la somma aree di tutti gli oggetti

     double getPerimeter(int id, boolean selettoreGruppo);// restituisce perimetro dell'oggetto o la somma perimetri del gruppo
     double getPerimeter(String type); // restituisce la somma perimetri degli oggetti di tipo type
     double getPerimeter(); // restituisce la somma perimetri di tutti gli oggetti

}
