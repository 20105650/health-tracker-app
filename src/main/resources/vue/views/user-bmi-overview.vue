<template id="user-bmi-overview">
  <app-layout>
    <div class="card bg-light mb-3">
      <div class="card-header">
        <div class="row">
          <div class="col-6">
            Add BMI
          </div>
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
              <label for="inputweight">Weight ( kg )</label>
              <input type="text" class="form-control"  v-model="formData.weight" name="weight" placeholder="Weight ( kg )"/>
            </div>
            <div class="form-group col-md-4">
              <label for="inputheight">Height ( cm )</label>
              <input type="text" class="form-control"  v-model="formData.height" name="height" placeholder="Height ( cm )"/>
            </div>
            <div class="form-group col-md-4">
            <button @click="calculateBmi()" class="btn btn-primary">Calculate</button>
              </div>
          </div>
        </form>
      </div>
    </div>
    <div class="list-group list-group-flush">
      <h3 class="col-6">BMI History</h3>
      <table class="table">
        <thead>
        <tr>
          <th scope="col">WEIGHT</th>
          <th scope="col">HEIGHT</th>
          <th scope="col">AGE</th>
          <th scope="col">BMI</th>
          <th scope="col">RESULT</th>
          <th scope="col">CREATED AT</th>
          <th scope="col">ACTION</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(bmi,index) in bmis" v-bind:key="index">
          <td>{{ bmi.weight }}</td>
          <td>{{ bmi.height }}</td>
          <td>{{ bmi.age }}</td>
          <td>{{ bmi.bmival }}</td>
          <td>{{ bmi.bmiresult}}</td>
          <td>{{ bmi.createdat }}</td>
          <td>
            <button rel="tooltip" title="Delete" class="btn btn-info btn-simple btn-link" @click="deleteBmi(bmi, index)">
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
app.component("user-bmi-overview", {
  template: "#user-bmi-overview",
  data: () => ({
    bmis: [],
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
      axios.get(`/api/users/${this.userId}/bmis`)
          .then(res => this.bmis = res.data)
          .catch(() => alert("Error while fetching BMI"));
      axios.get(`/api/users/${this.userId}`)
          .then(res => this.users = res.data)
          .catch(() => alert("Error while fetching user data"));
    },
    deleteBmi: function (bmi, index) {
      if (confirm('Are you sure you want to delete this BMI? This action cannot be undone.', 'Warning')) {
        //user confirmed delete
        const bmiId = bmi.id;
        const url = `/api/bmi/${bmiId}`;
        axios.delete(url)
            .then(response =>
                //delete from the local state so Vue will reload list automatically
                this.bmis.splice(index, 1).push(response.data))
            .catch(function (error) {
              console.log(error)
            });
      }
    },
    calculateBmi: function () {
      const url = `/api/bmi`;
      const userId = this.$javalin.pathParams["user-id"];
      let hwold = null;
      if (this.users.dob) {
        const birthDate = new Date(this.users.dob);
        const currentDate = new Date();
        let age = currentDate.getFullYear() - birthDate.getFullYear();

        // Adjust age based on the month and day of birth
        if (
            currentDate.getMonth() < birthDate.getMonth() ||
            (currentDate.getMonth() === birthDate.getMonth() &&
                currentDate.getDate() < birthDate.getDate())
        ) {
          age--;
        }
        hwold = age;
      } else {
        hwold = null;
      }
      axios.post(url,
          {
            weight: this.formData.weight,
            height: this.formData.height,
            user_id:userId,
            age:hwold,
            community:"Asian",
            createdat:new Date().toISOString()

          })
          .then(response => {
            this.bmis.push(response.data)
            this.hideForm = true;
          })
          .catch(error => {
            console.log(error)
          })
    }
  }
});
</script>
