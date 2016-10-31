//21-1.js
jQuery(function ($) {
	$('#birthdaySelect').datePicker({
		clickInput:true,
		startDate:'1940-1-1',
		endDate:'2000-1-1',
		showYearNavigation:true
	});
	$('#form21-1').submit(function (evt) {
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
			if ($('#userName').val().length<2) {
				alert('请填写正确的姓名!');
				return $('#userName').focus();
			}
			obj=$('#userHeight')
			val=Number(obj.val());
			if (val>300 || val<10) {
				alert('请填写正确的身高!');
				return obj.focus();
			}
			obj =$('#userExpYear');
			val=Number(obj.val());
			if (val<0 || val > 50) {
				alert('请填写正确的工作经验!');
				return obj.focus();
			}
			obj=$('#userExpMonth');
			val=Number(obj.val());
			if (val <1 || val >12) {
				alert('请填写正确的工作经验!');
				return obj.focus();
			}
			obj=$('#userTel');
			val=obj.val();
			if (!/^[0-9-]{5,12}$/.test(val)) {
				alert('请填写正确的固定电话号码!');
				return obj.focus();
			}
			obj=$('#userMobile');
			val=obj.val();
			if (!/^[0-9-]{5,20}$/.test(val)) {
				alert('请填写正确的手机号码!');
				return obj.focus();
			}
			obj=$('#userEmail');
			val=obj.val();
			if (!$.emailCheck(val)) {
				alert('请填写正确的邮箱地址!');
				return obj.focus();
			}
			this.submit();
		}
	});
	
	$('#userWorkPlace').addressPicker();	
	$('#hAddress').addressPicker({maxSelectCount:1});
});