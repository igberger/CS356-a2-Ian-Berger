import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;


public class AdminFrameTest extends JFrame implements TreeSelectionListener {

	private JPanel contentPane;
	private JTree tree;
	private DefaultMutableTreeNode  top;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminFrameTest frame = new AdminFrameTest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdminFrameTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 200, 366);
		contentPane.add(scrollPane);
		
		top = new DefaultMutableTreeNode("The Java Series");
	    createNodes(top);
	    tree = new JTree(top);
	    
	    tree.getSelectionModel().setSelectionMode
        (TreeSelectionModel.SINGLE_TREE_SELECTION);
	    
	    tree.addTreeSelectionListener(this);
		scrollPane.setViewportView(tree);
		
		
	}
	
    public void valueChanged(TreeSelectionEvent event) {
    	DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();

	    if (node == null)
	    //Nothing is selected.     
	    return;
	
	    Object nodeInfo = node.getUserObject();
	    if (node.isLeaf()) {
	    	top.add(node);
	    } else {
	    	try {
				UserFrameTest frame = new UserFrameTest();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
    }
	private void createNodes(DefaultMutableTreeNode top) {
		DefaultMutableTreeNode category = null;
	    DefaultMutableTreeNode book = null;
	    
	    category = new DefaultMutableTreeNode("Books for Java Programmers");
	    top.add(category);
	    
	    //original Tutorial
	    book = new DefaultMutableTreeNode
	        ("The Java Tutorial: A Short Course on the Basics");
	    category.add(book);
	    
	    //Tutorial Continued
	    book = new DefaultMutableTreeNode("The Java Tutorial Continued: The Rest of the JDK");
	    category.add(book);
	    
	    //Swing Tutorial
	    book = new DefaultMutableTreeNode("The Swing Tutorial: A Guide to Constructing GUIs");
	    category.add(book);

	    //...add more books for programmers...

	    category = new DefaultMutableTreeNode("Books for Java Implementers");
	    top.add(category);

	    //VM
	    book = new DefaultMutableTreeNode("The Java Virtual Machine Specification");
	    category.add(book);

	    //Language Spec
	    book = new DefaultMutableTreeNode("The Java Language Specification");
	    category.add(book);
	}
}
