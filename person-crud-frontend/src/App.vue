<template>
  <v-app>
    <v-app-bar elevation="1">
      <v-app-bar-title>Cadastro de pessoas</v-app-bar-title>
      <template v-slot:append>
        <v-btn icon="mdi-plus" @click="$router.push({ name: 'AddPerson' })" v-if="displayPlusBtn" />
        <v-btn icon="mdi-close" @click="$router.push('/')" v-else />
      </template>
    </v-app-bar>
    <v-main>
      <router-view></router-view>
    </v-main>
  </v-app>
</template>

<script lang="ts">
import PersonList from '@/components/PersonList.vue';
import { defineComponent } from 'vue';

export default defineComponent({
  data: () => ({

  }),
  computed: {
    displayPlusBtn() {
      return this.$route.name !== 'AddPerson';
    }
  }
});
</script>
<v-app-bar-title></v-app-bar-title>