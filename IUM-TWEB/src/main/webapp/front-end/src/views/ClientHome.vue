<template>
  <header>
    Book a lesson
  </header>
  <div class="dropdown">
    <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false" @click="getAllActiveCourses" id="button-course">
      {{ this.selectedCourse }}
    </button>
    <ul class="dropdown-menu dropdown-menu-dark dropdown-menu-scrollable">
      <li v-if="info.length" v-for="course in info" class="dropdown-item"
          @click="updateDropdown1(course.title)"
          @mouseover="showCourseDescription(course.description)"
          @mouseleave="hideCourseDescription()">
        {{ course.title }}
      </li>
    </ul>
  </div>

  <div v-if="selectedCourseDescription" class="course-description-box course-description-border">
    {{ selectedCourseDescription }}
  </div>

  <div class="dropdown">
    <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false"
            :disabled="!isFirstDropdownSelected" @click="getQualifiedTeachers(this.selectedCourse)" id="button-teacher">
      {{ this.selectedTeacherName + ' ' + this.selectedTeacherSurname }}
    </button>
    <ul class="dropdown-menu dropdown-menu-dark" id="dropdown-teacher">
      <li v-if="info.length && !loading" v-for="teacher in info" class="dropdown-item"
          @click="updateDropdown2(); this.selectedTeacherName = teacher.name; this.selectedTeacherSurname = teacher.surname">
        {{ teacher.name + " " + teacher.surname }}
      </li>
    </ul>
  </div>

  <div class="datepicker-container">
    <DatePicker :placeholder="formatDate(new Date())" @click="hideButton" v-model="selectedDate" :lower-limit="new Date()"
                :upper-limit="new Date(new Date().getFullYear(), 11, 31)"
                :disabled-dates="{dates: disabledDates, predicate: isWeekend}"
                :disabled="!picker" style="text-align: center"/>
  </div>

  <div class="dropdown">
    <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false"
            :disabled="!isThirdDropdownSelected" id="button-slot"
            @click="getSlots(this.selectedTeacherName, this.selectedTeacherSurname, this.formattedDate=formatDate(this.selectedDate))">
      {{
        this.selectedSlotStart === 'Time slot' ? this.selectedSlotStart : this.selectedSlotStart + ' - ' + this.selectedSlotEnd
      }}
    </button>
    <ul class="dropdown-menu dropdown-menu-dark" id="dropdown-slot">
      <li v-if="info.length" v-for="slot in info" class="dropdown-item"
          @click="this.selectedSlotStart = slot.start; this.selectedSlotEnd = slot.end">
        {{ slot.start + " - " + slot.end }}
      </li>
    </ul>
  </div>

  <div class="confirm-booking-container">
    <button @click="bookLesson" :class="{ 'active-button': isFourthDropdownSelected }"
            v-bind:disabled="!isFourthDropdownSelected"> Confirm Booking!
    </button>
  </div>

  <div v-if="showAlert" :class="{'success-alert': true, 'error-alert': this.resp.result !== 'Lesson added successfully'}">
      {{ this.resp.result }}!
  </div>

</template>


<script>
import {bookLesson, getAllActiveCourses, getQualifiedTeachers, getSlots, logout} from "@/services/fetchData"
import DatePicker from 'vue3-datepicker'
import {isWeekend} from 'date-fns'
import {store} from "@/main";

