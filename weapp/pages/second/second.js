// pages/tb/tb.js
var app=getApp()
Page({
  data:{
    motto1:'奥迪 A4L\n价 格：40~60万\n排 量：2.0T',
    motto2:'奥迪 S5\n价 格：30~50万\n排 量：2.2T',
    motto3:'奥迪 A6\n价 格：30~50万\n排 量：2.2T',
    userInfo: {}
  },
  photo: function() {
    wx.showLoading({
  title: '加载中',
  })

  setTimeout(function(){
    wx.hideLoading()
  },1000)
  wx.previewImage({
  //current: 'http://123.206.126.162/picture/1.png', // 当前显示图片的http链接
  urls: ['http://32906079.jxggdxw.com/picture/4.png',       'http://32906079.jxggdxw.com/picture/5.png',
  'http://32906079.jxggdxw.com/picture/6.png',
  'http://32906079.jxggdxw.com/picture/7.png',
  'http://32906079.jxggdxw.com/picture/8.png',
  'http://32906079.jxggdxw.com/picture/9.png'] // 需要预览的图片http链接列表
}) 
  },
   photo1: function() {
     wx.showLoading({
  title: '加载中',
  })

setTimeout(function(){
  wx.hideLoading()
},1000)
wx.previewImage({
  //current: 'http://123.206.126.162/picture/1.png', // 当前显示图片的http链接
  urls: ['http://32906079.jxggdxw.com/picture/4.png',      'http://32906079.jxggdxw.com/picture/5.png',
  'http://32906079.jxggdxw.com/picture/6.png',
  'http://32906079.jxggdxw.com/picture/7.png',
  'http://32906079.jxggdxw.com/picture/8.png',
  'http://32906079.jxggdxw.com/picture/9.png'] // 需要预览的图片http链接列表
})
   },
photo2: function() {
     wx.showLoading({
  title: '加载中',
  })

setTimeout(function(){
  wx.hideLoading()
},1000)
  },
addnewgoods:function(){
    wx.showLoading({
    title: '加载中',
  })

    setTimeout(function(){
     wx.hideLoading()
},1000)

  wx.navigateTo({
      url: '../third/third'
    })

  },
  callphone:function(){
    wx.makePhoneCall({
    phoneNumber: '15002121792' //仅为示例，并非真实的电话号码
})
  },


  onLoad: function () {
    console.log('onLoad')
    var that = this
    //调用应用实例的方法获取全局数据
    app.getUserInfo(function(userInfo){
      //更新数据
      that.setData({
        userInfo:userInfo
      })
    })
  },
   onLaunch: function() {
    wx.login({
      success: function(res) {
        //console.log('获取用户登录态成功！' )
        if (res.code) {
          //发起网络请求
          wx.request({
            url: 'https://test.com/onLogin',
            data: {
              code: res.code
            }
          })
        } else {
          console.log('获取用户登录态失败！' + res.errMsg)
        }
      }
    });
  }
  
})