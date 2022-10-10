package com.epam.training.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyThread implements Runnable {

  private static final Logger LOGGER = LoggerFactory.getLogger(MyThread.class);

  @Override
  public void run() {
    LOGGER.info("Called from thread");
  }
}
