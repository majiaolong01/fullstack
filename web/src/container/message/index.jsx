import React, {Component} from 'react'
import './index.scss'
import { Input,Button } from 'antd';
import $http from '../../service/config'

const { TextArea } = Input;
class Message extends Component {
    constructor(props) {
        super(props);
        this.state = {
                message:''
        }

    }
    componentWillMount() {
        this.initWebSocket();
    }


    initWebSocket=()=>{
        this.ws = new WebSocket("ws://127.0.0.1:8080/demo1/websocket/test");
        let _this=this;
        this.ws.onopen = function(evt) {
            console.log("Connection open ...");
            _this.ws .send("Hello WebSockets!");
        };

        this.ws.onmessage = function(evt) {
            console.log( "Received Message: " + evt.data);
            //_this.ws.close();
        };

        this.ws.onclose = function(evt) {
            console.log("Connection closed.");
        };
    }

    sendMessage=message=>{
          this.ws.send('猪八戒娶媳妇');
    }
    setMessage=e=>{
          this.setState({[e.target.name]:e.target.value})
    }

    render() {
        const {message}=this.state;
        return <div className="container">
                   <div className="message-box">
                       <div className="item">
                           <TextArea  rows={10} name="message" value={message} onChange={e=>this.setMessage(e)}/>
                       </div>
                       <Button type="primary" onClick={()=>this.sendMessage()}>发送</Button>
                   </div>
              </div>
    }
}

export default Message

  
