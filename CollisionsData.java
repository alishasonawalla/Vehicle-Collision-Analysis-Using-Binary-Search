/**
 * This class stores all the collision objects into an AVL tree.
 * It provides methods to add and remove into the AVL tree by balancing the tree.
 * It also provides a getReport method to produce the output of the tree traversal.
 *
 * Note: several parts of the code are inspired by implementation
 * provided by Joanna Klukowska
 *
 * @author Alisha Sonawalla
 * @version 12/11/2017
 */

public class CollisionsData {
	// root of the tree
	protected Node root;
	// current number of nodes in the tree
	protected int numOfElements;
	//helper variable used by the remove methods
	private boolean found;

	/*
	 * Default constructor that creates an empty tree.
	 */
	public CollisionsData() {
		this.root = null;
		numOfElements = 0;
	}

	/*
	 * Add the given data item to the tree. If item is null, the tree does not
	 * change. If item already exists, the tree does not change.
	 *
	 * @param item the new element to be added to the tree
	 */
	public void add(Collision item) {
		if (item == null)
			return;
		root = add (root, item);

	}

	/*
	 * Actual recursive implementation of add.
	 *
	 * @param node, item the new element to be added to the tree and root of tree
	 * @return node node added or the modified subtree
	 */
	private Node add(Node node, Collision item) {
		// Perform the actual recursive BST add.
		if (node == null) {
			numOfElements++;
			return new Node(item);
		}

		//Compare the node data and the item data to determine
		//direction of traversal
		if (node.data.compareTo(item) > 0)
			node.left = add(node.left, item);
		else if (node.data.compareTo(item) <= 0)
			node.right = add(node.right, item);

		// Update the height of parent/ancestor.
		updateHeight(node);

		// Determine if the insertion led to imbalance.
		int balanceFactor = getBalanceFactor(node);

		// Re-balance the AVL tree if an imbalance occurred.
		if(balanceFactor > 1) {
			if(getBalanceFactor(node.right) >= 0) return rotateRightRight(node);

			return rotateRightLeft(node);
		}
		else if(balanceFactor < -1) {
			if(getBalanceFactor(node.left) <= 0) return rotateLeftLeft(node);

			return rotateLeftRight(node);
		}

		// If no imbalance, then return the node.
		return node;
	}

	/*
	 * Remove the item from the tree. If item is null the tree remains unchanged. If
	 * item is not found in the tree, the tree remains unchanged.
	 *
	 * @param target the item to be removed from this tree
	 * @return boolean true, false based on if the item was removed
	 */
	public boolean remove(Collision target)
	{
		root = recRemove(target, root);
		return found;
	}


	/*
	 * Find the node to remove and re-balance the AVL tree
	 *   if imbalanced.
	 *
	 * @param target the item to be removed from this tree
	 *
	 * @return node  New root after deletion and re-balance.
	 */
	private Node recRemove(Collision target, Node node)
	{
		//Check if the node is null
		if (node == null)
			found = false;
		//Compare the item to node data to determine direction of traversal
		else if (target.compareTo(node.data) < 0)
			node.left = recRemove(target, node.left);
		else if (target.compareTo(node.data) > 0)
			node.right = recRemove(target, node.right );
		else {
			node = removeNode(node);
			found = true;
		}

		if(node == null) return node;

		// Update Height of the current subtree.
		updateHeight(node);

		// Determine if the deletion led to imbalance.
		int balanceFactor = getBalanceFactor(node);

		// Re-balance the AVL tree if an imbalance occurred.
		if(balanceFactor > 1) {
			if(getBalanceFactor(node.right) >= 0) return rotateRightRight(node);

			//node.left = rotateLeft(node.left);
			return rotateRightLeft(node);
		}
		else if(balanceFactor < -1) {
			if(getBalanceFactor(node.left) <= 0) return rotateLeftLeft(node);

			//node.right = rotateRightLeft(node.right);
			return rotateLeftRight(node);
		}

		// If no imbalance, then return the node.
		return node;
	}

