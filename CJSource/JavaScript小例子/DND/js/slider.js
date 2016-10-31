(function () {
/**
args  {
	knob Node
	mode DND.MODE_H DND.MODE_V
	posRange [0,100]
	valueRange [0,100]
	onChange Function
}
*/
function Slider(args) {
	DND.init.call(Slider,this,args);
	this.valueLength=this.values[1]-this.values[0];

	this.attrs=this.mode==DND.MODE_H?{
		uh:"X",
		css:"left"
	}:{
		uh:"Y",
		css:"top"
	};

	var $this=this;
	
	this.DNDInstance=new DND({
		layer:this.knob,
		mode:this.mode,
		range:this.range,
		onDrag:function (evt,dnd) {
			var pos=parseInt(getRealStyle(dnd.layer,$this.attrs.css)),
				percent=pos/$this.posLength;
			$this.value=Math.round(percent*$this.valueLength+$this.values[0]);
			$this.onChange($this.value,$this);
		}
	});
	var range=this.DNDInstance.getRange();
	this.posLength=range["max"+this.attrs.uh]-range["min"+this.attrs.uh];
}
extend(Slider,{
	defaultArgs:{
		mode:DND.MODE_H,
		values:[0,100],
		value:0
	}
});

Slider.prototype={
	setValue:function (v) {
		if (!v.inter(this.values[0],this.values[1]))
			return false;
		var percent=(v-this.values[0])/this.valueLength,
			pos=Math.round(this.posLength*percent);
		
		this.value=v;
		this.knob.style[this.attrs.css]=pos+"px";
	}
};


window.Slider=Slider;
	
})();