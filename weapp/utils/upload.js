//图片上传函数
var uploadurl='https://32906079.jxggdxw.com:8443/WeappServer/main'

function upload (Goodsaname,picturename,localfilepath){
   wx.uploadFile({
     url:uploadurl,  //开发者服务器URL
     filePath: localfilepath,  //上传文件的临时路径
     header: {           //http头
        'content-type':'application/json' 
        },
    name: Goodsaname,    //上传文件所对应的key
    formData: {           //除了上传文件以外，其他的上传信息
         picturename:picturename
        },
    success: function(uploadRes){
        var data = uploadRes.data
        console.log('success_upload')
        console.log(uploadRes)
        //do something
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
module.exports = upload