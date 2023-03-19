<template>
    <div>
        <h2>로그인</h2>
        <input type="text" placeholder="ID">
        <input type="password" placeholder="PW">

        <div @click="googleLogin" style="cursor">구글로 로그인하기</div>
    </div>
</template>

<script>
export default {
    name: "Login",
    data() {
        return{
            params: {
                client_id : process.env.VUE_APP_GOOGLE_CLIENT_ID
            },
        }
    },
    methods:{
        googleLogin(){
            let backendUrl = process.env.VUE_APP_BACKEND_URL + "/oauth2/authorization/google?redirect_uri=" + process.env.VUE_APP_REDIRECT_URI;
            this.$router.go(backendUrl);
        }
    }
}
</script>

<style scoped>

</style>