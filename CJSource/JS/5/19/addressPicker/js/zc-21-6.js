//zc-21-6.js
jQuery(function ($) {
	$('#endDateInput').datePicker({
		clickInput:true,
		startDate:new Date().asString(),
		createButton:false,
		showYearNavigation:true
	});
	$('#salarySelect').change(function () {
		if(!this.value)  {
			$('#salaryDefine').show();
		} else {
			$('#salaryDefine').hide();
		}
	});
	$('#endDateButton').click(function () {$('#endDateInput').click();});
	$('#form-zc-21-6').submit(function (evt) {
		evt.preventDefault();
		var fill=true,$f=$(this),val,obj;
		$f.find('.required:visible')
		.each(function () {
			if (!this.value) {
				alert('请将表单填写完整!');
				this.focus();
				return fill=false;
			}
		});
		if (fill) {
			$f.find('.numberField:visible').each(function () {
				if (!/\d+/.test(this.value)) {
					alert('请填写正确的数字!');
					this.focus();
					return fill=false;
				}
			});
			if (fill) this.submit();
		}
	});
	
	$('#workPlace').addressPicker({maxSelectCount:2});	
});