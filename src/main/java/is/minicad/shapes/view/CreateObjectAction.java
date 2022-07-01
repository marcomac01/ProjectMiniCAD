package is.minicad.shapes.view;

import is.minicad.command.CommandHandler;
import is.minicad.shapes.model.AbstractGraphicObject;
import is.minicad.shapes.model.GraphicObject;
import is.minicad.shapes.specificcommand.NewObjectCmd;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class CreateObjectAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5399493440620423134L;
	AbstractGraphicObject prototype;
	GraphicObjectPanel panel;
	CommandHandler ch;

	public CreateObjectAction(AbstractGraphicObject prototype,
			GraphicObjectPanel panel, CommandHandler ch) {
		super();
		this.prototype = prototype;
		this.panel = panel;
		this.ch = ch;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		GraphicObject go = prototype.clone();
		ch.handle(new NewObjectCmd(panel, go));

	}

}
