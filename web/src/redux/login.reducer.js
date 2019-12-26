
const initState={
    userId: "",
    username:"",
    accessToken:""
}
const user=(state=initState,action)=>{
    if (action.type === 'LOGIN') {
        return {...state,...action.payload};
    } else {
        return state;
    }
};
export default user;
//action creator
