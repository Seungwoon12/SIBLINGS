<template>
    <h2>마이페이지</h2>
    <div>
        <img :src="this.infoObj.picture">
    </div>
</template>

<script>
import axios from 'axios'
export default {
    name: "MyPage",
    data() {
        return{
            infoObj : {},
            imageUrl : "",
            email : "",
            name : "",
            role : "",
            accessToken : "",
        }
    },
    mounted(){
        this.infoObj = this.$store.state.infoObj;
    },
    methods:{
        
    }
}
</script>

<style>

</style>
