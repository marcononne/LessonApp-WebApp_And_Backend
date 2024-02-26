<template>
  <h1 style="padding-top: 1%">Courses area</h1>

  <div class="container-wrapper scrollable-container">
    <div class="course-container">
      <div v-for="course in info"
           :key="course.id"
           :class="{'course-item': true, 'course-active': course.status === true && !course.isSelected, 'course-inactive': course.status === false && !course.isSelected, 'course-disable': course.status === true && course.isSelected}"
           @click="toggleCourseSelection(course)">
        {{ course.title }}
      </div>
    </div>
  </div>

  <div v-if="loading">Loading...</div>
  <h4 style="padding-top: 2%; padding-bottom: 1%;">Remember that when you remove a course, any teaching and lesson associated with it is also removed!</h4>
  <div class="button-container">
    <button @click="setCourseDisabled" :class="{ 'active-button-disable': selectedCourses.length !== 0 }"
            :disabled="!selectedCourses.length">
      Disable
    </button>
    <button @click="showModal = true" class="add-course-button">Add new course</button>
  </div>

  <div v-if="showModal" class="modal">
    <div class="modal-content">
      <span class="close" @click="showModal = false">X</span>
      <form @submit.prevent="addCourse">
        <div class="form-row">
          <label for="title">Title:</label>
          <input type="text" id="title" v-model="newCourse.title" required>
        </div>
        <br>
        <div class="form-row">
          <label for="description">Description:</label>
          <input type="text" id="description" v-model="newCourse.description" required>
        </div>
        <br>
        <button type="submit" :class="{'active-add-button': newCourse.title !== '' && newCourse.description !== ''}"
                :disabled="newCourse.title === '' || newCourse.description === ''">Add</button>
      </form>
    </div>
  </div>

  <div v-if="showAlert" :class="{'success-alert': true, 'error-alert': this.resp.result !== 'Course added successfully'}">
    {{ this.resp.result }}!
  </div>
</template>

<script>
import {store} from "@/main";
import {addCourse, setCourseDisabled, getAllCourses} from "@/services/fetchData";

export default {
  name: "AdminCourses",
  data() {
    return {
      user: store.data.username,
      info: [],
      selectedCourses: [],
      loading: false,
      newCourse: {
        title: '',
        description: ''
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
    this.getAllCourses();
  },
  methods: {
    async getAllCourses() {
      this.loading = true;
      try {
        const response = await getAllCourses();
        this.info = response;
      } catch (error) {
        console.error('Error retrieving courses:', error);
      } finally {
        this.loading = false;
      }
    },
    toggleCourseSelection(course) {
      if (course.status === true) {
        course.isSelected = !course.isSelected;
        if (course.isSelected) {
          this.selectedCourses.push(course);
        } else {
          const index = this.selectedCourses.findIndex((selectedCourse) =>
              selectedCourse === course
          );
          if (index !== -1) {
            this.selectedCourses.splice(index, 1);
          }
        }
      }
    },
    async setCourseDisabled() {
      this.loading = true;
      try {
        await Promise.all(
            this.selectedCourses.map(course => {
              return setCourseDisabled(course.title);
            })
        )
        await this.getAllCourses();
        this.selectedCourses = [];
      } catch (error) {
        console.error('Error marking courses unactive:', error);
      } finally {
        this.loading = false;
      }
    },
    addCourse() {
      const {title, description} = this.newCourse;
      this.showModal = false;
      addCourse(title, description).then(response => {
        this.resp = response;
        this.showAlert = true;
        setTimeout(() => {
          this.showAlert = false;
        }, 2500);
        this.refreshCourses();
        this.showModal = false;
      })
      this.newCourse.title = '';
      this.newCourse.description = '';
    },
    refreshCourses() {
      this.getAllCourses();
    }
  }
}
</script>

<style scoped>
.container-wrapper {
  max-width: 700px;
  margin: 0 auto;
}
.course-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
}
.scrollable-container {
  max-width: 700px;
  margin: 0 auto;
  overflow-y: auto;
  max-height: 470px;
}
.course-item {
  width: 130px;
  height: 130px;
  margin: 20px;
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
.course-active {
  border: 2px solid #003921;
  background-color: #46e098;
  color: #003921;
}
.course-active:hover {
  border: 2px solid #68fdb2;
  background-color: #003921;
  color: #68fdb2;
}
.course-inactive {
  border: 2px solid #930009;
  background-color: #ffb4ab;
  color: #690004;
  cursor: not-allowed;
}
.course-disable {
  border: 2px solid #46e098;
  background-color: #002111;
  color: #46e098;
}
button {
  margin-right: 10px;
  margin-left: 10px;
  border-radius: 5px;
}
.button-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
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
.add-course-button {
  margin-right: 10px;
  margin-left: 10px;
  color: #041c35;
  background-color: #b3c8e8;
}
.add-course-button:hover {
  margin-right: 10px;
  margin-left: 10px;
  color: #b3c8e8;
  background-color: #041c35;
}
.add-course-button:focus {
  margin-right: 10px;
  margin-left: 10px;
  color: #b3c8e8;
  background-color: #041c35;
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
  background-color: #041c35;
  color: #b3c8e8;
  padding: 20px;
  border: 2px solid #b3c8e8;
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
.active-add-button {
  border: solid 2px #002020;
  background-color: #6ff7f6;
  color: #002020;
}

.active-add-button:hover {
  border: 2px solid #6ff7f6;
  background-color: #002020;
  color: #6ff7f6;
}

.active-add-button:focus {
  border: 2px solid #6ff7f6;
  background-color: #002020;
  color: #6ff7f6;
}

</style>
