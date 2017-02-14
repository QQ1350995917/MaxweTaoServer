/**
 * Created by dingpengwei on 2/13/17.
 */
function signOut(){
    var url = basePath + "/manager/logout";
    asyncRequestByGet(url,null,function(data){
        window.location.href = basePath + "/manager/login";
    },function(){
        alert("错误");
    },function(){
        alert("登录超时");
    });
}
function onCreateManagerSubmit() {
    var url = basePath + "/manager/create";
    var data = {loginName: "DingPengwei",nickName:"丁朋伟",password:"123456",cellphone:"18511694468",access:"200,201"};
    asyncRequestByGet(url,data,function(data){
        $("#mainContainer").html(data.result);
    },function(){
        alert("错误");
    },function(){
        alert("登录超时");
    });
}






