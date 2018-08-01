<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <h2 @click="login">Essential Links</h2>
  </div>
</template>

<script>
export default {
  // name: 'HelloWorld',
  data() {
    return {
      msg: "Welcome to Your Vue.js App"
    };
  },
  watch: {
    '$route.params.id'() {
      console.log(3);
    }
  },
  methods: {
    login() {
      this.$router.push({ path: '/hello/2' });
    }
  },
  created() {
    console.log(2);
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1,
h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
