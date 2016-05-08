package mytrack;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.media.j3d.BranchGroup;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.LayoutStyle;

import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.SwingUtilities;

import myscene.A_MyScene;
import org.jdesktop.application.Application;

import sm2.E1;

public class B_myGUI extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The j tree1. */
	private static JTree myJTree;

	/** The j panel1. */
	private JPanel jPanel1;

	/** The jbtn add straight. */
	private JButton jbtnAddStraight;

	/** The jbtn add right curve. */
	private JButton jbtnAddRightCurve;

	/** The j panel3. */
	private JPanel jPanel3;

	/** The j panel2. */
	private JPanel jPanel2;

	/** The jbtn add. */
	private JButton jbtnAdd;

	/** The jbtn remove. */
	private JButton jbtnRemove;

	/** The jbtn load tree. */
	private JButton jbtnLoadTree;

	/** The jbtn save. */
	private JButton jbtnSave;

	/** The model. */
	DefaultTreeModel model = new DefaultTreeModel(
			new SortableDefaultMutableTreeNode("root"));
	private JButton jbtn_UpdateGraph;
	private JScrollPane jScrollPane1;
	private JButton jbtn_StopPlace;
	private JButton jbtn_TrainRoute;
	private JButton jbtnSetRoute;
	private JButton jBtnSort;
	private JButton jbtnStart;
	private JButton jbtnInit;
	private JPanel jPanel4;
	private JTabbedPane jTabbedPane1;
	private JButton jBtnStations;
	private JButton jButton2;
	private JButton jButton1;
	private JButton jBtnAddExtend;
	private JButton jBtnAddLeftCurve;

	/** The jbtn clear. */
	private JButton jbtnClear;

	/** The jlbl save as file. */
	private JLabel jlblSaveAsFile;

	/** The jbtn load from. */
	private JButton jbtnLoadFrom;

	/** The jbtn save as. */
	private JButton jbtnSaveAs;

	/** The inner model. */
	DefaultTreeModel innerModel = model;

	private BranchGroup trackBG;

	private A_MyScene sceneModel;
	private A_MyScene sceneAdaption;

	private B2_LinkedLists _ArcAndNodeLinkedList;

	private B3_Hashmaps _ArcAndNodeHashmaps;

	private C1_BranchGroup _modelBranchGroup;

	{
//		// Set Look & Feel
//		try {
//			javax.swing.UIManager
//					.setLookAndFeel("com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	public JTree GetJTree() {
		return myJTree;
	}

	// private void setBG(){
	// // EMKGraph EG;
	// // @Before
	// // public void setUp(){
	// // //Add Nodes
	// // EG.AddNodes("N1", new Vector3(10, -20, 10), Vector3.Normalize(new
	// Vector3(1, 0, 0)));
	// // EG.AddNodes("N2");
	// // EG.AddNodes("N3");
	// // EG.AddNodes("N4reverse");
	// // EG.AddNodes("N5reverse");
	// // EG.AddNodes("N6");
	// // EG.AddNodes("N7");
	// //
	// // //Add Arcs
	// //
	// // String[] SegElements1 = { "RC", "RC", "RC", "RC", "RC", "RC" };
	// // TrackArcSpec TAS = new TrackArcSpec(EG.TG.TrackNodesDict["N1"], 1,
	// EG.TG.TrackNodesDict["N5reverse"], 1, SegElements1);
	// // EG.AddTrackArc_and_Track("N1_1_N5reverse_1", TAS);
	// // //EG.TG.TrackArcsDict2.Add ( 0,"N1_1_N5reverse_1" );
	// }

	public DefaultTreeModel GetModel() {
		return model;
	}

	/**
	 * Auto-generated main method to display this JFrame.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				B_myGUI inst = new B_myGUI();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	public void setSceneModel(A_MyScene myscene) {
		sceneModel = myscene;
	}

	public void setSceneAdaption(A_MyScene myscene) {
		sceneAdaption = myscene;
	}

	public B_myGUI() {
		super();
		initGUI();
	}

	/**
	 * Instantiates a new myGUI.
	 * 
	 * @param _ArcAndNodeLinkedList2
	 * @param _ArcAndNodeHashmaps
	 * @param _ArcAndNodeLinkedList
	 */

	public B_myGUI(B2_LinkedLists _ArcAndNodeLinkedList2,
			B3_Hashmaps ArcAndNodeHashmaps) {
		super();
		_ArcAndNodeLinkedList = _ArcAndNodeLinkedList2;
		_ArcAndNodeHashmaps = ArcAndNodeHashmaps;
		initGUI();
	}

	/**
	 * Inits the gui.
	 */
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			GridLayout thisLayout = new GridLayout(1, 1);
			thisLayout.setColumns(1);
			thisLayout.setHgap(5);
			thisLayout.setVgap(5);
			getContentPane().setLayout(thisLayout);
			{
				jTabbedPane1 = new JTabbedPane();
				getContentPane().add(jTabbedPane1);
				jTabbedPane1.setPreferredSize(new java.awt.Dimension(454, 402));
				{
					jPanel3 = new JPanel();
					jTabbedPane1.addTab("jPanel3", null, jPanel3, null);
					FlowLayout jPanel3Layout = new FlowLayout();
					jPanel3.setLayout(jPanel3Layout);
					jPanel3.setPreferredSize(new java.awt.Dimension(527, 374));
					jPanel3.setBorder(BorderFactory
							.createEtchedBorder(BevelBorder.LOWERED));
					{
						jbtnAddStraight = new JButton();
						jPanel3.add(jbtnAddStraight);
						// FlowLayout jButton1Layout = new FlowLayout();
						// jbtnAddStraight.setLayout(null);
						jbtnAddStraight.setText("Add Straight");
						jbtnAddStraight.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								jbtnAddStraightActionPerformed(evt);
							}
						});
					}
					{
						jbtnAddRightCurve = new JButton();
						jPanel3.add(jbtnAddRightCurve);
						jbtnAddRightCurve.setText("Add Right Curve");
						jbtnAddRightCurve
								.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent evt) {
										jbtnAddRightCurveActionPerformed(evt);
									}
								});
					}
					{
						jBtnAddLeftCurve = new JButton();
						FlowLayout jBtnAddLeftCurveLayout = new FlowLayout();
						jBtnAddLeftCurve.setLayout(jBtnAddLeftCurveLayout);
						jPanel3.add(jBtnAddLeftCurve);
						jBtnAddLeftCurve.setText("Add Left Curve");
						jBtnAddLeftCurve
								.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent evt) {
										jBtnAddLeftCurveActionPerformed(evt);
									}
								});
					}
					{
						jBtnAddExtend = new JButton();
						jPanel3.add(jBtnAddExtend);
						jBtnAddExtend.setText("Add Extend");
						jBtnAddExtend.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								jBtnAddExtendActionPerformed(evt);
							}
						});
					}
					{
						jbtnClear = new JButton();
						jPanel3.add(jbtnClear);
						jbtnClear.setText("Clear");
						jbtnClear.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								jbtnClearActionPerformed(evt);
							}
						});
					}
					{
						jbtn_UpdateGraph = new JButton();
						jPanel3.add(jbtn_UpdateGraph);
						jbtn_UpdateGraph.setText("Update Graph");
						jbtn_UpdateGraph
								.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent evt) {
										jbtn_UpdateGraphActionPerformed(evt);
									}
								});
					}
					{
						jButton1 = new JButton();
						jPanel3.add(jButton1);
						jButton1.setText("Remove Last Element");
						jButton1.setPreferredSize(new java.awt.Dimension(143,
								23));
						jButton1.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								jBtnRemoveLastElementActionPerformed(evt);
							}
						});
					}
					{
						jButton2 = new JButton();
						jPanel3.add(jButton2);
						jButton2.setText("Show Nodes");
						jButton2.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								jBtnToggleNodesActionPerformed(evt);
							}
						});
					}
					{
						jBtnStations = new JButton();
						jPanel3.add(jBtnStations);
						jBtnStations.setText("Show Stations");
						jBtnStations.setPreferredSize(new java.awt.Dimension(
								94, 22));
						jBtnStations.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								jButtonToggleStations(evt);
							}
						});
					}
				}
				{
					jPanel2 = new JPanel();
					jTabbedPane1.addTab("jPanel2", null, jPanel2, null);
					FlowLayout jPanel2Layout = new FlowLayout();
					jPanel2.setPreferredSize(new java.awt.Dimension(193, 439));
					jPanel2.setLayout(jPanel2Layout);
					jPanel2.setBorder(BorderFactory.createCompoundBorder(null,
							null));
					{
						jbtnAdd = new JButton();
						jPanel2.add(jbtnAdd);
						FlowLayout jButton3Layout = new FlowLayout();
						jbtnAdd.setLayout(null);
						jbtnAdd.setText("Add");
						jbtnAdd.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								// 1//2//3//99System.out.print("jbtnAdd.actionPerformed, event="+evt);
								// TODO add your code for
								// jbtnAdd.actionPerformed
								SortableDefaultMutableTreeNode parent = (SortableDefaultMutableTreeNode) myJTree
										.getLastSelectedPathComponent();

								if (parent == null) {
									JOptionPane
											.showMessageDialog(null,
													"No node in the left tree is selected");
									return;
								}

								// Enter a new node
								String nodeName = JOptionPane.showInputDialog(
										null, "Enter a child node for "
												+ parent, "Add a Child",
										JOptionPane.QUESTION_MESSAGE);

								// Insert the new node as a child of treeNode
								SortableDefaultMutableTreeNode mynode = new SortableDefaultMutableTreeNode(
										nodeName);
								parent.add(mynode);

								// Reload the model since a new tree node is
								// added
								((DefaultTreeModel) (myJTree.getModel()))
										.reload();
								// ((DefaultTreeModel)
								// (jTree2.getModel())).reload();
								// make the node visible by scroll to it
								displayNode(mynode);
							}

						});
					}
					{
						jbtnRemove = new JButton();
						jPanel2.add(jbtnRemove);
						FlowLayout jButton2Layout = new FlowLayout();
						jbtnRemove.setLayout(null);
						jbtnRemove.setText("Remove");
						jbtnRemove.setPreferredSize(new java.awt.Dimension(57,
								21));
						jbtnRemove.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								// 1//2//3//99System.out.print("jbtnRemove.actionPerformed, event="+evt);
								// TODO add your code for
								// jbtnRemove.actionPerformed
								// Get all selected paths
								TreePath[] paths = myJTree.getSelectionPaths();

								if (paths == null) {
									JOptionPane.showMessageDialog(null,
											"No node in the tree is selected");
									return;
								}

								// Remove all selected nodes
								for (int i = 0; i < paths.length; i++) {
									DefaultMutableTreeNode node = (DefaultMutableTreeNode) (paths[i]
											.getLastPathComponent());

									if (node.isRoot()) {
										JOptionPane.showMessageDialog(null,
												"Cannot remove the root");
									} else
										node.removeFromParent();
								}

								// Reload the model since a tree node is removed
								((DefaultTreeModel) (myJTree.getModel()))
										.reload();
								updateTree();
							}
						});
					}
					{
						jbtnSave = new JButton();
						jPanel2.add(jbtnSave);
						jbtnSave.setText("Save Tree");
						jbtnSave.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								// 1//2//3//99System.out.print("jbtnSave.actionPerformed, event="+evt);
								// TODO add your code for
								// jbtnSave.actionPerformed
								try {
									// final File serFile = new
									// File("jtreefiles\\JTree.dat");
									File lastfilelocation = getFileLocation();
									File serFile = lastfilelocation;
									// 3//99System.out.print("last file location"
									// + lastfilelocation.getPath());
									SaveTree(lastfilelocation);
									setFileLocation(lastfilelocation);
									// 1//2//3//99System.out.print("last file location"
									// + lastfilelocation.getPath());
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
					}
					{
						jbtnLoadTree = new JButton();
						jPanel2.add(jbtnLoadTree);
						jbtnLoadTree.setText("Load Tree");
						jbtnLoadTree.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								// 1//2//3//99System.out.print("jbtnLoadTree.actionPerformed, event="+evt);
								// TODO add your code for
								// jbtnLoadTree.actionPerformed
								try {
									// File serFile = new
									// File("jtreefiles\\JTree.dat");
									File lastfilelocation = getFileLocation();

									// 1//2//3//99System.out.print("last file location: "
									// + lastfilelocation.getPath());
									File serFile = lastfilelocation;
									// File serFile =
									LoadTree(serFile);

								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ClassNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
					}
					{
						jbtnSaveAs = new JButton();
						jPanel2.add(jbtnSaveAs);
						jbtnSaveAs.setText("Save As");
						jbtnSaveAs.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								jbtnSaveAsActionPerformed(evt);
							}
						});
					}
					{
						jbtnLoadFrom = new JButton();
						jPanel2.add(jbtnLoadFrom);
						jbtnLoadFrom.setText("Load From");
						jbtnLoadFrom.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								jbtnLoadFromActionPerformed(evt);
							}
						});
					}
					{
						jBtnSort = new JButton();
						jPanel2.add(jBtnSort);
						jBtnSort.setText("Sort JTree");
						jBtnSort.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								jBtnSortActionPerformed(evt);
							}
						});
					}
				}
				{
					jlblSaveAsFile = new JLabel();
					jTabbedPane1.addTab("jlblSaveAsFile", null, jlblSaveAsFile,
							null);
					jlblSaveAsFile.setText("Last Saved File");
					jlblSaveAsFile.setOpaque(true);
					jlblSaveAsFile.setPreferredSize(new java.awt.Dimension(154,
							66));
					jlblSaveAsFile.setAutoscrolls(true);
				}
				{
					jPanel4 = new JPanel();
					jTabbedPane1.addTab("train", null, jPanel4, null);
					{
						jbtnSetRoute = new JButton();
						jPanel4.add(jbtnSetRoute);
						jbtnSetRoute.setName("jbtnSetRoute");
					}
					{
						jbtn_TrainRoute = new JButton();
						jPanel4.add(jbtn_TrainRoute);
						jbtn_TrainRoute.setName("jbtn_TrainRoute");
					}
					{
						jbtnInit = new JButton();
						jPanel4.add(jbtnInit);
						;
						jbtnInit.setName("jbtnInit");
						jbtnInit.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								// 2//3//99System.out.print("jbtnInit.actionPerformed, event="+evt);
								E1.e_setTrainPosition(myJTree);
							}
						});
						jbtnInit.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent evt) {
								jbtnInitMouseClicked(evt);

							}
						});
					}
					{
						jbtnStart = new JButton();
						jPanel4.add(jbtnStart);
						jbtnStart.setName("jbtnStart");
					}
					{
						jbtn_StopPlace = new JButton();
						jPanel4.add(jbtn_StopPlace);
						jbtn_StopPlace.setName("jbtn_StopPlace");
						jbtn_StopPlace.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								E1.e_initialise(myJTree);
							}

						});
					}
				}
			}
			{
				jScrollPane1 = new JScrollPane();
				getContentPane().add(jScrollPane1);
				jScrollPane1.setPreferredSize(new java.awt.Dimension(298, 469));
				{
					jPanel1 = new JPanel();
					jScrollPane1.setViewportView(jPanel1);
					BorderLayout jPanel1Layout = new BorderLayout();
					jPanel1.setLayout(jPanel1Layout);
					jPanel1.setPreferredSize(new java.awt.Dimension(282, 775));
					{
						if (model == null) {
							model = new DefaultTreeModel(
									new SortableDefaultMutableTreeNode("root"));
						}
						myJTree = new JTree(model);
						// / The JTree can get big, so allow it to scroll
						// JScrollPane scrollpane = new JScrollPane(tree);
						jPanel1.add(myJTree, BorderLayout.NORTH);
						myJTree.setEditable(true);
						myJTree.setPreferredSize(new java.awt.Dimension(309,
								900));
						// myJTree.setPreferredSize(null);
						myJTree.addTreeSelectionListener(new TreeSelectionListener() {
							public void valueChanged(TreeSelectionEvent evt) {
								myJTreeValueChanged(evt);
							}
						});
					}
				}
			}

			// Load the last file saved
			// File serFile = new File("jtreefiles\\JTree.dat");
			File serFile = getFileLocation();

			// 3//99System.out.print("last file location: " +
			// lastfilelocation.getPath());
			// File serFile = lastfilelocation;

			LoadTree(serFile);

			pack();
			this.setSize(617, 505);
			Application.getInstance().getContext().getResourceMap(getClass())
					.injectComponents(getContentPane());
		} catch (Exception e) {
			// add your error handling code here
			e.printStackTrace();
		}
	}

	/**
	 * Jbtn add right curve action performed.
	 * 
	 * @param evt
	 *            the evt
	 */
	private void jbtnAddRightCurveActionPerformed(ActionEvent evt) {
		// 1//2//3//99System.out.print("jbtnAddRightCurve.actionPerformed, event="+evt);
		// TODO add your code for jbtnAddRightCurve.actionPerformed
		addStringToNode("RC");
	}

	/**
	 * Jbtn add straight action performed.
	 * 
	 * @param evt
	 *            the evt
	 */
	private void jbtnAddStraightActionPerformed(ActionEvent evt) {
		// 1//2//3//99System.out.print("jbtnAddStraight.actionPerformed, event="+evt);
		// TODO add your code for jbtnAddStraight.actionPerformed
		addStringToNode("ST");
	}

	private void jBtnAddLeftCurveActionPerformed(ActionEvent evt) {
		// 1//2//3//99System.out.print("jButton1.actionPerformed, event="+evt);
		// TODO add your code for jButton1.actionPerformed
		addStringToNode("LC");
	}

	private void jBtnAddExtendActionPerformed(ActionEvent evt) {
		// 1//2//3//99System.out.print("jButton2.actionPerformed, event="+evt);
		// TODO add your code for jButton2.actionPerformed
		addStringToNode("STE");
	}

	/**
	 * Adds the string to node.
	 * 
	 * @param myToken
	 *            the my token
	 */
	private void addStringToNode(String myToken) {
		TreePath path = myJTree.getLeadSelectionPath();
		Object last = path.getLastPathComponent();
		if (!(last instanceof SortableDefaultMutableTreeNode)) {
			return;
		}
		SortableDefaultMutableTreeNode node = (SortableDefaultMutableTreeNode) last;
		// node.setUserObject((String)node.getUserObject()+","+myToken);
		addToken(node, myToken, ",");
		innerModel.nodeChanged(node);
		// Reload the model since a new tree node is added
		((DefaultTreeModel) (myJTree.getModel())).reload();
		displayNode(node);
		updateGraph();
	}

	private void removeLastToken() {
		TreePath path = myJTree.getLeadSelectionPath();
		Object last = path.getLastPathComponent();
		if (!(last instanceof SortableDefaultMutableTreeNode)) {
			return;
		}
		SortableDefaultMutableTreeNode node = (SortableDefaultMutableTreeNode) last;
		// node.setUserObject((String)node.getUserObject()+","+myToken);
		removeLastToken(node, ",");
		innerModel.nodeChanged(node);
		// Reload the model since a new tree node is added
		((DefaultTreeModel) (myJTree.getModel())).reload();
		displayNode(node);
	}

	/**
	 * Display node.
	 * 
	 * @param mynode
	 *            the mynode
	 */
	private void updateTree() {
		_ArcAndNodeLinkedList.set_jTree(myJTree);
		_ArcAndNodeLinkedList.traverseTreeSettingUpTrackNodesAndArcs();
	}

	public void displayNode(SortableDefaultMutableTreeNode mynode) {

		_ArcAndNodeLinkedList.set_jTree(myJTree);
		_ArcAndNodeLinkedList.traverseTreeSettingUpTrackNodesAndArcs();
		// _ArcAndNodeHashmaps.calculateHashmapsAndParameters();

		// Calculate Hashmaps pointing to nodes and arcs parameters
		// 1. set up the link to the lists
		_ArcAndNodeHashmaps.setArcAndNodeLinkedLists(_ArcAndNodeLinkedList);
		// 2. Calculate the hashmaps and the parameters
		_ArcAndNodeHashmaps.calculateHashmapsAndParameters();

		// //Calculate BackgroundGroup for track
		_modelBranchGroup.set_HM(_ArcAndNodeLinkedList, _ArcAndNodeHashmaps);
		_modelBranchGroup.set_BG();

		// _ArcAndNodeLinkedList.expandAll(myJTree);
		// TreeNode[] nodes = model.getPathToRoot(mynode);
		// TreePath path = new TreePath(nodes);
		// myJTree.scrollPathToVisible(path);
		// myJTree.setSelectionPath(path);
		//
		//
		//
		//
		// sceneModel.getPanel().removeTrack();
		// //1//2//3//99System.out.print("removedTrack");
		// sceneModel.getPanel().addTrack(myJTree);

		// scene.setJTree(myJTree);
		// scene.setTrackBG();
	}

	/**
	 * Sets the token.
	 * 
	 * @param node
	 *            the node
	 * @param s
	 *            the s
	 */
	static void addToken(SortableDefaultMutableTreeNode node, String s,
			String delimeter) {
		if ((String) node.getUserObject() == ":") {
			node.setUserObject(s);
		} else {
			node.setUserObject((String) node.getUserObject() + delimeter + s);
		}
	}

	static void removeLastToken(SortableDefaultMutableTreeNode node,
			String delimeter) {

		String existingString = (String) node.getUserObject();
		String[] splitString = existingString.split(",");
		String shortenedString = null;
		if (splitString.length == 1) {
			shortenedString = ":";
			node.setUserObject(shortenedString);
		}
		for (int i = 0; i < splitString.length - 1; i++) {
			if (i == 0) {
				shortenedString = splitString[0];
			} else {
				shortenedString = shortenedString + delimeter + splitString[i];
			}
		}
		node.setUserObject(shortenedString);
	}

	/**
	 * Save tree.
	 * 
	 * @param serFile
	 *            the ser file
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void SaveTree(File serFile) throws FileNotFoundException,
			IOException {

		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
				serFile));
		oos.writeObject(model);
		oos.close();

	}

	/**
	 * Load tree.
	 * 
	 * @param serFile
	 *            the ser file
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public void LoadTree(File serFile) throws FileNotFoundException,
			IOException, ClassNotFoundException {
		// ObjectInputStream input =
		// new ObjectInputStream(new FileInputStream("JTree.dat"));
		// jTree1 = (JTree)(input.readObject());
		// jTree1.setEditable(true);
		// ((DefaultTreeModel) (jTree1.getModel())).reload();

		if (serFile.length() > 0) {
			//99System.out.print("serFile.getPath() = " + serFile.getPath());
			try {
				ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream(serFile));
				model = (DefaultTreeModel) ois.readObject();
				ois.close();
			} catch (IOException err) {
				err.printStackTrace();
			} catch (ClassNotFoundException err) {
				err.printStackTrace();
			}
		} else {
			//99System.out.print("serFile.getPath() = " + serFile.getPath());
			//99System.out.print("cannot read from file");
		}

		if (model == null) {
			model = new DefaultTreeModel(new SortableDefaultMutableTreeNode(
					"root"));
		}

		// final DefaultTreeModel innerModel = model;

		// JFrame frame = new JFrame("Tree Demo");
		// final JTree tree = new JTree(model);

		myJTree.setModel(model);
		;
		// initGUI();

	}

	/**
	 * Gets the jbtn load tree.
	 * 
	 * @return the jbtn load tree
	 */
	public JButton getJbtnLoadTree() {
		return jbtnLoadTree;
	}

	/**
	 * Jbtn save as action performed.
	 * 
	 * @param evt
	 *            the evt
	 */
	private void jbtnSaveAsActionPerformed(ActionEvent evt) {
		// 1//2//3//99System.out.print("jbtnSaveAs.actionPerformed, event="+evt);
		// TODO add your code for jbtnSaveAs.actionPerformed
		File lastfilelocation = getFileLocation();
		final JFileChooser fc = new JFileChooser(lastfilelocation);

		// In response to a button click:
		int returnVal = fc.showSaveDialog(B_myGUI.this);

		if (returnVal != JFileChooser.APPROVE_OPTION) {
			// user cancelled save action
			JOptionPane.showMessageDialog(fc, "cancelled");
		} else {
			File serFile = fc.getSelectedFile();
			try {
				SaveTree(serFile);
				setFileLocation(serFile);
				// 3//99System.out.print("New Location:" + serFile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * Jbtn load from action performed.
	 * 
	 * @param evt
	 *            the evt
	 */
	private void jbtnLoadFromActionPerformed(ActionEvent evt) {
		// 3//99System.out.print("jbtnLoadFrom.actionPerformed, event="+evt);
		// TODO add your code for jbtnLoadFrom.actionPerformed
		File lastFileLocation = getFileLocation();
		// 3//99System.out.print("last file location: " +
		// lastFileLocation.getPath());

		// JFileChooser fileChooser = new
		// JFileChooser(System.getProperty("user.dir"));
		JFileChooser fileChooser = new JFileChooser(lastFileLocation);
		int returnVal = fileChooser.showOpenDialog(B_myGUI.this);
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			// user cancelled load action
			JOptionPane.showMessageDialog(fileChooser, "cancelled");
		} else {
			File serFile = fileChooser.getSelectedFile();
			// 1//2//3//99System.out.print("chosen location: " +
			// serFile.getPath());
			try {
				LoadTree(serFile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setFileLocation(serFile);

		}
	}

	/**
	 * Sets the file location.
	 * 
	 * @param serFile
	 *            the new file location
	 */
	private void setFileLocation(File serFile) {
		// TODO Auto-generated method stub
		try {
			// String fos = U4_Constants.projectlocation +
			// "\\jtreefiles\\JTree.def";
			// String fos = "\\jtreefiles\\JTree.def";
			// ObjectOutputStream oos = new ObjectOutputStream(new
			// FileOutputStream(fos));
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream("jtreefiles\\JTree.def"));
			oos.writeObject(serFile);
			wrapLabelText(this.jlblSaveAsFile,
					"Default file: " + serFile.getName());
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Gets the file location.
	 * 
	 * @return the file location
	 */
	private File getFileLocation() {

		// System.setProperty("user.dir", U4_Constants.projectlocation);
		// System.setProperty("user.dir",
		// "C:\\Users\\bill\\EclipseWorkspace\\java3dbill");
		// System.setProperty("user.dir", "G:\\EclipseWorkspace\\java3dbill");
		File dir1 = new File(".");
		File dir2 = new File("..");
		try {
			//99System.out.print("Current dir : " + dir1.getCanonicalPath());
			//99System.out.print("Parent  dir : " + dir2.getCanonicalPath());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			// String prefix = System.getProperty("user.dir");
			// String prefix = "C:\\Users\\bill\\AppData\\Local\\Temp";
			//String prefix = "C:\\Users\\bill\\EclipseWorkspace\\control";
			String prefix = ".";
			////99//99System.out.print("USERDIR = " +
			// System.getProperty("user.dir"));
			prefix = dir1.getCanonicalPath();
			String filename = prefix + "\\jtreefiles\\JTree.def";
			//99System.out.print("prefix = " + prefix);
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
					filename));
			File serFile = (File) ois.readObject();
			//99System.out.print("getpath: " + serFile.getPath());
			//99System.out.print("serFile.getName() = " + serFile.getName());
			//99System.out.print("serfileexists =" + serFile.exists());
			//99System.out.print("serfilelength =" + serFile.length());
			wrapLabelText(this.jlblSaveAsFile,
					"Default file: " + serFile.getName());
			ois.close();


			// //3//99System.out.print("Hi");
			// //String jtreedef = U4_Constants.projectlocation +
			// "\\jtreefiles\\JTree.def";
			// //ObjectInputStream ois = new ObjectInputStream(new
			// FileInputStream(jtreedef));
			// //ObjectInputStream ois = new ObjectInputStream(new
			// FileInputStream("C:\\Users\\bill\\EclipseWorkspace\\java3dbill\\jtreefiles\\JTree.def"));
			////99//99System.out.print("USERDIR = " +
			// System.getProperty("user.dir"));
			// //String filename =
			// "C:\\Users\\bill\\EclipseWorkspace\\java3dbill\\jtreefiles\\JTree.def";
			// //String filename = "jtreefiles\\JTree.def";
			// String prefix = System.getProperty("user.dir");
			// prefix = "C:\\Users\\bill\\EclipseWorkspace\\java3dbill";
			////99//99System.out.print("USERDIR = " +
			// System.getProperty("user.dir"));
			// String filename = prefix + "\\jtreefiles\\JTree.def";
			// //ObjectInputStream ois = new ObjectInputStream(new
			// FileInputStream("jtreefiles\\JTree.def"));
			// ObjectInputStream ois = new ObjectInputStream(new
			// FileInputStream(filename));
			// ObjectInputStream ois = new ObjectInputStream(new
			// FileInputStream(filename));
			// //3//99System.out.print("Lo");
			// File serFile = (File)ois.readObject();
			////99//99System.out.print("getpath: " + serFile.getPath());
			// //this.jlblSaveAsFile.setText("Default file: " +
			// serFile.getName());
			// wrapLabelText(this.jlblSaveAsFile,"Default file: " +
			// serFile.getName());
			// ois.close();
			// //serFile = "NewJTreeDesign2";
			////99//99System.out.print("serFile.getName() = " + serFile.getName());
			////99//99System.out.print("serfileexists =" + serFile.exists());
			////99//99System.out.print("serfilelength =" + serFile.length());
			File file1 = new File(prefix + "\\jtreefiles\\" + serFile.getName());
			//return serFile;
			return file1;
		} catch (IOException err) {
			err.printStackTrace();
			return null;
		} catch (ClassNotFoundException err) {
			err.printStackTrace();
			return null;
		}

	}

	/**
	 * Wrap label text.
	 * 
	 * @param label
	 *            the label
	 * @param text
	 *            the text
	 */
	private void wrapLabelText(JLabel label, String text) {
		FontMetrics fm = label.getFontMetrics(label.getFont());
		Container container = label.getParent();
		int containerWidth = container.getWidth();

		BreakIterator boundary = BreakIterator.getWordInstance();
		boundary.setText(text);

		StringBuffer trial = new StringBuffer();
		StringBuffer real = new StringBuffer("<html>");

		int start = boundary.first();
		for (int end = boundary.next(); end != BreakIterator.DONE; start = end, end = boundary
				.next()) {
			String word = text.substring(start, end);
			trial.append(word);
			int trialWidth = SwingUtilities.computeStringWidth(fm,
					trial.toString());
			if (trialWidth > containerWidth) {
				trial = new StringBuffer(word);
				real.append("<br>");
			}
			real.append(word);
		}

		real.append("</html>");

		label.setText(real.toString());
	}

	/**
	 * Jbtn clear action performed.
	 * 
	 * @param evt
	 *            the evt
	 */
	private void jbtnClearActionPerformed(ActionEvent evt) {
		// 1//2//3//99System.out.print("jbtnClear.actionPerformed, event="+evt);
		// TODO add your code for jbtnClear.actionPerformed
		TreePath path = myJTree.getLeadSelectionPath();
		Object last = path.getLastPathComponent();
		if (!(last instanceof SortableDefaultMutableTreeNode)) {
			return;
		}
		SortableDefaultMutableTreeNode node = (SortableDefaultMutableTreeNode) last;
		// node.setUserObject((String)node.getUserObject()+","+myToken);
		// setToken(node,":");
		node.setUserObject(":");
		innerModel.nodeChanged(node);
		// Reload the model since a new tree node is added
		((DefaultTreeModel) (myJTree.getModel())).reload();
		displayNode(node);
	}

	private void jbtn_UpdateGraphActionPerformed(ActionEvent evt) {
		// 1//2//3//99System.out.print("jbtn_UpdateGraph.actionPerformed, event="+evt);
		// TODO add your code for jbtn_UpdateGraph.actionPerformed
		updateGraph();
	}

	public void updateGraph() {
		((DefaultTreeModel) (myJTree.getModel())).reload();
		// displayNode(node);

		sceneModel.getPanel().removeTrack();
		// 1//2//3//99System.out.print("removedTrack");
		sceneModel.getPanel().addTrack();
	}

	private void jBtnRemoveLastElementActionPerformed(ActionEvent evt) {
		// 1//2//3//99System.out.print("jButton1.actionPerformed, event="+evt);
		removeLastToken();
		updateGraph();
	}

	private void jBtnToggleNodesActionPerformed(ActionEvent evt) {
		// 1//2//3//99System.out.print("ToggleNodes.actionPerformed, event="+evt);
		boolean toggleNode = true;
		// TODO add your code for jButton2.actionPerformed
		traversetree(toggleNode, false);
		// Reload the model since a new tree node is added
		((DefaultTreeModel) (myJTree.getModel())).reload();
		// displayNode(node);
		updateGraph();
	}

	private void jButtonToggleStations(ActionEvent evt) {
		// 1//2//3//99System.out.print("jButton3.actionPerformed, event="+evt);
		// TODO add your code for jButton3.actionPerformed
		boolean toggleStations = true;
		// TODO add your code for jButton2.actionPerformed
		traversetree(false, toggleStations);
		// Reload the model since a new tree node is added
		((DefaultTreeModel) (myJTree.getModel())).reload();
		updateGraph();
	}

	public void traversetree(boolean toggleNode, boolean toggleStations) {
		int level = 0;
		if (myJTree != null) {
			TreeModel model = myJTree.getModel();
			if (model != null) {
				Object root = model.getRoot();
				// 1//2//3//99System.out.print(root.toString());
				level = 1;
				walk(model, root, level, toggleNode, toggleStations);
			} else
				System.out.print("Tree is empty.");
		} else
			System.out.print("JTree is null");
	}

	protected void walk(TreeModel model, Object o, int level,
			boolean bln_toggleNode, boolean toggleStations) {
		int cc;
		cc = model.getChildCount(o);

		for (int i = 0; i < cc; i++) {
			Object child = model.getChild(o, i);
			if (model.isLeaf(child)) {
				// 1//2//3//99System.out.print("level:" + level + " " +
				// child.toString());
				if (bln_toggleNode && level == 1) {
					String Nodechar = "N";
					toggleNode((SortableDefaultMutableTreeNode) child,
							Nodechar, "_");
				}
				if (toggleStations && level == 1) {
					String Nodechar = "S";
					toggleNode((SortableDefaultMutableTreeNode) child,
							Nodechar, "_");
				}
			} else {
				// 3//99System.out.print.print("level:" + level + " " +
				// child.toString()+" -- ");

				if (bln_toggleNode && level == 1) {
					String Nodechar = "N";
					toggleNode((SortableDefaultMutableTreeNode) child,
							Nodechar, "_");
				}
				if (toggleStations && level == 1) {
					String Nodechar = "S";
					toggleNode((SortableDefaultMutableTreeNode) child,
							Nodechar, "_");
				}
				level++;
				// walk(model,child );
				level--;
			}
		}

	}

	static void toggleNode(SortableDefaultMutableTreeNode node,
			String myString, String delimeter) {
		String existingString = (String) node.getUserObject();
		// if (existingString.matches(".*"+delimeter+".*")){
		String[] nodes = existingString.split("[" + delimeter + "|\\s]");
		boolean myStringPresent = false;
		for (String item : nodes) {
			if (item.equals(myString)) {
				myStringPresent = true;
			}
		}

		if (myStringPresent) {
			// remove myString
			removeToken(node, myString, delimeter);
		} else {
			// insert myString
			addToken(node, myString, delimeter);
		}
		// }
	}

	private static void removeToken(SortableDefaultMutableTreeNode node,
			String stringToFind, String delimeter) {
		String existingString = (String) node.getUserObject();
		String updatedString = "";
		if (existingString.matches(".*" + delimeter + ".*")) {
			String[] nodes = existingString.split("[" + delimeter + "|\\s]");
			for (String item : nodes) {
				if (item.equals(stringToFind)) {
					// do nothing
				} else if (updatedString.equals("")) {
					updatedString = item;
				} else {
					updatedString = updatedString + delimeter + item;
				}
			}
		}
		node.setUserObject(updatedString);
	}

	private void jbtnInitMouseClicked(MouseEvent evt) {
		// 1//2//3//99System.out.print("jbtnInit.mouseClicked, event="+evt);
		// TODO add your code for jbtnInit.mouseClicked
		// SimpleBehavior sBehav = scene.getPanel().get_myDoAll().getSbehav();
		Container c = new Container();
		c.dispatchEvent(evt);
	}

	private void jBtnSortActionPerformed(ActionEvent evt) {
		// 1//2//3//99System.out.print("jBtnSort.actionPerformed, event="+evt);
		model.reload();

	}

	public static void set_jTree(JTree myJTree2) {
		myJTree = myJTree2;
		TreePath path = U2_Tree.findByName(myJTree, new String[] { "root" });
		MutableTreeNode node = (MutableTreeNode) path.getLastPathComponent();
		((DefaultTreeModel) myJTree.getModel()).nodeStructureChanged(node);
	}

	private void myJTreeValueChanged(TreeSelectionEvent evt) {
		// 2//3//99System.out.print("myJTreeValueChanged, event="+evt);
		// //_ArcAndNodeLinkedList.set_jTree(myJTree);

		// ((DefaultTreeModel) (myJTree.getModel())).reload();
		// _ArcAndNodeLinkedList.traverseTreeSettingUpTrackNodesAndArcs();
		// _ArcAndNodeHashmaps.calculateHashmapsAndParameters();
	}

	public void set_modelBranchGroup(C1_BranchGroup _modelBranchGroup) {
		this._modelBranchGroup = _modelBranchGroup;
	}

}
