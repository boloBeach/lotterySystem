$(document)
		.ready(
				function() {

					var currentPrize = $("#currentPrize");
					var prizeData;
					var prizeCount = 0;
					$.ajax({
						url : "getPrizeInfoServlet",
						type : "post",
						cache : true,
						dataType : 'json',
						data : {},
						success : function(data) {
							console.debug(data);
							prizeData = data;
						}
					});

					$("#next").on("click",function() {
								if (prizeCount >= prizeData.length) {
									alert("No prize left");
									return;
								}
								var prize = prizeData[prizeCount];
								currentPrize.html("Current Prize:<a class=\"group1\" href=\"./images/"+prize.prizePicName+"\" >"+ prize.prizeName + "</a>");

								$("#arrayLength").val(prize.prizedPersonNum);
								$("#prizeType").val(prize.prizeLevel);
								$("#prizeName").val(prize.prizeName);

								prizeCount++;
							});
					
					$(".group1").colorbox({rel:'group1'});

					// --------------------
					var cacheData;
					var iCount;
					var array = new Array();
					var arrayLength;
					var ids = new Array();
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

					function showData() {
						iCount = setInterval(showHtml, 100);
						// showHtml();
					}
					function showHtml() {
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
							// alert(array[j]);
							var object = cacheData[array[j] - 1];
							if(j == array.length/2||j==0){
								$("<br/><br/><br/>").appendTo(
										contentUl);
							}
//							$("<li><div id=\""
//											+ object.id + "\">"
//											+ object.englishName + "-"
//											+ object.chineseName
//											+ "</div></li>").appendTo(
//									contentUl);
							$("<input class=\"btn btn-primary\" type=\"text\" id=\""+ object.id + "\" value=\""
											+ object.englishName + "-"
											+ object.chineseName
											+ "\" />&nbsp;&nbsp;&nbsp;").appendTo(contentUl);
							
						}
						array.splice(0, array.length);
					}

					function getData() {
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
							success : function(data) {
								cacheData = data;
								showData();
							}
						});
					}

					$("#start").on("click", function() {
						arrayLength = $("#arrayLength").val();

						var reg = new RegExp("^[0-9]*[1-9][0-9]*$");
						if (arrayLength.match(reg) == null) {
							alert("Please enter a integer above 1");
							$("#arrayLength").val("1");
							return;
						}
						if (arrayLength == null || arrayLength == "") {
							arrayLength == 1;
						}
						getData();

						$("#start").attr("disabled", "true");
						$("#stop").removeAttr("disabled");
					});

					$("#stop").on("click", function() {
						clearInterval(iCount);

						$("#start").removeAttr("disabled");
						$("#stop").attr("disabled", "true");

						var allSpans = $("span");
						for (var i = 0; i < allSpans.length; i++) {
							var span = allSpans[i];
							span = $(span);
							ids[i] = span.attr("id");
						}
						console.debug(ids);
						$.ajax({
							url : "getDataServlet",
							type : "post",
							cache : false,
							dataType : 'json',
							data : {
								"ids" : ids.toString(),
								prizeType : $("#prizeName").val(),
								prizeName : $("#prizeName").val()
							},
							success : function(data) {
								var size = data.length;
								if (size <= $("#arrayLength").val()) {
									$("#arrayLength").val(size);
								}
							}
						});
					});

				});

function showPrizeInfo(picName,personNum,prizeName){
	
}

