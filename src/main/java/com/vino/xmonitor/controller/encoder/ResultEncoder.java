package com.vino.xmonitor.controller.encoder;

import com.alibaba.fastjson.JSON;
import com.vino.xmonitor.result.Result;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @author phantom
 */
public class ResultEncoder implements Encoder.Text<Result> {
    @Override
    public String encode(Result t) throws EncodeException {
        return JSON.toJSONString(t);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
