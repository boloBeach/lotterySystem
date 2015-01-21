$(document).ready(function(){
				//slidedown Effect
				$('.slidedown').hover(function(){
					$('.search').show();
					$(this).children().animate({top:'60'},{queue:false,duration:500});
					$(this).find("h3").css("line-height","45px");
				},function(){
					$(this).children().animate({top:'0'},{queue:false,duration:500});
					$(this).find("h3").css("line-height","150px");
					$('.search').hide();
					}
				);
				
				$('.peek').hover(function(){
					$('.groups').show();
					$(this).children().animate({top:'60'},{queue:false,duration:500});
					$(this).find("h3").css("line-height","45px");
				},function(){
					$(this).children().animate({top:'0'},{queue:false,duration:500});
					$(this).find("h3").css("line-height","150px");
					$('.groups').hide();
					}
				);
				//slideleft Effect
				$('.slideleft').hover(function(){
					$(this).children().animate({left:'-150'},{queue:false,duration:160});
					$(this).find("div:eq(1) h3").css("font-size","25px").css("text-align","right");
					$(this).find("p").css("font-size","10pt").css("text-align","right");
					$('.news').show();
				},function(){
					$(this).children().animate({left:'0'},{queue:false,duration:160});
					$(this).find("div:eq(1) h3").css("font-size","32px").css("text-align","center");
					$(this).find("p").css("font-size","18pt").css("text-align","center");
					$('.news').hide();
					}
				);
				//slideright Effect
				$('.slideright').hover(function(){
					$(this).children().animate({left:'140'},{queue:false,duration:160});
					$(this).find("h3").css("font-size","25px").css("text-align","left");
					$(this).find("p").css("font-size","10pt").css("text-align","left");
					$('.news2').show();
				},function(){
					$(this).children().animate({left:'0'},{queue:false,duration:160});
					$(this).find("h3").css("font-size","32px").css("text-align","center");
					$(this).find("p").css("font-size","18pt").css("text-align","center");
					$('.news2').hide();
					}
				);	
				
				
				/***************get the user data***************/
				var userDatas;//use to cache data
				var dataLength = 0;
				var groupA;
				var groupB;
				var groupC;
				var groupD;
				var groupE;
				$.ajax({
					url : "usersServlet",
					type : "get",
					cache : false,
					dataType : 'json',
					data : {
						start:"start"
					},
					success : function(data) {
						//alert(data.length);
						userDatas = data;
						dataLength = userDatas.length;
						//group A
						groupA = userDatas.slice(0,50);
						var textA = "<ul>";
						for (var i = 0; i < 5; i++) {
							var user = groupA[i];
							textA +=("<li>"+user.id+" &nbsp;&nbsp;&nbsp;  "+user.englishName+"  &nbsp;&nbsp;&nbsp;"+user.chineseName+"</li>");
						}
						textA+="</ul>";
						$("#pa").text(groupA[0].id+"~~"+groupA[groupA.length-1].id);
						$(".search").html(textA);
						//Group B
						groupB = userDatas.slice(50,100);
						var textB = "<ul>";
						for (var i = 0; i < 10; i++) {
							var user = groupB[i];
							textB +=("<li>"+user.id+" &nbsp;  "+user.englishName+"  &nbsp;"+user.chineseName+"</li>");
						}
						textB +="</ul>";
						$("#pb").text(groupB[0].id+"~~"+groupB[groupB.length-1].id);
						$(".news").html(textB);
						//Group C
						groupC = userDatas.slice(100,150);
						var textC = "<ul>";
						for (var i = 0; i < 12; i++) {
							var user = groupC[i];
							textC +=("<li>"+user.id+" &nbsp;&nbsp;&nbsp;  "+user.englishName+"  &nbsp;&nbsp;&nbsp;"+user.chineseName+"</li>");
						}
						textC +="</ul>";
						$("#pc").text(groupC[0].id+"~~"+groupC[groupC.length-1].id);
						$(".group-e-content").html(textC);
						//Group D
						groupD = userDatas.slice(150,200);
						var textD = "<ul>";
						for (var i = 0; i < 10; i++) {
							var user = groupD[i];
							textD +=("<li>"+user.id+" &nbsp;  "+user.englishName+"  &nbsp;"+user.chineseName+"</li>");
						}
						textD +="</ul>";
						$("#pd").text(groupD[0].id+"~~"+groupD[groupD.length-1].id);
						$(".news2").html(textD);
						//Group E
						groupE = userDatas.slice(200,userDatas.length);
						var textE = "<ul>";
						for (var i = 0; i < 5; i++) {
							var user = groupE[i];
							textE +=("<li>"+user.id+" &nbsp;&nbsp;&nbsp;  "+user.englishName+"  &nbsp;&nbsp;&nbsp;"+user.chineseName+"</li>");
						}
						textE +="</ul>";
						$("#pe").text(groupE[0].id+"~~"+groupE[groupE.length-1].id);
						$(".groups").html(textE);
					}
				});
				
			
				
		});