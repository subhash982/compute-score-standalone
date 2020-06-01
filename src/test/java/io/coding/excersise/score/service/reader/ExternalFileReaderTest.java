/**
 * 
 */
package io.coding.excersise.score.service.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.time.Instant;
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
import io.coding.excercise.score.helper.FileProcessingHelper;
import io.coding.excercise.score.reader.ExternalFileReader;

/**
 * @author subha
 *
 */
public class ExternalFileReaderTest {

	private ExternalFileReader externalFileReader;
	private FileProcessingHelper fileProcessingHelper;

	private static ApplicationContext context;
	private String filepath = "testdata";

	@BeforeAll
	public static void beforeAll() {
		context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
	}

	@BeforeEach
	public void setup() {
		// Create file service and create temo directory to store intermediate results
		externalFileReader = context.getBean(ExternalFileReader.class);
		fileProcessingHelper = context.getBean(FileProcessingHelper.class);
		fileProcessingHelper.createDirectoryPath(ScoreConstants.TEMP_PATH);
	}

	@Test
	@DisplayName("Verify file reader using external memory")
	public void testExternalFileReader() {
		String targetPath = ScoreConstants.TEMP_PATH + File.separator + String.valueOf(Instant.now().getEpochSecond());
		String filePath = filepath + File.separator + "SmallDataSetFile.txt";
		String expected = "BARBARA,HAI,JERE,LINDA,LYNWOOD,MARY,PATRICIA,SHON,VINCENZO";
		Stream<String> dataStream = externalFileReader.read(filePath, targetPath,ScoreConstants.SCORE_BY_FIRST_NAME);
		String actual = dataStream.collect(Collectors.joining(","));
		assertEquals(expected, actual);
	}

}
