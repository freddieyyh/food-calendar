<template>
  <div class="home">
    <intake-calendar></intake-calendar>
  </div>
</template>

<script>
// @ is an alias to /src
import IntakeCalendar from "@/components/IntakeCalendar";

export default {
  name: 'Home',
  components: {
    IntakeCalendar
  }
}
</script>
