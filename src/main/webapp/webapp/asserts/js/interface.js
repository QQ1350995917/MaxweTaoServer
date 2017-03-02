/**
 * Created by dingpengwei on 2/13/17.
 */
function onMenuClick(id, object) {
    active(id);
    if (id == "100") {
        createSystemInfoListView();
    } else if (id == "101") {
        emptyMainContainer();
        $("#mainContainer").append("没数据");
    } else if (id == "102") {
        createSystemMoneyListView();
    } else if (id == "103") {
        createSystemBackupView();
    } else if (id == "104") {
        createSystemVersionView(0, 12);
    } else if (id == "105") {
        createSystemLevelView(0, 12);
    } else if (id == "200") {
        createManagerListView(0, 12);
    } else if (id == "201") {
        createManagerAddView();
    } else if (id == "202") {
        createAgentsListView(0, 12);
    } else if (id == "203") {
        createUsersListView(0, 12);
    } else if (id == "300") {
        emptyMainContainer();
        $("#mainContainer").append("没数据");
    } else if (id == "301") {
        emptyMainContainer();
        $("#mainContainer").append("没数据");
    } else if (id == "302") {
        createThirdDataView();
    }


}
function removeDefaultActive() {
    $(".active").removeClass("active");
}

function active(id) {
    removeDefaultActive();
    $("#" + id + "").addClass("active");
}

function emptyMainContainer() {
    $("#mainContainer").empty();
}

function createPasswordView() {
    removeDefaultActive();
    emptyMainContainer();
    var url = basePath + "/view/password";
    asyncRequestByGet(url, null, function (data) {
        $("#mainContainer").html(data);
        var object = {
            url: basePath + "/manager/password",//form提交数据的地址
            type: "post",　　　  //form提交的方式(method:post/get)
            target: "#mainContainer",　　//服务器返回的响应数据显示的元素(Id)号
            beforeSerialize: function () {
            }, //序列化提交数据之前的回调函数
            beforeSubmit: function () {
            },　　//提交前执行的回调函数
            success: function () {
                alert("修改密码成功");
                createPasswordView();
            },　　　　   //提交成功后执行的回调函数
            error: function () {
                alert("修改密码失败");
            },             //提交失败执行的函数
            dataType: "json",　　　　　　　//服务器返回数据类型
            clearForm: true,　　　　　　 //提交成功后是否清空表单中的字段值
            restForm: true,　　　　　　  //提交成功后是否重置表单中的字段值，即恢复到页面加载时的状态
            timeout: 5000 　　　　　 　 //设置请求时间，超过该时间后，自动退出请求，单位(毫秒)。　　
        };
        $("#form_manager_password").ajaxForm(object);
    }, function () {
        alert("错误");
    }, function () {
        alert("登录超时");
    });
}

function createManagerListView(pageIndex, pageSize) {
    emptyMainContainer();
    var url = basePath + "/manager/accounts";
    var data = {pageIndex: pageIndex, pageSize: pageSize};
    asyncRequestByGet(url, data, function (data) {
        $("#mainContainer").html(data);
    }, function () {
        alert("错误");
    }, function () {
        alert("登录超时");
    });
}

function createManagerAddView() {
    emptyMainContainer();
    var url = basePath + "/view/addm";
    asyncRequestByGet(url, null, function (data) {
        $("#mainContainer").html(data);
        var object = {
            url: basePath + "/manager/create",//form提交数据的地址
            type: "post",　　　  //form提交的方式(method:post/get)
            target: "#mainContainer",　　//服务器返回的响应数据显示的元素(Id)号
            beforeSerialize: function () {
            }, //序列化提交数据之前的回调函数
            beforeSubmit: function () {
            },　　//提交前执行的回调函数
            success: function () {
                alert("创建账户成功");
            },　　　　   //提交成功后执行的回调函数
            error: function () {
                alert("创建账户失败");
            },             //提交失败执行的函数
            dataType: "json",　　　　　　　//服务器返回数据类型
            clearForm: true,　　　　　　 //提交成功后是否清空表单中的字段值
            restForm: true,　　　　　　  //提交成功后是否重置表单中的字段值，即恢复到页面加载时的状态
            timeout: 5000 　　　　　 　 //设置请求时间，超过该时间后，自动退出请求，单位(毫秒)。　　
        };
        $("#form_manager_create").ajaxForm(object);
    }, function () {
        alert("错误");
    }, function () {
        alert("登录超时");
    });
}

function createAgentsListView(pageIndex, pageSize) {
    emptyMainContainer();
    var url = basePath + "/manager/agents";
    var data = {pageIndex: pageIndex, pageSize: pageSize};
    asyncRequestByGet(url, data, function (data) {
        $("#mainContainer").html(data);
    }, function () {
        alert("错误");
    }, function () {
        alert("登录超时");
    });
}

