/**
 * 
 */
package io.coding.excersise.score.service.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.coding.excercise.ApplicationConfiguration;
import io.coding.excercise.score.constants.ScoreConstants;
import io.coding.excercise.score.reader.InMemoryFileReader;

/**
 * @author subha
 *
 */
public class InMemoryFileReaderTest {
	private InMemoryFileReader inMemoryFileReader;
	private String filepath = "testdata";
	
	private static ApplicationContext context;
	
	@BeforeAll
	public static void beforeAll() {
		context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
	}

	@BeforeEach
	public void setup() {
		// Create file service and create temo directory to store intermediate results
		inMemoryFileReader = context.getBean(InMemoryFileReader.class);
	}

	@Test
	@DisplayName("Verify in-memory file reader")
	public void testInMemoryFileReader() {
		String filePath = filepath + File.separator + "SmallDataSetFile.txt";
		String expected = "MARY,PATRICIA,LINDA,BARBARA,VINCENZO,SHON,LYNWOOD,JERE,HAI";
		Stream<String> dataStream = inMemoryFileReader.read(filePath, ScoreConstants.SCORE_BY_FIRST_NAME);
		String actual=dataStream.collect(Collectors.joining(","));
		
		assertEquals(expected, actual);
	}
}
