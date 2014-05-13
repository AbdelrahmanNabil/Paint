import java.awt.Color;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;

import javax.swing.ImageIcon;
import javax.swing.RepaintManager;

public class MyEllipse extends MyShape {
	protected Ellipse2D ellip;

	public MyEllipse(int x, int y) {
		topLeftX = x;
		topLeftY = y;
		endX = x;
		endY = y;
		ellip = new Ellipse2D.Double();
		ellip.setFrameFromDiagonal(x, y, x, y);
		super.getHis().addFirst(this);
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		super.getMark().setMarkTop(topLeftX, topLeftY);
		super.getMark().setMarkBot(endX, endY);
		super.getMark().setFlag(true);
	}

	public MyEllipse(int x, int y, int w, int z) {
		topLeftX = x;
		topLeftY = y;
		endX = w;
		endY = z;
		ellip = new Ellipse2D.Double();
		ellip.setFrameFromCenter(topLeftX, topLeftY, endX, endY);
	}

	@Override
	public Shape getShape() {
		// TODO Auto-generated method stub
		return ellip;
	}

	// ((x-xcenter)^2)/a^2+((y-ycenter)^2)/b^2
	@Override
	public boolean isInside(int postionX, int postionY) {
		// TODO Auto-generated method stub
		int tempX = (int) (postionX - ellip.getCenterX());
		int tempY = (int) (postionY - ellip.getCenterY());
		if (Math.pow((tempX / (ellip.getWidth() / 2)), 2)
				+ Math.pow((tempY / (ellip.getHeight() / 2)), 2) <= 1) {
			super.getMark().setFlag(true);
			return true;
		} else {
			return false;
		}
		
	}

	@Override
	public void resizeBottom(int newSizeX, int newSizeY) {
		// TODO Auto-generated method stub
		endX = newSizeX;
		endY = newSizeY;
		ellip.setFrameFromDiagonal(topLeftX, topLeftY, endX, endY);
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		super.getMark().setMarkBot(endX, endY);
	}

	@Override
	public void resizeTop(int newSizeX, int newSizeY) {
		// TODO Auto-generated method stub
		topLeftX = newSizeX;
		topLeftY = newSizeY;
		ellip.setFrameFromDiagonal(topLeftX, topLeftY, endX, endY);
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		super.getMark().setMarkTop(topLeftX, topLeftY);

	}

	@Override
	public void move(int oldX, int oldY, int newX, int newY) {
		// TODO Auto-generated method stub
		int diffX = oldX - newX;
		int diffY = oldY - newY;
		topLeftX -= diffX;
		topLeftY -= diffY;
		endX -= diffX;
		endY -= diffY;
		ellip.setFrameFromDiagonal(topLeftX, topLeftY, endX, endY);
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		super.getMark().setMarkTop(topLeftX, topLeftY);
		super.getMark().setMarkBot(endX, endY);
	}

	@Override
	public MyShape copy() {
		MyEllipse copy = new MyEllipse(this.topLeftX, this.topLeftY, this.endX,
				this.endY);
		copy.setDrawCol(this.getDrawCol());
		copy.fill = this.fill;
		copy.parent = this;
		return copy;
	}

	@Override
	public void merge(MyShape b) {
		b=(MyEllipse)b;
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
		((Ellipse2D) this.getShape()).setFrameFromDiagonal(topLeftX, topLeftY,
				endX, endY);
		super.getMark().setMarkTop(topLeftX, topLeftY);
		super.getMark().setMarkBot(endX, endY);
	}

}
