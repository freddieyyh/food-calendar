<template>
  <v-row justify="center">
    <v-dialog
        v-model="dialog"
        persistent
        max-width="600px"
    >
      <v-card>
        <v-card-title>
          <span class="headline">식단 등록</span>
        </v-card-title>
        <v-card-text>
          <intake-input-form
              v-if="dialog"
              :intake-id="intakeId"
              :default-date="date"
              @close="dialog = false"
              @submit="submit"
          ></intake-input-form>
        </v-card-text>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import IntakeInputForm from "@/components/IntakeInputForm";

export default {
  name: "IntakeDialog",
  components: {
    IntakeInputForm,
  },
  model: {
    prop: 'open',
    event: 'change'
  },
  props: {
    open: Boolean,
    intakeId: Number,
    date: String,
  },
  data: function () {
    return {
      dialog: this.open,
    }
  },
  watch: {
    dialog(newDialog) {
      this.$emit("change", newDialog)
    },

    open(newOpen) {
      this.dialog = newOpen
    }
  },
  methods: {
    submit() {
      this.dialog = false
      this.$emit('submit')
    }
  }
}
</script>

<style scoped>

</style>