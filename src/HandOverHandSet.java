public class HandOverHandSet extends AbstractCompositionalIntSet {
	
	private Node head;
	private Node tail;

	public HandOverHandSet() {
		head = new Node(Integer.MIN_VALUE);
		tail = new Node(Integer.MAX_VALUE);
		head.next = tail;
	}
	
	@Override
	public boolean addInt(int item) {
		head.lock();
		
		Node pred = head;
		Node curr = pred.next;

		try {
			
			curr.lock();
			
			try {
				// searching until the current element is the successor of 
				// the new element
				// locking a node at time
				while (curr.value < item) {
					pred.unlock();
					pred = curr;
					curr = pred.next;
					curr.lock();
				}
				
				// if the elements was just insert
				// it should not be inserted again
				if (curr.value == item)
					return false;
				
				// setting the node between the predecessor and current
				// [predecessor] -> [new element] -> [current]
				Node node = new Node(item);
				node.next = curr;
				pred.next = node;

				return true;
				
			// making sure that all elements will be unlock
			// even if an error occurs
			} finally {

				curr.unlock();
			}
		} finally {

			pred.unlock();
		}
	}
	
	@Override
	public boolean removeInt(int item) {
		head.lock();
		Node pred = head;
		Node curr = pred.next;

		try {
			curr.lock();
			try {
				while (curr.value < item) {
					pred.unlock();
					pred = curr;
					curr = pred.next;
					
					curr.lock();
				}

				if (curr.value == item) {
					pred.next = curr.next;
					return true;
				}

				return false;
			} finally {
				curr.unlock();
			}
		} finally {
			pred.unlock();
		}
	}
	
	@Override
	public boolean containsInt(int item) {
		//head.lock();
		Node pred = head;
		Node curr = pred.next;

		try {
			curr.lock();
			try {
				while (curr.value < item) {
					//pred.unlock();
					pred = curr;
					curr = pred.next;
					//curr.lock();
				}

				return curr.value == item;
			} finally {
				//curr.unlock();
			}
		} finally {
			//pred.unlock();
		}
	}
	
	@Override
	public void clear() {
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
	}
	
	@Override
	public int size() {
		int count = 0;

		Node curr = head.next;
		while (curr.value != Integer.MAX_VALUE) {
			curr = curr.next;
			count++;
		}
		return count;
	}
}
