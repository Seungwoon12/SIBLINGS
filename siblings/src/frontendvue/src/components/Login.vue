<template>
    <GoogleLogin :params="params" :callback="callback">
        <span>구글 로그인</span>
    </GoogleLogin>
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
        callback(googleUser){
            console.log(googleUser);
        },
    }
}
</script>

<style scoped>

</style>