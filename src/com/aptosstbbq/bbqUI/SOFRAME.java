package com.aptosstbbq.bbqUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.aptosstbbq.bbqapp.menu.Ingredient;
import com.aptosstbbq.bbqapp.menu.Menu;
import com.aptosstbbq.bbqapp.web.WebIn;
import com.aptosstbbq.bbqapp.web.WebOut;

public class SOFRAME extends JFrame {

	private JPanel contentPane;
	static Menu bleh = new Menu();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SOFRAME frame = new SOFRAME();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SOFRAME() {
		bleh = Menu.fromJSON(new WebIn().read());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		Ingredient[] ings = new Ingredient[bleh.getIngredients().size()];
		bleh.getIngredients().toArray(ings);
		JButton[] buttons = new JButton[bleh.getIngredients().size()];
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton(ings[i].getName());
			buttons[i].setBackground(ings[i].isSoldOut() ? Color.RED : Color.GREEN);
			buttons[i].setContentAreaFilled(false);
			buttons[i].setOpaque(true);
			buttons[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					setSO((JButton) arg0.getSource());
					new WebOut(bleh.toJSON()).start();
					System.out.println(bleh.toString());
				}
			});
			contentPane.add(buttons[i]);
		}
	}
	// JButton btnNewButton = new ("New button");
			// btnNewButton.setBackground(Color.GREEN);
			// btnNewButton.setContentAreaFilled(false);
			// btnNewButton.setOpaque(true);

			// contentPane.add(btnNewButton);

			// JButton btnNewButton_1 = new JButton("New button");
			// btnNewButton_1.setBackground(Color.GREEN);
			// btnNewButton_1.setContentAreaFilled(false);
			// btnNewButton_1.setOpaque(true);
			// contentPane.add(btnNewButton_1);

	private void setSO(JButton b) {
		String check = b.getText();
		bleh.toggleSoldOut(check);
		if (bleh.getIngredient(check).isSoldOut()) {
			b.setBackground(Color.RED);
			b.setContentAreaFilled(false);
			b.setOpaque(true);
		} else {
			b.setBackground(Color.GREEN);
			b.setContentAreaFilled(false);
			b.setOpaque(true);
		}
		bleh.saveMenu();
	}
}
