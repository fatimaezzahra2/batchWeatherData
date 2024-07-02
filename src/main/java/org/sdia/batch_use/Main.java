package org.sdia.batch_use;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Main {
    public static String timelineRequestHttpClient(String apiEndPoint, String location, String startDate, String endDate, String apiKey) throws Exception {
      /*  String apiEndPoint = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";
        String location = "London";
        String startDate = "2024-05-01";
        String endDate = "2024-05-03";*/

        String unitGroup = "metric";
       // String apiKey = "JN69GT5FUL5LLDTL6CGQGTR4W";

        StringBuilder requestBuilder = new StringBuilder(apiEndPoint);
        requestBuilder.append(URLEncoder.encode(location, StandardCharsets.UTF_8.toString()));

        if (startDate != null && !startDate.isEmpty()) {
            requestBuilder.append("/").append(startDate);
            if (endDate != null && !endDate.isEmpty()) {
                requestBuilder.append("/").append(endDate);
            }
        }

        URIBuilder builder = new URIBuilder(requestBuilder.toString());

        builder.setParameter("unitGroup", unitGroup)
                .setParameter("key", apiKey);

        HttpGet get = new HttpGet(builder.build());
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(get);

        String rawResult = null;
        try {
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                System.out.printf("Bad response status code:%d%n", response.getStatusLine().getStatusCode());
                return null;
            }

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                rawResult = EntityUtils.toString(entity, Charset.forName("utf-8"));
            }
        } finally {
            response.close();
        }
        return rawResult;
    }
}
