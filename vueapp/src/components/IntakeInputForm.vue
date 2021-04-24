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
            :close-on-content-click="true"
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
          ></v-date-picker>
        </v-menu>
      </v-row>
      <v-row>
        <v-autocomplete
            v-model="food"
            :items="foods"
            item-text="name"
            dense
            filled
            label="음식"
            @change="selectFood"
            return-object
        ></v-autocomplete>
      </v-row>
      <v-row v-if="food !== null">
        <v-col
            v-for="nutrient in food.nutrients"
            v-bind:key="nutrient.category"
            cols="12"
            md="3"
        >
          <v-text-field
              :value="(nutrient.amount * amount)"
              :label="nutrient.category"
              type="number"
              disabled
          ></v-text-field>
        </v-col>
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
    </v-container>

    <v-btn
        :disabled="!valid"
        color="success"
        class="mr-4"
        @click="save"
    >
      저장하기
    </v-btn>

    <v-btn
        color="warning"
        @click="close"
    >
      닫기
    </v-btn>
  </v-form>
</template>

<script>
export default {
  name: "IntakeInputForm",
  props: {
    intakeId: Number,
    defaultDate: String,
  },
  data() {
    return {
      valid: true,
      foods: [],
      food: null,
      amount: 0,
      date: this.defaultDate,
    }
  },
  watch: {
    intakeId(newIntakeId) {
      this.loadIntake(newIntakeId)
    }
  },
  methods: {
    close() {
      this.$emit('close')
    },
    selectFood(food) {
      this.food = food
    },
    save() {
      this.$refs.form.validate()
      if (this.valid) {
        this.$http.post(`/api/intakes`, {
          date: this.date,
          amount: this.amount,
          foodId: this.food.id
        })
            .then(() => this.$emit('submit'))
            .catch(() => alert("저장에 실패했습니다."))
      }
    },
    loadFoods(name) {
      let params
      if (name !== "") {
        params = {
          name: name
        }
      } else {
        params = {}
      }

      this.$http.get("/api/foods", params)
          .then(response => {
            this.foods = response.data.content
          })
    },
    loadIntake(intakeId) {
      return this.$http.get(`/api/intakes/${intakeId}`)
          .then(response => {
            this.amount = response.data.amount
            this.date = response.data.date
            return this.$http.get(`/api/foods/${response.data.food.id}`)
          })
          .then(response => {
            this.food = response.data
            this.foods = [this.food]
          })
    },
  },
  mounted: function () {
    if (this.intakeId != null) {
      this.loadIntake(this.intakeId)
          .finally(() => {
          })
    } else {
      this.loadFoods()
    }
  }
}
</script>

<style scoped>

</style>