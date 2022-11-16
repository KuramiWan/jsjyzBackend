package ga.jsjyz.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan("ga.jsjyz.mapper")
@Configuration
public class MybatisPlusConfig {
}

