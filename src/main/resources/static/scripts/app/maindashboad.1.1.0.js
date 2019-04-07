
'use strict';

//////////////////////////////////////////window设置仓库//////////////////////////////

window.monitorCache = {};
window.intervals = {};

////////////////////////////////////////全局变量////////////////////////////////
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

var storage_select = 'total';
/**
 * 图标颜色的封装
 * @type {{red: string, orange: string, green: string, blue: string, yellow: string, purple: string, grey: string}}
 */
window.chartColors = {
    red: 'rgb(255, 99, 132)',
    orange: 'rgb(255, 159, 64)',
    yellow: 'rgb(255, 205, 86)',
    green: 'rgb(75, 192, 192)',
    blue: 'rgb(54, 162, 235)',
    purple: 'rgb(153, 102, 255)',
    grey: 'rgb(201, 203, 207)'
};

////////////////////////////////////////Websocket///////////////////////////////////////

(function ($) {
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
 */
let sendWebSocketDataRequest = function (dataname) {
    //需要先判断websocket的连接状态
    //1表示连接上，
    if (websocket.readyState === 1) {
        const msg = new WSMessage("get", "", dataname);
        // websocket.addEventListener('open', function () {
        // socket.send(message)

        websocket.send(JSON.stringify(msg));
    } else {
        setTimeout(function () {
            sendWebSocketDataRequest(dataname)
        }, 1000);
    }
};


/**
 * 发送对系统信息的请求
 */
let sendSysRequest = function () {
    sendWebSocketDataRequest("sys")
};
/**
 * 发送CPU信息请求消息
 */
let sendCpuCoresRequest = function () {
    sendWebSocketDataRequest("cpucores")
};

/**
 * 发送对内存信息的请求
 */
let sendMemRequest = function () {
    sendWebSocketDataRequest("mem")
};
/**
 * 发送对网速的请求
 */
let sendNetspeedRequest = function () {
    sendWebSocketDataRequest("netspeed")
};
/**
 * 发送对存储信息的请求
 */
let sendStoragesRequest = function () {
    sendWebSocketDataRequest("storages")
};


//////////////////////////////////数据分发器/////////////////////////////
/**
 * 数据分发方法，收到服务器响应时执行。
 * @Param data Object类型
 */
let distributeData = function (data) {
    if ("sys" === data.dataName) {
        setSysInfo(data.data);
    }
    if ("cpucores" === data.dataName) {
        updateCpuData(data.data);
    } else if ("mem" === data.dataName) {
        updateMemPieData(data.data);
    } else if ("cpu" === data.dataName) {
        setCpuInfo(data.data);
    } else if ("netspeed" === data.dataName) {
        setNetspeed(data.data);
    } else if ("storages" === data.dataName) {
        setStoragesData(data.data);
    }

};

//更新柱状图
let setBarData = function (chart, i, j, newData) {
    chart.data.datasets[i].data[j] = newData;
};

let updateCpuData = function (data) {
    const lineChart = window.CpuUsageChart;
    const barChar = window.CpuEachCoreUsageChart;
    let totalUsage = 0;
    let userUsage = 0;
    const cpucores = data;

    const len = cpucores.length;
    if (len === 0) return;
    for (let i = 0; i < len; i++) {

        setBarData(barChar, 0, i, toPercent(cpucores[i].userUsage));
        setBarData(barChar, 1, i, toPercent(cpucores[i].sytemUsage));
        totalUsage += cpucores[i].combined;
        userUsage += cpucores[i].userUsage;
    }
    totalUsage = toPercent(totalUsage / len);
    userUsage = toPercent(userUsage / len);
    setLineData(lineChart, 0, totalUsage);
    setLineData(lineChart, 1, userUsage);
    barChar.update();
    lineChart.update();
    $("#cpu-usage-percent-number").html('' + totalUsage);

};
let setCpuInfo = function (data) {
    $("#cpu-model-info").html(data.model);
    $("#sum_of_cpu_core").html(data.totalCores);
};


////////////////////////////////////工具方法///////////////////////////////////////////////
/**
 * 判断字符串是否是数字类型
 * @param val
 * @returns {boolean}
 */
let isNumber = function (val) {
    let regPos = /^\d+(\.\d+)?$/; //非负浮点数
    let regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
    return regPos.test(val) || regNeg.test(val);

};

let fixTwo = function (n) {
    if (isNumber(n)) return n.toFixed(2);
    else return 0;
};
let toPercent = function (n) {
    if (isNumber(n)) return fixTwo(n * 100);
    else return 0;
};

/**
 * 单位转换，Byte转换成GiB
 * @param n
 * @returns {number}
 */
let byteToGib = function (n) {
    if (isNumber(n)) return n / 1073741824;
    else return 0;
};


/**
 * 存储单位的转换
 * @param size 单位KiB
 * @returns {string}
 */
let convertStorageSize = function (size) {
    if (!isNumber(size)) return "";
    else if (size > 1048576) return (size / 1048576).toFixed(2) + " GiB";
    else if (size > 1024) return (size / 1024).toFixed(2) + "MiB";
    return "";
};

/**
 * 网速单位转换
 * @param bytesPerSec 网速。单位为比特每秒
 * @returns {string}
 */
const convertNetSpeed = function (bytesPerSec) {
    if (bytesPerSec < 512) return bytesPerSec.toFixed(2) + " B/s";
    else if (bytesPerSec > 512 && bytesPerSec < 524288) return (bytesPerSec / 1000).toFixed(2) + " KB/s"
    else return (bytesPerSec / 1000000).toFixed(2) + "MB/s"
};


/**
 * 补零
 * @return {string}
 */
function PrefixInteger(num, n) {
    return (Array(n).join(0) + num).slice(-n);
}


//////////////////////////////////////图表更新方法/////////////////////////////////
/**
 * 更新内存图标的值
 * @param data
 */
let updateMemPieData = function (data) {
    const newData = data.usedPercent.toFixed(2);
    window.MemChart.data.datasets[0].data[1] = (100 - newData).toFixed(2);
    window.MemChart.data.datasets[0].data[0] = newData;
    window.MemChart.update();

    $("#mem-usage-percent").html(newData);
    $("#memory-used-size-num").html(fixTwo(byteToGib(data.actualUsed)));
    $("#memory-idle-size-num").html(fixTwo(byteToGib(data.actualFree)));

};
/**
 * 更新‘存储’图标的值
 * @param index
 */
let updateStorageChart = function (index) {
    const usePercent = parseFloat(((window.monitorCache.storages[index].usePercent)*100).toFixed(2));
    const freePercent = parseFloat((100 - usePercent).toFixed(2));
    window.StorageChart.data.datasets[0].data[0] = usePercent;
    window.StorageChart.data.datasets[0].data[1] = freePercent;
    window.StorageChart.update();
    $("#storage-total-size").empty().append(convertStorageSize(window.monitorCache.storages[index].total));
    $("#storage-used-size").empty().append(convertStorageSize(window.monitorCache.storages[index].used));
    $("#storage-idle-size").empty().append(convertStorageSize(window.monitorCache.storages[index].free));

};

/**
 * 设置‘存储’图表的值
 * @param data
 */
let setStoragesData = function (data) {
    window.monitorCache.storages = data;
    let totalStorage = 0;
    let totalUsedStorage = 0;
    const select = $("#storage-select").empty();
    select.append($("<option selected>总共</option>").val('total'));
    for (const i in data) {
        totalStorage += data[i].total;
        totalUsedStorage += data[i].used;
        select.append($("<option></option>").append(data[i].dirName).val(i));

    }
    window.monitorCache.storages.total = {};
    window.monitorCache.storages.total.total = totalStorage;
    window.monitorCache.storages.total.used = totalUsedStorage;
    window.monitorCache.storages.total.free = totalStorage - totalUsedStorage;
    window.monitorCache.storages.total.usePercent = (totalUsedStorage / totalStorage);
    // updateStorageChart('total');
    select.val(storage_select);
    updateStorageChart(storage_select);
    select.change(function () {
        updateStorageChart(select.val());
    });
};

/**
 * 设置‘系统信息’
 * @param data
 */
let setSysInfo = function (data) {
    try {
        $("#sys-name").html(data.sys);
        $("#sys-arch").html(data.arch);
        $("#sys-user").html(data.user);
        $("#sys-ip").html(data.currentIP);
        $("#sys-version").html(data.version);
        $("#sys-computer-name").html(data.computerName);
    } catch (e) {
        // statements
        console.log(e);
    }

};
//更新折线图
let setLineData = function (chart, i, newData) {
    chart.data.datasets[i].data.push(newData);
    chart.data.datasets[i].data.splice(0, 1);
};
/**
 * 设置‘网速’值
 * @param data
 */
let setNetspeed = function (data) {

    const upSpeed = data.upLinkNetSpeed;
    const downSpeed = data.downLinkNetSpeed;
    $("#down-net-speed").html(convertNetSpeed(downSpeed));
    $("#up-net-speed").html(convertNetSpeed(upSpeed));
    const upChart = window.NetspeedChart[0];
    const downChart = window.NetspeedChart[1];
    setLineData(upChart, 0, upSpeed);
    setLineData(downChart, 0, downSpeed);
    upChart.update();
    downChart.update();
};
//////////////////////////////////////////sys////////////////////////////////////////////


const getSysInfo = function () {
    $.ajax({
        type: "GET",
        url: '/sys/info',
        dataType: 'json',
        async: false,
        success: function (data) {
            // init(data.data);
            window.monitorCache.SysInfo = data.data;
        },
        error: function () {

        }

    });
};


/**
 * 初始化内存使用率饼图
 */
const initMemChart = function () {
    //
    // 内存pie
    //

    // Data
    const memoryUsageChartData = {
        datasets: [{
            hoverBorderColor: '#ffffff',
            data: [68.3, 31.7],
            backgroundColor: [
                'rgba(0,123,255,0.9)',
                'rgba(0,123,255,0.5)',
                'rgba(0,123,255,0.3)'
            ]
        }],
        labels: ["已用", "空闲"]
    };

    // Options
    const memoryUsageChartOptions = {
        legend: {
            position: 'bottom',
            labels: {
                padding: 25,
                boxWidth: 20
            }
        },
        cutoutPercentage: 0,
        // Uncomment the following line in order to disable the animations.
        // animation: false,
        tooltips: {
            custom: false,
            mode: 'index',
            position: 'nearest'
        }
    };

    const memCtx = document.getElementsByClassName('memory-usage')[0];

    // Generate the users by device chart.
    window.MemChart = new Chart(memCtx, {
        type: 'pie',
        data: memoryUsageChartData,
        options: memoryUsageChartOptions
    });

};
/**
 * 初始化Cpu利用率折线图
 */
const initcpuUsageChart = function () {
    //
    // CPU 利用率 折线图
    //

    const cpuUsageCtx = document.getElementsByClassName('cpu-usage')[0];

    // Data
    const cpuUsageData = {
        // 横坐标
        labels: Array.from(new Array(60), function (_, i) {
            // return i === 0 ? 1 : i;
            return i;
        }).reverse(),
        // 数据
        datasets: [{
            label: '总利用率',
            fill: 'start',
            // data: [0.500, 0.800, 0.320, 0.180, 0.240, 0.320, 0.230, 0.650, 0.590, 0.1200, 0.750, 0.940, 0.1420],
            data: Array(60).fill(0),
            backgroundColor: 'rgba(0,123,255,0.1)',
            borderColor: 'rgba(0,123,255,1)',
            pointBackgroundColor: '#ffffff',
            pointHoverBackgroundColor: 'rgb(0,123,255)',
            borderWidth: 1.5,
            pointRadius: 0,
            pointHoverRadius: 3
        }, {
            label: '用户程序',
            fill: 'start',
            //初始化为0
            data: Array(60).fill(0),
            backgroundColor: 'rgba(255,65,105,0.1)',
            borderColor: 'rgba(255,65,105,1)',
            pointBackgroundColor: '#ffffff',
            pointHoverBackgroundColor: 'rgba(255,65,105,1)',
            borderDash: [3, 3],
            borderWidth: 1,
            pointRadius: 0,
            pointHoverRadius: 2,
            pointBorderColor: 'rgba(255,65,105,1)'
        }]
    };

    // Options
    const cpuUsageOptions = {
        responsive: true,
        legend: {
            position: 'top'
        },
        elements: {
            line: {
                // A higher value makes the line look skewed at this ratio.
                tension: 0.3
            },
            point: {
                radius: 0
            }
        },
        scales: {
            xAxes: [{
                gridLines: false,
                // 横坐标
                ticks: {
                    callback: function (tick, index) {
                        return tick % 10 === 0 ? tick : "";
                    }
                }
            }],
            //
            // 纵轴
            yAxes: [{
                ticks: {
                    suggestedMax: 100,
                    callback: function (tick, index, ticks) {
                        if (tick === 0) {
                            return tick;
                        }
                        // Format the amounts using Ks for thousands.
                        // return tick > 999 ? (tick/ 1000).toFixed(1) + 'K' : tick;
                        return Math.floor(tick) + '%';
                    }
                }
            }]
        },
        // Uncomment the next lines in order to disable the animations.
        // animation: {
        //   duration: 0
        // },
        // hover: {
        //   mode: 'nearest',
        //   intersect: false
        // },
        tooltips: {
            custom: false,
            mode: 'nearest',
            intersect: false
        }
    };

    // Generate the Analytics Overview chart.
    window.CpuUsageChart = new Chart(cpuUsageCtx, {
        type: 'LineWithLine',
        data: cpuUsageData,
        options: cpuUsageOptions
    });

};

/**
 * 初始化Cpu核心条形图
 * @param sysInfo
 */
const initCpuEachCoreUsageChart = function (sysInfo) {
    let cpuEachCoreUsageCtx = document.getElementsByClassName('each-cpu-core-usage')[0];

    const cpuCoreUsageData = {
        labels: Array.from({length: sysInfo.numOfCores}, (v, k) => {
            return k + 1
        }),
        datasets: [{
            label: '用户程序',
            backgroundColor: 'rgb(54, 162, 235)',
            data: Array(sysInfo.numOfCore).fill(0)
        }, {
            label: '系统程序',
            backgroundColor: 'rgb(255, 99, 132)',
            data: Array(sysInfo.numOfCore).fill(0)
        }]

    };
    const cpuEachCoreUsageChartOptions = {
        // title: {
        //     display: true,
        //     text: '内存'
        // },
        tooltips: {
            mode: 'index',
            intersect: false
        },
        responsive: true,
        scales: {
            xAxes: [{
                stacked: true
            }],
            yAxes: [{
                stacked: true,
                ticks: {
                    suggestedMax: 100,
                    callback: function (tick, index, ticks) {
                        if (tick === 0) {
                            return tick;
                        }
                        // Format the amounts using Ks for thousands.
                        // return tick > 999 ? (tick/ 1000).toFixed(1) + 'K' : tick;
                        return Math.floor(tick) + '%';
                    }
                }

            }]
        }
    };
    window.CpuEachCoreUsageChart = new Chart(cpuEachCoreUsageCtx, {
        type: 'bar',
        data: cpuCoreUsageData,
        options: cpuEachCoreUsageChartOptions
    });
};


/**
 * 初始化网速图表
 */
const initNetSpeedCharts = function () {
    const boSmallStatsDatasets = [
        {
            backgroundColor: 'rgba(0, 184, 216, 0.1)',
            borderColor: 'rgb(0, 184, 216)',
            data: [1, 2, 1, 3, 5, 4, 7],
        },
        {
            backgroundColor: 'rgba(23,198,113,0.1)',
            // borderColor: 'rgb(23,198,113)',
            borderColor: 'rgb(255,65,105)',
            data: [1, 2, 3, 3, 3, 4, 4]
        }
        // },
        // {
        //     backgroundColor: 'rgba(255,180,0,0.1)',
        //     borderColor: 'rgb(255,180,0)',
        //     data: [2, 3, 3, 3, 4, 3, 3]
        // },
        // {
        //     backgroundColor: 'rgba(255,65,105,0.1)',
        //     borderColor: 'rgb(255,65,105)',
        //     data: [10, 10, 1, 3, 1, 4, 8]
        // },
        // {
        //     backgroundColor: 'rgb(0,123,255,0.1)',
        //     borderColor: 'rgb(0,123,255)',
        //     data: [3, 2, 3, 2, 4, 5, 4]
        // }
    ];

    // Options
    function boSmallStatsOptions(max) {
        return {
            maintainAspectRatio: true,
            responsive: true,
            // Uncomment the following line in order to disable the animations.
            // animation: false,
            legend: {
                display: false
            },
            tooltips: {
                enabled: false,
                custom: false
            },
            elements: {
                point: {
                    radius: 0
                },
                line: {
                    tension: 0.3
                }
            },
            scales: {
                xAxes: [{
                    gridLines: false,
                    scaleLabel: false,
                    ticks: {
                        display: false
                    }
                }],
                yAxes: [{
                    gridLines: false,
                    scaleLabel: false,
                    ticks: {
                        display: false,
                        // Avoid getting the graph line cut of at the top of the canvas.
                        // Chart.js bug link: https://github.com/chartjs/Chart.js/issues/4790
                        suggestedMax: max
                    }
                }],
            },
        };
    }

    window.NetspeedChart = {};
    // Generate the small charts
    boSmallStatsDatasets.map(function (el, index) {
        // console.log(el);
        const chartOptions = boSmallStatsOptions(Math.max.apply(Math, el.data) + 1);
        const ctx = document.getElementsByClassName('blog-overview-stats-small-' + (index + 1));
        window.NetspeedChart[index] = new Chart(ctx, {
            type: 'line',
            data: {
                labels: ["Label 1", "Label 2", "Label 3", "Label 4", "Label 5", "Label 6", "Label 7"],
                datasets: [{
                    label: 'Today',
                    fill: 'start',
                    data: el.data,
                    backgroundColor: el.backgroundColor,
                    borderColor: el.borderColor,
                    borderWidth: 1.5,
                }]
            },
            options: chartOptions
        });
    });
};

/**
 * 初始化存储环形图
 */
let initStorageChart = function () {

    // Options
    const storageChartOptions = {
        legend: {
            position: 'bottom',
            labels: {
                padding: 60,
                boxWidth: 20
            }
        },
        cutoutPercentage: 50,
        // Uncomment the following line in order to disable the animations.
        // animation: false,
        tooltips: {
            custom: false,
            mode: 'index',
            position: 'nearest'
        }
    };
    const StorageChartData = {
        datasets: [{
            data: [40, 60],
            backgroundColor: [
                window.chartColors.red,
                // window.chartColors.orange,
                // window.chartColors.yellow,
                // window.chartColors.green,
                window.chartColors.blue
            ],
            label: '存储'
        }],
        labels: ['已用', '剩余']
    };
    const storageChartCtx = document.getElementsByClassName('storage-usage')[0].getContext('2d');
    window.StorageChart = new Chart(storageChartCtx, {
        type: 'doughnut',
        data: StorageChartData,
        options: storageChartOptions
    });

};

/**
 * 初始化图表
 * @param sysInfo
 */
let initCharts = function (sysInfo) {
    initStorageChart();
    initcpuUsageChart();
    initNetSpeedCharts();
    initMemChart();
    initCpuEachCoreUsageChart(sysInfo);
};

/**
 * 初始化图表信息
 */
let initChartInfo = function () {
    sendStoragesRequest();
    $("#refresh-storage").click(function () {
        storage_select = $("#storage-select").val();
        sendStoragesRequest();
    });
};

/**
 * 设置获取数据的定时器
 */
let setIntervals = function () {
    window.intervals.getChartDataInterval = setInterval(function () {
        sendMemRequest();
        sendCpuCoresRequest();

    }, 1000);
    window.intervals.getNetSpeedInterval = setInterval(function () {
        sendNetspeedRequest();
    }, 3000);

};
/**
 * 清理定时器
 */
let clearGetCharDataInterval = function () {
    if (window.intervals.getChartDataInterval) window.clearInterval(window.intervals.getChartDataInterval);
    if (window.intervals.getNetSpeedInterval) window.clearInterval(window.intervals.getNetSpeedInterval);
};


//////////////////////////////////////////////DOM加载完毕//////////////////////////////////////////////////



    $(document).ready(function () {
        //初始化WebSocket
        initWebSocket();
        //获取系统信息
        getSysInfo();
        //初始化图表
        initCharts(window.monitorCache.SysInfo);
        //初始化图表信息
        initChartInfo();
        //设置获取信息的定时器
        setIntervals();
    });

})(jQuery);


