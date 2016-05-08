package mytrack;

import java.util.Enumeration;

import javax.swing.JTree;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public final class U2_Tree {
	
	
//	// Example use of Class
//	// Search forward from first visible row looking for any visible node
//	// whose name starts with prefix.
//	int startRow = 0;
//	String prefix = "b";
//	TreePath path = tree.getNextMatch(prefix, startRow, Position.Bias.Forward);
//
//	// Search backward from last visible row looking for any visible node
//	// whose name starts with prefix.
//	startRow = tree.getRowCount()-1;
//	prefix = "b";
//	path = tree.getNextMatch(prefix, startRow, Position.Bias.Backward);
//
//	// Find the path (regardless of visibility) that matches the
//	// specified sequence of names
//	path = findByName(tree, new String[]{"JTree", "food", "bananas"});

	public U2_Tree() {
		// TODO Auto-generated constructor stub
	}

	// Finds the path in tree as specified by the node array. The node array is a sequence
	// of nodes where nodes[0] is the root and nodes[i] is a child of nodes[i-1].
	// Comparison is done using Object.equals(). Returns null if not found.
	public TreePath find(JTree tree, Object[] nodes) {
	    TreeNode root = (TreeNode)tree.getModel().getRoot();
	    return find2(tree, new TreePath(root), nodes, 0, false);
	}

	// Finds the path in tree as specified by the array of names. The names array is a
	// sequence of names where names[0] is the root and names[i] is a child of names[i-1].
	// Comparison is done using String.equals(). Returns null if not found.
	public static TreePath findByName(JTree tree, String[] names) {
	    TreeNode root = (TreeNode)tree.getModel().getRoot();
	    return find2(tree, new TreePath(root), names, 0, true);
	}
	private static TreePath find2(JTree tree, TreePath parent, Object[] nodes, int depth, boolean byName) {
	    TreeNode node = (TreeNode)parent.getLastPathComponent();
	    Object o = node;

	    // If by name, convert node to a string
	    if (byName) {
	        o = o.toString();
	    }

	    // If equal, go down the branch
	    if (o.equals(nodes[depth])) {
	        // If at end, return match
	        if (depth == nodes.length-1) {
	            return parent;
	        }

	        // Traverse children
	        if (node.getChildCount() >= 0) {
	            for (Enumeration e=node.children(); e.hasMoreElements(); ) {
	                TreeNode n = (TreeNode)e.nextElement();
	                TreePath path = parent.pathByAddingChild(n);
	                TreePath result = find2(tree, path, nodes, depth+1, byName);
	                // Found a match
	                if (result != null) {
	                    return result;
	                }
	            }
	        }
	    }

	    // No match at this branch
	    return null;
	}

}
