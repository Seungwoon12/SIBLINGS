<template>
  <div>
    <nav-bar />
    <router-view />
  </div>
</template>

<script>
import NavBar from './NavBar.vue';
export default {
  name : "Index",
  components:{
    NavBar
  },
  data() {
    return{
    }
  },
  mounted(){
  },
  methods:{

  }

}
</script>

<style>

</style>