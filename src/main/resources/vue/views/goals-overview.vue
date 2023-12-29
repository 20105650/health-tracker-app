<template id="goals-overview">
  <app-layout>
    <div class="list-group list-group-flush">
      <h3>Your Goals are here...</h3>
      <div class="col-md-12">
        <div  v-for="(goal,index) in goals" v-bind:key="index" class="col-md-12 box-goal">
          <h4>{{ goal.goal_category }}</h4>
          <p>{{ goal.description }}</p>
        </div>
      </div>
    </div>
  </app-layout>
</template>

<script>
app.component("goals-overview", {
  template: "#goals-overview",
  data: () => ({
    goals: [],
    users:[],
    formData: [],
    userId: null,
    hideForm: true,
  }),
  created() {
    this.fetchUsers();
  },

  methods: {
    fetchUsers: function () {
      this.userId = this.$javalin.pathParams["user-id"];
      axios.get(`/api/users/${this.userId}/goals`)
          .then(res => this.goals = res.data)
          .catch(() => alert("Error while fetching Goals"));
    }
  }
});
</script>

<style>
.list-group .container {
  display: flex;
  justify-content: space-around;
  align-items: center;
  height: 100vh;
}
.list-group h3{
  text-transform: uppercase;
  text-align: center;
}
.list-group h4{
  text-transform: uppercase;
  text-align: center;
}
.box-goal {
  height: auto;
  border: 2px solid #333;
  padding: 20px;
  text-align: center;
  font-size: 16px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease-in-out;
}

.box-goal:hover {
  transform: scale(1.1);
}

.box-goal {
  background-color: #87b2de;
  color: #fff;
  margin :10px 0px;
}
</style>