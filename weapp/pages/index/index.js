//index.js
//获取应用实例
var app = getApp()
Page({
  data: {
    motto: '欢迎来到TPS汽车服务',
    userInfo: {}
  },
  //事件处理函数
  bindViewTap: function() {
    wx.showLoading({
   title: '加载中'
 
  })
 

setTimeout(function(){
  wx.hideLoading()
},1000)

  wx.navigateTo({
      url: '../tb/tb',
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
        if (res.code) {
          //发起网络请求
          wx.request({
            url: 'https://32906079.jxggdxw.com/sertest/UserServlet',
            data: {
              code: res.code
            },
             success: function(cosRes) {
            //console.log(cosRes.data.names)
            console.log('aaaaaa')
            console.log(cosRes.code)
            console.log(cosRes.message)
            }
            
          })
        } else {
          console.log('获取用户登录态失败！' + res.errMsg)
        }
      }
    });
  }
})
