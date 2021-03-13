function sub() {
    $.post("/users/loginValidate",
        {
            uiName:$("#uiName").val(),
        },
        function(data,status){
            alert("数据: \n" + data + "\n状态: " + status);
            var obj = JSON.parse(data);
            window.location.replace("index.html?id="+obj.id+"&uiName="+obj.uiName+"&tenantId="+obj.tenantId);
        }
    );
}