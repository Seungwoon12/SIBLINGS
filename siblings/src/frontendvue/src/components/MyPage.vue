<template>
    <h2>마이페이지</h2>
    <div>
        <img :src="imageUrl">
        <div>{{email}}</div>
        <div>{{role}}</div>
    </div>
</template>

<script>
import axios from 'axios'
export default {
    name: "MyPage",
    created(){
        let at = `Bearer ${this.$route.query.accessToken}`;
        console.log(at);
        axios.get(`${process.env.VUE_APP_BACKEND_URL}/member/me`,{
            headers : {
                "Authorization" : at
            }
        })
        .then(res=>console.log(res))
        .catch(err=>{
            console.log("에러 : "+err);});

    },
    data() {
        return{
            imageUrl : "",
            email : "",
            role : "",
            accessToken : "",
        }
    },
    methods:{
        
    }
}
</script>

<style>

</style>
