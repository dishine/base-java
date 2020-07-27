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
		String url = "ws://47.57.152.73:30023/api/v1/ws/";
		HttpHeaders headers = new DefaultHttpHeaders();
		headers.add("tenant_id",6);
		Client client = new Client(url, headers);
		client.start();
	}
<<<<<<< HEAD
}
Beanutils.copyProperties( )
		log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
=======
}
>>>>>>> b9b124bb1e53266cc2a50a3385537239cab71225
