package utsyn;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import modell.*;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

/**
 * @author Peter Boer, Daniel Moberg og Abdella Haji
 *
 */
public class StigeOgSlange extends JFrame implements ChangeListener {

	private static final long serialVersionUID = -8676592650951494887L;
	private JPanel contentPane;
	private static JLabel[][] lbl_Ruter;
	private JButton btnNewButton;
	private static JTextField textField;
	static JLabel handlingLabel;
	static JLabel lblBrikker[];
	static Timer timer = null;
	static Spiller vinner = null;
	private static Spill spill;

	/**
	 * main methode
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					StigeOgSlange frame = new StigeOgSlange();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * konstruktør.
	 */
	public StigeOgSlange() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 539, 650);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnNewButton = new JButton("Start");
		btnNewButton.addActionListener(enter);
		btnNewButton.setBounds(421, 527, 89, 23);
		contentPane.add(btnNewButton);

		textField = new JTextField();
		textField.addActionListener(enter);
		textField.setBounds(10, 528, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		handlingLabel = new JLabel("tast inn antall spillere (fom 2 tom 4)");
		handlingLabel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		handlingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		handlingLabel.setBounds(45, 580, 436, 20);
		handlingLabel.setOpaque(true);
		contentPane.add(handlingLabel);

		/**************************
		 * tur_speed for timer inni i spill klasse
		 **********************************/
		JSlider tur_speed = new JSlider(JSlider.HORIZONTAL, 1, 10, 5);
		tur_speed.setName("");
		tur_speed.setToolTipText("juster fart for neste spill-kj\u00F8ring ");
		tur_speed.setPaintTicks(true);
		tur_speed.setBounds(106, 542, 305, 31);
		tur_speed.addChangeListener(this);

		tur_speed.setMajorTickSpacing(10);
		tur_speed.setMinorTickSpacing(1);
		tur_speed.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		Font font = new Font("Serif", Font.ITALIC, 12);
		tur_speed.setFont(font);
		contentPane.add(tur_speed);
		/************************************************************/

		// 10X10 tabell med jlabel som representerer ruter
		lbl_Ruter = new JLabel[10][10];

		// 4 label som representerer brikker
		lblBrikker = new JLabel[4];

		// lager nye ruter
		lagRuter_Label();

		// vanlig tabell begynner fra topp-venstre men vi vil at tabellen
		// begynner fra bunn-venstre og itillegg hvert lbl_ruter[x][y] før samme
		// form som spillet
		setRuter_LabelPosisjon();

		// lager lblbrikker og setter dem i lblRuter[0][0]
		lagBrikker_Label();

		// jlabel for bakgrunn bildet
		JLabel thumb = new JLabel();
		thumb.setIcon(new ImageIcon(new ImageIcon("ressurs/spill.png").getImage().getScaledInstance(500, 500, Image.SCALE_DEFAULT)));
		
		/**tester om filen fins*/
		System.out.println(new File("ressurs/spill.png").exists());
		/***********************/
		
		thumb.setBounds(10, 11, 500, 500);
		contentPane.add(thumb);

		JLabel label = new JLabel("Juster hastighet");
		label.setOpaque(true);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBorder(null);
		label.setBounds(130, 522, 251, 20);
		contentPane.add(label);


	}// konstruktøren

	/** lag nye ruter */
	private void lagRuter_Label() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j <= 9; j++) {
				lbl_Ruter[i][j] = new JLabel();
				lbl_Ruter[i][j].setBorder(new LineBorder(new Color(0, 0, 0)));
				lbl_Ruter[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				lbl_Ruter[i][j].setOpaque(false);
				contentPane.add(lbl_Ruter[i][j]);
			}
		}

	}

	/**
	 * vanlig tabell begynner fra topp-venstre men vi vil at tabellen begynner
	 * fra bunn-venstre og itillegg hvert lbl_ruter[x][y] før samme form som
	 * spillet
	 */
	private void setRuter_LabelPosisjon() {
		for (int i = 0; i <= 9; i++) {

			if (i % 2 == 0) {
				// hvis rad nr er et partall
				for (int j = 0; j <= 9; j++) {
					lbl_Ruter[i][j].setBounds(10 + (j * 50), 11 + ((9 - i) * 50), 50, 50);
				}
			} else {
				// hvis rad nr er et oddetall
				for (int j = 9; j >= 0; j--) {
					lbl_Ruter[i][j].setBounds(10 + ((9 - j) * 50), 11 + ((9 - i) * 50), 50, 50);
				}
			}
		} // for
	}

	/** lager lblbrikker og setter dem i lblRuter[0][0] */
	private void lagBrikker_Label() {
		for (int i = 0; i < 4; i++) {
			lblBrikker[i] = new JLabel();
			lblBrikker[i].setBorder(new LineBorder(new Color(0, 0, 0)));
			lblBrikker[i].setHorizontalAlignment(SwingConstants.CENTER);
			lblBrikker[i].setIcon(new ImageIcon(new ImageIcon("ressurs/" + i + ".png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT)));

			lblBrikker[i].setOpaque(true);
			lblBrikker[i].setBounds(10, 11 + ((9) * 50), 25, 25);
			// setter alle brikker på 1. rute-posisjon
			Point justert_pnt = juster_lokasjon_pluss(i, lbl_Ruter[0][0].getLocation());
			lblBrikker[i].setLocation(justert_pnt);

			contentPane.add(lblBrikker[i]);

		}
	}

	

	/** timer er brukt kunn for reset */
	public static void iterate(Action name, int miliseconds) {
		timer = new Timer(miliseconds, name);
		timer.setInitialDelay(miliseconds);
		timer.setDelay(miliseconds);
		timer.setRepeats(false);
		timer.start();
	}

	/** reset etter spillet er ferdig */
	private static Action reset = new AbstractAction() {
		private static final long serialVersionUID = 7892201122490351698L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			lagBrikker_reset();
			handlingLabel.setText("tast inn antall spillere (fom 2 tom 4)");
			textField.setEnabled(true);
			textField.setText("");
			textField.requestFocus();

		}
	};

	/** setter brikkkere på start posisjon etter at spill er ferdig */
	private static void lagBrikker_reset() {
		for (int i = 0; i < 4; i++) {
			Point justert_pnt = juster_lokasjon_pluss(i, lbl_Ruter[0][0].getLocation());
			lblBrikker[i].setLocation(justert_pnt);

		}
	}

	/** Listen to the slider. justere timer inni i spill klasse */
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		if (!source.getValueIsAdjusting()) {// sjekker om bruker er ferdig med
											// justering
			spill.MILLISEKUND_TUR = (int) (1000 / source.getValue());
			System.out.println(spill.MILLISEKUND_TUR);
		}
	}

	/**
	 * plassering av brikker etter hvert logg/tur + oppdater handlingLabel +
	 * reset spillet
	 * 
	 * @param logg
	 *            logg før info om hvert tur/handling fra Logg klasse
	 */
	public static void oppdater_komponenter(Logg l) {
		if (lblBrikker != null) {
			Logg logg = l;
			int fraPos = logg.getFraRute() - 1; // legg merke til -1
			int x = fraPos / 10;
			int y = fraPos % 10;

			int i = 0;
			int ndx = -1;
			while (i < lblBrikker.length && ndx == -1) {
				Point justert_pnt = juster_lokasjon_minus(i, lblBrikker[i].getLocation());
				if (justert_pnt.equals(lbl_Ruter[x][y].getLocation())) 
					ndx = i;
				i++;
			}
			int tilPos = logg.getTilRute() - 1; // legg merke til -1
			x = tilPos / 10;
			y = tilPos % 10;
			Point justert_pnt = juster_lokasjon_pluss(ndx, lbl_Ruter[x][y].getLocation());
			lblBrikker[ndx].setLocation(justert_pnt);

			handlingLabel.setText(logg.getHandling());
			if (handlingLabel.getText().contains("har vunnet")) {
				// reset om 3 sekunder etter spillet er ferdig
				iterate(reset, 3000);
			}
		}
	}

	/**
	 * starter spillet
	 */
	Action enter = new AbstractAction() {
		private static final long serialVersionUID = -1659365131717518851L;

		@Override
		public void actionPerformed(ActionEvent e) {
			try {

				int antallspillere = Integer.valueOf(textField.getText());
				spill =  new Spill(antallspillere);
 				spill.startSpill_timer();

				textField.setText("");
				textField.setEnabled(false);

			} catch (NumberFormatException e2) {
				JOptionPane.showMessageDialog(null, "Ulovlig input: " + e2.getMessage()+"\ntast inn antall spillere (fom 2 tom 4)");
				textField.setText("");
			}

		}
	};

	// hjelpe metoder

	/**
	 * justere brikere posisjon slik at de ikke ligger opp hverandre
	 * 
	 * @param int
	 *            i er spiller NDX
	 * @param Point
	 *            point
	 */
	public static Point juster_lokasjon_pluss(int i, Point point) {
		int leggTilX = 0;
		int leggTilY = 0;

		if (i == 1)
			leggTilX = 25;
		else if (i == 2) {
			leggTilY = 25;
		} else if (i == 3) {
			leggTilX = 25;
			leggTilY = 25;
		}
		int x = (int) point.getX() + leggTilX;
		int y = (int) point.getY() + leggTilY;

		Point nyPoint = new Point(x, y);

		return nyPoint;
	}

	/**
	 * justere brikere posisjon slik at de ikke ligger opp hverandre
	 * 
	 * @param int
	 *            i er spiller NDX
	 * @param Point
	 *            point
	 */
	public static Point juster_lokasjon_minus(int i, Point point) {
		int leggTilX = 0;
		int leggTilY = 0;

		if (i == 1)
			leggTilX = -25;
		else if (i == 2) {
			leggTilY = -25;
		} else if (i == 3) {
			leggTilX = -25;
			leggTilY = -25;
		}

		int x = (int) point.getX() + leggTilX;
		int y = (int) point.getY() + leggTilY;

		Point nyPoint = new Point(x, y);

		return nyPoint;
	}
}
