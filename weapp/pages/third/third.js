// pages/third/third.js
var uploadfn = require('../../utils/conf.js')
var app = getApp()
const ctx = wx.createCanvasContext('myCanvas')
var initData = 'this is first line\nthis is second line'
var extraLine = []
var tempFilePaths
var newgoodsname
var goodsname
Page({
  data:{},
  newgoodsname:function(e){
    newgoodsname = e.detail.value
    //console.log(newgoodsname)

  },
  newgoodsprice:function(e){
    var newgoodsprice = e.detail.value
    console.log(newgoodsprice)
  },
  newgoodsabstract:function(e){
    var newgoodsabstract = e.detail.value
    console.log(newgoodsabstract)
  },
addpicture:function(){
 
    wx.chooseImage({
    count: 1, // 默认9
    sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
    sourceType: ['album'], // 可以指定来源是相册还是相机，默认二者都有
       
    success: function (res) {
    // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
      console.log(res.tempFilePaths[0])
      var filname='picture'
      var filepath= res.tempFilePaths[0] 
       //console.log(tempFilePaths)
      console.log(filname)
      //uploadfn(filepath,filname)
      wx.uploadFile({
        url:                              "https://32906079.jxggdxw.com:8443/WeappServer/main",  //开发者服务器URL
        filePath: filepath,  //上传文件的临时路径
        header: {           //http头
           'content-type':'application/json' 
              },
        name: newgoodsname,    //上传文件所对应的key
       
        formData: {           //除了上传文件以外，其他的上传信息
           op: 'upload',
           goodsname:newgoodsname
          },
        
        success: function(uploadRes){
           var data = uploadRes.data

           console.log(goodsname)
           console.log(data)
           console.log('uploadRes', uploadRes)
                    //do something
            wx.showLoading({
                title: '上传成功',
               })
            setTimeout(function(){
                 wx.hideLoading()
                 },1000)
                 },
            fail: function(e) {
                console.log('e', e)
                wx.showLoading({
                  title: '上传失败',
                  })
             setTimeout(function(){
                 wx.hideLoading()
                   },1000)
                }
            }) 
          }
      })
    }
})