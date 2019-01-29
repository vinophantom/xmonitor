


'use strict';


(function ($) {

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
const dataWebSocketUrl = 'ws://' + currRootUrl + '/process';


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
        alert("Don't support websocket")
    }

    websocket.onopen = function () {
        sendSysRequest();
    };


    websocket.onclose = function () {
        clearGetCharDataInterval();
    };

    websocket.error = function () {
        clearGetCharDataInterval();
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
            clearGetCharDataInterval();
        }
    };
}





    $(document).ready(function () {
       //初始化导航栏
       initNavi();
        

    });
})(jQuery);