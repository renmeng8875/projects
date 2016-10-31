jQuery(function ($) {
	
	
	var ul=$("#domainSearchResult"),
			illegalDomain=$("#domainSearchResult li.domain"),
			illegalSuffix=$("#domainSearchResult li.suffix"),
			waitLi=$("#domainSearchResult li.wait"),
			failedLi=$("#domainSearchResult li.failed"),
			takenLi=$("#domainSearchResult li.taken"),
			availableLi=$("#domainSearchResult li.available"),
			closeResult=$("#closeResult");
	
	$([illegalDomain[0],illegalSuffix[0]]).hide();
	
	$([waitLi[0],failedLi[0],takenLi[0],availableLi[0]]).remove();
	
	closeResult.click(function () {
		ul.slideUp("slow");
		closeResult.hide();
	});
	
	
	$("#domainSearchForm").submit(function (evt) {
		evt.preventDefault();
		var domain=jQuery.trim(this.domain.value);
		this.domain.value=domain;
		var suffix=$("p.suffix input:checked",this);
		var re=/(^[0-9a-z][0-9a-z-]*[0-9a-z]$)|^[0-9a-z]$/i;
		illegalDomain.hide();
		illegalSuffix.hide();
		ul.show();
		if (!re.test(domain) || domain.length>63) {
			closeResult.show();
			illegalDomain.slideDown("slow");
			return;
		}
		if (!suffix.length) {
			closeResult.show();
			illegalSuffix.slideDown("slow");
			return;
		}
		
		
		var suffixAry=[],list=[];
		ul.find("li:not(.illegal)").remove();
		suffix.each(function () {
			suffixAry.push(this.value);
			var li=waitLi.clone();
			li.data("suffix",this.value);
			li.append(domain+this.value);
			ul.append(li);
			list.push(li);
			li.find("a").click(function () {
				li.remove();
			});
			
		});
		
		var url="action.php";
		
		searchDomain(list,suffixAry);
		//发送Ajax请求
		
		function searchDomain(list,suffixAry) {
			$.getJSON(url,{
				domain:domain,
				suffix:suffixAry
			},function (data) {
				jQuery.each(list,function () {
					var suffix=this.data("suffix");
					if (!suffix) return;
					var newLi=[0,availableLi,takenLi,failedLi][data[suffix].code].clone();
					newLi.append(domain+suffix);
					if (data[suffix].code===3) {
						newLi.find("a").click(function () {
							var li=waitLi.clone();
							li.data("suffix",suffix);
							li.append(domain+suffix);
							newLi.replaceWith(li);
							li.find("a").click(function () {
								li.remove();
							});
							searchDomain([li],[suffix]);
						});
					}
					this.replaceWith(newLi);
				});
			});
			
		}
		
	});
});