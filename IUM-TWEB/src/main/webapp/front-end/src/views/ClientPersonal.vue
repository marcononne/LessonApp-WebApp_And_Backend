<template>
  <div class="main-content">
    <h1 id="title" style="padding-top: 1%"> {{ this.user }}'s Personal Area </h1>

    <div class="active-lessons-area">
      <h2>Active Lessons</h2>
      <div class="active-lessons">
        <div v-for="lesson in activeLessons" v-if="activeLessons.length > 0">
          <button @click="selectLesson(lesson)"
                  :class="{'lesson-box-active': true, 'lesson-box-selected': selectedLessons.includes(lesson)}">
            {{ lesson.course }} <br>
            {{ lesson.teacher }} <br>
            {{ lesson.day }} <br>
            {{ lesson.date }} <br>
            {{ lesson.hour }}:00 - {{ lesson.hour + 1 }}:00 <br>
          </button>
        </div>
        <div v-else class="alternative-text">
          You don't have any lessons scheduled for the future...
        </div>
      </div>
    </div>

    <button v-if="activeLessons.length > 0" @click="setDone"
            :class="{ 'active-button-done': selectedLessons.length !== 0 }"
            v-bind:disabled="!selectedLessons.length" class="rounded-button"> Done
    </button>
    <button v-if="activeLessons.length > 0" @click="setDeleted"
            :class="{ 'active-button-delete': selectedLessons.length !== 0 }"
            v-bind:disabled="!selectedLessons.length" class="rounded-button"> Delete
    </button>

    <div class="done-lessons-area" style="margin-top: 1.5%">
      <h2>Done Lessons</h2>
      <div class="done-lessons">
        <div v-for="lesson in doneLessons" v-if="doneLessons.length > 0">
          <div class="single-lesson-done">
            {{ lesson.course }} <br>
            {{ lesson.teacher }} <br>
            {{ lesson.day }} <br>
            {{ lesson.date }} <br>
            {{ lesson.hour }}:00 - {{ lesson.hour + 1 }}:00 <br>
          </div>
        </div>
        <div v-else class="alternative-text">
          You don't have any completed lessons in your history...
        </div>
      </div>
    </div>

    <div class="deleted-lessons-area" style="margin-top: 1.5%; margin-bottom: 5%">
      <h2>Deleted Lessons</h2>
      <div class="deleted-lessons">
        <div v-for="lesson in deletedLessons" v-if="deletedLessons.length > 0">
          <div class="single-lesson-deleted">
            {{ lesson.course }} <br>
            {{ lesson.teacher }} <br>
            {{ lesson.day }} <br>
            {{ lesson.date }} <br>
            {{ lesson.hour }}:00 - {{ lesson.hour + 1 }}:00 <br>
          </div>
        </div>
        <div v-else class="alternative-text">
          You don't have any deleted lessons in your history...
        </div>
      </div>
    </div>


  </div>
</template>

<script>
import {store} from "@/main";
import {getLessons, logout, setDeleted, setDone} from "@/services/fetchData";

