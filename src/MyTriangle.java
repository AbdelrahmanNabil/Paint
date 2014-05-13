import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Point2D;


public class MyTriangle extends MyShape{
	protected Polygon tring;
	private Point p1,p2,p3;
	public MyTriangle(int x,int y) {
		topLeftX = x;
		topLeftY = y;
		endX = x;
		endY = y;
		tring=new Polygon();
		p1=new Point(x, y);
			p2=new Point(x,y);
				p3=new Point(x,y);
		tring.addPoint(p1.x, p1.y);
		tring.addPoint(p2.x, p2.y);
		tring.addPoint(p3.x, p3.y);
		super.getHis().addFirst(this);
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		super.getMark().setMarkTop(topLeftX, topLeftY);
		super.getMark().setMarkBot(endX, endY);
		super.getMark().setFlag(true);
	}

	public MyTriangle(int x, int y, int w, int z) {
		topLeftX = x;
		topLeftY = y;
		endX = w;
		endY = z;
		tring=new Polygon();
		p1=new Point(x+(Math.abs(x-w)/2), y);
		p2=new Point(x,z);
		p3=new Point(w,z);
		tring.addPoint(p1.x, p1.y);
		tring.addPoint(p2.x, p2.y);
		tring.addPoint(p3.x, p3.y);
	}
	@Override
	public Shape getShape() {
		// TODO Auto-generated method stub
		return tring;
	}
	public double calcuArea(double x,double y,Point2D z , Point2D n){
		double a = 0;
		double x1 = x - z.getX();
		double y1 = y - z.getY();
		double x2 = x - n.getX();
		double y2 = y - n.getY();
		
		a = Math.abs((x1*y2) - (y1*x2));
		return a;
	}
	public boolean isInside(int X,int y){
		double x = calcuArea(p1.getX(),p1.getY(),p2,p3);
		double t1 = calcuArea(X,y, p1, p2);
		double t2 = calcuArea(X,y, p1, p3);
		double t3 = calcuArea(X,y, p2, p3);
		double tt = t1 + t2 + t3;
		
		if(Math.abs(x-tt)<.001){
			super.getMark().setFlag(true);
			return true;
		}
		return false;
	}
	@Override
	public void resizeBottom(int newSizeX, int newSizeY) {
		// TODO Auto-generated method stub
		endX = newSizeX;
		endY = newSizeY;
		p1.setLocation(topLeftX+(Math.abs(topLeftX-endX)/2),topLeftY);
		p2.setLocation(topLeftX, endY);
		p3.setLocation(endX, endY);
		tring.xpoints[0]=p1.x;
		tring.xpoints[1]=p2.x;
		tring.xpoints[2]=p3.x;
		tring.ypoints[0]=p1.y;
		tring.ypoints[1]=p2.y;
		tring.ypoints[2]=p3.y;
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		super.getMark().setMarkBot(endX, endY);
	}

	@Override
	public void resizeTop(int newSizeX, int newSizeY) {
		// TODO Auto-generated method stub
		topLeftX = newSizeX;
		topLeftY = newSizeY;
		p1.setLocation(topLeftX+(Math.abs(topLeftX-endX)/2),topLeftY);
		p2.setLocation(topLeftX, endY);
		p3.setLocation(endX, endY);
		tring.xpoints[0]=p1.x;
		tring.xpoints[1]=p2.x;
		tring.xpoints[2]=p3.x;
		tring.ypoints[0]=p1.y;
		tring.ypoints[1]=p2.y;
		tring.ypoints[2]=p3.y;
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
		p1.setLocation(topLeftX+(Math.abs(topLeftX-endX)/2),topLeftY);
		p2.setLocation(topLeftX, endY);
		p3.setLocation(endX, endY);
		tring.xpoints[0]=p1.x;
		tring.xpoints[1]=p2.x;
		tring.xpoints[2]=p3.x;
		tring.ypoints[0]=p1.y;
		tring.ypoints[1]=p2.y;
		tring.ypoints[2]=p3.y;
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		super.getMark().setMarkTop(topLeftX, topLeftY);
		super.getMark().setMarkBot(endX, endY);
	}

	@Override
	public MyShape copy() {
		MyTriangle copy = new MyTriangle(this.topLeftX, this.topLeftY, this.endX,
				this.endY);
		copy.setDrawCol(this.getDrawCol());
		copy.fill = this.fill;
		copy.parent = this;
		return copy;
	}

	@Override
	public void merge(MyShape b) {
		b=(MyTriangle)b;
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
		this.resizeBottom(this.endX, this.endY);
		super.getMark().setMarkTop(topLeftX, topLeftY);
		super.getMark().setMarkBot(endX, endY);
	}


}
