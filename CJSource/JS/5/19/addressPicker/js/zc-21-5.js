//zc-21-5.js
jQuery(function ($) {
	$('#form-zc-21-5').submit(function (evt) {
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
		
		if (fill) {
			$('#form-zc-21-5 input.numberFields').each(function () {
				if (!/\d+/.test(this.value)) {
					alert('请填写正确的号码!');
					this.focus();
					return fill=false;
				}
			});
		}
		if (fill) {
			if (!$.emailCheck($('#email21-5').val())) {
				alert('请填写正确的邮箱地址!');
				return $('#email21-5').focus();
			}
			if (!/[a-z0-9.-]+/i.test($('#homePage'))) {
				alert('请填写正确的公司主页地址!');
				return $('#homePage').focus();
			}
		
			this.submit();
		}
	});

});