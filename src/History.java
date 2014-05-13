import java.util.Stack;

public class History {
	// the stak that all undo step will save in
	static Stack<MyShape> forUndo = new Stack<MyShape>();
	// the stake where all of redo shape will save in
	static Stack<MyShape> forRedo = new Stack<MyShape>();

	public History() {

	}

	// save history is take the prevous shap and copy it to put it in undo stake
	public void saveHistory(MyShape parent) {
		MyShape temp;
		if (parent != null) {
			temp = parent.copy();
		} else {
			temp = MyShape.his.getFirst();
		}
		forUndo.push(temp);
	}

	// undo pop from undo stake to use it
	public void unDo() {
		if (!forUndo.isEmpty()) {
			MyShape temp = forUndo.pop();

			if (temp.parent != null) {
				temp.parent.merge(temp);
				forRedo.push(temp);
			} else {
				if (!temp.isfirst) {
					MyShape.his.remove(temp);
					forRedo.push(temp);
					MyShape.mark.setFlag(false);
				} else {
					MyShape.his.addFirst(temp);
					MyShape t=temp.copy();t.isfirst=true;
					forRedo.push(t);
					temp.isfirst = false;
				}
			}
		}
		// pop from redo stake to use it
	}

	public void reDo() {
		if (!forRedo.isEmpty()) {
			MyShape temp = forRedo.pop();
			MyShape.mark.setFlag(false);
			if(!temp.isfirst){
			if (temp.parent != null) {
				temp.parent.merge(temp);
				forUndo.push(temp);
			} else {
				MyShape.his.addFirst(temp);
				forUndo.push(temp);
			}}else{
				temp.parent.delete();
			}
		}

	}

	// reset redo stake remove all elment of it
	public void resetRedo() {
		forRedo.removeAllElements();
	}
}
