
public class RBTree {

	public RBNode root;

	public RBTree() {
		
		//initialize with a leaf for the root. this is an empty tree
		
		this.root = new RBNode(0, true);
		
		System.out.println("initializing tree");
		
	}
	
	public void insert(int key) {
		
		//to insert, create z, which will be the node we are inserting
		
		RBNode z = new RBNode(key, false);
		
		//create a T.nil or leaf node
		
		RBNode y = new RBNode(0, true);
		
		//and create x, which will begin with the root
		
		RBNode x = this.root;
		
		//while we still have an inner node for x
		
		while ( x.leaf != true) {
			
			//store x in y
			
			y = x;
			
			//move x down the tree in a left or right direction, depending on whether the key is lesser or greater than x.key
			
			if (key < x.key) {
				x = x.left;
			} else if (key > x.key) {
				x = x.right;
			} else return;
		}
		
		//when x is a leaf, we will exit the while loop. if the tree had no root (x =T.nil), we immediately exited and y is still a blank leaf
		// Y will now be holding the parent of the current (leaf) x. make y the parent of z
		
		z.p = y;
		if (y.leaf == true) {
			
			//if, at this point, y is a leaf then z will be the first node in the tree and is thus the root
			
			this.root = z;
		} else if (key < y.key) {
			
			//if the key being inserted is less than y's, then this should be y's left child
			
			y.left = z;
		} else {
			
			//vice versa
			
			y.right = z;
		}

		//color z red
		
		z.color = true;
		
		//call fix up
		
		this.insertFixUp(z);
		
	}
	
	public void insertFixUp(RBNode z) {
		
		while (z.p.color == true) {
				if(z.p == z.p.p.left) {
					RBNode y = z.p.p.right;
					if (y.color == true) {
						z.p.color = false;
						y.color = false;
						z.p.p.color = true;
						z = z.p.p;
					} else{ 
						if (z == z.p.right) {
							z = z.p;
							this.leftRotate(z);
						}
						z.p.color = false;
						z.p.p.color = true;
						this.rightRotate(z.p.p);
					}
				} else {
					RBNode y = z.p.p.left;
					if (y.color == true) {
						z.p.color = false;
						y.color = false;
						z.p.p.color = true;
						z = z.p.p;
					} else { 
						if (z == z.p.left) {
							z = z.p;
							this.rightRotate(z);
						}
						z.p.color = false;
						z.p.p.color = true;
						this.leftRotate(z.p.p);
					}
				}
			}
			this.root.color = false;
	}
	
	public void leftRotate(RBNode x) {
		
		System.out.println(x.key);
		RBNode y = x.right;
		System.out.println(x.right.key);
		
		System.out.println(x.right.right.key);
		
		x.right = y.left;
		
		System.out.println (y.left.key);
		if (y.left.leaf == false) y.left.p = x;
		y.p = x.p;
		if (x.p.leaf == true) this.root = y;
		else if (x == x.p.left) x.p.left = y;
		else x.p.right = y;
		y.left = x;
		x.p = y;
		
	}
	public void rightRotate(RBNode x) {
		
		System.out.println(x.key);
		RBNode y = x.left;
		x.left = y.right;
		if ( y.right.leaf != true) y.right.p = x;
		y.p = x.p;
		if (x.p.leaf == true) this.root = y;
		else if (x == x.p.right) x.p.right = y;
		else x.p.left = y;
		y.right = x;
		x.p = y;
		
	}
	
	public RBNode search(RBNode x, int key) {
		
		
		if ((x.leaf == true) || (x.key == key)){
			x.found = true;
			return x;
		}
		if (key < x.key) return this.search(x.left, key);
		else return this.search(x.right, key);
		
	}
	
	public RBNode minimum (RBNode x) {
		if (x.left.leaf == true) return x;
		else return this.minimum(x.left);
	}
	
	public void RBTransplant (RBNode u, RBNode v) {
		
		if (u.p.leaf == true) this.root = v;
		else if (u == u.p.left) u.p.left = v;
		else u.p.right = v;
		v.p = u.p;
	}
	
	public void RBDelete (RBNode z) {
		RBNode y = z;
		RBNode x;
		y.originalColor = y.color;
		if (z.left.leaf == true) {
			x = z.right;
			this.RBTransplant(z, z.right);
		} else if ( z.right.leaf == true) {
			x = z.left;
			this.RBTransplant(z, z.left);
		} else {
			y = this.minimum(z.right);
			y.originalColor = y.color;
			x = y.right;
			if (y.p == z) x.p = y;
			else {
				this.RBTransplant(y, y.right);
				y.right = z.right;
				y.right.p = y;
			}
			this.RBTransplant(z, y);
			y.left = z.left;
			y.left.p = y;
			y.color = z.color;
		}
		if (y.originalColor == false) this.RBDeleteFixup(x);
	}
	
	public void RBDeleteFixup (RBNode x) {
		while ((x != this.root) &&  (x.color == false)) {
			if (x==x.p.left) {
				RBNode w = x.p.right;
				if (w.color == true) {
					w.color = false;
					x.p.color = true;
					this.leftRotate(x.p);
					w = x.p.right;
				}
				if ((w.left.color == false) && (w.right.color == false)) {
					w.color = true;
					x = x.p;
				} else{
					if (w.right.color == false){
						w.left.color = false;
						w.color = true;
						this.rightRotate(w);
						w = x.p.right;
					}
					w.color = x.p.color;
					x.p.color = false;
					w.right.color = false;
					this.leftRotate(x.p);
					x = this.root;
				}
			} else {
				RBNode w = x.p.left;
				if (w.color == true) {
					w.color = false;
					x.p.color = true;
					this.rightRotate(x.p);
					w = x.p.left;
				}
				if ((w.right.color == false) && (w.left.color == false)) {
					w.color = true;
					x = x.p;
				} else{
					if (w.left.color == false){
						w.right.color = false;
						w.color = true;
						this.leftRotate(w);
						w = x.p.left;
					}
					w.color = x.p.color;
					x.p.color = false;
					w.left.color = false;
					this.rightRotate(x.p);
					x = this.root;
				}
			}
		}
		x.color = false;
	}
	
	

	

}
