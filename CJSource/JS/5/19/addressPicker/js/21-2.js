//21-2.js
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
	$('#form21-2').submit(function (evt) {
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
			if ($('#companyName').val().length<2) {
				alert('请填写正确的公司名称!');
				return $('#companyName').focus();
			}
			if ($('#officeName').val().length<2) {
				alert('请填写正确的职位名称!');
				return $('#officeName').focus();
			}
			if ($('#certifyName').val().length<2) {
				alert('请填写正确的证明人姓名!');
				return $('#cetifyName').focus();
			}
			this.submit();
		}
	});

});