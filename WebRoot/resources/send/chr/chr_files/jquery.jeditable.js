/*
 * Jeditable - jQuery in place edit plugin
 *
 */

/**
  * Version 1.7.1
  *
  * ** means there is basic unit tests for this parameter. 
  *
  * @name  Jeditable
  * @type  jQuery
  * @param String  target             (POST) URL or function to send edited content to **
  * @param Hash    options            additional options 
  * @param String  options[method]    method to use to send edited content (POST or PUT) **
  * @param String  options[name]      POST parameter name of edited content
  * @param String  options[id]        POST parameter name of edited div id
  * @param String  options[type]      text, textarea or select (or any 3rd party input type) **
  * @param Integer options[rows]      number of rows if using textarea ** 
  * @param Mixed   options[height]    'auto', 'none' or height in pixels **
  * @param Mixed   options[width]     'auto', 'none' or width in pixels **
  * @param Mixed   options[data]      Or content given as paramameter. String or function.**
  * @param String  options[event]     jQuery event such as 'click' of 'dblclick' **
  * @param String  options[submit]    submit button value, empty means no button **
  * @param String  options[cancel]    cancel button value, empty means no button **
  * @param String  options[style]     Style to apply to input form 'inherit' to copy from parent. **
  * @param String  options[select]    true or false, when true text is highlighted ??
  * @param String  options[onblur]    'cancel', 'submit', 'ignore' or function ??
  *             
  */

