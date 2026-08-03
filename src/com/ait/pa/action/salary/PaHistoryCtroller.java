package com.ait.pa.action.salary;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/pa/history")
public class PaHistoryCtroller {
	Logger logger = Logger.getLogger(PaHistoryCtroller.class);
}
