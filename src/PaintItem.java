import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PaintItem extends JPanel {
	int sizeW = 60;
	int sizeH = 60;
	int start = 5;
	Color chColor = Color.black;
	//array of boolean to make the pressed button diff from the other
	boolean[] ispressed;
	//is for the button of the fill, remove and move 
	boolean[] addPressed;
	JButton[] shap;
	// 0 JToggleButton ellip;
	// 1 Button circul;
	// 2 Button segment;
	// 3 Button line;
	// 4 Button tringle;
	// 5 Button rectangl;
	// 6 Button square;
	JButton color;
	JButton[] addbut;
	ActionListener lis, aLis, addLis;
	JLabel placeX, placeY;
//constructor of pant item panel where the left hand side of the screen button
	public PaintItem(int w, int l, int num) {
		addPressed = new boolean[3];
		addbut = new JButton[3];
		placeX = new JLabel("paint");
		placeY = new JLabel("paint");
		color = new JButton();
		setLayout(null);
		setBounds(0, 80, w, l);
		setBackground(Color.lightGray);
		//lis for the shapes button
		lis = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				for (int i = 0; i < ispressed.length; i++) {
					if (arg0.getSource() == shap[i]) {
						resetB(addPressed, addbut);
						resetB(ispressed, shap);
						shap[i].setBackground(Color.pink);
						ispressed[i] = true;

					}
				}
			}
		};
		//lis for color chooser 
		aLis = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JColorChooser jc = new JColorChooser();
				chColor = jc.showDialog(null, null, null);
			}
		};
		//listetner for the mode chooser
		addLis = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				for (int i = 0; i < 3; i++) {
					if (arg0.getSource() == addbut[i]) {
						resetB(ispressed, shap);
						resetB(addPressed, addbut);
						addbut[i].setBackground(Color.pink);
						addPressed[i] = true;

					}
				}
			}
		};
		color.addActionListener(aLis);
		buttunset(num);
	}
//here resting of the button of the shapes and the buttons under it deppend on the num that will be send 
	public void buttunset(int n) {
		shap = new JButton[n];
		ispressed = new boolean[n];
		for (int i = 0; i < n; i++) {
			shap[i] = new JButton("");
			shap[i].setBorder(null);
			shap[i].setBounds(start, i * sizeH, sizeW, sizeH);
			add(shap[i]);
			ispressed[i] = false;
			shap[i].addActionListener(lis);
			shap[i].setBackground(null);
			shap[i].setIcon(new ImageIcon(i + ".png"));
		}
		color.setBounds(start, (n + 3) * sizeH, sizeW, sizeH);
		color.setBorder(null);
		color.setIcon(new ImageIcon("color.png"));
		add(color);
		color.setBackground(null);
		for (int i = 0; i < 3; i++) {
			addbut[i] = new JButton(new ImageIcon(i + "" + i + "" + i + ".png"));
			addPressed[i] = false;
			addbut[i].setBounds(start, (n + i) * sizeH, sizeW, sizeH);
			add(addbut[i]);
			addbut[i].setBorder(null);
			addbut[i].setBackground(null);
			addbut[i].addActionListener(addLis);
		}
		placeX.setBounds(5, getHeight() - 165, 60, 40);
		placeY.setBounds(5, getHeight() - 150, 60, 40);
		add(placeX);
		add(placeY);
	}
//rest button make aall button un pressed
	private void resetB(boolean[] a, JButton[] ab) {
		for (int i = 0; i < a.length; i++) {
			a[i] = false;
			ab[i].setBackground(null);
		}
	}
//return the id of the pressed button and -1 if theres no pressed buttone
	public int getPressed(boolean[] a) {
		for (int i = 0; i < a.length; i++) {
			if (a[i] == true)
				return i;
		}
		return -1;
	}
}
