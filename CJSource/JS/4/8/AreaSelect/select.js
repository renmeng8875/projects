(function ($) {
	var provinceSel = $("province");
	var citySel =document.createElement("select");
	var townSel =document.createElement("select");
	var waitPic =$("waitPic");
	waitPic.style.visibility="hidden";
	$("city").parentNode.replaceChild(citySel,$("city"));
	$("town").parentNode.replaceChild(townSel,$("town"));
	citySel.name ="city";
	citySel.appendChild(provinceSel.options[0].cloneNode(true));
	townSel.name="town";
	townSel.appendChild(provinceSel.options[0].cloneNode(true));
	provinceSel.nextSel = citySel;
	citySel.nextSel=townSel;
	provinceSel.onchange = citySel.onchange  = getArea;
	function getArea(evt) {
		evt = evt || window.event;
		var obj =evt.target || evt.srcElement;
		var selectObj = obj.options[obj.selectedIndex];
		if (!selectObj.value) {
			while (obj.nextSel) {
				obj.nextSel.options.length=1;
				obj=obj.nextSel;
			}
			return false;
		}
		waitPic.style.visibility = "";
		var xhr = window.ActiveXObject?new ActiveXObject("Microsoft.XMLHTTP"):new XMLHttpRequest();
		xhr.open("get","select.php?id="+selectObj.value);
		xhr.onreadystatechange = function () {
			if (xhr.readyState == 4 && xhr.status ==200) {
				var db = xhr.responseXML;
				if (!db) {
					alert("出错了,请重试!");
				}
				var nodes = db.documentElement.getElementsByTagName("option");
				var opt;
				obj.nextSel.options.length =1;
				if (obj.nextSel.nextSel) {
					obj.nextSel.nextSel.options.length=1;
				}
				for (var i=0,len=nodes.length;i<len;i++) {
					opt = new Option(nodes[i].firstChild.nodeValue,nodes[i].getAttribute("value"));
					try {
						obj.nextSel.add(opt);
					}catch(e) {
						obj.nextSel.add(opt,null);
					}
				}
				waitPic.style.visibility="hidden";
			}
		};
		xhr.send(null);
	}
})(function (id) {return document.getElementById(id);});