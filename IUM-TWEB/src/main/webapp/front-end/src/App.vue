<template>
  <div>
    <nav class="navbar navbar-expand-lg navbar navbar-dark bg-dark" style="background-color: #191c1c;">
      <div class="container-fluid">
        <a class="navbar-brand" href="#" style="color: #e0e3e2; font-size: xx-large" @click="redirectToHome()">
          <img src="./assets/30L.jpg" alt="" width="50" height="50" class="d-inline-block align-text-center" @click="redirectToHome()">
          Teach Hub
        </a>
        <div v-if="loggedAdmin" class="nav-item">
          <router-link to="/AdminTeachers" class="nav-link link-item"
                       :class="{ active: activeLink === 'AdminTeachers' }"
                       @click="setActiveLink('AdminTeachers')">
            Teachers
          </router-link>
        </div>
        <div v-if="loggedAdmin" class="nav-item">
          <router-link to="/AdminCourses" class="nav-link link-item" :class="{ active: activeLink === 'AdminCourses' }"
                       @click="setActiveLink('AdminCourses')">
            Courses
          </router-link>
        </div>
        <div v-if="loggedAdmin" class="nav-item">
          <router-link to="/AdminTeachings" class="nav-link link-item"
                       :class="{ active: activeLink === 'AdminTeachings' }"
                       @click="setActiveLink('AdminTeachings')">
            Teachings
          </router-link>
        </div>
        <div v-if="loggedAdmin" class="nav-item">
          <router-link to="/AdminLessons" class="nav-link link-item"
                       :class="{ active: activeLink === 'AdminLessons' }"
                       @click="setActiveLink('AdminLessons')">
            Lessons
          </router-link>
        </div>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false"
                aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
          <form class="ms-auto">
            <span v-if="loggedClient || loggedAdmin">
              <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                <div class="navbar-nav ms-auto">
                  <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle btn btn-outline-success" href="#" id="navbarDropdownMenuLink"
                       role="button" data-bs-toggle="dropdown" aria-expanded="false">
                      {{ this.loggedClient || this.loggedAdmin }}
                    </a>
                    <ul class="dropdown-menu dropdown-menu-dark dropdown-menu-end" id="dropdown-after-login">
                      <li v-if="loggedClient && !showClientPersonal">
                        <a class="dropdown-item" id="personal-area-link">
                          <router-link to="/ClientPersonal"
                                       style="text-decoration: none; color: #041c35">Area personale</router-link>
                        </a>
                      </li>
                      <li v-if="loggedClient && showClientPersonal">
                        <a class="dropdown-item" id="home-link">
                          <router-link to="/ClientHome" style="text-decoration: none; color: #041c35">Home</router-link>
                        </a>
                      </li>
                      <li><a class="dropdown-item" @click="logout" id="logout-link"
                             style="text-decoration: none; color: #041c35">Logout</a></li>
                    </ul>
                  </li>
                </div>
              </div>
            </span>
            <button v-else class="btn btn-outline-success me-2" type="button" data-bs-toggle="modal"
                    data-bs-target="#exampleModal1" id="navbar-button">
              Sign In
            </button>
          </form>
        </div>
      </div>
    </nav>


    <router-view></router-view> <!-- HomeView -->


    <div class="modal fade" ref="access" id="exampleModal1" tabindex="-1" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content" style="color: #041c35">
          <button id="btnclose" type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"
                  @click="loginVisible = true; registerVisible = false"></button>
          <div v-show="loginVisible">
            <LoginForm @close="closeModal" @loginClient="showClientHome" @loginAdmin="showAdminHome"></LoginForm>
            <div>
              <p>You don't have an account?<span @click="loginVisible = false; registerVisible = true;"
                                                 style="cursor: pointer; color: #006a6a;"> Sign Up </span></p>
            </div>
          </div>
          <div v-show="registerVisible">
            <RegisterForm @close="closeModal" @registerClient="showClientHome"></RegisterForm>
            <p>You already have an account?<span @click="registerVisible = false; loginVisible = true;"
                                                 style="cursor: pointer; color: #006a6a;"> Login </span></p>
          </div>
        </div>
      </div>
    </div>
  </div>

  <footer>
    <p>© 2023 Teach_Hub. All rights reserved.</p>
  </footer>
</template>

<script>
import LoginForm from './views/LoginForm.vue';
import RegisterForm from './views/RegisterForm.vue';
import ClientHome from "@/views/ClientHome";
import ClientPersonal from "@/views/ClientPersonal.vue";
import router from "@/router/index";
import {store} from "@/main";
import {logout} from "@/services/fetchData";

