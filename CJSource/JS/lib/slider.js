(function () {
	
/**
args {
	knob Node	�϶��ֱ�
	range	Node	���Ʒ�Χ
	values [start,end] ֵ�ķ�Χ
	mode "H"/"V"	ˮƽ�϶����Ǵ�ֱ�϶�
}
*/
function Slider(args) {
	Base.init(Slider,this,args);
	
	this.valueLength=this.values[1]-this.values[0];//ֵ�ı仯
	
	var $this=this;
	this.value=this.values[0];
	
	this.modeAttrs=this.mode=="H"?{
		raMax:"maxX",
		raMin:"minX",
		css:"left"
	}:{
		raMax:"maxY",
		raMin:"minY",
		css:"top"
	};
	
	this.DNDInstance=new DND({
		layer:this.knob,
		range:this.range,
		mode:this.mode,
		onMove:function (evt,dnd) {
			var far=parseInt(getRealStyle($this.knob,$this.modeAttrs.css)) || 0,
					range=dnd.getRange(),
					rangeLength=range[$this.modeAttrs.raMax]-range[$this.modeAttrs.raMin],
					percent=far/rangeLength,
					value=parseInt(percent*$this.valueLength)+$this.values[0];
					
					
			
			$this.value=value;
			if ($this.onChange)
				$this.onChange(value,$this);
		}
	});
}

Slider.defaultArgs ={
	values:[0,100],
	mode:"H"
};
Slider.prototype={
	getValue:function () {
		return this.value;
	},
	setValue:function (v) {
		var percent=(v-this.values[0])/this.valueLength,
				range=this.DNDInstance.getRange(),
				rangeLength=range[this.modeAttrs.raMax]-range[this.modeAttrs.raMin],
				pos=percent*rangeLength;
				
		this.knob.style[this.modeAttrs.css]=pos+"px";
		this.value=v;
		if (this.onChange)
			this.onChange(v);
	}
};

window.Slider=Slider;


})();