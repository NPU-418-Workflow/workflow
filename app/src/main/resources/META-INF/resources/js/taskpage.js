$(document).ready(function(){
    $("#tiId").val(getQueryVariable("tiId"));
    $("#usertaskNo").val(getQueryVariable("usertaskNo"));
    $("#pdId").val(getQueryVariable("pdId"));
    $("#uiId").val(getQueryVariable("uiId"));
    $("#piBusinesskey").val(getQueryVariable("piBusinesskey"));
    $("#content").load(getQueryVariable("startForm"));
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