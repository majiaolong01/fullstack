import axios from 'axios'
import {message} from 'antd'
axios.defaults.withCredentials = true;
axios.interceptors.request.use(function(config) {
    const access_token=sessionStorage.getItem('accessToken');
   if(access_token)  config.headers.common["access_token"]=access_token;
    return config;
}, function(error) {

    return Promise.reject(error);
});
axios.interceptors.response.use(function(config) {
    if(config.data.code===401){
        message.error(config.data.message,2,()=>{window.location.replace("/");});

    }
    return config.data;
}, function(error) {
    // Do something with response error
    return Promise.reject(error);
});
export default {
    getPublicKey: () => {
        return axios.get('/demo1/publicKey')
    },
    login:data=>{
        return axios.post('/demo1/login',data);
    },
    getAllProduct:data=>{
        return  axios.post('/demo1/product/allproduct',data)
    }
}