package com.vino.xmonitor.controller.encoder;

import com.alibaba.fastjson.JSON;
import com.vino.xmonitor.bean.hardware.CpuCore;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.util.List;

public class CpuCoresEncoder implements Encoder.Text<List<CpuCore>> {


    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public String encode(List<CpuCore> cpuCores) throws EncodeException {
        return JSON.toJSONString(cpuCores);
    }
}
