function sub() {
    $.post("/users/personinfo",
        {
            pdId:$("#pdId",parent.document).val(),
            uiId:$("#uiId",parent.document).val(),
            tiId:$("#tiId",parent.document).val(),
            usertaskNo:$("#usertaskNo",parent.document).val(),
            businessdynamicassignee01:$("#businessdynamicassignee01").val()
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