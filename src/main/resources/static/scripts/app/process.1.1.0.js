


'use strict';


window.monitorCache = {};
window.intervals = {};
/////////////////////////////////////////


/**
 * 当前根地址
 * @type {string}
 */
const currRootUrl = window.location.host;
/**
 * WebSocket地址
 * @type {string}
 */
const dataWebSocketUrl = 'ws://' + currRootUrl + '/data';


//初始化导航栏
const initNavi = function() {
    $(".nav-link").removeClass("active");
    $("#process-nav").addClass("active");
};


//建立Websocket连接
let websocket = null;

function initWebSocket() {
//判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket(dataWebSocketUrl);
    } else {
        alert("Don't support websocket");
    }

    websocket.onopen = function () {
        // sendSysRequest();
    };

    websocket.onclose = function () {
        clearGetDataInterval();
    };

    websocket.error = function () {
        clearGetDataInterval();
    };

//接收到消息的回调方法

    websocket.onmessage = function (event) {

        try {
            let data = JSON.parse(event.data);
            //如果服务器成功
            if (isSuccessful(data)) {
                //数据分发
                distributeData(data);
            } else {
                // TODO : 服务器错误处理
            }
        } catch (e) {
            console.log(e);
            //发生错误时停止定时器
            clearGetDataInterval();
        }
    };
}

/**
 * 判断请求是否成功
 * @param data 服务器返回的数据包
 * @returns {boolean}
 */
let isSuccessful = function (data) {
    return 0 === data.code.code;
};



/**
 * 封装对服务器的消息
 * @param message 消息
 * @param data 数据
 * @param requestDataName 请求的数据名称
 * @constructor
 */
let WSMessage = function (message, data, requestDataName) {
    this.message = message;
    this.data = data;
    this.requestDataName = requestDataName;
};


////////////////////////////////////数据请求方法//////////////////////
/**
 * 发送请相关求数据的WebSocket请求
 * @param dataname 数据名。
 * @param args 参数
 */
let sendWebSocketDataRequest = function (dataname, args) {
    //需要先判断websocket的连接状态
    //1表示连接上，
    if (websocket.readyState === 1) {
        const msg = new WSMessage("get", args.join(","), dataname);
        websocket.send(JSON.stringify(msg));
    } else {
        setTimeout(function () {
            const msg = new WSMessage("get", args.join(","), dataname);
            websocket.send(JSON.stringify(msg));
        }, 1000);
    }
};

/**
 * 发送对系统信息的请求
 */
let sendProcessesRequest = function () {
    const sortArg = $("#sort-arg").val();
    sendWebSocketDataRequest("processes", [sortArg])
};


let distributeData = function (data) {
    if ("processes" === data['dataName']) {
        setProcessesTable(data.data);
    }
};


const setIntervals = function() {
    sendProcessesRequest();
    window.intervals.getDataInterval = setInterval(function () {
        sendProcessesRequest();
    }, 3000);
};

let clearGetDataInterval = function () {
    if (window.intervals.getDataInterval) window.clearInterval(window.intervals.getDataInterval);
};


const setProcessesTable = function(data) {
    //TODO:
    const table = $("#processes-table");
    const tbody = table.children("tbody");
    tbody.empty();
    let tr;
    data.forEach(p => {
        tr = $("<tr></tr>");
        tr.data("pid", p.pid);
        tr.data("name", p.name);
        tr.append("<td>" + p.pid + "</td>");
        tr.append("<td title='"  + p.cmd + "'>" + p.name + "</td>");
        tr.append("<td>" + p.user + "</td>");
        tr.append("<td>" + p.cpuUsage + "</td>");
        tr.append("<td>" + p.mem + "</td>");
        tr.append("<td>" + p.share + "</td>");
        tr.append("<td>" + p.startTime + "</td>");
        tr.append("<td><button type='button' class='btn btn-accent btn-pill kill-proc-btn' >结束进程</button></td>");
        tbody.append(tr);
    });
    initButton();
};

let fixTwo = function (n) {
    if (isNumber(n)) return n.toFixed(2);
    else return 0;
};

/**
 * 判断字符串是否是数字类型
 * @param val 字符串或数字
 * @returns {boolean}
 */
let isNumber = function (val) {
    const type = typeof val;
    if(type === "number") {
        return true;
    } else if(type === "string") {
        let regPos = /^\d+(\.\d+)?$/; //非负浮点数
        let regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
        return regPos.test(val) || regNeg.test(val);
    } else {
        return false;
    }

};


const initButton = function() {
    $(".kill-proc-btn").click(function() {
        const pid = $(this).parent().parent().data("pid");
        const pname = $(this).parent().parent().data("name");
        if(!confirm("确定要结束以下进程？\n " + pname)) return;
        killProcess(pid);
    });
};

const killProcess = function(pid) {

    $.ajax({
        type: "delete",
        url: "http://" + currRootUrl + "/process/" + pid,
        dataType: "json",
        success: function (response) {
            console.log(response);
        }
    });
};



$(document).ready(function () {
    //初始化Websocket
    initWebSocket();
    //初始化导航栏
    initNavi();
    //设置定时器
    setIntervals();
});
