package com.epam.training;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.epam.training.delivery", "com.epam.training.order", "com.epam.training.revision"
//        ,"com.epam.training.stock"
})
public class BootConfig {
}
