$(document).ready(function(){
    $.post("/users/queryLeaveInfo",
        {
            piBusinesskey:getQueryVariable("piBusinesskey")
        },
        function(data,status){
            var obj = JSON.parse(data);
            $("#durations").text(obj.durations);
            $("#uiName").text(obj.uiName);
        }
    );
});

function sub() {
    $.post("/users/completedRead",
        {
            pdId:$("#pdId",parent.document).val(),
            uiId:$("#uiId",parent.document).val(),
            tiId:$("#tiId",parent.document).val(),
            usertaskNo:$("#usertaskNo",parent.document).val()
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