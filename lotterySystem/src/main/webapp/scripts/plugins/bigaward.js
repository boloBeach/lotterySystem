$(document).ready(function(){
	//定义setTimeout执行方法
	var TimeFn = null;
	var theadTime = null;
	var longitude = null; // 经度
	var latitude = null;// 这个是纬度
	var tbody = $("#tbody");
	tbody.find("tr").children("th").each(function(e){ // 行(经度)
		var thNode = $(this);
		 // 取消上次延时未执行的方法
	    clearTimeout(TimeFn);
	    //执行延时
    	thNode.on("dblclick",function(){
    		clearTimeout(TimeFn);
			if(longitude==null){
				longitude = e;
				thNode.nextAll().css("background-color", "#bbf");
			}
			
			if(latitude != null){ // 说明纬度已经被选择了,并且执行ajax
				tbody.find("tr:gt(0):eq("+e+") td:eq("+latitude+")").css("background-color", "red");
				$("#test").html("坐标为="+longitude+":"+latitude);
			}
		});
		
		thNode.on("click",function(){
			// 取消上次延时未执行的方法
		    clearTimeout(TimeFn);
		    //执行延时
		    TimeFn = setTimeout(function(){
		    	longitude = null;
				thNode.nextAll().css("background-color", "#DEF3CA");
		    },300);
		});
	});
	
	$("#trThead").find("th").each(function(e){ // 列(纬度)
		var theadNode = $(this);
		var index = e+1;
		theadNode.on("click",function(){
			// 取消上次延时未执行的方法
		    clearTimeout(theadTime);
		    //执行延时
		    theadTime = setTimeout(function(){
		    	latitude = null;
				$("#tbody tr td:nth-child("+index+")").css("background-color","#DEF3CA");
		    },300);
		});
		
		theadNode.on("dblclick",function(){
			clearTimeout(theadTime); // 清楚事件
			if(longitude != null){
				alert(e);
				tbody.find("tr:gt(0):eq("+longitude+") td:eq("+e+")").css("background-color", "red");
				$("#test").html("thead:坐标为="+longitude+"--"+latitude);
			}
			if(latitude == null){
				latitude = e;
				$("#tbody tr td:nth-child("+index+")").css("background-color","#9BAF83");
			}
			
		});
		
		
	});
});