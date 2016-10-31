function Drag(titleBar,win,evtStyle) {
	this.titleBar = titleBar;
	this.win = win;
	this.evtStyle = evtStyle;
	titleBar.ownDrager = this;
	titleBar.onmousedown = Drag.startDrag;
}
Drag.startDrag = function (evt) {
	evt = evt || window.event;
	var target = evt.target || evt.srcElement;
	this.evtOffset ={
		x:evt.layerX || evt.offsetX,
		y:evt.layerY || evt.offsetY
	};
	Drag.currentDrag = this.ownDrager;
	this.ownDrager.win.className = this.ownDrager.evtStyle;
	document.onmousemove = Drag.moving;
	document.onmouseup = Drag.stopDrag;
	if (document.selection && document.selection.empty) {
		document.selection.empty();
	} else if (window.getSelection) {
		window.getSelection().removeAllRanges();
	}
};
Drag.moving = function (evt) {
	if (document.selection && document.selection.empty) {
		document.selection.empty();
	} else if (window.getSelection) {
		window.getSelection().removeAllRanges();
	}
	evt = evt || window.event;
	var obj = Drag.currentDrag;
	obj.win.style.left = evt.clientX-obj.titleBar.evtOffset.x+"px";
	obj.win.style.top = evt.clientY-obj.titleBar.evtOffset.y+"px";
};
Drag.stopDrag = function () {
	document.onmousemove = null;
	document.onmouseup = null;
	Drag.currentDrag.win.className = "";
	Drag.currentDrag = null;
};
function XHR() {
	var xhr;
	try {xhr = new XMLHttpRequest();}
	catch(e) {
		var IEXHRVers =["Msxml3.XMLHTTP","Msxml2.XMLHTTP","Microsoft.XMLHTTP"];
		for (var i=0,len=IEXHRVers.length;i< len;i++) {
			try {xhr = new ActiveXObject(IEXHRVers[i]);}
			catch(e) {continue;}
		}
	}
	return xhr;
}
function getCookie(cookieName) {
	var re = new RegExp("\\b"+cookieName+"=([^;]*)\\b");
	var arr =  re.exec(document.cookie);
	return decodeURI(arr?arr[1]:"");
}
function setCookie(name,value,expires,apath,domain,secure) {
	var str = name+"="+encodeURI(value);//不要忘了在对应getCookie函数里面加上decodeURI方法
	if (expires) {str += "; expires="+expires.toGMTString();}
	if (apath) {str += "; path="+apath;}
	if (domain) {str += "; domain="+domain;}
	if (secure) {str += "; secure";}
	document.cookie = str;
}
function delCookie(cookieName) {
	var expires = new Date();
	expires.setTime(expires.getTime()-1);//将expires设为一个过去的日期，浏览器会自动删除它
	document.cookie = cookieName+"=; expires="+expires.toGMTString();
}

(function ($) {
	var fm = $("upFileForm");
	var fileInput = fm.file;
	fm.onsubmit = checkForm;
	createTargetFrame();
	function checkForm() {
		if (!$("upLoadFrame")) {
			createTargetFrame();
		}
		var allowed = [".avi",".rm",".rmvb",".pdf",".wmv",".jpg",".png",".gif",".jpe",".jpeg",".rar",".zip",".7z",".tar",".gz",".iso"];
		var filePath = fileInput.value.toLowerCase();
		for (var i=0,len=allowed.length;i<len;i++) {
			if (filePath.indexOf(allowed[i])==filePath.length-allowed[i].length) {
				progressDiv.style.display="block";
				fm.send.disabled = true;
				cancelUpload = false;
				resetProgress();
				return getProgress();
			}
		}
		alert("对不起，您上传文件格式不符合要求！");
		return false;
	}
	var xhr=XHR();
	function createTargetFrame() {
		var frame = document.createElement("span");
		frame.innerHTML = "<iframe id='upLoadFrame' name='upLoadFrame'></iframe>";
		frame.style.display="none";
		document.body.appendChild(frame);
		frame = null;
	}
	function getProgress() {
		xhr.open("get","action.php",true);
		xhr.setRequestHeader("AjaxRequest-Upload-Progress",fm.APC_UPLOAD_PROGRESS.value);
		xhr.onreadystatechange = listener;
		xhr.send(null);
		return true;
	}
	function listener() {
		if (cancelUpload) {return;}
		if (xhr.readyState== 4 && xhr.status ==200) {
			var obj;
			try {
				obj = eval("("+xhr.responseText+")")
			} catch (e) {}
			if (!obj) {
				setTimeout(getProgress,1000);
				return;
			}
			if (!obj.done) {
				setTimeout(getProgress,1000);
			} else {
				fm.APC_UPLOAD_PROGRESS.value = parseInt(Math.random()*1000000000000000);
				delCookie("lastBytes");
			}
			updateProgressBar(obj);
		}
	}
	var totalBytes = $("totalBytes");
	var currentBytes = $("currentBytes");
	var rate  = $("rate");
	var progressPercent = $("progressPercent");
	var progressPic = $("progressPic");
	var cancelUpload = false;
	function updateProgressBar(obj) {
		totalBytes.innerHTML = formatFileSize(obj.total);
		currentBytes.innerHTML = formatFileSize(obj.current);
		if (!obj.rate) {
			var lastBytes = parseInt(getCookie("lastBytes"));
			if (!lastBytes) {
				obj.rate=0;
			} else {
				obj.rate = (obj.current-lastBytes)*8;
			}
		}
		rate.innerHTML = formatFileSize(obj.rate/8);
		progressPercent.innerHTML = (obj.current/obj.total*100).toFixed(2)+"%";
		progressPic.style.width = parseInt(obj.current/obj.total*100)+"%";
		if (!obj.done) {
			setCookie("lastBytes",obj.current);
		}
	}
	function formatFileSize(size) {
		if (!size) return 0;
		if (size > 0 && size <1024) {
			return parseInt(size)+"B";
		} else if (size >=1024 && size < 1048576) {
			return (size/1024).toFixed(2)+"KB";
		} else {
			return (size/1024/1024).toFixed(2)+"MB";
		}
	}
	var progressDiv = $("progressDiv");
	new Drag($("titleBar"),progressDiv,"moving")
	var closeDiv = $("closeDiv");
	closeDiv.onclick = function () {
		if (parseInt(progressPic.style.width) != 100) {
			if (confirm("您确定要取消上传吗？")) {
				if (window.ActiveXObject) {
					location.reload();
				} else {
					$("upLoadFrame").parentNode.removeChild($("upLoadFrame"));
				}
				cancelUpload = true;
				fm.send.disabled = false;
			} else {
				return false;
			}
		}
		progressDiv.style.display="none";
	};
	function resetProgress() {
		progressPic.style.width = "0px";
		totalBytes.innerHTML ="0MB";
		currentBytes.innerHTML = "0MB";
		rate.innerHTML = "0MB";
		progressPercent.innerHTML = "0%";
		
	}
})(function (id) {return document.getElementById(id)});