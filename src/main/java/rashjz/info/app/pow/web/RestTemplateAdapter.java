package rashjz.info.app.pow.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import rashjz.info.app.pow.config.properties.ApiProperties;
import rashjz.info.app.pow.exception.ClientException;

import java.net.URI;
import java.util.Map;


@Slf4j
@Component
public class RestTemplateAdapter {


    private final ApiProperties apiProperties;

    private final RestTemplate restTemplate;

    @Autowired
    public RestTemplateAdapter(RestTemplate restTemplate, ApiProperties apiProperties) {
        this.restTemplate = restTemplate;
        this.apiProperties = apiProperties;
    }

    public <T> T execute(String resourcePath, HttpMethod httpMethod, HttpEntity<?> request, Class<T> returnType) {
        return execute(resourcePath, null, httpMethod, request, returnType);
    }

    public <T> T execute(String resourcePath, Map<String, ?> pathVariables, HttpMethod httpMethod, Class<T> returnType) {
        return execute(resourcePath, pathVariables, httpMethod, null, returnType);
    }

    private  <T> T execute(String resourcePath, Map<String, ?> pathVariables, HttpMethod httpMethod, HttpEntity<?> request, Class<T> returnType) {
        URI uri = buildResourceURIWithPathVariables(resourcePath, pathVariables);
        try {
            ResponseEntity<T> responseEntity;
            switch (httpMethod) {
                case GET:
                    responseEntity = restTemplate.getForEntity(uri, returnType);
                    break;
                case POST:
                    responseEntity = restTemplate.postForEntity(uri, request, returnType);
                    break;
                case PUT: {
                    restTemplate.put(uri, request);
                    return null;
                }
                default:
                    throw new IllegalArgumentException("Unrecognized Http Method");
            }

            validateResponse(responseEntity);

            return responseEntity.getBody();

        } catch (RestClientException ex) {
            log.error("error in execution ", ex);
            throw new ClientException(ex);
        }
    }


    private URI buildResourceURIWithPathVariables(String resourcePath, Map<String, ?> uriVariables) {
        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString(apiProperties.getUri())
                .path(resourcePath)
                .build();
        if (!CollectionUtils.isEmpty(uriVariables)) {
            uriComponents = uriComponents.expand(uriVariables);
        }
        return uriComponents
                .encode()
                .toUri();
    }


    private void validateResponse(ResponseEntity<?> responseEntity) {
        HttpStatus responseStatus = responseEntity.getStatusCode();
        if (responseStatus != HttpStatus.OK && responseStatus != HttpStatus.CREATED && responseStatus != HttpStatus.NO_CONTENT) {
            throw new ClientException(responseStatus.value() + " " + responseStatus.getReasonPhrase());
        }
    }


}