	/*
	 * Actual recursive implementation of remove method: perform the removal.
	 *
	 * @param target the item to be removed from this tree
	 * @return a reference to the node itself, or to the modified subtree
	 */
	private Node removeNode(Node node)
	{
		Collision data;
		if (node.left == null)
			return node.right ;
		else if (node.right  == null)
			return node.left;
		else {
			data = getPredecessor(node.left);
			node.data = data;
			node.left = recRemove(data, node.left);
			return node;
		}
	}

	/*
	 * Returns the information held in the rightmost node of subtree
	 *
	 * @param subtree root of the subtree within which to search for the rightmost node
	 * @return returns data stored in the rightmost node of subtree
	 * @throws NullPointerException
	 */
	private Collision getPredecessor(Node subtree) throws NullPointerException{
		//Check for null subtree
		if (subtree == null) {
			throw new NullPointerException("getPredecessor called with an empty subtree");
		}
		Node temp = subtree;
		while (temp.right  != null)
			temp = temp.right;
		return temp.data;
	}



	/*
	 * Determines the number of elements stored in this BST.
	 *
	 * @return numberOfElements number of elements in this BST
	 */
	public int size() {
		return numOfElements;
	}

	/*
	 * Returns a string representation of this tree using an inorder traversal .
	 * @see java.lang.Object#toString()
	 * @return string representation of this tree
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		inOrderPrint(root, s);
		return s.toString();
	}

	/*
	 * Actual recursive implementation of inorder traversal to produce string
	 * representation of this tree.
	 *
	 * @param tree the root of the current subtree
	 * @param s the string that accumulated the string representation of this BST
	 */
	private void inOrderPrint(Node tree, StringBuilder s) {
		if (tree != null) {
			inOrderPrint(tree.left, s);
			s.append(tree.data.toString() + "  ");
			inOrderPrint(tree.right , s);
		}
	}

	/*
	 * Produces tree like string representation of this BST.
	 * @return string containing tree-like representation of this BST.
	 */
	public String toStringTreeFormat() {
		StringBuilder s = new StringBuilder();
		preOrderPrint(root, 0, s);
		return s.toString();
	}

	/*
	 * Actual recursive implementation of preorder traversal to produce tree-like string
	 * representation of this tree.
	 *
	 * @param tree the root of the current subtree
	 * @param level level (depth) of the current recursive call in the tree to
	 *   determine the indentation of each item
	 * @param output the string that accumulated the string representation of this
	 *   BST
	 */
	private void preOrderPrint(Node tree, int level, StringBuilder output) {
		if (tree != null) {
			String spaces = "\n";
			if (level > 0) {
				for (int i = 0; i < level - 1; i++)
					spaces += "   ";
				spaces += "|--";
			}
			output.append(spaces);
			output.append(tree.data.toString());
			preOrderPrint(tree.left, level + 1, output);
			preOrderPrint(tree.right , level + 1, output);
		}
		// uncomment the part below to show "null children" in the output
		else {
			String spaces = "\n";
			if (level > 0) {
				for (int i = 0; i < level - 1; i++)
					spaces += "   ";
				spaces += "|--";
			}
			output.append(spaces);
			output.append("null");
		}
	}

	/*
	 * Determines the maximum height of the two subtrees.
	 *
	 * @param heightOne  Height of one subtree.
	 *
	 * @param heightTwo  Height of the second subtree.
	 *
	 * @return int  Maximum of the two given heights.
	 */
	private int maxHeight(int heightOne, int heightTwo) {
		return (heightOne > heightTwo) ? heightOne : heightTwo;
	}

