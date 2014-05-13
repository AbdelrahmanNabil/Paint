import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
//out frame class is for the frame tht will all previous panels will be add on
public class OutFrame extends JFrame {
	protected static Paintarea area;
	protected static boolean press = false;

	public OutFrame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Toolkit kit = Toolkit.getDefaultToolkit();
		setLayout(null);
		setSize(kit.getScreenSize().width, kit.getScreenSize().height - 40);
		add(new MenuPanel(getWidth(), 30));
		area = new Paintarea((getWidth() - (getWidth() / 22)),
				getHeight() - 30, getWidth() / 22, getWidth() / 12, getHeight());
		add(area);
		add(area.item);
		setVisible(true);
		setIconImage(kit.getImage("f.png"));
		setTitle("Paint");
	}

	public static void main(String[] args) {
		new OutFrame();

	}

	// menu panel that will find the top part of the screen buttons
	class MenuPanel extends JPanel {
		JMenuBar menu;
		JMenu myFile;
		JMenu about;
		JButton mArray[];
		JMenuItem loadL;
		JMenuItem save;
		JMenuItem exit;
		JMenuItem open;
		JMenuItem help;
		JMenuItem customize;
		public MenuPanel(int w, int l) {
			menu = new JMenuBar();
			myFile = new JMenu("File");
			about = new JMenu("about");
			loadL=new JMenuItem("Load Library");
			save=new JMenuItem("Save");
			exit=new JMenuItem("EXit");
			open=new JMenuItem("Open");
			help=new JMenuItem("Help");
			customize=new JMenuItem("customize");
			mArray = new JButton[5];
			for (int i = 0; i < 5; i++) {
				mArray[i] = new JButton(new ImageIcon(i + "" + i + ".png"));
				mArray[i].setBackground(null);
				mArray[i].setBorder(null);
				mArray[i].setBounds(10 + (i * 2 * 50), l, 50, 50);
				add(mArray[i]);
			}
			help.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					JFrame hel=new JFrame("help");
					hel.setBounds(400, 200,675, 575);
					hel.setResizable(false);
					JLabel l=new JLabel();
					l.setIcon(new ImageIcon("about.png"));
				
					hel.add(l);
					hel.setVisible(true);
				}
			});
			exit.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					System.exit(0);
				}
			});
			setLayout(null);
			setBounds(0, 0, w, l + 50);
			menu.setBounds(0, 0, w, l);
			menu.setVisible(true);
			add(menu);
			myFile.add(customize);
			myFile.add(loadL);
			myFile.add(open);
			myFile.add(save);
			myFile.add(exit);
			menu.add(myFile);
			menu.add(about);
			about.add(help);
			menu.setBackground(Color.lightGray);
			setBackground(Color.GRAY);
			ActionListener loadLis=new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					Loader load = new Loader();
					Class nc = load.newCla();
					Class[] temp = Paintarea.allShapes;
					if (nc != null)
						for (int i = 0; i < temp.length; i++) {
							if (nc.getName().equals(temp[i].getName())) {
								nc = null;
								break;
							}
						}
					if (nc != null) {

						Class[] temp1 = new Class[temp.length + 1];
						for (int i = 0; i < temp.length; i++) {
							temp1[i] = temp[i];
						}
						temp1[temp.length] = nc;
						Paintarea.allShapes = temp1;
						Paintarea.item.removeAll();
						Paintarea.item.buttunset(temp.length + 1);
					}
				}
			};
			mArray[4].addActionListener(loadLis);
			// undo button
			mArray[0].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					new History().unDo();
					area.repaint();
				}
			});
			// redo button
			mArray[1].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					new History().reDo();
					area.repaint();
				}
			});
			ActionListener saveLis=new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					JFileChooser jc=new JFileChooser();
					jc.setFileFilter(new FileNameExtensionFilter(".xml", ".xml"));
					jc.setFileFilter(new FileNameExtensionFilter(".json", ".json"));
					int resp=jc.showSaveDialog(null);
					String url="";
					String name="";
					if(resp==jc.APPROVE_OPTION){
						File file=jc.getSelectedFile();
						url=file.getPath()+jc.getFileFilter().getDescription();
						name=file.getName()+jc.getFileFilter().getDescription();
					if(name.charAt(name.length()-1)=='l'){
						SaveAndLoadXml.save(url);
					}else{
						new JasonSave().saveJson(url);
					}
					}
				}
			};
			mArray[2].addActionListener(saveLis);save.addActionListener(saveLis);
			ActionListener opLis=new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					JFileChooser jc=new JFileChooser();
					jc.setFileFilter(new FileNameExtensionFilter(".xml", ".xml"));
					jc.setFileFilter(new FileNameExtensionFilter(".json", ".json"));
					int resp=jc.showOpenDialog(null);
					String url="";
					String name="";
					if(resp==jc.APPROVE_OPTION){
						History.forRedo.clear();
						History.forUndo.clear();
						File file=jc.getSelectedFile();
						url=file.getPath();
						name=file.getName();
			
					if(name.charAt(name.length()-1)=='l'){
						new SaveAndLoadXml().loadList(file);
					}else{
						MyShape.his=new LinkedList<MyShape>();
						try{
						new JasonSave().load(url);
						}catch(Exception e){
						JOptionPane.showMessageDialog(null, "wrong in file");
						}
						MyShape.his.removeLast();
					}
					}
					MyShape.mark.setFlag(false);
					area.repaint();
				}
			};
			mArray[3].addActionListener(opLis);
			open.addActionListener(opLis);
			customize.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					String hight="0";
					String width="0";
					try{
						JOptionPane pane=new JOptionPane();
						width=pane.showInputDialog("insert width"+"("+"Current Width="+Math.abs(area.getTemp().endX-area.getTemp().topLeftX)+")");
						hight=pane.showInputDialog("insert hight"+"("+"Current hight="+Math.abs(area.getTemp().endY-area.getTemp().topLeftY)+")");
						int h=Integer.parseInt(hight);
						int w=Integer.parseInt(width);
					if(area.getTemp()!=null){
						new History().saveHistory(area.getTemp());
						area.getTemp().resizeBottom(area.getTemp().endX+w,area.getTemp().endY+h);
						area.repaint();
					}
					}catch(Exception e){
					JOptionPane.showMessageDialog(null, "invalid input");	
					}
				}
			});
		}
	}
}