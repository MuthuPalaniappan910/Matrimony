package com.match.matrimony.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The following class has 4 fields that is required to display  in dashboard.
 * 
 * @author Bindushree
 *
 */

@Getter
@Setter
public class DashboardResponse {
	
	private Long userProfileId;
	private String userProfileName;
	private String religion;
	private String profession;

}
