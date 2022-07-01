package is.minicad.command;

public interface Command {
	boolean doIt();

	boolean undoIt();
}
