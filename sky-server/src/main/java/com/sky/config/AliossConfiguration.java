package com.sky.config;

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
/**
 * AliossConfiguration创建aliossUtil对象
 */
public class AliossConfiguration {
    @Bean
    @ConditionalOnMissingBean//没有创建Bean时，才创建AliOssUtil对象
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties) {
        log.info("AliossConfiguration init success");
        return new AliOssUtil(aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName());


    }
}
