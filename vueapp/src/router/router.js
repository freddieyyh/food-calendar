import IntakeCalendar from "@/components/IntakeCalendar";
import FoodList from "@/components/FoodList";

export const routeName = {
    food: "food",
    intakeCalendar: "intake-calendar"
}

export const routes = [
    {
        path: '/calendar',
        name: routeName.intakeCalendar,
        component: IntakeCalendar,
    },
    {
        path: '/food',
        name: routeName.food,
        component: FoodList,
    },
]