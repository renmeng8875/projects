//21-4.js
jQuery(function ($) {
	$('#workPlaceHope').addressPicker({maxSelectCount:2});
	$('#form21-4').submit(function (evt) {
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