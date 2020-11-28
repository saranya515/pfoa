package com.pfoa.expenditure;

import java.io.FileNotFoundException;

import com.pfoa.expenditure.dao.OverviewDao;

public class Test {

	public static void main(String args[]) throws FileNotFoundException {
		OverviewDao csv = new OverviewDao();
		System.out.println(csv.readFromCsv().toString());
	}
}
