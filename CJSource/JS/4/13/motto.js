(function () {

function isVisible(o) {
	var offset=getOffset(o),de=document.documentElement;
	return offset.x<de.scrollLeft+de.clientWidth &&
					offset.y<de.scrollTop+de.clientHeight;
}

var shareStart,lock;

addEvent(window,"load",function () {
	insertData(1,25);
	var interval=setInterval(function () {
		if (lock) return;
		window.scrollTo(0,0);
		addEvent(window,"scroll",function () {
			if (lock) return false;
			var table=$("mottoTable");
			var index=table.rows.length-6;
			var row=table.rows[index];
			if (isVisible(row)) {
				insertData(shareStart,10);
			}
		});
		clearInterval(interval);
	},100);
	
});

function insertData(start,length) {
	lock=true;
	var table=$("mottoTable");
	Base.ajax({
		url:"proxy.php",
		data:{
			start:start,
			len:length
		},
		success:function (txt) {
			var data=eval("("+txt+")");
			for (var i=0,row,cell;i<data.length;i++) {
				row=table.insertRow(table.rows.length);
				cell=row.insertCell(row.cells.length);
				cell.innerHTML=data[i].man;
				cell=row.insertCell(row.cells.length);
				cell.innerHTML=data[i].text;
			}
			lock=false;
		}
	});
	shareStart=start+length;
}

})();