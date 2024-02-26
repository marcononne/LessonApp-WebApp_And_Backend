<template>
  <div class="pt-3">
    <h4 class="pb-4">Sign In</h4>
    <div class="pb-3 d-flex justify-content-center">
      <form>
        <div class="mb-3 text-start">
          <label class="form-label">Username</label>
          <input class="form-control" placeholder="Username" type="text" required v-model="username" @input="hideErrorMessage()">
        </div>
        <div class="mb-3 text-start">
          <label class="form-label">Password</label>
          <input class="form-control" placeholder="Password" type="password" required v-model="password" @input="hideErrorMessage()">
        </div>
        <button type="submit" class="btn btn-primary mt-3" @click.prevent="login" id="login-button">Login</button>
      </form>
    </div>
    <div v-show="showErrorMessage" class="error-message">
      {{ errorMessage }}
    </div>
  </div>

</template>

<script>
import {login} from "@/services/fetchData";
import {store} from "@/main";

export default {
  name: "LoginForm",
  emits: ['loginClient', 'loginAdmin', 'close'],
  data() {
    return {
      username: null,
      password: null,
      showErrorMessage: false,
      errorMessage: null
    }
  },
  mounted() {
    this.showErrorMessage = false;
    this.errorMessage = null;
  },
  methods: {
    login() {
      if(!this.username || !this.password) {
        this.showErrorMessage = true;
        this.errorMessage = "Both the username and the password are needed";
      }
      else {
        login(this.username, this.password)
          .then((response) => {
            store.data.token = response.token;
            store.data.username = response.username;
            localStorage.setItem('token', response.token);
            localStorage.setItem('username', response.username);
            if (response.role === "Admin") {
              this.username = null;
              this.password = null;
              this.showErrorMessage = false;
              this.errorMessage = null;
              this.$emit('loginAdmin');
              this.$emit('close');
            } else {
              if (response.role === "Client") {
                this.username = null;
                this.password = null;
                this.showErrorMessage = false;
                this.errorMessage = null;
                this.$emit('loginClient');
                this.$emit('close');
              } else {
                this.showErrorMessage = true;
                this.errorMessage = response.role;
              }
            }
          })
          .catch((error) => {
            this.showErrorMessage = true;
            this.errorMessage = error.message;
          });
      }
    },
    hideErrorMessage() {
      if(this.showErrorMessage){
        this.showErrorMessage = false;
        this.errorMessage = null;
      }
    }
  },
}
</script>

<style scoped>
    #login-button {
        border-color: #b3c8e8;
        background-color: #b3c8e8;
        color: #1c314b;
    }
    #login-button:hover {
        border-color: #1c314b;
        background-color: #1c314b;
        color: #b3c8e8;
    }
    .pt-3 {
        color: #041c35;
    }
    .error-message {
        color: #ba1a1a;
        font-size: 24px;
        font-weight: bold;
        text-align: center;
    }
</style>
