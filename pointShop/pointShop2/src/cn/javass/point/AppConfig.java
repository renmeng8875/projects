package cn.javass.point;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(
        {"classpath:applicationContext-resources.xml", 
         "classpath:cn/javass/point/dao/applicationContext-hibernate.xml",
         "classpath:cn/javass/point/service/applicationContext-service.xml"
        })
public class AppConfig {

}
