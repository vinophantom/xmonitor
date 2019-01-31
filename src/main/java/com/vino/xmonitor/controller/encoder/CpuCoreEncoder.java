package com.vino.xmonitor.controller.encoder;

import com.alibaba.fastjson.JSON;
import com.vino.xmonitor.bean.hardware.CpuCore;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @author phantom
 */
public class CpuCoreEncoder implements Encoder.Text<CpuCore> {
    @Override
    public void init(EndpointConfig ec) { }
    @Override
    public void destroy() { }
    @Override
    public String encode(CpuCore cpuCore) throws EncodeException {
        return JSON.toJSONString(cpuCore);
    }
}