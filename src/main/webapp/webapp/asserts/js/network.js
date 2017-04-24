/**
 * Created by dingpengwei on 2/14/17.
 */
const RC_SUCCESS = 200;//执行成功
const RC_SUCCESS_EMPTY = 204;//执行成功,符合请求条件的参数是空
const RC_PARAMS_BAD = 400;//提交参数不符合要求
const RC_ACCESS_BAD = 401;//权限限制的无法访问
const RC_PARAMS_REPEAT = 406;//登录名重复
const RC_ACCESS_TIMEOUT = 408;//权限超时造成的无法访问
const RC_TO_MANY = 429;//访问频率造成的拒绝服务
const RC_SEVER_ERROR = 500;//服务器内部异常导致的失败


// const basePath = "http://taomami.net";
//const basePath = "http://101.200.56.221:8080";
const basePath = "http://localhost:8080";
//const basePath = "http://192.168.0.103:8080";

function asyncRequestByGet(url, data,onDataCallback, onErrorCallback, onTimeoutCallback) {
    $.ajax({
        type: "get",
        async: false,  //同步请求
        url: url,
        data: data,
        timeout: 3000,
        cache:false,
        beforeSend :function(xmlHttp){
            xmlHttp.setRequestHeader("If-Modified-Since","0");
            xmlHttp.setRequestHeader("Cache-Control","no-cache");
        },
        success: function (data) {
            onDataCallback(data);
        },
        error: function () {
            onErrorCallback();
        }
    });
}

function asyncRequestByPost(url, params,onDataCallback, onErrorCallback, onTimeoutCallback) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
            onDataCallback(xmlHttp.responseText);
        } else {
            onErrorCallback();
        }
    }
    xmlHttp.timeout = 5000;
    xmlHttp.ontimeout = onTimeoutCallback;
    xmlHttp.open("POST", url, false);
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xmlHttp.send(encodeURI(params));
}

function onErrorCallback() {

}

function onTimeoutCallback() {

}

Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    }
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}
