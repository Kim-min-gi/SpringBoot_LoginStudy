<script setup lang="ts">
// const count = ref(0)
import {ref} from "vue";

import axios from 'axios'

const title = ref("")
const content = ref("")

const write = () => {
  console.log(title.value,content.value)
  axios.post("/my-backend-api/posts",{
    title: title.value,
    content: content.value
  });
  // alert(title + " / " + content)
}
</script>

<template>
<!--  <p>안녕하세요</p> <button @click="count += 1">버튼을 눌러주세요</button>-->
<!--  <p>{{ count }}</p>-->
  <div>
    <el-input v-model="title" placeholder="제목을 입력해주세요"/>
  </div>

  <div class="mt-2">
    <el-input v-model="content" type="textarea" rows="15"></el-input>
  </div>

  <div class="mt-2">
   <el-button type="primary" @click="write()">글 작성완료</el-button>
  </div>

</template>

<style>

</style>