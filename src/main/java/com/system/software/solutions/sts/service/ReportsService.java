package com.system.software.solutions.sts.service;

import com.system.software.solutions.sts.formbean.DailyAuditedFB;
import com.system.software.solutions.sts.model.CustomEntity;
 
public interface ReportsService {
 
	DailyAuditedFB fetchDailyAudited();
	
}
