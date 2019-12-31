/**
 * 处理系统中的提示框alert（提示框），confirm（页面判断提示框），dialog（弹出页面）,tips(显示提示信息)
 * var api = frameElement.api, W = api.opener;其中W为调用窗口的页面，如果获取父页面中元素：W.document.getElementById('idname').value,其中api为窗口的api
 * 
 * <!-- 可以设置弹出框的风格 blue,chrome,discuz,iblack,iblue,idialog,igreen,jtop,mac -->
  <script src="components/lhgdialog-4.2.0/lhgdialog/lhgdialog.min.js?self=true&skin=mac"></script>
 */
(function($,mg,win){
	var jquery203URL ="js/jquery-2.0.3.min.js";
	var LhgDialogJSURL = 'components/lhgdialog/4.2.0/lhgdialog/lhgdialog.min.js?self=true&skin=mac';
	var LayerJSURL = 'components/layer-3.0.3/layer.js';
	var jqueryTipURL = 'js/jquery/jquery.tips.js';
	
	win.loadLhgDialogJS =function(){
			jQuery("head").find("script[role='lhgdialog']").remove();
            var head = jQuery("head");
            jQuery("<scri" + "pt>" + "</scr" + "ipt>").attr({ role: 'lhgdialog', src: LhgDialogJSURL, type: 'text/javascript' }).appendTo(head);
	};
	win.loadJS = function(rolename,jsurl){
			jQuery("script[role='"+rolename+"']").remove();
		    var head = jQuery("body");
		    head.append(jQuery("<scri" + "pt>" + "</scr" + "ipt>").attr({ role: rolename, src: jsurl, type: 'text/javascript' }).appendTo(head));
		    
	};
	win.checkLoadJS = function(rolename,js){
		var loadjs = jQuery("head").find("script[role='"+rolename+"']");
		if(loadjs.length>0){
			;
		}else{
			win.loadJS(rolename,js);
		}
	};
	/**
	 * 初始化工作，把对应的js导入到页面中
	 */
	win.init=function(){
		var scripts = document.getElementsByTagName("script");
		var tag = 0; //0未导入，1已经导入
		for(var i=0;i<scripts.length;i++){
			if(scripts[i].src == LhgDialogJSURL){
				tag = 1;
				break;
			}
		}
		if(0 == tag){
			win.loadJS('jquery-2.0.3',jquery203URL);
			win.loadLhgDialogJS();
		}
	};
	/**
	 * 获取网页的宽和高
	 */
	win.getPageWidthAndHeight = function(){
		var pheight = window.screen.availHeight;
//		if(isNaN(pheight)){
//			pheight = document.body.clientHeight;
//		}
		var pwidth = window.screen.availWidth;
//		if(isNaN(pwidth)){
//			pwidth = document.body.clientWidth;
//		}
		return [pheight,pwidth];
	};
	/**
	 * 打开元素的浏览器的窗口
	 */
	win.openWin = function(url){
		window.open(url,'','');
	};
	win.openWin = function(sURL,w,h){	
		if(typeof(w) == 'undefined' || w == null  || w == ''){
			w = 600;
		}
		if(typeof(h) == 'undefined' || h == null  || h == ''){
			h = 400;
		}
		 var iTop = (window.screen.height-30-h)/2;
		 iTop =0;
		 var iLeft = (window.screen.width-10-w)/2; 
		 iLeft =0;
		 //var features1 = 'height='+h+',innerHeight='+h+',width='+w+',innerWidth='+w+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizeable=yes,location=no,status=no';
		 var features1 = 'height='+h+',width='+w+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no';
		 //全屏的代码事例
		 //window.open(url,'新窗口','width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-30)+',top=0,left=0,resizable=yes,status=yes,menubar=no,scrollbars=yes');
		 var oWin = window.open(sURL,'',features1);
		 oWin.resizeTo(w,h); 
		 iLeft=(screen.width-w)/2;
		 iTop=(screen.height-h)/2;
		 oWin.moveTo(iLeft,iTop);
		 oWin.focus();
	};
	/**
	 * 全屏打开原生窗口
	 */
	win.fullWin = function(url){
		var w = window.screen.width;//*0.95;
		 var h = window.screen.height;//*0.85;
		 var iTop = (h-30)/2;
		 iTop =0;
		 var iLeft = (w-10)/2;
		 iLeft = 0;
		//全屏的代码事例
		 //window.open(url,'新窗口','width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-30)+',top=0,left=0,resizable=yes,status=yes,menubar=no,scrollbars=yes');
		 var features1 = 'height='+h+',width='+w+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no';
		 var oWin = window.open(sURL,'',features1);
		 try{
		 oWin.resizeTo(w,h);
		 iLeft=(screen.width-w)/2;
		 iTop=(screen.height-h)/2;
		 oWin.moveTo(iLeft,iTop);
		 oWin.focus();
		 }catch(e){}
	};
	win.fullWin = function(url,title){
		var w = window.screen.width;//*0.95;
		 var h = window.screen.height;//*0.85;
		 var iTop = (h-30)/2;
		 iTop =0;
		 var iLeft = (w-10)/2;
		 iLeft = 0;
		 var features1 = 'height='+h+',width='+w+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no';
		 var oWin = window.open(sURL,title,features1);
		 try{
		 oWin.resizeTo(w,h);
		 iLeft=(screen.width-w)/2;
		 iTop=(screen.height-h)/2;
		 oWin.moveTo(iLeft,iTop);
		 oWin.focus();
		 }catch(e){}
	};
	win.getWinWidthAndHeight = function(height,width){
		height = height+'';
		width = width+'';
		var isPersent = 1; //1是百分号，0否
 		if((null!=height) && ((height+'').indexOf("%")<0)) isPersent = 0;
 		var pHei = '';
 		var pWid = '';
 		if(1==isPersent){
 			var arr = win.getPageWidthAndHeight();
 			//alert('arr[0]='+arr[0]+' | arr[1]='+arr[1]);
 			pHei = arr[0]*parseFloat(height.substring(0,height.length-1))/100;
 			pWid = arr[1]*parseFloat(width.substring(0,width.length-1))/100;
 		}else{
 			pHei = height;
 			pWid = width;
 		}
 		return [pHei,pWid];
	};
	/**
	 * 打开一个弹出窗口
	 * @param ptitle 弹出窗口的标题，如果为0则不显示标题
	 * @param pmaxmin false表示不能最小和最大化，true可以最小最大化
	 * @param url  打开的窗口对应的url地址
	 * @param height 窗口高度像素也可以为百分数，如：500或50%
	 * @param width  窗口宽度像素也可以为百分数，如：500或50%
	 */
 	win.openFrameWin = function(ptitle,pmaxmin,url,height,width,closeFn,xButton,winid){ //打开一个frame窗口
 		win.init();
 		if(false == xButton) xButton = false; else xButton=null;
 		var arr = win.getWinWidthAndHeight(height,width);
 		var pWid = arr[1];
 		var pHei = arr[0];
 		//alert('pmaxmin='+pmaxmin+' | closeFn='+closeFn+' | height='+height+' | width='+width+' | pWid='+pWid+' | pHei='+pHei);
 		jQuery.dialog({
 			title:ptitle,
		    content: 'url:'+url,
		    lock:true,
		    min:pmaxmin,
		    max:pmaxmin,
		    width:pWid,
		    height:pHei,
		    close:closeFn,
		    resize: false,
		    cancel:xButton,
		    id:winid
		    //,init:function(){jQuery.dialog.tips('数据加载中...',600,'loading.gif');}
		});
 	};
 	
 	win.openFrameWinWithConent = function(ptitle,pmaxmin,content,height,width,closeFn,xButton){ //打开一个frame窗口
 		win.init();
 		if(false == xButton) xButton = false; else xButton=null;
 		var arr = win.getWinWidthAndHeight(height,width);
 		var pWid = arr[1];
 		var pHei = arr[0];
 		//alert('pmaxmin='+pmaxmin+' | closeFn='+closeFn+' | height='+height+' | width='+width+' | pWid='+pWid+' | pHei='+pHei);
 		jQuery.dialog({
 			title:ptitle,
		    content: content,
		    lock:true,
		    min:pmaxmin,
		    max:pmaxmin,
		    width:pWid,
		    height:pHei,
		    close:closeFn,
		    resize: false,
		    cancel:xButton
		    //,init:function(){jQuery.dialog.tips('数据加载中...',600,'loading.gif');}
		});
 	};
 	
 	win.openFrameWinWithInitFn = function(ptitle,pmaxmin,url,height,width,initFn,closeFn,xButton){ //打开一个frame窗口  initFn为初始化弹出窗口后执行的函数
 		win.init();
 		if(false == xButton) xButton = false; else xButton=null;
 		var arr = win.getWinWidthAndHeight(height,width);
 		var pWid = arr[1];
 		var pHei = arr[0];
 		//alert('pmaxmin='+pmaxmin+' | closeFn='+closeFn+' | height='+height+' | width='+width+' | pWid='+pWid+' | pHei='+pHei);
 		return jQuery.dialog({
 			title:ptitle,
		    content: 'url:'+url,
		    lock:true,
		    min:pmaxmin,
		    max:pmaxmin,
		    width:pWid,
		    height:pHei,
		    close:closeFn,
		    resize: false,
		    cancel:xButton
		    //,init:function(){jQuery.dialog.tips('数据加载中...',600,'loading.gif');}
		});
 	};
 	win.openFrameWinWithInitFn2 = function(ptitle,pmaxmin,htmlcon,height,width,initFn,closeFn,xButton){ //打开一个frame窗口  initFn为初始化弹出窗口后执行的函数
 		win.init();
 		if(false == xButton) xButton = false; else xButton=null;
 		var arr = win.getWinWidthAndHeight(height,width);
 		var pWid = arr[1];
 		var pHei = arr[0];
 		alert('htmlcon='+htmlcon);
 		//alert('pmaxmin='+pmaxmin+' | closeFn='+closeFn+' | height='+height+' | width='+width+' | pWid='+pWid+' | pHei='+pHei);
 		return jQuery.dialog({
 			title:ptitle,
		    content: htmlcon,
		    lock:true,
		    min:pmaxmin,
		    max:pmaxmin,
		    width:pWid,
		    height:pHei,
		    close:closeFn,
		    resize: false,
		    cancel:xButton
		    //,init:function(){jQuery.dialog.tips('数据加载中...',600,'loading.gif');}
		});
 	};
 	win.openFrameWinMax2 = function(ptitle,pmaxmin,url,height,width,closeFn,xButton){ //打开一个frame窗口
 		if(false == xButton) xButton = false; else xButton=null;
 		win.init();
 		var arr = win.getWinWidthAndHeight(height,width);
 		var pWid = arr[1];
 		var pHei = arr[0];
 		jQuery.dialog({
 			title:ptitle,
		    content: 'url:'+url,
		    lock:true,
		    min:pmaxmin,
		    max:pmaxmin,
		    width:pWid,
		    height:pHei,
		    close:closeFn,
		    cancel:xButton
		}).max();
 	};
 	win.openFrameWinMax3 = function(ptitle,url,closeFn,xButton){ //打开一个frame窗口
 		/*
 		if(false == xButton) xButton = false; else xButton=null;
 		win.init();
 		var arr = win.getPageWidthAndHeight();
 		var pWid = arr[1];
 		var pHei = arr[0];
 		jQuery.dialog({
 			title:ptitle,
		    content: 'url:'+url,
		    lock:true,
		    width:pWid,
		    height:pHei,
		    close:closeFn,
		    cancel:xButton,
		    min:false,
		    max:false
		}).max();*/
 		win.openFrameWin(ptitle,false,url,"95%","95%",closeFn,xButton);
 	};
 	win.openFrameWinMax4 = function(ptitle,pmaxmin,url,height,width,wid,closeFn,xButton){ //打开一个frame窗口
 		if(false == xButton) xButton = false; else xButton=null;
 		win.init();
 		var arr = win.getWinWidthAndHeight(height,width);
 		var pWid = arr[1];
 		var pHei = arr[0];
 		jQuery.dialog({
 			id:wid,
 			title:ptitle,
		    content: 'url:'+url,
		    lock:true,
		    min:pmaxmin,
		    max:pmaxmin,
		    width:pWid,
		    height:pHei,
		    close:closeFn,
		    cancel:xButton
		}).max();
 	};
 	/**
	 * 打开一个弹出窗口
	 * @param ptitle 弹出窗口的标题，如果为0则不显示标题
	 * @param pmaxmin false表示不能最小和最大化，true可以最小最大化
	 * @param htmlcontent  打开的窗口显示的内容为HtmlCotent
	 * @param canClose 0表示弹出窗口标题中没有关闭按钮，1出现关闭按钮
	 * @param height 窗口高度像素也可以为百分数，如：500px或50%
	 * @param width  窗口宽度像素也可以为百分数，如：500px或50%
	 */
 	win.openHTMLWin = function(ptitle,pmaxmin,htmlcontent,height,width,closeFn){ //打开一个innerHTML内容窗口
 		win.init();
 		var arr = win.getWinWidthAndHeight(height,width);
 		var pWid = arr[1];
 		var pHei = arr[0];
 		jQuery.dialog({
 			title:ptitle,
		    content: htmlcontent,
		    lock:true,
		    min:pmaxmin,
		    max:pmaxmin,
		    width:pWid,
		    height:pHei,
		    close:closeFn
		});
 	};
 	/**
 	 * 关闭当前的frame弹出窗口
 	 */
 	win.closeFrameWin = function(){
 		try{frameElement.api.close();}catch(e){} 
 	};
 	win.closeWin = function(dialogId){//关闭弹出窗口
 		try{jQuery.dialog({id:dialogId}).close();}catch(e){} 
 	};
 	win.closeWin = function(){//关闭弹出窗口
 		try{
 			frameElement.api.close(); 
 		}catch(e){}
 	};
 	
 	/**
 	 * 提示框，alertContent提示的内容，callback为点击确定后的回调函数
 	 */
	win.alert = function(alertContent,callback){//仅仅就是提示信息
 		win.init();
 		var cc = {};
 		if(jQuery.isFunction(callback)) cc = callback //直接用原生alert
 		jQuery.dialog.alert(alertContent,cc);
 	};
 	/**
 	 * 询问提示框
 	 * @param content为询问的内容
 	 * @param confirmCallBack 为选择确定按钮的回调函数，function(layero, index){}
 	 * @param cancelCallBack  为选择取消按钮的回调函数，function(layero, index){}
 	 */
 	win.confirm = function(content,confirmCallBack,cancelCallBack){
 		win.init();
 		jQuery.dialog.confirm(content,confirmCallBack,cancelCallBack);
 	};
 	
 	/**
 	 * 提示信息
 	 * 使用layer进行处理
 	 */
 	win.initTip = function(){
 		//win.checkLoadJS('layer',LayerJSURL);
 		win.checkLoadJS('jquery.tips',jqueryTipURL);
 	};
 	win.tips = function(tipsContent,tipId,time){
 		win.initTip();
 		//layer.tips(tipsContent, '#'+tipId, {tips: time});
 		jQuery("[id='"+tipId+"']").tips({side:3,msg:tipsContent,bg:'#307FC1',time:time,x:0,y:0}); //,color:'#FFF'
 		//alert('tips:'+tipsContent);
 	};
 	//加载数据开始
 	win.startLoadingDialogTip = function(){
 		win.init();
 		jQuery.dialog.tips('数据加载中...',1,'loading.gif');
 	};
 	
 	//加载完毕
 	win.loadedDialogTip = function(callback,params){
 		win.init();
 		jQuery.dialog.tips('数据加载完毕',1,'tips.gif',function(){ //tips.gif  success.gif
 		//这里写完成后执行的其它代码
 			if(typeof(callback) == 'function'){
 				//callback.call(callback,prams); //动态执行方法
 				try{
 					callback(params);
 				}catch(e){}
 			}
 		});
 		win.closeWin();
 	};
 	
 	win.fullWin =function(sURL){
		 var w = window.screen.width;//*0.95;
		 var h = window.screen.height;//*0.85;
		 var iTop = (h-30)/2;
		 iTop =0;
		 var iLeft = (w-10)/2;
		 iLeft = 0;
		 var features1 = 'height='+h+',width='+w+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no';
		 var oWin = window.open(sURL,'',features1);
		 try{
		 oWin.resizeTo(w,h);
		 iLeft=(screen.width-w)/2;
		 iTop=(screen.height-h)/2;
		 oWin.moveTo(iLeft,iTop);
		 oWin.focus();
		 }catch(e){}
	};
	/**
 	 * 关闭原生窗口
 	 */
 	win.closeOrientWin = function(){
 		window.close();
 	};
})(jQuery,window.mg||(window.mg={}),window.mg.window||(window.mg.window={}));
