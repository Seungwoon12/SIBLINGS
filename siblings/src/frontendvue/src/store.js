import {createStore} from 'vuex';

const store = createStore({
    state(){
        return{
            infoObj : {},
        }
    },
    getters:{
        getInfo(state){
            return state.infoObj;
        }
    },
    mutations : {
        saveInfo(state, data){
            state.infoObj = data;
        }
    }
})

export default store;