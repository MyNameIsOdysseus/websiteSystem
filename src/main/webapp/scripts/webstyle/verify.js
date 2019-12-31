
;(function($, window, document,undefined) {
	//定义Code的构造函数
    var Code = function(ele, opt) {
        this.$element = ele,
        this.defaults = {
        	type : 1,
        	figure : 100,	//位数，仅在type=2时生效
        	arith : 0,	//算法，支持加减乘，0为随机，仅在type=2时生效
        	width : '200px',
		    height : '60px',
		    fontSize : '30px',
        	codeLength : 6,
        	btnId : 'check-btn',
        	ready : function(){},
        	success : function(){},
            error : function(){}
        },
        this.options = $.extend({}, this.defaults, opt)
    };
    var _code_chars = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
    var _code_color1 = ['#fffff0', '#f0ffff', '#f0fff0', '#fff0f0'];
    var _code_color2 = ['#FF0033', '#006699', '#993366', '#FF9900', '#66CC66', '#FF33CC'];
    
    //定义Code的方法
    Code.prototype = {
    	init : function() {
			var _this = this;
			
			this.loadDom();
			this.setCode();
			
			this.options.ready();
			
			this.$element[0].onselectstart = document.body.ondrag = function(){ 
				return false; 
			};
			
			//点击验证码
			this.$element.find('.verify-code, .verify-change-code').on('click', function() {
				_this.setCode();
			});
			
			//确定的点击事件
			this.htmlDoms.code_btn.on('click', function() {
				_this.checkCode();
			})
			
    	},
    	
    	//加载页面
    	loadDom : function() {
    		var panelHtml = '<div class="cerify-code-panel"><div class="verify-code"></div><div class="verify-code-area"><div class="verify-input-area"><input type="text" class="varify-input-code" placeholder="请输入验证码"/></div></div></div>';
        	this.$element.append(panelHtml);
        	
        	this.htmlDoms = {
        		code_btn : $('#'+this.options.btnId),
        		code : this.$element.find('.verify-code'),
        		code_area : this.$element.find('.verify-code-area'),
        		code_input : this.$element.find('.varify-input-code'),
        	};
        	       
    	},
    	 	
    	//设置验证码
    	setCode : function() {	
    		var color1Num = Math.floor(Math.random() * 3);
    		var color2Num = Math.floor(Math.random() * 5);
    		
    		this.htmlDoms.code.css({'background-color': _code_color1[color1Num], 'color': _code_color2[color2Num]});
    		this.htmlDoms.code_input.val('');
    		
    		var code = '';
    		this.code_chose = '';
    		
    		if(this.options.type == 1) {		//普通验证码
				for(var i = 0; i < this.options.codeLength; i++) {
					var charNum = Math.floor(Math.random() * 52);
					var tmpStyle = (charNum%2 ==0)? "font-style:italic;margin-right: 10px;":"font-weight:bolder;";
					tmpStyle += (Math.floor(Math.random() * 2) == 1)? "font-weight:bolder;":"";
					
					this.code_chose += _code_chars[charNum];
					code += '<font style="'+tmpStyle+'">'+_code_chars[charNum]+'</font>';
				}
    		}else {		//算法验证码
    			
    			var num1 = Math.floor(Math.random() * this.options.figure);
    			var num2 = Math.floor(Math.random() * this.options.figure);
    			
    			if(this.options.arith == 0) {
    				var tmparith = Math.floor(Math.random() * 3);
    			}
    			
    			switch(tmparith) {
    				case 1 :
    					this.code_chose = parseInt(num1) + parseInt(num2);
    					code = num1 + ' + ' + num2 + ' = ?';
    					break;
    				case 2 :
    					if(parseInt(num1) < parseInt(num2)) {
    						var tmpnum = num1;
    						num1 = num2;
    						num2 = tmpnum;
    					}
    					this.code_chose = parseInt(num1) - parseInt(num2);
    					code = num1 + ' - ' + num2 + ' = ?';
    					break;
    				default :
    					this.code_chose = parseInt(num1) * parseInt(num2);
    					code = num1 + ' × ' + num2 + ' = ?';
    					break;
    			}
    		}
    		
    		this.htmlDoms.code.html(code);
    		
    	},
    	
    	//比对验证码
    	checkCode : function() {
    		if(this.options.type == 1) {		//普通验证码
    			var own_input = this.htmlDoms.code_input.val().toUpperCase();
    			this.code_chose = this.code_chose.toUpperCase();
    		}else {
    			var own_input = this.htmlDoms.code_input.val();
    		}
    		
    		if(own_input == this.code_chose) {
    			this.options.success();
    		}else {
    			this.options.error();
    			this.setCode();
    		}
    	}
    };
    
    
    //定义Slide的构造函数
    var Slide = function(ele, opt) {
        this.$element = ele,
        this.defaults = {
        	
        	type : 1,
        	vOffset: 5,
            vSpace : 5,
            imgName : ['1.jpg', '2.jpg'],
            imgSize : {
	        	width: '400px',
	        	height: '200px',
	        },
	        blockSize : {
	        	width: '50px',
	        	height: '50px',
	        },
	        barSize : {
	        	width : '400px',
	        	height : '40px',
	        },
            ready : function(){},
        	success : function(){},
            error : function(){}
            
        },
        this.options = $.extend({}, this.defaults, opt)
    };
    
    
    //定义Slide的方法
    Slide.prototype = {
        
        init: function() {
        	var _this = this;
        	
        	//加载页面
        	this.loadDom();
        	this.options.ready();
        	
        	this.$element[0].onselectstart = document.body.ondrag = function(){ 
				return false; 
			};
        	
        	//按下
        	this.htmlDoms.move_block.on('touchstart', function(e) {
        		_this.start(e);
        	});
        	
        	this.htmlDoms.move_block.on('mousedown', function(e) {
        		_this.start(e);
        	});
        	
        	//拖动
            window.addEventListener("touchmove", function(e) {
            	_this.move(e);
            });
            window.addEventListener("mousemove", function(e) {
            	_this.move(e);
            });
            
            //鼠标松开
            window.addEventListener("touchend", function() {
            	_this.end();
            });
            window.addEventListener("mouseup", function() {
            	_this.end();
            });
            
            //刷新
            _this.$element.find('.verify-refresh').on('click', function() {
            	_this.refresh();
            });
        },
        
        //获取left值
        getLeft: function(node) {
			var left = $(node).offset().left; 
            return left;
        }
    };
    
    
    //定义Points的构造函数
    var Points = function(ele, opt) {
        this.$element = ele,
        this.defaults = {
        	defaultNum : 4,	//默认的文字数量
		    checkNum : 3,	//校对的文字数量
		    vSpace : 5,	//间隔
        	imgName : ['1.jpg', '2.jpg'],
        	imgSize : {
	        	width: '400px',
	        	height: '200px',
	        },
	        barSize : {
	        	width : '400px',
	        	height : '40px',
	        },
	        ready : function(){},
        	success : function(){},
            error : function(){}
        },
        this.options = $.extend({}, this.defaults, opt)
    };
    
    //定义Points的方法
    Points.prototype = {
    	init : function() {
			
			var _this = this;
			
			//加载页面
        	_this.loadDom();
        	 
        	_this.refresh();
        	_this.options.ready();
        	
        	this.$element[0].onselectstart = document.body.ondrag = function(){ 
				return false; 
			};
        	
		 	//点击事件比对
        	_this.$element.find('.verify-img-panel canvas').on('click', function(e) {
        		
				_this.checkPosArr.push(_this.getMousePos(this, e));
				
				if(_this.num == _this.options.checkNum) {
					
					_this.num = _this.createPoint(_this.getMousePos(this, e));
					setTimeout(function () { 
						var flag = _this.comparePos(_this.fontPos, _this.checkPosArr);
						
						if(flag == false) {	//验证失败
							
							_this.options.error();
							_this.$element.find('.verify-bar-area').css({'color': '#d9534f', 'border-color': '#d9534f'});
						    _this.$element.find('.verify-msg').text('验证失败');
							
							setTimeout(function () { 
								_this.$element.find('.verify-bar-area').css({'color': '#000','border-color': '#ddd'});
						    	_this.refresh();
						    }, 400);
							
						}else {	//验证成功
							_this.$element.find('.verify-bar-area').css({'color': '#4cae4c', 'border-color': '#5cb85c'});
							_this.$element.find('.verify-msg').text('验证成功');
							_this.$element.find('.verify-refresh').hide();
							_this.$element.find('.verify-img-panel').unbind('click');
							_this.options.success();
						}
					}, 400);
					
				}
				
				if(_this.num < _this.options.checkNum) {
					_this.num = _this.createPoint(_this.getMousePos(this, e));
				}

        	});
        	
        	 //刷新
            _this.$element.find('.verify-refresh').on('click', function() {
            	_this.refresh();
            });
        	
    	},
    	
    	//加载页面
    	loadDom : function() {
    		
    		this.fontPos = [];	//选中的坐标信息
    		this.checkPosArr = [];	//用户点击的坐标
    		this.num = 1;	//点击的记数
    		this.img_rand = Math.floor(Math.random() * this.options.imgName.length);			//随机的背景图片
    		
        	var panelHtml = '';
        	var tmpHtml = '';
        	
        	this.setSize = Slide.prototype.resetSize(this);	//重新设置宽度高度
        	
        	panelHtml += '<div class="verify-img-panel"><div  class="verify-refresh" style="z-index:9999"><i class="iconfont icon-refresh"></i></div><canvas width="'+this.setSize.img_width+'" height="'+this.setSize.img_height+'"></canvas></div><div class="verify-bar-area"><span  class="verify-msg"></span></div>';
        	
        	this.$element.append(panelHtml);
        	
        	
        	this.htmlDoms = {
        		img_panel : this.$element.find('.verify-img-panel'),
        		bar_area : this.$element.find('.verify-bar-area'),
        		msg : this.$element.find('.verify-msg'),
        	};
        	
    		this.htmlDoms.img_panel.css({'width': this.setSize.img_width, 'height': this.setSize.img_height, 'background-size' : this.setSize.img_width + ' '+ this.setSize.img_height, 'margin-bottom': this.options.vSpace + 'px'});
    		this.htmlDoms.bar_area.css({'width': this.options.barSize.width, 'height': this.options.barSize.height, 'line-height':this.options.barSize.height});
    		
    	},
    	
    	//绘制合成的图片
    	drawImg : function(obj, img) {
    		//准备canvas环境 
	       	var canvas = this.$element.find('canvas')[0];
	      	//var canvas=document.getElementById("myCanvas");
	       	var ctx=canvas.getContext("2d");
	       	var fontChars = [];
	       	
	       	var avg = Math.floor(parseInt(this.setSize.img_width)/(parseInt(this.options.defaultNum)+1));
	       	var tmp_index = '';
	       	var color2Num = Math.floor(Math.random() * 5);
	       	
	       	for(var i = 1; i <= this.options.defaultNum; i++) {
	       		
	       		fontChars[i-1] = this.getChars(fontStr, fontChars);
	       		
	       		tmp_index = Math.floor(Math.random()*3);
	       		ctx.font = fontSizeArr[tmp_index];
		       	ctx.fillStyle = _code_color2[color2Num];
		       	
		       	if(Math.floor(Math.random() * 2) == 1) {
		       		var tmp_y = Math.floor(parseInt(this.setSize.img_height)/2) + tmp_index*20 + 20;
		       	}else {
		       		var tmp_y = Math.floor(parseInt(this.setSize.img_height)/2) - tmp_index*20;
		       	}
		       	
		       	ctx.fillText(fontChars[i-1],avg * i, tmp_y);
		       	this.fontPos[i-1] = {'char': fontChars[i-1], 'x': avg * i, 'y': tmp_y};
		       	
	       	}
	       	
	       	for(var i = 0; i < (this.options.defaultNum-this.options.checkNum); i++) {
	       		this.shuffle(this.fontPos).pop();
	       	}
	       	
	       	var msgStr = '';
	       	for(var i = 0; i < this.fontPos.length; i++) {
	       		msgStr += this.fontPos[i].char + ',';
	       	}
	       	
	       	this.htmlDoms.msg.text('请顺序点击【' + msgStr.substring(0,msgStr.length-1) + '】');
	       	
	       	return this.fontPos;
    	},
    	
    	//获取坐标
    	getMousePos :function(obj, event) {
            var e = event || window.event;
            var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
            var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
            var x = e.clientX - ($(obj).offset().left - $(window).scrollLeft());
    		var y = e.clientY - ($(obj).offset().top - $(window).scrollTop());
    		
            return {'x': x, 'y': y};
     	},
     	
     	//递归去重
     	getChars : function(fontStr, fontChars) {
     		
     		var tmp_rand = parseInt(Math.floor(Math.random() * fontStr.length));
     		if(tmp_rand > 0) {
     			tmp_rand = tmp_rand - 1;
     		}
     		
     		tmp_char = fontStr.charAt(tmp_rand);
       		if($.inArray(tmp_char, fontChars) == -1) {
       			return tmp_char;
       		}else {
       			return Points.prototype.getChars(fontStr, fontChars);
       		}
     	},
		
	
       	//刷新
        refresh: function() {
        	var _this = this;
        	this.$element.find('.point-area').remove();
        	this.fontPos = [];
        	this.checkPosArr = [];
        	this.num = 1;
        	
        	this.img_rand = Math.floor(Math.random() * this.options.imgName.length);			//随机的背景图片
        	var img = new Image();
		    img.src = 'images/'+this.options.imgName[this.img_rand];
		 	
		 	
		 	// 加载完成开始绘制
		 	$(img).on('load', function(e) {
        		this.fontPos = _this.drawImg(_this, this);
        	});

        },
    	
    };
    
    //在插件中使用codeVerify对象
    $.fn.codeVerify = function(options, callbacks) {
        var code = new Code(this, options);
        code.init();
    };
    
    //在插件中使用slideVerify对象
    $.fn.slideVerify = function(options, callbacks) {
        var slide = new Slide(this, options);
        slide.init();
    };
    
    //在插件中使用clickVerify对象
    $.fn.pointsVerify = function(options, callbacks) {
        var points = new Points(this, options);
        points.init();
    };
   
})(jQuery, window, document);
