(function () {


addEvent(window,"load",function () {
	var red_scale=$("red_scale");
	var red_knob=$("red_knob");
	var red_slider=new Slider({
		knob:red_knob,
		range:red_scale,
		mode:DND.MODE_H,
		values:[0,255],
		onChange:setColor
	});
	
	
	var green_scale=$("green_scale");
	var green_knob=$("green_knob");
	var green_slider=new Slider({
		knob:green_knob,
		range:green_scale,
		mode:DND.MODE_H,
		values:[0,255],
		onChange:setColor
	});
	
	
	var blue_scale=$("blue_scale");
	var blue_knob=$("blue_knob");
	var blue_slider=new Slider({
		knob:blue_knob,
		range:blue_scale,
		mode:DND.MODE_H,
		values:[0,255],
		onChange:setColor
	});
	
	
	function setColor() {
		document.body.style.backgroundColor="rgb("+
			red_slider.value+
			","+green_slider.value
			+","+blue_slider.value
			+")";
			
		$("header").style.color="rgb("+
			(255-red_slider.value)+
			","+(255-green_slider.value)
			+","+(255-blue_slider.value)
			+")";

	}
	setColor();
	
	
});


})();