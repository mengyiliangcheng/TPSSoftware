//index.js
//获取应用实例
var requsturl='https://32906079.jxggdxw.com:8443/WeappServer/DownloadInfo'
var app = getApp()
var A=[1,2,3,4,5]
var GoodsName=new Array()

Page({
  data: {
    motto: '欢迎来到TPS汽车服务',
    userInfo: {},
    Goodsname:[],
    Goodsinfo:[], 
  },
  //事件处理函数
  bindViewTap: function() {
    var that=this
    var Goodsname1=[]
    //wx.showLoading({
      //title: '加载中'
    //})
    wx.request({
    url: requsturl ,
    data: {
      command:'getgoodsinfo' 
    },
    method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
    header: {
     'content-type':'application/x-www-form-urlencoded'
   }, // 设置请求的 header
    success: function(res){ 
      console.log(res.data.goods.length)
      that.data.Goodsname.splice(0,that.data.Goodsname.length)
      that.data.Goodsinfo.splice(0, that.data.Goodsinfo.length)
      for (var i = 0; i < res.data.goods.length;i++){
        that.data.Goodsname.push(res.data.goods[i][0].name)
        that.data.Goodsinfo.push([res.data.goods[i][3].urls[0].url, res.data.goods[i][0].name + '\n\n价 格：' +
          res.data.goods[i][1].price + '\n排 量：' + res.data.goods[i][2].abstract]); 
        
      }
      console.log(that.data.Goodsname)
      console.log(that.data.Goodsinfo)
      app.data.GoodsInfo = that.data.Goodsinfo
      app.data.GoodsName = that.data.Goodsname
    }
  })
    wx.showLoading({
      title: '加载中',
    })
    setTimeout(function () {
      wx.hideLoading()
    }, 1000)
  wx.showLoading({
   title: '加载中'
  })
 
setTimeout(function(){
  wx.hideLoading()
},3000)
  wx.navigateTo({       //跳转到tb页面
      url: '../second/second',
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
