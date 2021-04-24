<template>
  <div>
    <v-sheet height="64">
      <v-toolbar
          flat
      >
        <v-btn
            icon
            class="ma-2"
            @click="$refs.calendar.prev()"
        >
          <v-icon>mdi-chevron-left</v-icon>
        </v-btn>
        <v-btn
            outlined
            class="mr-4"
            color="grey darken-2"
            @click="setToday"
        >
          Today
        </v-btn>
        <v-spacer></v-spacer>
        <v-toolbar-title v-if="$refs.calendar">
          {{ $refs.calendar.title }}
        </v-toolbar-title>
        <v-spacer></v-spacer>
        <v-btn
            icon
            class="ma-2"
            @click="$refs.calendar.next()"
        >
          <v-icon>mdi-chevron-right</v-icon>
        </v-btn>
      </v-toolbar>
    </v-sheet>

    <v-calendar
        ref="calendar"
        v-model="focus"
        :weekdays="weekday"
        :type="type"
        :events="events"
        :event-overlap-mode="mode"
        :event-overlap-threshold="30"
        :event-color="getEventColor"
        @click:date="viewDay"
        @click:event="showEvent"
        @change="getEvents"
    ></v-calendar>
    <intake-dialog
        v-model="selectedOpen"
        :date="selectedDate"
        :intake-id="selectedIntakeId"
    ></intake-dialog>
  </div>
</template>

<script>
import IntakeDialog from "@/components/IntakeDialog";

export default {
  name: "IntakeCalendar",
  components: {
    IntakeDialog,
  },
  data: () => ({
    type: 'month',
    mode: 'stack',
    weekday: [0, 1, 2, 3, 4, 5, 6],
    focus: '',
    events: [],
    colors: ['blue', 'indigo', 'deep-purple', 'cyan', 'green', 'orange', 'grey darken-1'],
    selectedEvent: {},
    selectedElement: null,
    selectedOpen: false,
    selectedDate: null,
  }),
  computed: {
    selectedIntakeId() {
      return this.selectedEvent && this.selectedEvent.intakeId
    }
  },
  methods: {
    getEvents ({ start, end }) {
      this.$http.get(`/api/intakes?startDate=${start.date}&endDate=${end.date}`)
        .then(response => {
          const events = response.data.map(intake => {
            return {
              intakeId: intake.id,
              name: intake.food.name,
              start: `${intake.date}`,
              end: `${intake.date}`,
              timed: false,
              color: this.colors[this.rnd(0, this.colors.length - 1)]
            }
          })

          this.events = events
        })
    },
    clearSelectedItem() {
      this.selectedEvent = null
      this.selectedDate = null
      this.selectedElement = null
    },
    showEvent ({ nativeEvent, event }) {
      const open = () => {
        this.clearSelectedItem()
        this.selectedEvent = event
        this.selectedElement = nativeEvent.target
        setTimeout(() => {
          this.selectedOpen = true
        }, 10)
      }

      if (this.selectedOpen) {
        this.selectedOpen = false
        setTimeout(open, 10)
      } else {
        open()
      }

      nativeEvent.stopPropagation()
    },
    viewDay (date) {
      const open = () => {
        this.clearSelectedItem()
        this.selectedDate = date.date
        setTimeout(() => {
          this.selectedOpen = true
        }, 10)
      }

      if (this.selectedOpen) {
        this.selectedOpen = false
        setTimeout(open, 10)
      } else {
        open()
      }
    },
    getEventColor (event) {
      return event.color
    },
    rnd (a, b) {
      return Math.floor((b - a + 1) * Math.random()) + a
    },
    setToday () {
      this.focus = ''
    },
  },
}
</script>

<style scoped>

</style>