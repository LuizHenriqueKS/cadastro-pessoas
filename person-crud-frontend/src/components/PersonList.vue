<template>
  <div class="fill-height">
    <div class="d-flex align-center justify-center fill-height" v-if="loading">
      <v-progress-circular indeterminate color="primary" size="60" />
    </div>
    <v-table v-if="!loading">
      <thead>
        <tr>
          <th>
            ID
          </th>
          <th>
            Primeiro nome
          </th>
          <th>
            Último nome
          </th>
          <th>
            Data de nascimento
          </th>
          <th>
            Ações
          </th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="person of personList">
          <td>{{ person.id }}</td>
          <td>{{ person.firstName }}</td>
          <td>{{ person.lastName }}</td>
          <td>{{ formatDate(person.birthDate) }}</td>
          <td>
            <v-btn icon="mdi-delete" elevation="0" size="small" title="Deletar" @click="deletePerson(person)" />
          </td>
        </tr>
      </tbody>
    </v-table>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import PersonDto from '@/dto/PersonDto';

export default defineComponent({
  data: () => ({
    loading: true,
    personList: [] as PersonDto[]
  }),
  async mounted() {
    await this.loadPersonList();
  },
  methods: {
    async loadPersonList() {
      try {
        this.loading = true;
        const response = await fetch('/api/v1/person');
        this.personList = await response.json();
      } finally {
        this.loading = false;
      }
    },
    formatDate(date) {
      if (!date) return date;
      const [year, month, day] = date.split('-');
      return `${day}/${month}/${year}`;
    },
    async deletePerson(person) {
      if (confirm(`Tem certeza que deseja deletar "${person.firstName}"?`)) {
        try {
          this.loading = true;
          await fetch('/api/v1/person/' + person.id, { method: 'DELETE' });
        } finally {
          this.loadPersonList();
        }
      }
    }
  }
})
</script>
