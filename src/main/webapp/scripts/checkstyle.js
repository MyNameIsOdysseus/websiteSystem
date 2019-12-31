

function checkcode(map) {
    var a;
    for(var key in map){
        $("#"+key).css("border","1px solid red");
        $("#"+key).attr("title",""+map[key]);
        if(a !=undefined){
            a=a+map[key]+"\n";
        }else{
            a=map[key]+"\n";
        }

    }
    alert(a);
}

function uncheckcode(map) {
    for(var key in map){
        $("#"+key).css("border","");
        $("#"+key).attr("title","");
    }
}

function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
        + " " + date.getHours() + seperator2 + date.getMinutes()
        + seperator2 + date.getSeconds();
    return currentdate;
}

function getMessage(list,xx,flag){
    var htmltable="";
    for(var a=0;a<list.length;a++){
        var index=0;
        htmltable+="<tr>";
        if(flag =="1"){
            for(var b=0;b<xx.length-1;b++){
                if(b==2){
                    htmltable+="<td title='titlevalue' class='clasname'>"+list[a][xx[b]]+"</td>";
                    htmltable=htmltable.replace(/titlevalue/g,list[a][xx[b]]);
                    if(list[a][xx[0]].length==4){
                        htmltable=htmltable.replace(/clasname/g,'tcscsac2');
                    }else if(list[a][xx[0]].length==8){
                        htmltable=htmltable.replace(/clasname/g,'tab-tree2');
                    }else if(list[a][xx[0]].length==12){
                        htmltable=htmltable.replace(/clasname/g,'tab-tree3');
                    }else if(list[a][xx[0]].length==16){
                        htmltable=htmltable.replace(/clasname/g,'tab-tree4');
                    }
                }else{
                    htmltable+="<td title='titlevalue'>"+list[a][xx[b]]+"</td>";
                    htmltable=htmltable.replace(/titlevalue/g,list[a][xx[b]]);
                }
            }
        }else{
            for(var b=0;b<xx.length-1;b++){
                htmltable+="<td title='titlevalue'>"+list[a][xx[b]]+"</td>";
                htmltable=htmltable.replace(/titlevalue/g,list[a][xx[b]]);
            }
        }
        if(flag == undefined){
            htmltable+="<td>"+
                        "<a ><button class=\"btn-small btn-blue\" onclick='updateppt(updateid)'>修改</button></a>\n"+
                        "<a ><button class=\"btn-small btn-del\" onclick='confirmdelppt(updateid)'>删除</button></a>"+
                        "</td>";

        }else if(flag =="1"){
            if(list[a].chanStatus=="启用" || list[a].userStatus=="启用"){
                htmltable+="<td>"+
                    "<a><button class=\"btn-small btn-green\" onclick='upda(updateid)'>修改</button></a>"+
                    "<a><button class=\"btn-small btn-del\" onclick='confirmfordel(updateid)'>删除</button></a>"+
                    "<a><button class=\"btn-small btn-red\" onclick='stop(updateid)'>禁用</button></a>"+
                    "</td>";
            }else if(list[a].chanStatus=="禁用" || list[a].userStatus=="禁用"){
                htmltable+="<td>"+
                    "<a><button class=\"btn-small btn-green\" onclick='upda(updateid)'>修改</button></a>"+
                    "<a><button class=\"btn-small btn-del\" onclick='confirmfordel(updateid)'>删除</button></a>"+
                    "<a><button class=\"btn-small btn-blue\" onclick='start(updateid)'>启用</button></a>"+
                    "</td>";
            }else{
                htmltable+="<td>"+
                    "<a><button class=\"btn-small btn-green\" onclick='upda(updateid)'>修改</button></a>"+
                    "<a><button class=\"btn-small btn-del\" onclick='confirmfordel(updateid)'>删除</button></a>"+
                    "<a><button class=\"btn-small btn-blue\" onclick='start(updateid)'>启用</button></a>"+
                    "<a><button class=\"btn-small btn-red\" onclick='stop(updateid)'>禁用</button></a>"+
                    "</td>";
            }

        }else if(flag=="2"){
            htmltable+="<td>"+
                            "<a><button class=\"btn-small btn-green\" onclick='upda(updateid)'>修改</button></a>"+
                            "<a><button class=\"btn-small btn-orange\" onclick='set(updateid)'>设置</button></a>"+
                            "<a><button class=\"btn-small btn-del\" onclick='confirmdel(updateid)'>删除</button></a>"+
                        "</td>";
        }else if(flag=="3"){
            htmltable+="<td>"+
                "<a><button class=\"btn-small btn-blue\" onclick='updat(updateid)'>修改</button></a>"+
                "<a><button class=\"btn-small btn-del\" onclick='confirmdel(updateid)'>删除</button></a>"+
                // "<a><button class=\"btn-small btn-green\" onclick='upda(updateid)'>预览</button></a>"+
                "<a><button class=\"btn-small btn-orange\" onclick='pub(updateid)'>推送</button></a>"+
                "</td>";
        }else if(flag=="4"){
            htmltable+="<td>"+
                "<a><button class=\"btn-small btn-green\" onclick='audit(updateid)'>审核</button></a>"+
                "</td>";
        }else if(flag=="5"){
            htmltable+="<td>"+
                "<a><button class=\"btn-small btn-blue\" onclick='look(updateid)'>查看</button></a>"+
                "</td>";
        }else if(flag=="6"){
        	if(list[a].templateCode!='index' && list[a].templateCode !='search'){
        	htmltable+="<td>"+
	            "<a ><button class=\"btn-small btn-blue\" onclick='updateppt(updateid)'>修改</button></a>\n"+
	            "<a ><button class=\"btn-small btn-del\" onclick='confirmdelppt2(updateid)'>删除</button></a>"+
	            "</td>";
        	}else{
        		htmltable+="<td>"+
	            "<a ><button class=\"btn-small btn-blue\" onclick='updateppt(updateid)'>修改</button></a>\n"+
	            "</td>";
        	}
	    }else if(flag=="7"){
            htmltable+="<td>"+
                "<a><button class=\"btn-small btn-green\" onclick='look(updateid)'>查看</button></a>"+
                "<a><button class=\"btn-small btn-del\" onclick='confirmdel(updateid)'>删除</button></a>"+
                "</td>";
        }
        htmltable+="</tr>";
        var reg = new RegExp( 'updateid' , "g" )
        htmltable = htmltable.replace(reg,list[a][xx[xx.length-1]]);
    }
    return htmltable;

}

