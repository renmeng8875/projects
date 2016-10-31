/**
 * 分之单例
 * 用处:
 * 在做Ajax的时候根据不同的浏览器获得不同XHR(XMLHttpRquets)
 * 不同的分辨率初始化不一样的界面(PCAT2)
 */
(function(){
	//得到机器的分辨率
	var screenWidth = window.screen.width;
	var screenHeight = window.screen.height;
	var portalInfo = (function(){
		var $12801024 = {info:"1,2,3,5"};
		var $1024768 = {info:"4,2,4,6"};
		if(screenWidth == 1280){
			return $12801024;
		}else if(screenWidth == 1024){
			return $1024768; 
		}
	})();
	alert(portalInfo.info)
})()
//这些并非javascript的高深技术,是他的使用技巧