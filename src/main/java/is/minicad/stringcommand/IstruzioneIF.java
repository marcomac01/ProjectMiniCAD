package is.minicad.stringcommand;

import is.minicad.command.HistoryCommandHandler;
import is.minicad.editor.EditorIF;

public interface IstruzioneIF {
     void interpreta(String istruzione) throws StringCommandException;
     void setEditor(EditorIF editor, HistoryCommandHandler handler);
}