// const init = function (sysInfo) {
//
//     initCpuEachCoreUsageChart(sysInfo);
//
// };



////////////////////////////////////////////////////////////////////////CPU/////////////////////////////////////////////////////////


// window.chartColors = {
//     red: 'rgb(255, 99, 132)',
//     orange: 'rgb(255, 159, 64)',
//     yellow: 'rgb(255, 205, 86)',
//     green: 'rgb(75, 192, 192)',
//     blue: 'rgb(54, 162, 235)',
//     purple: 'rgb(153, 102, 255)',
//     grey: 'rgb(201, 203, 207)'
// };

// (function(global) {
//     var Months = [
//         'January',
//         'February',
//         'March',
//         'April',
//         'May',
//         'June',
//         'July',
//         'August',
//         'September',
//         'October',
//         'November',
//         'December'
//     ];

//     var COLORS = [
//         '#4dc9f6',
//         '#f67019',
//         '#f53794',
//         '#537bc4',
//         '#acc236',
//         '#166a8f',
//         '#00a950',
//         '#58595b',
//         '#8549ba'
//     ];

//     var Samples = global.Samples || (global.Samples = {});
//     var Color = global.Color;

//     Samples.utils = {
//         // Adapted from http://indiegamr.com/generate-repeatable-random-numbers-in-js/
//         srand: function(seed) {
//             this._seed = seed;
//         },

