package net.ultrasist.api.utils;

import java.net.http.HttpClient;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.net.http.HttpClient.Builder;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateHttpClient {
    private static final int SECONDS_DELAY = 10;
    private final HttpClient client;
    private boolean ignoreSslVerification = true;
    private static CreateHttpClient instance = null;

    private CreateHttpClient() {
            // Create the HTTP client Builder
            Builder httpClientBuilder = HttpClient
                .newBuilder()
                .connectTimeout(Duration.ofSeconds(SECONDS_DELAY));

            // Set the sslContext only if we want to do so
            if(this.ignoreSslVerification) {
                try {
                    SSLContext sslContext = this.createCertTrustManager();
                    httpClientBuilder.sslContext(sslContext);
                } catch (KeyManagementException | NoSuchAlgorithmException e) {
                    log.error("Could not set the SslContext because: {}", e.getMessage());
                }
            }
            
            // Create the HTTP client
            this.client = httpClientBuilder.build();
    }

    public static CreateHttpClient getInstance() {
        if(instance==null) {
            instance = new CreateHttpClient();
        }
        return instance;
    }

    public HttpClient getClient() {
        return this.client;
    }

    private SSLContext createCertTrustManager() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCertificates = new TrustManager[] {
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];// it was null
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    // This method MUST be implemented, as it has been defined in 
                    // the interface named TrustManager. It doesn't matter if it 
                    // is empty, it has to be here.
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    // This method MUST be implemented, as it has been defined in
                    // the interface named TrustManager. It doesn't matter if it
                    // is empty, it has to be here.
                }
            }
        };
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());
        return sslContext;
    }
}
