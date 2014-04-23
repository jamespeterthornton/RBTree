
public class RBNode {

	//declare our variables. I used "leaf" boolean to effectively recreate the textbook's "T.nil"
	
	public RBNode p;
	public RBNode left;
	public RBNode right;
	public boolean color;
	public int key;
	public boolean leaf;
	public boolean found;
	public boolean originalColor;

	//accept a key and whether or not this node is a leaf. If it is a leaf, the key doesn't matter but I found it more straightforward
	// to use one initialization function
	
	public RBNode(int key, boolean leaf) {
		
		//set the key and set found to false
		
		this.key = key;
		this.found = false;
		
		//if it is a leaf, create the black leaf node with null pointers for its children
		
		if (leaf == true) {	
			this.left = null;
			this.right = null;
			this.color = false;
			this.leaf = true;
		} else {
		
		//otherwise, create a blank node and give it leaves for its children and its parent
			
			RBNode leftChild = new RBNode(0, true);
			RBNode rightChild = new RBNode(0, true);
			RBNode parent = new RBNode(0, true);
			this.left = leftChild;
			this.right = rightChild;
			this.color = true;
			this.p = parent;
			this.leaf = false;
		
			System.out.println ("making new node");
		
		}
		
	}
	
}
