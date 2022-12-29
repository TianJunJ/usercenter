package com.yupi.usercenter;
//spring boot全局启动入口
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/**mybatis会扫描mapper文件，将文件中的增删改查操作注入到项目中 */
@MapperScan("com.yupi.usercenter.mapper")
public class UserCenterApplication {
	public static void main(String[] args) {
		/**springboot从此处启动 */
		SpringApplication.run(UserCenterApplication.class, args);
	}

}
