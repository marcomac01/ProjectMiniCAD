package is.minicad.stringcommand;

import is.MiniCADFacade;
import is.minicad.command.HistoryCommandHandler;
import is.minicad.editor.EditorIF;
import is.minicad.shapes.model.CircleObject;
import is.minicad.shapes.model.ImageObject;
import is.minicad.shapes.model.RectangleObject;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class IstruzioneIm implements IstruzioneIF {
    private static final String simboli = "(), " + '"';

    private StringTokenizer st;
    private EditorIF editor;
    private HistoryCommandHandler handler;

    public void setEditor(EditorIF editor, HistoryCommandHandler handler) {
        this.editor =  editor;
        this.handler = handler;
    }

    public void interpreta(String istruzione) throws StringCommandException {
        StringTokenizer st = new StringTokenizer(istruzione, simboli, false);
        String token;
        while(st.hasMoreTokens()){
            token = st.nextToken();
            String supporto;
            switch(token){
                case "create":
                    token = st.nextToken();
                    switch(token){
                        case "Circle":
                            double raggio = parseDouble(st.nextToken());
                            editor.create(new CircleObject(new Point.Double(parseDouble(st.nextToken()), parseDouble(st.nextToken())), raggio));
                            break;
                        case "Rectangle":
                            editor.create(new RectangleObject(new Point.Double(parseDouble(st.nextToken()), parseDouble(st.nextToken())), parseDouble(st.nextToken()),parseDouble(st.nextToken())));
                            break;
                        case "img":
                            editor.create(new ImageObject(new ImageIcon(MiniCADFacade.class.getResource(st.nextToken())), new Point2D.Double(parseDouble(st.nextToken()), parseDouble(st.nextToken()))));
                            break;
                    }; break;
                case "del":
                    supporto = st.nextToken();
                    if(supporto.contains("id")) editor.rem(parseInt(supporto.substring(2,supporto.length())), false);
                    else if(supporto.contains("g")) editor.rem(parseInt(supporto.substring(1,supporto.length())), true);
                    else {throw new IllegalArgumentException();} break;
                case "mv":
                    supporto = st.nextToken();
                    if(supporto.contains("id")) editor.move(parseInt(supporto.substring(2,supporto.length())), new Point.Double(parseDouble(st.nextToken()),parseDouble(st.nextToken())), false);
                    else if(supporto.contains("g")) editor.move(parseInt(supporto.substring(1,supporto.length())), new Point.Double(parseDouble(st.nextToken()),parseDouble(st.nextToken())), true);
                    else {throw new IllegalArgumentException();} break;
                case "mvoff":
                    supporto = st.nextToken();
                    if(supporto.contains("id")) editor.moveOffset(parseInt(supporto.substring(2,supporto.length())), new Point.Double(parseDouble(st.nextToken()),parseDouble(st.nextToken())), false);
                    else if(supporto.contains("g")) editor.moveOffset(parseInt(supporto.substring(1,supporto.length())), new Point.Double(parseDouble(st.nextToken()),parseDouble(st.nextToken())), true);
                    else {throw new IllegalArgumentException();} break;
                case "scale":
                    supporto = st.nextToken();
                    if(supporto.contains("id")) editor.scale(parseInt(supporto.substring(2,supporto.length())), parseDouble(st.nextToken()), false);
                    else if(supporto.contains("g")) editor.scale(parseInt(supporto.substring(1,supporto.length())), parseDouble(st.nextToken()), true);
                    else {throw new IllegalArgumentException();} break;
                case "ls":
                    token = st.nextToken();
                    if(token.equals("Circle")|token.equals("Rectangle")|token.equals("img")) editor.print(token);
                    else if(token.contains("id")) editor.print(parseInt(token.substring(2,token.length())), false);
                    else if(token.contains("g")) editor.print(parseInt(token.substring(1,token.length())), true);
                    else if(token.equals("groups")) editor.printGroups();
                    else if(token.equals("all")) editor.print();
                    break;
                case "area":
                    token = st.nextToken();
                    if(token.equals("Circle")|token.equals("Rectangle")|token.equals("img")) System.out.println(editor.getArea(token));
                    else if(token.contains("id")) editor.getArea(parseInt(token.substring(2,token.length())), false);
                    else if(token.contains("g")) editor.getArea(parseInt(token.substring(1,token.length())), true);
                    else if(token.equals("all")) editor.getArea();
                    break;
                case "perimeter":
                    supporto = st.nextToken();
                    if(token.equals("Circle")|token.equals("Rectangle")|token.equals("img")) editor.getArea(token);
                    else if(token.contains("id")) editor.getPerimeter(parseInt(supporto.substring(2,supporto.length())), false);
                    else if(token.contains("g")) editor.getPerimeter(parseInt(supporto.substring(1,supporto.length())), true);
                    else if(token.equals("all")) editor.getPerimeter();
                    break;
                case "grp":
                    ArrayList<Integer> ids = new ArrayList();
                    while (st.hasMoreTokens()) {
                        token = st.nextToken();
                        if(token.contains("id"))ids.add(parseInt(token.substring(2, token.length())));
                    }
                    if(ids.isEmpty()) throw new StringCommandException("Il gruppo sarebbe vuoto");
                    else editor.group(ids);
                    break;
                case "ungrp":
                    token = st.nextToken();
                    if(token.contains("g")) editor.ungroup(parseInt(token.substring(1,token.length())));
                    break;
                case "undo":
                    handler.handle(HistoryCommandHandler.NonExecutableCommands.UNDO);
                    break;
                case "redo":
                    handler.handle(HistoryCommandHandler.NonExecutableCommands.REDO);
                    break;
                default: throw new StringCommandException("Comando non valido");
            }
        }
    }
}
