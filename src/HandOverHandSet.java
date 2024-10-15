public class HandOverHandSet {
	private Node head;
	private Node tail;

	public HandOverHandSet() {
		head = new Node(Integer.MIN_VALUE);
		tail = new Node(Integer.MAX_VALUE);
		head.next = tail;
	}

	public boolean addInt(int item) {
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

				if (curr.value == item)
					return false;

				Node node = new Node(item);
				node.next = curr;
				pred.next = node;

				return true;

			} finally {
				curr.unlock();
			}
		} finally {

			pred.unlock();
		}
	}

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

	public boolean containsInt(int item) {
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

				return curr.value == item;
			} finally {
				curr.unlock();
			}
		} finally {
			pred.unlock();
		}
	}

	public void clear() {
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
	}

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
