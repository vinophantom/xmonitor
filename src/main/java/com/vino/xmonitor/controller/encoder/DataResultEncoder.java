package com.vino.xmonitor.controller.encoder;

import com.alibaba.fastjson.JSON;
import com.vino.xmonitor.result.DataResult;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class DataResultEncoder implements Encoder.Text<DataResult> {
    @Override
    public String encode(DataResult t) throws EncodeException {
        return JSON.toJSONString(t);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
