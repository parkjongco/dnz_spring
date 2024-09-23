package com.kedu.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;


@Configuration
public class GCSConfig {
	
	// application 사용한 경로
	@Value("${gcp.credentials.location}")
	private Resource credentialLocation;
	
	// application 사용한 경로
	@Value("${gcp.project.id}")
	private String projectId;
	
	// resource 안에 api를 받아 올 수 있게함.
	@Bean
    public Storage storage() throws IOException {
        // GoogleCredentials 인스턴스를 설정
        GoogleCredentials cred = GoogleCredentials.fromStream(credentialLocation.getInputStream());
        
        // Storage 인스턴스를 빌드하여 반환
        return StorageOptions.newBuilder().setCredentials(cred).setProjectId(projectId).build().getService();
    }

}







