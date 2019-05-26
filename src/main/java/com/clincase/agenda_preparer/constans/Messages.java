package com.clincase.agenda_preparer.constans;

public class Messages {

	// Info Messages
	public static final String INFO_MESSAGE = "####Welcome to Agenda Calculator Software####\n"
			+ "The author: Onur Kececi - onurkececi@gmail.com\n";

	// Warning Messages
	public static final String UNRECOGNIZED_DURATION = "ERROR: Unrecognized duration input: ";
	public static final String EMPTY_LINE = "Unrecognized line. Skipping the line!";
	public static final String TITLE_CONTAIN_NUMBERS = "Given conference title contains number. It is not allowed. The title is skipped. Title: ";
	public static final String CONF_SIZE_AND_CONF_VALUES_NOT_MATCH = "WARNING: Given # conference and actual conferences does not match. The process continues";

	// Error Messages
	public static final String ERROR_GENERAL = "An Error occured while readling lines";
	public static final String ERROR_FILE_INPUT = "Please provide input file with path ex: input.txt";
	public static final String FILE_WRITE_ERROR = "While generating putput file an error occured. Detailed error: ";
	public static final String UNRECOGNIZED_INPUT_FILE = "ERROR: Unrecognized input file. Exiting!";

}
