package censusanalyser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CensusAnalyser {

	List<IndiaCensusCSV> censusCSVList = null;
	List<IndiaStateCodeCSV> stateCodeCSVList = null;

	public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			censusCSVList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
			return censusCSVList.size();
		} catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		} catch (CSVBuilderException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
		} catch (RuntimeException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.FILE_ERROR);
		}
	}

	public int loadIndianStateCode(String csvFilePath) throws CensusAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			Iterator<IndiaStateCodeCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader,
					IndiaStateCodeCSV.class);
			return this.getCount(censusCSVIterator);
		} catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		} catch (CSVBuilderException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
		} catch (RuntimeException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.FILE_ERROR_IN_STATE_CODE);
		}
	}

	private <E> int getCount(Iterator<E> iterator) {
		Iterable<E> csvIterable = () -> iterator;
		int numOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
		return numOfEnteries;
	}

	public String getStateWiseSortedCensusData() throws CensusAnalyserException {
		if (censusCSVList == null || censusCSVList.size() == 0) {
			throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_DATA);
		}
		Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.state);
		this.smallestFirstSort(censusComparator);
		String sortedStateCensusJson = new Gson().toJson(censusCSVList);
		return sortedStateCensusJson;
	}

	public String getPopulationWiseSortedCensusData() throws CensusAnalyserException {
		try (Writer writer = new FileWriter("./src/test/resources/IndiaStatePopulationDataJson.json")) {
			if (censusCSVList == null || censusCSVList.size() == 0) {
				throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_DATA);
			}
			Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.population);
			this.largesetFirstSort(censusComparator);
			String sortedStateCensusJson = new Gson().toJson(censusCSVList);
			Gson gson = new GsonBuilder().create();
			gson.toJson(censusCSVList, writer);
			return sortedStateCensusJson;
		} catch (RuntimeException | IOException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.FILE_ERROR);
		}
	}
	
	public String getDensityPerSqKmWiseSortedCensusData() throws CensusAnalyserException {
		try (Writer writer = new FileWriter("./src/test/resources/IndiaCensusDensityPerSqKmDataJson.json")) {
			if (censusCSVList == null || censusCSVList.size() == 0) {
				throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_DATA);
			}
			Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
			this.largesetFirstSort(censusComparator);
			String sortedStateCensusJson = new Gson().toJson(censusCSVList);
			Gson gson = new GsonBuilder().create();
			gson.toJson(censusCSVList, writer);
			return sortedStateCensusJson;
		} catch (RuntimeException | IOException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.FILE_ERROR);
		}
	}
	
	public String getAreaInSqKmWiseSortedCensusData() throws CensusAnalyserException {
		try (Writer writer = new FileWriter("./src/test/resources/IndiaCensusAreaInSqKmDataJson.json")) {
			if (censusCSVList == null || censusCSVList.size() == 0) {
				throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_DATA);
			}
			Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.areaInSqKm);
			this.largesetFirstSort(censusComparator);
			String sortedStateCensusJson = new Gson().toJson(censusCSVList);
			Gson gson = new GsonBuilder().create();
			gson.toJson(censusCSVList, writer);
			return sortedStateCensusJson;
		} catch (RuntimeException | IOException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.FILE_ERROR);
		}
	}

	public void smallestFirstSort(Comparator<IndiaCensusCSV> censusComparator) {
		for (int i = 0; i < censusCSVList.size() - 1; i++) {
			for (int j = 0; j < censusCSVList.size() - 1 - i; j++) {
				IndiaCensusCSV census1 = censusCSVList.get(j);
				IndiaCensusCSV census2 = censusCSVList.get(j + 1);
				if (censusComparator.compare(census1, census2) > 0) {
					censusCSVList.set(j, census2);
					censusCSVList.set(j + 1, census1);
				}
			}
		}
	}
	
	public void largesetFirstSort(Comparator<IndiaCensusCSV> censusComparator) {
		for (int i = 0; i < censusCSVList.size() - 1; i++) {
			for (int j = 0; j < censusCSVList.size() - 1 - i; j++) {
				IndiaCensusCSV census1 = censusCSVList.get(j);
				IndiaCensusCSV census2 = censusCSVList.get(j + 1);
				if (censusComparator.compare(census1, census2) < 0) {
					censusCSVList.set(j, census2);
					censusCSVList.set(j + 1, census1);
				}
			}
		}
	}

	public String getStateCodeWiseSortedData() throws CensusAnalyserException {
		if (stateCodeCSVList == null || stateCodeCSVList.size() == 0) {
			throw new CensusAnalyserException("No Data", CensusAnalyserException.ExceptionType.NO_DATA);
		}
		Comparator<IndiaStateCodeCSV> censusComparator = Comparator.comparing(census -> census.stateCode);
		this.sortStateCode(censusComparator);
		String sortedStateCensusJson = new Gson().toJson(stateCodeCSVList);
		return sortedStateCensusJson;
	}

	public void sortStateCode(Comparator<IndiaStateCodeCSV> censusComparator) {
		for (int i = 0; i < stateCodeCSVList.size() - 1; i++) {
			for (int j = 0; j < stateCodeCSVList.size() - 1 - i; j++) {
				IndiaStateCodeCSV census1 = stateCodeCSVList.get(j);
				IndiaStateCodeCSV census2 = stateCodeCSVList.get(j + 1);
				if (censusComparator.compare(census1, census2) > 0) {
					stateCodeCSVList.set(j, census2);
					stateCodeCSVList.set(j + 1, census1);
				}
			}
		}
	}
}