(function($) {

    $.fn.editable = function(options) {
            
        var settings = $.extend({}, $.fn.editable.defaults, {target:""}, options);
        
        /* setup some functions */
        var content  = $.editable.types[settings.type].content 
                    || $.editable.types['defaults'].content;
        var element  = $.editable.types[settings.type].element 
                    || $.editable.types['defaults'].element;
        var reset    = $.editable.types[settings.type].reset 
                    || $.editable.types['defaults'].reset;
        var onedit   = settings.onedit   || function() { }; 
          
        settings.autowidth  = 'auto' == settings.width;
        settings.autoheight = 'auto' == settings.height;
        
        return this.each(function() {
                        
            /* save this to self because this changes when scope changes */
            var self = this;  
                   
            /* inlined block elements lose their width and height after first edit */
            /* save them for later use as workaround */
            var savedwidth  = $(self).width();
            var savedheight = $(self).height();
            
            /* save so it can be later used by $.editable('destroy') */
            $(this).data('event.editable', settings.event);
            
            $(this).bind(settings.event, function(e) {
                
                /* abort if disabled for this element */
                if (true === $(this).data('disabled.editable')) {
                    return;
                }
                
                /* prevent throwing an exeption if edit field is clicked again */
                if (self.editing) {
                    return;
                }
                
                /* prevent default action and bubbling */
                e.preventDefault();
                e.stopPropagation();
                
                /* figure out how wide and tall we are, saved width and height */
                /* are workaround for http://dev.jquery.com/ticket/2190 */
                if (0 == $(self).width()) {
                    //$(self).css('visibility', 'hidden');
                    settings.width  = savedwidth;
                    settings.height = savedheight;
                } else {
                    if (settings.width != 'none') {
                        settings.width = 
                            settings.autowidth ? $(self).width()  : settings.width;
                    }
                    if (settings.height != 'none') {
                        settings.height = 
                            settings.autoheight ? $(self).height() : settings.height;
                    }
                }
                                
                self.editing    = true;
                self.revert     = $(self).html();
                $(self).html('');

                /* create the form object */
                var form = $('<form onsubmit="return false;"/>');

                /* add main input element to form and store it in input */
                var input = element.apply(form, [settings, self]);

                /* set input content via POST, GET, given data or existing value */
                var input_content = self.revert; 
                if(settings.type == 'autoDate'){
                	input_content=input_content.replace(/\D/g,'');
                }
                if (settings.type == 'select') {
                    content.apply(form, [$(this).attr("sysValue"), settings, self]);
                }else{
                	input_content=input_content.replace(/(^\s*)|(\s*$)/g, "");
                    content.apply(form, [input_content, settings, self]);
                }
                input.attr('name', settings.name);
         
                /* add created form to self */
                $(self).append(form);

                /* focus to first visible form element */
                $(':input:visible:enabled:first', form).focus();
                if($(this).attr("sysSpeParam")=='selectAll')
                	$(':input:visible:enabled:first', form).select();

                /* discard changes if pressing esc */
                input.keydown(function(e) {
                    if (e.keyCode == 27) {
                        if (settings.type == 'select') {
                            $(self).html(input.find("option:selected").text());
                        }if (settings.type == 'lookUp') {
                            input.blur();
                        }else{
                            $(self).html(input.val());
                        }
                        self.editing = false;
                    }
                });

                /* discard, submit or nothing with changes when clicking outside */
                /* do nothing is usable when navigating with tab */
                if ('cancel' == settings.onblur) {
                    input.blur(function(e) {
                        /* prevent canceling if submit was clicked */
                    	//if(settings.type != "date"){
	                        if (settings.type == 'select') {
	                            $(self).html(input.find("option:selected").text());
	                        }else{
	                            $(self).html(input.val());
	                        }
                    	//}
                        self.editing   = false;
                    });
                } else if ($.isFunction(settings.onblur)) {
                    input.blur(function(e) {
                        if (settings.type == 'select') {
                            settings.onblur.apply(self, [input.find("option:selected").text(), settings]);
                        }else{
                            settings.onblur.apply(self, [input.val(), settings]);
                        }
                    });
                }
                if ('cancel' == settings.onkeydown) {
                    input.keydown(function(e) {
                        /* prevent canceling if submit was clicked */
                    	//if(settings.type != "date"){
	                        if (settings.type == 'select') {
	                            $(self).html(input.find("option:selected").text());
	                        }else{
	                            $(self).html(input.val());
	                        }
                    	//}
                        self.editing   = false;
                    });
                } else if ($.isFunction(settings.onkeydown)) {
                    input.keydown(function(e) {
                    	if (e.keyCode == 13) {
	                        if (settings.type == 'select') {
	                            settings.onkeydown.apply(self, [input.find("option:selected").text(), settings]);
	                        }else{
	                            settings.onkeydown.apply(self, [input.val(), settings]);
	                        }
                    	}
                    });
                }
                if ('cancel' == settings.onchange) {
                    input.change(function(e) {
                        if (settings.type == 'select') {
                            $(self).html(input.find("option:selected").text());
                        }else{
                            $(self).html(input.val());
                        }
                        self.editing   = false;
                    });
                } else if ($.isFunction(settings.onchange)) {
                    input.change(function(e) {
                        if (settings.type == 'select') {
                            settings.onchange.apply(self, [input.find("option:selected").val(),input.find("option:selected").text(), settings]);
                        }else{
                            settings.onchange.apply(self, [input.val(), settings]);
                        }
                    });
                }
            });
        });
    };


    $.editable = {
        types: {
            defaults: {
                element : function(settings, original) {
                    var input = $('<input type="hidden"></input>');                
                    $(this).append(input);
                    return(input);
                },
                content : function(string, settings, original) {
                    $(':input:first', this).val(string);
                },
                reset : function(settings, original) {
                  original.reset(this);
                }
            },
            text: {
                element : function(settings, original) {
                    var input = $('<input type="text"  onkeydown="javascript:if(event.keyCode == 13)$(this).blur();"/>');
                    if (settings.width  != 'none') { input.width(settings.width);  }
                    input.attr('autocomplete','off');
                    $(this).append(input);
                    return(input);
                }
            },
            lookUp: {
                element : function(settings, original) {
        			var sysIndex = $(original).attr("sysIndex");
        			var input = $('<input type="text" onkeydown="javascript:if(event.keyCode == 13)$(this).blur();"/>');
        			if (settings.width  != 'none') { input.width(settings.width);  }
                    input.attr('autocomplete','off');
                    $(this).append(input);
                    return(input);
                }
            },
            date: {
                element : function(settings, original) {
            		var format = $(original).attr("format");
            		/*日期格式化*/
            		if( typeof(format) == "undefined" || format == ""){
            			format = "yyyyMMdd";
            		}
            		var input = $('<input type="text" class="Wdate" onkeydown="javascript:if(event.keyCode == 13)$(this).blur();" onClick="select();WdatePicker({dateFmt:\'' + format + '\',onpicked:jeditableDate});"/>');
                    if (settings.width  != 'none') { input.width(settings.width);  }
                    input.attr('autocomplete','off');
                    $(this).append(input);
                    return(input);
                }
            },
            /*考勤几个申请页面用的*/
            date1: {
                element : function(settings, original) {
            		var format = $(original).attr("format");
            		/*日期格式化*/
            		if( typeof(format) == "undefined" || format == ""){
            			format = "yyyyMMdd";
            		}
            		var input = $('<input type="text" class="Wdate required" readonly="true" onkeydown="javascript:if(event.keyCode == 13)$(this).blur();" onFocus="select();WdatePicker({dateFmt:\'' + format + '\',onpicked:jeditableDate});"/>');
                    if (settings.width  != 'none') { input.width(settings.width);  }
                    input.attr('autocomplete','off');
                    $(this).append(input);
                    return(input);
                }
            },
            autoDate: {
                element : function(settings, original) {
	    			var input = $('<input type="text" maxlength="10" onkeydown="javascript:if(event.keyCode == 13)$(this).blur();"/>');
	    			if (settings.width  != 'none') { input.width(settings.width);  }
	                input.attr('autocomplete','off');
	                $(this).append(input);
	                return(input);
                }
            },
            textarea: {
                element : function(settings, original) {
                    var textarea = $('<textarea  onkeydown="javascript:if(event.keyCode == 13)$(this).blur();"/>');
                    if (settings.rows) {
                        textarea.attr('rows', settings.rows);
                    } else if (settings.height != "none") {
                        textarea.height(settings.height);
                    }
                    if (settings.cols) {
                        textarea.attr('cols', settings.cols);
                    } else if (settings.width != "none") {
                        textarea.width(settings.width);
                    }
                    $(this).append(textarea);
                    return(textarea);
                }
            },
            select: {
               element : function(settings, original) {
                    var select = $('<select />');
                    $(this).append(select);
                    return(select);
                },
                content : function(data, settings, original) {
                    /* If it is string assume it is json. */
                    if (String == data.constructor) {      
                        eval ('var json = ' + data);
                    } else {
                    /* Otherwise assume it is a hash already. */
                        var json = data;
                    }
                    //添加一个空的
                    $('select', this).append($('<option />').val('').append(''));
                    for(var i=0,l=json.length;i<l;i++){
                        var option = $('<option />');
                    	for(var key in json[i]){
                            if ('CODE_NO' == key) {
                            	option.val(json[i][key]);
                            }else if('CODENAME' == key){
                            	option.append(json[i][key]);
                            }
                    	}
                        $('select', this).append(option); 
                    }
                    /* Loop option again to set selected. IE needed this... */ 
                    $('select', this).children().each(function() {
                        if ($(this).text() == $.trim(original.revert)) {
                        	$(this).attr('selected', 'selected');
                        }
                    });
                }
            }
        },

        /* Add new input type */
        addInputType: function(name, input) {
            $.editable.types[name] = input;
        }
    };

    // publicly accessible defaults
    $.fn.editable.defaults = {
        name       : 'value',
        id         : 'id',
        type       : 'text',
        width      : 'auto',
        height     : 'auto',
        event      : 'click.editable',
        onblur     : 'cancel',
        onComplete : ''
    };

})(jQuery);

function jeditableDate(){
	var objTag = $(this).parent().parent();
	var index = objTag.attr("sysIndex");
	objTag.html($(this).val());
    if($("#FROM_DATE_"+index,navTab.getCurrentPanel()).attr("sysFlag")==1){
    	callength(index);
    }
    if($("#APPLY_OT_DATE_"+index,navTab.getCurrentPanel()).attr("sysFlag")==2){
    	otAffirm_callength(index,1);
    }
    if($("#AD_APPLY_OT_DATE_"+index,navTab.getCurrentPanel()).attr("sysFlag")==2){
    	ad_callength(index,1);
    }
    if(objTag.attr("sysFlag")=="contract"){
		calContractLength(index);
    }
}