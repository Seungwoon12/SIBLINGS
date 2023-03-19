<template>
  <header>
    <router-link to="/" >Home</router-link>
    <button @click="$emit('openLoginPop')" >로그인</button>
  </header>
</template>

<script>
export default {
    name : 'NavBar',
    data(){
        return{
            menu:[]
        }
    }
}
</script>

<style>

</style>