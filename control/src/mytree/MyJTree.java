package mytree;

import java.util.Collections;
import java.util.Comparator;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;

public class MyJTree extends DefaultMutableTreeNode{

	JTree _jT;
	int _level = 0;
	
	String _nodeFrom;
	String _nodeTo;
	int _nodeFromDir;
	int _nodeToDir;

	public MyJTree(JTree jT){
		_jT = jT;
	}

	public void traverse() {
		_level = 0;
		Object a;
		if (_jT != null){
			TreeModel model = _jT.getModel();
			if (model != null) {
				Object root = model.getRoot();
				//1//2//3//99System.out.print(root.toString());
				_level = 1;
				walk(model,root);    
			}
			else
				a=null;
				//1//2//3//99System.out.print("Tree is empty.");
		}
		else
			a = null;
			//1//2//3//99System.out.print("JTree is null");
	}

	protected void walk(TreeModel model, Object o){
		int  cc;
		cc = model.getChildCount(o);
		
		for( int i=0; i < cc; i++) {
			Object child = model.getChild(o, i );
			if (model.isLeaf(child)){
				//1//2//3//99System.out.print("level:" + _level + " " + child.toString());
				addarc(child.toString());
			}
			else {
				//3//99System.out.print.print("level:" + _level + " " + child.toString()+" -- ");
				setnodes(child.toString());
				_level++;
				walk(model,child );
				_level--;
			} 
		}
	
	}

	private void setnodes(String myString) {
		//1//2//3//99System.out.print("\naddnode:" + myString + " level = " + _level +"\n" );
		if (myString.matches(".*_.*")){
			String[] nodes = myString.split("[_|\\s]");

			_nodeFrom = nodes[0];
			_nodeFromDir = Nodedir(nodes[1]);
			_nodeTo = nodes[2];
			_nodeToDir = Nodedir(nodes[3]);
			
			//1//2//3//99System.out.print("\n_nodeFrom = " + _nodeFrom + "\n_nodeFromDir = " + _nodeFromDir +  " " + nodes[1] +	"\n_nodeTo = " + _nodeTo + "\n_nodeFromDir = " + _nodeFromDir );
			
		}
		else
		{
			int i = 1;
			i++;
		}
	}
	
	int Nodedir(String ND){
		if( ND.equals("F")){
				return 1;
		}
		else
		{
			return -1;
		}
	}

	private void addarc(String string) {
		//1//2//3//99System.out.print("addarc:" + string + " " );
	}
	
	public void sortTree(){
		Collections.sort(this.children, nodeComparator);
	}
	
	@Override
	public void insert(MutableTreeNode newChild, int childIndex)    {
	    super.insert(newChild, childIndex);
	    Collections.sort(this.children, nodeComparator);
	}
	 
	protected Comparator nodeComparator = new Comparator () {
	    @Override
	    public int compare(Object o1, Object o2) {
	        return o1.toString().compareToIgnoreCase(o2.toString());
	    }
	 
	    @Override
	    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	    public boolean equals(Object obj)    {
	        return false;
	    }
	 
	    @Override
	    public int hashCode() {
	        int hash = 7;
	        return hash;
	    }
	}; 
}
