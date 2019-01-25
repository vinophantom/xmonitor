package com.vino.xmonitor.controller.decoder;

import com.vino.xmonitor.bean.hardware.CpuCore;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.util.List;
import java.util.Map;

public class CpuCoreDecoder implements Decoder.Text<CpuCore> {
    @Override
    public CpuCore decode(String s) throws DecodeException {
        return null;
    }

    @Override
    public boolean willDecode(String s) {
        return false;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
