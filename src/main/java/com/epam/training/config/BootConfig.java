package com.epam.training.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
    basePackages = {
      "com.epam.training.config",
      "com.epam.training.controller",
      "com.epam.training.dao",
      "com.epam.training.exception",
      "com.epam.training.model",
      "com.epam.training.service"
    })
public class BootConfig {}
