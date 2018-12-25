package com.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("config")
public class ConfigProperties {
    
	private final UploadProperties uploadProperties = new UploadProperties();
	
	public static class UploadProperties {

		private String filePath;
		private String coordinatorFilePath;

		public String getFilePath() {
			return filePath;
		}

		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}

		public String getCoordinatorFilePath() {
			return coordinatorFilePath;
		}

		public void setCoordinatorFilePath(String coordinatorFilePath) {
			this.coordinatorFilePath = coordinatorFilePath;
		}
		
	}

	public UploadProperties getUploadProperties() {
		return uploadProperties;
	}
	
	
}
