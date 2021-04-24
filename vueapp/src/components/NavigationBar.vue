<template>
  <div>
    <v-app-bar
        color="deep-purple accent-4"
        dark
    >
      <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
      식단관리앱
    </v-app-bar>

    <v-navigation-drawer
        v-model="drawer"
        absolute
        bottom
        temporary
    >
      <v-list
          nav
          dense
      >
        <v-list-item-group
            v-model="group"
            mandatory
            active-class="deep-purple--text text--accent-4"
        >
          <v-list-item
              v-for="(tab, i) in tabs"
              :key="i"
              :to="{ name: tab.route.name }"
          >
            <v-list-item-title>{{ tab.name }}</v-list-item-title>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>
  </div>
</template>

<script>
import {routeName} from "@/router/router";

export default {
  name: "NavigationBar",
  data: () => ({
    drawer: false,
    group: 1,
    tabs: [
      {
        id: "intake-calendar",
        name: "식단달력",
        route: {
          name: routeName.intakeCalendar
        }
      },
      {
        id: "food",
        name: "음식관리",
        route: {
          name: routeName.food
        }
      }
    ]
  }),
  watch: {
    group() {
      this.drawer = false
    },
  },
}
</script>

<style scoped>

</style>