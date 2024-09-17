import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Window extends JFrame {

	private JPanel contentPane;
	private JLabel lbSzum;
	private JLabel lbLevel;

	private JLabel[] lbDb = new JLabel[5];
	private int[] db = new int[5];
	
	private JLabel[][] lt = new JLabel[7][11];
	private int[][] t = new int[7][11];
	
	private String iconJel = "hdfrtHDFRTI";
	private String[] iconNev = { "home", "date", "facebook", "reddit", "table",
			"homeX", "dateX", "facebookX", "redditX", "tableX", "icecream" };
	private ImageIcon[] icon = new ImageIcon[11];
	
	private final int NOX = 5; // Ez alatt nem fagyasztott
	private final int ICE = 10;
	
	int kattN = 1, kattS = 0, kattO = 0;
	private int level = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
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
	public Window() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Window.class.getResource("/icons/icecream.png")));
		setTitle("IceCream");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel pnCenter = new JPanel();
		pnCenter.setPreferredSize(new Dimension(10+11*100+10, 10+7*100+10));
		pnCenter.setBorder(new LineBorder(Color.LIGHT_GRAY));
		pnCenter.setBackground(Color.WHITE);
		contentPane.add(pnCenter, BorderLayout.CENTER);
		pnCenter.setLayout(null);
		
		JPanel pnRight = new JPanel();
		pnRight.setBorder(new EmptyBorder(15, 5, 15, 5));
		contentPane.add(pnRight, BorderLayout.EAST);
		pnRight.setLayout(new BoxLayout(pnRight, BoxLayout.Y_AXIS));
		
		for (int i=0; i<5; i++) {
			lbDb[i] = new JLabel("0 db");
			lbDb[i].setIcon(new ImageIcon(Window.class.getResource("/icons/" + iconNev[i] + "32.png")));
			lbDb[i].setFont(new Font("Tahoma", Font.BOLD, 14));
			lbDb[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			lbDb[i].setBorder(new EmptyBorder(0, 0, 10, 0));
			pnRight.add(lbDb[i]);
		}
		
		JSeparator separator = new JSeparator();
		separator.setMaximumSize(new Dimension(32767, 1));
		pnRight.add(separator);
		
		lbSzum = new JLabel("0 db");
		lbSzum.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				for (int s=0; s<7; s++) {
					for (int o=0; o<11; o++) System.out.printf("%c", iconJel.charAt(t[s][o]));
					System.out.println();
				}
				System.out.println();
			}
		});
		lbSzum.setBorder(new EmptyBorder(10, 0, 0, 0));
		lbSzum.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbSzum.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnRight.add(lbSzum);
		
		Component verticalGlue = Box.createVerticalGlue();
		pnRight.add(verticalGlue);
		
		JLabel lbUp = new JLabel("");
		lbUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (level < 9) load(level + 1);
			}
		});
		lbUp.setIcon(new ImageIcon(Window.class.getResource("/icons/up16.png")));
		lbUp.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnRight.add(lbUp);
		
		lbLevel = new JLabel("Level 0");
		lbLevel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				random();
			}
		});
		lbLevel.setBorder(new EmptyBorder(2, 0, 2, 0));
		lbLevel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lbLevel.setFont(new Font("Tahoma", Font.BOLD, 14));
		pnRight.add(lbLevel);
		
		JLabel lbDown = new JLabel("");
		lbDown.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (level > 1) load(level - 1);
			}
		});
		lbDown.setIcon(new ImageIcon(Window.class.getResource("/icons/down16.png")));
		lbDown.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnRight.add(lbDown);

		
		for (int i=0; i<11; i++) {
			icon[i] = new ImageIcon(Window.class.getResource("/icons/" + iconNev[i] + ".png"));
		}
		
		for (int s=0; s<7; s++) for (int o=0; o<11; o++) {
			int ss = s, oo = o;
			lt[s][o] = new JLabel("");
			lt[s][o].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					lt[ss][oo].setBackground(Color.ORANGE);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					lt[ss][oo].setBackground(Color.WHITE);
				}
				@Override
				public void mousePressed(MouseEvent e) {
					katt(ss, oo);
				}
			});
			lt[s][o].setOpaque(true);
			lt[s][o].setBackground(Color.WHITE);
			lt[s][o].setBorder(new LineBorder(Color.WHITE));
			lt[s][o].setHorizontalAlignment(SwingConstants.CENTER);
			t[s][o] = (int)(Math.random()*11); if (t[s][o] < NOX) db[t[s][o]]++;
			lt[s][o].setIcon(icon[t[s][o]]);
			lt[s][o].setBounds(10+o*100, 10+s*100, 100, 100);
			pnCenter.add(lt[s][o]);
		}
		
		pack();
		load(1);
	}
	
	private void load(int x) {
		Scanner be = null;
		try {
			be = new Scanner(new File("/levels/level" + x +".txt"), "utf-8");
			for (int i=0; i<NOX; i++) db[i] = 0;
			for (int s=0; s<7; s++) {
				String sor = be.nextLine();
				for (int o=0; o<11; o++) {
					t[s][o] = iconJel.indexOf(sor.charAt(o));
					lt[s][o].setIcon(icon[t[s][o]]);
					if (t[s][o] < NOX) db[t[s][o]]++;
				}
			}
			level = x;
			dbKiir();
			lbLevel.setText("Level " + level);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(contentPane, e.toString(), "Computers", JOptionPane.ERROR_MESSAGE);
		} finally {
			if (be != null) be.close();
		}
	}
	
	private void dbKiir() {
		int szum = 0;
		for (int i=0; i<NOX; i++) { 
			lbDb[i].setText(String.format("%02d db", db[i]));
			szum += db[i];
		}
		lbSzum.setText(szum + " db");
		if (szum == 0) {
			JOptionPane.showMessageDialog(contentPane, "Everything is frozen", "IceCream", 0, icon[ICE]);
			if (level < 9) load(level + 1);
		}
	}
	
	private void katt(int s, int o) {
		if (kattN == 1 && t[s][o] == ICE) {
			lt[s][o].setBorder(new LineBorder(Color.RED));
			kattN = 2; kattS = s; kattO = o;
		} else if (kattN == 2 && t[s][o] < NOX) {
			db[t[s][o]]--;
			t[s][o] += 5; lt[s][o].setIcon(icon[t[s][o]]);
			t[kattS][kattO] = 100; lt[kattS][kattO].setIcon(null);
			lt[kattS][kattO].setBorder(new LineBorder(Color.WHITE));
			kattN = 1;
			dbKiir();
		}
	}
	
	private void random() {
		int szum = 77;
		for (int i=0; i<NOX; i++) db[i] = 0;
		for (int s=0; s<7; s++) for (int o=0; o<11; o++) t[s][o] = -1;
		for (int s=0; s<7; s++) for (int o=0; o<11; o++) {
			if (t[s][o] == -1) {
				if (szum > 1) t[s][o] = (int)(Math.random()*10); else t[s][o] = (int)(Math.random()*5+NOX);
				lt[s][o].setIcon(icon[t[s][o]]); szum--;
				if (t[s][o] < NOX) {
					db[t[s][o]]++;
					int vs,vo;
					do {
						vs = (int)(Math.random()*7);
						vo = (int)(Math.random()*11);
					} while (t[vs][vo] != -1);
					t[vs][vo] = ICE;
					lt[vs][vo].setIcon(icon[t[vs][vo]]); szum--;
				}
			}
		}
		lbLevel.setText("Level " + level + "*");
		dbKiir();
	}
}