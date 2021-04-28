<template>
  <v-card>
    <v-card-title>
      음식관리
      <v-spacer></v-spacer>
      <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
          hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
        :headers="headers"
        :items="foods"
        :options.sync="options"
        :server-items-length="totalFoods"
        :loading="loading"
        class="elevation-1"
    >
      <template v-slot:top>
        <v-toolbar
            flat
        >
          <v-dialog
              v-model="dialog"
              max-width="500px"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                  color="primary"
                  dark
                  class="mb-2"
                  v-bind="attrs"
                  v-on="on"
              >
                New Item
              </v-btn>
            </template>
            <food-form
                form-title="등록하기"
                :item="editedItem"
                @close="closeEditForm"
                @save="save"
            >
            </food-form>
          </v-dialog>
          <v-dialog v-model="dialogDelete" max-width="500px">
            <v-card>
              <v-card-title class="headline">Are you sure you want to delete this item?</v-card-title>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="closeDelete">Cancel</v-btn>
                <v-btn color="blue darken-1" text @click="deleteItemConfirm">OK</v-btn>
                <v-spacer></v-spacer>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-toolbar>
      </template>
      <template v-slot:item.servingSize="{ item }">
        {{ `${item.servingSize.amount} ${item.servingSize.unit}` }}
      </template>
      <template v-slot:item.proteins="{ item }">
        {{ item.nutrientMap['PROTEIN'] }}
      </template>
      <template v-slot:item.fats="{ item }">
        {{ item.nutrientMap['FAT'] }}
      </template>
      <template v-slot:item.carbs="{ item }">
        {{ item.nutrientMap['CARBOHYDRATE'] }}
      </template>
      <template v-slot:item.calories="{ item }">
        <v-chip
            :color="getColor(item.nutrientMap['CALORIES'])"
            dark
        >
          {{ item.nutrientMap['CALORIES'] }}
        </v-chip>
      </template>
      <template v-slot:item.actions="{ item }">
        <v-icon
            small
            class="mr-2"
            @click="editItem(item)"
        >
          mdi-pencil
        </v-icon>
        <v-icon
            small
            @click="deleteItem(item)"
        >
          mdi-delete
        </v-icon>
      </template>
      <template v-slot:no-data>
        <v-btn
            color="primary"
            @click="getDataFromApi"
        >
          Reload
        </v-btn>
      </template>
    </v-data-table>
  </v-card>
</template>

<script>
import FoodForm from "@/components/FoodForm";

export default {
  name: "FoodList",
  components: {
    FoodForm
  },
  data () {
    return {
      search: '',
      headers: [
        {
          text: '음식명',
          align: 'start',
          sortable: false,
          value: 'name',
        },
        { text: '제공단위', value: 'servingSize', sortable: false },
        { text: '칼로리', value: 'calories' },
        { text: '지방 (g)', value: 'fats' },
        { text: '탄수화물 (g)', value: 'carbs' },
        { text: '단백질 (g)', value: 'proteins' },
        { text: '', value: 'actions' },
      ],
      totalFoods: 0,
      foods: [],
      loading: true,
      options: {},
      editedItem: null,
      dialog: false,
      dialogDelete: false,
    }
  },
  watch: {
    options: {
      handler () {
        this.getDataFromApi()
      },
      deep: true,
    },
    search: {
      handler(newValue) {
        this.getDataFromApi(newValue)
      }
    }
  },
  methods: {
    getDataFromApi (name) {
      this.loading = true
      this.fetchFoodData(name).then(response => {
        const data = response.data
        this.foods = data.content.map(item => {
          const nutrientMap = item.nutrients.reduce(function(map, obj) {
            map[obj.category] = obj.amount;
            return map;
          }, {});

          return {
            id: item.id,
            name: item.name,
            servingSize: item.servingSize,
            nutrients: item.nutrients,
            nutrientMap: nutrientMap,
          }
        })
        this.totalFoods = data.totalElements
        this.loading = false
      })
    },
    getColor (calories) {
      if (calories > 400) return 'red'
      else if (calories > 200) return 'orange'
      else return 'green'
    },
    fetchFoodData(name) {
      const { sortBy, sortDesc, page, itemsPerPage } = this.options
      return this.$http.get(`/api/foods`, {
        params : {
          page: page - 1,
          size: itemsPerPage,
          sortBy: sortBy,
          sortDesc: sortDesc,
          name: name || ''
        }
      })
    },
    editItem (item) {
      this.editedItem = Object.assign({}, item)
      this.dialog = true
    },
    deleteItem (item) {
      this.editedItem = Object.assign({}, item)
      this.dialogDelete = true
    },
    deleteItemConfirm () {
      this.$http.delete(`/api/foods/${this.editedItem.id}`)
        .then(() => {
          this.closeDelete()
          this.$nextTick(() => {
            this.getDataFromApi()
          })
        })
    },
    closeDelete () {
      this.dialogDelete = false
    },
    closeEditForm() {
      this.editedItem = null
      this.dialog = false
    },
    save() {
      this.getDataFromApi()
    }
  },
  mounted () {
    this.getDataFromApi()
  },
}
</script>

<style scoped>

</style>