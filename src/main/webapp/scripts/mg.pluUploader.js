/**
 * 
 * mg.pluUploader是plupload上传组件的工具类js
 * 使用注意如下事项：
 * 1、对应的上传按钮必须是可见的不能隐藏否则会报初始化错误。
 * 2、js格式如下：
 *     {
		url:'',             //应用的默认路径,eg: /root
		path:'',            //上传的文件夹，eg:informations
		folder:'',          //上传的父文件夹,eg:upload，默认upload
		maxFileSize:'',     //上传文件大小,eg:10mb,100kb,1gb etc.
		browserButton:'',   //上传按钮对应的id
		up_container:'',    //上传按钮对应的父容器的id
 		filters:{
			title:'',       //上传选择文件时显示名称
			extensions:''   //可以删除的文件，不控制为空或*.*
		},
		tableName:'',       //上传的tableName唯一
		queueSize:'',       //最多允许上传的文件个数
		canDel:'',          //是否可以删除（修改），0否，1是
		fileName:'',        //文件名
		saveName:'',        //保存名
		uploadCallBack:'',  //上传回调函数,函数的参数为json格式
		delCallBack:''      //删除回调函数,函数的参数为json格式
	}; 
 */

var mg = window.mg || {};//命名空间
{
	//先判断当期命名空间下的子空间是否存在，不存在就new function(){}创建
   if(undefined == mg.pluUploader || null == mg.pluUploader){
   		mg.pluUploader = new function(){};
   }
   
   //下面给子空间扩展方法
   var self = mg.pluUploader;
   
   //允许上传的全部文件
   var allowextensions = "rar,zip,7z,txt,pdf,xml,dwg,vsd,eml,msg,ceb,tif,tiff,key,iso,ofd,doc,docx,xls,xlsx,xlsm,ppt,pptx,pps,wps,mpp,odt,ods,odp,jpg,jpeg,gif,png,bmp,asf,wmv,wav,swf,flv,mp3,mp4,rm,rmvb,avi";
   var tmpextensions = ","+allowextensions+",";
   /**
	 * pluUploader上传组件初始化
	 * json:上传参数
	 */
	self._mgPluUploader = function(json){
		var url = json.url;                                     //服务路径：/root
		var path = json.path;                                   //上传存放路径，例如：infomations等
		var folder = (null==json.folder)?"upload":json.folder;  //上传路径，默认为upload
		var maxFileSize = json.maxFileSize;                     //文件最大容量 1gb
		var browserButton = json.browserButton;                 //显示上传按钮的id
		var up_container = json.up_container;                   //显示上传按钮的div容器id
		var filters = self._getFilters(json.filters);           //上传过滤文件json={extensions,title}
		var tableName = json.tableName;                         //上传组件的tableName
		var queueSize = json.queueSize;                         //最多允许上传的文件的数量
		var canDel = json.canDel;                               //是否允许删除，0否，1是
		var fileName = json.fileName;                           //文件原始名组件的名称
		var saveName = json.saveName;                           //文件保存名组件的名称
		var uploadCallBack = json.uploadCallBack;               //上传后的回调函数
		var delCallBack = json.delCallBack;                     //删除后的回调函数
		var oldName = json.oldName;

		var swf_url = url+'/components/pluupload/pluUploader2.3.1/js/Moxie.swf';          //swf的路径
		var silverlight_url = url+'/components/pluupload/pluUploader2.3.1/js/Moxie.xap'; //
		var upload_url = url+'/pluUploader?path='+path+'&folder='+folder;             //上传路径
		
		var uploader = new plupload.Uploader({
			runtimes:'html5,html4',
			flash_swf_url:swf_url,
			silverlight_xap_url:silverlight_url,
			url:upload_url,
			max_file_size:maxFileSize,
			chunk_size:maxFileSize,     //分块大小，小于这个大小的不分块,永远不分块
			unique_names:true,
			multi_selection:true,
			browse_button:browserButton,
			prevent_duplicates:false,
			container:up_container,
			filters:filters,
	
			init:{
				FilesAdded: function(up, files) {//添加附件
					var sizeLimit = maxFileSize;
					//判断允许上传的文件的数量
					var uploadCount = jQuery("#"+tableName).find("tr").length-1+files.length; //总共需要上传的文件的总数

					if((uploadCount>0) && (uploadCount > queueSize )){
						alert('最多允许上传'+queueSize+'个文件!');
						jQuery.each(files,function(i,file){
							up.removeFile(file);
						});
					}
	
					//判断每个文件的大小
					else if( sizeLimit < files[0].size){
						jQuery.each(files,function(i,file){
							up.removeFile(file);
						});
					}
	
					else{//判断每个文件的格式或命名
						 jQuery.each(files, function(i, file) { 
							 if(file.size==0){
								alert('请不要选择空文件!');
								up.removeFile(file);
							 }
						 });
						uploader.start();
					}
					up.refresh();
					return false;
				},
				
				
				FileUploaded : function(up, file, info) {//文件上传完毕触发
					var json = info.response;
					if(json.indexOf(":\\") != -1 || json.indexOf(":\\") != null || json.indexOf(":\\") != undefined){
						var i = json.indexOf(":\\")-1;
						var A = json.substring(0,i);
						var j = json.lastIndexOf("\\")+1;
						var B = json.substring(j);
						json = A+B;
					}
					var response = jQuery.parseJSON(json);
					if (response.status) {
						var fid = file.id;
						var fOName = file.name; //原始名字
						var fSName = response.newName; //新名字
						var fExt = fOName.substring(fOName.lastIndexOf(".")+1,fOName.length);
						if((null == fSName)||("null"==fSName) || ("" == fSName) || (("."+fExt)==fSName)){
							alert(fExt+" 类型文件不允许上传!");
							jQuery("[id='"+fid+"']").remove();//没有任何返回值，说明没有上传文件[文件类型不允许]
						}else{
							var fSize = file.size; //单位字节
							fSName = fSName.substring(0,fSName.lastIndexOf("."))+"."+fExt;
							jQuery("#savename_"+fid).val(fSName);
							jQuery("#filename_"+fid).val(fOName);
							if(canDel == '1'){
								jQuery("#div_"+fid).append('<img src="images/delete.gif" onclick="mg.pluUploader.smartDeleteFileByPlu("'+fSName+'",this,"'+delCallBack+'")"/>');
							}
							
							jQuery("#"+fid).find("[name='"+fileName+"']").val(fOName);
							jQuery("#"+fid).find("[name='"+saveName+"']").val(fSName);
							
							jQuery("#fj2_"+fid).attr("onclick","mg.pluUploader.smartDownloadByPlu('"+fSName+"')");
							jQuery("#fj2_"+fid).show();
							jQuery("#a_"+fid).attr("onclick","mg.pluUploader.smartDownloadByPlu('"+fSName+"')");
							jQuery("#a_"+fid).show();
							if(canDel == '1'){
								jQuery("#del_"+fid).attr("onclick","mg.pluUploader.smartDeleteFileByPlu('"+fSName+"',this,'"+delCallBack+"')");
								jQuery("#del_"+fid).show();
							}
							jQuery("#span_"+fid).html('100%');
							
							if("" != uploadCallBack){ //上传后的回调
								try{ self._dynamicExcJsFunction(uploadCallBack,{saveName:fSName,fileName:fOName,oldName:oldName});}catch(e){} //执行回调函数
							}
						}
					}
					up.refresh();
					
				},
				
				
				UploadComplete : function( up,files ) {
				},
				
				
				UploadProgress : function( up,file ) {
					var fid = file.id;
					var fidF = document.getElementById(fid);
					if(fidF){
					   jQuery("#span_"+fid).html(file.percent+'%');
					}else{
					   var fOName = file.name; //原始名字
						if(fOName.length>30){
							fOName=fOName.substr(0,20)+"..."+fOName.substr(fOName.length-6,fOName.length);
						}
					   var fSize = file.size; //单位字节
					   fSizeDis = self.getFileSizeWithuploadDisp(fSize);
					   var appendHTML = '<tr id="'+fid+'"><td><input type="hidden" name="'+fileName+'" id="'+fileName+'" value=""/><input type="hidden" name="'+saveName+'" id="'+saveName+'" value=""/><input type="hidden" name="'+saveName+'_size" value="" /><img src="'+url+'/image/fj2.gif" alt="下载" onclick="" id="fj2_'+fid+'" style="display:none"/><a href="javascript:void(0)" style="TEXT-DECORATION:none;cursor:pointer;" id="a_'+fid+'" style="display:none">'+fOName+'&nbsp;&nbsp;'+fSizeDis+'</a><img src="'+url+'/image/del.gif" title="删除" onclick="javascript:void(0)" style="cursor:pointer;display:none" id="del_'+fid+'"/>&nbsp;&nbsp;<span id="span_'+fid+'">0%</span></td></tr>';
					   // var appendHTML = '<tr id="'+fid+'"><td><input type="hidden" name="'+fileName+'" id="'+fileName+'" value=""/><input type="hidden" name="'+saveName+'" id="'+saveName+'" value=""/><input type="hidden" name="'+saveName+'_size" value="" /><img src="'+url+'/image/fj2.gif" alt="下载" onclick="" id="fj2_'+fid+'" style="display:none"/><a href="javascript:void(0)" style="TEXT-DECORATION:none;cursor:pointer;" id="a_'+fid+'" style="display:none">'+fOName+'&nbsp;&nbsp;'+fSizeDis+'</a>&nbsp;&nbsp;<span id="span_'+fid+'">0%</span></td></tr>';
						jQuery("#"+tableName).append(appendHTML);
					}
					
				},
				
				Error:function(up,err){
					var code = err.code;
					if('-500' == code){
						alert('上传组件初始化失败!');
					}else if('-600' == code){
						alert('您选择的文件过大!');
					}else if('-601' == code){
						alert('您选择的文件不符合要求!');
					}else if('-602' == code){
						alert('请不要选择重复文件!');
					}else if('-702' == code){
						alert('您选择的文件过大!');
					}else{
						alert(err.code+' error!');
					}
					//up.refresh();
				}
			}//end init
			
		});//end new uploader
	    
		uploader.init(); //初始化
	};
	
	self._getFilters = function(filters){ //获取过滤条件
	    var rslt = [{}];
		if((null == filters) || (undefined == filters) || ('*.*'== filters.extensions) || (''== filters.extensions) || ('*'== filters.extensions)){
			rslt = [{title:"所有文件",extensions: allowextensions}];
		}else{
		    var myexten = filters.extensions;
			var mytitle = filters.title;
			myexten = myexten.replace(";\\*.",",").replace("\\*.","");
			var myextenArr = myexten.split(",");
			var allowed = "";//最终允许上传的文件类型
			for(var k=0;k<myextenArr.length;k++){
				if(tmpextensions.indexOf(","+myextenArr[k]+",")>-1){
					if(allowed.length>0) allowed+= ",";
					allowed += myextenArr[k];
				}
			}
			rslt = [{title:mytitle,extensions:allowed}];
		}
		return rslt;
	};
	
	/**
	 * 动态执行一个js方法
	 * @param fName js函数名称
	 * @param jsParams 函数的json格式的参数
	 */
	self._dynamicExcJsFunction = function(fName,jsParams){
			var func = eval(fName);
			return func(jsParams);
	};
	
	self.smartDownloadByPlu=function(filesavename){
		var url = "components/pluupload/download.jsp?fileName=" + filesavename;
		var curWwwPath = window.document.location.href
		var len2=curWwwPath.indexOf('cms_mysql');
		var len=curWwwPath.indexOf('cms');
		if(len2==len){
			curWwwPath=curWwwPath.substring(0,len+10);
		}else{
			curWwwPath=curWwwPath.substring(0,len+4);
		}

		//curWwwPath=curWwwPath.substring(0,26);
		window.open(curWwwPath+url);
	};
	
	self.viewUploaderFile = function(filesavename){
		var url = "components/pluupload/view.jsp?fileName=" + filesavename;
		window.open(url);
	};

	/**
	 * 
	 * @param {} filesavename  文件保存后的名称  20170201xxxxxx.jpg
	 * @param {} fileObj       当前文件对应的对象
	 * @param {} deletefileCallback 删除回调函数
	 */
	self.smartDeleteFileByPlu = function(filesavename,fileObj,deletefileCallback) {//删除
		// jQuery.dialog.confirm('你确定要删除吗?',
	    //     function() {
	    //        jQuery.post(F,
		//         function(H) {
		//             var G = jQuery.trim(H);
		//             if ("success" == G) {
		//                 jQuery(fileObj).parent().parent().remove(); //tr remove
		//
		// 	                if('' != jQuery.trim(deletefileCallback)){//deleteCallBack //回调函数
		// 	                	var param = {saveName:filesavename};
		// 	                	try{
		// 	                		self.deletefileCallbackWithfuntionName(deletefileCallback,param);//传递函数名，动态执行函数
		// 	                	}catch(e){mg.window.alert(deletefileCallback+' 回调函数执行失败');}
		// 	                }
		//             }
		//             if ("failure" == G) {
		//                mg.window.alert("删除失败!");
		//             }
		//         });
		//     },
	  //       function(){}
	  // );//end confirm
		$.confirm({
			title: '删除',
			content: '你确定要删除吗?',
			confirm: function() {
				var F = "components/pluupload/deletefile.jsp?filename=" + filesavename;
				var curWwwPath = window.document.location.href
				var len2=curWwwPath.indexOf('cms_mysql');
				var len=curWwwPath.indexOf('cms');
				if(len2==len){
					curWwwPath=curWwwPath.substring(0,len+10);
				}else{
					curWwwPath=curWwwPath.substring(0,len+4);
				}
				F=curWwwPath+F;
				jQuery.post(F,
					function(H) {
						var G = jQuery.trim(H);
						if ("success" == G) {
							jQuery(fileObj).parent().parent().remove(); //tr remove

							if('' != jQuery.trim(deletefileCallback)){//deleteCallBack //回调函数
								var param = {saveName:filesavename};
								try{
									self.deletefileCallbackWithfuntionName(deletefileCallback,param);//传递函数名，动态执行函数
								}catch(e){alert(deletefileCallback+' 回调函数执行失败');}
							}
						}
						if ("failure" == G) {
							alert("删除失败!");
						}
					});
			},
			cancel: function(){

			}
		});
	};




	/**
	 * 获取文件大小，带单位
	 * @param {} size 文件大小，long类型的值，单位字节
	 */
	self.getFileSizeDispWithupload = function(fSize){
		var fSizeTag = 'b';
		if((fSize/1024)>1){
			fSize = fSize/1024.0;
			fSizeTag = 'kb';
		}
		if((fSize/1024)>1){
			fSize = fSize/1024.0;
			fSizeTag = 'mb';
		}
		if((fSize/1024)>1){
			fSize = fSize/1024.0;
			fSizeTag = 'gb';
		}
		fSize = fSize.toFixed(2);
		var fSizeDis = fSize+''+fSizeTag+'';
		return fSizeDis;
	};
	self.getFileSizeWithuploadDisp=function(fSize){//用来显示的
		var fSizeTag = 'B';
		if((fSize/1024)>1){
			fSize = fSize/1024.0;
			fSizeTag = 'KB';
		}
		if((fSize/1024)>1){
			fSize = fSize/1024.0;
			fSizeTag = 'MB';
		}
		if((fSize/1024)>1){
			fSize = fSize/1024.0;
			fSizeTag = 'GB';
		}
		fSize = fSize.toFixed(2);
		var fSizeDis = '('+fSize+fSizeTag+')';
		return fSizeDis;
	};

	self.UploadProgress_Mg=function(file,tableName){
	
	};

	self.uploadCallBack_plu = function(filename){};
	self.deletefileCallback_plu = function(param){} //param为对象，{saveName:saveName}
	self.deletefileCallbackWithfuntionName = function(functionname,param){
		var funC = eval(functionname);
		new funC(param);//回调函数
	};
	
	/**
	 * 删除已经上传的文件
	 */
	self.refreshNew = function(){
		$("[name='"+saveName+"']").parent().parent().remove();
	}
	
	self.refreshNew = function(fileSaveName){
		$("[name='"+fileSaveName+"']").parent().parent().remove();
	}
   
};