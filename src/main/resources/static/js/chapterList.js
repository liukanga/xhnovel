function toRead(cid) {
    alert("即将去往阅读"+cid);
    var next = false;
    window.location = "/novel/query/cid/"+cid+"/"+next;
}