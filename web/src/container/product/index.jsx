import React, {Component} from 'react'
import $http from "../../service/config";
import './index.scss'
import {connect} from "react-redux";
@connect(
    state=>state.user
)
class Product extends Component {
    constructor(props) {
        super(props);
        this.state = {
            param:{
                page:1,
                current:5
            }

        }

    }

    componentDidMount() {
       // console.log(this.props)
        const {param}=this.state;
        this.getAllProduct(param);
    }

    getAllProduct=param=>{
        $http.getAllProduct(param).then(res=>{
            if(res.code===200){
                console.log(res.data);
            }
        })
    }

    render() {
        return <div className="product">Product</div>
    }
}

export default Product

  
