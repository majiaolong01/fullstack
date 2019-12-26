import React, {Component} from 'react'
import {connect} from 'react-redux'
import {JSEncrypt} from 'jsencrypt'
import {login} from '../../redux/login.reducer'
import $http from "../../service/config";
import './index.scss'
import {Input,Button,message} from 'antd'
@connect(
    state=>state.user
)
class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username:'',
            password:'',
            publicKey:''
        }

    }
    componentDidMount() {
        this.getPublicKey();
    }

    handleChange=e=>{
        this.setState({
            [e.target.name]:e.target.value
        })
    }

    login=()=>{
        const {username,password}=this.state;
        const encrypt = new JSEncrypt();
        encrypt.setPublicKey(this.state.publicKey);
        const param={
            username,
            password:encrypt.encrypt(password)
        }
        $http.login(param).then(res=>{
            if(res.code===200){
                message.success(res.message);
                const {history,dispatch}=this.props;
                window.sessionStorage.setItem("accessToken",res.accessToken);
                dispatch({type:"LOGIN",payload:{...res.data,accessToken:res.accessToken}});
                this.props.history.push({pathname:'/product'});
            }else{
                message.error(res.message)
            }
        })
    }

    getPublicKey=()=>{
        $http.getPublicKey().then(res=>{
            //console.log(res.data);
             if(res.code===200){
                  this.setState({
                      publicKey:res.data.publicKeyStr
                  })
             }
        })
    }

    render() {
        const {username,password}=this.state;
        return <div className="container">
                      <div className="login-box">
                          <div className="item">
                              <Input size="large" value={username} name="username" onChange={e=>this.handleChange(e)} placeholder="请输入用户名" /><br />

                          </div>
                          <div className="item">
                              <Input size="large" value={password} name="password" type="password" onChange={e=>this.handleChange(e)} placeholder="请输入密码" />
                          </div>
                          <Button type="primary" size="large" block onClick={()=>this.login()} >
                             提交
                          </Button>
                      </div>
               </div>
    }
}

export default Login

  
