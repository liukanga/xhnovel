window.onload = function () {
    var content = document.getElementById("content");
    var HTMLstr = '';
    fetch("/uiuitest",{
            method: "GET"
        }
    )
        .then(function (response) {
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
//下一章
var nextBtn = document.getElementById("nextChapter");
nextBtn.addEventListener("click",function () {
    var cid = document.getElementById("id").value;
    cid++;
    var next = true;
    self.location = "/novel/query/cid/"+cid+"/"+next;

});
//上一章
var preBtn = document.getElementById("preChapter");
preBtn.addEventListener("click",function () {
    var cid = document.getElementById("id").value;
    cid--;
    var next = true;
    self.location = "/novel/query/cid/"+cid+"/"+next;
});

//目录
var nList = document.getElementById("nList");
nList.addEventListener("click",function () {
    var nid = document.getElementById("nid").value;
    window.location = "/novel/toChapterList?nid="+nid;
});


var timer  = null;
box.onclick = function(){
    cancelAnimationFrame(timer);
    //获取当前毫秒数
    var startTime = +new Date();
    //获取当前页面的滚动高度
    var b = document.body.scrollTop || document.documentElement.scrollTop;
    var d = 500;
    var c = b;
    timer = requestAnimationFrame(function func(){
        var t = d - Math.max(0,startTime - (+new Date()) + d);
        document.documentElement.scrollTop = document.body.scrollTop = t * (-c) / d + b;
        timer = requestAnimationFrame(func);
        if(t == d){
            cancelAnimationFrame(timer);
        }
    });
}

function toSwitch(){
    var aid = document.getElementById("authorId").value;

}
