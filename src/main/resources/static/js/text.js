var content = document.getElementById("content");
var num = document.getElementById("num").value;
window.onload = function () {
    alert(num)
    var HTMLstr = '';
    fetch("/uiuitest?num="+num,{
        method: "GET"
        }
    )
        .then(function (response,) {
            return response.json();
        })
        .then(function (result) {
            var list = result.data;
            for(var i=0;i<list.length;i++){
                HTMLstr += '<p style="text-indent:2em;">' + list[i] + '</p>';
            }
            content.innerHTML = HTMLstr;
        })
}

content.onload = {

}