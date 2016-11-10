<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<%@ include file="/WEB-INF/views/include/static.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>网站博客</title>
</head>
<script type="text/javascript">
$(function() {
	
	loadBlog(1,15);
	//批量删除，全选，全不选
// 	   $("input[name='chkList']").attr("checked",true);
// 	      var num=0;
// 	     $("input[name='chkAll']").click(function(){
// 		 	 if(num%2==0){
// 				  $("input[name='chkList']").attr("checked",true);
// 			  }else{
// 				 $("input[name='chkList']").attr("checked",false);
// 		 	 }
// 			  num=num+1;
// 		}); 
	 
});

function clearCondition(){
	$("#inforForm")[0].reset();  
	//加载页面数据
	loadBlog(1,15);

}

function loadBlog(nowPage,rows) {
	   $("#page").val(nowPage);
	   $("#rows").val(rows);
	      //请求后台数据
		  $.post("<%=path%>/simple/blog/query",$("#inforForm").serialize(),function(data){
			  $("#infoTbody").empty();
			  if(null!=data.blogs){
				 var first=""; var id="";var author="";var title="";var from="";var option="";
				  for(var i=0;i<data.blogs.length;i++){
					  first = "<td><input name='chkAll' type='checkbox' />";
					  id = "<td>"+data.blogs[i].id+"</td>";
					  author = "<td>"+data.blogs[i].author+"</td>";
					  title = "<td style='width:400'><a target='_blank' href='"+data.blogs[i].url+"'>"+data.blogs[i].title+"</a></td>";
					  sid = "<td>"+getFrom(data.blogs[i].sid)+"</td>";
					  option = "<td><a>编辑</a>&nbsp;&nbsp;&nbsp;<a>修改</a>&nbsp;&nbsp;&nbsp;<a>删除</a></td>";
					  var HTML ="<tr>"+first+id+author+title+sid+option+"</tr>";
					  $("#infoTbody").append(HTML);
				  }
					$("#tipino").html("<strong style='color:#686868;'>该时间段内共有</strong><span>"+data.pageUtil.totalRows+"</span><strong style='color:#686868'>条符合您查询条件的记录</strong></span>");	
					initPage(data.pageUtil.page, data.pageUtil.totalPage, data.pageUtil.totalRows,data.pageUtil.rows, loadBlog);
			  }else{
					$("#tipino").html("<strong style='color:#686868;'>该时间段内共有</strong><span>0</span><strong style='color:#686868'>条符合您查询条件的记录</strong></span>");	
			  }
		  });
	
}
	//删除  
function deleteInfor(id){
	$.dialog('confirm','提示','您确认要删除该条信息嘛？',0,function(){ 
		$.post("<%=path%>/job/notice/delete2",{"id2":id},function(data){
			if(data.message=="success"){
				$("#mesgInfor span").empty().html("操作成功");
				$("#messageBox").hDialog({title:"温馨提示", autoShow: true,height: 108,width:310}); 
			//重新加载
				loadNotice(kkpager.pno,15);
				$("#showBusy").fadeOut();
			}else{
				$("#mesgInfor2 span").empty().html(data);
				$("#messageBox2").hDialog({title:"温馨提示", autoShow: true,height: 108,width:350}); 
		
			}
			$.closeDialog(); 
		})
	});
}
//批量删除
function de() {
    var ids =document.getElementsByName("chkList");
    var result = "";
        if (typeof (ids.length) != "undefined") {
        	for ( var i = 0; i < ids.length; i++) {
                if (ids[i].checked) {
                    result += ids[i].value+","
                }
            }
            result = result.substring(0,result.length-1);
        } 
    
    if (result == "") {
        alert("请选择要删除的记录");
    } else {
    	$.dialog('confirm','提示','您确认要删除该条信息嘛？',0,function(){ 
    		
    	 $.post("<%=path%>/job/notice/delete2",{"id2":result},function(data){
			if(data.message=="success"){
				$("#mesgInfor span").empty().html("操作成功");
				$("#messageBox").hDialog({title:"温馨提示", autoShow: true,height: 108,width:310}); 
		         //重新加载
				loadNotice(kkpager.pno,15);
				$("#showBusy").fadeOut();
			}else{
				$("#mesgInfor2 span").empty().html(data);
				$("#messageBox2").hDialog({title:"温馨提示", autoShow: true,height: 108,width:350}); 
		
			}
			$.closeDialog(); 
		})
    	});
    	
    }
 
}


