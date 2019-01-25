package com.vino.xmonitor.config;

import com.vino.xmonitor.controller.ws.DataSocketServer;
import com.vino.xmonitor.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }


    @Autowired
    public void setMessageService(CpuService cpuService,
                                  MemService memService,
                                  SysService sysService,
                                  NetService netService,
                                  StorageService storageService) {
        DataSocketServer.cpuService = cpuService;
        DataSocketServer.memService = memService;
        DataSocketServer.sysService = sysService;
        DataSocketServer.netService = netService;
        DataSocketServer.storageService = storageService;
    }
}