//         rand: function(min, max) {
//             var seed = this._seed;
//             min = min === undefined ? 0 : min;
//             max = max === undefined ? 1 : max;
//             this._seed = (seed * 9301 + 49297) % 233280;
//             return min + (this._seed / 233280) * (max - min);
//         },

//         numbers: function(config) {
//             var cfg = config || {};
//             var min = cfg.min || 0;
//             var max = cfg.max || 1;
//             var from = cfg.from || [];
//             var count = cfg.count || 8;
//             var decimals = cfg.decimals || 8;
//             var continuity = cfg.continuity || 1;
//             var dfactor = Math.pow(10, decimals) || 0;
//             var data = [];
//             var i, value;

//             for (i = 0; i < count; ++i) {
//                 value = (from[i] || 0) + this.rand(min, max);
//                 if (this.rand() <= continuity) {
//                     data.push(Math.round(dfactor * value) / dfactor);
//                 } else {
//                     data.push(null);
//                 }
//             }

//             return data;
//         },

//         labels: function(config) {
//             var cfg = config || {};
//             var min = cfg.min || 0;
//             var max = cfg.max || 100;
//             var count = cfg.count || 8;
//             var step = (max - min) / count;
//             var decimals = cfg.decimals || 8;
//             var dfactor = Math.pow(10, decimals) || 0;
//             var prefix = cfg.prefix || '';
//             var values = [];
//             var i;

