<template id="user-activity-overview">
  <app-layout>
    <div class="card bg-light mb-3">
      <div class="card-header">
        <div class="row">
          <div class="col-6">Add Activity </div>
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
            <div class="form-group col-md-12">
              <div class="col-md-6"><label for="inputactivity">Select the Activity  </label></div>
              <div class="col-md-6"><select @change="handleactivityChange"  class="form-select" id="activity" v-model="formData.activity">
                <option selected value="walking">Walking</option>
                <option value="cycling">Cycling</option>
                <option value="running">Running</option>
                <option value="swimming">Swimming</option>
              </select></div>
            </div>
            <div class="form-group col-md-12">
              <div class="col-md-6"><label for="inputweight">Weight (Kg)</label></div>
              <div class="col-md-6"><input type="text" class="form-control"  v-model="formData.weight" name="weight" placeholder="Weight"/></div>
            </div>
            <div class="form-group col-md-12">
              <div class="col-md-6"><label for="inputduration">Duration</label></div>
              <div class="col-md-3"><input type="number" class="form-control"  v-model="formData.duration_hours" name="duration_hours" placeholder="Hours"/></div>
                <div class="col-md-3"><input type="number" class="form-control"  v-model="formData.duration_minutes" name="duration_minutes" placeholder="Minutes"/></div>
            </div>

            <div class="form-group col-md-12">
              <div class="col-md-6"><label for="inputactivitytype">Select the type of Activity</label></div>
              <div class="col-md-6">
              <select :disabled="formData.activity !== 'walking'" v-if="formData.activity === 'walking'" class="form-select" id="walking_type" v-model="formData.type">
                <option selected value="Less than 2.0 mph, level, strolling, very slow@2">Less than 2.0 mph, level, strolling, very slow</option>
                <option value="Walking a dog@3">Walking a dog</option>
                <option value="Walking for pleasure@3.5">Walking for pleasure</option>
                <option value="Walking with children@4">Walking with children</option>
              </select>

              <select :disabled="formData.activity !== 'cycling'" v-if="formData.activity === 'cycling'" class="form-select" id="cycling_type" v-model="formData.type">
                <option selected value="Bicycling, leisure, 5.5 mph@3.5">Bicycling, leisure, 5.5 mph</option>
                <option value="Unicycling@5">Unicycling</option>
                <option value="Bicycling, leisure, 9.4 mph@5.8">Bicycling, leisure, 9.4 mph</option>
                <option value="Bicycling, 12 - 13.9 mph, leisure, moderate effort@8">Bicycling, 12 - 13.9 mph, leisure, moderate effort</option>
              </select>

              <select :disabled="formData.activity !== 'running'" v-if="formData.activity === 'running'" class="form-select" id="running_type" v-model="formData.type">
                <option selected value="4 mph (13 min / mile)@5">4 mph (13 min / mile)</option>
                <option value="Running, general@8">Running, general</option>
                <option value="6 mph (10 min / mile)@9.8">6 mph (10 min / mile)</option>
                <option value="7 mph (8.5 min / mile)@11">7 mph (8.5 min / mile)</option>
              </select>


              <select :disabled="formData.activity !== 'swimming'" class="form-select" v-if="formData.activity === 'swimming'" id="swimming_type" v-model="formData.type">
                <option selected value="Backstroke, recreational@4.8">Backstroke, recreational</option>
                <option value="Sidestroke, general@7">Sidestroke, general</option>
                <option value="Water areobics, water callsthenics@5.5">Water areobics, water callsthenics</option>
                <option value="Treading water, fast, vigorous effort@9.8">Treading water, fast, vigorous effort</option>
              </select></div>
            </div>

            <div class="form-group col-md-4">
              <button @click="calculateCalorie()" class="btn btn-primary">Calculate</button>
            </div>
          </div>
        </form>
      </div>

    </div>  <!-- mb-3 -->

    <div v-if="noActivityFound">
      <p> We're sorry, we were not able to retrieve any activities for this user.</p>
      <p> View <a :href="'/users'">all users</a>.</p>
    </div>

    <div class="list-group list-group-flush" v-if="!noActivityFound">
      <h1></h1> <h1></h1><h1></h1> <h1></h1>
      <h3 class="col-6" style="color: #ec5525;font-size: 18px;text-transform: uppercase;">Exercises Covered</h3>
      <table class="table">
        <thead>
        <tr>
          <th scope="col">ACTIVITY</th>
          <th scope="col">DURATION</th>
          <th scope="col">MODE</th>
          <th scope="col">CALORIES BURNED</th>
          <th scope="col">DATE</th>
          <th scope="col">ACTION</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(activity,index) in activities" v-bind:key="index">
          <td>{{ activity.activity }}</td>
          <td>{{ activity.duration }}</td>
          <td>{{ activity.type }}</td>
          <td>{{ activity.calories}}</td>
          <td>{{ activity.createdat }}</td>

          <td>
            <button rel="tooltip" title="Delete" class="btn btn-info btn-simple btn-link" @click="deleteActivities(activity, index)" >
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
app.component("user-activity-overview",{
  template: "#user-activity-overview",
  data: () => ({
    activities: [],
    users:[],
    formData: [],
    userId: null,
    hideForm: true,
    noActivityFound: false,
    activity:null
  }),
  created() {
    this.fetchUsers();
  },

  methods: {
    fetchUsers: function () {
      this.userId = this.$javalin.pathParams["user-id"];
      axios.get(`/api/users/${this.userId}/activities`)
          .then(res => this.activities = res.data)
          .catch(() => alert("Error while fetching User activities"));
      axios.get(`/api/users/${this.userId}`)
          .then(res => this.users = res.data)
          .catch(() => alert("Error while fetching user data"));
    },

    deleteActivities: function (activity, index) {
      if (confirm('Are you sure you want to delete this activity? This action cannot be undone.', 'Warning')) {
        //user confirmed delete
        const activityId = activity.id;
        const url = `/api/activities/${activityId}`;
        axios.delete(url)
            .then(response =>
                //delete from the local state so Vue will reload list automatically
                this.activities.splice(index, 1).push(response.data))
            .catch(function (error) {
              console.log(error)
            });
      }
    },
    calculateCalorie: function () {
      const url = `/api/activities`;
      const userId = this.$javalin.pathParams["user-id"];

      axios.post(url,
          {
            activity: this.formData.activity,
            duration: (this.formData.duration_hours * 60) + this.formData.duration_minutes,
            weight : this.formData.weight,
            type : this.formData.type,
            user_id:userId,
            createdat:new Date().toISOString()

          })
          .then(response => {
            this.activities.push(response.data)
            this.hideForm = true;
          })
          .catch(error => {
            console.log(error)
          })
    }
  }
});
</script>