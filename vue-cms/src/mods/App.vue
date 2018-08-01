<template>
  <div>
    <img src="@/assets/font/cart.svg" />
    <a href="javascript:;" v-on:click="login">aaa</a>
  </div>
</template>

<script>

export default {
  methods: {
    login() {
      this.$router.push({ path: '/hello/1' });
    },
    reqUserInfoApi() {
      this.$api.userinfo().then((res) => {
        console.log(res);
      }).catch(((error) => {
        console.log(error.message)
      }));
    }
  },
  created() {
    this.reqUserInfoApi();
  }
};
</script>

<style scoped>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
