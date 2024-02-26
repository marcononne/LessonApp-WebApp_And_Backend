<template>
  <h1 style="padding-top: 1%">Teachings area</h1>

  <div class="container-wrapper scrollable-container">
    <div class="teaching-container">
      <div v-for="teaching in info"
           :key="teaching.id"
           :class="{'teaching-item': true, 'teaching-active': teaching.status === true && !teaching.isSelected, 'teaching-inactive': teaching.status === false && !teaching.isSelected, 'teaching-disable': teaching.status === true && teaching.isSelected}"
           @click="toggleTeachingSelection(teaching)">
        {{ teaching.course + ' <==> ' + teaching.teacher }}
      </div>
    </div>
  </div>

  <div v-if="loading">Loading...</div>
  <div class="button-container" style="padding-top: 2%;">
    <button @click="setTeachingDisabled" :class="{ 'active-button-disable': selectedTeachings.length !== 0 }"
            :disabled="!selectedTeachings.length">
      Disable
    </button>
    <button @click="showModal = true" class="add-teaching-button">Add new teaching</button>
  </div>

  <div v-if="showModal" class="modal">
    <div class="modal-content">
      <span class="close" @click="showModal = false">X</span>
      <form @submit.prevent="addTeaching">
        <div class="form-row">
          <button class="btn btn-secondary dropdown-toggle button-dropdown-for-add" type="button" data-bs-toggle="dropdown" aria-expanded="false"
                  @click="getAllActiveCourses">
            {{ this.newTeaching.course }}
          </button>
          <ul class="dropdown-menu dropdown-menu-dark dropdown-menu-scrollable">
            <li v-if="activeCourses.length" v-for="course in activeCourses" class="dropdown-item"
                @click="newTeaching.course = course.title"
                @mouseover="showCourseDescription(course.description)"
                @mouseleave="hideCourseDescription()">
              {{ course.title }}
            </li>
          </ul>
        </div>
        <br>
        <div class="form-row">
          <button class="btn btn-secondary dropdown-toggle button-dropdown-for-add" type="button" data-bs-toggle="dropdown" aria-expanded="false"
                  @click="getAllActiveTeachers">
            {{ this.newTeaching.teacherName + ' ' + this.newTeaching.teacherSurname }}
          </button>
          <ul class="dropdown-menu dropdown-menu-dark dropdown-menu-scrollable">
            <li v-if="activeTeachers.length" v-for="teacher in activeTeachers" class="dropdown-item"
                @click="newTeaching.teacherName = teacher.name; newTeaching.teacherSurname = teacher.surname">
              {{ teacher.name + " " + teacher.surname }}
            </li>
          </ul>
        </div>
        <br>
        <button type="submit" :class="{ 'button-dropdown-for-add': newTeaching.course !== 'Course' && newTeaching.teacherName !== 'Teacher' }"
                :disabled="newTeaching.course === 'Course' || newTeaching.teacherName === 'Teacher'">
          Add
        </button>
      </form>
    </div>
  </div>

  <div v-if="courseDescription" class="course-description-box course-description-border">
    {{ courseDescription }}
  </div>

  <div v-if="showAlert" :class="{'success-alert': true, 'error-alert': this.resp.result !== 'Teaching added successfully'}">
    {{ this.resp.result }}!
  </div>
</template>

<script>
import {store} from "@/main";
import {
  addTeaching,
  getAllActiveCourses,
  getAllActiveTeachers,
  getAllTeachings,
  setTeachingDisabled
} from "@/services/fetchData";

export default {
  name: "AdminTeachings",
  data() {
    return {
      user: store.data.username,
      info: [],
      selectedTeachings: [],
      loading: false,
      activeCourses: [],
      activeTeachers: [],
      courseDescription: '',
      newTeaching: {
        course: 'Course',
        teacherName: 'Teacher',
        teacherSurname: ''
      },
      showModal: false,
      showAlert: false,
      resp: ''
    }


  },
  created() {
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    if (token && username) {
      store.data.token = token;
      store.data.username = username;
    }
    this.getAllTeachings();
  },
  methods: {
    getAllActiveTeachers() {
      getAllActiveTeachers()
          .then(response => {
            this.activeTeachers = response;
          })
    },
    getAllActiveCourses() {
      getAllActiveCourses('getAllActiveCourses')
          .then(response => {
            this.activeCourses = response;
          })
    },
    showCourseDescription(description) {
      this.courseDescription = description;
    },
    hideCourseDescription() {
      this.courseDescription = '';
    },
    async getAllTeachings() {
      this.loading = true;
      try {
        const response = await getAllTeachings();
        this.info = response;
      } catch (error) {
        console.error('Error retrieving teachings:', error);
      } finally {
        this.loading = false;
      }
    },
    toggleTeachingSelection(teaching) {
      if (teaching.status === true) {
        teaching.isSelected = !teaching.isSelected;
        if (teaching.isSelected) {
          this.selectedTeachings.push(teaching);
        } else {
          const index = this.selectedTeachings.findIndex((selectedTeaching) =>
              selectedTeaching === teaching
          );
          if (index !== -1) {
            this.selectedTeachings.splice(index, 1);
          }
        }
      }
    },
    async setTeachingDisabled() {
      this.loading = true;
      try {
        await Promise.all(
            this.selectedTeachings.map(teaching => {
              const [name, surname] = teaching.teacher.split(' ');
              return setTeachingDisabled(teaching.course, name, surname);
            })
        );
        await this.getAllTeachings();
        this.selectedTeachings = [];
      } catch (error) {
        console.error('Error marking teachings unactive:', error);
      } finally {
        this.loading = false;
      }
    },
    addTeaching() {
      const {course, teacherName, teacherSurname} = this.newTeaching;
      this.showModal = false;
      addTeaching(course, teacherName, teacherSurname).then(response => {
        this.resp = response;
        this.showAlert = true;
        setTimeout(() => {
          this.showAlert = false;
        }, 2500);
        this.refreshTeachings();
        this.showModal = false;
      })
      this.newTeaching.course = '';
      this.newTeaching.teacherName = '';
      this.newTeaching.teacherSurname = '';
    },
    refreshTeachings() {
      this.getAllTeachings();
    }
  }
}
</script>

