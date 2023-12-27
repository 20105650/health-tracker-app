<template id="user-overview">
  <app-layout>
    <div class="card bg-light mb-3">
      <div class="card-header">
        <div class="row">
          <div class="col-6">
            Users
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
        <form id="addUser">
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-name">Name</span>
            </div>
            <input type="text" class="form-control" v-model="formData.name" name="name" placeholder="Name"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-email">Email</span>
            </div>
            <input type="email" class="form-control" v-model="formData.email" name="email" placeholder="Email"/>
          </div>
        </form>
        <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link" @click="addUser()">Add User
        </button>
      </div>
    </div>
    <div class="list-group list-group-flush">
      <h3 class="col-6">Users Data </h3>
      <table class="table">
        <thead>
        <tr>
          <th scope="col">NAME</th>
          <th scope="col">EMAIL</th>
          <th scope="col">ACTION</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(user,index) in users" v-bind:key="index">
          <td>{{ user.name }}</td>
          <td>{{ user.email }}</td>
          <td>
            <a :href="`/users/${user.id}`">
              <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link">
                <i class="fa fa-pencil" aria-hidden="true"></i>
              </button>
            </a>
            <button rel="tooltip" title="Delete" class="btn btn-info btn-simple btn-link"
                    @click="deleteUser(user, index)">
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
app.component("user-overview", {
  template: "#user-overview",
  data: () => ({
    users: [],
    formData: [],
    hideForm: true,
  }),
  created() {
    this.fetchUsers();
  },
  methods: {
    fetchUsers: function () {
      axios.get("/api/users")
          .then(res => this.users = res.data)
          .catch(() => alert("Error while fetching users"));
    },
    deleteUser: function (user, index) {
      if (confirm('Are you sure you want to delete this user? This action cannot be undone.', 'Warning')) {
        //user confirmed delete
        const userId = user.id;
        const url = `/api/users/${userId}`;
        axios.delete(url)
            .then(response =>
                //delete from the local state so Vue will reload list automatically
                this.users.splice(index, 1).push(response.data))
            .catch(function (error) {
              console.log(error)
            });
      }
    },
    addUser: function () {
      const url = `/api/users`;
      axios.post(url,
          {
            name: this.formData.name,
            email: this.formData.email
          })
          .then(response => {
            this.users.push(response.data)
            this.hideForm = true;
          })
          .catch(error => {
            console.log(error)
          })
    }
  }
});
</script>