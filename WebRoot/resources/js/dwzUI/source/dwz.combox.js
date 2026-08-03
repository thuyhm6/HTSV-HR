/**
 * @author Roger Wu
 */

(function($){
	var allSelectBox = [];
	var killAllBox = function(bid){
		$.each(allSelectBox, function(i){
			if (allSelectBox[i] != bid) {
				if (!$("#" + allSelectBox[i])[0]) {
					$("#op_" + allSelectBox[i]).remove();
					//allSelectBox.splice(i,1);
				} else {
					$("#op_" + allSelectBox[i]).css({ height: "", width: "" }).hide();
				}
				$(document).unbind("click", killAllBox);
			}
		});
	};
	
	$.extend($.fn, {
		comboxSelect: function(options){
			var op = $.extend({ selector: ">a" }, options);
			
			var box = $(this);
			var selector = $(op.selector, box);

			allSelectBox.push(box.attr("id"));
			$(op.selector, box).click(function(){
				var options = $("#op_"+box.attr("id"));
				if (options.is(":hidden")) {
					if(options.height() > 300) {
						options.css({height:"300px",overflow:"scroll"});
					}
					var top = box.offset().top+box[0].offsetHeight-50;
					if(top + options.height() > $(window).height() - 20) {
						top =  $(window).height() - 20 - options.height();
					}
					options.css({top:top,left:box.offset().left}).show();
					killAllBox(box.attr("id"));
					$(document).click(killAllBox);
				} else {
					$(document).unbind("click", killAllBox);
					killAllBox();
				}
				return false;
			});
			$("#op_"+box.attr("id")).find(">li").comboxOption(selector, box);		
			return this ;
		},
		comboxOption: function(selector, box){
			$(">a", this).click(function(){
				
				var $this = $(this);
				$this.parent().parent().find(".selected").removeClass("selected");
				$this.addClass("selected");
				selector.text($this.text());
				
				var $input = $("select", box);
				if ($input.val() != $this.attr("value")) {
					$("select", box).val($this.attr("value")).trigger("refChange").trigger("change");
				}
			});
			return this ;
		},
		combox:function(){
			/* 清理下拉层 */
			var _selectBox = [];
			$.each(allSelectBox, function(i){ 
				if ($("#" + allSelectBox[i])[0]) {
					_selectBox.push(allSelectBox[i]);
				} else {
					$("#op_" + allSelectBox[i]).remove();
				}
			});
			allSelectBox = _selectBox;
			
			return this.each(function(i){
				var $this = $(this).removeClass("combox");
				var name = $this.attr("name");
				var value= $this.attr("value");
				/* 原码 var label = $("option[value=" + value + "]",$this).text();
				 * value='0.5' 时出错
				 *  */
				var label = $("option[value='" + value + "']",$this).text();
				var ref = $this.attr("ref");
				var refUrl = $this.attr("refUrl") || "";
				
				var ref2 = $this.attr("ref2");
				var refUrl2 = $this.attr("refUrl2") || "";
				
				var cid = $this.attr("id") ;
				if (cid){
					cid = $this.attr("id") + "_" + Math.round(Math.random()*10000000);
				}
				else{
					cid = Math.round(Math.random()*10000000);
				}
				
				$this.attr("cid" ,cid) ;
				
				var select = '<div class="combox"><div id="combox_'+ cid +'" class="select"' + (ref?' ref="' + ref + '"' : '') + (ref2?' ref2="' + ref2 + '"' : '') + '>';
				select += '<a href="javascript:" class="'+$this.attr("class")+'" name="' + name +'" value="' + value + '">' + label +'</a></div></div>';
				var options = '<ul class="comboxop" id="op_combox_'+ cid +'">';
				$("option", $this).each(function(){
					var option = $(this);
					options +="<li><a class=\""+ (value==option[0].value?"selected":"") +"\" href=\"#\" value=\"" + option[0].value + "\">" + option[0].text + "</a></li>";
				});
				options +="</ul>";
				
				$("body").append(options);
				$this.after(select);
				$("div.select", $this.next()).comboxSelect().append($this);
				
				if (ref && refUrl) {
					$this.unbind("refChange").bind("refChange", function(event){
						
						var $parent = $this.parent() ;
						
						while($parent.find("#"+ref).size() == 0){
							$parent = $parent.parent() ;
						}
						
						var $ref = $parent.find("#"+ref) ;
						
						var $refIdObj = $parent.find("#"+$this.attr("refId")) ;
						if($refIdObj.size() > 0){
							refUrl = refUrl + "&" + $refIdObj.attr("name") + "=" + $refIdObj.attr("value") ;
						}	
						
						if ($ref.size() == 0) return false;
						$.ajax({
							type:'GET', dataType:"json", url:refUrl.replace("{value}", $this.attr("value")), cache: false,
							data:{},
							success: function(jsonObjects){

								if (!jsonObjects) return;
								var html = '';
								
								$.each(jsonObjects, function(index, json){
								    var i = 1 ;
									$.each(json, function(key, value){
										
									    if (i == 1 && json[key] && json[key].length > 0){
									    	html += '<option value="'+ value +'">' ;
									    }
										if (i == 2 && json[key] && json[key].length > 0){
											html += value + '</option>';
										}
										i ++ ;
									});
								});
								
								var $refCombox = $ref.parents("div.combox:first");
								$ref.html(html).insertAfter($refCombox);
								$refCombox.remove();
								$ref.trigger("refChange").trigger("change").combox();
							},
							error: DWZ.ajaxError
						});
						
						
						if (ref2 && refUrl2) {
							var $parent2 = $this.parent() ;
							
							while($parent2.find("#"+ref2).size() == 0){
								$parent2 = $parent2.parent() ;
							}
							
							var $ref2 = $parent2.find("#"+ref2) ;
							
							if ($ref2.size() == 0) return false;
							$.ajax({
								type:'GET', dataType:"json", url:refUrl2.replace("{value}", $this.attr("value")), cache: false,
								data:{},
								success: function(jsonObjects){

									if (!jsonObjects) return;
									var html = '';
									
									$.each(jsonObjects, function(index, json){
									    var i = 1 ;
										$.each(json, function(key, value){
											
										    if (i == 1 && json[key] && json[key].length > 0){
										    	html += '<option value="'+ value +'">' ;
										    }
											if (i == 2 && json[key] && json[key].length > 0){
												html += value + '</option>';
											}
											i ++ ;
										});
									});
									
									var $refCombox = $ref2.parents("div.combox:first");
									$ref2.html(html).insertAfter($refCombox);
									$refCombox.remove();
									$ref2.trigger("refChange").trigger("change").combox();
								},
								error: DWZ.ajaxError
							});
						}
						
						
					});
				}
				
			});
		}
	});
})(jQuery);
