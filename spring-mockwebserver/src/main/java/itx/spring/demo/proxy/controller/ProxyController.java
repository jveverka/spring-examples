package itx.spring.demo.proxy.controller;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/services")
public class ProxyController {

    private static final String baseUrlDefault = "http://localhost:8087";
    private final OkHttpClient client;
    private String baseUrl;

    public ProxyController() {
        client = new OkHttpClient();
        baseUrl = baseUrlDefault;
    }

    @GetMapping("/data")
    public ResponseEntity<String> getAll() {
        try {
            Request request = new Request.Builder()
                    .url(baseUrl + "/data")
                    .get()
                    .build();
            Response response = client.newCall(request).execute();
            return ResponseEntity.ok(response.body().string());
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

}
