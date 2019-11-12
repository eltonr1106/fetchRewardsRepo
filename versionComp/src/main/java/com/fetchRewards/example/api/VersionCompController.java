package com.fetchRewards.example.api;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fetchRewards.example.dao.VersionComp;
import com.fetchRewards.example.model.VersionHolder;

@RestController
@RequestMapping("/fetchRewards")
public class VersionCompController {
	
	private final VersionComp versionComp;
	public VersionCompController(@Qualifier("defaultImpl") VersionComp p_versionsComp) {
		versionComp = p_versionsComp;
	}
	
	@PostMapping
	String compareStrings(@RequestBody VersionHolder p_versionHolder) {
		String resultStr = this.versionComp.compareStrings(p_versionHolder.getVersion1(),
				p_versionHolder.getVersion2());
				
		return resultStr;
	}
	
	
}
