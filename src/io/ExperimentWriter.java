/**
 * 
 */
package io;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ExperimentWriter {
	
	// file name to write on
	private String name;
	
	public ExperimentWriter(String name) {
		this.name = name;	
	}
	
	public void add(int numberOfThreads, int listSize, int updateRatio, double throughput ) {	
		try {
			
			// format the experiment result
			// result format : [number of threads, list size, update ratio, throughput]
			String experiment = String.format("[%d, %d , %d, %.5f],", numberOfThreads, listSize, updateRatio, throughput);
			
			// path to the experiment file
			Path path = Paths.get(".", name);
			
			// adding the experiment result
			// this do not overwrite the file content
			// only add another line of content
			Path writtenPath = Files.write(path, 
					experiment.getBytes(StandardCharsets.UTF_8),
			        StandardOpenOption.APPEND,
			        StandardOpenOption.CREATE);
			
			// informing that the experiment was written in the right file 
			System.out.println(String.format("Experiment Writted On %s", writtenPath));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
