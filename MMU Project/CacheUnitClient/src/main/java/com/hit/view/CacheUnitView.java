package com.hit.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Observable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.w3c.dom.Text;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JTree;
import javax.swing.JSpinner;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.UIManager;

public class CacheUnitView extends Observable implements View {

	private JTextArea text;
	private JTextArea text1;
	private JPanel contentPane;


	
	@Override
	public void start() {

		JFrame frame = new JFrame();
		frame.setResizable(false);
		
		frame.setTitle("Management Memory Unit");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 540, 500);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu loadMain = new JMenu("~ Load A Request ~");
		loadMain.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 17));
		menuBar.add(loadMain);
		
		JMenuItem get = new JMenuItem("Get");                         
		get.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 15));
		get.setForeground(Color.BLUE);
		loadMain.add(get);
		get.addActionListener(new CacheUnitPanel());

		JMenuItem update = new JMenuItem("Update");
		update.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 15));
		update.setForeground(new Color(20, 150, 20));
		loadMain.add(update);
		update.addActionListener(new CacheUnitPanel());
		

		JMenuItem delete = new JMenuItem("Delete");
		delete.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 15));
		delete.setForeground(Color.RED);
		loadMain.add(delete);
		
		update.addActionListener(new CacheUnitPanel());
		delete.addActionListener(new CacheUnitPanel());
		delete.addActionListener(new CacheUnitPanel());
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);


		text = new JTextArea();
		text.setSelectedTextColor(Color.WHITE);
		text.setForeground(Color.WHITE);
		text.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 20));
		text.setBounds(83, 131, 350, 220);
		text.setBackground(Color.DARK_GRAY);
		contentPane.add(text);

		text1 = new JTextArea();
		text1.setSelectedTextColor(Color.WHITE);
		text1.setForeground(Color.WHITE);
		text1.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 20));
		text1.setBounds(83, 131, 350, 220);
		text1.setBackground(Color.DARK_GRAY);
		text1.setRequestFocusEnabled(true);
		contentPane.add(text1);

		
		JLabel lblMMU = new JLabel("Memory Management Unit ");
		lblMMU.setForeground(new Color(0, 54, 108));
		lblMMU.setFont(new Font("Showcard Gothic", Font.BOLD, 30));
		lblMMU.setBounds(50, 1, 628, 68);
		contentPane.add(lblMMU);

		JLabel lblMadeBy = new JLabel("Made By Almog & Golan");
		lblMadeBy.setFont(new Font("Bauhaus 93", Font.BOLD, 18));
		lblMadeBy.setForeground(Color.BLACK);
		lblMadeBy.setBounds(10, 415, 513, 20);
		contentPane.add(lblMadeBy);

		JButton btnStatistics = new JButton("Show  Statistics");
		btnStatistics.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		btnStatistics.setActionCommand("stat");
		btnStatistics.setBounds(135, 60, 240, 60);
		btnStatistics.setIcon(new ImageIcon("Images/stat.png"));
		btnStatistics.addActionListener(new CacheUnitPanel());
		contentPane.add(btnStatistics);
		
		JLabel lblBG = new JLabel("");
		lblBG.setBounds(5, 0, 600, 500);
		lblBG.setIcon(new ImageIcon("Images/BG.jpg"));
		contentPane.add(lblBG);

		frame.setVisible(true);


	}

	@SuppressWarnings("serial")
	class CacheUnitPanel extends JPanel implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			BufferedReader br;
			try {
				if(e.getActionCommand()=="Get")
					br = new BufferedReader(new FileReader("src/main/resources/Get.txt"));
				else if (e.getActionCommand()=="Update")
					br = new BufferedReader(new FileReader("src/main/resources/Update.txt"));
				else 
					br = new BufferedReader(new FileReader("src/main/resources/Delete.txt"));


				StringBuilder sb = new StringBuilder();
				String line = br.readLine();

				while (line != null) {
					sb.append(line);
					sb.append("\n");
					line = br.readLine();
				}
				setChanged();
				notifyObservers(sb.toString());
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} 
			catch (IOException e1) {
				e1.printStackTrace();
			} 

			if (e.getActionCommand().equals("stat")) {String requestStat="{ \"headers\" :\r\n" + 
						                                "	{ \"action\" :\"STATISTICS\" },\r\n" + 
						                                "        \"body\" :\r\n" + 
					                                 	"	[{}]\r\n" + 
				                                 		"}";
				setChanged();
				notifyObservers(requestStat);
			}



		}
		}


	@Override
	public <T> void updateUIData(T t) {

		String arr[] = t.toString().split(",");
		text.setText("");
		for (String s : arr) {
			text.setText(text.getText() + "\n" + s);
		}
	}
}

