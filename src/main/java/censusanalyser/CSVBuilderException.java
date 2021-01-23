package censusanalyser;

public class CSVBuilderException extends Exception {

	enum ExceptionType {
        CENSUS_FILE_PROBLEM, UNABLE_TO_PARSE, FILE_ERROR, FILE_ERROR_IN_STATE_CODE
    }

    ExceptionType type;

	public CSVBuilderException(String message, CSVBuilderException.ExceptionType type) {
		super(message);
		this.type = type;
	}  
}
