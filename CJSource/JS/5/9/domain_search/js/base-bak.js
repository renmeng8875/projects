/*
 * 盐城太极网络
 * http://www.yctjwl.com/
 * JavaScript
 * Powered By CJ(QQ:499457633)
 * Date: 2009-10-26 14:34:21 +0800 (Tue, 26 Oct 2009)
 * More infomation: http://www.pl4cj.org
 */


jQuery(function ($) {
	//Highlight the navList
	var navLinksHighlight=$("#nav li a.highlight"),
	navLinks=$("#nav li a").hover(function () {
		navLinks.removeClass("highlight");
		$(this).addClass("highlight");
	}).mouseout(function () {
		navLinks.removeClass("highlight");
		navLinksHighlight.addClass("highlight");
	});
	
	//For XHTML Strict DTD, Element a has no declaration for an attribute named "target"
	//To open a url in new window,must use javascript window.open method
	$("a.blank").click(function (evt,href) {
		if (/^#/.test($(this).attr('href'))) {
			//Anchor link or empty link,ignore it!
			return true;
		}
		window.open(this.href,'_blank');
		evt.preventDefault();
	});
	
	//Player
	$(".playerContainer").each(function () {
		var buttons,contents,container=$(this);
		
		//Bind the function to the dom element's attribute for solve the event bubble problem
		this.onmouseover=function () {clearInterval(interval);};
		this.onmouseout=function () {clearInterval(interval);interval=setInterval(playerInterval,3000);};
		
		$((contents=container.find(".playerContent").hide())[0]).show();
		(buttons = container.find(".playerButton")).hover(function () {
			buttons.removeClass("focus");
			$(this).addClass("focus");
			$(contents.hide()[buttons.index(this)]).show();
		});
		
		var interval=setInterval(playerInterval,3000);
		function playerInterval() {
			var index;
			(index=buttons.index(container.find(".focus"))+1) >= buttons.length?index=0:index;
			buttons.removeClass("focus");
			$(buttons[index]).addClass("focus");
			$(contents.hide()[index]).show();
		}
	});
	
	
	
	
	
	
	//Domain Search
	var resultUl=$('#domainSearchResult').empty(),
	closeButton=$('#closeResult').click(function () {
		resultUl.slideUp();
		closeButton.hide();
	});
	$('#domainSearchForm').submit(function (evt) {
		closeButton.show();
		evt.preventDefault();
		var domain=this.domain.value,
		i=0,
		suffix=$(this).find('p.suffix input:checked');
		if (!domain || /[^a-z0-9-]/i.test(domain) || /^-|-$/.test(domain)) {
			resultUl.html('<li class="illegal domain">域名只能包含数字、字母及减号，且不能以减号开头或结尾！长度允许范围为1-63位！</li>').slideDown();			
		} else if (!suffix.length) {
			resultUl.html('<li class="illegal suffix">请选择至少一个域名后缀！</li>').slideDown();
		} else {
			resultUl.empty();
			suffix.each(function () {
				var _self=this,fullDomain=domain+_self.value,
				abortDomainSearch=function () {
					$(this).parent().remove();
					!resultUl.children().length && resultUl.slideUp();
				},li=$(document.createElement('li'))
				.addClass('wait')
				.append(
				$(document.createElement('a'))
					.attr('href','###')
					.text('取消')
					.click(abortDomainSearch)).append('<img src="images/wait.gif" alt="正在查询..." title="正在查询..." />')
				.append(fullDomain)
				.appendTo(resultUl);
				(function () {
					var reTry=arguments.callee;
					var xhr=$.getJSON('action/search_domain.php',{domain:domain,suffix:_self.value},function (json) {
						if (!li || !li[0].parentNode) return;
						switch (json.code) {
							case false:
								//Error!Force Notice!
								alert(json.error);
								break;
							case 1:
								li=li.replaceWith(
								'<li class="available"><em>未被注册</em>'+
								fullDomain
								+'</li>'
								);
								break;
							case 0:
								li=li.replaceWith(
								'<li class="taken"><strong>已被注册</strong><a href="http://www.'+
								fullDomain
								+'" target="_blank">前往</a><a href="http://www.whois-search.com/whois/www.'+
								fullDomain
								+'" target="_blank">Whois信息</a>'+
								fullDomain
								+'</li>'
								);
								break;
							case -1:
							default:
								//Search Timeout
								var newLi=$(document.createElement('li'))
								.addClass('failed').append('<strong>查询超时</strong>')
								.append($(document.createElement('a'))
									.attr('href','###').html('重试').click(function (evt) {
										evt.preventDefault();
										newLi.replaceWith(li);
										li.find('a').click(abortDomainSearch);
										reTry();
								})).append(fullDomain);
								li.replaceWith(newLi);
								break;
						}
					});
				})();
			});
			resultUl.slideDown();
		}
		
	});
	$('#domainSearchForm input[name=domain]').blur(function () {
		if (resultUl.find('li.illegal').length) {
			resultUl.slideUp();
			closeButton.hide();
		}
	});
	
	
	var caseLink;
	if ((caseLink=$('a.caseView')).zoomimage) {
		
		caseLink.zoomimage({
			controlsTrigger: 'mouseover',
			className: 'custom',
			shadow: 40,
			centered:true,
			opacity: 1,
			beforeZoomIn: function(boxID) {
				$('#' + boxID)
					.find('img')
					.css('opacity', 0)
					.animate(
						{'opacity':1},
						{ duration: 500, queue: false }
					);
			},
			beforeZoomOut: function(boxID) {
				$('#' + boxID)
					.find('img')
					.css('opacity', 1)
					.animate(
						{'opacity':0},
						{ duration: 500, queue: false }
					);
			}
		});
	}
	
	
	
	
});