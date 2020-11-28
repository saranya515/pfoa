package com.pfoa.expenditure;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfoa.expenditure.dao.OverviewDao;
import com.pfoa.expenditure.dto.OverviewDto;

@RestController
public class OverviewController {

	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@GetMapping(path = "/overview")
	public List<OverviewDto> expenditureOverview() throws FileNotFoundException {
		OverviewDao csv = new OverviewDao();
		return csv.readFromCsv();
	}
}
