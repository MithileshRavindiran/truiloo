package com.example.demo.app;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trulioo.normalizedapi.ApiClient;
import com.trulioo.normalizedapi.api.ConfigurationApi;
import com.trulioo.normalizedapi.api.ConnectionApi;
import com.trulioo.normalizedapi.api.VerificationsApi;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by mravindran on 31/12/19.
 */
@Configuration
@Getter
@Setter
public class ProjectConfiguration {


    @Value("${basepath}")
    String basePath;

    @Value("${apikey}")
    String apiKey;

    @Bean
    public ApiClient apiClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(basePath);
        apiClient.setApiKey(apiKey);
        return apiClient;
    }

    @Bean
    public ConnectionApi connectionApi() {
        ConnectionApi connectionApi = new ConnectionApi(apiClient());
        return connectionApi;
    }

    @Bean(name = "truilooConfigApi")
    public ConfigurationApi configurationApi() {
        ConfigurationApi configurationApi = new ConfigurationApi(apiClient());
        return configurationApi;
    }

    @Bean
    public VerificationsApi verificationsApi() {
        VerificationsApi verificationsApi = new VerificationsApi(apiClient());
        return verificationsApi;
    }


    @Bean(name = "restTemplate")
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        restTemplate.getMessageConverters().add(0, mappingJacksonHttpMessageConverter());
        return restTemplate;
    }

    @Bean(name = "mappingHttpConverter")
    public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper());
        return converter;
    }

    @Bean(name = "objectMapper")
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true);
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return objectMapper;
    }
}
