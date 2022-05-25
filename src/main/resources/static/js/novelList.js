function toReadChapter(nid) {
    alert("即将去往目录"+nid);
    window.location = "/novel/toChapterList?nid="+nid;
};

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
        window.location = "/user/bookshelf";
    }else{
        window.location = "/exit";
    }
}
const de = document.querySelector(".cDetails");
const signOut = document.querySelector(".signOut");
const currentUser = document.querySelector(".ccUser");
currentUser.addEventListener("mouseover", function () {
    de.style.display = "block";
    signOut.style.display = "block";
});
currentUser.addEventListener("click", function () {
    de.style.display = "none";
    signOut.style.display = "none";
});

function queryNovel() {

    const keyWords = document.getElementById("keyWords").value;
    const status = document.getElementById("status").value;

    if (status === '0'){
        alert("查找所有”"+ keyWords +"”")
        window.location = "/novel/query?keyWords="+keyWords;
    }else{
        alert("查找所有”"+ status +"”")
        window.location = "/novel/query?keyWords="+keyWords+"&&status="+status;
    }


}

function addNovel() {

    window.location= "/novel/toAddNovel";

}

function modifyNovel(id) {
    window.location = "/novel/toModifyNovel?nid="+id;
}

function removeNovel(id) {
    alert("删除小说成功！")
    var uid = document.getElementById("cUserId").value;
    window.location = "/novel/remove?nid="+id+"&uid="+uid;
}

function closeDiv() {
    const dlg = document.querySelector(".dlg2");
    dlg.style.display = "none";
}
