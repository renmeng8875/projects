jKit(function ($) {
	/**主函数
	 * 一个匿名函数,将在window.load后执行,$为jKit的引用,所有变量及函数都限定在局部作用域中
	 */
	 var startYear=1980,finishYear=2080;
	 $(".jCalender")
	 .find(".jYearSelect").empty()
	 .append(makeOptions(startYear,finishYear))
	 .addEvent("change",function () {
	 	goYear(this.value);
	}).end()
	.find(".jMonthSelect").empty()
	.append(makeOptions(1,12))
	.addEvent("change",function () {
		
	}).end()
	.find(".jPrevYear")
	.addEvent("click",function () {
		
	});
	 function makeOptions(start,finish) {
	 	var frag=document.createDocumentFragment();
	 	for (;start<=finish;) {
	 		frag.appendChild(jKit.extend(document.createElement("option"),{
	 			value:start,
	 			innerHTML:start++
	 		}));
	 	}
	 	return frag;
	}
	function getPickDate() {
		var jC=$(".jCalender");
		var year=jC.find(".jYearSelect")[0];
		year = year.value || year.options[year.selectedIndex];
		var month=jC.find(".jMonthSelect")[0];
		month=month.value || month.options[month.selectedIndex];
		var day = jC.find(".pickedDay")[0].title;
		var d = new Date();
		d.setFullYear(year);
		d.setMonth(month);
		d.setDate(day);
		return d;
	}
	function dateWalk(d,times) {
		var t = d.getTime();
		t+=times;
		return new Date(t);
	}
	function goYear(n) {
	}
	function goMonth(n) {
	}
	function val(selectObj) {
		return selectObj.value || selectObj.options[selectObj.selectedIndex];
	}
});