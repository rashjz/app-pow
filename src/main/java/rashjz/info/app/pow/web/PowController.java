package rashjz.info.app.pow.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import rashjz.info.app.pow.config.properties.ApiProperties;
import rashjz.info.app.pow.domain.Post;
import rashjz.info.app.pow.domain.RequestData;


@Slf4j
@RestController
@RequestMapping("/api/pow")
public class PowController {
    private static final String resourcePath = "/submit";
    private final ApiProperties apiProperties;
    private final RestTemplateAdapter restTemplateAdapter;

    @Autowired
    public PowController(ApiProperties apiProperties, RestTemplateAdapter restTemplateAdapter) {
        this.apiProperties = apiProperties;
        this.restTemplateAdapter = restTemplateAdapter;
    }

    @GetMapping(value = "/get")
    public Post submitGet() {
        HttpEntity<RequestData> requestData = new HttpEntity<>(RequestData.builder()
                .actionId("1")
                .processId("2")
                .build());

        return restTemplateAdapter.execute(resourcePath, HttpMethod.POST, requestData, Post.class);
    }

    @PostMapping(value = "/submit")
    public Post testRestApi(@RequestBody RequestData action) {
        return Post.builder().id(1)
                .title(apiProperties.getUsername())
                .userId(Integer.valueOf(action.getActionId()))
                .build();
    }
}