function createUsersListView(pageIndex, pageSize) {
    emptyMainContainer();
    var url = basePath + "/manager/users";
    var data = {pageIndex: pageIndex, pageSize: pageSize};
    asyncRequestByGet(url, data, function (data) {
        $("#mainContainer").html(data);
    }, function () {
        alert("错误");
    }, function () {
        alert("登录超时");
    });
}

function createSystemMoneyListView() {
    emptyMainContainer();
    var url = basePath + "/system/money";
    asyncRequestByGet(url, null, function (data) {
        $("#mainContainer").html(data);
        var object = {
            url: basePath + "/system/append",//form提交数据的地址
            type: "post",　　　  //form提交的方式(method:post/get)
            target: "#mainContainer",　　//服务器返回的响应数据显示的元素(Id)号
            beforeSerialize: function () {
            }, //序列化提交数据之前的回调函数
            beforeSubmit: function () {
            },　　//提交前执行的回调函数
            success: function () {
                alert("追加授权码成功");
                createSystemMoneyListView();
            },　　　　   //提交成功后执行的回调函数
            error: function () {
                alert("追加授权码失败");
            },             //提交失败执行的函数
            dataType: "json",　　　　　　　//服务器返回数据类型
            clearForm: true,　　　　　　 //提交成功后是否清空表单中的字段值
            restForm: true,　　　　　　  //提交成功后是否重置表单中的字段值，即恢复到页面加载时的状态
            timeout: 5000 　　　　　 　 //设置请求时间，超过该时间后，自动退出请求，单位(毫秒)。　　
        };
        $("#form_system_money0").ajaxForm(object);
        $("#form_system_money1").ajaxForm(object);
        $("#form_system_money2").ajaxForm(object);
    }, function () {
        alert("错误");
    }, function () {
        alert("登录超时");
    });
}

function createSystemInfoListView() {
    emptyMainContainer();
    var url = basePath + "/system/system";
    asyncRequestByGet(url, null, function (data) {
        $("#mainContainer").html(data);
    }, function () {
        alert("错误");
    }, function () {
        alert("登录超时");
    });
}

function createEditManagerView(index, loginName) {
    $("#managerListEditor").remove();
    var url = basePath + "/view/grant?loginName=" + loginName;
    asyncRequestByGet(url, null, function (data) {
        $("#managerList" + index).after("<tr id='managerListEditor'>" +
        "<td colspan='8' id='managerListEditorTD'>" +
        "</td>" +
        "</tr>");
        $("#managerListEditorTD").html(data);
        var object = {
            url: basePath + "/manager/grant",//form提交数据的地址
            type: "post",　　　  //form提交的方式(method:post/get)
            target: "#mainContainer",　　//服务器返回的响应数据显示的元素(Id)号
            beforeSerialize: function () {
            }, //序列化提交数据之前的回调函数
            beforeSubmit: function () {
            },　　//提交前执行的回调函数
            success: function () {
                alert("修改账户成功");
            },　　　　   //提交成功后执行的回调函数
            error: function () {
                alert("修改账户失败");
            },             //提交失败执行的函数
            dataType: "json",　　　　　　　//服务器返回数据类型
            clearForm: true,　　　　　　 //提交成功后是否清空表单中的字段值
            restForm: true,　　　　　　  //提交成功后是否重置表单中的字段值，即恢复到页面加载时的状态
            timeout: 5000 　　　　　 　 //设置请求时间，超过该时间后，自动退出请求，单位(毫秒)。　　
        };
        $("#form_manager_editor").ajaxForm(object);
    }, function () {
        alert("错误");
    }, function () {
        alert("登录超时");
    });

}

function removeEditManagerView() {
    $("#managerListEditor").remove();
    return false;
}

function createSystemVersionView(pageIndex, pageSize) {
    $("#managerListEditor").remove();
    var url = basePath + "/version/versions";
    var data = {pageIndex: pageIndex, pageSize: pageSize};
    asyncRequestByGet(url, data, function (data) {
        $("#mainContainer").html(data);
        var object = {
            url: basePath + "/version/create",//form提交数据的地址
            type: "post",　　　  //form提交的方式(method:post/get)
            target: "#mainContainer",　　//服务器返回的响应数据显示的元素(Id)号
            beforeSerialize: function () {
            }, //序列化提交数据之前的回调函数
            beforeSubmit: function () {
            },　　//提交前执行的回调函数
            success: function () {
                alert("创建成功");
                createSystemVersionView(0, 12);
            },　　　　   //提交成功后执行的回调函数
            error: function () {
                alert("创建失败");
            },             //提交失败执行的函数
            dataType: "html",　　　　　　　//服务器返回数据类型
            clearForm: true,　　　　　　 //提交成功后是否清空表单中的字段值
            restForm: true,　　　　　　  //提交成功后是否重置表单中的字段值，即恢复到页面加载时的状态
            timeout: 5000 　　　　　 　 //设置请求时间，超过该时间后，自动退出请求，单位(毫秒)。　　
        };
        $("#form_system_topVersion0").ajaxForm(object);
        $("#form_system_topVersion1").ajaxForm(object);
        $("#form_system_topVersion2").ajaxForm(object);
        $("#form_system_topVersion3").ajaxForm(object);
    }, function () {
        alert("错误");
    }, function () {
        alert("登录超时");
    });
}


