<template>
  <h1 style="padding-top: 1%">Teachers area</h1>

  <div class="container-wrapper scrollable-container">
    <div class="teacher-container">
      <div v-for="teacher in info"
           :key="teacher.id"
           :class="{'teacher-item': true, 'teacher-active': teacher.status === true && !teacher.isSelected, 'teacher-inactive': teacher.status === false && !teacher.isSelected, 'teacher-disable': teacher.status === true && teacher.isSelected}"
           @click="toggleTeacherSelection(teacher)">
        {{ teacher.name }}<br>{{ teacher.surname }}
      </div>
    </div>
  </div>

  <div v-if="loading">Loading...</div>
  <h4 style="padding-top: 2%; padding-bottom: 1%;">Remember that when you remove a teacher, any teaching and lesson associated with it is also removed!</h4>
  <div class="button-container">
    <button @click="setTeacherDisabled" :class="{ 'active-button-disable': selectedTeachers.length !== 0 }"  :disabled="!selectedTeachers.length">
      Disable
    </button>
    <button @click="showModal = true" class="add-teacher-button">Add new teacher</button>
  </div>

  <div v-if="showModal" class="modal">
    <div class="modal-content">
      <span class="close" @click="showModal = false">X</span>
      <form @submit.prevent="addTeacher">
        <div class="form-row">
          <label for="name">Name:</label>
          <input type="text" id="name" v-model="newTeacher.name" required>
        </div>
        <br>
        <div class="form-row">
          <label for="surname">Surname:</label>
          <input type="text" id="surname" v-model="newTeacher.surname" required>
        </div>
        <br>
        <button type="submit" :class="{'active-add-button': newTeacher.name !== '' && newTeacher.surname !== ''}"
                :disabled="newTeacher.name === '' || newTeacher.surname === ''">Add</button>
      </form>
    </div>
  </div>

  <div v-if="showAlert" :class="{'success-alert': true, 'error-alert': this.resp.result !== 'Teacher added successfully'}">
    {{ this.resp.result }}!
  </div>
</template>

<script>
import {store} from "@/main";
import {addTeacher, getAllTeachers, setTeacherDisabled} from "@/services/fetchData";

export default {
  name: "AdminTeachers",
  data() {
    return {
      user: store.data.username,
      info: [],
      selectedTeachers: [],
      loading: false,
      newTeacher: {
        name: "",
        surname: "",
      },
      showModal: false,
      showAlert: false,
      resp: ''
    };
  },
  created() {
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    if (token && username) {
      store.data.token = token;
      store.data.username = username;
    }
    this.getAllTeachers();
  },
  methods: {
    async getAllTeachers() {
      this.loading = true;
      try {
        const response = await getAllTeachers();
        this.info = response.map((teacher) => ({
          ...teacher,
          isSelected: false
        }));
      } catch (error) {
        console.error("Error retrieving teachers:", error);
      } finally {
        this.loading = false;
      }
    },
    toggleTeacherSelection(teacher) {
      if (teacher.status === true) {
        teacher.isSelected = !teacher.isSelected;
        if (teacher.isSelected) {
          this.selectedTeachers.push(teacher);
        } else {
          const index = this.selectedTeachers.findIndex((selectedTeacher) =>
              selectedTeacher === teacher
          );
          if (index !== -1) {
            this.selectedTeachers.splice(index, 1);
          }
        }
      }
    },
    async setTeacherDisabled() {
      this.loading = true;
      try {
        await Promise.all(
            this.selectedTeachers.map((teacher) => {
              return setTeacherDisabled(teacher.name, teacher.surname);
            })
        );
        await this.getAllTeachers();
        this.selectedTeachers = [];
      } catch (error) {
        console.error("Error marking teachers inactive:", error);
      } finally {
        this.loading = false;
      }
    },
    addTeacher() {
      const {name, surname} = this.newTeacher;
      this.showModal = false;
      addTeacher(name, surname).then((response) => {
        this.resp = response;
        this.showAlert = true;
        setTimeout(() => {
          this.showAlert = false;
        }, 2500);
        this.refreshTeachers();
        this.showModal = false;
      });
      this.newTeacher.name = "";
      this.newTeacher.surname = "";
    },
    refreshTeachers() {
      this.getAllTeachers();
    }
  },
};
</script>

<style scoped>
.container-wrapper {
  max-width: 700px;
  margin: 0 auto;
}
.teacher-container {
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
.teacher-item {
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
.teacher-active {
  border: 2px solid #003921;
  background-color: #46e098;
  color: #003921;
}
.teacher-active:hover {
  border: 2px solid #68fdb2;
  background-color: #003921;
  color: #68fdb2;
}
.teacher-inactive {
  border: 2px solid #930009;
  background-color: #ffb4ab;
  color: #690004;
  cursor: not-allowed;
}
.teacher-disable {
  border: 2px solid #46e098;
  background-color: #002111;
  color: #46e098;
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
.add-teacher-button {
  margin-right: 10px;
  margin-left: 10px;
  border: 2px solid #041c35;
  background-color: #b3c8e8;
  color: #041c35;
}
.add-teacher-button:hover {
  margin-right: 10px;
  margin-left: 10px;
  border: 2px solid #b3c8e8;
  background-color: #041c35;
  color: #b3c8e8;
}
.add-teacher-button:focus {
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
