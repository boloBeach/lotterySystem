$(document)
		.ready(
				function() {

					var currentPrize = $("#currentPrize");
					var prizeData;
					var prizeCount = 0;

					var currentPrizeTotal = -1;
					var currentPrizeLeft = -1;
					var currentRound = 0;
					var currentPrizeName = null;

					$.ajax({
						url : "getPrizeInfoServlet",
						type : "post",
						cache : false,
						dataType : 'json',
						data : {},
						success : function(data) {
							alert(data.length);
							prizeData = data;
						}
					});

					$("#next").on("click",function() {
									    $("#start").removeAttr("disabled");
									    $("#start").css({"width": "100px","height": "60px","padding":"0 20px","margin-right": "30px","font-size": "large"});
										$("#next").attr("disabled", true);
										if (prizeCount >= prizeData.length) {
											alert("No prize left");
											return;
										}
										var prize = prizeData[prizeCount];
										if (currentPrizeTotal == -1
												|| currentPrizeLeft == 0) {
											currentPrizeTotal = prize.prizeTotal;
											currentPrizeLeft = currentPrizeTotal;
											currentRound = prize.round;
											currentPrizeName = prize.prizeName;
										}
										

										currentPrize.html("Current Prize:<a class=\"group1\" href=\"./images/"
														+ prize.prizePicName
														+ "\" >"
														+ prize.prizeName
														+ "</a>");
										console.debug("prizeCount: "+prizeCount+", prize.prizedPersonNum: "+prize.prizedPersonNum);
										$("#arrayLength").val(prize.prizedPersonNum);
										$("#round").val(prize.round);
										$("#prizeName").val(prize.prizeName);

										prizeCount++;
									});

					// --------------------
					var cacheData;
					var iCount;
					var array = new Array();
					var arrayLength;
					
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
					var contentUl = $(".content");

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
					
							// $("<li><div id=\""
							// + object.id + "\">"
							// + object.englishName + "-"
							// + object.chineseName
							// + "</div></li>").appendTo(
							// contentUl);
							$("<div class=\"person\" id=\"" + object.id
											+ "\" >" + object.chineseName
											+ " <br/>" + object.englishName
											+ "<br/>"+object.lastName+" <br/></div>")
									.appendTo(contentUl);
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
					
					
					$("#start").attr("disabled", "true");
					$("#start").on("click", function() {
						if(prizeCount==27){
							alert("No prize left!");
							return;
						}
						arrayLength = $("#arrayLength").val();

						var reg = new RegExp("^[0-9]*[1-9][0-9]*$");
						if (arrayLength == null || arrayLength == "") {
							arrayLength == 1;
						}
						getData();

						$("#start").css({"width": "64px","height": "34px","padding":"0 1px","margin-right": "30px","font-size": "small"});
						$("#start").attr("disabled", "true");
						$("#stop").removeAttr("disabled");
						$("#stop").css({"width": "100px","height": "60px","padding":"0 20px","margin-left": "30px","font-size": "large"});
					});
					
					$("#stop").attr("disabled", "true");
					$("#stop").on("click",function() {
										clearInterval(iCount);
										var allDivs = contentUl.find("div");
										var ids = new Array();
										for (var i = 0; i < allDivs.length; i++) {
											var div = allDivs[i];
											div = $(div);
											ids[i] = div.attr("id");
										}
										console.debug(ids);
										$.ajax({
													url : "getDataServlet",
													type : "post",
													cache : false,
													dataType : 'json',
													data : {
														"ids" : ids.toString(),
														round : $("#round").val(),
														prizeNo :prizeData[prizeCount-1].prizeNo,
														prizeName : $("#prizeName").val()
													},
													success : function(data) {
														$("#start").removeAttr("disabled");
														$("#start").css({"width": "100px","height": "60px","padding":"0 20px","margin-right": "30px","font-size": "large"});
														
														$("#stop").css({"width": "64px","height": "34px","padding":"0 1px","margin-left": "30px","font-size": "small"});
														$("#stop").attr("disabled", "true");
														

														var prize = prizeData[prizeCount - 1];

														currentPrizeLeft = currentPrizeLeft - prize.prizedPersonNum;
														prize.prizedPersonNum = 0;
														
														var nextPrize = prizeData[prizeCount];
														$("#arrayLength").val(nextPrize.prizedPersonNum);
														var size = data.length;
														if (size <= nextPrize.prizedPersonNum) {
															$("#arrayLength").val(size);
														}
														
														if (nextPrize.prizeName != prize.prizeName) {
															$("#next").removeAttr("disabled");
															$("#start").css({"width": "64px","height": "34px","padding":"0 1px","margin-right": "30px","font-size": "small"});
															$("#start").attr("disabled",true);
														} else {
															$("#next").removeAttr("disabled");
															$("#next").click();
															$("#next").attr("disabled",true);
														}
													}
												});
									});

				});

function showPrizeInfo(picName, personNum, prizeName) {

}
