import is.MiniCADFacade;
import is.minicad.stringcommand.StringCommandException;

public class Launcher {
    public static void main(String[] args) throws StringCommandException {
        MiniCADFacade minicad = new MiniCADFacade();
        minicad.inizializza();
        minicad.avviaShell();
    }
}
