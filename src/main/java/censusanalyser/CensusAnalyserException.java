package censusanalyser;

public class CensusAnalyserException extends Exception {

    enum ExceptionType {
        CENSUS_FILE_PROBLEM, UNABLE_TO_PARSE, FILE_ERROR, FILE_ERROR_IN_STATE_CODE, NO_CENSUS_DATA
    }

    ExceptionType type;
   
    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