export default {
  name: "ClientHome",
  components: {
    DatePicker
  },
  data() {
    return {
      user: store.data.username,
      selectedCourse: 'Course',
      selectedCourseDescription: '',
      selectedTeacherName: 'Teacher',
      selectedTeacherSurname: '',
      selectedSlotStart: 'Time slot',
      selectedSlotEnd: '',
      info: [],
      picker: false,
      button: true,
      disabledDates: [],
      selectedDate: null,
      formattedDate: '',
      resp: '',
      showAlert: false,
      loading: false
    }
  },
  created() {
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    if (token && username) {
      store.data.token = token;
      store.data.username = username;
    }
  },
  mounted() {
    this.$bus.emit('showClientPersonal', false);
  },
  computed: {
    isFirstDropdownSelected() {
      return this.selectedCourse !== 'Course';
    },
    isThirdDropdownSelected() {
      return this.selectedDate !== null;
    },
    isFourthDropdownSelected() {
      return this.selectedSlotStart !== 'Time slot';
    }
  },
  methods: {
    isWeekend,
    getAllActiveCourses() {
      getAllActiveCourses('getAllActiveCourses')
          .then(response => {
            this.info = response;
          })
    },
    getQualifiedTeachers() {
      this.loading = true;
      getQualifiedTeachers(this.selectedCourse)
          .then(response => {
            this.info = response;
            this.loading = false;
          })
    },
    getSlots() {
      getSlots(this.selectedTeacherName, this.selectedTeacherSurname, this.formattedDate)
          .then(response => {
            this.info = response;
          })
    },
    bookLesson() {
      bookLesson(this.selectedCourse, this.selectedTeacherName, this.selectedTeacherSurname, this.user, this.formattedDate, this.selectedSlotStart)
          .then(response => {
            this.resp = response;
            this.showAlert = true;
            setTimeout(() => {
              this.showAlert = false;
              if (response.result === "Lesson added successfully") {
                this.$router.push('/ClientPersonal');
              }
            }, 2500);
          })
    },
    updateDropdown1(item) {
      this.selectedCourse = item;
      this.selectedCourseDescription = "";
      this.selectedTeacherName = "Teacher";
      this.selectedTeacherSurname = "";
      this.selectedDate = null;
      this.selectedSlotStart = "Time slot";
      this.selectedSlotEnd = "";
      this.button = true;
      this.picker = false;
    },
    updateDropdown2() {
      this.selectedDate = null;
      this.selectedSlotStart = "Time slot";
      this.selectedSlotEnd = "";
      this.button = true;
      this.picker = true;
    },
    formatDate(date) {
      const newDate = new Date(date);
      const day = newDate.toLocaleDateString('it-IT', {day: '2-digit'});
      const month = newDate.toLocaleDateString('it-IT', {month: '2-digit'});
      const year = newDate.toLocaleDateString('it-IT', {year: 'numeric'});
      return `${year}-${month}-${day}`;
    },
    hideButton() {
      this.button = !this.button;
    },
    logout() {
      logout()
          .then(() => {
            this.$router.push('/');
          })
    },
    showCourseDescription(description) {
      this.selectedCourseDescription = description;
    },
    hideCourseDescription() {
      this.selectedCourseDescription = '';
    }
  }
}
</script>

<style scoped>
    header {
        padding-top: 5px;
        color: #191c1c;
        padding-bottom: 5px;
        font-size: xx-large;
        font-weight: bolder;
        font-style: italic;
    }
    .active-button {
        border-radius: 10px;
        offset: 10px;
        border-color: #041c35;
        background-color: #b3c8e8;
        color: #041c35;
    }
    .active-button:hover {
        border-radius: 10px;
        offset: 10px;
        border-color: #b3c8e8;
        background-color: #041c35;
        color: #b3c8e8;
    }
    .confirm-booking-container {
        border-radius: 10px;
        display: flex;
        justify-content: center;
        margin-top: 6%;
    }
    .confirm-booking-container button {
        border-radius: 10px;
        margin-top: 6%;
        padding: 1%;
    }
    .dropdown-menu-scrollable {
        max-height: 300px;
        overflow-y: auto;
        background-color: #041c35;
        color: #b3c8e8;
    }
    .dropdown-menu-scrollable li:hover {
        background-color: #b3c8e8;
        color: #041c35;
        font-style: italic;
    }
    #dropdown-teacher {
        background-color: #041c35;
        color: #b3c8e8;
    }
    #dropdown-teacher li:hover {
        background-color: #b3c8e8;
        color: #041c35;
        font-style: italic;
    }
    #dropdown-slot {
        background-color: #041c35;
        color: #b3c8e8;
    }
    #dropdown-slot li:hover {
        background-color: #b3c8e8;
        color: #041c35;
        font-style: italic;
    }
    .course-description-box {
        position: fixed;
        bottom: 17.5%;
        left: 17.5%;
        right: 17.5%;
        width: 65%;
        background-color: #b3c8e8;
        color: #041c35;
        padding: 2%;
        border-radius: 10px;
        font-weight: bolder;
    }
    .course-description-border {
        border: 3px solid #041c35;
    }
    .datepicker-container {
        display: flex;
        justify-content: center;
        align-items: center;
        margin-top: 5%;
    }
    .success-alert {
        position: fixed;
        top: 70%;
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
        top: 70%;
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
    .dropdown {
        margin-top: 2.5%;
    }
    #button-course, #button-slot, #button-teacher {
        border-color: #041c35;
        background-color: #b3c8e8;
        color: #041c35;
        margin-bottom: 10px;
    }
    #button-slot {
        margin-top: 10px;
        margin-bottom: -8px;
    }
</style>