	/*
	 * Updates the height of the current subtree.
	 *
	 * @param tree  the root of the current subtree.
	 */
	private void updateHeight(Node subtree) {
		//Check if subtree is null
		if (subtree == null) {
			return;
		}
		//If we hit a leaf return 0
		if (subtree.left == null && subtree.right == null) subtree.height = 0;
		//If one of the subtree is null then return the height of the other +1
		else if (subtree.left == null) subtree.height = subtree.right.height+1;
		else if (subtree.right == null) subtree.height = subtree.left.height+1;
		//Otherwise return the max of the two heights +1;
		else subtree.height = maxHeight(subtree.left.height, subtree.right.height) + 1;
	}



	/*
	 * Rotates the subtree rooted at tree to left.
	 *
	 * @param tree  the root of the current subtree.
	 *
	 * @return Node  new root after the rotation.
	 */

	private Node rotateLeftLeft(Node tree) {
		Node leftChild = tree.left;
		tree.left = leftChild.right;
		leftChild.right = tree;

		// Update heights.
		updateHeight(tree);
		updateHeight(leftChild);

		// rightChild is the rew root.
		return leftChild;
	}

	/*
	 * Rotates the subtree rooted at tree to right.
	 *
	 * @param tree  the root of the current subtree.
	 *
	 * @return Node  new root after the rotation.
	 */
	private Node rotateRightRight(Node tree) {
		Node RightChild = tree.right;
		tree.right = RightChild.left;
		RightChild.left = tree;

		// Update heights.
		updateHeight(tree);
		updateHeight(RightChild);

		// rightChild is the new root.
		return RightChild;
	}


	/*
	 * Rotates the subtree rooted at tree to left then right.
	 *
	 * @param tree  the root of the current subtree.
	 *
	 * @return Node  new root after the rotation.
	 */
	private Node rotateLeftRight(Node tree) {
		Node leftChild = tree.left;
		Node rightTreeofLeftChild = leftChild.right;

		tree.left = rightTreeofLeftChild.right;
		leftChild.right = rightTreeofLeftChild.left;
		rightTreeofLeftChild.left = leftChild;
		rightTreeofLeftChild.right = tree;


		// Update heights.
		updateHeight(tree);
		updateHeight(leftChild);
		updateHeight(rightTreeofLeftChild);

		// rightChild is the new root.
		return rightTreeofLeftChild;
	}

	/*
	 * Rotates the subtree rooted at tree to right then left.
	 *
	 * @param tree  the root of the current subtree.
	 *
	 * @return Node  new root after the rotation.
	 */
	private Node rotateRightLeft(Node tree) {
		Node rightChild = tree.right;
		Node leftTreeofRightChild = rightChild.left;
		tree.right = leftTreeofRightChild.left;
		rightChild.left = leftTreeofRightChild.right;
		leftTreeofRightChild.right = rightChild;
		leftTreeofRightChild.left = tree;

		// Update heights.
		updateHeight(tree);
		updateHeight(rightChild);
		updateHeight(leftTreeofRightChild);

		// rightChild is the new root.
		return leftTreeofRightChild;
	}

	/*
	 * Retrieves the balance factor of the Node subtree.
	 *
	 * @param tree  the root of the current subtree.
	 *
	 * @return int  Balance factor of the current subtree.
	 */
	private int getBalanceFactor(Node subtree) {
		//Check if subtree is null
		if (subtree == null) {
			return 0;
		}

		//Check if both or any one of the subtrees is null
		if (subtree.right == null && subtree.left == null) return 0;
		if (subtree.right == null) return -subtree.height;
		if (subtree.left == null) return subtree.height;
		return  subtree.right.height - subtree.left.height;
	}

