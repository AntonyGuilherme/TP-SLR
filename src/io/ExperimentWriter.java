/**
 * 
 */
package io;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 
 */
public class ExperimentWriter {
	
	private String name;
	
	public ExperimentWriter(String name) {
		this.name = name;	
	}
	
	public void add(int listSize, int updateRatio, double trhoughput) {	
		try {
			String experiment = String.format("[%d , %d, %.5f]", listSize, updateRatio, trhoughput);
			
			Path path = Paths.get(".", name);
        
			Path writtedPath = Files.write(path, 
					experiment.getBytes(StandardCharsets.UTF_8),
			        StandardOpenOption.APPEND,
			        StandardOpenOption.CREATE);
			
			System.out.println(String.format("Experiment Writted On %s", writtedPath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
