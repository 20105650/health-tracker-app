<template id="water-intake">
  <app-layout>
    <div class="card bg-light mb-3">
      <div class="card-header">
        <div class="row">
          <div class="col-6">Add Water Intake</div>
          <div class="col" align="right">
            <button rel="tooltip" title="Add"
                    class="btn btn-info btn-simple btn-link"
                    @click="hideForm =!hideForm">
              <i class="fa fa-plus" aria-hidden="true"></i>
            </button>
          </div>
        </div>
      </div>
      <div class="card-body" :class="{ 'd-none': hideForm}">
        <form>
          <div class="form-row">
            <div class="form-group col-md-4" style="display: none">
              <input type="hidden" class="form-control" v-model="userId" name="id" readonly placeholder="Id"/>
            </div>
            <div class="form-group col-md-4">
              <label for="inputweight">Weight ( lbs )</label>
              <input type="text" class="form-control"  v-model="formData.weight" name="weight" placeholder="Weight (lbs)"/>
            </div>
            <div class="form-group col-md-4">
              <label for="inputwater">Water Consumed</label>
              <input type="text" class="form-control"  v-model="formData.water" name="water" placeholder="Consumed Water(Ounces)"/>
            </div>
            <div class="form-group col-md-4">
              <button @click="calculateWaterintakes()" class="btn btn-primary">Calculate</button>
            </div>
          </div>
        </form>
      </div>
    </div><!--  card bg-light mb-3 -->
    <div class="list-group list-group-flush">
      <h3 class="col-6">Todays Water Intake </h3>
      <table class="table">
        <thead>
        <tr>
          <th scope="col">WEIGHT(lbs)</th>
          <th scope="col">WATER CONSUMED(Ounces)</th>
          <th scope="col">WATER DAILY NEEDED(Ounces)</th>
          <th scope="col">CREATED AT</th>
          <th scope="col">RECOMMEND</th>
          <th scope="col">ACTION</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(waterintake,index) in waterintakes" v-bind:key="index">
          <td>{{ waterintake.weight }}</td>
          <td>{{ waterintake.waterconsumed }}</td>
          <td>{{ waterintake.dailyrequired }}</td>
          <td>{{ waterintake.createdat }}</td>
          <td><p style="color:red;font-weight:bold;text-align: center">It is recommend that you drink: {{ waterintake.dailyrequired }} Ounces of water per day.</p></td>
          <td>
            <button rel="tooltip" title="Delete" class="btn btn-info btn-simple btn-link" @click="deleteWaterintake(waterintake, index)" >
              <i class="fas fa-trash" aria-hidden="true"></i>
            </button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </app-layout>
</template>




<script>
app.component("water-intake", {
  template: "#water-intake",
  data: () => ({
    waterintakes: [],
    users:[],
    formData: [],
    userId: null,
    hideForm: true,
  }),
  created() {
    this.fetchWaterintakes();
  },
  methods: {
    fetchWaterintakes: function () {
      this.userId = this.$javalin.pathParams["user-id"];

      axios.get(`/api/users/${this.userId}/waterintakes`)
          .then(res => this.waterintakes = res.data)
          .catch(() => alert("Error while fetching waterintakes"));
    },
    deleteWaterintake: function (waterintake, index) {
      if (confirm('Are you sure you want to delete this waterintake? This action cannot be undone.', 'Warning')) {
        //user confirmed delete
        const waterintakeId = waterintake.id;
        const url = `/api/waterintake/${waterintakeId}`;axios.delete(url)
            .then(response =>
                //delete from the local state so Vue will reload list automatically
                this.waterintakes.splice(index, 1).push(response.data))
            .catch(function (error) {
              console.log(error)
            });
      }
    },
    calculateWaterintakes: function () {
      const url = `/api/waterintake`;
      const userId = this.$javalin.pathParams["user-id"];
      const dailyneeded = this.formData.weight * (2/3);
      axios.post(url,
          {
            weight: this.formData.weight,
            waterconsumed: this.formData.water,
            dailyrequired: dailyneeded,
            user_id:userId,
            createdat:new Date().toISOString()

          })
          .then(response => {
            this.waterintakes.push(response.data)
            this.hideForm = true;
          })
          .catch(error => {
            console.log(error)
          })
    }
  }
});
</script>