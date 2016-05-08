package mytrack;

import java.util.Collections;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

//public class SortableDefaultMutableTreeNode extends DefaultMutableTreeNode {
//	public B1_Comparator nodeComparator;
//
//	public SortableDefaultMutableTreeNode(B1_Comparator nodeComparator) {
//		this.nodeComparator = nodeComparator;
//	}
//	
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//
//
//  public SortableDefaultMutableTreeNode(String string) {
//		super (string);
//	}
//  
//	@Override
//	public void insert(MutableTreeNode newChild, int childIndex)    {
//	    super.insert(newChild, childIndex);
//	    Collections.sort(this.children, nodeComparator);
//	}
//	
//	
//	public void sort(){
//		Collections.sort(this.children, nodeComparator);
//	}
//	
//	protected B1_Comparator nodeComparator = new B1_Comparator();
//}

public class SortableDefaultMutableTreeNode extends DefaultMutableTreeNode{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


  public SortableDefaultMutableTreeNode(String string) {
		super (string);
	}
  
	@Override
	public void insert(MutableTreeNode newChild, int childIndex)    {
	    super.insert(newChild, childIndex);
	    Collections.sort(this.children, nodeComparator);
	}
	
	
	public void sort(){
		Collections.sort(this.children, nodeComparator);
	}
	
	protected B1_Comparator nodeComparator = new B1_Comparator();

}