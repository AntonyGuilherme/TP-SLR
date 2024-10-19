package io;
import org.junit.Test;

public class ExperimentWriterTest {
	
	@Test
	public void shouldCreateAJsonFileForAnExperiment() {
		
		String name = "HandOverHand_FixedList_UpdateRatio.txt";
		double throughtput = 1000.95;
		int listSize = 100;
		int updateRatio = 10;
		
		ExperimentWriter writer = new ExperimentWriter(name);
		
		
		writer.add(listSize, updateRatio, throughtput);
	}

}
