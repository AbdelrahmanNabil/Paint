import java.awt.Color;
import java.awt.Shape;
import java.util.LinkedList;

public abstract class MyShape {
	// marke of resize that appear around the shape
	static MoverMark mark = new MoverMark();
	// the main linkedlist that all shpes on the screen will be save in
	static LinkedList<MyShape> his = new LinkedList<MyShape>();
	// is first is important when delet any shape because it will not have
	// aprent
	protected boolean isfirst = false;
	// aparent of shap that will be the elment that all chage will be on it
	protected MyShape parent = null;
	protected int topLeftX, topLeftY, endX, endY;
	protected Color drawCol;
	protected boolean fill = false;// to initialize it in all subclass

	public static LinkedList<MyShape> getHis() {
		return his;
	}

	public static MoverMark getMark() {
		return mark;
	}

	// choosing the color of the shape
	public void setDrawCol(Color c) {
		drawCol = c;
	}

	// return acolor of shape
	public Color getDrawCol() {
		return drawCol;
	}

	// changing the center to move the shape
	public abstract void move(int oldX, int oldY, int newX, int newY);

	// method for changing the shape size from bottom
	public abstract void resizeBottom(int newSizeX, int newSizeY);

	// method for changing the shape size from top
	public abstract void resizeTop(int newSizeX, int newSizeY);

	// this method return the shape
	public abstract Shape getShape();

	// take the index of point and check if its inside the shape or not
	public abstract boolean isInside(int postionX, int postionY);

	// method for deleting the last shape
	public void delete() {
		this.isfirst = true;
		new History().forUndo.push(this);
		his.remove(this);
	}

	// copy the shape to put it in history of undo and redo
	public abstract MyShape copy();

	// merge the shapewith the other given shap
	public abstract void merge(MyShape b);
}
