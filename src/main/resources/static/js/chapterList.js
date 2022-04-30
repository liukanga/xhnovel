function switchTo(value) {
    if(value===1){
        window.location = "/user/toUserList";
    }else if(value===2){
        window.location = "/user/toUserList?status="+2;
    }else if(value===3 || value===4){
        window.location = "/novel/query";
    }else if(value===5){
        window.location = "/comment/toCommentList";
    }else if(value===6){
        var uid = document.getElementById("cUserId").value;
        window.location = "/user/toHomePage?uid="+uid;
    }else if(value===7){
        var aid = document.getElementById("cUserId").value;
        window.location = "/novel/toNovelList/"+aid
    }else{
        window.location = "/user/loginPage"
    }
}

function toRead(cid) {
    alert("即将去往阅读"+cid);
    var next = false;
    window.location = "/novel/query/cid/"+cid+"/"+next;
}

window.onload = function(){
    var star = document.getElementById("star");
    var star_li = star.getElementsByTagName("li");
    var star_word = document.getElementById("star_word");
    var result = document.getElementById("result");
    var i=0;
    var j=0;
    var len = star_li.length;
    var word = ['1分','2分','3分',"4分","5分"]
    for(i=0; i<len; i++){
        star_li[i].index = i;
        star_li[i].onmouseover = function(){
            star_word.style.display = "block";
            star_word.innerHTML = word[this.index];
            for(i=0; i<=this.index; i++){
                star_li[i].className = "act";
            }
        }
        star_li[i].onmouseout = function(){
            star_word.style.display = "none";
            for(i=0; i<len; i++){
                star_li[i].className = "";
            }
        }
        star_li[i].onclick = function(){
            result.innerHTML = (this.index+1)+"分";
            star_word.style.display = "block";
            star_word.innerHTML = word[this.index];
            for(i=0; i<=this.index; i++){
                star_li[i].className = "act";
            }
        }
    }
}

function addNovelToBookshelf(id) {

    window.location = "/novel/addNovel?nid=" + id;

}