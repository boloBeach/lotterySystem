$(document).ready(function(){
	//定义setTimeout执行方法
	var TimeFn = null;
	var theadTime = null;
	var longitude = null; // 经度
	var latitude = null;// 这个是纬度
	var tbody = $("#tbody");
	var alarm = $("#alarm");
	var showAward =  $(".show-award");
	showAward.find("div").on("click",function(){
		showAward.hide();
	});
	
	if(alarm.val()=="true"){
		alert("此等奖项已经抽取完毕,请勿重复抽取!");
		//return;
	}
	
	
	tbody.find("tr").children("th").each(function(e){ // 行(经度)
		var thNode = $(this);
		 // 取消上次延时未执行的方法
	    clearTimeout(TimeFn);
	    //执行延时
    	thNode.on("dblclick",function(){
    		if(alarm.val()=="true"){
    			alert("此等奖项已经抽取完毕,请勿重复抽取!");
    			return;
    		}
    		clearTimeout(TimeFn);
			if(longitude==null){
				longitude = e;
				thNode.nextAll().css("background-color", "#bbf");
			}
			
			if(latitude != null){ // 说明纬度已经被选择了,并且执行ajax
				var bigawardNode = tbody.find("tr:eq("+e+") td:eq("+(latitude-1)+")");
				var awardedId = bigawardNode.html().split("<br>")[0];
				var usernameE = bigawardNode.html().split("<br>")[1];
				var usernameC = bigawardNode.html().split("<br>")[2];
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
				$("#userId").html(awardedId);
				$("#usernameE").html(usernameE);
				$("#usernameC").html(usernameC);
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
			if(alarm.val()=="true"){
				alert("此等奖项已经抽取完毕,请勿重复抽取!");
				return;
			}
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
				var usernameE = bigawardNode.html().split("<br>")[1];
				var usernameC = bigawardNode.html().split("<br>")[2];
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
				$("#userId").html(awardedId);
				$("#usernameE").html(usernameE);
				$("#usernameC").html(usernameC);
				/*showAward.find("div").css("background-color","#DEF3CA").html("<p>Congratulation!</p> "+bigawardNode.html()).animate({
					width: "70%",
					height: "80%",
					opacity: 1
				},2000);*/
			}
		});
		
		
	});
});