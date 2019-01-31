package com.vino.xmonitor.controller.ws;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.vino.xmonitor.constant.DataNames;
import com.vino.xmonitor.controller.encoder.DataResultEncoder;
import com.vino.xmonitor.result.CodeMsg;
import com.vino.xmonitor.result.DataResult;
import com.vino.xmonitor.service.*;
import com.vino.xmonitor.utils.StringUtils;
import com.vino.xmonitor.msg.WSMessage;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author phantom
 */
@ServerEndpoint(
        value = "/data",
        encoders = {DataResultEncoder.class}
)
@Component
public class DataSocketServer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static SysService sysService;
    public static CpuService cpuService;
    public static MemService memService;
    public static NetService netService;
    public static StorageService storageService;
    public static ProcessService processService;


    /**
     * 用本地线程保存session
     */
    private static ThreadLocal<Session> sessions = new ThreadLocal<Session>();


    /**
     * 连接
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
         distributeData(session, DataNames.CPU_NAME, "");
    }

    /**
     * 关闭
     *
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
    }

    /**
     * 接收消息   客户端发送过来的消息
     *
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {

        WSMessage msg = JSON.parseObject(message, new TypeReference<WSMessage>() {});
        handleMsg(msg, session);
    }

    /**
     * 异常
     *
     * @param session
     * @param throwable
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        try {
            throwable.printStackTrace();
        } finally {
            sessions.remove();
        }
    }


    /**
     * 统一的发送消息方法
     *
     * @param session
     * @param msg
     */
    public synchronized void sendMsg(Session session, String msg) {
        try {
            session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 统一的发送数据方法
     *
     * @param session Seesion对象
     * @param o 数据
     */
    private synchronized void sendObj(Session session, Object o) {
        try {
            session.getBasicRemote().sendObject(o);
        } catch (IOException | EncodeException e) {
            logger.error("发送消息失败！", e);
        }
    }


    private void handleMsg(WSMessage msg, Session session) {
        String dataName = msg.getRequestDataName();
        if (!StringUtils.isEmptyString(dataName)) {
            distributeData(session, dataName, "");
        }
        // TODO
    }

    private void distributeData(Session session, String dataName, String args ) {
        try {
            if (DataNames.CPU_CORES_NAME.equals(dataName)) {
                sendObj(session, DataResult.success(DataNames.CPU_CORES_NAME, cpuService.getCpuCores()));
            } else if (DataNames.MEM_NAME.equals(dataName)) {
                sendObj(session, DataResult.success(DataNames.MEM_NAME, memService.getMem()));
            } else if(DataNames.CPU_NAME.equals(dataName)) {
                sendObj(session, DataResult.success(DataNames.CPU_NAME, cpuService.getCpu()));
            } else if(DataNames.SYS_NAME.equals(dataName)) {
                sendObj(session, DataResult.success(DataNames.SYS_NAME, sysService.getSysInfo()));
            } else if(DataNames.NET_SPEED_NAME.equals(dataName)) {
                sendObj(session,DataResult.success(DataNames.NET_SPEED_NAME, netService.getCacheNetSpeed()));
            } else if(DataNames.STORAGE_NAME.equals(dataName)) {
                sendObj(session, DataResult.success(DataNames.STORAGE_NAME, storageService.getStorages()));
            } else if(DataNames.PROCESSES_NAME.equals(dataName)) {
                sendObj(session, DataResult.success(DataNames.PROCESSES_NAME, processService.getProcessVoList(args)));
            }
        } catch (Exception e) {
            sendObj(session, DataResult.error(CodeMsg.SERVER_ERROR));
            logger.error("数据分发失败", e);
        }

    }


}
