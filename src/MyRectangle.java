import java.awt.Color;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;

public class MyRectangle extends MyShape {
	private Rectangle2D rectangle;;

	public MyRectangle(int x, int y) {
		topLeftX = x;
		topLeftY = y;
		endX = x;
		endY = y;
		rectangle = new Rectangle2D.Double();
		rectangle.setFrameFromDiagonal(topLeftX, topLeftY, endX, endY);
		super.getHis().addFirst(this);
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		super.getMark().setMarkTop(topLeftX, topLeftY);
		super.getMark().setMarkBot(endX, endY);
		super.getMark().setFlag(true);
	}

	public MyRectangle(int x, int y, int w, int z) {
		topLeftX = x;
		topLeftY = y;
		endX = w;
		endY = z;
		rectangle = new Rectangle2D.Double();
		rectangle.setFrame(topLeftX, topLeftY, endX, endY);
	}

	public void resizeBottom(int newSizeX, int newSizeY) {
		endX = newSizeX;
		endY = newSizeY;
		rectangle.setFrameFromDiagonal(topLeftX, topLeftY, endX, endY);
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		super.getMark().setMarkBot(endX, endY);
	}

	@Override
	public void resizeTop(int newSizeX, int newSizeY) {
		// TODO Auto-generated method stub
		topLeftX = newSizeX;
		topLeftY = newSizeY;
		rectangle.setFrameFromDiagonal(topLeftX, topLeftY, endX, endY);
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		super.getMark().setMarkTop(topLeftX, topLeftY);

	}

	public void move(int oldX, int oldY, int newX, int newY) {
		int diffX = oldX - newX;
		int diffY = oldY - newY;
		topLeftX -= diffX;
		topLeftY -= diffY;
		endX -= diffX;
		endY -= diffY;
		rectangle.setFrameFromDiagonal(topLeftX, topLeftY, endX, endY);
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		super.getMark().setMarkTop(topLeftX, topLeftY);
		super.getMark().setMarkBot(endX, endY);
	}

	public Shape getShape() {
		// TODO Auto-generated method stub
		return rectangle;
	}

	@Override
	public boolean isInside(int postionX, int postionY) {
		if (((postionX <= topLeftX) ^ (postionX <= endX))
				&& ((postionY <= topLeftY) ^ (postionY <= endY))) {
			super.getMark().setFlag(true);
			return true;
		}
		return false;
	}

	@Override
	public MyShape copy() {
		MyRectangle copy = new MyRectangle(this.topLeftX, this.topLeftY,
				this.endX, this.endY);
		copy.setDrawCol(this.getDrawCol());
		copy.fill = this.fill;
		copy.parent = this;
		return copy;
	}

	@Override
	public void merge(MyShape b) {
		b=(MyRectangle)b;
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
		((Rectangle2D) this.getShape()).setFrameFromDiagonal(topLeftX,
				topLeftY, endX, endY);
		super.getMark().setMarkTop(topLeftX, topLeftY);
		super.getMark().setMarkBot(endX, endY);
	}

}
