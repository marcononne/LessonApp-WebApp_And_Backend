<template>
  <div class="pt-3">
    <h4 class="pb-4">Sign Up</h4>
    <div class="pb-3 d-flex justify-content-center">
      <form @submit.prevent="validatePassword">

        <div class="mb-3 text-start">
          <label class="form-label"> Name: </label>
          <input class="form-control" type="text" placeholder="Name" required v-model="name">
        </div>

        <div class="mb-3 text-start">
          <label class="form-label"> Username: </label>
          <input class="form-control" type="text" placeholder="Username" required v-model="username">
        </div>

        <div class="mb-3 text-start">
          <label class="form-label"> Gender: </label>
          <select class="form-select" v-model="gender" required>
            <option value="" disabled selected hidden>Select gender</option>
            <option value="Male">Male</option>
            <option value="Female">Female</option>
            <option value="I prefer not to specify">I prefer not to specify</option>
          </select>
        </div>

        <div class="mb-3 text-start">
          <label class="form-label"> Password: </label>
          <input class="form-control" type="Password" placeholder="Password" required v-model="password">
        </div>

        <div class="mb-3 text-start">
          <label class="form-label"> Confirm Password: </label>
          <input class="form-control" type="password" placeholder="Confirm Password" required v-model="confirmPassword">
        </div>


        <div v-if="passwordError"> {{ passwordErrorMessage }}</div>

        <div class="checkbox-container">
          <input type="checkbox" v-model="terms" required>
          <label class="checkbox-label"> Accept terms and conditions </label>
        </div>

        <div>
          <button v-bind:disabled="!filledFields()" :class="{ 'enabled-button': filledFields() }" @click="signUp" id="create-account-button"> Create an Account</button>
        </div>
      </form>
    </div>
    <div v-show="showErrorMessage" id="errorMessage">
      {{ errorMessage }}
    </div>
  </div>
</template>


<script>
import {signUp} from "@/services/fetchData";
import {store} from "@/main";

export default {
  name: "RegisterForm",
  emits: ['registerClient', 'close'],
  data() {
    return {
      name: '',
      username: '',
      password: '',
      confirmPassword: '',
      terms: false,
      gender: '',
      passwordError: false,
      passwordErrorMessage: '',
      showErrorMessage: false,
      errorMessage: 'test',
    }
  },
  computed: {
    passwordIsValid() {
      const passwordRegex = /^(?=.*\d).{6,}$/;
      return passwordRegex.test(this.password);
    }
  },
  watch: {
    password() {
      this.validatePassword();
    },
    confirmPassword() {
      this.validateConfirmPassword();
    }
  },
  methods: {
    validateConfirmPassword() {
      if (this.password !== this.confirmPassword) {
        this.passwordError = true;
        this.passwordErrorMessage = 'Passwords do not match';
      } else {
        this.passwordError = false;
        this.passwordErrorMessage = '';
      }
    },
    validatePassword() {
      if (!this.passwordIsValid) {
        this.passwordError = true;
        if (this.password.length < 6) {
          this.passwordErrorMessage = 'Password must be at least 6 characters long';
        } else if (!/\d/.test(this.password)) {
          this.passwordErrorMessage = 'Password must contain at least one number';
        }
      } else {
        this.passwordError = false;
        this.passwordErrorMessage = '';
      }
    },
    filledFields() {
      return (this.name !== '' &&
          this.username !== '' &&
          this.gender !== '' &&
          this.password !== '' &&
          this.confirmPassword !== '' &&
          this.terms !== false
      );
    },
    signUp() {
      signUp(this.username, this.password, this.name, this.gender)
          .then((response) => {
            store.data.token = response.token;
            store.data.username = response.username;
            localStorage.setItem('token', response.token);
            localStorage.setItem('username', response.username);
            this.$emit('registerClient');
            this.$emit('close');
            this.name = '';
            this.username = '';
            this.gender = '';
            this.password = '';
            this.confirmPassword = '';
            this.terms = false;
            this.passwordErrorMessage = '';
          })
          .catch((error) => {
            this.showErrorMessage = true;
            this.errorMessage = error.message;
          });
    }
  }
}
</script>

<style scoped>
    .pt-3 {
        color: #041c35;
    }
    .checkbox-container {
        margin-bottom: 10px;
    }
    .checkbox-label {
        padding-left: 5px;
    }
    #create-account-button {
        border-radius: 10px;
        padding-top: 1%;
        padding-bottom: 1%;
        padding-left: 3%;
        padding-right: 3%;
    }
    .enabled-button {
        border-radius: 10px;
        background-color: #d3e4ff;
        color: #041c35;
    }
    .enabled-button:hover {
        border-radius: 10px;
        background-color: #041c35;
        color: #d3e4ff;
    }
    #errorMessage {
        padding: 2%;
    }
</style>
