/**
 * 最终上传到cos的URL
 * 把以下字段配置成自己的cos相关信息，详情可看API文档 https://www.qcloud.com/document/product/436/6066
 * REGION: cos上传的地区
 * APPID: 账号的appid
 * BUCKET_NAME: cos bucket的名字
 * DIR_NAME: 上传的文件目录
 */
//var cosUrl = "https://" + "sh" + ".file.myqcloud.com/files/v4/" + "1253560901" + "/" + "tps" 
//填写自己的鉴权服务器地址
//var cosSignatureUrl = 'https://32906079.jxggdxw.com'
//var cosSignatureUrl = 'https://32906079.jxggdxw.com:8443/sertest/UserServlet' 
var cosSignatureUrl = 'https://32906079.jxggdxw.com:8443/sertest/UserServlet' 
/**
 * 上传方法
 * filePath: 上传的文件路径
 * fileName： 上传到cos后的文件名
 */
function upload(filePath, fileName) {

    // 鉴权获取签名
    wx.request({
        url: cosSignatureUrl,
        method:'POST',
        data:{
            code:'',
            message:''
        },
        header: {
          'content-type': 'json'
        },
        success: function(cosRes) {
            //console.log(cosRes.data.names)
           console.log(cosRes.data.code)
           //console.log(cosRes.head)
            wx.showLoading({
             title: '访问成功',
            })

            setTimeout(function(){
                       wx.hideLoading()
                    },4000)
            // 签名
            var signature = cosRes.data

            // 头部带上签名，上传文件至COS
          wx.uploadFile({
               url:                              "https://32906079.jxggdxw.com:8443/sertest/show",
                filePath: filePath,
               header: {
                    'content-type':'application/json' 
                },
               name: 'TB',
                formData: {
                    op: 'upload'
                },
                success: function(uploadRes){
                    var data = uploadRes.data
                    console.log('uploadRes', uploadRes)
                    //do something
                    wx.showLoading({
                       title: '上传成功',
                     })

                    setTimeout(function(){
                       wx.hideLoading()
                    },4000)
                 },
                fail: function(e) {
                    console.log('e', e)
                    wx.showLoading({
                       title: '上传失败',
                     })

                    setTimeout(function(){
                       wx.hideLoading()
                    },4000)
                }
            }) 
        },
        fail: function(r){
            wx.showLoading({
             title: '访问失败',
            })

            setTimeout(function(){
                       wx.hideLoading()
                    },4000)
            console.log('r', r)
        }
    })
}

module.exports = upload