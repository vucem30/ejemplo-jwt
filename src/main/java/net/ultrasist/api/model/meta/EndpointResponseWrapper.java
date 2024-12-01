package net.ultrasist.api.model.meta;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class EndpointResponseWrapper {
    private String content;
    private HttpStatus httpStatus;
    private String response;
    private String responseCasting;

    public EndpointResponseWrapper() {
    }

    public EndpointResponseWrapper(
            HttpStatus httpStatus, 
            String response, 
            String responseCasting) {
        this.httpStatus = httpStatus;
        this.response = response;
        this.responseCasting = responseCasting;
        String tmp = "{'httpResponseCode':"+httpStatus+", 'responseCasting':"+responseCasting+", 'response':"+response+"}";
        this.content = tmp.replace('\'', '\"');
    }

}
