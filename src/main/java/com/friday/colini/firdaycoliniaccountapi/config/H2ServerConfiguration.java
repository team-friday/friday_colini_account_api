package com.friday.colini.firdaycoliniaccountapi.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("local")
@Component
public class H2ServerConfiguration {
    @Bean
    public Server h2TcpServer() throws Exception{
        return Server.createTcpServer().start();

    }
}
