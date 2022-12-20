<template>
  <div class="personEdit">
    <div class="personEdit__content">
      <v-text-field label="Primeiro nome" color="primary" variant="underlined" required v-model="firstName" />
      <v-text-field label="Último nome" color="primary" variant="underlined" v-model="lastName" />
      <v-text-field label="Data de nascimento" color="primary" variant="underlined" v-model="birthDate" type="date" />
      <div class=" personEdit__btns">
        <v-btn color="primary" :loading="saving" @click="save">Salvar</v-btn>
        <v-btn>Cancelar</v-btn>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  data: () => ({
    saving: false,
    firstName: '',
    lastName: '',
    birthDate: ''
  }),
  methods: {
    async save() {
      try {
        this.saving = true;
        const requestBody = { firstName: this.firstName, lastName: this.lastName, birthDate: this.birthDate };
        const options = {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(requestBody)
        };
        await fetch("/api/v1/person", options);
        this.$router.push('/');
      } catch (e) {
        console.error(e);
      } finally {
        this.saving = false;
      }
    }
  }
});
</script>

<style scoped>
.personEdit {
  padding: 20px;
  display: flex;
  justify-content: center;
}

.personEdit__content {
  max-width: 600px;
  width: 100%;
}

.personEdit__btns {
  display: flex;
  gap: 12px;
  justify-content: flex-end;
}
</style>
