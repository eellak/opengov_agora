
(function($) {  
    var self = null;

    $.fn.autoComplete = function(o) {
    	// MG: added description and few new options
    	// MG: maxResults was in your examples, but not in the options, is added now
    	// MG: maxHeight was in the option list, but not implemented, is now implemented
    	// MG: offsetY is now default 0, see end of createList function (outerHeight() part)
    	// MG: all options are now in camelCase for consistency
    	o = jQuery.extend({
			valueSep:';', 				// separator for different values
		    minChars:1, 				// minimum chars typed by user before suggestion list is shown
		    meth:"get", 				// AJAX method (get/post)
		    varName:"input", 			// variable name in the query string of the url
		    className:"autocomplete", 	// classname of the list
		    timeout:3000, 				// idle time before hiding the list, set to 0 to disable the timeout
		    delay:500, 					// delay before showing the list
		    offsetY:0, 					// y offset of the list
		    showNoResults: true, 		// show a message when no results were found
		    noResults: "No results were found.", // the message when no results were found
		    showMoreResults: false, 	// show a message when more results were found than the maximum
		    moreResults: "More results were found", // the message when more results were found than the maximum
		    cache: true, 				// cache the received data
		    maxEntries: 25, 			// maximum number of suggestions cached
		    maxResults: 25, 			// maximum number of suggestions shown in the list
		    onAjaxError:null, 			// function to execute when an AJAX error occurred
		    maxHeight: 0, 				// maximum height of the list, set to 0 to disable maximum height
		    setWidth: false, 			// set to true to define a max/min width
		    minWidth: 100, 				// the minimum width of the list when setWidth is true
		    maxWidth: 200, 				// the maximum width of the list when setWidth is true
		    useNotifier: true, 			// use an icon notifier (spinner) between AJAX calls?
		    showAnimProperties: {height:'100%'}, // show animation options, see http://api.jquery.com/animate/ 
		    									 // note: width and height settings will be animated when set, but will be overruled with calculated settings
		    showAnimSpeed: 'fast', 		// the duration of the show animation, see http://api.jquery.com/animate/ 
		    hideAnimProperties: {opacity:'0'}, // hide animation options, see http://api.jquery.com/animate/
		    hideAnimSpeed: 'fast'		// hide animation duration, see http://api.jquery.com/animate/
		}, o);  
        return $(this).each(function() {
           new $.autoComplete(this, o);
         
        });
    };

    $.autoComplete = function (e, o) {
        this.field = $(e);
        this.options = jQuery.extend(o,this.options);
        this.KEY_UP 	  = 38;
        this.KEY_DOWN 	  = 40;
        this.KEY_ESC 	  = 27;
        this.KEY_RETURN   = 13;
        this.KEY_TAB 	  = 9;
        // MG: added a few more keys
        this.KEY_HOME     = 36;
        this.KEY_END      = 35;
        this.KEY_PAGEUP   = 33;
        this.KEY_PAGEDOWN = 34;
    
        $.extend(this,{
        init : function() {
            var self = this;
            
		    this.sInp 	= ""; 	// input value 
		    this.nInpC 	= 0;	// input value length
		    this.aSug 	= []; 	// suggestions array 
		    this.iHigh 	= 0;	// level of list selection
		    // MG: for suppressing show animation when list is rebuilt
		    this.wasVisible = true;
		    // MG: for cross-browser keydown/keypress/auto-repeat
		    this.lastKey = 0;
		    // MG: for cross-browser keydown/keypress/auto-repeat
		    this.lastEvent = '';
		    
		    if(this.options.useNotifier) this.field.addClass('ac_field');
		    
		    // MG: both keydown and keypress are now handled by handleKeyEvent, for cross-browser keydown/keypress/auto-repeat
		    this.field.keydown(function(e){return self.handleKeyEvent(e);});
		    this.field.keypress(function(e){return self.handleKeyEvent(e);});
		    
			this.field.keyup(function(e){
				if(!e) e = window.event;
				var key = e.keyCode || e.which;
				
				// MG: clear the last key and key event
				self.lastKey = 0;
				self.lastEvent = '';
				
				// MG: suppress all the special keys, up/down is now handled on keypress/keydown
				if(key != self.KEY_RETURN && key != self.KEY_TAB && key != self.KEY_ESC && key != self.KEY_UP && key != self.KEY_DOWN && key != self.KEY_PAGEDOWN && key != self.KEY_PAGEUP) {
					self.getSuggestions(self.field.val());
				}
				
				return true;

			});
			
			this.field.blur(function(){ self.resetTimeout(); return true;});
			this.field.attr('Autocomplete','off');
        },
        handleKeyEvent : function(e) {
			if(!e) e = window.event;
			var key = e.keyCode || e.which;
			var returnVal = true;
			var self = this;
			var changeCount = 1;
			
			if($('#' + self.acID).length) {
				// MG: prevent hijacking of too many keys from the browser, let the browser handle the key if alt/ctrl/shift is pressed
				if(!e.altKey && !e.ctrlKey && !e.shiftKey) {
					switch(key)
					{
						case self.KEY_RETURN:
							self.setHighlightedValue();
							e.stopPropagation();
							// MG: suppress browser default event handling of return key
							returnVal = false;
							break;
						case self.KEY_TAB: 
							self.setHighlightedValue();
							// MG: suppress browser default event handling of tab key
							returnVal = false;
							break;
						case self.KEY_ESC: 
							self.clearSuggestions();
							// MG: suppress browser default event handling of esc key
							e.stopPropagation();
							returnVal = false;
							break;
						// MG: added home/end key to go to the start/end of the list
						case self.KEY_HOME:
						case self.KEY_END:
							self.changeHighlight(key, $('#ac_ul').children().length);
							// MG: suppress browser default event handling of home/end key
							e.stopPropagation();
							returnVal = false;
							break;
						// MG: added page down/page up keys for browsing through the list
						case self.KEY_PAGEUP:
						case self.KEY_PAGEDOWN:
							if(this.options.maxHeight > 0) {
								// when using maxHeight, scroll up/down the number of elements which is (at least half) visible in the list
								changeCount = ($("#ac_ul").height() - ($(".ac_highlight").outerHeight() / 2)) / $(".ac_highlight").outerHeight();
								// round the value downwards to the first integer
								changeCount = Math.floor(changeCount - 0.5);
							} else {
								// when not using maxHeight, scroll up/down 10 elements
								changeCount = 10;
							}
							// fall through
						case self.KEY_UP:
						case self.KEY_DOWN:
							// MG: Make key up/down auto-repeat work correctly in all browsers; only execute a keypress event if it wasn't preceded by a keydown, see http://unixpapa.com/js/key.html 
							if(e.type == 'keydown' || (self.lastEvent != 'keydown' && self.lastKey == key) || self.lastKey != key) {
								if($('#'+self.acID).length) {
									self.changeHighlight(key, changeCount);
								}
							}
							// MG: suppress browser default event handling of the key
							returnVal = false;
							break;
					}
				}
			}
			
			// MG: track the last key and key event
			self.lastKey = key;
			self.lastEvent = e.type;
			
			// MG: false is returned to suppress browser default event handling
			return returnVal;
        },
        getSuggestions : function(val){
        	var lastInput = this.getLastInput(val);
        	var self = this;
        	
        	if(val==this.sInp) return false;
        	
        	// MG: remember if element was visible (used later when rebuilding the list)
        	this.wasVisible = $('#'+this.acID).length;
        	
        	// MG: removed if($('#'+this.acID)) because it will always succeed (and remove will check if the element exist anyway) (updated this on some more places in the code)
        	$('#'+this.acID).remove();
        	
        	this.sInp = val;
        	
        	// MG: the last input has to be checked for the minumum length, not the total input (otherwise second input would show the list too with no input)
        	if(lastInput.length < this.options.minChars)
        	{
        		this.aSug = [];
        		this.nInpC = val.length;
        		return false;	
        	}
        	var ol = this.nInpC; 
        	this.nInpC = val.length ? val.length : 0;
        	var l = this.aSug.length;
        	if(this.options.cache && (this.nInpC > ol) && l && (l < this.options.maxEntries))
        	{
        		var arr = new Array();
        		var oldval = '';
        		$(this.aSug).each(function(i){
        				if(oldval == this.value) return true;
        				oldval = this.value;
        				// MG: again, the last input has to be used instead of the total input (otherwise second input would show no result when the results are cached)
        				if(this.value.toLowerCase().indexOf(lastInput.toLowerCase()) != -1)
        					arr.push(this);
        			});	
        		this.aSug = arr;
        		this.createList(this.aSug);
        	}
        	else 
        	{
        		clearTimeout(this.ajID);
        		this.ajID = setTimeout( function(){self.doAjaxRequest(self.sInp);},this.options.delay);
        	}
        	document.helper = this;
        	return false;
        },
        getLastInput : function(str){
        	var ret = str;
        	if(undefined != this.options.valueSep) {
        		var idx = ret.lastIndexOf(this.options.valueSep);
        		ret = idx == -1 ? ret : ret.substring(idx + 1, ret.length);	
        	}
        	return ret;
        },
        doAjaxRequest: function(input) {
        	if($.trim(input) == '') return false;
        	
        	var self = this;
        	
        	if( input != this.field.val())
				return false;
			
			this.sInp = this.getLastInput(this.sInp);
			// MG: also trim the last input and check if it's empty
			if($.trim(this.sInp) == '') return false;
			
			if(typeof this.options.script == 'undefined') 
				throw('You have to specify a server script to make ajax calls!');
			else if(typeof this.options.script == 'function')
				var url = this.options.script(encodeURIComponent(this.sInp));
			else
				var url = this.options.script+this.options.varName+'='+encodeURIComponent(this.sInp);
			
			if(!url) return false;
			
			// MG: moved the notifier add code to here because of a few returns in the previous code (resulting in the notifier possibly being shown forever on invalid input while there is no AJAX call made)
			if(this.options.useNotifier) this.field.removeClass('ac_field').addClass('ac_field_busy');
			
			var options = {
				url: url,
				type: self.options.meth,
				success: function(req)
				{
					if(self.options.useNotifier)
					{
						self.field.removeClass('ac_field_busy').addClass('ac_field');

					}
					
					self.setSuggestions(req,input);
				},
				error:	(typeof self.options.onAjaxError == 'function') ? function(status) {
					if (self.options.useNotifier)
					{
						self.field.removeClass('ac_field_busy').addClass('ac_field');	
					}
					self.options.onAjaxError(status);
				} : function(status) {
						if(self.options.useNotifier)
						{
							self.field.removeClass('ac_field_busy').addClass('ac_field');	
							
						}
						alert('AJAX error: '+status);
				}
			};
			$.ajax(options);
        },
        setSuggestions: function(req,input)
        {
        	if(input != this.field.val())
        		return false;
        	if(this.options.json){
        		// MG: jQuery will automatically parse the JSON data if the correct headers are set, if req.results is set the data will already be parsed by jQuery, otherwise do a parse here. (same goes for XML, but i didn't modify that code)
        		this.aSug = req.results;
        		if(!this.aSug) {
        			var jsondata = eval('('+req+')');
        			this.aSug = jsondata.results;
        		}
        	} else {
        		var results = req.getElementsByTagName('results')[0].childNodes;
        		this.aSug = [];
        		for(var i=0;i<results.length;i++)
				{
				  if(results[i].hasChildNodes())
				    this.aSug.push(  { 'id':results[i].getAttribute('id'), 'value':results[i].childNodes[0].nodeValue, 'info':results[i].getAttribute('info') }  );
				}
        		
        	}
        	this.acID = 'ac_'+this.field.attr('id');
        	this.createList(this.aSug);
        },
        createList: function(arr)
        {
        	var self = this;
        	
        	// MG: if the list was visible before don't animate it (anim speed=0), otherwise animate with the given speed when the list is shown
        	var animSpeed = this.wasVisible ? 0 : this.options.showAnimSpeed;
        	$('#'+this.acID).remove();
			
			this.killTimeout();
			
			if(arr.length == 0 && !this.options.showNoResults) return false;
			
			var div = $('<div></div>').addClass(this.options.className).attr('id',this.acID);	
			var hcorner = $('<div></div>').addClass('ac_corner');
			var hbar = $('<div></div>').addClass('ac_bar');
			var header = $('<div></div>').addClass('ac_header');
			header.append(hcorner).append(hbar);
			div.append(header);
			
			var ul = $('<ul></ul>').attr('id','ac_ul');
			
			if(arr.length == 0 && this.options.showNoResults)
			{
				var li = $('<li></li>').addClass('ac_warning').html(this.options.noResults);
				ul.append(li);
			}
			else
			{
				// MG: here too; last input should be used instead of the total input
				var lastInput = this.getLastInput(this.sInp);
				// MG: added the maxResults option
				for(var i= 0;i<arr.length && i < self.options.maxResults;i++){
					
					var val = arr[i].value;
					// MG: few fixes to use last input instead of total input, resulting in invalid highlight of typed characters
					var st = val.toLowerCase().indexOf(lastInput.toLowerCase());
					var output = val.substring(0,st) + '<em>' + val.substring(st,st+lastInput.length) + '</em>' + val.substring(st+lastInput.length);
					
					var span = $('<span></span>').html(output);
					
					if(this.info != '')
					{
						var br = $('<br/>');
						span.append(br);
						var small = $('<small></small>').html(arr[i].info);
						span.append(small);	
					}
					var a = $('<a></a>').attr('href','#');
					// USE THE FOLLOWING IF YOU WANT TO PUT TITLES TO ELEMENTS (YOU COULD USE THE SAME AS WE DID WITH INFO ABOVE)
					var tl = $('<span></span>').addClass('tl').html('&nbsp;');
					var tr = $('<span></span>').addClass('tr').html('&nbsp;');
					
					a.append(tl);
					a.append(tr);
					a.append(span).attr('rel',i);
					
					a.click(function(e){self.setHighlightedValue(); return false;});
					a.mouseover(function(e){self.setHighlight($(this).attr('rel'));});
					
					var li = $('<li></li>').html(a);
					ul.append(li);
				}	
				// MG: check if there are more results than allowed to display, then show a message if the options is enabled
				if(arr.length > self.options.maxResults && this.options.showMoreResults) {
					var li = $('<li></li>').addClass('ac_message').html(this.options.moreResults);
					ul.append(li);
				}
			}
			
			div.append(ul);
			// MG: Add a scroll handler which temporarily adds a class to the ul, this is to force a redraw (otherwise IE sometimes doesn't redraw the list when scrolling)
			ul.scroll(function() {$("#ac_ul").addClass("ac_tmpClass").removeClass("ac_tmpClass");});
			var fcorner = $('<div></div>').addClass('ac_corner');
			var fbar = $('<div></div>').addClass('ac_bar');
			var footer = $('<div></div>').addClass('ac_footer');
			footer.append(fcorner).append(fbar);
			div.append(footer);
			
			var pos = this.field.offset();

			// MG: modified width() function call to outerWidth(), otherwise the border and padding of the input element were ignored, possibly resulting in the list being narrower than the input element
			var w = 
		    (
		      this.options.setWidth && this.field.outerWidth() < this.options.minWidth
		    )
		    ? this.options.minWidth : 
		    (
		      this.options.setWidth && this.field.outerWidth() > this.options.maxWidth
		    )
		    ? this.options.maxWidth : 
		    this.field.outerWidth();
		    
		    /*div.left(pos.left).top(pos.top + this.field.height()).width(w).mouseover(function(){self.killTimeout();}).mouseout(function(){self.resetTimeout();});*/
		    // bug fixed by Konstantin
			// MG: modified height() call to outerHeight(), otherwise the border and padding of the input element were ignored resulting in an offset
			// MG: added min-height and max-height css properties, to get the maxHeight option working
		    div.css('left',pos.left)
		       .css('top',pos.top + this.field.outerHeight() + this.options.offsetY)
		       .width(w)
		       .css('min-height', this.options.maxHeight > 0 ? '20px' : '')
		       .css('max-height', this.options.maxHeight > 0 ? this.options.maxHeight + 'px' : '')
		       .mouseover(function(){self.killTimeout();})
		       .mouseout(function(){self.resetTimeout();});
		    
		    $(document.body).append(div);
	        
		    // MG: code for the maxHeight property, if enabled
		    if(this.options.maxHeight > 0) {
		    	// When maxHeight is enabled we have to do some browser and version checks, since IE has some bugs regarding overflow and sizing of the div/ul
			    if($.browser.msie) {
			    	// determine the width of the scrollbar by creating 2 temporary divs and changing the overflow value
			    	var tmpDiv = $('<div style="width:100px; height:100px; overflow:hidden; position:absolute; top:-1000px; left:-1000px;"></div>');
			        var tmpInnerDiv = $('<div style="height:200px;">');
			        $(document.body).append(tmpDiv.append(tmpInnerDiv)); 
			        var width1 = tmpInnerDiv.innerWidth(); 
			        tmpDiv.css('overflow-y', 'scroll'); 
			        var width2 = tmpInnerDiv.innerWidth();
			        tmpDiv.remove();
			        var scrollbarWidth = width1 - width2;
			        
			    	if($.browser.version < 7) {
			    		// IE6
			    		// set ul height to 100%, so it will scale with the div (otherwise it won't scale correctly)
			    		ul.height("100%");
			    		// if height of the div is larger than the maxHeight
			    		if(div.outerHeight() > this.options.maxHeight - ((header.outerHeight(true) + footer.outerHeight(true)) * 2)) {
			    			// set the maximum height of the div
			    			div.css('height', this.options.maxHeight - ((header.outerHeight(true) + footer.outerHeight(true)) * 2) + 'px');
			    			
			    			// fix to display the scrollbar inside the ul instead of outside
			    			ul.css({'padding-right' : scrollbarWidth, 'overflow-x' : 'hidden'});
				    		ul.width(ul.width() - scrollbarWidth);
			    		}
			    	} else if($.browser.version < 8) {
			    		// IE7
			    		// set ul height
			    		ul.height(div.height() - (header.outerHeight(true) + footer.outerHeight(true)));
			    		// fix to prevent the content showing in front of the scrollbar
			    		if (ul[0].scrollHeight > ul[0].offsetHeight) {
		    				ul.css({ 'padding-right' : scrollbarWidth + 'px', 'overflow-x' : 'hidden' });
				    	}
			    	} else {
			    		// IE 8
			    		// set ul height
			    		ul.height(div.height() - (header.outerHeight(true) + footer.outerHeight(true)));
			    	}
			    } else {
			    	// All other browsers
			    	// set ul height
			    	ul.height(div.height() - (header.outerHeight(true) + footer.outerHeight(true)));
			    	
			    	// fix for firefox, otherwise it will keep on displaying a vertical scrollbar for an unkown reason... (and sometimes the list was still scrolled down)
			    	if(div.height() < this.options.maxHeight) {
			    		ul.css('overflow', 'hidden');
			    		ul.scrollTop(0);
			    	}
			    }
		    } else {
		    	// fix for firefox, otherwise it will keep on displaying a vertical scrollbar for an unkown reason...
		    	ul.css('overflow', 'hidden');
		    }
		    
		    // MG: the following code is needed to animate the show of the list
		    // store the div height and display values
		    var divHeight = div.height();
		    var divDisplay = div.css('display');
		    if(this.options.showAnimProperties.height) {
		    	// height option is set, overrule it with the actual height of the div
		    	this.options.showAnimProperties.height = divHeight + 'px';
		    }
		    if(this.options.showAnimProperties.width) {
		    	// width option is set, overrule it with the calculated width of the div
		    	this.options.showAnimProperties.width = w + 'px';
		    }
		    // start the animate by hiding the div and setting the desired properties (0 when used to animate, otherwise the value it has to be) 
		    div.hide()
		       .height(this.options.showAnimProperties.height ? 0 : divHeight)
		       .width(this.options.showAnimProperties.width ? 0 : w)
		       .css('opacity', this.options.showAnimProperties.opacity ? 0 : div.css('opacity'))
		       .css('display', divDisplay)
		       .animate(this.options.showAnimProperties, animSpeed, 'linear');
		    
		    
		    this.setHighlight(0);
		    // MG: suppress timeout if it's 0
		    if(this.options.timeout > 0) {
		    	this.toID = setTimeout(function(){self.clearSuggestions();},this.options.timeout);
		    }
        },
        // MG: added the count parameter, for scrolling up/down more than 1 element
        changeHighlight: function(key, count){
        	var list = $('#ac_ul');
        	
        	// MG: modified to valid check
        	if(!list.length) return false;
        	
        	// MG: removed tab key, will never be passed to this function
        	// MG: added page down key and count
        	var n = (key == this.KEY_DOWN || key == this.KEY_PAGEDOWN || key == this.KEY_END)? this.iHigh + count : this.iHigh - count; 
        	
			n = (n >= list.children().length)? list.children().length -1 : ((n < 0)? 0 : n);
			
			this.setHighlight(n);
			
			// MG: code for scrolling through the list when a key was pressed, this code will ensure the highlighted element will always be visible
			var totalTop = $(".ac_highlight").position().top - $("#ac_ul").position().top + $(".ac_header").outerHeight(true) + $(".ac_header").position().top;
			if(totalTop + $(".ac_highlight").outerHeight() > $("#ac_ul").height()) {
				$("#ac_ul").scrollTop($("#ac_ul").scrollTop() + ((totalTop + $(".ac_highlight").outerHeight()) - $("#ac_ul").height()));
			} else if(totalTop < 0) {
				$("#ac_ul").scrollTop($("#ac_ul").scrollTop() + totalTop);
			}
        },
        setHighlight: function(n){
        	
        	var list = $('#ac_ul');
        	
        	// MG: modified to valid check
        	if(!list.length) return false;
        	
        	this.iHigh = Number(n);
        	
        	list.children().removeClass('ac_highlight').eq(this.iHigh).addClass('ac_highlight');
        	
        	this.killTimeout();
        },
        clearHighlight: function(){
        	var list = $('#ac_ul');
        	
        	// MG: modified to valid check
        	if(!list.length) return false;
        	list.children().removeClass('ac_highlight');
        	this.iHigh = 0;	
        },
        setHighlightedValue: function(){
        	if(this.iHigh>=0)
        	{
        		if(!this.aSug[this.iHigh]) return;
        		if(null != this.options.valueSep)
        		{
        			var str = this.getLastInput(this.field.val());
        			var idx = this.field.val().lastIndexOf(str);
        			str	= this.aSug[this.iHigh].value+this.options.valueSep;
        			this.sInp = idx == -1? str: this.field.val().substring(0, idx) + str;
        			this.field.val(this.sInp);
        		}
        		else
        		{
        			var str = this.getLastInput(this.field.val());
        			var idx = this.field.val().lastIndexOf(str);
        			str = this.aSug[this.iHigh].value;
        			this.sInp =  idx == -1? str: this.field.val().substring(0,idx) + str;
        			this.field.val(this.sInp);
        		}
        		this.field.focus();
        		if(this.field.selectionStart)
        		{
        			this.field.setSelectionRange(this.sInp.length, this.sInp.length);
        		}
        		this.clearSuggestions();
        		if(typeof this.options.callback == 'function')
        			this.options.callback(this.aSug[this.iHigh]);
        	}
        	
        	// MG: clear the suggestion list, otherwise invalid results could be shown when entering another value
        	this.aSug = [];
        },
        killTimeout: function(){
        	clearTimeout(this.toID);
        },
        resetTimeout: function(){
        	this.killTimeout();
        	var self = this;
        	// MG: suppress timeout if it's 0
        	if(self.options.timeout > 0) {
        		this.toID = setTimeout(function(){ self.clearSuggestions();}, self.options.timeout);
		    }
        },
        clearSuggestions: function(){
        	this.killTimeout();
        	// MG: fadeOut replaced by user set animation
        	$('#'+this.acID).animate(this.options.hideAnimProperties, this.options.hideAnimSpeed, 'linear', function(){$(this).remove();});
        }
        
       });
       this.init();
      }})(jQuery);