function confirmdelppt(id) {
    var r=confirm("确认删除么");
    if (r==true)
    {
        delppt(id);
    }
    else
    {
        return;
    }

}
function confirmdel(id){
    var r=confirm("确认删除么");
    if (r==true)
    {
        del(id);
    }
    else
    {
        return;
    }
}

function confirmfordel(id){
    var r=confirm("在删除栏目时，会清空栏目下所有信息，确认继续么");
    if (r==true)
    {
        del(id);
    }
    else
    {
        return;
    }
}
function confirmdelppt2(id){
    var r=confirm("确认删除么");
    if (r==true)
    {
        delppt(id);
    }
    else
    {
        return;
    }

}
var zuidaxianshi;
var zongshumu;

function setMaxindex(result,page){
    zuidaxianshi=page;
    zongshumu=result.maxnum;
    $("#skipnum").val(pageindex);
    updatefenye();
    if(parseInt(result.maxnum)>page && parseInt(result.maxnum)%page >0){
        maxindexnum=parseInt(result.maxnum/page)+1;
    }else if(parseInt(result.maxnum)>page && parseInt(result.maxnum)%page ==0 ){
        maxindexnum=parseInt(result.maxnum/page);
    }else{
        maxindexnum=1;
    }
    huifu();
    $("#_totalPageSpan").text(maxindexnum);
}


