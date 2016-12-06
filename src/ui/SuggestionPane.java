package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class SuggestionPane extends JTextField {
	
	UI _ui;
	int _state = 0;
	ArrayList<String> _inputs = new ArrayList<String>();
	
	/**
	 * Necessary because inside an anon. inner class, 'this' will not refer to the SuggestionPane.
	 */
	SuggestionPane _self = this;
	
	public SuggestionPane(UI ui) {
		
		_ui = ui;
		this.setEnabled(false);
		
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String input = e.getActionCommand();
				
				switch (_state) {
				
					case 0:
						_state++;
						_self.setText("");
						_inputs.add(input);
						_ui.displayInstruction("Please enter the room.");
						break;
						
					case 1:
						_state++;
						_self.setText("");
						_inputs.add(input);
						_ui.displayInstruction("Please enter the player.");
						break;
						
					case 2:
						_state = 0;
						_self.setText("");
						_inputs.add(input);
						//ui.makeSuggestion
						_self.setEnabled(false);
						
						// submit the suggestion
						_ui.passSuggestion(_inputs);
					
						break;
				
				}
				
			}
		});
	}
	
	public void grabSuggestionInput() {
		_state = 0;
		_inputs = new ArrayList<String>();
		this.setEnabled(true);
		_ui.displayInstruction("Please enter the weapon.");
	}
}
