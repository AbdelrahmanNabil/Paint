import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.lang.reflect.InvocationTargetException;
import java.sql.Savepoint;
import java.util.Iterator;
import javax.swing.JPanel;

public class Paintarea extends JPanel {
	protected static PaintItem item;
	protected static Class[] allShapes;
	private int x, y;
	private MyShape temp = null;
	private boolean tempF = false;
	private boolean resizeBot = false;
	private boolean resizetop = false;
	protected History shapHistory;

	public MyShape getTemp() {
		return temp;
	}

	public Paintarea(int w, int h, int s, int iW, int iH) {
		shapHistory = new History();
		allShapes = new Class[] { MyEllipse.class, MyCircle.class,
				MySquare.class, MyRectangle.class, MyLine.class };
		setBounds(s, 80, w, h);
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		addMouseListener(new ListnerH());
		addMouseMotionListener(new ListnerH());
		item = new PaintItem(iW, iH, 5);
	}

	// this is main method that paint on the panel
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		if (!MyShape.his.isEmpty()) {
			if (!MyShape.his.isEmpty()) {
				if (MyShape.mark.isFlag()) {
					g2.fill(MyShape.mark.getTop());
					g2.fill(MyShape.mark.getBottom());
				}
				MyShape itr;
				Iterator<MyShape> t = MyShape.his.iterator();
				while (t.hasNext()) {
					itr = t.next();
					g2.setColor(itr.getDrawCol());
					if (itr.fill) {
						g2.fill(itr.getShape());
					} else
						g2.draw(itr.getShape());

				}
			}
		}
	}

	// mouselistener that get all response of drawing
	class ListnerH extends MouseAdapter {
		public void mouseExited(MouseEvent arg0) {
			item.placeX.setText("x is out");
			item.placeY.setText("y is out");

		}

		public void mouseMoved(MouseEvent arg0) {
			item.placeX.setText("x=" + arg0.getX());
			item.placeY.setText("y=" + arg0.getY());
		}

		public void mouseEntered(MouseEvent arg0) {
			setCursor(new Cursor(1));
		}

		public void mouseDragged(MouseEvent arg0) {
			item.placeX.setText("x=" + arg0.getX());
			item.placeY.setText("y=" + arg0.getY());

			if (!MyShape.his.isEmpty()) {
				int choice = item.getPressed(item.ispressed);
				if (choice != -1) {
					MyShape.his.getFirst().resizeBottom(arg0.getX(),
							arg0.getY());
					repaint();
				} else {
					choice = item.getPressed(item.addPressed);
					if (choice == 0) {
						if (resizetop && MyShape.mark.isFlag()) {
							MyShape.his.getFirst().resizeTop(arg0.getX(),
									arg0.getY());
						} else {
							if (resizeBot && MyShape.mark.isFlag())
								MyShape.his.getFirst().resizeBottom(
										arg0.getX(), arg0.getY());
							else {
								if (tempF) {
									temp.move(x, y, arg0.getX(), arg0.getY());
									x = arg0.getX();
									y = arg0.getY();
								}
							}
						}
					}
				}
			}
			repaint();
		}

		public void mousePressed(MouseEvent arg0) {
			int choice = item.getPressed(item.ispressed);
			if (choice != -1) {
				try {
					temp = (MyShape) allShapes[choice].getConstructor(
							new Class[] { Integer.TYPE, Integer.TYPE })
							.newInstance(arg0.getX(), arg0.getY());
					temp.setDrawCol(item.chColor);
					shapHistory.saveHistory(null);
					shapHistory.resetRedo();
				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
				repaint();
			} else {
				choice = item.getPressed(item.addPressed);
				if (choice == 0) {
					x = arg0.getX();
					y = arg0.getY();
					if (MyShape.mark.isIntop(arg0.getX(), arg0.getY())) {
						resizetop = true;
						shapHistory.saveHistory(MyShape.his.getFirst());
						shapHistory.resetRedo();
					} else {
						if (MyShape.mark.isInbot(arg0.getX(), arg0.getY())) {
							resizeBot = true;
							shapHistory.saveHistory(MyShape.his.getFirst());
							shapHistory.resetRedo();
						} else {
							Iterator<MyShape> itrat = MyShape.his.iterator();
							while (itrat.hasNext()) {
								temp = itrat.next();
								if (temp.isInside(x, y)) {
									MyShape.mark.setMarkTop(temp.topLeftX,
											temp.topLeftY);
									MyShape.mark.setMarkBot(temp.endX,
											temp.endY);
									tempF = true;
									MyShape.his.remove(temp);
									MyShape.his.addFirst(temp);
									shapHistory.saveHistory(MyShape.his
											.getFirst());
									shapHistory.resetRedo();
									break;

								} else {
									MyShape.mark.setFlag(false);
									temp = null;
								}
							}

						}
					}
				} else {
					Iterator<MyShape> itrat1 = MyShape.his.iterator();
					while (itrat1.hasNext()) {
						temp = itrat1.next();
						if (temp.isInside(arg0.getX(), arg0.getY())) {

							MyShape.mark.setMarkTop(temp.topLeftX,
									temp.topLeftY);
							MyShape.mark.setMarkBot(temp.endX, temp.endY);

							repaint();
							break;
						}
					}
					if (choice == 1 && !(temp instanceof MyLine)
							&& temp != null) {
						MyShape.his.remove(temp);
						MyShape.his.addFirst(temp);
						shapHistory.saveHistory(MyShape.his.getFirst());
						shapHistory.resetRedo();
						temp.fill = true;
						temp.setDrawCol(item.chColor);
					} else {
						if (choice == 2) {
							if (!MyShape.his.isEmpty()
									&& temp.isInside(arg0.getX(), arg0.getY())) {
								temp.delete();
								MyShape.mark.setFlag(false);
							}
						}
					}
				}
			}
			repaint();
		}

		public void mouseReleased(MouseEvent arg0) {
			if(resizeBot||resizetop||tempF){
				MyShape comp=History.forUndo.pop();
				if(comp.parent!=null){
					MyShape comp1=comp.parent;
					if(comp.topLeftX==comp1.topLeftX&&comp.topLeftY==comp1.topLeftY&&comp.endX==comp1.endX&&comp.endY==comp1.endY){
						
					}else{
						History.forUndo.push(comp);
					}
						
				}else{
					History.forUndo.push(comp);
				}
			}
			tempF = false;
			resizeBot = false;
			resizetop = false;
		}
	}
}