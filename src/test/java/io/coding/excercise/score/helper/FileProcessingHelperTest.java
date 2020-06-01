package io.coding.excercise.score.helper;

import static io.coding.excercise.score.calculator.FirstNameScoreCalculator.withSorting;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.coding.excercise.ApplicationConfiguration;
import io.coding.excercise.score.constants.ScoreConstants;
import io.coding.excercise.score.exception.ComputeScoreException;

public class FileProcessingHelperTest {
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
		fileProcessingHelper = context.getBean(FileProcessingHelper.class);
		fileProcessingHelper.createDirectoryPath(ScoreConstants.TEMP_PATH);
	}

	@Test
	@DisplayName("Verify the number of chunkc created from large file(> 50KB)")
	public void testSplitAndSortFileIntoChunksSuccess() {
		String splitedFilePrefix = ScoreConstants.TEMP_PATH + File.separator
				+ String.valueOf(System.currentTimeMillis());
		String filePath = filepath + File.separator + "LargeDataSetFile.txt";
		int count = fileProcessingHelper.splitAndSortFileIntoChunks(filePath, splitedFilePrefix, withSorting());
		// Clean all the chunk file
		fileProcessingHelper.deleteAllMatchingFiles(splitedFilePrefix);
		// Each chunk size is defined to 20KB so from 50KB file chunk count will be 3
		assertEquals(count, 3);
	}

	@Test
	@DisplayName("Verify error creating chunks with null input")
	public void testSplitAndSortFileIntoChunksFailure() {
		String splitedFilePrefix = ScoreConstants.TEMP_PATH + File.separator
				+ String.valueOf(System.currentTimeMillis());
		assertThrows(ComputeScoreException.class, () -> {
			fileProcessingHelper.splitAndSortFileIntoChunks(null, splitedFilePrefix, withSorting());
		});
	}

	@Test
	@DisplayName("Verify chunk sorting and storing into the temp file fetatue")
	public void testSortAndWriteChunkContentToFileSuccess() {
		String splitedFilePrefix = ScoreConstants.TEMP_PATH + File.separator
				+ String.valueOf(System.currentTimeMillis());
		String sortedContent = "BARBARAHAIJERELINDALYNWOODMARYPATRICIASHONVINCENZO";
		String unsortedContent = "MARY,PATRICIA,LINDA,BARBARA,VINCENZO,SHON,LYNWOOD,JERE,HAI";

		fileProcessingHelper.sortAndWriteChunkContentToFile(unsortedContent, splitedFilePrefix, 0, withSorting());
		// Read the entire file content
		String actualSortedContent = fileProcessingHelper.readFileContent(splitedFilePrefix + "_" + 0 + ".txt");

		// Clean all the chunk file
		fileProcessingHelper.deleteAllMatchingFiles(splitedFilePrefix);

		assertEquals(sortedContent, actualSortedContent);
	}

	@Test
	@DisplayName("Verify merging chunks into a single file")
	public void testMergeAllTheFileChunksSuccess() {
		String splitedFilePrefix = filepath + File.separator + "SplitFileChunk";
		String targetFile = ScoreConstants.TEMP_PATH + File.separator + ScoreConstants.MERGED_FILE_NAME;
		String expectedContent = "AARONABBEYABBIEABBYABIGAILABRAHAMADAADAHADALINEADDIEKANDICEKANDYLASHAUNLASHAUNDAPIAPILARPINKIEZOEZOILAZOLA";
		fileProcessingHelper.mergeAllTheFileChunks(targetFile, splitedFilePrefix, 2, withSorting());

		// Read the entire file content
		String actualSortedContent = fileProcessingHelper.readFileContent(targetFile);

		// Clean all the chunk file
		fileProcessingHelper.deleteAllMatchingFiles(ScoreConstants.MERGED_FILE_NAME);

		assertEquals(actualSortedContent, expectedContent);
	}

}
