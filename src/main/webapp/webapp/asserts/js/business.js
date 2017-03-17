/**
 * Created by dingpengwei on 3/14/17.
 */
function createGoodsAddView() {
    emptyMainContainer();
    var url = basePath + "/view/addGoodsView";
    var data = {};
    asyncRequestByGet(url, data, function (data) {
        $("#mainContainer").html(data);
        iterationGoodsAddView();
    });
}
/**
 * 提交URL进行查找后迭代进行提交数据
 */
function iterationGoodsAddView() {
    var object = {
        url: basePath + "/tao/generate",//form提交数据的地址
        type: "post",　　　  //form提交的方式(method:post/get)
        target: "#mainContainer",　　//服务器返回的响应数据显示的元素(Id)号
        beforeSerialize: function () {
        }, //序列化提交数据之前的回调函数
        beforeSubmit: function () {
        },　　//提交前执行的回调函数
        success: function (data) {
            $("#mainContainer").html(data);
            iterationGoodsAddView();
        },　　　　   //提交成功后执行的回调函数
        error: function () {
            alert("查询失败");
        },             //提交失败执行的函数
        dataType: "html",　　　　　　　//服务器返回数据类型
        clearForm: true,　　　　　　 //提交成功后是否清空表单中的字段值
        restForm: true,　　　　　　  //提交成功后是否重置表单中的字段值，即恢复到页面加载时的状态
        timeout: 5000 　　　　　 　 //设置请求时间，超过该时间后，自动退出请求，单位(毫秒)。　　
    };
    $("#generateGoodsForm").ajaxForm(object);
}

/**
 * 商品发布
 * @param viewId
 * @param goods
 */
function publishGoods(viewId, goods) {
    var url = basePath + "/tao/publish";
    var data = {goods: goods};
    $.post(url,data,function(result){
        alert("发布成功");
        $("#" + viewId).attr("disabled", true);
    });
}

function createGoodsListView(pageIndex, pageSize){
    emptyMainContainer();
    var url = basePath + "/tao/query";
    var data = {pageIndex: pageIndex, pageSize: pageSize};
    asyncRequestByGet(url, data, function (data) {
        $("#mainContainer").html(data);
    });
}

function deleteGoods(){
    var object = {
        url: basePath + "/tao/delete",//form提交数据的地址
        type: "post",　　　  //form提交的方式(method:post/get)
        target: "#mainContainer",　　//服务器返回的响应数据显示的元素(Id)号
        beforeSerialize: function () {
        }, //序列化提交数据之前的回调函数
        beforeSubmit: function () {
        },　　//提交前执行的回调函数
        success: function (data) {
            $("#mainContainer").html(data);
        },　　　　   //提交成功后执行的回调函数
        error: function () {
            alert("删除失败");
        },             //提交失败执行的函数
        dataType: "html",　　　　　　　//服务器返回数据类型
        clearForm: true,　　　　　　 //提交成功后是否清空表单中的字段值
        restForm: true,　　　　　　  //提交成功后是否重置表单中的字段值，即恢复到页面加载时的状态
        timeout: 5000 　　　　　 　 //设置请求时间，超过该时间后，自动退出请求，单位(毫秒)。　　
    };
    $("#delete_goods_form").ajaxForm(object);
}
