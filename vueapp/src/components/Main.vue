<template>
  <div class="text-center">
    <v-btn
        color="deep-purple accent-4"
        class="white--text"
        :href="naverLoginUrl"
    >
      네이버 로그인
      <v-icon right>
        mdi-open-in-new
      </v-icon>
    </v-btn>
  </div>
</template>

<script>
export default {
  name: "Main",
  data: () => ({
    naverLoginUrl: "/oauth2/authorization/naver"
  })
}
</script>

<style scoped>

</style>