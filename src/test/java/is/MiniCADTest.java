package is;

import is.minicad.stringcommand.StringCommandException;
import is.minicad.shapes.model.CircleObject;
import is.minicad.shapes.model.GraphicObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MiniCADTest {


    @Test
    void createObjectsAreaPErimeterTest() throws StringCommandException {
        MiniCADFacade minicad = new MiniCADFacade(400,400);
        minicad.inizializza();

        minicad.comandoManuale("create Rectangle (200.1,100.3) (40,50)");
        GraphicObject oggCreato = minicad.getEditor().getOggetti().get(0);
        assertEquals(oggCreato.getClass().getName(),"is.minicad.shapes.model.RectangleObject");
        assertTrue(oggCreato.getPosition().getX()==200.1 && oggCreato.getPosition().getY()==100.3);
        assertTrue(oggCreato.getDimension().getWidth() ==40 && oggCreato.getDimension().getHeight() ==50);
        minicad.comandoManuale("undo");
        assertFalse(minicad.getEditor().getOggetti().contains(oggCreato));
        minicad.comandoManuale("redo");
        assertSame(oggCreato, minicad.getEditor().getOggetti().get(0));

        assertEquals(40 * 50, oggCreato.getArea());
        assertEquals((40 + 50) * 2, oggCreato.getPerimeter());

        minicad.comandoManuale("create Circle (10.2) (100.1,100.3)");
        oggCreato = minicad.getEditor().getOggetti().get(1);
        assertEquals(oggCreato.getClass().getName(),"is.minicad.shapes.model.CircleObject");
        assertTrue(oggCreato.getPosition().getX()==100.1 && oggCreato.getPosition().getY()==100.3);
        CircleObject cerchio = (CircleObject) oggCreato;
        assertEquals(10.2, cerchio.getRadius());

        minicad.comandoManuale("create img (\"/duke2.png\") (200,200)\n");
        oggCreato = minicad.getEditor().getOggetti().get(2);
        assertEquals(oggCreato.getClass().getName(),"is.minicad.shapes.model.ImageObject");
        assertTrue(oggCreato.getPosition().getX()==200 && oggCreato.getPosition().getY()==200);
    }
    @Test
    void removeTest() throws StringCommandException {
        MiniCADFacade minicad = new MiniCADFacade(400,400);
        minicad.inizializza();

        minicad.comandoManuale("create Rectangle (200.1,100.3) (40,50)");
        GraphicObject oggCreato = minicad.getEditor().getOggetti().get(0);
        minicad.comandoManuale("del id0");
        assertNull(minicad.getEditor().getOggetti().get(0));

        minicad.comandoManuale("undo");
        assertEquals(oggCreato, minicad.getEditor().getOggetti().get(0));

        minicad.comandoManuale("redo");
        assertNull(minicad.getEditor().getOggetti().get(0));
    }


    @Test
    void movementsNoGroupTest() throws StringCommandException {
        MiniCADFacade minicad = new MiniCADFacade(400,400);
        minicad.inizializza();

        minicad.comandoManuale("create Rectangle (200.1,100.3) (40,50)");
        GraphicObject oggCreato = minicad.getEditor().getOggetti().get(0);

        minicad.comandoManuale("mv id0 (300,300)");
        assertTrue(oggCreato.getPosition().getX()==300 && oggCreato.getPosition().getY()==300);
        minicad.comandoManuale("undo");
        assertTrue(oggCreato.getPosition().getX()==200.1 && oggCreato.getPosition().getY()==100.3);

        minicad.comandoManuale("mvoff id0 (100,100)");
        assertTrue(oggCreato.getPosition().getX()==200.1+100 && oggCreato.getPosition().getY()==100.3+100);

    }

    @Test
    void groupUngroupTest() throws StringCommandException {
        MiniCADFacade minicad = new MiniCADFacade(400,400);
        minicad.inizializza();
        ArrayList<GraphicObject> gruppo = new ArrayList<GraphicObject>();

        minicad.comandoManuale("create Rectangle (200.1,100.3) (40,50)");
        minicad.comandoManuale("create img (\"/duke2.png\") (200,200)\n");
        gruppo.add(minicad.getEditor().getOggetti().get(0));
        gruppo.add(minicad.getEditor().getOggetti().get(1));

        minicad.comandoManuale("grp id0 id1");
        assertEquals(gruppo, minicad.getEditor().getGruppi().get(0));
        minicad.comandoManuale("undo");
        assertNull(minicad.getEditor().getGruppi().get(0));

        minicad.comandoManuale("redo");
        assertEquals(gruppo, minicad.getEditor().getGruppi().get(0));

        minicad.comandoManuale("ungrp g0");
        assertNull(minicad.getEditor().getGruppi().get(0));

        minicad.comandoManuale("undo");
        assertEquals(gruppo, minicad.getEditor().getGruppi().get(0));

        minicad.comandoManuale("redo");
        assertNull(minicad.getEditor().getGruppi().get(0));
    }

    @Test
    void movementsGroupTest() throws StringCommandException {
        MiniCADFacade minicad = new MiniCADFacade(400,400);
        minicad.inizializza();
        minicad.comandoManuale("create Rectangle (200.1,100.3) (40,50)");
        minicad.comandoManuale("create Rectangle (200.1,100.3) (60,50)");

        minicad.comandoManuale("grp id0 id1");
        minicad.comandoManuale("mv g0 (100,100)");
        for(GraphicObject o : minicad.getEditor().getGruppi().get(0))
            assertTrue((o.getPosition().getX()==100 && o.getPosition().getY()==100));

        minicad.comandoManuale("undo");
        assertTrue(minicad.getEditor().getOggetti().get(0).getPosition().getX() == 200.1 &&
                minicad.getEditor().getOggetti().get(0).getPosition().getY() ==100.3);
        assertTrue(minicad.getEditor().getOggetti().get(1).getPosition().getX() == 200.1 &&
                minicad.getEditor().getOggetti().get(1).getPosition().getY() ==100.3);

        minicad.comandoManuale("redo");
        for(GraphicObject o : minicad.getEditor().getGruppi().get(0))
            assertTrue((o.getPosition().getX()==100 && o.getPosition().getY()==100));

        minicad.comandoManuale("mvoff g0 (50,50)");
        for(GraphicObject o : minicad.getEditor().getGruppi().get(0))
            assertTrue((o.getPosition().getX()==150 && o.getPosition().getY()==150));

        minicad.comandoManuale("undo");
        for(GraphicObject o : minicad.getEditor().getGruppi().get(0))
            assertTrue((o.getPosition().getX()==100 && o.getPosition().getY()==100));
    }
    @Test
    void removeGroupTest() throws StringCommandException {
        MiniCADFacade minicad = new MiniCADFacade(400,400);
        minicad.inizializza();
        ArrayList<GraphicObject> gruppo;
        minicad.comandoManuale("create Rectangle (200.1,100.3) (40,50)");
        minicad.comandoManuale("create Rectangle (200.1,100.3) (60,50)");

        minicad.comandoManuale("grp id0 id1");
        gruppo = minicad.getEditor().getGruppi().get(0);
        minicad.comandoManuale("del g0");

        assertNull(minicad.getEditor().getGruppi().get(0));
        assertNull(minicad.getEditor().getOggetti().get(0));
        assertNull(minicad.getEditor().getOggetti().get(1));
        minicad.comandoManuale("undo");
        assertEquals(gruppo, minicad.getEditor().getGruppi().get(0));
        assertEquals(gruppo.get(0), minicad.getEditor().getOggetti().get(0));
        assertEquals(gruppo.get(1), minicad.getEditor().getOggetti().get(1));
    }

}