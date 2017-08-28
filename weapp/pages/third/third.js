// pages/third/third.js
var uploadfn = require('../../utils/upload.js')
var app = getApp()
var tempFilePaths
var newgoodsname
var goodsname
var newgoodsprice
var newgoodsabstract
var uploadurl ='https://32906079.jxggdxw.com:8443/WeappServer/UploadService'
//var uploadurl ='http://localhost:8080/WeappServer/UploadService'
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
     // wx.showLoading({
     //     title: '上传中',
     //   })
      wx.showToast({
        title: '当前点餐人数12人，已经符合配送要求',
        duration:90000
      })
      wx.uploadFile({    //上传第一张图片的同时上传图片信息
         url:uploadurl,  //开发者服务器URL
         filePath: res.tempFilePaths[0],  //上传文件的临时路径
         header: {           //http头
            'content-type':'application/json' 
         },
         formData: {           //除了上传文件以外，其他的上传信息
            goodsname:encodeURI(newgoodsname),
            goodsprice:encodeURI(newgoodsprice),
            goodsabstract:encodeURI(newgoodsabstract),
          },
           name: newgoodsname,    //上传文件所对应的key
         success: function(uploadRes){
            var data = uploadRes.data
            console.log(uploadRes)
            console.log(data)
            console.log('first_upload_success')
        //do something
             for(var i=1;i<res.tempFilePaths.length;i++){
                 uploadfn(newgoodsname,res.tempFilePaths[i])//调用图片上传函数 
            }
             wx.showLoading({
                  title: '上传成功',
              })
             setTimeout(function(){
                    wx.hideLoading()
               },750)
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
    },
    complete:function(){

      wx.showToast({
        title: 'toast',
      })
      setTimeout(function () {
        wx.hideLoading()
      }, 1000)
      wx.redirectTo({       //跳转到tb页面
        url: '../first/first',
      })
    }
})