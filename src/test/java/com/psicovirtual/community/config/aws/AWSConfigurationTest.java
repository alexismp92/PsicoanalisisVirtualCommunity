package com.psicovirtual.community.config.aws;

import com.psicovirtual.community.component.S3Properties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.core.exception.SdkException;

import static org.junit.jupiter.api.Assertions.*;

class AWSConfigurationTest {
    private AWSConfiguration awsConfiguration;

    @BeforeEach
    void setUp() {
        awsConfiguration = new AWSConfiguration();
    }

    @Test
    void s3ClientTest(){
        S3Properties s3Properties = new S3Properties();
        s3Properties.setRegion("us-east-1");
        s3Properties.setBucketName("bucketName");
        s3Properties.setKmsKey("kmsKey");
        assertThrows(SdkException.class, () -> awsConfiguration.s3Client(s3Properties));
    }
}