<template>
    <div>
        <h2>구글 로그인</h2>
        <a :href="url1">구글로 로그인하기</a>
    </div>

    <div>
        <h2>카카오 로그인</h2>
        <a :href="kakao_url">카카오로 로그인하기</a>
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
            url1 :  process.env.VUE_APP_BACKEND_URL + "/oauth2/authorization/google?redirect_uri=" + process.env.VUE_APP_REDIRECT_URI,
            kakao_url : process.env.VUE_APP_BACKEND_URL + "/oauth2/authorization/kakao?redirect_uri=" + process.env.VUE_APP_REDIRECT_URI
        }
    },
    methods:{
        
    }
}
</script>

<style scoped>

</style>