<template>
  <div>
    <nav-bar @openLoginPop="openLoginPop"/>
    <login v-if="isLoginPopOpen" />
  </div>
</template>

<script>
import Login from './Login.vue'
import NavBar from './NavBar.vue'
export default {
  components: { NavBar, Login },
  data() {
    return{
        isLoginPopOpen : false,
    }
  },
  methods:{
    openLoginPop(){
        this.isLoginPopOpen = true;
    },

  }

}
</script>

<style>

</style>