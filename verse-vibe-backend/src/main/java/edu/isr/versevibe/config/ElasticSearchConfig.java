package edu.isr.versevibe.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.elasticsearch.client.RestClient;

import java.nio.file.Files;
import java.nio.file.Paths;

import static edu.isr.versevibe.constants.Constants.HTTP;

public class ElasticSearchConfig {
    private static final String HOST = "localhost";
    private static final int PORT = 9200;
    private static final String USERNAME = "elastic";
    private static final String PASSWORD = "rA0j3Q7k";


    public static ElasticsearchClient createClient() {
        try {
            final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(USERNAME, PASSWORD));
            RestClient restClient = RestClient.builder(new HttpHost(HOST, PORT, HTTP)).setHttpClientConfigCallback(
                    httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
                            .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())).build();
            final ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
            return new ElasticsearchClient(transport);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getAttributeMappings(String attributeMappingsFile) {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(attributeMappingsFile)));

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(jsonContent);

            return mapper.writeValueAsString(node);
        } catch (Exception e) {
            System.err.println("Error reading or parsing the JSON file. " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
