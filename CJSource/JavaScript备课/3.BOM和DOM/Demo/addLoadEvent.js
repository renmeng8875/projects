function addLoadEvent(fn) {
    


    var init = function() {

        if (arguments.callee.done) return;
   
        arguments.callee.done = true;

        fn.apply(document,arguments);
    };
    
    //注册DOMContentLoaded事件，如果支持的话
    if (document.addEventListener) {
        document.addEventListener("DOMContentLoaded", init, false);
    }
    
    //但对于Safari，我们需要使用setInterval方法不断检测document.readyState
    //当为loaded或complete的时候表明DOM已经加载完毕
    if (/WebKit/i.test(navigator.userAgent)) {
        var _timer = setInterval(function() {
            if (/loaded|complete/.test(document.readyState)) {
                clearInterval(_timer);
                init();
            }
        },10);
    }
    //对于IE则使用条件注释，并使用script标签的defer属性
    //IE中可以给script标签添加一个defer(延迟)属性，这样，标签中的脚本只有当DOM加载完毕后才执行
    /*@cc_on @*/
    /*@if (@_win32)
    document.write("<script id=__ie_onload defer src=javascript:void(0)><\/script>");
    var script = document.getElementById("__ie_onload");
    script.onreadystatechange = function() {
        if (this.readyState == "complete") {
            init();
        }
    };
    /*@end @*/
    return true;
}