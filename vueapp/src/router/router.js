import IntakeCalendar from "@/components/IntakeCalendar";
import FoodList from "@/components/FoodList";
import Main from "@/components/Main";

export const routeName = {
    food: "food",
    intakeCalendar: "intake-calendar",
    login: "login"
}

export const routes = [
    {
        path: '/',
        name: routeName.intakeCalendar,
        component: IntakeCalendar,
        meta: {
            requiresAuth: true,
        },
    },
    {
        path: '/food',
        name: routeName.food,
        component: FoodList,
        meta: {
            requiresAuth: true,
        },
    },
    {
        path: '/login',
        name: routeName.login,
        component: Main,
    },
]