<style scoped>
.container-wrapper {
  margin: 0 auto;
}
.teaching-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
}
.scrollable-container {
  max-width: 900px;
  margin: 0 auto;
  overflow-y: auto;
  max-height: 470px;
}
.teaching-item {
  width: 165px;
  height: 165px;
  margin: 15px;
  padding: 20px;
  border: 2px solid #003921;
  border-radius: 5px;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
}
.teaching-active {
  border: 2px solid #003921;
  background-color: #46e098;
  color: #003921;
}
.teaching-active:hover {
  border: 2px solid #68fdb2;
  background-color: #003921;
  color: #68fdb2;
}
.teaching-inactive {
  border: 2px solid #930009;
  background-color: #ffb4ab;
  color: #690004;
  cursor: not-allowed;
}
.teaching-disable {
  border: 2px solid #46e098;
  background-color: #002111;
  color: #46e098;
}
.button-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
button {
  margin-right: 10px;
  margin-left: 10px;
  border-radius: 5px;
}
.active-button-disable {
  border: 2px solid #46e098;
  background-color: #002111;
  color: #46e098;
}
.active-button-disable:hover {
  border: 2px solid #002111;
  background-color: #46e098;
  color: #002111;
}
.add-teaching-button {
  margin-right: 10px;
  margin-left: 10px;
  border: 2px solid #041c35;
  background-color: #b3c8e8;
  color: #041c35;
}
.add-teaching-button:hover {
  margin-right: 10px;
  margin-left: 10px;
  border: 2px solid #b3c8e8;
  background-color: #041c35;
  color: #b3c8e8;
}
.add-teaching-button:focus {
  margin-right: 10px;
  margin-left: 10px;
  border: 2px solid #b3c8e8;
  background-color: #041c35;
  color: #b3c8e8;
}
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.modal-content {
  border: 2px solid #b3c8e8;
  background-color: #041c35;
  color: #b3c8e8;
  padding: 20px;
  border-radius: 5px;
  margin: 0 20px;
  max-width: 700px;
  align-items: center;
}
.form-row {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.dropdown-menu-scrollable {
  max-height: 200px;
  overflow-y: auto;
  background-color: #041c35;
  color: #b3c8e8;
}
.dropdown-menu-scrollable li:hover {
  background-color: #b3c8e8;
  color: #041c35;
  font-style: italic;
}
.course-description-box {
  position: fixed;
  bottom: 5%;
  left: 25%;
  right: 25%;
  width: 50%;
  background-color: #b3c8e8;
  color: #041c35;
  padding: 2%;
  border-radius: 10px;
  font-weight: bolder;
}
.course-description-border {
  border: 3px solid #041c35;
}
.close {
  position: absolute;
  top: 2px;
  right: 5px;
  color: #b3c8e8;
  font-size: 20px;
  font-weight: bold;
  cursor: pointer;
}
.success-alert {
  position: fixed;
  top: 65%;
  left: 50%;
  transform: translate(-50%, -50%);
  border: solid 1.5px #002020;
  background-color: #6ff7f6;
  color: #002020;
  padding-top: 1%;
  padding-bottom: 1%;
  padding-left: 5%;
  padding-right: 5%;
  border-radius: 10px;
  z-index: 9999;
  font-weight: bolder;
}
.error-alert {
  position: fixed;
  top: 65%;
  left: 50%;
  transform: translate(-50%, -50%);
  border: 1.5px solid #930009;
  background-color: #ffb4ab;
  color: #690004;
  padding-top: 1%;
  padding-bottom: 1%;
  padding-left: 5%;
  padding-right: 5%;
  border-radius: 10px;
  z-index: 9999;
  font-weight: bolder;
}

.close {
    color: #b3c8e8;
}

.button-dropdown-for-add {
  border: solid 2px #002020;
  background-color: #6ff7f6;
  color: #002020;
}

.button-dropdown-for-add:hover {
  border: 2px solid #6ff7f6;
  background-color: #002020;
  color: #6ff7f6;
}

.button-dropdown-for-add:focus {
  border: 2px solid #6ff7f6;
  background-color: #002020;
  color: #6ff7f6;
}


</style>
