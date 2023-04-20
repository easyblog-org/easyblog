package top.easyblog;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableAspectJAutoProxy
@EnableWebMvc
@MapperScans({
		@MapperScan("top.easyblog.dao")
})
@SpringBootApplication(scanBasePackages = {"top.easyblog"})
public class Application {

	public static void main(String[] args) {
		System.setProperty("nacos.logging.default.config.enabled","false");
		SpringApplication.run(Application.class, args);
	}

}
