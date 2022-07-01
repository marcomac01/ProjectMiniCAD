package is;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import is.minicad.command.HistoryCommandHandler;
import is.minicad.editor.EditorIF;
import is.minicad.editor.EditorImpl;
import is.minicad.stringcommand.StringCommandException;
import is.minicad.stringcommand.IstruzioneIF;
import is.minicad.stringcommand.IstruzioneIm;
import is.minicad.shapes.model.*;
import is.minicad.shapes.view.*;

public class MiniCADFacade {
	private EditorIF editor;
	private IstruzioneIF istruzione;
	private HistoryCommandHandler handler;
	private GraphicObjectPanel gpanel;
	private int x = -1;
	private int y = -1;

	public MiniCADFacade(int x, int y){
		this.x = x;
		this.y = y;
		this.handler = new HistoryCommandHandler();
		this.gpanel = new GraphicObjectPanel();
		this.istruzione = new IstruzioneIm();
		this.editor = new EditorImpl(handler,gpanel);
		this.istruzione.setEditor(this.editor,this.handler);
	}
	public MiniCADFacade(){
		this.handler = new HistoryCommandHandler();
		this.gpanel = new GraphicObjectPanel();
		this.editor = new EditorImpl(handler,gpanel);
		this.istruzione = new IstruzioneIm();
	}
	public void inizializza() throws StringCommandException {
		istruzione.setEditor(editor,handler);

		JFrame f = new JFrame();

		final HistoryCommandHandler handler = new HistoryCommandHandler();


		splashSetDimensions(gpanel, (x<=0 && y<=0));

		gpanel.installView(RectangleObject.class, new RectangleObjectView());
		gpanel.installView(CircleObject.class, new CircleObjectView());
		gpanel.installView(ImageObject.class, new ImageObjectView());

		f.add(new JScrollPane(gpanel), BorderLayout.CENTER);

		JPanel controlPanel = new JPanel(new FlowLayout());

		f.setTitle("MiniCAD");
		f.getContentPane().add(controlPanel, BorderLayout.SOUTH);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}

	public void comandoManuale(String comando) {
		try {
			istruzione.interpreta(comando);
		}
		catch (StringCommandException e) {
			System.out.println("errore interprete: "+e.getMessage());
		}
	}

	public void avviaShell() {
		System.out.println("Puoi immettere i tuoi comandi:");
		Scanner sc = new Scanner(System.in);
		while (true) {
			try{
			istruzione.interpreta(sc.nextLine());
			}
			catch (StringCommandException e) {
				System.out.println("errore interprete: "+ e.getMessage());
			}
		}
	}
	private void splashSetDimensions(GraphicObjectPanel gpanel, boolean flag){
		System.out.println(
				"__/\\\\\\\\____________/\\\\\\\\__________________________________/\\\\\\\\\\\\\\\\\\_____/\\\\\\\\\\\\\\\\\\_____/\\\\\\\\\\\\\\\\\\\\\\\\____        \n" +
				" _\\/\\\\\\\\\\\\________/\\\\\\\\\\\\_______________________________/\\\\\\////////____/\\\\\\\\\\\\\\\\\\\\\\\\\\__\\/\\\\\\////////\\\\\\__       \n" +
				"  _\\/\\\\\\//\\\\\\____/\\\\\\//\\\\\\__/\\\\\\________________/\\\\\\___/\\\\\\/____________/\\\\\\/////////\\\\\\_\\/\\\\\\______\\//\\\\\\_      \n" +
				"   _\\/\\\\\\\\///\\\\\\/\\\\\\/_\\/\\\\\\_\\///___/\\\\/\\\\\\\\\\\\___\\///___/\\\\\\_____________\\/\\\\\\_______\\/\\\\\\_\\/\\\\\\_______\\/\\\\\\_     \n" +
				"    _\\/\\\\\\__\\///\\\\\\/___\\/\\\\\\__/\\\\\\_\\/\\\\\\////\\\\\\___/\\\\\\_\\/\\\\\\_____________\\/\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\_\\/\\\\\\_______\\/\\\\\\_    \n" +
				"     _\\/\\\\\\____\\///_____\\/\\\\\\_\\/\\\\\\_\\/\\\\\\__\\//\\\\\\_\\/\\\\\\_\\//\\\\\\____________\\/\\\\\\/////////\\\\\\_\\/\\\\\\_______\\/\\\\\\_   \n" +
				"      _\\/\\\\\\_____________\\/\\\\\\_\\/\\\\\\_\\/\\\\\\___\\/\\\\\\_\\/\\\\\\__\\///\\\\\\__________\\/\\\\\\_______\\/\\\\\\_\\/\\\\\\_______/\\\\\\__  \n" +
				"       _\\/\\\\\\_____________\\/\\\\\\_\\/\\\\\\_\\/\\\\\\___\\/\\\\\\_\\/\\\\\\____\\////\\\\\\\\\\\\\\\\\\_\\/\\\\\\_______\\/\\\\\\_\\/\\\\\\\\\\\\\\\\\\\\\\\\/___ \n" +
				"        _\\///______________\\///__\\///__\\///____\\///__\\///________\\/////////__\\///________\\///__\\////////////_____\n" +
				"                                   Progetto Ing del Software Marco Macri' (MAT.220070)\n"+
				"I comani possibili sono riportati nel file di assegnazione della traccia.\n");
		if(flag){
			System.out.println("Prima di cominciare, immetti la dimensione della finestra del MiniCAD:");
			Scanner sc = new Scanner(System.in);
			System.out.print("Larghezza: ");
			int a = sc.nextInt();
			System.out.print("Altezza: ");
			int b = sc.nextInt();
			gpanel.setPreferredSize(new Dimension(a, b));
		} else gpanel.setPreferredSize(new Dimension(x, y));
	}

	public EditorIF getEditor() {
		return editor;
	}
}