function skippage(flag){        //进行分页分析的函数
    jiasuo();
    if(flag == 1){
        if(pageindex>1){
            pageindex-=1;
            $("#skipnum").val(pageindex);
            updatefenye();
        }else{
            //alert("当前已是起始页");
            huifu();
            return;
        }
        updatetable();
    }else if(flag == 2){
        if(pageindex==maxindexnum){
            //alert("当前已是最大页");
            huifu();
            return;
        }else{
            pageindex+=1;
            $("#skipnum").val(pageindex);
            updatefenye();
        }
        updatetable();
    }else if(flag == 3){
        if(pageindex==maxindexnum){
            //alert("当前已是最大页");
            huifu();
            return;
        }else{
            pageindex=maxindexnum;
            $("#skipnum").val(pageindex);
            updatefenye();
        }
        updatetable();
    }else if(flag == 0){
        if(pageindex==1){
            //alert("当前已是起始页");
            huifu();
            return;
        }else{
            pageindex=1;
            $("#skipnum").val(pageindex);
            updatefenye();
        }
        updatetable();
    }
}

function updatefenye(){
    if(pageindex==1){
        $("#maxnum").text("显示1到"+zuidaxianshi+"条记录，"+"共计"+zongshumu+"条记录");
    }else{
        $("#maxnum").text("显示"+(((pageindex-1)*zuidaxianshi)+1)+"到"+(pageindex*zuidaxianshi)+"条记录，"+"共计"+zongshumu+"条记录");
    }

}

function huifu(){
    $("#skipnum").removeAttr("disabled");
    $("#disa0").attr('onclick',"skippage(0);");
    $("#disa1").attr('onclick',"skippage(1);");
    $("#disa2").attr('onclick',"skippage(2);");
    $("#disa3").attr('onclick',"skippage(3);");
    $("#changePageSize").attr('onchange',"updatetable(1)");
}

function jiasuo(){
    $("#skipnum").attr("disabled", true);
    $("#disa0").removeAttr('onclick');
    $("#disa1").removeAttr('onclick');
    $("#disa2").removeAttr('onclick');
    $("#disa3").removeAttr('onclick');
    $("#changePageSize").removeAttr('onchange');
}




function blurFunc(){
    if($("#skipnum").val() == ""){
        alert("不可能空页码跳转");
        $("#skipnum").val(pageindex);
        return;
    }
    if($("#skipnum").val()>maxindexnum){
        alert("不存在那么多页");
        $("#skipnum").val(pageindex);
        return;
    }
    $("#skipnum").attr("disabled", true);
    $(".disa").removeAttr('onclick');
    pageindex=$("#skipnum").val();
    updatetable();
}



function showOldMessage(oldname,oldduixiang,filename){
    var oldHtml="<img src='/cms/image/fj2.gif' onclick='mg.pluUploader.smartDownloadByPlu(\"oldsavename\")' >" +
        "<a  style='TEXT-DECORATION:none;cursor:pointer;'  onclick='mg.pluUploader.smartDownloadByPlu(\"oldsavename\")'>" +
        "oldfilename</a><img src='/cms/image/del.gif' title=\"删除\" onclick=\"deleteold('duixiang')\" style=\"cursor: pointer;\" >";

    var reg = new RegExp( 'oldsavename' , "g" )
    oldHtml = oldHtml.replace(reg,oldname);
    if(filename!=undefined){
        oldHtml = oldHtml.replace(/oldfilename/g,filename);
    }else{
        oldHtml = oldHtml.replace(/oldfilename/g,oldname);
    }
    oldHtml = oldHtml.replace(/duixiang/g,oldduixiang);
    return oldHtml;

}

function deleteold(old){
    $("#"+old).css("display","none");
}


function addSelectOption(list,arr){
    var optm="";
    for(var a=0;a<list.length;a++){
        var temp="<option value='valuetemp'>nametemp</option>"
        temp=temp.replace(/valuetemp/g,list[a][arr[0]]);
        temp=temp.replace(/nametemp/g,list[a][arr[1]]);
        optm=optm+temp;
    }
    return optm;
}


function IsURL(str_url){
    var ty=str_url.substr(0,4)=="http";
    if (ty){
        return (true);
    }else{
        return (false);
    }
}

$.ajaxSetup( {
//设置ajax请求结束后的执行动作
    complete :
        function(XMLHttpRequest, textStatus) {
// 通过XMLHttpRequest取得响应头，sessionstatus
            var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
            if (sessionstatus == "TIMEOUT") {
                var win = window;
                while (win != win.top){
                    win = win.top;
                }
                win.location.href= XMLHttpRequest.getResponseHeader("CONTEXTPATH");
            }
        }
});