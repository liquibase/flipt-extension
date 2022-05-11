package org.liquibase.ext.precondition;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Objects;

public class Flipt {
    private String url;
    private FliptFlag[] fliptFlags;

    public Flipt(String url) {
        this.url = url;
    }

    private void fetchFlags() throws IOException {
        HttpGet request = new HttpGet(this.url + "/api/v1/flags");
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new IOException("Unable to connect to Flipt API.");
            }

            if (entity != null) {
                String result = EntityUtils.toString(entity);
                Gson gson = new Gson();
                FliptResponse res = gson.fromJson(result, FliptResponse.class);
                this.fliptFlags = res.flags;
            }
        }
    }

    public Boolean Enabled(String key) throws IOException {
        if (this.fliptFlags == null) {
            this.fetchFlags();
        }
        for (FliptFlag fliptFlag : this.fliptFlags) {
            if (Objects.equals(fliptFlag.key, key)) {
                return Boolean.TRUE.equals(fliptFlag.enabled);
            }
        }
        return false;
    }
}

class FliptResponse {
    public FliptFlag[] flags;
}

class FliptFlag {
    public String key;
    public String name;
    public Boolean enabled;
}
