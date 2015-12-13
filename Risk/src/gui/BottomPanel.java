package gui;
import logic.*;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class BottomPanel extends JTabbedPane {

	
	private static final long serialVersionUID = 3783195947293878938L;
	private GameState gs;
	public BottomPanel(GameState gs){
		super();
		
		this.gs = gs;
		JComponent panel1 = makeTextPanel("Panel #1");
		this.addTab("Mensagens", panel1);
		
		JComponent panel2 = makeTextPanel("Panel #2");
		this.addTab("Aliancas", panel2);
		
		JComponent panel3 = makeTextPanel("Panel #3");
		this.addTab("Exercitos", panel3);
		
		
	}
	
	
	
	protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }
}