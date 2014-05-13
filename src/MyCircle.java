import java.awt.Toolkit;

import javax.swing.ImageIcon;

public class MyCircle extends MyEllipse {

	public MyCircle(int x, int y) {
		super(x, y);
	}

	public void resizeBottom(int newSizeX, int newSizeY) {
		endX = newSizeX;
		endY = Math.abs(topLeftX - newSizeX) + topLeftY;
		ellip.setFrameFromDiagonal(topLeftX, topLeftY, endX, endY);
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		super.getMark().setMarkBot(endX, endY);
	}

	public void resizeTop(int newSizeX, int newSizeY) {
		// TODO Auto-generated method stub
		topLeftX = newSizeX;
		topLeftY = endY - Math.abs(endX - newSizeX);
		ellip.setFrameFromDiagonal(topLeftX, topLeftY, endX, endY);
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		super.getMark().setMarkTop(topLeftX, topLeftY);

	}
}
