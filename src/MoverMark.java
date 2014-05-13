import java.awt.geom.Rectangle2D;

public class MoverMark {
	private boolean flag;
	private final int size = 8;
	private int x1, y1, x2, y2;
	Rectangle2D top;
	Rectangle2D bottom;

	// move marker class for resizing
	public MoverMark() {
		flag = false;
		top = new Rectangle2D.Double();
		top.setFrameFromDiagonal(x1, y1, size, size);
		bottom = new Rectangle2D.Double();
		bottom.setFrameFromDiagonal(x2, y2, size, size);
	}

	// boolean to gave the premisson to marker to be vissable
	public boolean isFlag() {
		return flag;
	}

	// set the flage
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	// return the to mrker of the flage
	public Rectangle2D getTop() {
		return top;
	}

	// return the bottom marker of the flage
	public Rectangle2D getBottom() {
		return bottom;
	}

	// set the squre mark
	public void setX1(int x1) {
		this.x1 = x1;
		top.setFrame(x1, y1, size, size);
	}

	// set the y of the top mark
	public void setY1(int y1) {
		this.y1 = y1;
		top.setFrame(x1, y1, size, size);
	}

	// set the x of the bottom mark
	public void setX2(int x2) {
		this.x2 = x2;
		bottom.setFrame(x2, y2, size, size);
	}

	// set the y of the bottom mark
	public void setY2(int y2) {
		this.y2 = y2;
		bottom.setFrame(x2, y2, size, size);
	}

	// another setter of the to mark
	public void setMarkTop(int x1, int y1) {
		this.x1 = x1 - size;
		this.y1 = y1 - size;
		top.setFrame(this.x1, this.y1, size, size);
	}

	// another setter of the bototom mark
	public void setMarkBot(int x2, int y2) {
		this.x2 = x2;
		this.y2 = y2;
		bottom.setFrame(x2, y2, size, size);
	}

	// check if the point is in the top mark
	public boolean isIntop(int posX, int posY) {
		if (((posX <= x1) ^ (posX <= x1 + size))
				&& ((posY <= y1) ^ (posY <= y1 + size))) {
			return true;
		} else
			return false;
	}

	// check if the given point is in the bottom mark
	public boolean isInbot(int posX, int posY) {
		if (((posX <= x2) ^ (posX <= x2 + size))
				&& ((posY <= y2) ^ (posY <= y2 + size))) {
			return true;
		} else
			return false;
	}
}
