<script setup lang="ts">
// const count = ref(0)
import {ref} from "vue";

import axios from 'axios'
import {useRouter} from "vue-router";
import {defineProps} from "vue/dist/vue";


const router = useRouter()

const post = ref({
  id : 0,
  title : "",
  content : ""
})

const props = defineProps({
  postId: {
    type : [Number, String],
    required: true,
  }
})

axios.get('/my-backend-api/${props.postId}').then(response => {
  post.value = response.data;
});

const edit = () => {
  axios.patch('/my-backend-api/${props.postId}', post.value).then(() => {
      router.replace({name : "home"});
  });
}
</script>

<template>
  <!--  <p>안녕하세요</p> <button @click="count += 1">버튼을 눌러주세요</button>-->
  <!--  <p>{{ count }}</p>-->
  <div>
    <el-input v-model="post.title"/>
  </div>

  <div class="mt-2">
    <el-input v-model="post.content" type="textarea" rows="15"></el-input>
  </div>

  <div class="mt-2 d-flex justify-content-end">
    <el-button type="warning" @click="edit()">수정완료</el-button>
  </div>

</template>

<style>

</style>