</script>
<body>
    <!-- main -->
    <div class="main">
        <div class="tab-glzx">
            <ul>
                <li><a class="cur"  href="Seller_Infostatistics.html" target="_self">博客列表</a></li>
            </ul>
        </div>
        <div id="showBusy"></div>
        <div class="form form5">
        <form action="" method="post" id="inforForm">
				
                <input type="hidden" name="page" id="page"/>
				<input type="hidden" name="rows" id="rows"/>
				<input type="hidden"  class="dialog" name="showInfor" id="showInfor"/>
				
            <div class="item item_1">
            	<span class="label">博客来源：</span>
                <select class="txt txt_2" style="height:auto; padding:4px 5px;" name="sid">
                    <option value=""  selected="selected">--------</option>
                    <option value="1">51CTO</option>
                    <option value="2">CSDN</option>
                    <option value="3">博客园</option>
                </select>
            </div>
            <div class="item item_1"><span class="label">作者：</span><input type="text" class="txt txt_2" placeholder="可模糊查询" name="author"/></div>
            <div class="item item_1"><span class="label">标题：</span><input type="text" class="txt txt_2" placeholder="可模糊查询" name="title"/></div>
            <div class="item item_1"><span class="label">链接：</span><input type="text" class="txt txt_2" placeholder="可模糊查询" name="url"/></div>
            <div class="find item_1" style="margin-left:60px;">
				<a style="margin-right:15px;" href="javascript:void(0)"  onclick="loadBlog(kkpager.pno,15)">搜索</a>
				<a href="javascript:void(0)"  onclick="clearCondition();">清空</a>
			</div><br />
<!--             <div class="item item_1"> -->
<!--                 <span class="label">置顶：</span> -->
<!--                 <span class="block"><input type="radio" id="lististop_1" name="ifding"  value="1"/><label for="lististop_1">是</label></span> -->
<!--                 <span class="block"><input type="radio" id="lististop_2" name="ifding" value="0"/><label for="lististop_2">否</label></span> -->
<!--             </div> -->
<!--                 <div class="item"> -->
<!-- 					<span class="label">录入时间：</span> <span class="block"> -->
<!-- 					<input type="radio" id="date_2" name="timeNum"  value="1"/> -->
<!-- 					<input  -->
<!-- 						type="text" disabled="disabled" class="Wdate" type="text" id="startDate" name="startDate"	onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" /> -->
<!-- 					 到 <input id="endDate" name="endDate" type="text" class="Wdate" type="text" disabled="disabled" -->
<!-- 						onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" /></span>  -->
<!-- 					<span class="block"><input type="radio" id="date_1" name="timeNum" value="2"/> -->
<!-- 						<label for="date_1">昨天</label></span>  -->
<!-- 					<span class="block"><input type="radio" id="date_2" name="timeNum"  value="3"/><label -->
<!-- 						for="date_2">最近一周</label></span>  -->
<!-- 					<span class="block"><input type="radio" id="date_3" name="timeNum"  value="4"/><label for="date_3">最近一月</label></span> -->
<!-- 					<div class="find item_1"> -->
<!-- 						<a style="margin-right:15px;" href="javascript:void(0)"  onclick="loadNotice(kkpager.pno,10)">搜索</a> -->
<!-- 						<a href="javascript:void(0)"  onclick="clearCondition();">清空</a> -->
<!-- 					</div> -->
<!-- 				</div> -->
					
				</form>
<!--             <div class="item"> -->
<!--                 <div class="op_area"> -->
<!--                     <a href="javascript:void(0);" class="del_btn" onclick="de()">批量删除</a> -->
<!--                 </div> -->
<!--             </div> -->
        </div>
        <table class="tbl_1">
            <tr class="nav_t">
                <th style='width:50'><input name="chkAll" type="checkbox" /></th>
                <th style='width:50'>编号</th>
                <th style='width:200'>作者</th>
                <th style='width:400'>标题</th>
                <th style='width:100'>来源</th>
                <th style='width:300'>操作</th>
            </tr>
             <tbody id="infoTbody">
                </tbody>
             </table>
            
           <div class="page">
				<span class="tips" id="tipino"></span>
       	<div style="width: 800px; margin: 0 auto;">
					<div id="kkpager"></div>
				</div>
			</div>
    </div>
   
</body>
</html>