export default {
  name: "ClientPersonal",
  data() {
    return {
      user: store.data.username,
      status: '',
      activeLessons: [],
      doneLessons: [],
      deletedLessons: [],
      selectedLessons: []
    }
  },
  created() {
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    if (token && username) {
      store.data.token = token;
      store.data.username = username;
    }
    this.getLessons('active');
    this.getLessons('done');
    this.getLessons('deleted');
  },
  mounted() {
    this.$bus.emit('showClientPersonal', true);
  },
  methods: {
    getLessons(status) {
      this.status = status;
      this.user = localStorage.getItem('username');
      getLessons(this.user, status)
          .then(response => {
            if (status === 'active') {
              if (response != null)
                this.activeLessons = response;
            } else if (status === 'done') {
              if (response != null)
                this.doneLessons = response;
            } else if (status === 'deleted') {
              if (response != null)
                this.deletedLessons = response;
            }
          })
    },
    selectLesson(lesson) {
      if (this.selectedLessons.includes(lesson)) {
        const index = this.selectedLessons.indexOf(lesson);
        this.selectedLessons.splice(index, 1);
      } else
        this.selectedLessons.push(lesson);
    },
    setDone() {
      const updatePromises = this.selectedLessons.map(lesson => {
        const [name, surname] = lesson.teacher.split(' ');
        return setDone(lesson.course, name, surname, lesson.username, lesson.date, lesson.hour);
      });
      Promise.all(updatePromises)
          .then(() => {
            this.selectedLessons.forEach(lesson => {
              const index = this.activeLessons.findIndex(activeLesson => activeLesson === lesson);
              if (index !== -1) {
                this.activeLessons.splice(index, 1);
                this.doneLessons.push(lesson);
              }
            });
            this.selectedLessons = [];
          })
          .catch(error => {
            console.error('Error marking lessons done:', error);
          });
    },
    setDeleted() {
      const updatePromises = this.selectedLessons.map(lesson => {
        const [name, surname] = lesson.teacher.split(' ');
        return setDeleted(lesson.course, name, surname, lesson.username, lesson.date, lesson.hour);
      })
      Promise.all(updatePromises)
          .then(() => {
            this.selectedLessons.forEach(lesson => {
              const index = this.activeLessons.findIndex(activeLesson => activeLesson === lesson);
              if (index !== -1) {
                this.activeLessons.splice(index, 1);
                this.deletedLessons.push(lesson);
              }
            });
            this.selectedLessons = [];
          })
          .catch(error => {
            console.error('Error marking lessons deleted:', error);
          })
    },
    logout() {
      logout()
          .then(() => {
            this.$router.push('/');
          })
    }
  }
}

</script>

<style scoped>
.main-content {
  color: #041c35;
}

#title {
  font-style: italic;
  font-weight: bolder;
  padding-bottom: 1%;
}

.active-button-done {
  background-color: lawngreen;
}

.active-button-delete {
  background-color: red;
}

.rounded-button {
  border-radius: 10px;
  margin: 1%
}

.active-lessons-area {
  max-width: 1500px;
  margin: 0 auto;
  overflow-y: auto;
  max-height: 500px;
}

.active-lessons {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
}

.lesson-box-active {
  width: 200px;
  height: 200px;
  margin: 10px;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  border: 2px solid #041c35;
  background-color: #b3c8e8;
  color: #041c35;
}

.lesson-box-active:hover {
  width: 200px;
  height: 200px;
  margin: 10px;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  border: 2px solid #b3c8e8;
  background-color: #1c314b;
  color: #b3c8e8;
}

.lesson-box-selected {
  width: 200px;
  height: 200px;
  margin: 10px;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  border: 2px solid #b3c8e8;
  background-color: #041c35;
  color: #b3c8e8;
}

.lesson-box-selected:hover {
  width: 200px;
  height: 200px;
  margin: 10px;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  border: 2px solid #b3c8e8;
  background-color: #041c35;
  color: #b3c8e8;
}

.done-lessons-area {
  max-width: 1500px;
  margin: 0 auto;
  overflow-y: auto;
  max-height: 500px;
}

.done-lessons {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
}

.single-lesson-done {
  width: 200px;
  height: 200px;
  margin: 10px;
  padding: 10px;
  border-radius: 5px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  border: 2px solid #003921;
  background-color: #46e098;
  color: #003921;
  cursor: not-allowed;
}

.deleted-lessons-area {
  max-width: 1500px;
  margin: 0 auto;
  overflow-y: auto;
  max-height: 500px;
}

.deleted-lessons {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
}

.single-lesson-deleted {
  width: 200px;
  height: 200px;
  margin: 10px;
  padding: 10px;
  border-radius: 5px;
  cursor: not-allowed;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  border: 2px solid #930009;
  background-color: #ffb4ab;
  color: #690004;
}

.alternative-text {
  font-size: x-large;
  font-weight: bolder;
  font-style: italic;
  color: #041c35;
  margin-top: 1.5%;
  margin-bottom: 0.5%;
}
</style>
