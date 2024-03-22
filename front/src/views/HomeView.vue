<script setup lang="ts">
import axios from "axios";
import {ref} from "vue";
import {useRouter} from "vue-router";

const posts = ref([]);

const router = useRouter();

axios.get("/my-backend-api/posts?page=1&size=5").then(response => {
    response.data.forEach((r:any) => {
      posts.value.push(r);
    })
});

</script>

<template>
  <ul>
    <li v-for="post in posts" :key="post.id">
      <div class="title">
        <router-link :to="{name: 'read',params : {postId : post.id}}">{{post.title}}</router-link>
      </div>

      <div class="content">
        {{post.content}}
      </div>

      <div class="sub d-flex">
        <div class="category">개발</div>
        <div class="regDate">2022-06-01</div>
      </div>
    </li>
  </ul>
</template>


<style scoped lang="scss">
ul{
  list-style: none;
  padding: 0;

  li {
    margin-bottom: 1.6rem;

    .title {
      a{
        font-size: 1.2rem;
        color: #383838;
        text-decoration: none;
      }

      &:hover {
        text-decoration: underline;
      }
    }

    .content{
      font-size: 0.95rem;
      margin-top: 8px;
      color: #6b6b6b;
    }

    &:last-child{
      margin-bottom: 0;
    }

    .sub{
      margin-top: 4px;
      font-size: 0.78rem;

      .regDate{
        color: #6b6b6b;
      }
    }

  }
}


li
</style>