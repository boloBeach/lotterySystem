$(document).ready(function() {
	var iCount;
	var array = new Array();
	// get random number
	function getRandom(n) {
		var rand = Math.floor(Math.random() * n + 1);
		for (var i = 0; i < array.length; i++) {
			if (array[i] == rand) {
				return false;
			}
		}
		array.push(rand);
	}
	var contentUl = $(".content ul");
	
	function showData(data){
		setInterval(showHtml(data),300);
	}
	function showHtml(data){
		contentUl.html("");
		for (var i = 0;; i++) {
			// 只生成10个随机数
			if (array.length < 10) {
				getRandom(data.length);
			} else {
				break;
			}
		}
		for (var j = 0; j < array.length; j++) {
			//alert(array[j]);
			var object = data[array[j]-1];
			$("<li><div><img alt=\""+object.englishName+"-"+object.chineseName+"\" title=\""+object.englishName+"-"+object.chineseName+"\" src=\""+object.userImg+"\"><span>"+object.id+"/"+object.englishName+"-"+object.chineseName+"</span></div></li>").appendTo(contentUl);
		}
		array.splice(0,array.length);  
	}
	
	function getData(){
		var startString = "start";
		$.ajax({
			url : "usersServlet",
			type : "get",
			cache : false,
			dataType : 'json',
			data : {
				sendTime : (new Date()).getTime(),
				start : startString
			},
			success : function(data){
				showData(data);
			}
		});
	}
	$("#start").on("click", function() {
		iCount = setInterval(getData, 100);
	});
	
	$("#stop").on("click",function(){
		 clearInterval(iCount);
	});
});