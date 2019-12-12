package com.match.matrimony.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The following class fetches data from DashboardResponse and returns list along with message and status code.
 * 
 * @author Bindushree
 *
 */

@Getter
@Setter
public class DashboardResponseDto {
	
	List<DashboardResponse> dashboardResponses;
	private Integer statusCode;
	private String message;
}
