<template>
  <v-form
      ref="form"
      v-model="valid"
      lazy-validation
  >
    <v-container>
      <v-row>
        <v-menu
            ref="menu"
            v-model="menu"
            :close-on-content-click="false"
            transition="scale-transition"
            offset-y
            min-width="auto"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-text-field
                v-model="date"
                label="날짜"
                prepend-icon="mdi-calendar"
                readonly
                v-bind="attrs"
                v-on="on"
            ></v-text-field>
          </template>
          <v-date-picker
              ref="picker"
              v-model="date"
              :max="new Date().toISOString().substr(0, 10)"
              min="1950-01-01"
              @change="save"
          ></v-date-picker>
        </v-menu>
      </v-row>
      <v-row>
        <v-text-field
            v-model="amount"
            hide-details
            single-line
            label="수량"
            type="number"
        />
      </v-row>
      <v-divider></v-divider>

      <v-row>
        <v-autocomplete
            v-model="food"
            :items="foods"
            dense
            filled
            label="음식"
        ></v-autocomplete>
      </v-row>
      <v-row>
        <v-col
            cols="12"
            md="3"
        >
          <v-text-field
              v-model="calorie"
              label="칼로리"
              type="number"
              disabled
          ></v-text-field>
        </v-col>

        <v-col
            cols="12"
            md="3"
        >
          <v-text-field
              v-model="carbohydrate"
              label="탄수화물"
              type="number"
              disabled
          ></v-text-field>
        </v-col>

        <v-col
            cols="12"
            md="3"
        >
          <v-text-field
              v-model="protein"
              label="단백질"
              type="number"
              disabled
          ></v-text-field>
        </v-col>

        <v-col
            cols="12"
            md="3"
        >
          <v-text-field
              v-model="fat"
              label="지방"
              type="number"
              disabled
          ></v-text-field>
        </v-col>
      </v-row>
    </v-container>

    <v-btn
        :disabled="!valid"
        color="success"
        class="mr-4"
        @click="validate"
    >
      Validate
    </v-btn>

    <v-btn
        color="error"
        class="mr-4"
        @click="reset"
    >
      Reset Form
    </v-btn>

    <v-btn
        color="warning"
        @click="resetValidation"
    >
      Reset Validation
    </v-btn>
  </v-form>
</template>

<script>
export default {
  name: "IntakeInputForm",
  data: () => ({
    valid: true,
    foods: [
      '식량1',
      '식량2',
      '식량3',
      '식량4',
    ],
    food: null,
    amount: 0,
    calorie: 200,
    carbohydrate: 10,
    fat: 20,
    protein: 30,
    date: null,
    menu: false,
  }),
  watch: {
    menu (val) {
      val && setTimeout(() => (this.$refs.picker.activePicker = 'YEAR'))
    },
  },
  methods: {
    validate () {
      this.$refs.form.validate()
    },
    reset () {
      this.$refs.form.reset()
    },
    resetValidation () {
      this.$refs.form.resetValidation()
    },
    save (date) {
      this.$refs.menu.save(date)
    },
  },
}
</script>

<style scoped>

</style>