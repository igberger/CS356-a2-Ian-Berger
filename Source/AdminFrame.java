import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Enumeration;

import javax.swing.JTextArea;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;


public class AdminFrame extends JFrame implements TreeSelectionListener {

	private Group root;
	private JPanel contentPane;
	private JTree tree;
	private DefaultMutableTreeNode rootNode;
	private DefaultMutableTreeNode selectedNode;
	private JTextArea txtrUserId;
	private String userId;
	private JTextArea txtrGroupId;
	private String groupId;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminFrame frame = new AdminFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public AdminFrame(Group r) {
		root = r;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 300, 558);
		contentPane.add(scrollPane);
		//TODO 
		rootNode = new DefaultMutableTreeNode(root, true);
		tree = new JTree(rootNode, true);
		tree.addTreeSelectionListener(this);
		scrollPane.setViewportView(tree);
		//TODO 
		JButton btnAddUser = new JButton("Add User");
		btnAddUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(selectedNode.getUserObject() != null && selectedNode.getUserObject() instanceof Group) {
					//Check that a user with the given id does not already exist
					GetUserVisitor v = new GetUserVisitor(txtrUserId.getText());
					root.accept(v);
					if(!v.hasUser()) {
						addUserNode((Group)selectedNode.getUserObject(), new User(txtrUserId.getText()));
					} else {
						//TODO Tell user to enter a unique user id
					}
				} else {
					//TODO Tell user to first select a group node
				}
			}
		});
		btnAddUser.setBounds(777, 10, 117, 29);
		contentPane.add(btnAddUser);
		
		JButton btnAddGroup = new JButton("Add Group");
		btnAddGroup.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(selectedNode.getUserObject() != null && selectedNode.getUserObject() instanceof Group) {
					addGroupNode((Group)selectedNode.getUserObject(), new Group(txtrGroupId.getText()));
				} else {
					//TODO Tell user to first select a group node
				}
			}
		});
		btnAddGroup.setBounds(777, 50, 117, 29);
		contentPane.add(btnAddGroup);
		//TODO 
		JButton btnOpenUserView = new JButton("Open User View");
		btnOpenUserView.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if(selectedNode.getUserObject() != null && selectedNode.getUserObject() instanceof User) {
					try {
						UserFrame frame = new UserFrame((User)selectedNode.getUserObject(), root);
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					//TODO Tell user to first select a user node
				}
			}
		});
		btnOpenUserView.setBounds(322, 90, 572, 56);
		contentPane.add(btnOpenUserView);
		//TODO 
		JButton btnShowPositivePercentage = new JButton("Show Positive Percentage");
		btnShowPositivePercentage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CountPositiveMessagesVisitor v = new CountPositiveMessagesVisitor();
				root.accept(v);
				JOptionPane.showMessageDialog(null, v.getPositiveMessagePercentage() + "% of messages are positive");
			}
		});
		btnShowPositivePercentage.setBounds(618, 539, 276, 29);
		contentPane.add(btnShowPositivePercentage);
		//TODO 
		JButton btnShowMessagesTotal = new JButton("Show Messages Total");
		btnShowMessagesTotal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CountMessagesVisitor v = new CountMessagesVisitor();
				root.accept(v);
				JOptionPane.showMessageDialog(null, "Message total is " + v.getMessageCount());
			}
		});
		btnShowMessagesTotal.setBounds(322, 539, 276, 29);
		contentPane.add(btnShowMessagesTotal);
		//TODO 
		JButton btnShowUserTotal = new JButton("Show User Total");
		btnShowUserTotal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CountUsersVisitor v = new CountUsersVisitor();
				root.accept(v);
				JOptionPane.showMessageDialog(null, "User total is " + v.getUserCount());
			}
		});
		btnShowUserTotal.setBounds(322, 498, 276, 29);
		contentPane.add(btnShowUserTotal);
		//TODO 
		JButton btnShowGroupTotal = new JButton("Show Group Total");
		btnShowGroupTotal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CountGroupsVisitor v = new CountGroupsVisitor();
				root.accept(v);
				JOptionPane.showMessageDialog(null, "Group total is " + v.getGroupCount());
			}
		});
		btnShowGroupTotal.setBounds(618, 498, 276, 29);
		contentPane.add(btnShowGroupTotal);
		
		txtrUserId = new JTextArea();
		txtrUserId.setText("User ID");
		txtrUserId.setBounds(322, 10, 443, 29);
		contentPane.add(txtrUserId);
		
		txtrGroupId = new JTextArea();
		txtrGroupId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				groupId = txtrGroupId.getText();
			}
		});
		txtrGroupId.setText("Group ID");
		txtrGroupId.setBounds(322, 50, 443, 29);
		contentPane.add(txtrGroupId);
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		selectedNode = node;
	}
	
	private void addUserNode(Group group, User user) {
		group.add(user);
		selectedNode.add(new DefaultMutableTreeNode(user, false));
		tree.updateUI();
	}
	
	private void addGroupNode(Group superGroup, Group subGroup) {
		superGroup.add(subGroup);
		selectedNode.add(new DefaultMutableTreeNode(subGroup, true));
		tree.updateUI();
	}
}
