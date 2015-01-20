$(document).ready(function(){
	//定义setTimeout执行方法
	var TimeFn = null;
	var theadTime = null;
	var longitude = null; // 经度
	var latitude = null;// 这个是纬度
	var tbody = $("#tbody");
	var showAward =  $(".show-award");
	showAward.find("div").on("click",function(){
		showAward.hide();
	});
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
				var bigawardNode = tbody.find("tr:eq("+e+") td:eq("+(latitude-1)+")");
				var awardedId = bigawardNode.html().split("<br>")[0];
				var ids = new Array();
				ids[0]=awardedId;
				$.ajax({
					url : "getDataServlet",
					type : "get",
					cache : false,
					dataType : 'json',
					data : {
						"ids" : ids.toString(),
						prizeName : "bigAward"
					},
					success : function(data) {
					}
				});
				
				bigawardNode.css("background-color", "red");
				showAward.show();
				showAward.find("div").css("background-color","#DEF3CA").html(bigawardNode.html()).animate({
					width: "90%",
					height: "90%",
					opacity: 1
				},2000);
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
		theadNode.on("click",function(){
			// 取消上次延时未执行的方法
		    clearTimeout(theadTime);
		    //执行延时
		    theadTime = setTimeout(function(){
		    	latitude = null;
		    	var index = e+1;
				$("#tbody tr td:nth-child("+index+")").css("background-color","#DEF3CA");
				
		    },300);
		});
		
		theadNode.on("dblclick",function(){
			clearTimeout(theadTime); // 清楚事件
			if(latitude == null){
				latitude = e;
				var index = e+1;
				
				$("#tbody tr td:nth-child("+index+")").css("background-color","#9BAF83");
			}
			
			if(longitude != null){ // this is write ajax
				var index = e-1;
				var bigawardNode =tbody.find("tr:eq("+longitude+") td:eq("+index+")") ;
				
				var awardedId = bigawardNode.html().split("<br>")[0];
				var ids = new Array();
				ids[0]=awardedId;
				alert(ids);
				$.ajax({
					url : "getDataServlet",
					type : "get",
					cache : false,
					dataType : 'json',
					data : {
						"ids" : ids.toString(),
						prizeName : "bigAward"
					},
					success : function(data) {
					}
				});
				
				bigawardNode.css("background-color", "red");
				showAward.show();
				showAward.find("div").css("background-color","#DEF3CA").html(bigawardNode.html()).animate({
					width: "90%",
					height: "90%",
					opacity: 1
				},2000);
			}
		});
		
		
	});
});