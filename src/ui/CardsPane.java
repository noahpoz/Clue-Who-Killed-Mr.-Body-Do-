package ui;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class CardsPane extends JPanel {

	private UI _ui;
	
	private ArrayList<String> _cards;
	private ArrayList<JButton> _buttons;
	private ArrayList<JButton> _selected;

	public CardsPane(UI ui) {

		_ui = ui;
		
		// so that we can arbitrarily place components (cards)
		this.setLayout(null);

		_cards = new ArrayList<String>();
		_buttons = new ArrayList<JButton>();

		_selected = new ArrayList<JButton>();
	}

	public void addCard(String s) {
		_cards.add(s);
	}

	public void removeAllCards() {
		_cards = new ArrayList<String>();
	}

	public void drawCards() {

		this.removeAll();

		int xOffset = 10;
		int yOffset = 0;
		int count = 0;
		int cardWidth = (this.getWidth() - 40) / 3;

		for (String s : _cards) {

			// draw card outline
			JButton button = new JButton(s);
			button.setSize(cardWidth, (int) (UI.WINDOW_HEIGHT / 6));
			button.setLocation(xOffset, yOffset);
			button.setOpaque(true);
			button.setBackground(Color.WHITE);
			button.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));

			this.add(button);

			xOffset += cardWidth + 10;
			count++;

			if (count % 3 == 0) {
				xOffset = 10;
				yOffset += (UI.WINDOW_HEIGHT / 6) + 10;
			}
		}
	}
}
