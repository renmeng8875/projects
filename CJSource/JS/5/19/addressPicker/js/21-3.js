//21-3.js
jQuery(function ($) {
	$('#rYearFrom').click(function () {
		$(this).datePicker({
			clickInput:true,
			startDate:'1940-1-1',
			endDate:$('#rYearTo').val() || new Date().asString(),
			createButton:false,
			showYearNavigation:true
		});
	});
	$('#rYearFromButton').click(function () {$('#rYearFrom').click();});
	$('#rYearTo').click(function () {
		$('#rToday').attr('checked','');
		$(this).datePicker({
			clickInput:true,
			createButton:false,
			endDate:new Date().asString(),
			startDate:$('#rYearFrom').val() || '1940-1-1',
			showYearNavigation:true
		});
	});
	$('#rYearToButton').click(function () {$('#rYearTo').click();});
	$('#rToday').click(function () {
		if (this.checked) {
			$('#rYearTo').data('oldvalue',$('#rYearTo').val());
			$('#rYearTo').val(new Date().asString());
		} else {
			$('#rYearTo').val($('#rYearTo').data('oldvalue'));
		}
	});
	$('#form21-3').submit(function (evt) {
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