package com.github.ore.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.ore.boot.bootstrap.starter.AbsBootStarter;

@SpringBootApplication
public class BootStarter extends AbsBootStarter
{
    
    @Override
    public void run(String[] args)
    {
        SpringApplication.run(BootStarter.class, args);
    }
    
    public static void main(String[] args)
    {
        BootStarter starter = new BootStarter();
        starter.run(args);
    }
    
}
