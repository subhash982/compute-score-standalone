/**
 * 
 */
package io.coding.excercise;

import java.nio.file.Paths;
import java.util.Optional;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.coding.excercise.score.constants.ScoreConstants;
import io.coding.excercise.score.service.ComputeScoreService;

/**
 * @author subha
 *
 */
public class CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(CommandLineRunner.class);

	/**
	 * Start the command line runner and initial the score computation based on the
	 * user input.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		logger.debug("Score computation started");

		try {
			/*
			 * I am using SOP to make the text clearly visible to command line screen
			 * instead of using the logger. This utility is using spring core module for
			 * dependency injection
			 */
			ApplicationContext context = loadSpringScontext();
			long computedScore = 0;

			// Set the initial command line value or default
			String fileName = args.length > 0 ? args[0] : null;
			// Set the default first time if not provided through command line
			String scoreAlgo = args.length > 1 ? args[1] : null;

			Scanner scanner = new Scanner(System.in);
			while (true) {
				// Input a file path from user
				if (fileName == null) {
					System.out.print("\nEnter a valid txt/csv file path or type exit to stop : ");
					fileName = scanner.nextLine();
					if (fileName.equalsIgnoreCase("exit")) {
						break;
					}
				}

				// Input a score algorithm from user
				if (scoreAlgo == null) {
					System.out.print(
							"\nEnter score algorithm(Optional - Default [scoreByFirstName]).You can skip this by pressing enter or type exit to stop : ");
					scoreAlgo = scanner.nextLine();
					if (scoreAlgo.equalsIgnoreCase("exit")) {
						break;
					}
					scoreAlgo = scoreAlgo.trim().length() == 0 ? ScoreConstants.SCORE_BY_FIRST_NAME : scoreAlgo;
				}

				// Validate and compute the score
				if (Paths.get(fileName).toFile().exists()) {
					if (isValidFile(fileName)) {
						try {
							// Compute the score
							ComputeScoreService scoreService = context.getBean(ComputeScoreService.class);
							computedScore = scoreService.compute(fileName, scoreAlgo);
							System.out.printf("Computed score :  [%s]  \n\n", computedScore);
						} catch (Exception e) {
							System.out.println("Error in computing score : " + e.getMessage());
						}

					} else {
						System.out.println("Invalide File, Please select valid txt / csv file.\n\n");
					}

				} else {
					System.out.println("Input file doesn't exist,Please select valid txt / csv file.\n\n");
				}

				// Rest value for next input
				fileName = null;
				scoreAlgo = null;

				// Check with user to continue
				System.out.print("Do you want to calculate score for additional files. (Y/ N) ...");
				if (!scanner.nextLine().equalsIgnoreCase("Y")) {
					break;
				}
			}
			// Close the scanner
			scanner.close();
			logger.debug("Score computation completed");
		} catch (Exception e) {
			// If there is an error in loading the spring application context
			System.out.println("Some unkown error, Please check with utility owner [" + e.getMessage() + "]");
		}

	}

	/**
	 * Only allow the csv and text file.
	 * 
	 * @param filename Input File name
	 * @return true if valid file name
	 */
	public static boolean isValidFile(String filename) {
		String extension = Optional.ofNullable(filename).filter(f -> f.contains("."))
				.map(f -> f.substring(filename.lastIndexOf(".") + 1)).orElse("");

		return ScoreConstants.CSV_EXTN.equalsIgnoreCase(extension)
				|| ScoreConstants.TXT_EXTN.equalsIgnoreCase(extension);
	}

	/**
	 * Load the spring application context.
	 * 
	 * @return Spring context
	 */
	private static ApplicationContext loadSpringScontext() {
		ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
		return context;
	}

}
