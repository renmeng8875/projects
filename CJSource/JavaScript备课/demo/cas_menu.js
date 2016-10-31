	
		
		function getObj(id) {
			return document.getElementById(id);
		}
		function getObjs(className) {
			var eles = document.getElementsByTagName("*");
			var reArr = [];
			for (var i=0;i<eles.length;i++) {
				if (eles[i].className == className) {
					reArr.push(eles[i]);
				}
			}
			return reArr;
		}
		function initAll() {
			function pop() {
				var dis = this.menu.style.display ;
				if (dis =="block" || !dis) {
					this.menu.style.display = "none";
				} else {
					this.menu.style.display ="block";
				}
			}
			var menuTitles=getObjs("menuTitle");
			var menuContents = getObjs("menuContent");
			
			for (var i=0;i< menuTitles.length;i++) {
				menuTitles[i].menu = menuContents[i];
				menuTitles[i].onclick =pop;
			}
			
		}
	window.onload = initAll;