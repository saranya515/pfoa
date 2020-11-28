package com.pfoa.expenditure.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import com.pfoa.expenditure.dto.OverviewDto;
import com.pfoa.expenditure.util.CsvUtil;

public class OverviewDao {
	
	public List<OverviewDto> readFromCsv() throws FileNotFoundException {
		String fileName = "statement.csv";
		FileReader fileReader = new FileReader(fileName);
		return CsvUtil.readLineByLine(fileReader);
	}	
}
