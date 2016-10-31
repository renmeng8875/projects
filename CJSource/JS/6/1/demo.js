jQuery(function ($) {
	//xhrÖ»ÄÜ´«×Ö·û´®
	var finput=$("#fm1 input:file");
	$("#fm1").submit(function () {
		var i=setInterval(function () {
			$.get("progress.php?apc_id="+$("#fm1")[0].APC_UPLOAD_PROGRESS.value,function (data) {
				$("#progressContainer").html(data);
				var obj=eval("("+data+")");
				if (obj.done) {
					clearInterval(i);
				}
			});
		},300);
	});
	
	
});