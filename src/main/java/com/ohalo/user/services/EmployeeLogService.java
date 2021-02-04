package com.ohalo.user.services;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohalo.user.dto.EmployeeDto;
import com.ohalo.user.models.EmployeeLog;
import com.ohalo.user.repositories.EmployeeLogRepo;
import com.ohalo.user.utils.SecurityUtils;

@Service
public class EmployeeLogService {

	@Autowired
	EmployeeLogRepo employeeLogRepo;

	@Autowired
	ObjectMapper objectMapper;

	@Async
	public void saveEmployeeLog(EmployeeDto employeeDto) throws JsonProcessingException, NoSuchAlgorithmException {
		EmployeeLog employeeLog = new EmployeeLog();
		employeeDto.setSocSecNumber(SecurityUtils.hash(employeeDto.getSocSecNumber()));
		employeeLog.setData(objectMapper.writeValueAsString(employeeDto));

		employeeLogRepo.save(employeeLog);
	}

}