//             for (i = min; i < max; i += step) {
//                 values.push(prefix + Math.round(dfactor * i) / dfactor);
//             }

//             return values;
//         },

//         months: function(config) {
//             var cfg = config || {};
//             var count = cfg.count || 12;
//             var section = cfg.section;
//             var values = [];
//             var i, value;

//             for (i = 0; i < count; ++i) {
//                 value = Months[Math.ceil(i) % 12];
//                 values.push(value.substring(0, section));
//             }

//             return values;
//         },

//         color: function(index) {
//             return COLORS[index % COLORS.length];
//         },

//         transparentize: function(color, opacity) {
//             var alpha = opacity === undefined ? 0.5 : 1 - opacity;
//             return Color(color).alpha(alpha).rgbString();
//         }
//     };

//     // DEPRECATED
//     window.randomScalingFactor = function() {
//         return Math.round(Samples.utils.rand(0, 50));
//     };

//     // INITIALIZATION

//     Samples.utils.srand(Date.now());

//     // Google Analytics
//     /* eslint-disable */
//     if (document.location.hostname.match(/^(www\.)?chartjs\.org$/)) {
//         (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
//         (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
//         m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
//         })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
//         ga('create', 'UA-28909194-3', 'auto');
//         ga('send', 'pageview');
//     }
//     /* eslint-enable */

