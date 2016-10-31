(function () {
/**
args {
province SelectElement
city	SelectElement
cityWrap Element
town	SelectElement
townWrap Element
waitImg ImageElement
}
*/
AreaSelect.proxy="action.php";
function AreaSelect(args) {
	Base.init(AreaSelect,this,args);
	var $this=this;
	addEvent(this.province,"change",function () {
		var css=this.value?"inline":"none";
		$this.cityWrap.style.display=css;
		$this.townWrap.style.display="none";
		$this.initSelect($this.province.value,$this.city);
	});
	addEvent(this.city,"change",function () {
		var css=this.value?"inline":"none";
		$this.townWrap.style.display=css;
		$this.initSelect($this.city.value,$this.town);
	});
}

AreaSelect.prototype={
	initSelect:function (id,select) {
		select.options.length=1;
		this.waitImg.style.display="inline";
		var $this=this;
		Base.ajax({
			url:AreaSelect.proxy,
			data:{aid:id},
			success:function (json) {
				var data=eval("("+json+")");
				for (var i=0,opt;i<data.length;i++) {
					opt=new Option(data[i][0],data[i][1]);
					select.add(opt,null);
				}
				$this.waitImg.style.display="none";
			}
		});
		
	}
};

addEvent(window,"load",function () {
	new AreaSelect({
		province:$("province"),
		city:$("city"),
		town:$("town"),
		cityWrap:$("citySpan"),
		townWrap:$("townSpan"),
		waitImg:$("waitPic")
	});
});

})();