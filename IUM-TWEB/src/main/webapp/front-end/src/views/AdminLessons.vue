<template>
  <h1 style="padding-top: 1%">Lessons area</h1>

  <div class="container-wrapper scrollable-container">
    <div class="lesson-container">
      <div v-for="lesson in info"
           :key="lesson.id"
           :class="{'lesson-item': true, 'lesson-active': lesson.status === 'Active' && !lesson.isSelected, 'lesson-done': lesson.status === 'Done' && !lesson.isSelected, 'lesson-inactive': lesson.status === 'Deleted' && !lesson.isSelected, 'lesson-disable': lesson.status === 'Active' && lesson.isSelected}"
           @click="toggleLessonSelection(lesson)">
          {{ lesson.username }} - <br> {{ lesson.course }} - <br> {{ lesson.teacher }} - <br> {{ lesson.date + ' at ' + lesson.hour }}
      </div>
    </div>
  </div>

  <div v-if="loading">Loading...</div>
  <div class="button-container">
    <button @click="setDeleted" :class="{ 'active-button-disable': selectedLessons.length !== 0 }"
            :disabled="!selectedLessons.length">
      Disable
    </button>
  </div>
</template>

<script>
import {store} from "@/main";
import {getAllLessons, setDeleted} from "@/services/fetchData";

export default {
  name: "AdminLessons",
  data() {
    return {
      user: store.data.username,
      info: [],
      selectedLessons: [],
      loading: false,
    }
  },
  created() {
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    if (token && username) {
      store.data.token = token;
      store.data.username = username;
    }
    this.getAllLessons();
  },
  methods: {
    async getAllLessons() {
      this.loading = true;
      try {
        const response = await getAllLessons();
        this.info = response;
      } catch (error) {
        console.error('Error retrieving lessons:', error);
      } finally {
        this.loading = false;
      }
    },
    toggleLessonSelection(lesson) {
      if (lesson.status === 'Active') {
        lesson.isSelected = !lesson.isSelected;
        if (lesson.isSelected) {
          this.selectedLessons.push(lesson);
        } else {
          const index = this.selectedLessons.findIndex((selectedLesson) =>
              selectedLesson === lesson
          );
          if (index !== -1) {
            this.selectedLessons.splice(index, 1);
          }
        }
      }
    },
    selectLesson(lesson) {
      if (lesson.selected) {
        this.selectedLessons.push(lesson);
      } else {
        const index = this.selectedLessons.findIndex(selectedLesson => selectedLesson === lesson);
        if (index !== -1) {
          this.selectedLessons.splice(index, 1);
        }
      }
    },
    async setDeleted() {
      this.loading = true;
      try {
        await Promise.all(
            this.selectedLessons.map(lesson => {
              const [name, surname] = lesson.teacher.split(' ');
              return setDeleted(lesson.course, name, surname, lesson.username,  lesson.date, lesson.hour);
            })
        );
        await this.getAllLessons();
        this.selectedLessons = [];
      } catch (error) {
        console.error('Error marking lessons deleted:', error);
      } finally {
        this.loading = false;
      }
    },
  }
}
</script>

<style scoped>
.container-wrapper {
  margin: 0 auto;
}
.lesson-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
}
.scrollable-container {
  max-width: 90%;
  margin: 0 auto;
  overflow-y: auto;
  max-height: 500px;
}
.lesson-item {
  width: 200px;
  height: 200px;
  margin: 15px;
  padding: 20px;
  border: 1.5px solid black;
  border-radius: 5px;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
}
.lesson-active {
  border: 2px solid #003921;
  background-color: #46e098;
  color: #003921;
}
.lesson-active:hover {
  border: 2px solid #68fdb2;
  background-color: #003921;
  color: #68fdb2;
}
.lesson-done {
  border: 2px solid #041c35;
  background-color: #b3c8e8;
  color: #041c35;
  cursor: not-allowed;
}
.lesson-inactive {
  border: 2px solid #930009;
  background-color: #ffb4ab;
  color: #690004;
  cursor: not-allowed;
}
.lesson-disable {
  border: 2px solid #46e098;
  background-color: #002111;
  color: #46e098;
}
.button-container {
  display: flex;
  justify-content: center;
  margin-top: 1.2%;
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
</style>
