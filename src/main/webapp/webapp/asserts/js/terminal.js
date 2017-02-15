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
