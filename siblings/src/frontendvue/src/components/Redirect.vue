<template>
</template>

<script>
import axios from 'axios';
export default {
    name: "Redirect",
    data(){
        return{
        }
    },
    created(){
        let atoken = `Bearer ${this.$route.query.accessToken}`;
        console.log(atoken);
        axios.get(`${process.env.VUE_APP_BACKEND_URL}/member/me`,{
            headers : {
                "Authorization" : atoken
            }
        })
        .then(res=>{
                this.$store.commit("saveInfo", res.data);
                this.$router.push('/');
        })
        .catch(err=>{
            console.log("에러 : "+err);});
    },
}
</script>

<style>

</style>