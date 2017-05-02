// pages/third/third.js
var uploadfn = require('../../utils/upload.js')
var app = getApp()
const ctx = wx.createCanvasContext('myCanvas')
var initData = 'this is first line\nthis is second line'
var extraLine = []
var tempFilePaths
var newgoodsname
var goodsname
var newgoodsprice
var newgoodsabstract
Page({
  data:{},
  newgoodsname:function(e){
    newgoodsname = e.detail.value
    console.log(newgoodsname)

  },
  newgoodsprice:function(e){
    newgoodsprice = e.detail.value
    console.log(newgoodsprice)
  },
  newgoodsabstract:function(e){
    newgoodsabstract = e.detail.value
    console.log(newgoodsabstract)
  },
addpicture:function(){
    wx.chooseImage({
    count: 9, // 默认9
    sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
    sourceType: ['album'], // 可以指定来源是相册还是相机，默认二者都有
       
    success: function (res) {
    // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
      console.log(res.tempFilePaths.length)
      var filname='picture'
      for(var i=0;i<res.tempFilePaths.length;i++){
         uploadfn(newgoodsname,newgoodsprice,newgoodsabstract, res.tempFilePaths[i])   //调用图片上传函数
            }
          }
      })
    }
})