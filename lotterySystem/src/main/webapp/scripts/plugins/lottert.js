$(document).ready(function() {
	
	var cacheData;
	var iCount;
	var array = new Array();
	var arrayLength;
	var ids= new Array();
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
	
	function showData(){
		iCount =setInterval(showHtml,100);
		// showHtml();
	}
	function showHtml(){
		contentUl.html("");
		for (var i = 0;; i++) {
			// 只生成10个随机数
			if (array.length < arrayLength) {
				getRandom(cacheData.length);
			} else {
				break;
			}
		}
		for (var j = 0; j < array.length; j++) {
			//alert(array[j]);
			var object = cacheData[array[j]-1];
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
				cacheData=data;
				showData();
			}
		});
	}
	
	
	$("#start").on("click", function() {
		arrayLength = $("#arrayLength").val();
		
		var reg = new RegExp("^[0-9]*[1-9][0-9]*$");
		if(arrayLength.match(reg)==null){
			alert("Please enter a integer above 1");
			$("#arrayLength").val("1");
			return;
		}
		if(arrayLength==null||arrayLength==""){
			arrayLength==1;
		}
		getData();
		
		$("#start").attr("disabled","true");
		$("#submit").attr("disabled","true");
		$("#stop").removeAttr("disabled");
	});
	
	
	$("#stop").on("click",function(){
		 clearInterval(iCount);
		 
		 $("#start").removeAttr("disabled");
		 $("#submit").removeAttr("disabled");
		 $("#stop").attr("disabled","true");
	});
	
	
	$("#submit").attr("disabled","true");
	$("#submit").on("click",function(){
		var allSpans = $("span");
		for (var i = 0; i < allSpans.length; i++) {
			var span =allSpans[i];
			span=$(span);
			ids[i] = span.text().split("/")[0];
		}
		console.debug(ids);
		$.ajax({
			url : "getDataServlet",
			type : "post",
			cache : false,
			dataType : 'json',
			data : {
				"ids":ids.toString(),
				prizeType:$("#prizeType").val()
			},
			success : function(data){
			}
		});
	});
	
	
});