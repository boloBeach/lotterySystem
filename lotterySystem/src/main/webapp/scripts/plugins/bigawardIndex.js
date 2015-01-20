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
		});