export default {
  name: 'App',
  components: {ClientHome, LoginForm, RegisterForm, ClientPersonal},
  data() {
    return {
      loginVisible: true,
      registerVisible: false,
      loggedClient: null,
      loggedAdmin: null,
      showClientPersonal: false,
      activeLink: null
    }
  },
  created() {
    router.beforeEach((to, from, next) => {
      const blockedAdminPages = ['/AdminCourses', '/AdminLessons', '/AdminTeachers', '/AdminTeachings'];
      const blockedClientPages = ['/ClientHome', '/ClientPersonal'];
      const isLoggedAdmin = localStorage.getItem('loggedAdmin') !== null;
      const isLoggedClient = localStorage.getItem('loggedClient') !== null;
      if (blockedAdminPages.includes(to.path)) {
        if (isLoggedAdmin) {
          next();
        } else {
          if (isLoggedClient) {
            next('/ClientHome');
          } else {
            next('/');
          }
        }
      } else if (blockedClientPages.includes(to.path)) {
        if (isLoggedClient) {
          next();
        } else {
          if (isLoggedAdmin) {
            this.activeLink = 'AdminLessons';
            next('/AdminLessons');
          } else {
            next('/');
          }
        }
      } else {
        next();
      }
    });

    const loggedAdmin = localStorage.getItem('loggedAdmin');
    const loggedClient = localStorage.getItem('loggedClient');
    const activeLink = localStorage.getItem('activeLink');

    if (loggedAdmin) {
      this.loggedAdmin = loggedAdmin;
      this.activeLink = activeLink;
    } else if (loggedClient) {
      this.loggedClient = loggedClient;
    }
    this.$bus.on('showClientPersonal', (value) => {
      this.showClientPersonal = value;
    })
  },
  methods: {
    showClientHome() {
      this.loginVisible = false;
      this.registerVisible = false;
      this.showClientPersonal = false;
      this.loggedClient = store.data.username;
      localStorage.setItem('loggedClient', this.loggedClient);
      router.push('/ClientHome');
    },
    showAdminHome() {
      this.loginVisible = false;
      this.registerVisible = false;
      this.loggedAdmin = store.data.username;
      this.activeLink = 'AdminLessons';
      localStorage.setItem('loggedAdmin', this.loggedAdmin);
      localStorage.setItem('activeLink', this.activeLink);
      router.push('/AdminLessons');
    },
    logout() {
      localStorage.removeItem('loggedAdmin');
      localStorage.removeItem('loggedClient');
      localStorage.removeItem('activeLink');
      localStorage.removeItem('token');
      this.loggedClient = null;
      this.activeLink = null;
      this.loggedAdmin = null;
      this.loginVisible = true;
      this.registerVisible = false;
      this.showClientPersonal = false;
      logout()
          .then(() => {
            this.$router.push('/');
          })
    },
    closeModal() {
      document.getElementById('btnclose').click();
      this.loginVisible = true;
      this.registerVisible = false;
    },
    setActiveLink(link) {
      if (this.activeLink !== link){
        this.activeLink = link;
        localStorage.setItem('activeLink', link);
      }
    },
    redirectToHome() {
        if(this.loggedClient !== null)
            this.$router.push('/ClientHome');
        else if (this.loggedAdmin != null) {
            this.$router.push('/AdminLessons');
            this.activeLink = 'AdminLessons';
        }
        else
            this.$router.push('/');
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  background-color: #fafdfc;
}
.btn-close {
  padding: 0.5em;
}

.modal {
  display: none;
}

.modal.show {
  display: block;
}

#navbar-button {
  border-color: #041c35;
  background-color: #b3c8e8;
  color: #041c35;
}

#navbar-button:hover {
  border-color: #b3c8e8;
  background-color: #041c35;
  color: #b3c8e8;
}

#navbarDropdownMenuLink {
  border-color: #041c35;
  background-color: #b3c8e8;
  color: #041c35;
}

#navbarDropdownMenuLink:hover {
  border-color: #b3c8e8;
  background-color: #041c35;
  color: #b3c8e8;
}

#dropdown-after-login {
  background-color: #b3c8e8;
}

.nav-item {
  margin-right: 10px;
  color: #b3c8e8;
}

.nav-item:hover {
  margin-right: 10px;
  color: #00dcdc;
}

.nav-link.link-item.active {
  background-color: #4969ab;
  border-radius: 5px;
}
#logout-link:hover {
  cursor: pointer;
}

footer {
    padding-top: 2%;
    color: #191c1c;
}
</style>

<!-- TODO: -Fare in modo che quando ricarico la pagina dopo che mi sono loggato il pulsante non ritorni 'Accedi' ma rimanga 'Logout'
            (adesso se mi loggo e ricarico la pagina il pulsante passa da Logout ad Accedi e posso autenticarmi nuovamente anche se sono già nella schermata ClientHome)
           -Aggiungere funzione di add nuovo utente (Client)
           -Aggiungere tutti i ; dentro javascript
-->