	/*
	 * Reports the number of fatalities and injuries for a given zip code
	 *   and Date range.
	 *
	 * @param zip  containing the zip code.
	 * @param dateBegin  Beginning of the data range.
	 * @param dateEnd  End of the date range.
	 *
	 * @return String  Information about the number of fatalities and injuries.
	 */
	public String getReport (String zip, Date dateBegin, Date dateEnd) {
		if(root ==  null)
			return "";

		//Store the data about the collisions in an array
		int [] report = new int[7];

		// Get report.
		 getReport(root, report, zip, dateBegin, dateEnd);


		 	// Print report.
			StringBuilder sb = new StringBuilder();

			if (report[0] == 0) sb.append("There are no reports to show for this zip code.");

			else{
			sb.append("Motor Vehicle Collisions for zipcode " + zip + " "+ dateBegin.toString() + " - "
				+ dateEnd.toString() + "\n");
			sb.append("====================================================================" + "\n");
			sb.append("Total number of collisions: " + String.valueOf(report[0]) + "\n");
			sb.append("Number of fatalities: " + String.valueOf(report[1] + report[2] + report[3]) + "\n");
			sb.append("         pedestrians: " + String.valueOf(report[1]) + "\n");
			sb.append("            cyclists: " + String.valueOf(report[2]) + "\n");
			sb.append("           motorists: " + String.valueOf(report[3]) + "\n");
			sb.append("Number of injuries: " + String.valueOf(report[4] + report[5] + report[6]) + "\n");
			sb.append("       pedestrians: " + String.valueOf(report[4]) + "\n");
			sb.append("          cyclists: " + String.valueOf(report[5]) + "\n");
			sb.append("         motorists: " + String.valueOf(report[6]) + "\n");
			}
			return sb.toString();

	}

	/*
	 * Reports the number of fatalities and injuries for a given zip code
	 *   and Date range.
	 *
	 * @param node  current position in the AVL tree.
	 * @param report  containing data to be reported.
	 * @param zip  containing the zip code.
	 * @param dateBegin  Beginning of the data range.
	 * @param dateEnd  End of the date range.
	 */
	public void getReport(Node node, int [] report, String zip, Date dateBegin, Date dateEnd) {
		if (node == null) return;

		//Compare the zip codes to determine direction of traversal
		if (zip.compareTo(node.data.getZip()) < 0) {
			getReport(node.left, report, zip, dateBegin, dateEnd);
		}
		else if(zip.compareTo(node.data.getZip()) > 0) {
			getReport(node.right, report, zip, dateBegin, dateEnd);
		}
		else {
			if(dateBegin.compareTo(node.data.getDate()) <= 0) {
				//After zip codes, compare by dates
				if(dateEnd.compareTo(node.data.getDate()) >= 0) {
					++report[0];
					report[1] += Integer.valueOf(node.data.getPedestriansKilled());
					report[2] += Integer.valueOf(node.data.getCyclistsKilled());
					report[3] += Integer.valueOf(node.data.getMotoristsKilled());
					report[4] += Integer.valueOf(node.data.getPedestriansInjured());
					report[5] += Integer.valueOf(node.data.getCyclistsInjured());
					report[6] += Integer.valueOf(node.data.getMotoristsInjured());

					getReport(node.left, report, zip, dateBegin, dateEnd);
					getReport(node.right, report, zip, dateBegin, dateEnd);
				}
				else {
					getReport(node.right, report, zip, dateBegin, dateEnd);
				}
			}
			else {
				getReport(node.left, report, zip, dateBegin, dateEnd);
			}
		}
	}


	/**
	 * Node class is used to represent nodes in a binary search tree.
	 * It contains a data item that has to implement Comparable interface
	 * and references to left and right subtrees.
	 *
	 * @author Joanna Klukowska
	 *
	 * @param  a reference type that implements Comparable interface
	 */
	protected static class Node {


		protected Node left;  //reference to the left subtree
		protected Node right; //reference to the right subtree
		protected Collision data;            //data item stored in the node

		protected int height;


		/*
		 * Constructs a BSTNode initializing the data part
		 * according to the parameter and setting both
		 * references to subtrees to null.
		 * @param data
		 *    data to be stored in the node
		 */
		protected Node(Collision data) {
			this.data = data;
			left = null;
			right = null;
			height = 0;
		}

		/* (non-Javadoc)
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */


		public int compareTo(Node other) {
			return this.data.compareTo(other.data);
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return data.toString();
		}


	}
}
