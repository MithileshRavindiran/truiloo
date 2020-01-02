package com.example.demo;

import com.trulioo.normalizedapi.ApiCallback;
import com.trulioo.normalizedapi.ApiClient;
import com.trulioo.normalizedapi.ApiException;
import com.trulioo.normalizedapi.api.ConfigurationApi;
import com.trulioo.normalizedapi.api.ConnectionApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by mravindran on 31/12/19.
 */
@Service
public class TestAuthentication {

    @Autowired
    private RestTemplate restTemplate;


    public void testAuthentication() throws ApiException {
        //Example Username: JoeNapoli_API_Demo, Example Password: 05uZuPRCyPi!6
        ApiClient apiClient = new ApiClient();
        apiClient.setUsername("JoeNapoli_API_Demo");
        apiClient.setPassword("05uZuPRCyPi!6");
        ConnectionApi connectionClient = new ConnectionApi(apiClient);

//testAuthentication
        String testResult = connectionClient.testAuthentication();

//testAuthenticationAsync
        connectionClient.testAuthenticationAsync(new ApiCallback<String>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Logger.getLogger(TestAuthentication.class.getName()).log(Level.SEVERE, null, e);
            }

            @Override
            public void onSuccess(String result, int statusCode, Map<String, List<String>> responseHeaders) {
                System.out.println(result);
            }

            @Override
            public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {
                //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {
                //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

    public void testAuthenticationRest() throws ApiException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-trulioo-api-key", "cfffb9d452479019f8f5267bc5994667");
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://gateway.trulioo.com/trial/connection/v1/testauthentication", HttpMethod.GET, entity, String.class);

        String responseString = response.getBody();

    }

}
