package com.example.api.utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

import java.net.http.HttpRequest.Builder;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RestHttpsRequest {
    private static final String CT = "Content-Type";
        private static final String GOOGLE_RECAPTCHA = null;
                private static final String ACCESS = null;
                private final HttpClient client;
                private static RestHttpsRequest instance = null;
                private ObjectMapper om = new ObjectMapper();
            
                public static RestHttpsRequest getInstance() {
                    if(instance==null) {
                        instance = new RestHttpsRequest();
                    }
                    return instance;
                }
            
                private RestHttpsRequest() {
                    this.client = CreateHttpClient.getInstance().getClient();
                }
            
                public <T> T convertStringToObject(String source, Class<T> valueType) {
                    try {
                        return om.readValue(source, valueType);
                    } catch (JsonProcessingException e) {
                        log.error("couldn't convert {} to desired object", source);
                        return null;
                    }
                }
                public String convertObjectToString(Object bodyAsObject) {
                    try {
                        return om.writeValueAsString(bodyAsObject);
                    } catch (JsonProcessingException e) {
                        return "{'convesioneEror':'error'}".replace('\'', '\"');
                    }
                }
                public <T> T convertStringToObject(String content, TypeReference<T> valueTypeRef) {
                    try {
                        return om.readValue(content, valueTypeRef);
                    } catch (JsonProcessingException e) {
                        log.debug("Error converting {}", content);
                        return null;
                    }
                }
            
                private String buildError(Exception err) {
                    log.error(err.getMessage());
                    return "{'error':'" + err.getMessage() + "'}".replace('\"', '\'');
                }
            
                /**
                 * Invokes a "PUT" REST request for a specific url, body and jwt.
                 * Similar to the method that receives a String instead an Object.
                 * 
                 * @param url String with the full endpoint URL
                 * @param bodyAsObject Object with the body information
                 * @param jwt String with the authorized JWT
                 * @return String whit JSON structure
                 */
                public String sendPutRequest(String url, Object bodyAsObject, String jwt) {
                    try {
                        String bodyAsJson = om.writeValueAsString(bodyAsObject);
                        return sendPutRequest(url, bodyAsJson, jwt);
                    } catch (JsonProcessingException e) {
                        return buildError(e);
                    }
                }
                public String sendPostRequest(String url, Object bodyAsObject, String jwt) {
                    try {
                        String bodyAsJson = om.writeValueAsString(bodyAsObject);
                        return sendPostRequest(url, bodyAsJson, jwt);
                    } catch (JsonProcessingException e) {
                        return buildError(e);
                    }
                }
                public String sendPostRequest(String url, Object bodyAsObject, Map<String, String> headers) {
                    try {
                        String bodyAsJson = om.writeValueAsString(bodyAsObject);
                        return sendPostRequest(url, bodyAsJson, headers);
                    } catch (JsonProcessingException e) {
                        return buildError(e);
                    }
                }
                public String sendPutRequest(String url, String bodyAsJson, String jwt) {
                    try {
                        return sendChangeRequest(url, bodyAsJson, jwt, "PUT");
                    } catch (Exception e) {
                        return buildError(e);
                    }
                }
                public String sendPutRequest(String url, String bodyAsJson, Map<String, String> headers) {
                    try {
                        return sendChangeRequest(url, bodyAsJson, headers, "PUT");
                    } catch (Exception e) {
                        return buildError(e);
                    }
                }
            
                public String sendPostRequest(String url, String bodyAsJson, String jwt) {
                    try {
                        return sendChangeRequest(url, bodyAsJson, jwt, "POST");
                    } catch (Exception e) {
                        return buildError(e);
                    }
                }
            
                public String sendPostRequest(String url, String bodyAsJson, Map<String, String> headers) {
                    try {
                        return sendChangeRequest(url, bodyAsJson, headers, "POST");
                    } catch (Exception e) {
                        return buildError(e);
                    }
                }
            
                public String sendDeleteRequest(String url, String jwt) {
                    try {
                        return sendChangeRequest(url, "", jwt, "POST");
                    } catch (Exception e) {
                        return buildError(e);
                    }
                }
            
                public String sendDeleteRequest(String url, String bodyAsJson, Map<String, String> headers) {
                    try {
                        return sendChangeRequest(url, bodyAsJson, headers, "DELETE");
                    } catch (Exception e) {
                        return buildError(e);
                    }
                }
            
                public String sendGetRequest(String url, String jwt) {
                    try {
                        return sendGetRequestBase(url, jwt);
                    } catch (Exception e) {
                        return buildError(e);
                    }
                }
            
                public String sendGetRequest(String url, Map<String, String> headers) {
                    try {
                        return sendGetRequestBase(url, headers);
                    } catch (Exception e) {
                        return buildError(e);
                    }
                }
            
                public String sendGetRequest(String url) {
                    return sendGetRequest(url,"");
                }
            
                public String checkCaptcha(String response) {
                    return checkCaptcha(this.GOOGLE_RECAPTCHA, response);
            }
        
            public String checkCaptcha(String url, String response) {
                return genericRecaptchaChecker(url, "6LetXssSAAAAAJAWVWHjn8NCf47XAxLSlD_ePHLR", response);
            }
        
            public String genericRecaptchaChecker(String url, String secret, String response) {
                StringBuilder redirectUrl = new StringBuilder();
                    redirectUrl.append(url);
                    redirectUrl.append("?secret=");
                    redirectUrl.append(secret);
                    redirectUrl.append("&response=");
                    redirectUrl.append(response);
                    redirectUrl.append("&remoteip=127.0.0.1");
                Map<String, String> headers = new HashMap<>();
                headers.put(CT, "application/x-www-form-urlencoded");
                String body = "";
                return this.sendPostRequest(redirectUrl.toString(), body, headers);
            }
            /** Private methods */
        
            private String sendChangeRequest(
                    String url, 
                    String bodyAsJson, 
                    String jwt, 
                    String type) throws IOException, InterruptedException {
                        Map<String, String> headers = new HashMap<>();
                        if(jwt!=null && jwt.trim().length()>0) headers.put("jwt", jwt);
                        return sendChangeRequest(url, bodyAsJson, headers, type);
            }
        
            private String sendChangeRequest(
                    String url, 
                    String bodyAsJson, 
                    Map<String, String> headers, 
                    String type) throws IOException, InterruptedException {
                Builder prev = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header(CT, "application/json");
                        if(headers!=null && headers.size()>0) {
                            for(Map.Entry<String, String> entry : headers.entrySet()) {
                                prev.header(entry.getKey(), entry.getValue());
                            }
                        }
                        if("post".equalsIgnoreCase(type.toLowerCase())) {
                            prev.POST(BodyPublishers.ofString(bodyAsJson));
                        } else if("put".equalsIgnoreCase(type.toLowerCase())) {
                            prev.PUT(BodyPublishers.ofString(bodyAsJson));
                        } else if("delete".equalsIgnoreCase(type.toLowerCase())) {
                            // If body is not null, log a warning
                            if(bodyAsJson!=null && !bodyAsJson.isBlank()) {
                                log.warn("Ignoring not null body for DELETE verb");
                            }
                            prev.DELETE();
                        } else {
                            return "HTTP verb " + type + " unsopported by this invoker";
                        }
                HttpRequest request = prev.build();
        
                // Send the request and get the response
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
                // Return the response body
                return response.body();
            }
        
        
        
            private String sendGetRequestBase(String url, String jwt) throws Exception {
                Map<String, String> headers = new HashMap<>();
                headers.put("jwt", jwt);
                return sendGetRequestBase(url, headers);
            }
        
            private String sendGetRequestBase(String url, Map<String, String> headers) throws Exception {
                Builder prev = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header(CT, "application/json");
                    if(headers!=null && headers.size()>0) {
                        for(String key : headers.keySet()) {
                            prev.header(key, headers.get(key));
                        }
                    }
                    prev.GET();
                HttpRequest request = prev.build();
        
                // Send the request and get the response
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
                // Return the response body
                return response.body();
            }
        
            public boolean invalidJwt(String jwt) {
                return this.sendGetRequest(this.ACCESS + "verify-jwt/"+jwt).contains("null@mail.com");
    }   

}


