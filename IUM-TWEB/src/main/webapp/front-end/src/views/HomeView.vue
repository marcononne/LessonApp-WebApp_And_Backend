<template>
  <div>
    <h1 class="main-title">Welcome to the online tutoring booking service created by Marco and Federico</h1>
    <span>
      <h3 class="main-description">
        Our site allows you to quickly and conveniently book personalized lessons in various subjects, which can be
        followed online in streaming, with trained and qualified teachers, able to offer you the best teaching
        experience in the shortest possible time.
      </h3>
      <div class="image-sequence">
        <figure>
          <img src="../assets/first.jpeg" alt="Image containing three stacked books" height="150" width="150">
        </figure>
        <div class="arrow">
          <img src="../assets/arrow.png" alt="Arrow icon">
        </div>
        <figure>
          <img src="../assets/30L.jpg" alt="Image containing three stacked books" height="150" width="150">
        </figure>
        <div class="arrow">
          <img src="../assets/arrow.png" alt="Arrow icon">
        </div>
        <figure>
          <img src="../assets/immagine.jpeg" alt="Image containing three stacked books" height="150" width="150">
        </figure>
        <div class="arrow">
          <img src="../assets/arrow.png" alt="Arrow icon">
        </div>
        <figure>
          <img src="../assets/last.jpeg" alt="Image containing three stacked books" height="150" width="150">
        </figure>
      </div>
      <div class="course-list">
        <div v-for="(course, index) in info" :key="index" class="course-block" @mouseover="showDescription(index)" @mouseleave="hideDescription(index)">
          <div class="course-title">
            {{ course.title }}
          </div>
          <div class="course-description" v-if="expandedCourses.includes(index)">
            {{ course.description }}
          </div>
        </div>
      </div>
    </span>
  </div>
</template>

<script>
import {getAllActiveCourses} from "@/services/fetchData";


export default {
  name: 'HomeView',
  data() {
    return {
      info: [],
      expandedCourses: []
    }
  },
  mounted() {
    this.getAllActiveCourses();
  },
  methods: {
    getAllActiveCourses() {
      getAllActiveCourses('getAllActiveCourses')
          .then(response => {
            this.info = response;
          })
    },
    showDescription(index) {
      if (!this.expandedCourses.includes(index)) {
        this.expandedCourses.push(index);
      }
    },
    hideDescription(index) {
      const courseIndex = this.expandedCourses.indexOf(index);
      if (courseIndex !== -1) {
        this.expandedCourses.splice(courseIndex, 1);
      }
    }
  }
}
</script>

<style>
    .course-list {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
    }
    .course-block {
        width: 280px;
        height: 280px;
        background-color: #d3e4ff;
        color: #041c35;
        margin: 10px;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        transition: all 0.3s;
    }
    .course-block:hover {
        transform: scale(1.10);
        background-color: #041c35;
        color: #d3e4ff;
    }
    .course-title {
        font-size: 18px;
        font-weight: bold;
        text-align: center;
    }
    .course-description {
        font-size: 14px;
        text-align: center;
        display: none;
        margin-top: 10px;
        margin-left: 4px;
        margin-right: 4px;
        color: #d3e4ff;
    }
    .course-block:hover .course-title {
        font-size: 16px;
    }
    .course-block:hover .course-description {
        display: block;
    }
    .main-title {
        text-align: center;
        margin-top: 2%;
        font-style: italic;
        font-size: xxx-large;
        color: #041c35;
    }
    .main-description {
        text-align: center;
        margin-left: 5%;
        margin-right: 5%;
        margin-bottom: 3%;
        font-style: italic;
        color: #041c35;
    }
    .image-sequence {
        display: flex;
        align-items: center;
        justify-content: center;
        margin-top: 20px;
    }
    .image-sequence figure {
        margin: 0;
    }
    .arrow {
        display: flex;
        align-items: center;
        margin: 0 20px;
    }
    .arrow img {
        height: 50px;
        width: 50px;
    }
</style>

