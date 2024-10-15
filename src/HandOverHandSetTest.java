import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HandOverHandSetTest {
	
	@Test
	public void shouldCreateASetAndHoldAnElement() {
		HandOverHandSet set = new HandOverHandSet();
		
		set.addInt(10);
		
		assertTrue(set.containsInt(10));
		assertEquals(set.size(), 1);
	}
	
	@Test
	public void shouldNotHoldAnElement() {
		HandOverHandSet set = new HandOverHandSet();
		
		assertFalse(set.containsInt(10));
		assertEquals(set.size(), 0);
	}
	
	@Test
	public void shouldInformTheListSize() {
		HandOverHandSet set = new HandOverHandSet();
		
		for (int i = 0; i < 10; i++)
			set.addInt(i);
		
		assertEquals(set.size(), 10);
	}
	
	@Test
	public void shouldInform() {
		HandOverHandSet set = new HandOverHandSet();
		
		for (int i = 0; i < 10; i++)
			set.addInt(i);
		
		assertEquals(set.size(), 10);
	}
	
	class SetInserter implements Runnable {

		private HandOverHandSet set;
		private int sleep;
		public int correctOperations = 0;
		
		public SetInserter(HandOverHandSet set, int sleep) {
			this.sleep = sleep;
			this.set = set;
		}
		
		@Override
		public void run() {
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int i = 0; i < 150; i++) {
				if(set.addInt(i))
					this.correctOperations++;
			}
		}	
	}
	
	
}
