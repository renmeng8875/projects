var res = ["res/bg.gif","res/border.png","res/jquery-1.7.2.min.js"];

for(var i=0;i<res.length;i++){
	var xhr = new XMLHttpRequest();
	xhr.open("GET",res[i]);
	xhr.send(null);
	
	//
	postMessage(res[i]);
}