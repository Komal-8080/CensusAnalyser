package censusanalyser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.gson.Gson;

//import JarFileCensusAnalyser.CSVBuilderException;

public class CensusAnalyserTest {

	private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
	private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
	private static final String INDIA_STATE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";

	@Test
	public void givenIndianCensusCSVFileReturnsCorrectRecords() throws CSVBuilderException {
		try {
			CensusAnalyser censusAnalyser = new CensusAnalyser();
			int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
			Assert.assertEquals(29, numOfRecords);
		} catch (CensusAnalyserException e) {
		}
	}

	@Test
	public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() throws CSVBuilderException {
		try {
			CensusAnalyser censusAnalyser = new CensusAnalyser();
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(CensusAnalyserException.class);
			censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
		}
	}

	@Test
	public void givenIndiaCensusData_WithWrongFileType_ShouldThrowException() throws CSVBuilderException {
		try {
			CensusAnalyser censusAnalyser = new CensusAnalyser();
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(CensusAnalyserException.class);
			censusAnalyser.loadIndiaCensusData(INDIA_STATE_CSV_FILE_PATH);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.FILE_ERROR, e.type);
		}
	}

	@Test
	public void givenIndiaCensusData_WithIncorrectDelimiter_ShouldThrowException() throws CSVBuilderException {
		try {
			CensusAnalyser censusAnalyser = new CensusAnalyser();
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(CensusAnalyserException.class);
			censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.FILE_ERROR, e.type);
		}
	}

	@Test
	public void givenIndiaCensusData_WithIncorrectHeader_ShouldThrowException() throws CSVBuilderException {
		try {
			CensusAnalyser censusAnalyser = new CensusAnalyser();
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(CensusAnalyserException.class);
			censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.FILE_ERROR, e.type);
		}
	}

	@Test
	public void givenIndianStateCSV_ShouldReturnExactCount() throws CensusAnalyserException, CSVBuilderException {
		try {
			CensusAnalyser censusAnalyser = new CensusAnalyser();
			int stateCode = censusAnalyser.loadIndianStateCode(INDIA_STATE_CSV_FILE_PATH);
			Assert.assertEquals(37, stateCode);
		} catch (CensusAnalyserException e) {
		}
	}

	@Test
	public void givenIndianStateCSV_WithWrongFile_ShouldThrowException() throws CSVBuilderException {
		try {
			CensusAnalyser censusAnalyser = new CensusAnalyser();
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(CensusAnalyserException.class);
			censusAnalyser.loadIndianStateCode(WRONG_CSV_FILE_PATH);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
		}
	}

	@Test
	public void givenIndianStateCSV_WithWrongFileType_ShouldThrowException() throws CSVBuilderException {
		try {
			CensusAnalyser censusAnalyser = new CensusAnalyser();
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(CensusAnalyserException.class);
			censusAnalyser.loadIndianStateCode(INDIA_CENSUS_CSV_FILE_PATH);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.FILE_ERROR_IN_STATE_CODE, e.type);
		}
	}

	@Test
	public void givenIndianStateCSV_WithIncorrectDelimiter_ShouldThrowException() throws CSVBuilderException {
		try {
			CensusAnalyser censusAnalyser = new CensusAnalyser();
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(CensusAnalyserException.class);
			censusAnalyser.loadIndianStateCode(INDIA_STATE_CSV_FILE_PATH);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.FILE_ERROR_IN_STATE_CODE, e.type);
		}
	}

	@Test
	public void givenIndianStateCSV_WithIncorrectHeader_ShouldThrowException() throws CSVBuilderException {
		try {
			CensusAnalyser censusAnalyser = new CensusAnalyser();
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(CensusAnalyserException.class);
			censusAnalyser.loadIndianStateCode(INDIA_STATE_CSV_FILE_PATH);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.FILE_ERROR_IN_STATE_CODE, e.type);
		}
	}

	@Test
	public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
		try {
			CensusAnalyser censusAnalyser = new CensusAnalyser();
			censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
			String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
			IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
			Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
		} catch (CensusAnalyserException e) {
		}
	}

	@Test
	public void giveIndianCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() {
		try {
			CensusAnalyser censusAnalyser = new CensusAnalyser();
			censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
			String sortedCensusData = censusAnalyser.getPopulationWiseSortedCensusData();
			IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
			Assert.assertEquals(199812341, censusCSV[0].population);
		} catch (CensusAnalyserException e) {
		}
	}

	@Test
	public void giveIndianCensusData_WhenSortedOnDensityPerSqKm_ShouldReturnSortedResult() {
		try {
			CensusAnalyser censusAnalyser = new CensusAnalyser();
			censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
			String sortedCensusData = censusAnalyser.getDensityPerSqKmWiseSortedCensusData();
			IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
			Assert.assertEquals(1102, censusCSV[0].densityPerSqKm);
		} catch (CensusAnalyserException e) {
		}
	}

	@Test
	public void giveIndianCensusData_WhenSortedOnAreaInSqKm_ShouldReturnSortedResult() {
		try {
			CensusAnalyser censusAnalyser = new CensusAnalyser();
			censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
			String sortedCensusData = censusAnalyser.getAreaInSqKmWiseSortedCensusData();
			IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
			Assert.assertEquals(342239, censusCSV[0].areaInSqKm);
		} catch (CensusAnalyserException e) {
		}
	}

	@Test
	public void givenIndianStateCodeData_WhenSortedOnStateCode_ShouldReturnSortedResult() {
		try {
			CensusAnalyser censusAnalyser = new CensusAnalyser();
			censusAnalyser.loadIndianStateCode(INDIA_STATE_CSV_FILE_PATH);
			String sortedCensusData = censusAnalyser.getStateCodeWiseSortedData();
			IndiaStateCodeCSV[] stateCodeCSV = new Gson().fromJson(sortedCensusData, IndiaStateCodeCSV[].class);
			Assert.assertEquals("AD", stateCodeCSV[0].stateCode);
		} catch (CensusAnalyserException e) {
		}
	}

	@Test
	public void givenIndianCensusCommonCSVFileReturnsCorrectRecords() throws CSVBuilderException {
		try {
			CensusAnalyser censusAnalyser = new CensusAnalyser();
			int numOfRecords = censusAnalyser.loadIndiaCensusAndStateCodeInCommonsCSV(INDIA_CENSUS_CSV_FILE_PATH);
			Assert.assertEquals(29, numOfRecords);
		} catch (CensusAnalyserException e) {
		}
	}

	@Test
	public void givenIndiaCensusData_WithWrongFile_ShouldThrowException_WhenCommonCSVUsed() throws CSVBuilderException {
		try {
			CensusAnalyser censusAnalyser = new CensusAnalyser();
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(CensusAnalyserException.class);
			censusAnalyser.loadIndiaCensusAndStateCodeInCommonsCSV(WRONG_CSV_FILE_PATH);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
		}
	}
	
	@Test
	public void givenIndianStateCommonCSV_ShouldReturnExactCount() throws CensusAnalyserException, CSVBuilderException {
		try {
			CensusAnalyser censusAnalyser = new CensusAnalyser();
			int stateCode = censusAnalyser.loadIndiaCensusAndStateCodeInCommonsCSV(INDIA_STATE_CSV_FILE_PATH);
			Assert.assertEquals(37, stateCode);
		} catch (CensusAnalyserException e) {
		}
	}

	@Test
	public void givenIndianStateCSV_WithWrongFile_ShouldThrowException_WhenCommonCSVUsed() throws CSVBuilderException {
		try {
			CensusAnalyser censusAnalyser = new CensusAnalyser();
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(CensusAnalyserException.class);
			censusAnalyser.loadIndiaCensusAndStateCodeInCommonsCSV(WRONG_CSV_FILE_PATH);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
		}
	}

}
