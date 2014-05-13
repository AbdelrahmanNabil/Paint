import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Iterator;

public class MyLine extends MyShape {

	private Line2D line;
	private Double slope;

	public MyLine(int x, int y) {
		topLeftX = x;
		topLeftY = y;
		endX = x;
		endY = y;
		line = new Line2D.Double();
		line.setLine(topLeftX, topLeftY, endX, endY);
		super.getHis().addFirst(this);
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		super.getMark().setMarkTop(topLeftX, topLeftY);
		super.getMark().setMarkBot(endX, endY);
		super.getMark().setFlag(true);
	}

	public MyLine(int x, int y, int w, int z) {
		topLeftX = x;
		topLeftY = y;
		endX = w;
		endY = z;
		line = new Line2D.Double();
		line.setLine(topLeftX, topLeftY, endX, endY);
	}

	@Override
	// this method return java shape
	public Shape getShape() {
		// TODO Auto-generated method stub
		return line;
	}

	// this method to move line
	public void move(int oldX, int oldY, int newX, int newY) {
		// TODO Auto-generated method stub
		int diffX = oldX - newX;
		int diffY = oldY - newY;
		topLeftX -= diffX;
		topLeftY -= diffY;
		endX -= diffX;
		endY -= diffY;
		line.setLine(topLeftX, topLeftY, endX, endY);
		slope = getSlope(endX, endY);
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		super.getMark().setMarkTop(topLeftX, topLeftY);
		super.getMark().setMarkBot(endX, endY);
	}

	@Override
	// resizing shape from the bottom corrner
	public void resizeBottom(int newSizeX, int newSizeY) {
		// TODO Auto-generated method stub
		endX = newSizeX;
		endY = newSizeY;
		line.setLine(topLeftX, topLeftY, endX, endY);
		slope = getSlope(endX, endY);
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		super.getMark().setMarkBot(endX, endY);
	}

	// /resizing shape from the top corrner
	@Override
	public void resizeTop(int newSizeX, int newSizeY) {
		// TODO Auto-generated method stub
		topLeftX = newSizeX;
		topLeftY = newSizeY;
		line.setLine(topLeftX, topLeftY, endX, endY);
		slope = getSlope(endX, endY);
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		super.getMark().setMarkTop(topLeftX, topLeftY);
		;
	}

	// method calculate the slop of the line to make acheck
	private Double getSlope(int x1, int y1) {
		if (topLeftX != x1)
			return new Double((topLeftY - y1) / (topLeftX - x1));
		else
			return null;
	}

	// inside method used to know that the given point is in the range of the
	// line
	public boolean isInside(int postionX, int postionY) {
		if (slope == null || getSlope(postionX, postionY) == null) {
			if (((postionY > topLeftY && postionY < endY)
					|| (postionY < topLeftY && postionY > endY))
					&& (postionX < topLeftX + 10 && postionX > topLeftX - 10)) {
				super.getMark().setFlag(true);
				return true;
			} else {
				return false;
			}
		} else {
				if (((postionX > topLeftX && postionX < endX)
						|| (postionX < topLeftX && postionX > endX))
						&& (postionY < topLeftY + 10 && postionY > topLeftY - 10)) {
					super.getMark().setFlag(true);
					return true;
				} else {
			if ((postionY > topLeftY && postionY < endY)
					|| (postionY < topLeftY && postionY > endY)) {
				if (this.getSlope(postionX, postionY).equals(
						new Double((this.slope)))) {
					super.getMark().setFlag(true);
					return true;

				}
			}}
		}
		return false;
	}

	// copy method is help undo and redo
	@Override
	public MyShape copy() {
		MyLine copy = new MyLine(this.topLeftX, this.topLeftY, this.endX,
				this.endY);
		copy.setDrawCol(this.getDrawCol());
		copy.fill = this.fill;
		copy.parent = this;
		return copy;
	}

	// merge method is making aswaping between the main shap and the giveen one
	@Override
	public void merge(MyShape b) {
		b=(MyLine)b;
		int temp1 = this.topLeftX;
		int temp2 = this.topLeftY;
		int temp3 = this.endX;
		int temp4 = this.endY;
		boolean temb5 = this.fill;
		Color temb6 = this.getDrawCol();
		this.topLeftX = b.topLeftX;
		this.topLeftY = b.topLeftY;
		this.drawCol = b.drawCol;
		this.endX = b.endX;
		this.endY = b.endY;
		this.setDrawCol(b.getDrawCol());
		this.fill = b.fill;
		b.topLeftX = temp1;
		b.topLeftY = temp2;
		b.endX = temp3;
		b.endY = temp4;
		b.setDrawCol(temb6);
		b.fill = temb5;
		((Line2D) this.getShape()).setLine(topLeftX, topLeftY, endX, endY);
		super.getMark().setMarkTop(topLeftX, topLeftY);
		super.getMark().setMarkBot(endX, endY);
	}
}