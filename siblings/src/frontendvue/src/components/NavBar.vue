<template>
  <header>
    <router-link to="/" >Home</router-link>
    <button v-if="!loginYn" @click="goLogin" >로그인</button>
    <button v-if="loginYn" @click="goMypage" >마이페이지</button>
    <button v-if="loginYn">로그아웃</button>
  </header>
</template>

<script>
export default {
    name : 'NavBar',
    data(){
        return{
            loginYn : this.$store.state.loginYn,
        }
    },
    mounted(){
    },
    methods : {
      goMypage(){
        this.$router.push('/my');
      },
      goLogin(){
        this.$router.push('/login');
      }
    }
}
</script>

<style>

</style>