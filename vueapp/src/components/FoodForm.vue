<template>
  <v-card>
    <v-card-title>
      <span class="headline">{{ formTitle }}</span>
    </v-card-title>

    <v-card-text>
      <v-container>
        <v-row>
          <v-col
              cols="12"
              sm="12"
              md="12"
          >
            <v-text-field
                v-model="editItem.name"
                label="음식명"
            ></v-text-field>
          </v-col>
        </v-row>
        <v-row>
          <v-col
              cols="6"
              sm="6"
              md="6"
          >
            <v-select
                v-model="editItem.servingSize.unit"
                :items="servingUnits"
                label="Filled style"
            ></v-select>
          </v-col>
          <v-col
              cols="6"
              sm="6"
              md="6"
          >
            <v-text-field
                type="number"
                v-model="editItem.servingSize.amount"
                label="제공량"
            ></v-text-field>
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="3">
            영양성분
          </v-col>
          <v-spacer></v-spacer>
          <v-col cols="1">
            <v-icon
                v-if="unselectedNutrients.length !== 0"
                small
                @click="addNutrient"
            >
              mdi-plus
            </v-icon>
          </v-col>
        </v-row>
        <v-row v-for="(nutrient, index) in editItem.nutrients"
               :key="nutrient.category"
        >
          <v-col
              cols="5"
              sm="5"
              md="5"
          >
            <v-select
                v-model="nutrient.category"
                :items="unselectedNutrients.slice().concat(nutrient.category)"
                label="제공단위"
            ></v-select>
          </v-col>
          <v-col
              cols="5"
              sm="5"
              md="5"
          >
            <v-text-field
                type="number"
                v-model="nutrient.amount"
                label="총량"
            ></v-text-field>
          </v-col>
          <v-spacer></v-spacer>
          <v-col
              cols="1"
              sm="1"
              md="1"
          >
            <v-icon
                v-if="index !== 0"
                small
                @click="deleteNutrient(nutrient.category)"
            >
              mdi-delete
            </v-icon>
          </v-col>
        </v-row>
      </v-container>
    </v-card-text>

    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn
          color="blue darken-1"
          text
          @click="close"
      >
        Cancel
      </v-btn>
      <v-btn
          color="blue darken-1"
          text
          @click="save"
      >
        Save
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import _ from 'lodash'

export default {
  name: "FoodForm",
  props: {
    formTitle: String,
    item: Object,
  },
  data: function() {
    return {
      servingUnits: ['EACH', 'GRAM', 'KILOGRAM', 'MILLILITER', 'LITER'],
      nutrients: [
        'CALORIES', 'CARBOHYDRATE', 'FAT', 'PROTEIN'
      ],
      defaultItem: {
        name: '',
        servingSize: {
          amount : 1,
          unit: 'EACH',
        },
        nutrients: [
          {
            category: 'CALORIES',
            amount: 0,
          }
        ],
      },
      editItem: null,
    }
  },
  created(){
    this.editItem = this.item || this.defaultItem
  },
  computed: {
    unselectedNutrients() {
      const selectedCategories = this.editItem.nutrients.map(nutrient => nutrient.category)
      return this.nutrients.filter(nutrientCategory => !selectedCategories.includes(nutrientCategory))
    }
  },
  watch: {
    item(item) {
      this.editItem = (item || this.defaultItem)
    }
  },
  methods: {
    close () {
      this.$nextTick(() => {
        this.editItem = Object.assign({}, this.defaultItem)
      })
      this.$emit('close')
    },
    save () {
      if(!this.validate()) {
        return
      }
      this.$http.post(`/api/foods`, this.editItem)
      this.$emit('save')
      this.close()
    },
    validate() {
      if (!this.editItem.name) {
        alert('음식명을 입력해주세요.')
        return false
      }

      if (this.editItem.nutrients.length === 0) {
        alert('영양성분을 입력해주세요.')
        return false
      }

      return true
    },
    addNutrient() {
      const unselectedNutrients = this.unselectedNutrients
      if (unselectedNutrients.length === 0) {
        return
      }
      const newNutrient = unselectedNutrients[0]
      this.editItem.nutrients.push({
        category: newNutrient,
        amount: 0
      })
    },
    deleteNutrient(category) {
      this.editItem.nutrients = _.remove(this.editItem.nutrients, item => item.category !== category)
    }
  }
}
</script>

<style scoped>

</style>