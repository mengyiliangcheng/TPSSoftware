// pages/tb/tb.js
var requsturl = 'https://32906079.jxggdxw.com:8443/WeappServer/DownloadInfo'
var GoodsName='';

//var motto=new Array();
var app=getApp();
var namess=''
Page({
  data:{
        Goodsname:[],
        Goodsurl:[],
        Goodsinfo:[],    
    },
  onReady: function () {
    // 页面渲染完成
    this.setData({
      Goodsinfo: app.data.GoodsInfo,
      Goodsname: app.data.GoodsName
    })
    console.log(this.data.Goodsinfo)
    console.log(this.data.Goodsname)
  },
addnewgoods:function(){
    wx.showLoading({
    title: '加载中',
  })
    setTimeout(function(){
     wx.hideLoading()
},1000)

    wx.redirectTo({
      url: '../third/third'
    })

  },
  callphone:function(){
    wx.makePhoneCall({
    phoneNumber: '15002121792' //仅为示例，并非真实的电话号码
})
  },
  deletegoods:function(){

    wx.showLoading({
    title: '加载中',
     })
    setTimeout(function(){
     wx.hideLoading()
      },1000)

  wx.navigateTo({
      url: '../five/five'
      })

  },
  goodsview: function(res){
    var that=this
     // console.log(res)
     // console.log(res.currentTarget.id)
      var GoodsName=app.data.GoodsName[res.currentTarget.id]
      //console.log(GoodsName)
      wx.request({
        url: requsturl,
        data: {
          command:'getgoodinfo' ,
          name:GoodsName
        },
        method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
        header: {
            'content-type':'application/x-www-form-urlencoded'
          }, // 设置请求的 header
        success: function(res){
          // success
          console.log(res.data)
           console.log(res.data.good[3].urls.length)
          that.data.Goodsurl.splice(0,that.data.Goodsurl.length)
          for (var i = 0; i < res.data.good[3].urls.length;i++){
            that.data.Goodsurl.push(res.data.good[3].urls[i].url)
          }
          console.log(that.data.Goodsurl)
        }
      })
      app.data.GoodsUrl.splice(0, app.data.GoodsUrl.length)//清空原有数据
      app.data.GoodsUrl=that.data.Goodsurl
      //console.log(app.data.GoodsUrl)
      wx.showLoading({
           title: '加载中'
        })
 
      setTimeout(function(){
         wx.hideLoading()
        },1000)
       wx.navigateTo({       //跳转到tb页面
      url: '../four/four',
    })
  },
  
 onLoad: function (res) {
    console.log(res.A)
    console.log('onLoad')
    var that = this
    //调用应用实例的方法获取全局数据
    //app.getUserInfo(function(userInfo){
      //更新数据
     
    //console.log(app.data.GoodsName)
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