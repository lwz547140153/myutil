/**
 * js混淆 http://www.sojson.com/javascriptobfuscator.html
 */
;~function (global) {
  /**
   * src:原字符串
   * 一、加密步骤说明:
   * 1、src补位，src.length<10则src首尾各补一位base64随机字符,否则src首补三位base64随机字符
   * 如：①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳ =》ABC①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳
   * 2、src分组，src按index奇偶分为两组,index为偶的倒序，index为奇的分成相等个数的两组元素再各自倒序
   * ABC①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳
   * A C ② ④ ⑥ ⑧ ⑩ ⑫ ⑭ ⑯ ⑱ ⑳=>⑳ ⑱ ⑯ ⑭ ⑫ ⑩ ⑧ ⑥ ④ ② C A||
   *  B ① ③ ⑤ ⑦ ⑨ ⑪ ⑬ ⑮ ⑰ ⑲ => ⑦ ⑤ ③ ① B ⑨ ⑲ ⑰ ⑮ ⑬ ⑪ ||
   *                          ⑳⑦⑱⑤⑯③⑭①⑫B⑩⑨⑧⑲⑥⑰④⑮②⑬C⑪A||
   * 3、src递归，递归折半逆序
   *   ||⑳⑦⑱⑤⑯③⑭①⑫B⑩⑨⑧⑲⑥⑰④⑮②⑬C⑪A||
   *   ||A⑪C⑬②⑮④⑰⑥⑲⑧||⑨||⑩B⑫①⑭③⑯⑤⑱⑦⑳||
   *   ||⑧⑲⑥⑰④||⑮||②⑬C⑪A||⑨||⑳⑦⑱⑤⑯||③||⑭①⑫B⑩||
   *   ||④⑰⑥⑲⑧||⑮||A⑪C⑬②||⑨||⑯⑤⑱⑦⑳||③||⑩B⑫①⑭||
   *   ||⑰④||⑥||⑧⑲||⑮||⑪A||C||②⑬||⑨||⑤⑯||⑱||⑳⑦||③||B⑩||⑫||⑭①||
   *   ||⑰④⑥⑧⑲⑮⑪AC②⑬⑨⑤⑯⑱⑳⑦③B⑩⑫⑭①
   * 4、base64编码
   *   4pGw4pGj4pGl4pGn4pGy4pGu4pGqQUPikaHikazikajikaTika/ikbHikbPikabikaJC4pGp4pGr4pGt4pGg
   * 二、解密步骤说明:
   * 1、base64解码
   * decode('4pGw4pGj4pGl4pGn4pGy4pGu4pGqQUPikaHikazikajikaTika/ikbHikbPikabikaJC4pGp4pGr4pGt4pGg')
   * ⑰④⑥⑧⑲⑮⑪AC②⑬⑨⑤⑯⑱⑳⑦③B⑩⑫⑭①
   * 2、src递归，递归分组合并逆序
   *   ⑰④||⑥||⑧⑲||⑮||⑪A||C||②⑬||⑨||⑤⑯||⑱||⑳⑦||③||B⑩||⑫||⑭①
   *                          || ||
   *            || ||                       || ||
   *     || ||         || ||         || ||         || ||
   *   ⑰④||⑥||⑧⑲||⑮||⑪A||C||②⑬||⑨||⑤⑯||⑱||⑳⑦||③||B⑩||⑫||⑭①
   *   ④⑰||⑥||⑲⑧||⑮||A⑪||C||⑬②||⑨||⑯⑤||⑱||⑦⑳||③||⑩B||⑫||①⑭
   *   ④⑰⑥⑲⑧||⑮||A⑪C⑬②||⑨||⑯⑤⑱⑦⑳||③||⑩B⑫①⑭
   *   ⑧⑲⑥⑰④||⑮||②⑬C⑪A||⑨||⑳⑦⑱⑤⑯||③||⑭①⑫B⑩
   *   ⑧⑲⑥⑰④⑮②⑬C⑪A||⑨||⑳⑦⑱⑤⑯③⑭①⑫B⑩
   *   A⑪C⑬②⑮④⑰⑥⑲⑧||⑨||⑩B⑫①⑭③⑯⑤⑱⑦⑳
   *   A⑪C⑬②⑮④⑰⑥⑲⑧⑨⑩B⑫①⑭③⑯⑤⑱⑦⑳
   *   ⑳⑦⑱⑤⑯③⑭①⑫B⑩⑨⑧⑲⑥⑰④⑮②⑬C⑪A
   * 3、src分组，src按index奇偶分为两组,index为偶的倒序，index为奇的分成相等个数的两组元素再各自倒序
   *   ⑳⑦⑱⑤⑯③⑭①⑫B⑩⑨⑧⑲⑥⑰④⑮②⑬C⑪A
   *   ⑳ ⑱ ⑯ ⑭ ⑫ ⑩ ⑧ ⑥ ④ ② C A=>A C ② ④ ⑥ ⑧ ⑩ ⑫ ⑭ ⑯ ⑱ ⑳||
   *    ⑦ ⑤ ③ ① B ⑨ ⑲ ⑰ ⑮ ⑬ ⑪ => B ① ③ ⑤ ⑦ ⑨ ⑪ ⑬ ⑮ ⑰ ⑲ ||
   *                            ABC①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳||
   * 4、src去补位，src.length<12则src首尾各去一位字符,否则src首去三位字符
   *   ABC①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳=>①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳
   *
   *   ④⑰⑥⑲⑧||⑮||A⑪||C||⑬②||⑨||⑯⑤||⑱||⑦⑳||③||⑩B||⑫||①⑭
   */
  global.Base64||(global.Base64={_keyStr:"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",encode:function(e){var t="";var n,r,i,s,o,u,a;var f=0;e=Base64._utf8_encode(e);while(f<e.length){n=e.charCodeAt(f++);r=e.charCodeAt(f++);i=e.charCodeAt(f++);s=n>>2;o=(n&3)<<4|r>>4;u=(r&15)<<2|i>>6;a=i&63;if(isNaN(r)){u=a=64}else if(isNaN(i)){a=64}t=t+this._keyStr.charAt(s)+this._keyStr.charAt(o)+this._keyStr.charAt(u)+this._keyStr.charAt(a)}return t},decode:function(e){var t="";var n,r,i;var s,o,u,a;var f=0;e=e.replace(/[^A-Za-z0-9+/=]/g,"");while(f<e.length){s=this._keyStr.indexOf(e.charAt(f++));o=this._keyStr.indexOf(e.charAt(f++));u=this._keyStr.indexOf(e.charAt(f++));a=this._keyStr.indexOf(e.charAt(f++));n=s<<2|o>>4;r=(o&15)<<4|u>>2;i=(u&3)<<6|a;t=t+String.fromCharCode(n);if(u!=64){t=t+String.fromCharCode(r)}if(a!=64){t=t+String.fromCharCode(i)}}t=Base64._utf8_decode(t);return t},_utf8_encode:function(e){e=e.replace(/rn/g,"n");var t="";for(var n=0;n<e.length;n++){var r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r)}else if(r>127&&r<2048){t+=String.fromCharCode(r>>6|192);t+=String.fromCharCode(r&63|128)}else{t+=String.fromCharCode(r>>12|224);t+=String.fromCharCode(r>>6&63|128);t+=String.fromCharCode(r&63|128)}}return t},_utf8_decode:function(e){var t="";var n=0;var r=c1=c2=0;while(n<e.length){r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r);n++}else if(r>191&&r<224){c2=e.charCodeAt(n+1);t+=String.fromCharCode((r&31)<<6|c2&63);n+=2}else{c2=e.charCodeAt(n+1);c3=e.charCodeAt(n+2);t+=String.fromCharCode((r&15)<<12|(c2&63)<<6|c3&63);n+=3}}return t}});
  var swap = function(arr,i,j){
    var t = arr[i];
    arr[i]=arr[j];
    arr[j]=t;
  };
  var reverse = function(arr,i,j,k){
    k||(k=1);
    while (i<j){
      swap(arr,i,j);
      i+=k;
      j-=k;
    }
    return (j - i)?[j,i]:[j-1,i+1];
  };
  var encrypt0 = function(arr,start,end) {
    if (start>=end) {
      //超出
      return;
    }
    var v = reverse(arr,start,end);
    encrypt0(arr,start,v[0]);
    encrypt0(arr,v[1],end);
  };
  var encrypt = function(arr) {
    encrypt0(arr,0,arr.length-1);
    return arr.join("");
  };
  var get_random_str = function(len){
    var $chars = global.Base64._keyStr;
    var factor = $chars.length-3+1;
    var str="";
    for (var i = 0;i<len;i++){
      str+=$chars[Math.floor(Math.random()*factor)]
    }
    return str;
  };
  var reverse2 = function (str) {
    var arr = str.split("");
    var len = arr.length;
    var len1= arr.length;
    if (len%2){
      len1--;
    }else{
      len--;
    }
    var i= 0,j=len-1;
    reverse(arr,i, j,2);
    i = 1,j = len1-1;
    var count = (i+j)/2;
    var e0 = null;
    var s0 = null;
    if (count%2){
      e0=count-2;
      s0 = count+2;
    } else{
      e0 = count-1;
      s0 = count+1;
    }
    reverse(arr,i,e0,2);
    reverse(arr,s0,j,2);
    return arr;
  };
  global.encrypt||(global.encrypt=function (str) {
    if (!str) {
      return str;
    }
    var len = str.length-10;
    if (len<0){
      str = get_random_str(1)+str+get_random_str(1);
    }else{
      str = get_random_str(3)+str;
    }
    return global.Base64.encode(encrypt(reverse2(str)));
  });
}(this);
