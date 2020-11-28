package com.pfoa.expenditure.util;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.pfoa.expenditure.dto.OverviewDto;

public class CsvUtil {

	public static List<OverviewDto> readLineByLine(Reader reader) {
        List<OverviewDto> dtos = new ArrayList<>();
        Map<String, Float> categoryMap = new HashMap<>();
        try {
            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(';')
                    .withIgnoreQuotations(true)
                    .build();

            CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withSkipLines(0)
                    .withCSVParser(parser)
                    .build();

            String[] line;
            int lineCount = 1;
            while ((line = csvReader.readNext()) != null) {
            	if(lineCount == 1) {
            		lineCount++;
            		continue;
            	}
            	categorizeTransaction(line, categoryMap);
            }
            reader.close();
            csvReader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        createCategoryList(categoryMap, dtos);
        return dtos;
    }
	
	private static void createCategoryList(Map<String, Float> categoryMap, List<OverviewDto> dtos) {
		for(String key : categoryMap.keySet()) {
			OverviewDto dto = new OverviewDto();
			dto.setCategory(key);
			dto.setExpenditure(categoryMap.get(key));
	    	dtos.add(dto);
		}
	}
	
	private static void categorizeTransaction(String[] line, Map<String, Float> categoryMap) {
		Map<String, String> dictionary = getCategoryDictionary();
		String item = line[11].toLowerCase();
		String amount = line[14];
		amount = amount.replace(",", ".");
		Float transactionAmount = new Float(amount);
		if(amount.contains("-")) {
			for(String key : dictionary.keySet()) {
				if(item.contains(key)) {
	            	String value = dictionary.get(key);
	            	updateCategoryMap(value, categoryMap, transactionAmount);
				}
			}
			updateCategoryMap("TotalDebit", categoryMap, transactionAmount);
		} else {
			updateCategoryMap("TotalCredit", categoryMap, transactionAmount);
		}
	}
	
	private static void updateCategoryMap(String key, Map<String, Float> categoryMap, Float transactionAmount) {
		Float sum = transactionAmount;
		if(categoryMap.containsKey(key)) {
    		sum = categoryMap.get(key);
    		sum += transactionAmount;
    	}
    	categoryMap.put(key, sum);
	}
	
	private static Map<String, String> getCategoryDictionary() {
		Map<String, String> dictionary = new HashMap<>();
		dictionary.put("Kaufland".toLowerCase(), "groceries");
		dictionary.put("MARKTKAUF".toLowerCase(), "groceries");
		dictionary.put("NORMA".toLowerCase(), "groceries");
		dictionary.put("stadtkasse hof".toLowerCase(), "fee");
		dictionary.put("hochschule".toLowerCase(), "fee");
		dictionary.put("Amazon".toLowerCase(), "online Shopping");
		return dictionary;
	}
}
