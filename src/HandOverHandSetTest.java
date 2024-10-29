import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

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
	
	@Test
	public void shouldNotContains() {
		HandOverHandSet set = new HandOverHandSet();
		set.addInt(10);
		CyclicBarrier berrier = new CyclicBarrier(2);
		
		Runnable run = new SetInserter(set, berrier);
		Runnable remover = new SetRemover(set, berrier);
		
		new Thread(remover).start();
		new Thread(run).start();
	}
	
	class SetInserter implements Runnable {

		private HandOverHandSet set;
		public int correctOperations = 0;
		private CyclicBarrier barrier;
		
		public SetInserter(HandOverHandSet set, CyclicBarrier barrier) {
			this.set = set;
			this.barrier = barrier;
		}
		
		@Override
		public void run() {
			try {
				System.out.println("Contains ready");
				this.barrier.await();
				//System.out.println("Contains");
				boolean val = this.set.containsInt(10);
				System.out.println("Contains ends");
				assertTrue(val);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
	
	class SetRemover implements Runnable {

		private HandOverHandSet set;
		public int correctOperations = 0;
		private CyclicBarrier barrier;
		
		public SetRemover(HandOverHandSet set, CyclicBarrier barrier) {
			this.set = set;
			this.barrier = barrier;
		}
		
		@Override
		public void run() {
			try {
				System.out.println("Remover ready");
				this.barrier.await();
				//System.out.println("Remove");
				this.set.removeInt(10);
				System.out.println("Remove ends");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
	
}
