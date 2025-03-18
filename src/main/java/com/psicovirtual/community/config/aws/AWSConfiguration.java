package com.psicovirtual.community.config.aws;

import com.psicovirtual.community.component.S3Properties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@AllArgsConstructor
@Profile("!local")
public class AWSConfiguration {

    @Bean
    public S3Client s3Client(final S3Properties s3Properties) {
        var region = Region.of(s3Properties.getRegion());
        return S3Client.builder().region(region).credentialsProvider(DefaultCredentialsProvider.create()).build();
    }

}
