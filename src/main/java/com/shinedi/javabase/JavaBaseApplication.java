package com.shinedi.javabase;

import com.shinedi.javabase.common.websocket.Client;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaBaseApplication {
	public static void main(String[] args) {

//		SpringApplication.run(JavaBaseApplication.class, args);
		String releaseV1 = "ws://47.57.152.73:30023/api/v1/ws/";
		String releaseV2 = "wss://stream.dawnbyte.com/ws";
		String devV2 = "ws://47.114.175.98:1325/ws";
		HttpHeaders headers = new DefaultHttpHeaders();
		headers.add("tenant_id",6);
		Client client = new Client(devV2, headers);
		client.start();
	}
}
