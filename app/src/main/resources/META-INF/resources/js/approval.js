$(document).ready(function(){
    $.post("/users/queryLeaveInfo",
        {
            piBusinesskey:$("#piBusinesskey",parent.document).val()
        },
        function(data,status){
            var obj = JSON.parse(data);
            $("#durations").text(obj.durations);
            $("#uiName").text(obj.uiName);
        }
    );
});

function sub() {
    $.post("/users/completeApproval",
        {
            pdId:$("#pdId",parent.document).val(),
            uiId:$("#uiId",parent.document).val(),
            tiId:$("#tiId",parent.document).val(),
            usertaskNo:$("#usertaskNo",parent.document).val(),
            piId:getQueryVariable("piId"),
            businessjudge:true
        },
        function(data,status){
            alert("数据: \n" + data + "\n状态: " + status);
            if(data === "1"){
                alert("成功");
            }else{
                alert("失败");
            }
        }
    );
}

function fail() {
    $.post("/users/completeApproval",
        {
            pdId:$("#pdId",parent.document).val(),
            uiId:$("#uiId",parent.document).val(),
            tiId:$("#tiId",parent.document).val(),
            usertaskNo:$("#usertaskNo",parent.document).val(),
            piId:getQueryVariable("piId"),
            businessjudge:false
        },
        function(data,status){
            alert("数据: \n" + data + "\n状态: " + status);
            if(data === "1"){
                alert("成功");
            }else{
                alert("失败");
            }
        }
    );
}

function getQueryVariable(variable){
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return(false);
}