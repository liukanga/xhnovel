function toNovel(nid) {
    window.location = "/novel/toChapterList?nid="+nid;
}

function removeNovel(nid, uid) {
    window.location = "/user/removeBookshelf/"+uid+"/"+nid;
}

function toNovelList() {
    window.location = "/novel/query";
}