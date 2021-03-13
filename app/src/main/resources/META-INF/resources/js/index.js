$(document).ready(function(){
    $("#username").text(getQueryVariable("uiName"));
    $('#dg').datagrid({
        url:'/users/queryApprovalList',
        queryParams: {
            uiName: getQueryVariable("uiName"),
            tenantId: getQueryVariable("tenantId"),
            uiId: getQueryVariable("id")
        },
        columns:[[
            {field:'pdName',title:'审批项目', width:180,
                formatter: function(value,row,index){
                    return '<a style="color:blue" href="/taskpage.html?startForm='+row.startForm+'&uiId='+getQueryVariable("id")+'&pdId='+row.id+'">'+row.pdName+'</a>';
                }
            }
        ]]
    });

    $('#dgtask').datagrid({
        url:'/users/queryUnTaskList',
        queryParams: {
            uiId: getQueryVariable("id")
        },
        columns:[[
            {field:'pdName',title:'待办事项名称', width:180,
                formatter: function(value,row,index){
                    return '<a style="color:blue" href="/taskpage.html?startForm='+row.bfUrl+'&uiId='+getQueryVariable("id")+'&pdId='+row.pdId+'&tiId='+ row.id +'&usertaskNo='+ row.usertaskNo +'&piBusinesskey='+ row.piBusinesskey+'&piId='+ row.piId +'">'+row.tiName+'</a>';
                }
            }
        ]]
    });

    $('#dgObtaintask').datagrid({
        url:'/users/queryUnObtainList',
        queryParams: {
            uiId: getQueryVariable("id")
        },
        columns:[[
            {field:'tiName',title:'可获取任务名称', width:180},
            {field:'opt',title:'操作',width:50,align:'center',
                formatter:function(value,row,index){
                     var btn = "<input type=\"submit\" value=\"获取\" onclick=\"obtainTask('"+row.id+"')\">";
                    //var btn = "<a οnclick=\"obtainTask('"+row.id+"');\">获取</a>";
                    return btn;
                }
            }
        ]]
    });

    $('#dgProcess').datagrid({
        url:'/users/queryProcessList',
        queryParams: {
            uiId: getQueryVariable("id")
        },
        columns:[[
            {field:'piName',title:'流程名称', width:180},
            {field:'piStarter',title:'流程发起人', width:180},
            {field:'createtime',title:'发起时间', width:180}
        ]]
    });

});

function getQueryVariable(variable){
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return(false);
}

function addLeave(){
    window.location.replace("leave.html?id="+obj.id+"&uiName="+obj.uiName);
}

function obtainTask(taskId) {
    $.post("/users/obtainTask",
        {
            uiId:getQueryVariable("id"),
            taskId:taskId
        },
        function(data,status){
            alert("数据: \n" + data + "\n状态: " + status);
            if(data === "1"){
                alert("成功");
                window.location.reload();
            }else{
                alert("失败");
            }
        }
    );
}