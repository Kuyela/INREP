package com.ElasticPublisher;

import java.io.File;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.HttpClientBuilder;
public class ElasticPublisher {
    public static void main(String args[]){
        try {
            sendFile();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private static void sendFile() throws Exception{
        String fileName = "C:\\Users\\malin\\Documents\\ELK\\employee.json";
        File jsonFile = new File(fileName);
        HttpEntity  entity = new FileEntity(jsonFile);
        HttpPost post = new HttpPost("http://localhost:9200/_bulk");
        post.setEntity(entity);
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        HttpClient client = clientBuilder.build();
        post.addHeader("content-type", "text/plain");
        post.addHeader("Accept","text/plain");
        HttpResponse response = client.execute(post);
        System.out.println("Response: " + response);
    }
}