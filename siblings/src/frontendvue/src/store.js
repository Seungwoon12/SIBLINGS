import {createStore} from 'vuex';
import createPersistedState from 'vuex-persistedstate';

const store = createStore({
    state(){
        return{
            infoObj : {},
            loginYn : false,
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
            state.loginYn = true;
        }
    },
    plugins: [
        createPersistedState()
    ]
})

export default store;