//     }(this));


//////////////////////////////////msg发送/////////////////////////////////

/////////////////////////////////定时器//////////////////////////////////


// 根据数据名 分发数据

///////////////////////////////////CPU//////////////////////////////////


///////////////////////////////////初始化//////////////////////////////////////////////


// var initCpuCoreChart = function (data) {
//     var numOfCores = data.numOfCores;
//     // var chart = window.CpuEachCoreUsageChart;
//     // while(!chart) {

//     //     setTimeout(function(){chart = window.CpuEachCoreUsageChart;},500);

//     // }
//     console.log(chart)
//     for (var i = 0; i < numOfCores; i ++) {
//         chart.data.labels.push(PrefixInteger(i + 1));
//     }
//     var dataNum = chart.data.datasets.length;
//     for (var i = 0; i < dataNum; i ++ ) chart.data.datasets[i].data = Array(numOfCores).fill(0);

//     getChartDataInterval = setInterval(function(){
//         sendMemRequest();
//         sendCpuCoresRequest();
//     },1000);
// }


// Hide initially the first and last analytics overview chart points.
// They can still be triggered on hover.

// const aocMeta = CpuUsageChart.getDatasetMeta(0);
// aocMeta.data[0]._model.radius = 0;
// aocMeta.data[cpuUsageData.datasets[0].data.length - 1]._model.radius = 0;
//
// // Render the chart.
// window.CpuUsageChart.render();


////////////////////////////////////////////////////////内存////////////////////////////////////////////////////////


//
///////////////////////////////////////////// CPU条形图////////////////////////////////////
// 每个核心显示使用率
//
































