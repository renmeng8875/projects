tip = {
	_tipObj: $("#tip"),
	_tipC: $("#tip div"),
	_intTime: -1,
	
	_setValue : function(msg){
		tip._tipC.html(msg);
	},
	info : function(msg,noHide){
		tip._setValue(msg);
		tip._tipObj.attr("class","info");
		tip.show(noHide);
	},

	err : function(msg,noHide){
		tip._setValue(msg);
		tip._tipObj.attr("class","error");
		tip.show(noHide);
	},
	succ : function(msg,noHide){
		tip._setValue(msg);
		tip._tipObj.attr("class","succ");
		tip.show(noHide);
	},
	hide : function(){
		tip._tipObj.hide();
	},
	show : function(noHide){
		if(tip._intTime != -1){
			window.clearTimeout(tip._intTime);
			tip._intTime = -1;
		}
		
		tip._tipObj.css("top",window.pageYOffset + "px").show();
		setTimeout(function() {
	    	var w = tip._tipObj[0].getBoundingClientRect().width;
	    	tip._tipObj.css("marginLeft", -w/2 + "px").show();
			if(!noHide){
				tip._intTime = window.setTimeout(tip.hide,3000);
			}
		}, 100);
	}
};