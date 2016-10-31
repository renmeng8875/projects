//zc-21-2.js
jQuery(function ($) {
	$('#addParamButton').click(function () {
		var copyNodes=$('#form-zc-21-2 dd.addParamCopy:last,#form-zc-21-2 dt.addParamCopy:last');
		$('#form-zc-21-2 dd.addParamCopy:last').after(copyNodes.clone());
	});
	$('#form-zc-21-2').submit(function (evt) {
		evt.preventDefault();
		var fill=true,$f=$(this),val,obj;
		$f.find('.required')
		.each(function () {
			if (!this.value) {
				alert('请将表单填写完整!');
				this.focus();
				return fill=false;
			}
		});
		if (fill) this.submit();
	});

});