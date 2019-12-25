# -
antd+react+axios+SSM+mysql+redis
前端使用create-react-app搭建项目
后端使用maven构建项目，技术主要为spring+springmvc+mybatis
数据存储 redis+mysql
服务器tomcat8,环境为jdk8
1.前端主要的问题是反向代理的配置：单个代理package.json里面配置;多个代理在setupProxy.js配置
const proxy = require('http-proxy-middleware');
const  proxyAddress=require('./service/proxyAddress')
module.exports = function(app) {
    proxyAddress.forEach(function(item,index){
        app.use(proxy(item.context, {
            target: item.target,
            secure: false,
            changeOrigin: true,
            pathRewrite: {
                ['^'+item.context]: "/"
            },
        }))
    })
   /* app.use(proxy('/api', {
        target: 'http://www.baidu.com',
        secure: false,
        changeOrigin: true,
        pathRewrite: {
            "^/api": "/"
        },
    }));
   
};
2.后端主要问题是HandlerInterceptor进行token拦截时，无法使用@Autowired实例化;
BeanFactoryfactory=WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
RedisUtil redisUtil = (RedisUtil) factory.getBean("redisUtil");
3.密码校验使用RSA进行密码校验；后台生成公钥，前端进行公钥加密，后端进行解密；校验通过生成token