function createSystemLevelView(pageIndex, pageSize) {
    $("#managerListEditor").remove();
    var url = basePath + "/level/levels";
    var data = {pageIndex: pageIndex, pageSize: pageSize};
    asyncRequestByGet(url, data, function (data) {
        $("#mainContainer").html(data);
        //var object = {
        //    url: basePath + "/version/create",//form提交数据的地址
        //    type: "post",　　　  //form提交的方式(method:post/get)
        //    target: "#mainContainer",　　//服务器返回的响应数据显示的元素(Id)号
        //    beforeSerialize: function () {
        //    }, //序列化提交数据之前的回调函数
        //    beforeSubmit: function () {
        //    },　　//提交前执行的回调函数
        //    success: function () {
        //        alert("创建成功");
        //        createSystemVersionView(0, 12);
        //    },　　　　   //提交成功后执行的回调函数
        //    error: function () {
        //        alert("创建失败");
        //    },             //提交失败执行的函数
        //    dataType: "html",　　　　　　　//服务器返回数据类型
        //    clearForm: true,　　　　　　 //提交成功后是否清空表单中的字段值
        //    restForm: true,　　　　　　  //提交成功后是否重置表单中的字段值，即恢复到页面加载时的状态
        //    timeout: 5000 　　　　　 　 //设置请求时间，超过该时间后，自动退出请求，单位(毫秒)。　　
        //};
        //$("#form_system_topVersion0").ajaxForm(object);
        //$("#form_system_topVersion1").ajaxForm(object);
        //$("#form_system_topVersion2").ajaxForm(object);
        //$("#form_system_topVersion3").ajaxForm(object);
    }, function () {
        alert("错误");
    }, function () {
        alert("登录超时");
    });
}


function createSystemBackupView() {
    emptyMainContainer();
    var url = basePath + "/system/backups";
    asyncRequestByGet(url, null, function (data) {
        $("#mainContainer").html(data);
        var object = {
            url: basePath + "/system/backup",//form提交数据的地址
            type: "post",　　　  //form提交的方式(method:post/get)
            target: "#mainContainer",　　//服务器返回的响应数据显示的元素(Id)号
            beforeSerialize: function () {
            }, //序列化提交数据之前的回调函数
            beforeSubmit: function () {
            },　　//提交前执行的回调函数
            success: function () {
                alert("备份成功");
                createSystemBackupView();
            },　　　　   //提交成功后执行的回调函数
            error: function () {
                alert("备份失败");
            },             //提交失败执行的函数
            dataType: "json",　　　　　　　//服务器返回数据类型
            clearForm: true,　　　　　　 //提交成功后是否清空表单中的字段值
            restForm: true,　　　　　　  //提交成功后是否重置表单中的字段值，即恢复到页面加载时的状态
            timeout: 5000 　　　　　 　 //设置请求时间，超过该时间后，自动退出请求，单位(毫秒)。　　
        };
        $("#form_system_backup").ajaxForm(object);
    }, function () {
        alert("错误");
    }, function () {
        alert("登录超时");
    });
}

function createThirdDataView() {
    emptyMainContainer();
    var url = basePath + "/system/summaryThird";
    asyncRequestByGet(url, null, function (data) {
        $("#mainContainer").html(data);
        var object = {
            url: basePath + "/system/initThird",//form提交数据的地址
            type: "post",　　　  //form提交的方式(method:post/get)
            target: "#mainContainer",　　//服务器返回的响应数据显示的元素(Id)号
            beforeSerialize: function () {
            }, //序列化提交数据之前的回调函数
            beforeSubmit: function () {
            },　　//提交前执行的回调函数
            success: function () {
                alert("初始化成功");
            },　　　　   //提交成功后执行的回调函数
            error: function () {
                alert("初始化失败");
            },             //提交失败执行的函数
            dataType: "json",　　　　　　　//服务器返回数据类型
            clearForm: true,　　　　　　 //提交成功后是否清空表单中的字段值
            restForm: true,　　　　　　  //提交成功后是否重置表单中的字段值，即恢复到页面加载时的状态
            timeout: 5000 　　　　　 　 //设置请求时间，超过该时间后，自动退出请求，单位(毫秒)。　　
        };
        $("#form_system_initThird").ajaxForm(object);
    }, function () {
        alert("错误");
    }, function () {
        alert("登录超时");
    });
}

